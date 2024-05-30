package com.ddangme.dmadmin.service.order;

import com.ddangme.dmadmin.dto.order.OrderResponse;
import com.ddangme.dmadmin.dto.order.OrderStatusRequest;
import com.ddangme.dmadmin.exception.DMAdminException;
import com.ddangme.dmadmin.exception.ErrorCode;
import com.ddangme.dmadmin.model.constants.DeliveryStatus;
import com.ddangme.dmadmin.model.order.Order;
import com.ddangme.dmadmin.model.order.OrderDelivery;
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

    public Page<OrderResponse> getOrderList(Pageable pageable, String status, String keyword) {
        return orderRepository.search(pageable, DeliveryStatus.findDeliveryStatus(status), keyword);
    }

    @Transactional
    public void changeDeliveryStatus(OrderStatusRequest request) {
        validateCurrentLocation(request.getCurrentLocation());
        request.getOrderIds()
                .forEach(id ->
                        findOrder(id)
                                .setDeliveryStatus(request.getStatus(), request.getCurrentLocation()));
    }

    private void validateCurrentLocation(String currentLocation) {
        if (currentLocation.isBlank()) {
            throw new DMAdminException(ErrorCode.CURRENT_LOCATION_IS_BLANK);
        }
    }

    private Order findOrder(Long id) {
        return orderRepository.findById(id)
                .orElseThrow(() -> new DMAdminException(ErrorCode.NOT_EXIST_ORDER));
    }

}
