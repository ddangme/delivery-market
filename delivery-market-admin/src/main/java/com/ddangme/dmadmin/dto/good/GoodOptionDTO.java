package com.ddangme.dmadmin.dto.good;

import com.ddangme.dmadmin.dto.admin.AdminDTO;
import com.ddangme.dmadmin.model.constants.SaleStatus;
import lombok.AllArgsConstructor;
import lombok.Data;

import java.time.LocalDateTime;

@Data
@AllArgsConstructor
public class GoodOptionDTO {

    private Long id;
    private GoodDTO goodDTO;
    private String name;
    private Long price;
    private Long discountPrice;
    private Integer discountPercent;
    private Long amount;
    private SaleStatus saleStatus;
    private LocalDateTime createdAt;
    private AdminDTO createdBy;
    private LocalDateTime updatedAt;
    private AdminDTO updatedBy;
    private LocalDateTime deletedAt;
    private AdminDTO deletedBy;

    public GoodOptionDTO(String name, Long price, Long discountPrice, Integer discountPercent, Long amount, SaleStatus saleStatus) {
        this.name = name;
        this.price = price;
        this.discountPrice = discountPrice;
        this.discountPercent = discountPercent;
        this.amount = amount;
        this.saleStatus = saleStatus;
    }

//    public static GoodsOptionDTO fromEntity(GoodsOption entity) {
//        return new GoodsOptionDTO(
//            entity.getId(),
//            GoodsDTO.fromEntity(entity.getGoods()),
//            entity.getName(),
//            entity.getPrice(),
//            entity.getDiscountPrice(),
//            entity.getDiscountPercent(),
//            entity.getAmount(),
//            entity.getSaleStatus(),
//            entity.getCreatedAt(),
//            AdminDTO.fromEntity(entity.getCreatedBy()),
//            entity.getUpdatedAt(),
//            AdminDTO.fromEntity(entity.getUpdatedBy()),
//            entity.getDeletedAt(),
//            AdminDTO.fromEntity(entity.getDeletedBy())
//        );
//    }

}
