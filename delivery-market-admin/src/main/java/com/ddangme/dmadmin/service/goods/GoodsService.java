package com.ddangme.dmadmin.service.goods;

import com.ddangme.dmadmin.dto.goods.GoodsDTO;
import com.ddangme.dmadmin.dto.goods.GoodsListResponse;
import com.ddangme.dmadmin.dto.goods.response.GoodsResponse;
import com.ddangme.dmadmin.exception.DMAdminException;
import com.ddangme.dmadmin.exception.ErrorCode;
import com.ddangme.dmadmin.model.constants.UploadFile;
import com.ddangme.dmadmin.model.goods.Category;
import com.ddangme.dmadmin.model.goods.Goods;
import com.ddangme.dmadmin.model.goods.GoodsDetail;
import com.ddangme.dmadmin.model.goods.GoodsOption;
import com.ddangme.dmadmin.repository.category.CategoryRepository;
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
    private final CategoryRepository categoryRepository;

    public Page<GoodsListResponse> search(Pageable pageable) {
        return goodsRepository.search(pageable);
    }

    @Transactional
    public void save(GoodsDTO dto, MultipartFile uploadFile) throws IOException {
        UploadFile file = fileUploadService.getUploadFile(uploadFile);

        Category category = categoryRepository.findById(dto.getCategoryDTO().getId())
                .orElseThrow(() -> new DMAdminException(ErrorCode.NOT_EXIST_CATEGORY));

        Goods goods = dto.toGoodsEntity(category, file);
        goodsRepository.save(goods);

        GoodsDetail detail = dto.toGoodsDetailEntity(goods);
        List<GoodsOption> options = dto.toGoodsOptionsEntity(goods);

        goods.saveDetail(detail);
        goods.saveOptions(options);

        fileUploadService.transferTo(uploadFile, file);
    }

    private void saveValidate(GoodsDTO dto) {
    }

    public GoodsResponse findByGoodsId(Long goodsId) {
        Goods goods = goodsRepository.findById(goodsId)
                .orElseThrow(() -> new DMAdminException(ErrorCode.NOT_EXIST_GOODS));

        return GoodsResponse.fromEntity(goods);
    }

}
