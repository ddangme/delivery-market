package com.ddangme.dmadmin.service.goods;

import com.ddangme.dmadmin.dto.AdminDTO;
import com.ddangme.dmadmin.dto.goods.GoodsDTO;
import com.ddangme.dmadmin.dto.goods.GoodsListResponse;
import com.ddangme.dmadmin.dto.goods.request.GoodsEditDetailRequest;
import com.ddangme.dmadmin.dto.goods.request.GoodsEditOptionRequest;
import com.ddangme.dmadmin.dto.goods.request.GoodsEditRequest;
import com.ddangme.dmadmin.dto.goods.response.GoodsResponse;
import com.ddangme.dmadmin.exception.DMAdminException;
import com.ddangme.dmadmin.exception.ErrorCode;
import com.ddangme.dmadmin.model.Admin;
import com.ddangme.dmadmin.model.constants.UploadFile;
import com.ddangme.dmadmin.model.goods.Category;
import com.ddangme.dmadmin.model.goods.Goods;
import com.ddangme.dmadmin.model.goods.GoodsDetail;
import com.ddangme.dmadmin.model.goods.GoodsOption;
import com.ddangme.dmadmin.repository.AdminRepository;
import com.ddangme.dmadmin.repository.category.CategoryRepository;
import com.ddangme.dmadmin.repository.goods.GoodsRepository;
import com.ddangme.dmadmin.service.FileService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.util.*;

@Slf4j
@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class GoodsService {

    private final FileService fileService;
    private final GoodsRepository goodsRepository;
    private final CategoryRepository categoryRepository;
    private final AdminRepository adminRepository;

    public Page<GoodsListResponse> search(Pageable pageable) {
        return goodsRepository.search(pageable);
    }

    @Transactional
    public void save(GoodsDTO dto, MultipartFile uploadFile) throws IOException {
        UploadFile file = fileService.getUploadFile(uploadFile);

        Category category = categoryRepository.findById(dto.getCategoryDTO().getId())
                .orElseThrow(() -> new DMAdminException(ErrorCode.NOT_EXIST_CATEGORY));

        Goods goods = dto.toGoodsEntity(category, file);
        goodsRepository.save(goods);

        GoodsDetail detail = dto.toGoodsDetailEntity(goods);
        List<GoodsOption> options = dto.toGoodsOptionsEntity(goods);

        goods.saveDetail(detail);
        goods.saveOptions(options);

        fileService.transferTo(uploadFile, file);
    }

    private void saveValidate(GoodsDTO dto) {
    }

    public GoodsResponse findByGoodsId(Long goodsId) {
        Goods goods = goodsRepository.findById(goodsId)
                .orElseThrow(() -> new DMAdminException(ErrorCode.NOT_EXIST_GOODS));

        return GoodsResponse.fromEntity(goods);
    }

    @Transactional
    public void edit(GoodsEditRequest request, MultipartFile uploadFile, AdminDTO adminDTO) throws IOException {
        Goods good = goodsRepository.findById(request.getId())
                .orElseThrow(() -> new DMAdminException(ErrorCode.NOT_EXIST_GOODS));
        Admin admin = findAdmin(adminDTO);

        setGood(good, request);
        setDetail(good, request.getGoodsDetail());
        setOptions(good, request.getGoodsOptions(), admin);
        setPhoto(good, uploadFile);
    }

    private void setGood(Goods good, GoodsEditRequest request) {
        Category category = categoryRepository.findById(request.getCategoryId())
                .orElseThrow(() -> new DMAdminException(ErrorCode.NOT_EXIST_CATEGORY));

        good.edit(
                category,
                request.getName(),
                request.getSummary(),
                request.getPrice(),
                request.getDiscountPrice(),
                request.getDiscountPercent(),
                request.getSaleStatus()
        );
    }

    private void setDetail(Goods good, GoodsEditDetailRequest request) {
        GoodsDetail detail = good.getGoodsDetail();

        if (!detail.getId().equals(request.getId())) {
            throw new DMAdminException(ErrorCode.NOT_EXIST_GOOD_DETAIL);
        }

        detail.edit(
                request.getOrigin(),
                request.getPackagingType(),
                request.getWeightVolume(),
                request.getAllergyInfo(),
                request.getGuidelines(),
                request.getExpiryDate(),
                request.getDescription());
    }

    private void setOptions(Goods good, List<GoodsEditOptionRequest> options, Admin admin) {
        Set<GoodsOption> goodsOption = good.getGoodsOption();

        List<GoodsEditOptionRequest> newOptions = options.stream().filter(option -> option.getId() == null).toList();
        addOption(good, newOptions);





        List<Long> oldOptionRequestIds = options.stream()
                .map(GoodsEditOptionRequest::getId)
                .filter(Objects::nonNull)
                .toList();

        List<Long> oldOptionIds = goodsOption.stream()
                .map(GoodsOption::getId)
                .filter(Objects::nonNull)
                .toList();

        log.info("oldOptionRequestIds={}", oldOptionRequestIds);
        log.info("oldOptionIds={}", oldOptionIds);

        for (GoodsOption option : goodsOption) {
            if (oldOptionRequestIds.contains(option.getId())) {
                GoodsEditOptionRequest editRequest = options.stream()
                        .filter(request -> request.getId().equals(option.getId()))
                        .findFirst()
                        .orElseThrow(() -> new DMAdminException(ErrorCode.NOT_EXIST_GOOD_OPTION));
                editOption(option, editRequest);
            } else {
                deleteOption(option, admin);
            }
        }
    }

    private void editOption(GoodsOption option, GoodsEditOptionRequest request) {
        option.edit(
                request.getName(),
                request.getPrice(),
                request.getDiscountPrice(),
                request.getDiscountPercent(),
                request.getAmount(),
                request.getSaleStatus()
        );
    }

    private void addOption(Goods good, List<GoodsEditOptionRequest> newOptionRequest) {
        List<GoodsOption> newOptions = newOptionRequest.stream().map(GoodsEditOptionRequest::toEntity).toList();
        good.saveOptions(newOptions);
    }

    private void deleteOption(GoodsOption option, Admin admin) {
        option.delete(admin);
    }

    private void setPhoto(Goods good, MultipartFile uploadFile) throws IOException {
        if (uploadFile != null) {
            UploadFile deleteFile = good.getPhoto();
            fileService.delete(deleteFile);

            UploadFile file = fileService.getUploadFile(uploadFile);
            good.setPhoto(file);

            fileService.transferTo(uploadFile, file);
        }
    }


    private Admin findAdmin(AdminDTO dto) {
        return adminRepository.findById(dto.getId())
                .orElseThrow(() -> new DMAdminException(ErrorCode.ADMIN_NOT_FOUND));
    }
}
