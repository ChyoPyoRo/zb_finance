package com.zerobase.finance.utils;

import com.zerobase.finance.dto.UserSessionDto;
import com.zerobase.finance.enums.ErrorCode;
import com.zerobase.finance.enums.RoleType;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;

import java.util.Collection;
import java.util.UUID;

public class UserUtils {
    public static UserSessionDto getUserSessionDto() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String userUuid = authentication.getName();
        Collection<? extends GrantedAuthority> authorities = authentication.getAuthorities();
        return UserSessionDto.builder()
                .userUuid(userUuid)
                .role(authorities.iterator().next().toString())
                .build();
    }

    public static UUID getUserUuidIfAdmin() throws IllegalAccessException {
        UserSessionDto userSessionDto = getUserSessionDto();
        String auth = userSessionDto.getRole();

        if(RoleType.ADMIN.getRole().equals(auth)){
            return UUID.fromString(userSessionDto.getUserUuid());
        }
        else
            throw new IllegalAccessException(ErrorCode.UNAUTHORIZED.name());
    }
    public static boolean isAdmin() {
        UserSessionDto userSessionDto = getUserSessionDto();
        return userSessionDto.getRole().equals(RoleType.ADMIN.getRole());
    }
}
