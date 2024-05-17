package com.ddangme.dm.dto.good.response;

import com.ddangme.dm.dto.good.CartProjection;
import com.ddangme.dm.model.constants.PackagingType;
import com.ddangme.dm.model.constants.SaleStatus;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.ArrayList;
import java.util.List;

@Data
@NoArgsConstructor
public class CartListResponse {

    private List<CartProjection> refrigerated = new ArrayList<>();
    private List<CartProjection> frozen = new ArrayList<>();
    private List<CartProjection> roomTemperature = new ArrayList<>();
    private List<CartProjection> stop = new ArrayList<>();
    private Integer saleStatusCount;
    private Integer checkCount;

    public CartListResponse fromProjection(List<CartProjection> projections) {
        saleStatusCount = projections.size();
        checkCount = projections.size();
        for (CartProjection projection : projections) {
            if (projection.getCheckStatus() == false || isNotSaleStatus(projection)) {
                checkCount--;
            }
            if (isNotSaleStatus(projection)) {
                stop.add(projection);
                saleStatusCount--;
            } else if (isRefrigerated(projection)) {
                refrigerated.add(projection);
            } else if (isFrozen(projection)) {
                frozen.add(projection);
            } else {
                roomTemperature.add(projection);
            }
        }

        return this;
    }

    private boolean isNotSaleStatus(CartProjection projection) {
        if (projection.getSaleStatus().equals(SaleStatus.SOLD_OUT)) {
            return true;
        }
        if (projection.getSaleStatus().equals(SaleStatus.RESTOCKING)) {
            return true;
        }
        if (projection.getSaleStatus().equals(SaleStatus.END)) {
            return true;
        }
        return false;
    }

    private boolean isRefrigerated(CartProjection projection) {
        return projection.getPackagingType().equals(PackagingType.REFRIGERATED);
    }

    private boolean isFrozen(CartProjection projection) {
        return projection.getPackagingType().equals(PackagingType.FROZEN);
    }
}
