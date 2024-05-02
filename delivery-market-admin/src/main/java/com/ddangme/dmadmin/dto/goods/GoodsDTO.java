package com.ddangme.dmadmin.dto.goods;

import com.ddangme.dmadmin.dto.AdminDTO;
import com.ddangme.dmadmin.dto.category.CategoryDTO;
import com.ddangme.dmadmin.model.constants.SaleStatus;
import com.ddangme.dmadmin.model.constants.UploadFile;
import com.ddangme.dmadmin.model.goods.Category;
import com.ddangme.dmadmin.model.goods.Goods;
import com.ddangme.dmadmin.model.goods.GoodsDetail;
import com.ddangme.dmadmin.model.goods.GoodsOption;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.ToString;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

@Getter
@AllArgsConstructor
@ToString
public class GoodsDTO {

    private Long id;
    private CategoryDTO categoryDTO;
    private String name;
    private String summary;
    private Long price;
    private Long discountPrice;
    private Integer discountPercent;
    private SaleStatus saleStatus;
    private GoodsDetailDTO goodsDetailDTO;
    private List<GoodsOptionDTO> goodsOptionDTO;
    private UploadFile photo;
    private LocalDateTime createdAt;
    private AdminDTO createdBy;
    private LocalDateTime updatedAt;
    private AdminDTO updatedBy;
    private LocalDateTime deletedAt;
    private AdminDTO deletedBy;

    public GoodsDTO(CategoryDTO categoryDTO, String name, String summary, Long price, Long discountPrice, Integer discountPercent, SaleStatus saleStatus, GoodsDetailDTO goodsDetailDTO, List<GoodsOptionDTO> goodsOptionDTO) {
        this.categoryDTO = categoryDTO;
        this.name = name;
        this.summary = summary;
        this.price = price;
        this.discountPrice = discountPrice;
        this.discountPercent = discountPercent;
        this.saleStatus = saleStatus;
        this.goodsDetailDTO = goodsDetailDTO;
        this.goodsOptionDTO = goodsOptionDTO;
    }

    public static GoodsDTO fromEntity(Goods entity) {
        return new GoodsDTO(
                entity.getId(),
                CategoryDTO.fromEntity(entity.getCategory()),
                entity.getName(),
                entity.getSummary(),
                entity.getPrice(),
                entity.getDiscountPrice(),
                entity.getDiscountPercent(),
                entity.getSaleStatus(),
                GoodsDetailDTO.fromEntity(entity.getGoodsDetail()),
                entity.getGoodsOption()
                        .stream().map(GoodsOptionDTO::fromEntity)
                        .toList(),
                entity.getPhoto(),
                entity.getCreatedAt(),
                AdminDTO.fromEntity(entity.getCreatedBy()),
                entity.getUpdatedAt(),
                AdminDTO.fromEntity(entity.getUpdatedBy()),
                entity.getDeletedAt(),
                AdminDTO.fromEntity(entity.getDeletedBy())
        );
    }

    public Goods toGoodsEntity(Category category, UploadFile photo) {
        return new Goods(
                id,
                category,
                name,
                summary,
                price,
                discountPrice,
                discountPercent,
                saleStatus,
                photo
        );
    }

    public GoodsDetail toGoodsDetailEntity(Goods goods) {
        return new GoodsDetail(
                goods,
                goodsDetailDTO.getOrigin(),
                goodsDetailDTO.getPackagingType(),
                goodsDetailDTO.getWeightVolume(),
                goodsDetailDTO.getAllergyInfo(),
                goodsDetailDTO.getGuidelines(),
                goodsDetailDTO.getExpiryDate(),
                goodsDetailDTO.getDescription()
        );
    }

    public List<GoodsOption> toGoodsOptionsEntity(Goods goods) {
        return goodsOptionDTO.stream()
                .map(optionDTO -> new GoodsOption(
                        goods,
                        optionDTO.getName(),
                        optionDTO.getPrice(),
                        optionDTO.getDiscountPrice(),
                        optionDTO.getDiscountPercent(),
                        optionDTO.getAmount(),
                        optionDTO.getSaleStatus()
                ))
                .collect(Collectors.toList());
    }

}
