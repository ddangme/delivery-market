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
    private Long quantity;
    private SaleStatus saleStatus;
    private LocalDateTime createdAt;
    private AdminDTO createdBy;
    private LocalDateTime updatedAt;
    private AdminDTO updatedBy;
    private LocalDateTime deletedAt;
    private AdminDTO deletedBy;

    public GoodOptionDTO(String name, Long price, Long discountPrice, Integer discountPercent, Long quantity, SaleStatus saleStatus) {
        this.name = name;
        this.price = price;
        this.discountPrice = discountPrice;
        this.discountPercent = discountPercent;
        this.quantity = quantity;
        this.saleStatus = saleStatus;
    }
}
