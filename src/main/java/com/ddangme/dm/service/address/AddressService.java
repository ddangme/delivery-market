package com.ddangme.dm.service.address;

import com.ddangme.dm.dto.address.AddressDTO;
import com.ddangme.dm.exception.DMException;
import com.ddangme.dm.exception.ErrorCode;
import com.ddangme.dm.model.member.Member;
import com.ddangme.dm.repository.AddressRepository;
import com.ddangme.dm.repository.MemberRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Slf4j
@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class AddressService {

    public final MemberRepository memberRepository;
    public final AddressRepository addressRepository;

    public List<AddressDTO> findAllByMemberId(Long memberId) {
        Member member = memberRepository.findById(memberId)
                .orElseThrow(() -> new DMException(ErrorCode.NOT_FOUND_ACCOUNT));
        return addressRepository.findAllByMember(member)
                .stream().map(AddressDTO::fromEntity)
                .toList();
    }
}
