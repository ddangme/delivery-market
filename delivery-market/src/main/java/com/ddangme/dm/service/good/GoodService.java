package com.ddangme.dm.service.good;

import com.ddangme.dm.dto.good.response.GoodResponse;
import com.ddangme.dm.dto.good.response.GoodSaleDetailResponse;
import com.ddangme.dm.exception.DMException;
import com.ddangme.dm.exception.ErrorCode;
import com.ddangme.dm.model.good.Good;
import com.ddangme.dm.repository.good.GoodRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Slf4j
@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class GoodService {

    private final GoodRepository goodRepository;

    public Page<GoodResponse> findSaleStatusGoods(Pageable pageable) {
        return goodRepository.findSaleStatusGood(pageable);
    }

    public GoodSaleDetailResponse findGoodDetail(Long goodId) {
        Good good = goodRepository.findById(goodId)
                .orElseThrow(() -> new DMException(ErrorCode.NOT_FOUND_GOOD));

        return GoodSaleDetailResponse.fromEntity(good);
    }


}
