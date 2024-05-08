package com.ddangme.dmadmin.service.goods;

import com.ddangme.dmadmin.dto.goods.request.GoodsEditDetailRequest;
import com.ddangme.dmadmin.dto.goods.request.GoodsEditOptionRequest;
import com.ddangme.dmadmin.dto.goods.request.GoodsEditRequest;
import com.ddangme.dmadmin.exception.DMAdminException;
import com.ddangme.dmadmin.exception.ErrorCode;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.List;

@Slf4j
@Service
public class GoodsValidateService {

    public void valid(GoodsEditRequest request) {
        validGood(request);
        validDetail(request.getGoodsDetail());
        validOption(request.getGoodsOptions());
    }

    private void validGood(GoodsEditRequest request) {
        if (request.getName() == null || request.getName().isEmpty()) {
            throw new DMAdminException(ErrorCode.FIELD_IS_NULL, "상품명을 입력해주세요.");
        }
        if (request.getCategoryId() == null) {
            throw new DMAdminException(ErrorCode.FIELD_IS_NULL, "카테고리를 선택해주세요.");
        }
        if (request.getSummary() == null || request.getSummary().isEmpty()) {
            throw new DMAdminException(ErrorCode.FIELD_IS_NULL, "상품 요약 설명을 입력해주세요.");
        }
        if (request.getSaleStatus() == null) {
            throw new DMAdminException(ErrorCode.FIELD_IS_NULL, "판매 상태를 선택해주세요.");
        }
        if (request.getPrice() == null) {
            throw new DMAdminException(ErrorCode.FIELD_IS_NULL, "상품 대표 가격을 입력해주세요.");
        }
        if (request.getGoodsOptions() == null || request.getGoodsOptions().isEmpty()) {
            throw new DMAdminException(ErrorCode.FIELD_IS_NULL, "옵션은 최소 1개가 필요합니다.");
        }
    }

    private void validDetail(GoodsEditDetailRequest request) {
        if (request.getPackagingType() == null) {
            throw new DMAdminException(ErrorCode.FIELD_IS_NULL, "포장 타입을 선택해주세요.");
        }
        if (request.getWeightVolume() == null || request.getWeightVolume().isEmpty()) {
            throw new DMAdminException(ErrorCode.FIELD_IS_NULL, "중량/용량을 입력해주세요.");
        }
        if (request.getDescription() == null || request.getDescription().isEmpty()) {
            throw new DMAdminException(ErrorCode.FIELD_IS_NULL, "상품 상세 설명을 입력해주세요.");
        }
    }

    private void validOption(List<GoodsEditOptionRequest> requests) {
        for (GoodsEditOptionRequest request : requests) {
            if (request.getName() == null || request.getName().isEmpty()) {
                throw new DMAdminException(ErrorCode.FIELD_IS_NULL, "옵션의 이름을 입력해주세요.");
            }
            if (request.getSaleStatus() == null) {
                throw new DMAdminException(ErrorCode.FIELD_IS_NULL, "옵션의 판매 상태를 선택해주세요.");
            }
            if (request.getPrice() == null) {
                throw new DMAdminException(ErrorCode.FIELD_IS_NULL, "옵션의 상품 금액을 입력해주세요.");
            }
            if (request.getAmount() == null) {
                throw new DMAdminException(ErrorCode.FIELD_IS_NULL, "옵션의 재고를 입력해주세요.");
            }
        }
    }

}
