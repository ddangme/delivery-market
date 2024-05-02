package com.ddangme.dmadmin.dto.goods.request;

import com.ddangme.dmadmin.dto.goods.GoodsDetailDTO;
import com.ddangme.dmadmin.exception.DMAdminException;
import com.ddangme.dmadmin.exception.ErrorCode;
import com.ddangme.dmadmin.model.constants.PackagingType;
import lombok.Data;

@Data
public class GoodsDetailRequest {
    private String origin;
    private PackagingType packagingType;
    private String weightVolume;
    private String allergyInfo;
    private String guidelines;
    private String expiryDate;
    private String description;

    public GoodsDetailDTO toDTO() {
        validate();
        return new GoodsDetailDTO(
                origin,
                packagingType,
                weightVolume,
                allergyInfo,
                guidelines,
                expiryDate,
                description
        );
    }

    private void validate() {
        if (packagingType == null) {
            throw new DMAdminException(ErrorCode.FIELD_IS_NULL, "포장 타입을 선택해주세요.");
        }
        if (weightVolume.isEmpty()) {
            throw new DMAdminException(ErrorCode.FIELD_IS_NULL, "중량/용량을 입력해주세요.");
        }
        if (description.isEmpty()) {
            throw new DMAdminException(ErrorCode.FIELD_IS_NULL, "상품 상세 설명을 입력해주세요.");
        }
    }
}
