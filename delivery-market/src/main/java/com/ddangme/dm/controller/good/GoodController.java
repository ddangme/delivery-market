package com.ddangme.dm.controller.good;

import com.ddangme.dm.dto.PageResponseCustom;
import com.ddangme.dm.dto.good.GoodResponse;
import com.ddangme.dm.dto.good.GoodSaleResponse;
import com.ddangme.dm.service.FileService;
import com.ddangme.dm.service.PaginationService;
import com.ddangme.dm.service.category.CategoryService;
import com.ddangme.dm.service.good.GoodService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.core.io.Resource;
import org.springframework.core.io.UrlResource;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.ResponseBody;

import java.net.MalformedURLException;
import java.util.List;

@Slf4j
@Controller
@RequiredArgsConstructor
public class GoodController {

    private final FileService fileService;
    private final GoodService goodService;
    private final PaginationService paginationService;

    @GetMapping("/categories/{categoryId}")
    public String findGoodsInCategory(
            @PageableDefault(size = 1) Pageable pageable, @PathVariable Long categoryId, Model model) {
        PageResponseCustom<List<GoodSaleResponse>> goods = goodService.findGoodsInCategory(pageable, categoryId);
        List<Integer> pages = paginationService.getPaginationLength(goods.getNumber(), goods.getTotalPages());
        model.addAttribute("goods", goods.getContent());
        model.addAttribute("categoryId", categoryId);
        model.addAttribute("page", goods.getPage());
        model.addAttribute("pages", pages);

        return "good/category";
    }

    @GetMapping("/goods")
    public String goods(@PageableDefault(size = 1) Pageable pageable, Model model) {
        PageResponseCustom<List<GoodResponse>> goods = goodService.getGoods(pageable);
        List<Integer> pages = paginationService.getPaginationLength(pageable.getPageNumber(), goods.getTotalPages());
        model.addAttribute("goods", goods.getContent());
        model.addAttribute("page", goods.getPage());
        model.addAttribute("pages", pages);

        return "good/good";
    }

    @ResponseBody
    @GetMapping("/api/goods/images/{filename}")
    public Resource loadImage(@PathVariable String filename) throws MalformedURLException {
        return new UrlResource("file:" + fileService.getFullPath(filename));
    }

}
