package com.ddangme.dm.controller.good;

import com.ddangme.dm.dto.category.CategoryResponse;
import com.ddangme.dm.service.category.CategoryService;
import com.ddangme.dm.service.good.GoodService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

@Slf4j
@Controller
@RequiredArgsConstructor
public class GoodController {

    private final GoodService goodService;
    private final CategoryService categoryService;

    @GetMapping("/categories/{categoryId}")
    public String goodInCategories(@PathVariable Long categoryId) {
        CategoryResponse categories =  categoryService.getGoodInCategory(categoryId);
        log.info("categories={}", categories);

        return "good/good";
    }
}
