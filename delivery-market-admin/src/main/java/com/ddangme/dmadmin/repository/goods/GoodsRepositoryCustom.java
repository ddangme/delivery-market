package com.ddangme.dmadmin.repository.goods;

import com.ddangme.dmadmin.dto.goods.GoodsListResponse;
import com.ddangme.dmadmin.model.goods.Goods;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface GoodsRepositoryCustom {

    Page<GoodsListResponse> search(Pageable pageable);
}
