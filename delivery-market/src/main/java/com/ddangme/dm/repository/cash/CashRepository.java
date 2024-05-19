package com.ddangme.dm.repository.cash;

import com.ddangme.dm.model.cash.CashCharging;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CashRepository extends JpaRepository<CashCharging, Long> {
}
