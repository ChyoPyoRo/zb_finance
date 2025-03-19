package com.zerobase.finance.controller;

import com.zerobase.finance.dto.ResponseDto;
import com.zerobase.finance.dto.UserSignUpRequestDto;
import com.zerobase.finance.enums.ErrorCode;
import com.zerobase.finance.service.UsersDetailService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@Slf4j
@RequestMapping("/auth")
@RequiredArgsConstructor
public class UsersDetailController {
    private final UsersDetailService usersDetailService;
    @PostMapping(value = "/signup")
    public ResponseEntity<ResponseDto> userSignUp(@RequestBody UserSignUpRequestDto requestDto){
        if(!requestDto.isValid()) throw new IllegalArgumentException(ErrorCode.MISSING_OR_INVALID_PARAM.name());
        ResponseDto<?> result = usersDetailService.userSignUp(requestDto);
        return ResponseEntity.ok(result);
    }
}
