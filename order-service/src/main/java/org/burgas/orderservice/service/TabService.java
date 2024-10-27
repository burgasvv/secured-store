package org.burgas.orderservice.service;

import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import org.burgas.orderservice.dto.TabResponse;
import org.burgas.orderservice.entity.Purchase;
import org.burgas.orderservice.entity.Tab;
import org.burgas.orderservice.exception.IdentityNotAuthorizedException;
import org.burgas.orderservice.exception.IdentityNotMatchException;
import org.burgas.orderservice.exception.TabNotFoundException;
import org.burgas.orderservice.handler.RestTemplateHandler;
import org.burgas.orderservice.mapper.TabMapper;
import org.burgas.orderservice.repository.PurchaseRepository;
import org.burgas.orderservice.repository.TabRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Objects;

import static org.springframework.transaction.annotation.Isolation.SERIALIZABLE;
import static org.springframework.transaction.annotation.Propagation.REQUIRED;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class TabService {

    private final TabRepository tabRepository;
    private final TabMapper tabMapper;
    private final RestTemplateHandler restTemplateHandler;
    private final PurchaseService purchaseService;
    private final PurchaseRepository purchaseRepository;

    @Transactional(
            isolation = SERIALIZABLE,
            propagation = REQUIRED,
            rollbackFor = RuntimeException.class
    )
    public String finishTab(Long tabId, Long paymentTypeId, HttpServletRequest request) {

        if (Boolean.TRUE.equals(restTemplateHandler.isAuthenticated(request).getBody())) {
            Long authenticatedIdentityId = restTemplateHandler.getAuthenticationCredentialId(request).getBody();
            Tab tab = tabRepository.findById(tabId).orElseThrow(
                    () -> new TabNotFoundException("Заказ с идентификатором " + tabId + " не найден")
            );
            if (Objects.equals(authenticatedIdentityId, tab.getIdentityId())) {
                tab.setPaymentTypeId(paymentTypeId);
                tab.setIsOpen(false);
                tab.setCloseDate(LocalDateTime.now());
                tabRepository.save(tab);
                return "Заказ только что совершен и закрыт, спасибо за покупки";

            } else
                throw new IdentityNotMatchException(
                        "Идентификатор авторизованного пользователя не совпадает с идентификатором владельца заказа"
                );

        } else
            throw new IdentityNotAuthorizedException("Пользователь не авторизован для оформления заказа");

    }

    public List<TabResponse> findTabsByIdentityId(Long identityId, HttpServletRequest request) {
        return tabRepository.findTabsByIdentityId(identityId)
                .stream()
                .map(
                        tab -> tabMapper.toTabResponse(tab, request)
                )
                .toList();
    }

    public TabResponse findTabByIdentityIdAndTabId(Long identityId, Long tabId, HttpServletRequest request) {
        return tabRepository.findTabByIdentityIdAndId(identityId, tabId)
                .map(
                        tab -> tabMapper.toTabResponse(tab, request)
                )
                .orElseGet(TabResponse::new);
    }

    public TabResponse findUnauthorizedAccountTab(Cookie unauthorizedCookie, HttpServletRequest request) {

        if (Boolean.FALSE.equals(restTemplateHandler.isAuthenticated(request).getBody())) {

            if (unauthorizedCookie != null) {
                return tabRepository.findTabByUnauthorizedCookieValue(unauthorizedCookie.getValue())
                        .map(tab -> tabMapper.toTabResponse(tab, request))
                        .orElseGet(TabResponse::new);
            }
        }
        throw new TabNotFoundException("Заказ не был найден");
    }

    @Transactional(
            isolation = SERIALIZABLE,
            propagation = REQUIRED,
            rollbackFor = RuntimeException.class
    )
    public String closeUnauthorizedAccountTab(Cookie unauthorizedCookie, HttpServletRequest request) {

        if (Boolean.FALSE.equals(restTemplateHandler.isAuthenticated(request).getBody())) {

            if (unauthorizedCookie != null) {
                Tab tab = tabRepository.findTabByUnauthorizedCookieValue(unauthorizedCookie.getValue())
                        .orElseThrow(
                                () -> new TabNotFoundException("Заказ 'Unauthorized' не найден")
                        );
                tab.setIsOpen(false);
                tab.setCloseDate(LocalDateTime.now());
                tabRepository.save(tab);
                return "Заказ 'Unauthorized' был успешно оформлен и закрыт";
            }
        }
        throw new TabNotFoundException("Заказ не был найден");
    }

    @Transactional(
            isolation = SERIALIZABLE,
            propagation = REQUIRED,
            rollbackFor = RuntimeException.class
    )
    public String deleteTabByIdIfNotFinished(Long tabId, HttpServletRequest request) {

        if (Boolean.TRUE.equals(restTemplateHandler.isAuthenticated(request).getBody())) {
            Long authenticatedIdentityId = restTemplateHandler.getAuthenticationCredentialId(request).getBody();
            Tab tab = tabRepository.findById(tabId).orElseGet(Tab::new);

            if (Objects.equals(authenticatedIdentityId, tab.getIdentityId())) {

                if (tab.getIsOpen()) {
                    List<Purchase> purchases = purchaseRepository.findPurchasesByTabId(tabId);
                    purchases.forEach(
                            purchase -> purchaseService.deletePurchase(purchase.getId(), tabId, request)
                    );
                    tabRepository.deleteById(tabId);
                    return "Заказ отменен, корзина очищена";

                } else return "Заказ уже завершен и не может быть удален или очищен";

            } else
                throw new IdentityNotMatchException(
                        "Идентификатор авторизованного пользователя не совпадает с идентификатором владельца заказа"
                );

        } else
            throw new IdentityNotAuthorizedException(
                "Пользователь не авторизован для удаления не завершенного заказа"
        );
    }

    public TabResponse findById(Long tabId, HttpServletRequest request) {
        return tabRepository.findById(tabId)
                .map(
                        tab -> tabMapper.toTabResponse(tab, request)
                )
                .orElseGet(TabResponse::new);
    }
}
