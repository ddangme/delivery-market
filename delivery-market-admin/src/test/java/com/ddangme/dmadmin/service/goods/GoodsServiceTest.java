package com.ddangme.dmadmin.service.goods;

import com.ddangme.dmadmin.dto.goods.GoodsDTO;
import com.ddangme.dmadmin.dto.goods.request.GoodsDetailRequest;
import com.ddangme.dmadmin.dto.goods.request.GoodsOptionRequest;
import com.ddangme.dmadmin.dto.goods.request.GoodsSaveRequest;
import com.ddangme.dmadmin.exception.DMAdminException;
import com.ddangme.dmadmin.exception.ErrorCode;
import com.ddangme.dmadmin.model.constants.PackagingType;
import com.ddangme.dmadmin.model.constants.SaleStatus;
import com.ddangme.dmadmin.model.goods.Category;
import com.ddangme.dmadmin.model.goods.Goods;
import com.ddangme.dmadmin.repository.AdminRepository;
import com.ddangme.dmadmin.repository.category.CategoryRepository;
import com.ddangme.dmadmin.repository.goods.GoodsRepository;
import com.ddangme.dmadmin.service.FileUploadService;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.BDDMockito.given;
import static org.mockito.BDDMockito.then;

@DisplayName("[상품] 비즈니스 로직")
@ExtendWith(MockitoExtension.class)
class GoodsServiceTest {
    @InjectMocks
    private GoodsService goodsService;

    @Mock
    private GoodsRepository goodsRepository;

    @Mock
    private AdminRepository adminRepository;

    @Mock
    private CategoryRepository categoryRepository;

    @Mock
    private FileUploadService fileUploadService;

    @Test
    void 상품_정상_등록() throws IOException {
        GoodsSaveRequest request = createGoodsSaveRequest();

        GoodsDTO dto = request.toDTO();

        given(categoryRepository.findById(dto.getCategoryDTO().getId()))
                .willReturn(Optional.of(new Category(1L)));

        goodsService.save(dto, any(MultipartFile.class));

        then(goodsRepository).should().save(any(Goods.class));
    }

    @Test
    void 존재하지_않는_카테고리_선택_후_상품_등록_오류() {
        GoodsSaveRequest request = createGoodsSaveRequest();

        GoodsDTO dto = request.toDTO();

        given(categoryRepository.findById(dto.getCategoryDTO().getId()))
                .willReturn(Optional.empty());

        DMAdminException exception = assertThrows(DMAdminException.class, () -> {
            goodsService.save(dto, any(MultipartFile.class));
        });

        assertThat(exception.getErrorCode()).isEqualTo(ErrorCode.NOT_EXIST_CATEGORY);
    }

    @Test
    void 상품명_미입력_상태로_상품_등록_오류() {
        GoodsSaveRequest request = createGoodsSaveRequest();
        request.setName("");
        assertValidateFailure(request);

        request.setName(null);
        assertValidateFailure(request);
    }

    @Test
    void 카테고리_미선택_상태로_상품_등록_오류() {
        GoodsSaveRequest request = createGoodsSaveRequest();
        request.setCategoryId(null);

        assertValidateFailure(request);
    }

    @Test
    void 상품_요약_설명_미입력_상태로_상품_등록_오류() {
        GoodsSaveRequest request = createGoodsSaveRequest();
        request.setSummary("");
        assertValidateFailure(request);

        request.setSummary(null);
        assertValidateFailure(request);
    }

    @Test
    void 판매_상태_미선택_상태로_상품_등록_오류() {
        GoodsSaveRequest request = createGoodsSaveRequest();
        request.setSaleStatus(null);

        assertValidateFailure(request);
    }

    @Test
    void 상품_대표_이미지_미선택_상태로_상품_등록_오류() {
        DMAdminException exception = assertThrows(DMAdminException.class, () -> {
            new FileUploadService().getUploadFile(null);
        });

        assertThat(exception.getErrorCode()).isEqualTo(ErrorCode.FIELD_IS_NULL);
    }

