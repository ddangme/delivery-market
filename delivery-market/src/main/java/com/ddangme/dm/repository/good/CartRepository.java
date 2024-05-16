package com.ddangme.dm.repository.good;

import com.ddangme.dm.model.good.Cart;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface CartRepository extends JpaRepository<Cart, Long> {

    Optional<Cart> findByMemberIdAndOptionId(Long memberId, Long optionId);
}
