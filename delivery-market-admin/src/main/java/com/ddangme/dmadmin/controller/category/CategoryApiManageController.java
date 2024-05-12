package com.ddangme.dmadmin.controller.category;

import com.ddangme.dmadmin.dto.Response;
import com.ddangme.dmadmin.dto.admin.AdminPrincipal;
import com.ddangme.dmadmin.dto.category.CategoryEditRequest;
import com.ddangme.dmadmin.dto.category.CategoryIdNameResponse;
import com.ddangme.dmadmin.dto.category.CategoryParentChildResponse;
import com.ddangme.dmadmin.dto.category.CategoryRequest;
import com.ddangme.dmadmin.service.category.CategoryService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@Slf4j
@RequiredArgsConstructor
@RequestMapping("/api/categories")
public class CategoryApiManageController {
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

    @PostMapping("/edit/{parentId}")
    public Response<Void> editCategory(@AuthenticationPrincipal AdminPrincipal principal,
                                       @PathVariable Long parentId,
                                       CategoryEditRequest request) {
        log.info("categoryId={}", parentId);
        log.info("request={}", request);

        categoryService.delete(request.getDelCategoryIds(), principal.toDTO());
        categoryService.edit(request.toDTO(parentId));
        categoryService.saveChildCategory(request.toNewDTO(), parentId);

        return Response.success();
    }

    @GetMapping("/{parentId}")
    public Response<List<CategoryIdNameResponse>> findChildCategory(@PathVariable Long parentId) {
        log.info("parentId={}", parentId);

        return Response.success(categoryService.findChild(parentId));
    }

    @GetMapping
    public ResponseEntity<List<CategoryParentChildResponse>> findAll() {
        return ResponseEntity.ok(categoryService.findAll());
    }
}
