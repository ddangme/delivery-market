package com.ddangme.dmadmin.controller.order;

import com.ddangme.dmadmin.dto.order.OrderResponse;
import com.ddangme.dmadmin.service.order.OrderManageService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@Slf4j
@RestController
@RequiredArgsConstructor
@RequestMapping("/api/orders")
public class OrderManageApiController {

    private final OrderManageService orderService;

    @GetMapping
    public ResponseEntity<Page<OrderResponse>> getOrders(@PageableDefault(size = 5) Pageable pageable, String status, String keyword) {
        Page<OrderResponse> orderList = orderService.getOrderList(pageable, status, keyword);
        return ResponseEntity.ok(orderList);
    }
}
