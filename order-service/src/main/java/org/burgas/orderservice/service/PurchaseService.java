package org.burgas.orderservice.service;

import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.burgas.orderservice.dto.ProductResponse;
import org.burgas.orderservice.dto.PurchaseRequest;
import org.burgas.orderservice.dto.StoreResponse;
import org.burgas.orderservice.entity.Purchase;
import org.burgas.orderservice.entity.Tab;
import org.burgas.orderservice.exception.*;
import org.burgas.orderservice.handler.RestClientHandler;
import org.burgas.orderservice.mapper.PurchaseMapper;
import org.burgas.orderservice.mapper.TabMapper;
import org.burgas.orderservice.repository.PurchaseRepository;
import org.burgas.orderservice.repository.TabRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Objects;
import java.util.UUID;

import static org.springframework.transaction.annotation.Isolation.SERIALIZABLE;
import static org.springframework.transaction.annotation.Propagation.REQUIRED;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class PurchaseService {

    private final PurchaseRepository purchaseRepository;
    private final TabRepository tabRepository;
    private final PurchaseMapper purchaseMapper;
    private final TabMapper tabMapper;
    private final RestClientHandler restClientHandler;


    @Transactional(
            isolation = SERIALIZABLE,
            propagation = REQUIRED,
            rollbackFor = RuntimeException.class
    )
    public String makeUnauthorizedAccountPurchase(
            PurchaseRequest purchaseRequest, Cookie unauthorizedCookie,
            HttpServletRequest request, HttpServletResponse response
    ) {

        if (Boolean.FALSE.equals(restClientHandler.isAuthenticated(request).getBody())) {

            if (unauthorizedCookie == null) {
                unauthorizedCookie = new Cookie("unauthorized-cookie", UUID.randomUUID().toString());
                unauthorizedCookie.setHttpOnly(true);
                unauthorizedCookie.setMaxAge(3600);
                response.addCookie(unauthorizedCookie);
            }

            ProductResponse productResponse = restClientHandler.
                    getProductByProductId(purchaseRequest.getProductId(), request).getBody();

            if (productResponse != null && productResponse.getAmount() <= 0) {
                throw new ProductOutOfStockException("Товар отсутствует на складе");
            }

            Purchase purchase = purchaseRepository.save(purchaseMapper.toPurchase(purchaseRequest));
            Cookie finalUnauthorizedCookie = unauthorizedCookie;
            Tab tab = tabRepository.findTabByUnauthorizedCookieValueAndIsOpenIsTrue(unauthorizedCookie.getValue())
                    .orElseGet(
                            () -> tabRepository.save(
                                    tabMapper.toNewUnauthorizedAccountTab(purchaseRequest, finalUnauthorizedCookie.getValue())
                            )
                    );
            savingPurchaseAndTab(purchaseRequest, productResponse, purchase, tab);
            return "Покупка совершена анонимно";

        } else throw new IdentityAuthorizedForPurchase("Пользователь авторизован для совершения покупки");
    }

    private void savingPurchaseAndTab(
            PurchaseRequest purchaseRequest, ProductResponse productResponse,
            Purchase purchase, Tab tab
    ) {
        purchase.setTab(tab);
        purchase = purchaseRepository.save(purchase);
        tab.getPurchases().add(purchase);

        if (productResponse != null) {
            if (purchaseRequest.getAmount() >= productResponse.getAmount()) {
                productResponse.setPrice(purchaseRequest.getAmount());
            }
            tab.setTotalPrice(tab.getTotalPrice() + (productResponse.getPrice() * purchase.getAmount()));
            purchaseRepository.updateProductSetAmount(
                    productResponse.getAmount() - purchase.getAmount(), productResponse.getId()
            );
        }

        tabRepository.save(tab);
    }

    @Transactional(
            isolation = SERIALIZABLE,
            propagation = REQUIRED,
            rollbackFor = RuntimeException.class
    )
    public String makePurchase(PurchaseRequest purchaseRequest, HttpServletRequest request) {

        if (Boolean.TRUE.equals(restClientHandler.isAuthenticated(request).getBody())) {
            Long authorizedCredentialId = restClientHandler.getAuthenticationCredentialId(request).getBody();

            if (Objects.equals(authorizedCredentialId, purchaseRequest.getIdentityId())) {
                ProductResponse productResponse = restClientHandler.
                        getProductByProductId(purchaseRequest.getProductId(), request).getBody();

                if (productResponse != null && productResponse.getAmount() <= 0) {
                    throw new ProductOutOfStockException("Товар отсутствует на складе");
                }

                Purchase purchase = purchaseRepository.save(purchaseMapper.toPurchase(purchaseRequest));
                Tab tab = tabRepository.findTabByIdentityIdAndIsOpenIsTrue(purchaseRequest.getIdentityId())
                        .orElseGet(
                                () -> tabRepository.save(tabMapper.toNewTab(purchaseRequest))
                        );
                savingPurchaseAndTab(purchaseRequest, productResponse, purchase, tab);
                return "Покупка совершена";

            } else throw new WrongIdentityPurchaseException("Попытка совершения покупки за другого пользователя");

        } else throw new IdentityNotAuthorizedException("Пользователь не авторизован");
    }

    @Transactional(
            isolation = SERIALIZABLE,
            propagation = REQUIRED,
            rollbackFor = RuntimeException.class
    )
    public String deletePurchase(Long purchaseId, Long tabId, HttpServletRequest request) {

        if (Boolean.TRUE.equals(restClientHandler.isAuthenticated(request).getBody())) {
            Long authorizedCredentialId = restClientHandler.getAuthenticationCredentialId(request).getBody();
            Tab tab = tabRepository.findById(tabId).orElse(null);

            if (Objects.equals(Objects.requireNonNull(tab).getIdentityId(), authorizedCredentialId)) {

                if (removePurchaseFromTabIfExists(purchaseId, tab, request))
                    return "Покупка с идентификатором " + purchaseId + " удалена из заказа и базы";
                else
                    throw new TabAlreadyDoneException("Заказ уже выполнен, удалить совершенную покупку невозможно");

            } else throw new WrongIdentityPurchaseException("Попытка удаления чужой покупки");

        } else throw new IdentityNotAuthorizedException("Пользователь не авторизован");
    }

    @Transactional(
            isolation = SERIALIZABLE,
            propagation = REQUIRED,
            rollbackFor = RuntimeException.class
    )
    public String deleteUnauthorizedAccountPurchase(Cookie unauthorizedCookie, Long purchaseId, HttpServletRequest request) {

        if (Boolean.FALSE.equals(restClientHandler.isAuthenticated(request).getBody())) {

            if (unauthorizedCookie != null) {
                Tab tab = tabRepository.findTabByUnauthorizedCookieValue(unauthorizedCookie.getValue())
                        .orElseThrow(
                                () -> new TabNotFoundException("Заказ по номеру куки не найден")
                        );

                if (removePurchaseFromTabIfExists(purchaseId, tab, request))
                    return "Покупка с идентификатором " + purchaseId + " удалена из заказа и базы";
            }
        }
        throw new TabAlreadyDoneException(
                "Заказ 'Unauthorized' уже выполнен, удалить совершенную покупку невозможно"
        );
    }

    private boolean removePurchaseFromTabIfExists(Long purchaseId, Tab tab, HttpServletRequest request) {
        if (tab != null && tab.getIsOpen()) {

            Purchase purchase = purchaseRepository.findById(purchaseId).orElse(null);

            if (purchase != null) {
                ProductResponse productResponse = restClientHandler.
                        getProductByProductId(purchase.getProductId(), request).getBody();

                if (productResponse != null) {
                    purchaseRepository.updateProductSetAmount(
                            productResponse.getAmount() + purchase.getAmount(), productResponse.getId()
                    );
                    tab.setTotalPrice(tab.getTotalPrice() - (productResponse.getPrice() * purchase.getAmount()));
                    tab.removeFromPurchases(purchase);
                    tabRepository.save(tab);
                    purchaseRepository.deleteById(purchaseId);

                    return true;
                }
            }
        }
        return false;
    }

    @Transactional(
            isolation = SERIALIZABLE,
            propagation = REQUIRED,
            rollbackFor = RuntimeException.class
    )
    public String incrementPurchaseProductAmount(Long purchaseId, Long productId, HttpServletRequest request) {

        if (Boolean.TRUE.equals(restClientHandler.isAuthenticated(request).getBody())) {
            Long authenticatedIdentityId = restClientHandler.getAuthenticationCredentialId(request).getBody();
            Purchase purchase = purchaseRepository.findPurchasesByIdAndProductId(purchaseId, productId)
                    .orElseThrow(
                            () -> new PurchaseNotFoundException("Искомая покупка не найдена")
                    );
            Tab tab = purchase.getTab();
            if (!Objects.equals(authenticatedIdentityId, tab.getIdentityId()))
                throw new IdentityNotMatchException("Попытка изменения покупки из чужого заказа");

            if (Objects.equals(authenticatedIdentityId, purchase.getIdentityId())) {
                StoreResponse storeResponse = restClientHandler
                        .getStoreByStoreId(tab.getStoreId(), request).getBody();
                Integer productAmountInStore = purchaseRepository
                        .findProductAmountByStoreIdAndProductId(
                                Objects.requireNonNull(storeResponse).getId(), productId
                        );

                if (Objects.equals(purchase.getAmount(), productAmountInStore))
                    throw new ProductOutOfStockException("На данный момент это весь конкретный товар в магазине");

                else {
                    purchase.setAmount(purchase.getAmount() + 1);
                    purchase = purchaseRepository.save(purchase);
                    return "Количество товара в корзине увеличено: " + purchase.getAmount();
                }

            } else
                throw new IdentityNotMatchException("Попытка изменения чужих данных");
        }
        throw new IdentityNotAuthorizedException("Пользователь не авторизован для изменения данных о покупке");
    }

    @Transactional(
            isolation = SERIALIZABLE,
            propagation = REQUIRED,
            rollbackFor = RuntimeException.class
    )
    public String decrementPurchaseProductAmount(Long purchaseId, Long productId, HttpServletRequest request) {

        if (Boolean.TRUE.equals(restClientHandler.isAuthenticated(request).getBody())) {
            Long authenticatedIdentityId = restClientHandler.getAuthenticationCredentialId(request).getBody();
            Purchase purchase = purchaseRepository.findPurchasesByIdAndProductId(purchaseId, productId)
                    .orElseThrow(
                            () -> new PurchaseNotFoundException("Искомая покупка не найдена")
                    );
            Tab tab = purchase.getTab();
            if (!Objects.equals(authenticatedIdentityId, tab.getIdentityId()))
                throw new IdentityNotMatchException("Попытка изменения покупки из чужого заказа");

            if (Objects.equals(authenticatedIdentityId, purchase.getIdentityId())) {

                if (purchase.getAmount() <= 1)
                    throw new ProductOutOfStockException("Вы не можете приобрести товар в количестве меньше 1");

                else {
                    purchase.setAmount(purchase.getAmount() - 1);
                    purchase = purchaseRepository.save(purchase);
                    return "Количество товара в корзине уменьшено: " + purchase.getAmount();
                }

            } else
                throw new IdentityNotMatchException("Попытка изменения чужих данных");
        }
        throw new IdentityNotAuthorizedException("Пользователь не авторизован для изменения данных о покупке");
    }
}
