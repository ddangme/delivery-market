package com.ddangme.dmadmin.dto.order;

import com.ddangme.dmadmin.model.constants.DeliveryStatus;
import lombok.Data;

import java.util.ArrayList;
import java.util.List;

@Data
public class OrderStatusRequest {

    private List<Long> orderIds = new ArrayList<>();
    private DeliveryStatus status;
    private String currentLocation;
}
