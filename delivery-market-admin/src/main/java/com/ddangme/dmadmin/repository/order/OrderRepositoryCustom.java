package com.ddangme.dmadmin.repository.order;

import com.ddangme.dmadmin.dto.order.OrderResponse;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface OrderRepositoryCustom {

    Page<OrderResponse> search(Pageable pageable);
}
