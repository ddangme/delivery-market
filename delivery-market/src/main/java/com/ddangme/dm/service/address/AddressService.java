package com.ddangme.dm.service.address;

import com.ddangme.dm.dto.address.AddressDTO;
import com.ddangme.dm.dto.address.AddressRequest;
import com.ddangme.dm.exception.DMException;
import com.ddangme.dm.exception.ErrorCode;
import com.ddangme.dm.model.Address;
import com.ddangme.dm.model.member.Member;
import com.ddangme.dm.repository.AddressRepository;
import com.ddangme.dm.repository.member.MemberRepository;
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
public class AddressService {

    public final MemberRepository memberRepository;
    public final AddressRepository addressRepository;

    public List<AddressDTO> findAllByMemberId(Long memberId) {
        Member member = findMember(memberId);
        return addressRepository.findAllByMember(member)
                .stream().map(AddressDTO::fromEntity)
                .toList();
    }

    @Transactional
    public void addAddress(AddressRequest request, Long memberId) {
        Member member = findMember(memberId);

        Address address = new Address(
                member,
                request.getRoad(),
                request.getDetail(),
                request.getZipcode(),
                request.getMain(),
                request.getRecipientName(),
                request.getRecipientPhone()
        );

        setMainAddress(member, address);

        addressRepository.save(address);
    }

    @Transactional
    public void editAddress(AddressRequest request, Long memberId) {
        Member member = findMember(memberId);

        Address address = addressRepository.findById(request.getId())
                .orElseThrow(() -> new DMException(ErrorCode.NOT_FOUND_ADDRESS));

        if (address.isNotOwner(memberId)) {
            throw new DMException(ErrorCode.IS_NOT_ADDRESS_OWNER);
        }

        log.info(address.toString());
        log.info(request.toString());
        if (!address.getMain() && request.getMain()) {
            Optional<Address> findAddress = addressRepository.findByMemberAndMain(member, true);

            findAddress.ifPresent(addr -> {
                addr.setMainToFalse();
                addressRepository.save(addr);
            });
        }

        address.editAddress(
                request.getRoad(),
                request.getDetail(),
                request.getZipcode(),
                request.getMain(),
                request.getRecipientName(),
                request.getRecipientPhone()
        );
        addressRepository.save(address);

    }


    private void setMainAddress(Member member, Address address) {
        Optional<Address> findAddress = addressRepository.findByMemberAndMain(member, true);

        if (findAddress.isEmpty()) {
            address.setMainToTrue();
            return;
        }

        if (address.getMain()) {
            findAddress.ifPresent(addr -> {
                addr.setMainToFalse();
                addressRepository.save(addr);
            });
        }
    }

    @Transactional
    public void deleteAddress(Long addressId, Long memberId) {
        Address address = addressRepository.findById(addressId)
                .orElseThrow(() -> new DMException(ErrorCode.NOT_FOUND_ADDRESS));

        if (address.isNotOwner(memberId)) {
            throw new DMException(ErrorCode.IS_NOT_ADDRESS_OWNER);
        }

        if (address.getMain()) {
            throw new DMException(ErrorCode.CANNOT_DELETE_DEFAULT_ADDRESS);
        }

        addressRepository.delete(address);
    }

    private Member findMember(Long memberId) {
        return memberRepository.findById(memberId)
                .orElseThrow(() -> new DMException(ErrorCode.NOT_FOUND_ACCOUNT));
    }
}
