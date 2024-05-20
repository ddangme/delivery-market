package com.ddangme.dmadmin.exception;

import lombok.AllArgsConstructor;
import lombok.Getter;
import org.springframework.http.HttpStatus;

@Getter
@AllArgsConstructor
public enum ErrorCode {
    INTERNAL_SERVER_ERROR(HttpStatus.INTERNAL_SERVER_ERROR, "Internal server error."),
    DUPLICATE_CATEGORY_NAME(HttpStatus.CONFLICT, "중복된 카테고리 이름이 존재합니다."),
    DUPLICATE_PARENT_CATEGORY_NAME(HttpStatus.CONFLICT, "상위 카테고리와 하위 카테고리 이름은 같을 수 없습니다."),
    UNABLE_LENGTH_CATEGORY_NAME(HttpStatus.BAD_REQUEST, "카테고리 이름은 2자 ~ 15자로 입력해주세요."),
    NOT_EXIST_PARENT_CATEGORY(HttpStatus.NOT_FOUND, "상위 카테고리가 존재하지 않습니다."),
    EXIST_CHILD_CATEGORY(HttpStatus.BAD_REQUEST, "하위 카테고리가 존재합니다."),
    NOT_EXIST_CATEGORY(HttpStatus.NOT_FOUND, "존재하지 않는 카테고리입니다."),
    NOT_CHOICE_CATEGORY(HttpStatus.BAD_REQUEST, "선택된 카테고리가 없습니다."),
    NOT_EXIST_ADMIN_ACCOUNT(HttpStatus.UNAUTHORIZED, "존재하지 않는 관리자입니다."),
    ADMIN_NOT_FOUND(HttpStatus.NOT_FOUND, "존재하지 않는 계정입니다."),
    NOT_EXIST_CASH_CHARGING(HttpStatus.NOT_FOUND, "존재하지 않는 캐시 요청입니다."),
    IS_NON_MODIFIABLE_CASH_CHARGING_STATUS(HttpStatus.BAD_REQUEST, "수정할 수 없는 캐시 요청입니다."),
    
    
    BAD_REQUEST(HttpStatus.BAD_REQUEST, "잘못된 요청입니다."),
    FIELD_IS_NULL(HttpStatus.BAD_REQUEST, "필수 값을 입력해주세요."),
    GOOD_NAME_IS_NULL(HttpStatus.BAD_REQUEST, "상품명을 입력해주세요."),
    GOOD_SUMMARY_IS_NULL(HttpStatus.BAD_REQUEST, "상품 요약 설명을 입력해주세요."),
    GOOD_SALE_STATUS_IS_NULL(HttpStatus.BAD_REQUEST, "판매 상태를 선택해주세요."),
    GOOD_PRICE_IS_NULL(HttpStatus.BAD_REQUEST, "상품 대표 가격을 입력해주세요."),
    GOOD_OPTION_IS_NULL(HttpStatus.BAD_REQUEST, "옵션은 최소 1개가 필요합니다."),
    GOOD_PACKAGING_TYPE_IS_NULL(HttpStatus.BAD_REQUEST, "포장 타입을 선택해주세요."),
    GOOD_WEIGHT_VOLUME_IS_NULL(HttpStatus.BAD_REQUEST, "중량/용량을 입력해주세요."),
    GOOD_DESCRIPTION_IS_NULL(HttpStatus.BAD_REQUEST, "상품 상세 설명을 입력해주세요."),
    GOOD_OPTION_NAME_IS_NULL(HttpStatus.BAD_REQUEST, "옵션의 이름을 입력해주세요."),
    GOOD_OPTION_SALE_STATUS_IS_NULL(HttpStatus.BAD_REQUEST, "옵션의 판매 상태를 선택해주세요."),
    GOOD_OPTION_PRICE_IS_NULL(HttpStatus.BAD_REQUEST, "옵션의 상품 금액을 입력해주세요."),
    GOOD_OPTION_AMOUNT_IS_NULL(HttpStatus.BAD_REQUEST, "옵션의 재고를 입력해주세요."),


    NOT_EXIST_GOOD(HttpStatus.NOT_FOUND, "존재하지 않는 상품입니다."),
    NOT_EXIST_GOOD_DETAIL(HttpStatus.NOT_FOUND, "존재하지 않는 상품 상세입니다."),
    NOT_EXIST_GOOD_OPTION(HttpStatus.NOT_FOUND, "존재하지 않는 상품 옵션입니다."),
    NOT_EXIST_GOOD_PHOTO(HttpStatus.BAD_REQUEST, "상품 이미지 삭제에 실패하였습니다. 관리자에게 문의해주세요."),




    ;


    private HttpStatus status;
    private String message;
}
