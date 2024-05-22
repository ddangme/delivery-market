package com.ddangme.dm.model.good;

import com.ddangme.dm.exception.DMException;
import com.ddangme.dm.exception.ErrorCode;
import com.ddangme.dm.model.constants.SaleStatus;
import com.ddangme.dm.model.member.Member;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.ToString;

import javax.persistence.*;

@Getter
@Entity
@ToString
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Cart {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "member_id")
    @ToString.Exclude
    private Member member;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "option_id")
    @ToString.Exclude
    private GoodOption option;

    private Integer quantity;

    private Boolean status;

    public void changeCount(Integer newCount) {
        this.quantity = newCount;
    }

    public void changeCheckStatus(Boolean status) {
        this.status = status;
    }

    public Cart(Member member, GoodOption option, Integer quantity) {
        checkQuantity(option, quantity);
        this.member = member;
        this.option = option;
        this.quantity = quantity;
        this.status = true;
    }


    private void checkQuantity(GoodOption option, Integer buyQuantity) {
        if (buyQuantity > option.getQuantity()) {
            throw new DMException(ErrorCode.NOT_FOUND, option.getName() + " 상품의 남은 수량은 " + option.getQuantity() + "개 입니다.");
        }
        if (option.getQuantity() == 0 || option.getSaleStatus().equals(SaleStatus.SOLD_OUT)) {
            throw new DMException(ErrorCode.NOT_FOUND, option.getName() + " 상품은 품절되었습니다.");
        }
    }

    public void addQuantity(Integer buyQuantity) {
        checkQuantity(option, quantity + buyQuantity);
        this.quantity += buyQuantity;
    }
}
