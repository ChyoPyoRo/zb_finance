package com.zerobase.finance.service;

import com.zerobase.finance.dto.ResponseDto;
import com.zerobase.finance.dto.UserSignUpRequestDto;
import com.zerobase.finance.entity.Users;
import com.zerobase.finance.enums.Description;
import com.zerobase.finance.enums.ErrorCode;
import com.zerobase.finance.enums.RoleType;
import com.zerobase.finance.repository.UsersDetailRepository;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.http.HttpStatus;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
@Slf4j
@Transactional
@RequiredArgsConstructor
public class UsersDetailService {
    private final PasswordEncoder passwordEncoder;
    private final UsersDetailRepository userDetailRepository;

    public ResponseDto<?> userSignUp(UserSignUpRequestDto requestDto){
        if(userDetailRepository.checkSameUserId(requestDto.getId())) throw new DataIntegrityViolationException(ErrorCode.DUPLICATE_ID.name());
        Users user = Users.builder().loginId(requestDto.getId()).password(pwdEncoder(requestDto.getPassword())).roleType(RoleType.ADMIN).build();
        userDetailRepository.signUP(user);
        return ResponseDto.success();
    }

    private String pwdEncoder(String password) {
        return passwordEncoder.encode(password);
    }
}
