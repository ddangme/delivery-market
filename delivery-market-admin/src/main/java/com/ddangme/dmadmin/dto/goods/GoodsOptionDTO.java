package com.ddangme.dmadmin.dto.goods;

import com.ddangme.dmadmin.dto.AdminDTO;
import com.ddangme.dmadmin.model.constants.SaleStatus;
import com.ddangme.dmadmin.model.goods.GoodsOption;
import lombok.AllArgsConstructor;
import lombok.Data;

import java.time.LocalDateTime;

@Data
@AllArgsConstructor
public class GoodsOptionDTO {

    private Long id;
    private GoodsDTO goodsDTO;
    private Long price;
    private Long salePrice;
    private Integer salePercent;
    private Long amount;
    private SaleStatus saleStatus;
    private LocalDateTime createdAt;
    private AdminDTO createdBy;
    private LocalDateTime updatedAt;
    private AdminDTO updatedBy;
    private LocalDateTime deletedAt;
    private AdminDTO deletedBy;

    public static GoodsOptionDTO fromEntity(GoodsOption entity) {
        return new GoodsOptionDTO(
            entity.getId(),
            GoodsDTO.fromEntity(entity.getGoods()),
            entity.getPrice(),
            entity.getSalePrice(),
            entity.getSalePercent(),
            entity.getAmount(),
            entity.getSaleStatus(),
            entity.getCreatedAt(),
            AdminDTO.fromEntity(entity.getCreatedBy()),
            entity.getUpdatedAt(),
            AdminDTO.fromEntity(entity.getUpdatedBy()),
            entity.getDeletedAt(),
            AdminDTO.fromEntity(entity.getDeletedBy())
        );
    }
}
