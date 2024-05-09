package com.ddangme.dmadmin.repository.good;

import com.ddangme.dmadmin.model.good.Good;
import org.springframework.data.jpa.repository.JpaRepository;

public interface GoodRepository extends JpaRepository<Good, Long>, GoodRepositoryCustom {
}
