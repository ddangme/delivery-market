package com.ddangme.dm.dto.cart.response;

import com.ddangme.dm.dto.cart.CartDTO;
import com.ddangme.dm.dto.cart.CartListProjection;
import com.ddangme.dm.model.constants.PackagingType;
import com.ddangme.dm.model.constants.SaleStatus;
import lombok.Data;

import java.util.ArrayList;
import java.util.List;

@Data
public class CartListResponse {

    private List<CartDTO> refrigerated;
    private List<CartDTO> frozen;
    private List<CartDTO> roomTemperature;
    private List<CartDTO> stop;
    private Integer saleStatusCount;
    private Integer checkCount;

    public CartListResponse() {
        refrigerated = new ArrayList<>();
        frozen = new ArrayList<>();
        roomTemperature = new ArrayList<>();
        stop = new ArrayList<>();
        saleStatusCount = 0;
        checkCount = 0;
    }

    public void add(CartListProjection projection) {
        if (projection.isCheck() && isSale(projection)) {
            checkCount++;
        }
        if (isNotSale(projection)) {
            addStop(CartDTO.fromProjection(projection));
        } else if (projection.getPackagingType().equals(PackagingType.REFRIGERATED)) {
            saleStatusCount++;
            addRefrigerated(CartDTO.fromProjection(projection));
        } else if (projection.getPackagingType().equals(PackagingType.FROZEN)) {
            saleStatusCount++;
            addFrozen(CartDTO.fromProjection(projection));
        } else if (projection.getPackagingType().equals(PackagingType.ROOM_TEMPERATURE)) {
            saleStatusCount++;
            addRoomTemperature(CartDTO.fromProjection(projection));
        }
    }

    private void addRefrigerated(CartDTO dto) {
        refrigerated.add(dto);
    }

    private void addFrozen(CartDTO dto) {
        frozen.add(dto);
    }

    private void addRoomTemperature(CartDTO dto) {
        roomTemperature.add(dto);
    }

    private void addStop(CartDTO dto) {
        stop.add(dto);
    }

    private boolean isNotSale(CartListProjection projection) {
        if (projection.getSaleStatus().equals(SaleStatus.RESTOCKING)) {
            return true;
        }
        if (projection.getSaleStatus().equals(SaleStatus.END)) {
            return true;
        }
        if (projection.getSaleStatus().equals(SaleStatus.SOLD_OUT)) {
            return true;
        }

        return false;
    }

    private boolean isSale(CartListProjection projection) {
        if (projection.getSaleStatus().equals(SaleStatus.RESTOCKING)) {
            return false;
        }
        if (projection.getSaleStatus().equals(SaleStatus.END)) {
            return false;
        }
        if (projection.getSaleStatus().equals(SaleStatus.SOLD_OUT)) {
            return false;
        }

        return true;
    }
}
