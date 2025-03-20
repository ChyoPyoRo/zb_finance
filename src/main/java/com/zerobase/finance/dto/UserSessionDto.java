package com.zerobase.finance.dto;

import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class UserSessionDto {
    private String userUuid;
    private String role;
}
