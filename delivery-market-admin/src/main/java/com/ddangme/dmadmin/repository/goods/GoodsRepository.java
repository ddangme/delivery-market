package com.ddangme.dmadmin.repository.goods;

import com.ddangme.dmadmin.model.goods.Goods;
import org.springframework.data.jpa.repository.JpaRepository;

public interface GoodsRepository extends JpaRepository<Goods, Long>, GoodsRepositoryCustom {
}
