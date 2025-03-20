package com.zerobase.finance.enums;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Getter
public enum RoleType {
    USER("USER"), ADMIN("ADMIN");

    private final String role;
}
