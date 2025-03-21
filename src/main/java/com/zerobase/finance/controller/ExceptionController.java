package com.zerobase.finance.controller;

import com.zerobase.finance.dto.ResponseDto;
import com.zerobase.finance.enums.Description;
import com.zerobase.finance.enums.ErrorCode;
import com.zerobase.finance.exception.ScrappiingException;
import jakarta.persistence.EntityExistsException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

@Slf4j
@ControllerAdvice
public class ExceptionController {
    @ExceptionHandler(IllegalArgumentException.class)
    public ResponseEntity<ResponseDto> illegalArgumentException(IllegalArgumentException e) {
        ErrorCode errorCode = ErrorCode.valueOf(e.getMessage());
        log.error(errorCode.toString());
        log.error("발생 위치 : {}:{}",e.getStackTrace()[0].getFileName(), e.getStackTrace()[0].getLineNumber());
        return new ResponseEntity<>(ResponseDto.error(HttpStatus.BAD_REQUEST, errorCode), HttpStatus.BAD_REQUEST);
    }
    @ExceptionHandler(EntityExistsException.class)
    public ResponseEntity<ResponseDto> entityExistsException(EntityExistsException e) {
        ErrorCode errorCode = ErrorCode.valueOf(e.getMessage());
        log.error(errorCode.toString());
        log.error("발생 위치 : {}:{}",e.getStackTrace()[0].getFileName(), e.getStackTrace()[0].getLineNumber());
        return new ResponseEntity<>(ResponseDto.error(HttpStatus.BAD_REQUEST, errorCode), HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(ScrappiingException.class)
    public ResponseEntity<ResponseDto> scrappiingException(ScrappiingException e) {
        ErrorCode errorCode = ErrorCode.valueOf(e.getMessage());
        log.error(errorCode.toString());
        log.error("발생 위치 : {}:{}",e.getStackTrace()[0].getFileName(), e.getStackTrace()[0].getLineNumber());
        return new ResponseEntity<>(ResponseDto.error(HttpStatus.BAD_REQUEST, errorCode), HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(IllegalAccessException.class)
    public ResponseEntity<ResponseDto> unAuthException(IllegalAccessException e) {
        ErrorCode errorCode = ErrorCode.valueOf(e.getMessage());
        log.error(errorCode.toString());
        log.error("발생 위치 : {}:{}",e.getStackTrace()[0].getFileName(), e.getStackTrace()[0].getLineNumber());
        return new ResponseEntity<>(ResponseDto.error(HttpStatus.UNAUTHORIZED, errorCode), HttpStatus.UNAUTHORIZED);
    }


    @ExceptionHandler(Exception.class)
    public ResponseEntity<?> handleException(Exception ex){
        log.error(ex.getMessage());
        //개발용
        log.error("{}",ex);
        log.error("발생 위치 : {}:{}",ex.getStackTrace()[0].getFileName(), ex.getStackTrace()[0].getLineNumber());
        return new ResponseEntity<>(ResponseDto.error(HttpStatus.INTERNAL_SERVER_ERROR, ErrorCode.UNEXPECTED_ERROR), HttpStatus.INTERNAL_SERVER_ERROR);
    }

}
