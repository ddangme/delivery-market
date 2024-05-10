package com.ddangme.dm.controller.good;

import com.ddangme.dm.dto.good.GoodResponse;
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
    private final CategoryService categoryService;
    private final PaginationService paginationService;

//    @GetMapping("/categories/{categoryId}")
//    public String goodInCategories(@PathVariable Long categoryId) {
////        CategoryResponse categories =  categoryService.getGoodInCategory(categoryId);
////        log.info("categories={}", categories);
//        goodService.getGoods
//                ();
//
//        return "good/good";
//    }

    @GetMapping("/goods")
    public String goods(@PageableDefault(size = 1) Pageable pageable, Model model) {
        Page<GoodResponse> goods = goodService.getGoods(pageable);
        List<Integer> pages = paginationService.getPaginationLength(pageable.getPageNumber(), getTotalPage(pageable, goods.getTotalElements()));

        model.addAttribute("pageable", pageable);
        model.addAttribute("goods", goods);
        model.addAttribute("pages", pages);

        return "good/good";
    }

    @ResponseBody
    @GetMapping("/api/goods/images/{filename}")
    public Resource loadImage(@PathVariable String filename) throws MalformedURLException {
        return new UrlResource("file:" + fileService.getFullPath(filename));
    }

    private static int getTotalPage(Pageable pageable, Long totalElements) {
        return (int) Math.ceil((double) totalElements / (double) pageable.getPageSize());
    }
}
