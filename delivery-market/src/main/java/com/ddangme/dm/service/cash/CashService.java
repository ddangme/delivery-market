package com.ddangme.dm.service.cash;

import com.ddangme.dm.dto.cash.CashListResponse;
import com.ddangme.dm.exception.DMException;
import com.ddangme.dm.exception.ErrorCode;
import com.ddangme.dm.model.cash.CashCharging;
import com.ddangme.dm.model.member.Member;
import com.ddangme.dm.repository.MemberRepository;
import com.ddangme.dm.repository.cash.CashRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Slf4j
@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class CashService {

    private final MemberRepository memberRepository;
    private final CashRepository cashRepository;

    public List<CashListResponse> findCashListByMember(Long memberId) {
        findMember(memberId);
        return cashRepository.findByMemberIdOrderByRequestAtDesc(memberId).stream()
                .map(CashListResponse::fromEntity)
                .toList();
    }

    @Transactional
    public void cashCharging(Long memberId, Long amount) {
        Member member = findMember(memberId);
        CashCharging cashCharging = CashCharging.create(member, amount);
        cashRepository.save(cashCharging);
    }

    private Member findMember(Long memberId) {
        return memberRepository.findById(memberId)
                .orElseThrow(() -> new DMException(ErrorCode.NOT_FOUND_ACCOUNT));
    }
}
