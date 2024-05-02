package com.ddangme.dmadmin.dto.goods.request;


import com.ddangme.dmadmin.dto.category.CategoryDTO;
import com.ddangme.dmadmin.dto.goods.GoodsDTO;
import com.ddangme.dmadmin.exception.DMAdminException;
import com.ddangme.dmadmin.exception.ErrorCode;
import com.ddangme.dmadmin.model.constants.SaleStatus;
import com.ddangme.dmadmin.model.constants.UploadFile;
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

    public GoodsDTO toDTO(UploadFile photo) {
        return new GoodsDTO(
                new CategoryDTO(categoryId),
                name,
                summary,
                price,
                discountPrice,
                discountPercent,
                saleStatus,
                goodsDetail.toDTO(),
                goodsOptions.stream().map(GoodsOptionRequest::toDTO).toList(),
                photo
        );
    }
}


