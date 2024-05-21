package com.ddangme.dm.dto.order;


import com.ddangme.dm.model.member.Member;
import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class OrderPayResponse {

    private Long point;
    private Long cash;

    public static OrderPayResponse fromEntity(Member member) {
        return new OrderPayResponse(
                member.getPoint(),
                member.getCash()
        );
    }
}
