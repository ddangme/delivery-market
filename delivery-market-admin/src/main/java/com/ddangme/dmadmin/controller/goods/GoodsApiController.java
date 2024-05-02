package com.ddangme.dmadmin.controller.goods;

import com.ddangme.dmadmin.dto.goods.request.GoodsSaveRequest;
import com.ddangme.dmadmin.model.constants.UploadFile;
import com.ddangme.dmadmin.service.FileUploadService;
import com.ddangme.dmadmin.service.goods.GoodsService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.io.IOException;

@RestController
@Slf4j
@RequiredArgsConstructor
@RequestMapping("/api/goods")
public class GoodsApiController {

    private final GoodsService goodsService;
    private final FileUploadService fileUploadService;

    @PostMapping("/add")
    public String add( GoodsSaveRequest request) throws IOException {
        log.info("request={}", request);
        UploadFile photo = fileUploadService.storeFile(request.getPhoto());
        goodsService.save(request.toDTO(photo));

        return "/goods/goods-list";
    }
}
