package com.ddangme.dm.dto.pick;

import com.ddangme.dm.model.good.Pick;
import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class PickedGoodResponse {

    private Long id;
    private String name;
    private Long price;
    private Long discountPrice;
    private Integer discountPercent;
    private String photo;

    public static PickedGoodResponse fromEntity(Pick entity) {
        return new PickedGoodResponse(
                entity.getGood().getId(),
                entity.getGood().getName(),
                entity.getGood().getPrice(),
                entity.getGood().getDiscountPrice(),
                entity.getGood().getDiscountPercent(),
                entity.getGood().getPhotoStoreFileName()
        );
    }
}
