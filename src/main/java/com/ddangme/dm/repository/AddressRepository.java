package com.ddangme.dm.repository;

import com.ddangme.dm.model.Address;
import com.ddangme.dm.model.member.Member;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface AddressRepository extends JpaRepository<Address, Long> {

    List<Address> findAllByMember(Member member);

    Optional<Address> findByMemberAndMain(Member member, Boolean main);

}
