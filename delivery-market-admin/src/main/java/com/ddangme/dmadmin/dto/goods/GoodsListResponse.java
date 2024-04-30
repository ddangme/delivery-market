package com.ddangme.dmadmin.dto.goods;

import com.ddangme.dmadmin.model.constants.SaleStatus;
import com.querydsl.core.annotations.QueryProjection;
import lombok.Getter;
import lombok.ToString;

import java.time.LocalDateTime;

@Getter
@ToString
public class GoodsListResponse {

    private Long id;
    private String categoryName;
    private String name;
    private Long price;
    private SaleStatus saleStatus;
    private String uploadFileName;
    private String storeFileName;
    private LocalDateTime createdAt;
    private String createdBy;
    private LocalDateTime updatedAt;
    private String updatedBy;
    private LocalDateTime deletedAt;
    private String deletedBy;

    @QueryProjection
    public GoodsListResponse(Long id, String categoryName, String name, Long price, SaleStatus saleStatus, String uploadFileName, String storeFileName, LocalDateTime createdAt, String createdBy, LocalDateTime updatedAt, String updatedBy, LocalDateTime deletedAt, String deletedBy) {
        this.id = id;
        this.categoryName = categoryName;
        this.name = name;
        this.price = price;
        this.saleStatus = saleStatus;
        this.uploadFileName = uploadFileName;
        this.storeFileName = storeFileName;
        this.createdAt = createdAt;
        this.createdBy = createdBy;
        this.updatedAt = updatedAt;
        this.updatedBy = updatedBy;
        this.deletedAt = deletedAt;
        this.deletedBy = deletedBy;
    }
}
