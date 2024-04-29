package com.ddangme.dmadmin.controller.goods;

import com.ddangme.dmadmin.dto.AdminPrincipal;
import com.ddangme.dmadmin.dto.Response;
import com.ddangme.dmadmin.dto.goods.CategoryDTO;
import com.ddangme.dmadmin.dto.goods.CategoryEditRequest;
import com.ddangme.dmadmin.dto.goods.CategoryRequest;
import com.ddangme.dmadmin.service.goods.CategoryService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@Slf4j
@RequiredArgsConstructor
@RequestMapping("/api/categories")
public class CategoryApiController {

    private final CategoryService categoryService;

    @PostMapping
    public Response<Void> addCategory(CategoryRequest request) {
        log.info("request={}", request);

        categoryService.save(request.toDTO());
        return Response.success();
    }

    @DeleteMapping
    public Response<Void> delCategory(@AuthenticationPrincipal AdminPrincipal principal,
                                      @RequestBody List<Long> categoryIds) {
        log.info("ids={}", categoryIds);
        categoryService.delete(categoryIds, principal.toDTO());

        return Response.success();
    }

    @PostMapping("/edit/{categoryId}")
    public Response<Void> editCategory(@AuthenticationPrincipal AdminPrincipal principal,
                                       @PathVariable Long categoryId,
                                       CategoryEditRequest categoryEditRequest) {
        log.info("categoryId={}", categoryId);
        log.info("categoryEditRequest={}", categoryEditRequest);

        categoryService.saveChild(categoryEditRequest.newChildToDTO(categoryId), categoryId);
        categoryService.edit(categoryEditRequest.toDTO(categoryId));
        categoryService.delete(categoryEditRequest.getDelCategoryIds(), principal.toDTO());

        return Response.success();
    }

}
