package com.ddangme.dmadmin.service.goods;

import com.ddangme.dmadmin.dto.AdminDTO;
import com.ddangme.dmadmin.dto.goods.GoodsListResponse;
import com.ddangme.dmadmin.dto.goods.request.GoodsDetailRequest;
import com.ddangme.dmadmin.dto.goods.request.GoodsOptionRequest;
import com.ddangme.dmadmin.dto.goods.request.GoodsRequest;
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

import java.io.IOException;
import java.util.List;
import java.util.Objects;
import java.util.Set;

@Slf4j
@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class GoodsService {

    private final FileService fileService;
    private final GoodsRepository goodsRepository;
    private final CategoryRepository categoryRepository;
    private final AdminRepository adminRepository;
    private final GoodsValidateService validateService;

    public Page<GoodsListResponse> search(Pageable pageable) {
        return goodsRepository.search(pageable);
    }

    @Transactional
    public void save(GoodsRequest request, MultipartFile uploadFile) throws IOException {
        UploadFile file = fileService.getUploadFile(uploadFile);
        validateService.valid(request);

        Category category = categoryRepository.findById(request.getCategoryId())
                .orElseThrow(() -> new DMAdminException(ErrorCode.NOT_EXIST_CATEGORY));

        Goods good = request.toEntity(category, file);

        goodsRepository.save(good);
        fileService.transferTo(uploadFile, file);
    }

    public GoodsResponse findByGoodsId(Long goodsId) {
        Goods goods = goodsRepository.findById(goodsId)
                .orElseThrow(() -> new DMAdminException(ErrorCode.NOT_EXIST_GOODS));

        return GoodsResponse.fromEntity(goods);
    }

    @Transactional
    public void edit(GoodsRequest request, MultipartFile uploadFile, AdminDTO adminDTO) throws IOException {
        Goods good = goodsRepository.findById(request.getId())
                .orElseThrow(() -> new DMAdminException(ErrorCode.NOT_EXIST_GOODS));
        Admin admin = findAdmin(adminDTO);

        validateService.valid(request);

        setGood(good, request);
        setDetail(good, request.getGoodsDetail());
        setOptions(good, request.getGoodsOptions(), admin);
        setPhoto(good, uploadFile);
    }

    private void setGood(Goods good, GoodsRequest request) {
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

    private void setDetail(Goods good, GoodsDetailRequest request) {
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

    private void setOptions(Goods good, List<GoodsOptionRequest> options, Admin admin) {
        Set<GoodsOption> goodsOption = good.getGoodsOption();

        List<GoodsOptionRequest> newOptions = options.stream().filter(option -> option.getId() == null).toList();
        addOption(good, newOptions);





        List<Long> oldOptionRequestIds = options.stream()
                .map(GoodsOptionRequest::getId)
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
                GoodsOptionRequest editRequest = options.stream()
                        .filter(request -> request.getId().equals(option.getId()))
                        .findFirst()
                        .orElseThrow(() -> new DMAdminException(ErrorCode.NOT_EXIST_GOOD_OPTION));
                editOption(option, editRequest);
            } else {
                deleteOption(option, admin);
            }
        }
    }

    private void editOption(GoodsOption option, GoodsOptionRequest request) {
        option.edit(
                request.getName(),
                request.getPrice(),
                request.getDiscountPrice(),
                request.getDiscountPercent(),
                request.getAmount(),
                request.getSaleStatus()
        );
    }

    private void addOption(Goods good, List<GoodsOptionRequest> newOptionRequest) {
        List<GoodsOption> newOptions = newOptionRequest.stream().map(option -> option.toEntity(good)).toList();
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
