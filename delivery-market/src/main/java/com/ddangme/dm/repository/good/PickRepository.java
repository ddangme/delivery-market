package com.ddangme.dm.repository.good;

import com.ddangme.dm.model.good.Pick;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface PickRepository extends JpaRepository<Pick, Long> {

    Optional<Pick> findByMemberIdAndGoodId(Long memberId, Long goodId);
}
