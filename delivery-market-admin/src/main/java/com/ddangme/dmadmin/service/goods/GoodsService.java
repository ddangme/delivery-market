package com.ddangme.dmadmin.service.goods;

import com.ddangme.dmadmin.dto.goods.GoodsDTO;
import com.ddangme.dmadmin.dto.goods.GoodsListResponse;
import com.ddangme.dmadmin.model.constants.UploadFile;
import com.ddangme.dmadmin.model.goods.Goods;
import com.ddangme.dmadmin.model.goods.GoodsDetail;
import com.ddangme.dmadmin.model.goods.GoodsOption;
import com.ddangme.dmadmin.repository.goods.GoodsDetailRepository;
import com.ddangme.dmadmin.repository.goods.GoodsOptionRepository;
import com.ddangme.dmadmin.repository.goods.GoodsRepository;
import com.ddangme.dmadmin.service.FileUploadService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;

@Slf4j
@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class GoodsService {

    private final FileUploadService fileUploadService;
    private final GoodsRepository goodsRepository;
    private final GoodsDetailRepository goodsDetailRepository;
    private final GoodsOptionRepository goodsOptionRepository;

    public Page<GoodsListResponse> search(Pageable pageable) {
        return goodsRepository.search(pageable);
    }

    @Transactional
    public void save(GoodsDTO dto, MultipartFile photo) throws IOException {
        UploadFile uploadFile = fileUploadService.getUploadFile(photo);

        Goods goods = dto.toGoodsEntity(uploadFile);
        goodsRepository.save(goods);

        GoodsDetail detail = dto.toGoodsDetailEntity(goods);
        List<GoodsOption> options = dto.toGoodsOptionsEntity(goods);

        goods.saveDetail(detail);
        goods.saveOptions(options);

        fileUploadService.transferTo(photo, uploadFile);
    }
}