    @Test
    void 상품_대표_가격_미입력_상태로_상품_등록_오류() {
        GoodsSaveRequest request = createGoodsSaveRequest();
        request.setSaleStatus(null);

        assertValidateFailure(request);
    }

    @Test
    void 포장_타입_미선택_상태로_상품_등록_오류() {
        GoodsSaveRequest request = createGoodsSaveRequest();
        request.getGoodsDetail().setPackagingType(null);

        assertValidateFailure(request);
    }

    @Test
    void 중량_용량_미입력_상태로_상품_등록_오류() {
        GoodsSaveRequest request = createGoodsSaveRequest();
        request.getGoodsDetail().setWeightVolume("");
        assertValidateFailure(request);

        request.getGoodsDetail().setWeightVolume(null);
        assertValidateFailure(request);
    }

    @Test
    void 상품_상세_설명_미입력_상태로_상품_등록_오류() {
        GoodsSaveRequest request = createGoodsSaveRequest();
        request.getGoodsDetail().setDescription("");
        assertValidateFailure(request);

        request.getGoodsDetail().setDescription(null);
        assertValidateFailure(request);
    }

    @Test
    void 옵션_0개_입력_상태로_상품_등록_오류() {
        GoodsSaveRequest request = createGoodsSaveRequest();
        request.setGoodsOptions(null);

        assertValidateFailure(request);
    }

    @Test
    void 옵션중_한_개_이상_이름_미입력_상태로_상품_등록_오류() {
        GoodsSaveRequest request = createGoodsSaveRequest();
        request.getGoodsOptions().get(0).setName("");
        assertValidateFailure(request);

        request.getGoodsOptions().get(0).setName(null);
        assertValidateFailure(request);
    }

    @Test
    void 옵션중_한_개_이상_판매_상태_미선택_상태로_상품_등록_오류() {
        GoodsSaveRequest request = createGoodsSaveRequest();
        request.getGoodsOptions().get(0).setSaleStatus(null);

        assertValidateFailure(request);
    }

    @Test
    void 옵션중_한_개_이상_상품_금액_미입력_상태로_상품_등록_오류() {
        GoodsSaveRequest request = createGoodsSaveRequest();
        request.getGoodsOptions().get(0).setPrice(null);

        assertValidateFailure(request);
    }

    @Test
    void 옵션중_한_개_이상_재고_미입력_상태로_상품_등록_오류() {
        GoodsSaveRequest request = createGoodsSaveRequest();
        request.getGoodsOptions().get(0).setAmount(null);

        assertValidateFailure(request);
    }

    private GoodsSaveRequest createGoodsSaveRequest() {
        GoodsSaveRequest request = new GoodsSaveRequest();
        GoodsDetailRequest detailRequest = new GoodsDetailRequest();
        GoodsOptionRequest optionRequest = new GoodsOptionRequest();

        request.setName("name");
        request.setCategoryId(1L);
        request.setPrice(10000L);
        request.setSummary("summary");
        request.setDiscountPrice(9000L);
        request.setDiscountPercent(10);
        request.setSaleStatus(SaleStatus.ON_SALE);
        request.setGoodsDetail(detailRequest);
        request.setGoodsOptions(List.of(optionRequest));

        detailRequest.setOrigin("origin");
        detailRequest.setDescription("description");
        detailRequest.setGuidelines("guidelines");
        detailRequest.setExpiryDate("expiryDate");
        detailRequest.setAllergyInfo("allergyInfo");
        detailRequest.setPackagingType(PackagingType.REFRIGERATED);
        detailRequest.setWeightVolume("weightVolume");

        optionRequest.setName("option-name");
        optionRequest.setPrice(10000L);
        optionRequest.setSaleStatus(SaleStatus.ON_SALE);
        optionRequest.setDiscountPrice(5000L);
        optionRequest.setDiscountPercent(50);
        optionRequest.setAmount(1000L);

        return request;
    }

    private void assertValidateFailure(GoodsSaveRequest request) {
        DMAdminException exception = assertThrows(DMAdminException.class, () -> {
            goodsService.save(request.toDTO(), any());
        });
        assertThat(exception.getErrorCode()).isEqualTo(ErrorCode.FIELD_IS_NULL);
    }
}