package com.ddangme.dmadmin.service.order;

import com.ddangme.dmadmin.dto.order.OrderResponse;
import com.ddangme.dmadmin.model.order.Order;
import com.ddangme.dmadmin.repository.order.OrderDeliveryRepository;
import com.ddangme.dmadmin.repository.order.OrderRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.data.support.PageableExecutionUtils;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Slf4j
@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class OrderManageService {

    private final OrderRepository orderRepository;

    public Page<OrderResponse> getOrderList(Pageable pageable) {
        return orderRepository.search(pageable);
    }

}
