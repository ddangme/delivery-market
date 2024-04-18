package com.ddangme.dm.repository;

import com.ddangme.dm.model.Address;
import com.ddangme.dm.model.member.Member;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface AddressRepository extends JpaRepository<Address, Long> {

    List<Address> findAllByMember(Member member);

}
