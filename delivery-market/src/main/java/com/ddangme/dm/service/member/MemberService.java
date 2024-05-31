package com.ddangme.dm.service.member;

import com.ddangme.dm.dto.member.MemberDTO;
import com.ddangme.dm.dto.member.MemberFindRequest;
import com.ddangme.dm.dto.member.ModifyMemberRequest;
import com.ddangme.dm.dto.member.SignUpRequest;
import com.ddangme.dm.dto.order.response.OrderAddressResponse;
import com.ddangme.dm.dto.order.response.OrderPayResponse;
import com.ddangme.dm.dto.order.response.OrderResponse;
import com.ddangme.dm.exception.DMException;
import com.ddangme.dm.exception.ErrorCode;
import com.ddangme.dm.model.Address;
import com.ddangme.dm.model.member.Member;
import com.ddangme.dm.repository.AddressRepository;
import com.ddangme.dm.repository.member.MemberRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Slf4j
@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class MemberService {

    private final MemberRepository memberRepository;
    private final AddressRepository addressRepository;
    private final PasswordEncoder encoder;

    public Optional<MemberDTO> findByLoginId(String loginId) {
        return memberRepository.findByLoginId(loginId)
                .map(MemberDTO::fromEntity);
    }

    @Transactional
    public MemberDTO signUp(String loginId, String password, String nickname) {
        return MemberDTO.fromEntity(
                memberRepository.save(Member.signUp(loginId, password, nickname)
                ));
    }

    @Transactional
    public void signUp(SignUpRequest request) {
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

    public String findLoginId(MemberFindRequest request) {
        return memberRepository.findByNameAndEmail(request.getName(), request.getEmail())
                .map(Member::getLoginId)
                .orElseThrow(() -> new DMException(ErrorCode.NOT_FOUND_ACCOUNT));
    }

    // TODO: 비밀번호 찾기 시 회원 확인 쿼리문이 2번 실행되는 것 1번 실행되도록 수정 필요
    @Transactional
    public void setPassword(MemberFindRequest request, String newPassword) {
        Member member = memberRepository.findByNameAndEmail(request.getName(), request.getEmail())
                .orElseThrow(() -> new DMException(ErrorCode.NOT_FOUND_ACCOUNT));

        member.setPassword(encoder.encode(newPassword));
        memberRepository.save(member);
    }

    @Transactional
    public void modifyMember(ModifyMemberRequest request, Long memberId) {
        Member member = findMember(memberId);

        member.modify(encoder.encode(request.getNewPassword()),
                request.getPhone(),
                request.getBirthday());

        memberRepository.save(member);
    }

    private Member findMember(Long memberId) {
        return memberRepository.findById(memberId)
                .orElseThrow(() -> new DMException(ErrorCode.NOT_FOUND_ACCOUNT));
    }

    public List<OrderAddressResponse> findAddressListByMemberId(Long memberId) {
        Member member = findMember(memberId);
        return member.getAddresses()
                .stream().map(OrderAddressResponse::new)
                .toList();
    }

    public OrderResponse findOrderInfo(Long memberId) {
        Member member = findMember(memberId);
        Address address = member.getMainAddress();
        OrderPayResponse pay = OrderPayResponse.fromEntity(member);

        return new OrderResponse(address, pay);
    }
}
