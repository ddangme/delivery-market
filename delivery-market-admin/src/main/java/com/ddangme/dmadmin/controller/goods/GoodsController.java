package com.ddangme.dmadmin.controller.goods;

import com.ddangme.dmadmin.dto.goods.GoodsDTO;
import com.ddangme.dmadmin.dto.goods.GoodsListResponse;
import com.ddangme.dmadmin.service.PaginationService;
import com.ddangme.dmadmin.service.goods.GoodsService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.List;

@Slf4j
@Controller
@RequiredArgsConstructor
@RequestMapping("/goods")
public class GoodsController {

    private final GoodsService goodsService;
    private final PaginationService paginationService;

    @GetMapping
    public String list(Model model,
                       @PageableDefault(size = 10, sort = "createdAt", direction = Sort.Direction.DESC)Pageable pageable) {
        Page<GoodsListResponse> goods = goodsService.search(pageable);
        List<Integer> pages = paginationService.getPaginationLength(pageable.getPageNumber(), goods.getTotalPages());

        model.addAttribute("goods", goods);
        model.addAttribute("pages", pages);
        return "goods/goods-list";
    }
}