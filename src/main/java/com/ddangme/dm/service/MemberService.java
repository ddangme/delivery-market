package com.ddangme.dm.service;

import com.ddangme.dm.dto.MemberDTO;
import com.ddangme.dm.repository.MemberRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class MemberService {

    private final MemberRepository memberRepository;
    private final PasswordEncoder encoder;

    public Optional<MemberDTO> searchMember(String loginId) {
        return memberRepository.findByLoginId(loginId)
                .map(MemberDTO::fromEntity);
    }

}
