package com.zerobase.finance.dto;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.zerobase.finance.enums.Description;
import com.zerobase.finance.enums.ErrorCode;
import lombok.*;
import org.springframework.http.HttpStatus;

@Getter
@NoArgsConstructor
@AllArgsConstructor
public class ResponseDto<T> {
    private Integer status;
    private Description description;
    @JsonInclude(JsonInclude.Include.NON_NULL)
    private T data;
    @JsonInclude(JsonInclude.Include.NON_NULL)
    private String errorCode;
    @JsonInclude(JsonInclude.Include.NON_NULL)
    private String errorMessage;


    public static <T> ResponseDto<T> success(T data) {
        return new ResponseDto<>(200, Description.SUCCESS, data, null, null);
    }

    public static ResponseDto<Void> success() {
        return new ResponseDto<>(200, Description.SUCCESS, null, null, null);
    }

    public static ResponseDto<Void> error(HttpStatus status, ErrorCode errorCode) {
        return new ResponseDto<>(status.value(), Description.FAILURE, null, errorCode.getErrorCode(), errorCode.getErrorMessage());
    }
}
