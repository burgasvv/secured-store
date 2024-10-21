package org.burgas.orderservice.service;

import lombok.RequiredArgsConstructor;
import org.burgas.orderservice.dto.TabResponse;
import org.burgas.orderservice.entity.Tab;
import org.burgas.orderservice.exception.TabNotFoundException;
import org.burgas.orderservice.mapper.TabMapper;
import org.burgas.orderservice.repository.TabRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;

import static org.springframework.transaction.annotation.Isolation.SERIALIZABLE;
import static org.springframework.transaction.annotation.Propagation.REQUIRED;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class TabService {

    private final TabRepository tabRepository;
    private final TabMapper tabMapper;

    @Transactional(
            isolation = SERIALIZABLE,
            propagation = REQUIRED,
            rollbackFor = RuntimeException.class
    )
    public String closeTab(Long tabId) {
        Tab tab = tabRepository.findById(tabId).orElseThrow(
                () -> new TabNotFoundException("Заказ с идентификатором " + tabId + " не найден")
        );
        tab.setIsOpen(false);
        tab.setCloseDate(LocalDateTime.now());
        tabRepository.save(tab);
        return "Заказ только что совершен и закрыт, спасибо за покупки";
    }

    public List<TabResponse> findTabsByIdentityId(Long identityId) {
        return tabRepository.findTabsByIdentityId(identityId)
                .stream().map(tabMapper::toTabResponse)
                .toList();
    }

    public TabResponse findTabByIdentityIdAndTabId(Long identityId, Long tabId) {
        return tabRepository.findTabByIdentityIdAndId(identityId, tabId)
                .map(tabMapper::toTabResponse)
                .orElseGet(TabResponse::new);
    }

}
