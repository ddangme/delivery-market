package com.ddangme.dmadmin.service.member;

import com.ddangme.dmadmin.dto.cash.CashListProjection;
import com.ddangme.dmadmin.repository.cash.CashRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Slf4j
@Service
@RequiredArgsConstructor
@Transactional
public class CashManageService {

    private final CashRepository cashRepository;

    public Page<CashListProjection> search(Pageable pageable) {
        return cashRepository.search(pageable);
    }
}
