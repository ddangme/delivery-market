package com.ddangme.dmadmin.service.member;

import com.ddangme.dmadmin.dto.member.MemberListResponse;
import com.ddangme.dmadmin.model.member.Member;
import com.ddangme.dmadmin.repository.admin.AdminRepository;
import com.ddangme.dmadmin.repository.member.MemberRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Slf4j
@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class MemberManageService {

    private final MemberRepository memberRepository;
    private final AdminRepository adminRepository;

    public Page<MemberListResponse> findMember(Pageable pageable) {
        Page<Member> members = memberRepository.findAll(pageable);
        return members.map(MemberListResponse::fromEntity);
    }
}
