package com.ddangme.dm.dto.address;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class AddressResponse {
    private String road;
    private String detail;
    private Integer zipcode;

    private Boolean main;

    private String recipientName;
    private String recipientPhone;
    public static AddressResponse fromDTO(AddressDTO dto) {
        return new AddressResponse(
                dto.getRoad(),
                dto.getDetail(),
                dto.getZipcode(),
                dto.getMain(),
                dto.getRecipientName(),
                dto.getRecipientPhone()
        );
    }

}
