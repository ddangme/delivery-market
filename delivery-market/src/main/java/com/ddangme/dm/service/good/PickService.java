package com.ddangme.dm.service.good;

import com.ddangme.dm.dto.good.response.PickedGoodResponse;
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

import java.util.List;
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
        Member member = findMember(memberId);
        checkPickCount(memberId);

        Good good = findGood(goodId);

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
        findMember(memberId);
        findGood(goodId);

        Optional<Pick> findPick = pickRepository.findByMemberIdAndGoodId(memberId, goodId);

        return findPick.isPresent();
    }

    public List<PickedGoodResponse> findPickedGood(Long memberId) {
        findMember(memberId);
        return pickRepository.findByMemberId(memberId)
                .stream().map(PickedGoodResponse::fromEntity)
                .toList();
    }


    @Transactional
    public void deletePick(Long memberId, Long goodId) {
        Optional<Pick> pick = pickRepository.findByMemberIdAndGoodId(memberId, goodId);
        pick.ifPresent(pickRepository::delete);
    }

    public void checkPickCount(Long memberId) {
        if (pickRepository.countByMemberId(memberId) >= 100) {
            throw new DMException(ErrorCode.MAX_PICK_EXCEEDED);
        }
    }

    private Member findMember(Long memberId) {
        return memberRepository.findById(memberId)
                .orElseThrow(() -> new DMException(ErrorCode.NOT_FOUND_ACCOUNT));
    }


    private Good findGood(Long goodId) {
        return goodRepository.findById(goodId)
                .orElseThrow(() -> new DMException(ErrorCode.NOT_FOUND_GOOD));
    }


}
