package com.ddangme.dm.repository.order;

import com.ddangme.dm.model.order.Order;
import org.springframework.data.jpa.repository.JpaRepository;

public interface OrderRepository extends JpaRepository<Order, Long> {
}
