package com.ddangme.dm.exception;

import lombok.AllArgsConstructor;
import lombok.Getter;
import org.springframework.http.HttpStatus;

@Getter
@AllArgsConstructor
public enum ErrorCode {
    NOT_FOUND(HttpStatus.NOT_FOUND, null),
    INTERNAL_SERVER_ERROR(HttpStatus.INTERNAL_SERVER_ERROR, "Internal server error."),
    NOT_FOUND_MEMBER(HttpStatus.NOT_FOUND, "Login Id or Password not founded."),
    NOT_FOUND_ACCOUNT(HttpStatus.NOT_FOUND, "This Account does not exist"),
    NOT_EXIST_MAIN_ADDRESS(HttpStatus.NOT_FOUND, "기본 배송지 정보가 없습니다. 등록 후 이용해주세요."),
    NOT_FOUND_GOOD(HttpStatus.NOT_FOUND, "존재하지 않는 상품입니다."),
    NOT_FOUND_CART(HttpStatus.BAD_REQUEST, "존재하지 않는 장바구니 상품입니다."),
    NOT_CHOICE_CART(HttpStatus.BAD_REQUEST, "선택된 상품이 없습니다."),
    NOT_FOUND_OPTION(HttpStatus.NOT_FOUND, "존재하지 않는 상품의 옵션입니다."),
    NOT_CHOICE_OPTION(HttpStatus.BAD_REQUEST, "선택된 옵션이 없습니다."),
    DUPLICATED_LOGIN_ID(HttpStatus.CONFLICT, "Login ID is duplicated."),

    INVALID_VERIFICATION_CODE(HttpStatus.UNAUTHORIZED, "The auth code is not valid."),
    AUTH_CODE_EXPIRED(HttpStatus.FORBIDDEN, "Authentication timed out."),
    INVALID_EMAIL_FORMAT(HttpStatus.BAD_REQUEST, "Invalid email."),
    NOT_FOUND_ADDRESS(HttpStatus.NOT_FOUND, "This Address does not exist."),
    IS_NOT_ADDRESS_OWNER(HttpStatus.UNAUTHORIZED, "Is not address owner."),
    CANNOT_DELETE_DEFAULT_ADDRESS(HttpStatus.FORBIDDEN, "The default address cannot be deleted."),
    ADMIN_SERVER_ERROR(HttpStatus.INTERNAL_SERVER_ERROR, "admin api error"),
    MAX_PICK_EXCEEDED(HttpStatus.BAD_REQUEST, "상품 찜은 최대 100개까지 가능합니다."),
    GOODS_LOADING_ERROR(HttpStatus.INTERNAL_SERVER_ERROR, "상품 리스트 조회에 실패하였습니다."),
    NOT_FOUND_CASH(HttpStatus.NOT_FOUND, "존재하지 않는 캐시 요청입니다."),
    IS_NON_CANCEL_CASH_CHARGING(HttpStatus.BAD_REQUEST, "상태를 변경할 수 없는 캐시 요청입니다. 관리자에게 문의해 주세요."),
    IS_NULL_CASH_CHARGING_AMOUNT(HttpStatus.BAD_REQUEST, "충전 금액을 입력해주세요."),
    MAX_OVER_CASH_CHARGING_AMOUNT(HttpStatus.BAD_REQUEST, "충전 금액은 최대 1,000,000원 까지 가능합니다."),

    EXIST_NON_ORDER_OPTION(HttpStatus.BAD_REQUEST, "재고 부족으로 구매할 수 없는 상품이 존재합니다."),
    NOT_ENOUGH_CASH(HttpStatus.BAD_REQUEST, "잔액이 부족합니다. 충전 후 시도해 주세요."),

    NOT_EXIST_ORDER(HttpStatus.NOT_FOUND, "존재하지 않는 주문입니다."),
    ;


    private HttpStatus status;
    private String message;
}
