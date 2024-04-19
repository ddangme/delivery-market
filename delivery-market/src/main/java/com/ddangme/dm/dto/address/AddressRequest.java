package com.ddangme.dm.dto.address;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
public class AddressRequest {
    private Long id;
    private String road;
    private String detail;
    private Integer zipcode;
    private Boolean main;
    private String recipientName;
    private String recipientPhone;

    public AddressRequest(Long id, String road, String detail, Integer zipcode, Boolean main, String recipientName, String recipientPhone) {
        this.id = id;
        this.road = road;
        this.detail = detail;
        this.zipcode = zipcode;
        this.main = main;
        if (main == null) {
            this.main = false;
        }
        this.recipientName = recipientName;
        this.recipientPhone = recipientPhone;
    }

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
