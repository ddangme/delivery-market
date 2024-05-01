package com.ddangme.dmadmin.dto.goods.request;


import com.ddangme.dmadmin.model.constants.SaleStatus;
import lombok.Data;
import lombok.ToString;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

@ToString
@Data
public class GoodsSaveRequest {

    private String name;
    private String summary;
    private Long categoryId;
    private Long price;
    private Long salePrice;
    private SaleStatus saleStatus;
    private MultipartFile photo;

    private GoodsDetailRequest goodsDetail;
    private List<GoodsOptionRequest> goodsOptions;
}


