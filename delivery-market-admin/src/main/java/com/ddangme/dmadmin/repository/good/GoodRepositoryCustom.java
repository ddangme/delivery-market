package com.ddangme.dmadmin.repository.good;

import com.ddangme.dmadmin.dto.good.response.*;
import com.ddangme.dmadmin.model.good.Good;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.Optional;

public interface GoodRepositoryCustom {

    Page<GoodListResponse> search(Pageable pageable);

    Page<GoodResponse> searchForMember(Pageable pageable);

    Page<GoodSaleResponse> searchGoodsInCategoryId(Pageable pageable, Long categoryId);

    Optional<Good> searchSaleGoodByGoodId(Long goodId);

}
