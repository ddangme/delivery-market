package com.ddangme.dm.service.member;

import com.ddangme.dm.controller.member.SignUpRequest;
import com.ddangme.dm.dto.member.MemberDTO;
import com.ddangme.dm.model.Address;
import com.ddangme.dm.model.member.Member;
import com.ddangme.dm.repository.AddressRepository;
import com.ddangme.dm.repository.MemberRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

@Slf4j
@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class MemberService {

    private final MemberRepository memberRepository;
    private final AddressRepository addressRepository;
    private final PasswordEncoder encoder;

    public Optional<MemberDTO> searchMember(String loginId) {
        log.info("login id={}", loginId);
        log.info("find loginId={}", memberRepository.findByLoginId(loginId));
        return memberRepository.findByLoginId(loginId)
                .map(MemberDTO::fromEntity);
    }

    @Transactional
    public MemberDTO saveMember(String loginId, String password, String nickname) {
        return MemberDTO.fromEntity(
                memberRepository.save(Member.signUp(loginId, password, nickname)
                ));
    }

    @Transactional
    public void signUpMember(SignUpRequest request) {
        Member savedMember = memberRepository.save(Member.signUp(
                request.getLoginId(),
                encoder.encode(request.getPassword()),
                request.getName(),
                request.getEmail(),
                request.getPhone(),
                request.getBirthday()
        ));

        addressRepository.save(new Address(savedMember, request.getRoad(), request.getDetail(), request.getZipcode(), true));

    }

}
