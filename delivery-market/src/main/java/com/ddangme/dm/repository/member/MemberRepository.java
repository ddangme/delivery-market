package com.ddangme.dm.repository.member;

import com.ddangme.dm.model.member.Member;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface MemberRepository extends JpaRepository<Member, Long>, MemberRepositoryCustom {

    Optional<Member> findByLoginId(String loginId);

    // TODO: 해당 값으로 조회 할 때 값이 있다면 LoginId만 불러오도록 수정하기
    Optional<Member> findByNameAndEmail(String name, String email);

    Optional<Member> findPointAndCashById(Long id);
}
