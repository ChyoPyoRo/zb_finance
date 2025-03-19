package com.zerobase.finance.enums;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public enum ErrorCode {
    UNEXPECTED_ERROR("E000001","예상치 못한 에러"),
    UNAUTHORIZED("E001001", "인증되지 않은 사용자 입니다."),
    JWT_TOKEN_INVALID("E001002", "유효하지 않은 토큰입니다."),
    MISSING_OR_INVALID_PARAM("E001003", "잘못된 파라미터가 전달됬거나 누락된 필수 파라미터가 존재합니다."),

    DUPLICATE_ID("E002001", "이미 사용중인 ID 입니다."),
    WRONG_ID("E002002", "ID값이 잘못됬습니다"),
    WRONG_PASSWORD("E002003", "PW값이 잘못됬습니다")
    ;

    private final  String errorCode;
    private final   String errorMessage;

}
