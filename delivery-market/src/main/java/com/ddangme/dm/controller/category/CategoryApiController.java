package com.ddangme.dm.controller.category;

import com.ddangme.dm.dto.Response;
import com.ddangme.dm.dto.category.CategoryResponse;
import com.ddangme.dm.service.category.CategoryService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@Slf4j
@RestController
@RequiredArgsConstructor
@RequestMapping("/api/categories")
public class CategoryApiController {

    private final CategoryService categoryService;

    @GetMapping
    public Response<List<CategoryResponse>> sendCategories() {
        return Response.success(categoryService.findAll());
    }
}
