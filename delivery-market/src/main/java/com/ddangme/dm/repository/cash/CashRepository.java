package com.ddangme.dm.repository.cash;

import com.ddangme.dm.model.cash.CashCharging;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface CashRepository extends JpaRepository<CashCharging, Long> {

    List<CashCharging> findByMemberIdOrderByRequestAtDesc(Long memberId);
}
