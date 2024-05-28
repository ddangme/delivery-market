package com.ddangme.dm.repository.order;

import com.ddangme.dm.model.order.Order;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.time.LocalDateTime;
import java.util.List;

public interface OrderRepository extends JpaRepository<Order, Long> {

    @Query("SELECT o FROM Orders o WHERE o.createdAt >= :startDateTime AND o.member.id = :memberId ORDER BY o.createdAt DESC")
    List<Order> findOrdersByStartDateTimeAndMemberId(@Param("startDateTime") LocalDateTime startDateTime, @Param("memberId") Long memberId);
}
