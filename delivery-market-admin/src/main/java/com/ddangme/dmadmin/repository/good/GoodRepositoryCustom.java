package com.ddangme.dmadmin.repository.good;

import com.ddangme.dmadmin.dto.good.response.GoodListResponse;
import com.ddangme.dmadmin.dto.good.response.GoodOptionResponse;
import com.ddangme.dmadmin.dto.good.response.GoodResponse;
import com.ddangme.dmadmin.dto.good.response.GoodSaleResponse;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface GoodRepositoryCustom {

    Page<GoodListResponse> search(Pageable pageable);

    Page<GoodResponse> searchForMember(Pageable pageable);

    Page<GoodSaleResponse> searchGoodsInCategoryId(Pageable pageable, Long categoryId);
}
