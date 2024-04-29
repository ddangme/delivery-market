package com.ddangme.dmadmin.controller.goods;

import com.ddangme.dmadmin.dto.goods.CategoryDTO;
import com.ddangme.dmadmin.service.PaginationService;
import com.ddangme.dmadmin.service.goods.CategoryService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import java.util.List;

@Controller
@Slf4j
@RequiredArgsConstructor
public class CategoryController {

    private final CategoryService categoryService;
    private final PaginationService paginationService;

    @GetMapping("/categories")
    public String categoriesList(Model model,
                                 @PageableDefault(size = 10, sort = "createdAt", direction = Sort.Direction.DESC) Pageable pageable) {
        Page<CategoryDTO> categories = categoryService.search(pageable);
        List<Integer> pages = paginationService.getPaginationLength(pageable.getPageNumber(), categories.getTotalPages());

        model.addAttribute("categories", categories);
        model.addAttribute("pages", pages);

        return "goods/category-list";
    }

    @GetMapping("/categories/add")
    public String addCategory() {
        return "goods/category-add";
    }
}
