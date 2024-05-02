package com.ddangme.dmadmin.service.goods;

import com.ddangme.dmadmin.dto.goods.GoodsDTO;
import com.ddangme.dmadmin.dto.goods.GoodsDetailDTO;
import com.ddangme.dmadmin.dto.goods.GoodsListResponse;
import com.ddangme.dmadmin.dto.goods.GoodsOptionDTO;
import com.ddangme.dmadmin.model.goods.Category;
import com.ddangme.dmadmin.model.goods.Goods;
import com.ddangme.dmadmin.model.goods.GoodsDetail;
import com.ddangme.dmadmin.model.goods.GoodsOption;
import com.ddangme.dmadmin.repository.goods.GoodsDetailRepository;
import com.ddangme.dmadmin.repository.goods.GoodsOptionRepository;
import com.ddangme.dmadmin.repository.goods.GoodsRepository;
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
@Transactional(readOnly = true)
public class GoodsService {

    private final GoodsRepository goodsRepository;
    private final GoodsDetailRepository goodsDetailRepository;
    private final GoodsOptionRepository goodsOptionRepository;

    public Page<GoodsListResponse> search(Pageable pageable) {
        return goodsRepository.search(pageable);
    }

    @Transactional
    public void save(GoodsDTO dto) {
        log.info("dto={}", dto);
        Goods goods = dto.toGoodsEntity();
        goodsRepository.save(goods);

        log.info("goodsId={}", goods.getId());
        GoodsDetail detail = dto.toGoodsDetailEntity(goods);
        List<GoodsOption> options = dto.toGoodsOptionsEntity(goods);

        goods.saveDetail(detail);
        goods.saveOptions(options);

        log.info("goods={}", goods);
    }
}
