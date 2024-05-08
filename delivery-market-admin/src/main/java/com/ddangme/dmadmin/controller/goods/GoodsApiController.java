package com.ddangme.dmadmin.controller.goods;

import com.ddangme.dmadmin.dto.AdminPrincipal;
import com.ddangme.dmadmin.dto.Response;
import com.ddangme.dmadmin.dto.goods.request.GoodsEditRequest;
import com.ddangme.dmadmin.dto.goods.request.GoodsSaveRequest;
import com.ddangme.dmadmin.service.FileService;
import com.ddangme.dmadmin.service.goods.GoodsService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.core.io.Resource;
import org.springframework.core.io.UrlResource;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.net.MalformedURLException;

@RestController
@Slf4j
@RequiredArgsConstructor
@RequestMapping("/api/goods")
public class GoodsApiController {

    private final GoodsService goodsService;
    private final FileService fileService;

    @PostMapping("/add")
    public Response<Void> add(GoodsSaveRequest request, @RequestParam(required=false) MultipartFile photo) throws IOException {
        log.info("request={}", request);

        goodsService.save(request.toDTO(), photo);

        return Response.success();
    }

    @GetMapping("/images/{filename}")
    public Resource loadImage(@PathVariable String filename) throws MalformedURLException {
        return new UrlResource("file:" + fileService.getFullPath(filename));
    }

    @PostMapping("/edit")
    public Response<Void> edit(GoodsEditRequest request, @RequestParam(required = false) MultipartFile photo,
                               @AuthenticationPrincipal AdminPrincipal principal) throws IOException {
        log.info("request={}", request);
        log.info("photo={}", photo);

        goodsService.edit(request, photo, principal.toDTO());

        return Response.success();
    }
}
