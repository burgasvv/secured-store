package org.burgas.orderservice.service;

import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.burgas.orderservice.dto.ProductResponse;
import org.burgas.orderservice.dto.PurchaseRequest;
import org.burgas.orderservice.entity.Purchase;
import org.burgas.orderservice.entity.Tab;
import org.burgas.orderservice.exception.ProductOutOfStockException;
import org.burgas.orderservice.handler.RestTemplateHandler;
import org.burgas.orderservice.mapper.PurchaseMapper;
import org.burgas.orderservice.mapper.TabMapper;
import org.burgas.orderservice.repository.PurchaseRepository;
import org.burgas.orderservice.repository.TabRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.Duration;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
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
    private final RestTemplateHandler restTemplateHandler;


    @Transactional(
            isolation = SERIALIZABLE,
            propagation = REQUIRED,
            rollbackFor = RuntimeException.class
    )
    public String makeUnauthorizedAccountPurchase(
            PurchaseRequest purchaseRequest, HttpServletRequest request, HttpServletResponse response
    ) {

        if (Boolean.FALSE.equals(restTemplateHandler.isAuthenticated().getBody())) {
            Cookie[] temp = request.getCookies();
            List<Cookie> cookies = new ArrayList<>();
            if (temp != null) {
                cookies = Arrays.stream(temp)
                        .toList().stream().filter(
                                cookie -> cookie.getName().equalsIgnoreCase("unauthorized-cookie")
                        ).toList();
            }

            Cookie findCookie;
            if (cookies.isEmpty()) {
                findCookie = new Cookie("unauthorized-cookie", UUID.randomUUID().toString());
                findCookie.setHttpOnly(true);
                findCookie.setMaxAge(Duration.ofMinutes(60).getNano());
                response.addCookie(findCookie);

            } else {
                findCookie = cookies.getFirst();
            }

            ProductResponse productResponse = restTemplateHandler.
                    getProductByProductId(purchaseRequest.getProductId()).getBody();

            if (productResponse != null && productResponse.getAmount() <= 0) {
                throw new ProductOutOfStockException("Товар отсутствует на складе");
            }

            Purchase purchase = purchaseRepository.save(purchaseMapper.toPurchase(purchaseRequest));
            Cookie finalFindCookie = findCookie;
            Tab tab = tabRepository.findTabByUnauthorizedCookieValueAndIsOpenIsTrue(findCookie.getValue())
                    .orElseGet(
                            () -> tabRepository.save(
                                    tabMapper.toNewUnauthorizedAccountTab(purchaseRequest, finalFindCookie.getValue())
                            )
                    );

            savingPurchaseAndTab(purchaseRequest, productResponse, purchase, tab);

            return "Покупка совершена анонимно";
        }

        return "Покупка не совершена";
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
    public String makePurchase(PurchaseRequest purchaseRequest) {

        ProductResponse productResponse = restTemplateHandler.
                getProductByProductId(purchaseRequest.getProductId()).getBody();

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
    }

    @Transactional(
            isolation = SERIALIZABLE,
            propagation = REQUIRED,
            rollbackFor = RuntimeException.class
    )
    public String deletePurchase(Long purchaseId, Long tabId) {

        Tab tab = tabRepository.findById(tabId).orElse(null);

        if (tab != null && tab.getIsOpen()) {

            Purchase purchase = purchaseRepository.findById(purchaseId).orElse(null);

            if (purchase != null) {
                ProductResponse productResponse = restTemplateHandler.
                        getProductByProductId(purchase.getProductId()).getBody();

                if (productResponse != null) {
                    purchaseRepository.updateProductSetAmount(
                            productResponse.getAmount() + purchase.getAmount(), productResponse.getId()
                    );
                    tab.setTotalPrice(tab.getTotalPrice() - (productResponse.getPrice() * purchase.getAmount()));
                    tab.removeFromPurchases(purchase);
                    tabRepository.save(tab);
                    purchaseRepository.deleteById(purchaseId);

                    return "Покупка с идентификатором " + purchaseId + " удалена из заказа и базы";
                }
            }
        }

        return "Заказ уже выполнен, удалить совершенную покупку невозможно";
    }
}
