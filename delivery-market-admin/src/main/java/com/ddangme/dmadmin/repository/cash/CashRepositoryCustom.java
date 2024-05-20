package com.ddangme.dmadmin.repository.cash;

import com.ddangme.dmadmin.dto.cash.CashListProjection;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface CashRepositoryCustom{

    public Page<CashListProjection> search(Pageable pageable);
}
