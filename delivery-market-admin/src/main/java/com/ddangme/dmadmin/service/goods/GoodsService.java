package com.ddangme.dmadmin.service.goods;

import com.ddangme.dmadmin.dto.goods.GoodsDTO;
import com.ddangme.dmadmin.dto.goods.GoodsListResponse;
import com.ddangme.dmadmin.repository.goods.GoodsDetailRepository;
import com.ddangme.dmadmin.repository.goods.GoodsOptionRepository;
import com.ddangme.dmadmin.repository.goods.GoodsRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

@Slf4j
@Service
@RequiredArgsConstructor
public class GoodsService {

    private final GoodsRepository goodsRepository;
    private final GoodsDetailRepository goodsDetailRepository;
    private final GoodsOptionRepository goodsOptionRepository;

    public Page<GoodsListResponse> search(Pageable pageable) {
        return goodsRepository.search(pageable);
    }
}
