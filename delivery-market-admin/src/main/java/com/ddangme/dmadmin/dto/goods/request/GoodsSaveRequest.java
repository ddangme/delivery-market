package com.ddangme.dmadmin.dto.goods.request;


import com.ddangme.dmadmin.dto.category.CategoryDTO;
import com.ddangme.dmadmin.dto.goods.GoodsDTO;
import com.ddangme.dmadmin.exception.DMAdminException;
import com.ddangme.dmadmin.exception.ErrorCode;
import com.ddangme.dmadmin.model.constants.SaleStatus;
import lombok.Data;
import lombok.ToString;

import java.util.List;

@ToString
@Data
public class GoodsSaveRequest {

    private String name;
    private String summary;
    private Long categoryId;
    private Long price;
    private Long discountPrice;
    private Integer discountPercent;
    private SaleStatus saleStatus;

    private GoodsDetailRequest goodsDetail;
    private List<GoodsOptionRequest> goodsOptions;

    public GoodsDTO toDTO() {
        validate();
        return new GoodsDTO(
                new CategoryDTO(categoryId),
                name,
                summary,
                price,
                discountPrice,
                discountPercent,
                saleStatus,
                goodsDetail.toDTO(),
                goodsOptions.stream().map(GoodsOptionRequest::toDTO).toList()
        );
    }

    public void validate() {
        if (name == null || name.isEmpty()) {
            throw new DMAdminException(ErrorCode.FIELD_IS_NULL, "상품명을 입력해주세요.");
        }
        if (categoryId == null) {
            throw new DMAdminException(ErrorCode.FIELD_IS_NULL, "카테고리를 선택해주세요.");
        }
        if (summary == null || summary.isEmpty()) {
            throw new DMAdminException(ErrorCode.FIELD_IS_NULL, "상품 요약 설명을 입력해주세요.");
        }
        if (saleStatus == null) {
            throw new DMAdminException(ErrorCode.FIELD_IS_NULL, "판매 상태를 선택해주세요.");
        }
        if (price == null) {
            throw new DMAdminException(ErrorCode.FIELD_IS_NULL, "상품 대표 가격을 입력해주세요.");
        }
        if (goodsOptions == null) {
            throw new DMAdminException(ErrorCode.FIELD_IS_NULL, "옵션은 최소 1개가 필요합니다.");
        }
    }
}


