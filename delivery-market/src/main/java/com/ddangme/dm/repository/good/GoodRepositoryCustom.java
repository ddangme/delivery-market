package com.ddangme.dm.repository.good;

import com.ddangme.dm.dto.good.response.GoodResponse;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface GoodRepositoryCustom {

    Page<GoodResponse> findSaleStatusGood(Pageable pageable);
}
