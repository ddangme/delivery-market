package com.ddangme.dmadmin.dto.good;

import com.ddangme.dmadmin.dto.admin.AdminDTO;
import com.ddangme.dmadmin.dto.category.CategoryDTO;
import com.ddangme.dmadmin.model.constants.SaleStatus;
import com.ddangme.dmadmin.model.constants.UploadFile;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.ToString;

import java.time.LocalDateTime;
import java.util.List;

@Getter
@AllArgsConstructor
@ToString
public class GoodDTO {

    private Long id;
    private CategoryDTO categoryDTO;
    private String name;
    private String summary;
    private Long price;
    private Long discountPrice;
    private Integer discountPercent;
    private SaleStatus saleStatus;
    private GoodDetailDTO goodDetailDTO;
    private List<GoodOptionDTO> goodOptionDTO;
    private UploadFile photo;
    private LocalDateTime createdAt;
    private AdminDTO createdBy;
    private LocalDateTime updatedAt;
    private AdminDTO updatedBy;
    private LocalDateTime deletedAt;
    private AdminDTO deletedBy;

}
