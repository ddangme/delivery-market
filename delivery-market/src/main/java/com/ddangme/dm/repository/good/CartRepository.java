package com.ddangme.dm.repository.good;

import com.ddangme.dm.model.good.Cart;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.Optional;

public interface CartRepository extends JpaRepository<Cart, Long> {

    Optional<Cart> findByMemberIdAndOptionId(Long memberId, Long optionId);

    @Query("SELECT COUNT(c) FROM Cart c WHERE c.member.id = :memberId")
    Integer countByMemberId(Long memberId);
}
