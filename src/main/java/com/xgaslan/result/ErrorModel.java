package com.xgaslan.result;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.UUID;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class ErrorModel {
    private final String traceId = UUID.randomUUID().toString().replace("-", "");
    private String code;
    private String message;
    private String detail;

    public static ErrorModel of(String code, String message, String detail) {
        ErrorModel e = new ErrorModel();
        e.setCode(code);
        e.setMessage(message);
        e.setDetail(detail);
        return e;
    }
}