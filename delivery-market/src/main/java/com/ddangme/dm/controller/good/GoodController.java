package com.ddangme.dm.controller.good;

import com.ddangme.dm.dto.PageResponseCustom;
import com.ddangme.dm.dto.good.GoodSaleResponse;
import com.ddangme.dm.service.PaginationService;
import com.ddangme.dm.service.good.GoodApiService;
import com.ddangme.dm.service.good.GoodService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import java.util.List;

@Slf4j
@Controller
@RequiredArgsConstructor
public class GoodController {
    private final GoodApiService goodApiService;
    private final PaginationService paginationService;

    @GetMapping("/categories/{categoryId}")
    public String findGoodsInCategory(
            @PageableDefault(size = 1) Pageable pageable, @PathVariable Long categoryId, Model model) {
        PageResponseCustom<List<GoodSaleResponse>> goods = goodApiService.findGoodsInCategory(pageable, categoryId);
        List<Integer> pages = paginationService.getPaginationLength(goods.getNumber(), goods.getTotalPages());
        model.addAttribute("goods", goods.getContent());
        model.addAttribute("categoryId", categoryId);
        model.addAttribute("page", goods.getPage());
        model.addAttribute("pages", pages);

        return "good/category";
    }

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
