package com.ddangme.dm.controller.good.api;

import com.ddangme.dm.dto.good.response.GoodResponse;
import com.ddangme.dm.dto.good.response.GoodSaleDetailResponse;
import com.ddangme.dm.service.good.GoodService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@Slf4j
@RestController
@RequiredArgsConstructor
@RequestMapping("/api/goods")
public class GoodApiController {

    private final GoodService goodService;

    @GetMapping
    public ResponseEntity<Page<GoodResponse>> findSaleStatusGoods(Pageable pageable) {
        log.info("controller - /api/goods");
        return ResponseEntity.ok().body(goodService.findSaleStatusGoods(pageable));
    }

    @GetMapping("/{goodId}")
    public ResponseEntity<GoodSaleDetailResponse> goodsDetailData(@PathVariable Long goodId) {
        return ResponseEntity.ok(goodService.findGoodDetail(goodId));
    }

}
