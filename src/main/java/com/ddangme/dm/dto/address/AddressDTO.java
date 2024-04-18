package com.ddangme.dm.dto.address;

import com.ddangme.dm.dto.member.MemberDTO;
import com.ddangme.dm.model.Address;
import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class AddressDTO {
    private MemberDTO member;

    private String road;
    private String detail;
    private Integer zipcode;

    private Boolean main;

    private String recipientName;
    private String recipientPhone;

    public static AddressDTO fromEntity(Address entity) {
        return new AddressDTO(
                MemberDTO.fromEntity(entity.getMember()),
                entity.getRoad(),
                entity.getDetail(),
                entity.getZipcode(),
                entity.getMain(),
                entity.getRecipientName(),
                entity.getRecipientPhone()
        );
    }

}
