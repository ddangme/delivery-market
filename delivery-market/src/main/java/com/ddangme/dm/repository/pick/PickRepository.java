package com.ddangme.dm.repository.pick;

import com.ddangme.dm.model.good.Pick;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;
import java.util.Optional;

public interface PickRepository extends JpaRepository<Pick, Long> {

    Optional<Pick> findByMemberIdAndGoodId(Long memberId, Long goodId);

    @Query("SELECT COUNT(p) FROM Pick p WHERE p.member.id = :memberId")
    Long countByMemberId(Long memberId);

    List<Pick> findByMemberId(Long memberId);
}
