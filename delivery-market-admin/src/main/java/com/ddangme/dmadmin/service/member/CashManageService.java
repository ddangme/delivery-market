package com.ddangme.dmadmin.service.member;

import com.ddangme.dmadmin.dto.cash.CashChangeRequest;
import com.ddangme.dmadmin.dto.cash.CashListProjection;
import com.ddangme.dmadmin.exception.DMAdminException;
import com.ddangme.dmadmin.exception.ErrorCode;
import com.ddangme.dmadmin.model.admin.Admin;
import com.ddangme.dmadmin.model.cash.CashCharging;
import com.ddangme.dmadmin.repository.admin.AdminRepository;
import com.ddangme.dmadmin.repository.cash.CashRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Slf4j
@Service
@RequiredArgsConstructor
@Transactional
public class CashManageService {

    private final CashRepository cashRepository;
    private final AdminRepository adminRepository;

    public Page<CashListProjection> search(Pageable pageable) {
        return cashRepository.search(pageable);
    }

    @Transactional
    public void changeStatus(Long adminId, CashChangeRequest request) {
        Admin admin = adminRepository.findById(adminId)
                .orElseThrow(() -> new DMAdminException(ErrorCode.NOT_EXIST_ADMIN_ACCOUNT));

        request.getIds()
                .forEach(id -> findCashCharging(id).changeStatus(request.getStatus(), admin));
    }

    private CashCharging findCashCharging(Long id) {
        return cashRepository.findById(id)
                .orElseThrow(() -> new DMAdminException(ErrorCode.NOT_EXIST_CASH_CHARGING));
    }


}
