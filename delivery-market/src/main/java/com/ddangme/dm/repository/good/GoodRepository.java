package com.ddangme.dm.repository.good;

import com.ddangme.dm.model.good.Good;
import org.springframework.data.jpa.repository.JpaRepository;

public interface GoodRepository extends JpaRepository<Good, Long>, GoodRepositoryCustom {
}
