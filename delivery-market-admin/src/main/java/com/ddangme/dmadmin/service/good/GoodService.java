package com.ddangme.dmadmin.service.good;

import com.ddangme.dmadmin.dto.admin.AdminDTO;
import com.ddangme.dmadmin.dto.good.request.GoodDetailRequest;
import com.ddangme.dmadmin.dto.good.request.GoodOptionRequest;
import com.ddangme.dmadmin.dto.good.request.GoodRequest;
import com.ddangme.dmadmin.dto.good.response.GoodListResponse;
import com.ddangme.dmadmin.dto.good.response.GoodResponse;
import com.ddangme.dmadmin.dto.good.response.GoodSaleResponse;
import com.ddangme.dmadmin.exception.DMAdminException;
import com.ddangme.dmadmin.exception.ErrorCode;
import com.ddangme.dmadmin.model.admin.Admin;
import com.ddangme.dmadmin.model.constants.UploadFile;
import com.ddangme.dmadmin.model.good.Category;
import com.ddangme.dmadmin.model.good.Good;
import com.ddangme.dmadmin.model.good.GoodDetail;
import com.ddangme.dmadmin.model.good.GoodOption;
import com.ddangme.dmadmin.repository.admin.AdminRepository;
import com.ddangme.dmadmin.repository.category.CategoryRepository;
import com.ddangme.dmadmin.repository.good.GoodRepository;
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
public class GoodService {

    private final FileService fileService;
    private final GoodRepository goodRepository;
    private final CategoryRepository categoryRepository;
    private final AdminRepository adminRepository;
    private final GoodValidateService validateService;

    public Page<GoodListResponse> search(Pageable pageable) {
        return goodRepository.search(pageable);
    }

    public Page<GoodResponse> searchForMember(Pageable pageable) {
        return goodRepository.searchForMember(pageable);
    }

    public Page<GoodSaleResponse> searchGoodsInCategoryId(Pageable pageable, Long categoryId) {
        return goodRepository.searchGoodsInCategoryId(pageable, categoryId);
    }


    @Transactional
    public void save(GoodRequest request, MultipartFile uploadFile) throws IOException {
        UploadFile file = fileService.getUploadFile(uploadFile);
        validateService.valid(request);

        Category category = categoryRepository.findById(request.getCategoryId())
                .orElseThrow(() -> new DMAdminException(ErrorCode.NOT_EXIST_CATEGORY));

        Good good = request.toEntity(category, file);

        goodRepository.save(good);
        fileService.transferTo(uploadFile, file);
    }

    public GoodResponse findByGoodId(Long goodId) {
        Good good = goodRepository.findById(goodId)
                .orElseThrow(() -> new DMAdminException(ErrorCode.NOT_EXIST_GOOD));

        return GoodResponse.fromEntity(good);
    }

    @Transactional
    public void edit(GoodRequest request, MultipartFile uploadFile, AdminDTO adminDTO) throws IOException {
        validateService.validIds(request);
        Good good = goodRepository.findById(request.getId())
                .orElseThrow(() -> new DMAdminException(ErrorCode.NOT_EXIST_GOOD));
        Admin admin = findAdmin(adminDTO);

        validateService.valid(request);

        setGood(good, request);
        setDetail(good, request.getGoodsDetail());
        setOptions(good, request.getGoodsOptions(), admin);
        setPhoto(good, uploadFile);
    }

    private void setGood(Good good, GoodRequest request) {
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

    private void setDetail(Good good, GoodDetailRequest request) {
        GoodDetail detail = good.getGoodDetail();

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

    private void setOptions(Good good, List<GoodOptionRequest> options, Admin admin) {
        Set<GoodOption> goodOption = good.getGoodOption();

        List<GoodOptionRequest> newOptions = options.stream()
                .filter(option -> option.getId() == null)
                .toList();
        addOption(good, newOptions);

        List<Long> oldOptionRequestIds = options.stream()
                .map(GoodOptionRequest::getId)
                .filter(Objects::nonNull)
                .toList();

        for (GoodOption option : goodOption) {
            if (oldOptionRequestIds.contains(option.getId())) {
                GoodOptionRequest editRequest = options.stream()
                        .filter(request -> request.getId().equals(option.getId()))
                        .findFirst()
                        .orElseThrow(() -> new DMAdminException(ErrorCode.NOT_EXIST_GOOD_OPTION));
                editOption(option, editRequest);
            } else {
                deleteOption(option, admin);
            }
        }
    }

    private void editOption(GoodOption option, GoodOptionRequest request) {
        option.edit(
                request.getName(),
                request.getPrice(),
                request.getDiscountPrice(),
                request.getDiscountPercent(),
                request.getAmount(),
                request.getSaleStatus()
        );
    }

    private void addOption(Good good, List<GoodOptionRequest> newOptionRequest) {
        List<GoodOption> newOptions = newOptionRequest.stream().map(option -> option.toEntity(good)).toList();
        good.saveOptions(newOptions);
    }

    private void deleteOption(GoodOption option, Admin admin) {
        option.delete(admin);
    }

    private void setPhoto(Good good, MultipartFile uploadFile) throws IOException {
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
