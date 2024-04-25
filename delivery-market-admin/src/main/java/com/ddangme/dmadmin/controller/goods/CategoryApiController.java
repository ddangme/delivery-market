package com.ddangme.dmadmin.controller.goods;

import com.ddangme.dmadmin.dto.goods.CategoryRequest;
import com.ddangme.dmadmin.service.goods.CategoryService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@Slf4j
@RequiredArgsConstructor
@RequestMapping("/api/categories")
public class CategoryApiController {

    private final CategoryService categoryService;

    @PostMapping("/new")
    public ResponseEntity<?> addCategory(@RequestBody CategoryRequest request) {
        log.info("request={}", request);

        categoryService.save(request.toDTO());
        return ResponseEntity.ok().build();
    }


}
