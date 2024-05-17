package com.ddangme.dmadmin.controller.category;

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
    public ResponseEntity<Void> addCategory(CategoryRequest request) {
        log.info("request={}", request);

        categoryService.save(request.toDTO());
        return ResponseEntity.ok().build();
    }

    @DeleteMapping("/{categoryId}")
    public ResponseEntity<Void> delCategory(@AuthenticationPrincipal AdminPrincipal principal,
                                            @PathVariable Long categoryId) {
        log.info("id={}", categoryId);
        categoryService.delete(categoryId, principal.toDTO());

        return ResponseEntity.ok().build();
    }

    @GetMapping("/{parentId}")
    public ResponseEntity<List<CategoryIdNameResponse>> findChildCategory(@PathVariable Long parentId) {
        log.info("parentId={}", parentId);

        return ResponseEntity.ok(categoryService.findChild(parentId));
    }

    @PostMapping("/edit/{parentId}")
    public ResponseEntity<Void> editCategory(@AuthenticationPrincipal AdminPrincipal principal,
                                       @PathVariable Long parentId,
                                       CategoryEditRequest request) {
        log.info("categoryId={}", parentId);
        log.info("request={}", request);

        categoryService.delete(request.getDelCategoryIds(), principal.toDTO());
        categoryService.edit(request.toDTO(parentId));
        categoryService.saveChildCategory(request.toNewDTO(), parentId);

        return ResponseEntity.ok().build();
    }

    @GetMapping
    public ResponseEntity<List<CategoryParentChildResponse>> findAll() {
        return ResponseEntity.ok(categoryService.findAll());
    }
}
