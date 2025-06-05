package com.xgaslan.controllers;

import com.xgaslan.result.ServiceResult;
import org.springframework.http.ResponseEntity;

public interface IHealthController {
    ResponseEntity<ServiceResult> alive();
}
