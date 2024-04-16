package com.ddangme.dm.service;

import com.ddangme.dm.dto.member.MemberDTO;
import com.ddangme.dm.model.member.Member;
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

    @Transactional
    public MemberDTO saveMember(String loginId, String password, String nickname) {
        return MemberDTO.fromEntity(
                memberRepository.save(Member.builder()
                        .loginId(loginId)
                        .password(password)
                        .name(nickname)
                        .build())
        );
    }

}
