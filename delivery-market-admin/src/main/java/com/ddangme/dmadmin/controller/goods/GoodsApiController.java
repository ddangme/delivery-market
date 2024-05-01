package com.ddangme.dmadmin.controller.goods;

import com.ddangme.dmadmin.dto.goods.request.GoodsSaveRequest;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@Slf4j
@RequiredArgsConstructor
@RequestMapping("/api/goods")
public class GoodsApiController {

    @PostMapping("/add")
    public String add( GoodsSaveRequest request) {
        log.info("request={}", request);

        return "/goods/goods-list";
    }
}
