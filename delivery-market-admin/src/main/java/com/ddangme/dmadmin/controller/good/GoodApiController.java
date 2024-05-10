package com.ddangme.dmadmin.controller.good;

import com.ddangme.dmadmin.dto.admin.AdminPrincipal;
import com.ddangme.dmadmin.dto.Response;
import com.ddangme.dmadmin.dto.good.request.GoodRequest;
import com.ddangme.dmadmin.dto.good.response.GoodResponse;
import com.ddangme.dmadmin.service.FileService;
import com.ddangme.dmadmin.service.good.GoodService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.core.io.Resource;
import org.springframework.core.io.UrlResource;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.net.MalformedURLException;

@RestController
@Slf4j
@RequiredArgsConstructor
@RequestMapping("/api/goods")
public class GoodApiController {

    private final GoodService goodService;
    private final FileService fileService;

    @GetMapping
    public Response<Page<GoodResponse>> searchForMember(
            @PageableDefault(size = 10, sort = "id", direction = Sort.Direction.DESC) Pageable pageable) {

        return Response.success(goodService.searchForMember(pageable));
    }

    @PostMapping("/add")
    public Response<Void> add(GoodRequest request, @RequestParam(required=false) MultipartFile photo) throws IOException {
        log.info("request={}", request);
        goodService.save(request, photo);

        return Response.success();
    }

    @GetMapping("/images/{filename}")
    public Resource loadImage(@PathVariable String filename) throws MalformedURLException {
        return new UrlResource("file:" + fileService.getFullPath(filename));
    }

    @PostMapping("/edit")
    public Response<Void> edit(GoodRequest request, @RequestParam(required = false) MultipartFile photo,
                               @AuthenticationPrincipal AdminPrincipal principal) throws IOException {
        log.info("request={}", request);
        log.info("photo={}", photo);

        goodService.edit(request, photo, principal.toDTO());

        return Response.success();
    }
}