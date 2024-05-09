package com.ddangme.dmadmin.repository.good;

import com.ddangme.dmadmin.dto.good.GoodListResponse;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface GoodRepositoryCustom {

    Page<GoodListResponse> search(Pageable pageable);
}
