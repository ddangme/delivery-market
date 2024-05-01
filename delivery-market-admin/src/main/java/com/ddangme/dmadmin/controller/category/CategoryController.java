package com.ddangme.dmadmin.controller.category;

import com.ddangme.dmadmin.dto.category.CategoryDTO;
import com.ddangme.dmadmin.dto.category.CategoryListResponse;
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
import org.springframework.web.bind.annotation.PathVariable;

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
        Page<CategoryListResponse> categories = categoryService.search(pageable);
        for (CategoryListResponse category : categories) {
            log.info(category.getChildCategories().toString());
        }
        List<Integer> pages = paginationService.getPaginationLength(pageable.getPageNumber(), categories.getTotalPages());

        model.addAttribute("categories", categories);
        model.addAttribute("pages", pages);

        return "category/category-list";
    }

    @GetMapping("/categories/add")
    public String addCategory() {
        return "category/category-add";
    }

    @GetMapping("/categories/{categoryId}")
    public String editCategory(@PathVariable Long categoryId, Model model) {
        CategoryDTO category = categoryService.getParentCategory(categoryId);

        model.addAttribute("category", category);
        model.addAttribute("childCategories", category.getChildCategories());
        return "category/category-edit";
    }
}