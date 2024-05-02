package com.ddangme.dmadmin.controller.goods;

import com.ddangme.dmadmin.dto.Response;
import com.ddangme.dmadmin.dto.goods.request.GoodsSaveRequest;
import com.ddangme.dmadmin.model.constants.UploadFile;
import com.ddangme.dmadmin.service.FileUploadService;
import com.ddangme.dmadmin.service.goods.GoodsService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;

@RestController
@Slf4j
@RequiredArgsConstructor
@RequestMapping("/api/goods")
public class GoodsApiController {

    private final GoodsService goodsService;

    @PostMapping("/add")
    public Response<Void> add(GoodsSaveRequest request, @RequestParam(required=false) MultipartFile photo) throws IOException {
        log.info("request={}", request);
        goodsService.save(request.toDTO(), photo);

        return Response.success();
    }
}
