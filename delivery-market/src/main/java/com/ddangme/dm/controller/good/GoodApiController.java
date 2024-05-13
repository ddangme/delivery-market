package com.ddangme.dm.controller.good;

import com.ddangme.dm.dto.good.GoodResponse;
import com.ddangme.dm.dto.good.GoodSaleDetailResponse;
import com.ddangme.dm.dto.member.MemberPrincipal;
import com.ddangme.dm.service.FileService;
import com.ddangme.dm.service.good.GoodApiService;
import com.ddangme.dm.service.good.GoodService;
import com.ddangme.dm.service.good.PickService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.core.io.Resource;
import org.springframework.core.io.UrlResource;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.util.ResourceUtils;
import org.springframework.web.bind.annotation.*;

import java.io.File;
import java.io.IOException;
import java.net.MalformedURLException;
import java.nio.file.Files;
import java.util.Base64;

@Slf4j
@RestController
@RequiredArgsConstructor
@RequestMapping("/api/goods")
public class GoodApiController {

    private final GoodApiService goodApiService;
    private final GoodService goodService;
    private final PickService pickService;
    private final FileService fileService;

    @GetMapping
    public ResponseEntity<Page<GoodResponse>> findSaleStatusGoods(@PageableDefault(size = 1) Pageable pageable) {
        log.info("controller - /api/goods");
        return ResponseEntity.ok().body(goodService.findSaleStatusGoods(pageable));
    }

    @GetMapping("/{goodId}")
    public ResponseEntity<GoodSaleDetailResponse> goodsDetailData(@PathVariable Long goodId) {
        return ResponseEntity.ok(goodApiService.findGoodDetail(goodId));
    }

    @GetMapping("/images/{filename}")
    public Resource loadImage(@PathVariable String filename) throws MalformedURLException {
        log.info("filename={}", filename);
        return new UrlResource("file:" + fileService.getFullPath(filename));
    }

    @GetMapping("/images/entity/{filename}")
    public ResponseEntity<byte[]> getImage(@PathVariable String filename) throws IOException {
        File file = ResourceUtils.getFile(fileService.getFullPath(filename));
        byte[] imageBytes = Files.readAllBytes(file.toPath());
        byte[] base64ImageBytes = Base64.getEncoder().encode(imageBytes);
        return ResponseEntity.ok().body(base64ImageBytes);
    }

    @PostMapping("/pick/{goodId}")
    public ResponseEntity<Boolean> pick(@PathVariable Long goodId,
                                     @AuthenticationPrincipal MemberPrincipal principal) {
        log.info("goodId={}", goodId);
        return ResponseEntity.ok().body(pickService.pick(goodId, principal.getId()));
    }

    @PostMapping("/find/pick/{goodId}")
    public ResponseEntity<Boolean> findPick(@PathVariable Long goodId,
                                     @AuthenticationPrincipal MemberPrincipal principal) {
        log.info("goodId={}", goodId);
        return ResponseEntity.ok().body(pickService.findPick(goodId, principal.getId()));
    }



}
