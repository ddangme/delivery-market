package com.ddangme.dm.dto.good;

import com.ddangme.dm.model.constants.SaleStatus;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.ToString;

@ToString
@Data
@AllArgsConstructor
public class GoodResponse {

    private Long id;
    private String name;
    private String summary;

    private Long price;
    private Long discountPrice;
    private Integer discountPercent;
    private SaleStatus saleStatus;
    private String photo;

}
