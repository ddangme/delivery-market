package com.ddangme.dmadmin.dto.goods.request;

import com.ddangme.dmadmin.dto.goods.GoodsOptionDTO;
import com.ddangme.dmadmin.exception.DMAdminException;
import com.ddangme.dmadmin.exception.ErrorCode;
import com.ddangme.dmadmin.model.constants.SaleStatus;
import lombok.Data;

@Data
public class GoodsOptionRequest {
    private String name;
    private Long price;
    private Long discountPrice;
    private Integer discountPercent;;
    private Long amount;
    private SaleStatus saleStatus;

    public GoodsOptionDTO toDTO() {
        validate();
        return new GoodsOptionDTO(
                name,
                price,
                discountPrice,
                discountPercent,
                amount,
                saleStatus
        );
    }

    private void validate() {
        if (name.isEmpty()) {
            throw new DMAdminException(ErrorCode.FIELD_IS_NULL, "옵션의 이름을 입력해주세요.");
        }
        if (saleStatus == null) {
            throw new DMAdminException(ErrorCode.FIELD_IS_NULL, "옵션의 판매 상태를 선택해주세요.");
        }
        if (price == null) {
            throw new DMAdminException(ErrorCode.FIELD_IS_NULL, "옵션의 상품 금액을 입력해주세요.");
        }
        if (amount == null) {
            throw new DMAdminException(ErrorCode.FIELD_IS_NULL, "옵션의 재고를 입력해주세요.");
        }
    }
}
