package com.ddangme.dm.dto.address;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class AddressRequest {
    private Long id;
    private String road;
    private String detail;
    private Integer zipcode;
    private Boolean main;
    private String recipientName;
    private String recipientPhone;

    @Override
    public String toString() {
        return "AddressRequest{" +
                "id=" + id +
                ", road='" + road + '\'' +
                ", detail='" + detail + '\'' +
                ", zipcode=" + zipcode +
                ", main=" + main +
                ", recipientName='" + recipientName + '\'' +
                ", recipientPhone='" + recipientPhone + '\'' +
                '}';
    }
}
