package com.ddangme.dm.service.good;

import com.ddangme.dm.dto.member.MemberDTO;
import com.ddangme.dm.exception.DMException;
import com.ddangme.dm.exception.ErrorCode;
import com.ddangme.dm.model.good.Good;
import com.ddangme.dm.model.good.Pick;
import com.ddangme.dm.model.member.Member;
import com.ddangme.dm.repository.MemberRepository;
import com.ddangme.dm.repository.good.GoodRepository;
import com.ddangme.dm.repository.good.PickRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

@Slf4j
@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class PickService {
    private final GoodRepository goodRepository;
    private final MemberRepository memberRepository;
    private final PickRepository pickRepository;

    @Transactional
    public boolean pick(Long goodId, Long memberId) {
        Member member = memberRepository.findById(memberId)
                .orElseThrow(() -> new DMException(ErrorCode.NOT_FOUND_ACCOUNT));
        Good good = goodRepository.findById(goodId)
                .orElseThrow(() -> new DMException(ErrorCode.NOT_FOUND_GOOD));

        Optional<Pick> pick = pickRepository.findByMemberIdAndGoodId(member.getId(), goodId);

        if (pick.isPresent()) {
            pickRepository.delete(pick.get());
            return false;
        } else {
            pickRepository.save(Pick.create(member, good));
            return true;
        }
    }

    public boolean findPick(Long goodId, Long memberId) {
        memberRepository.findById(memberId)
                .orElseThrow(() -> new DMException(ErrorCode.NOT_FOUND_ACCOUNT));
        goodRepository.findById(goodId)
                .orElseThrow(() -> new DMException(ErrorCode.NOT_FOUND_GOOD));

        Optional<Pick> findPick = pickRepository.findByMemberIdAndGoodId(memberId, goodId);

        return findPick.isPresent();
    }

}
