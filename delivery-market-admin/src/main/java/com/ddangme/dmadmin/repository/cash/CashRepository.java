package com.ddangme.dmadmin.repository.cash;

import com.ddangme.dmadmin.model.cash.CashCharging;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CashRepository extends JpaRepository<CashCharging, Long>, CashRepositoryCustom {
}
