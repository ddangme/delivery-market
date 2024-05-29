package com.ddangme.dmadmin.repository.order;

import com.ddangme.dmadmin.dto.order.OrderResponse;
import com.ddangme.dmadmin.model.constants.DeliveryStatus;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface OrderRepositoryCustom {

    Page<OrderResponse> search(Pageable pageable, DeliveryStatus status, String keyword);
}
