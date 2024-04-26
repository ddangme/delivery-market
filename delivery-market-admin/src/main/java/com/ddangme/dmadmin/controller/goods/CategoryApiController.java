package com.ddangme.dmadmin.controller.goods;

import com.ddangme.dmadmin.dto.Response;
import com.ddangme.dmadmin.dto.goods.CategoryRequest;
import com.ddangme.dmadmin.service.goods.CategoryService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@Slf4j
@RequiredArgsConstructor
@RequestMapping("/api/categories")
public class CategoryApiController {

    private final CategoryService categoryService;

    @PostMapping
    public Response<Void> addCategory(@RequestBody CategoryRequest request) {
        log.info("request={}", request);

        categoryService.save(request.toDTO());
        return Response.success();
    }

    @DeleteMapping
    public Response<Void> delCategory(@RequestBody List<Integer> ids) {
        log.info("ids={}", ids);
        
        return Response.success();
    }

}
