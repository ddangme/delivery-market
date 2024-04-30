package com.ddangme.dmadmin.dto.goods;

import com.ddangme.dmadmin.dto.AdminDTO;
import com.ddangme.dmadmin.model.constants.SaleStatus;
import com.ddangme.dmadmin.model.constants.UploadFile;
import com.ddangme.dmadmin.model.goods.Goods;
import lombok.AllArgsConstructor;
import lombok.Getter;

import java.time.LocalDateTime;
import java.util.Set;
import java.util.stream.Collectors;

@Getter
@AllArgsConstructor
public class GoodsDTO {

    private Long id;
    private CategoryDTO categoryDTO;
    private String name;
    private String summary;
    private Long price;
    private Long salePrice;
    private Long salePercent;
    private SaleStatus saleStatus;
    private GoodsDetailDTO goodsDetailDTO;
    private Set<GoodsOptionDTO> goodsOptionDTO;
    private UploadFile photo;
    private LocalDateTime createdAt;
    private AdminDTO createdBy;
    private LocalDateTime updatedAt;
    private AdminDTO updatedBy;
    private LocalDateTime deletedAt;
    private AdminDTO deletedBy;

    public static GoodsDTO fromEntity(Goods entity) {
        return new GoodsDTO(
                entity.getId(),
                CategoryDTO.fromEntity(entity.getCategory()),
                entity.getName(),
                entity.getSummary(),
                entity.getPrice(),
                entity.getSalePrice(),
                entity.getSalePercent(),
                entity.getSaleStatus(),
                GoodsDetailDTO.fromEntity(entity.getGoodsDetail()),
                entity.getGoodsOption()
                        .stream().map(GoodsOptionDTO::fromEntity)
                        .collect(Collectors.toUnmodifiableSet()),
                entity.getPhoto(),
                entity.getCreatedAt(),
                AdminDTO.fromEntity(entity.getCreatedBy()),
                entity.getUpdatedAt(),
                AdminDTO.fromEntity(entity.getUpdatedBy()),
                entity.getDeletedAt(),
                AdminDTO.fromEntity(entity.getDeletedBy())
        );
    }

}
