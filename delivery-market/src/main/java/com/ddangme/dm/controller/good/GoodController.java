package com.ddangme.dm.controller.good;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

@Slf4j
@Controller
@RequiredArgsConstructor
public class GoodController {

    @GetMapping("/goods")
    public String goodsList() {
        return "good/good";
    }

    @GetMapping("/goods/{goodId}")
    public String goodsDetail(@PathVariable Long goodId) {
        log.info("goodId={}", goodId);
        return "good/good-detail";
    }

}
