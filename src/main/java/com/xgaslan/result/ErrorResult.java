package com.xgaslan.result;

import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class ErrorResult {
    private final String code;

    private final String message;

    private final String detail;
}