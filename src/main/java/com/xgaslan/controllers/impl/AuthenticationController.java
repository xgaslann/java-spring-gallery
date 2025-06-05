package com.xgaslan.controllers.impl;

import com.xgaslan.controllers.IAuthenticationController;
import com.xgaslan.data.models.AuthenticationModel;
import com.xgaslan.data.models.UserModel;
import com.xgaslan.result.ServiceResult;
import com.xgaslan.services.IAuthenticationService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/auth")
public class AuthenticationController implements IAuthenticationController {

    private final IAuthenticationService _authenticationService;

    @Autowired
    public AuthenticationController(IAuthenticationService authenticationService) {
        _authenticationService = authenticationService;
    }

    @Override
    @PostMapping("/register")
    public ResponseEntity<ServiceResult<UserModel.UserViewModel>> register(@Valid @RequestBody AuthenticationModel.RegisterModel model) {
        var result = _authenticationService.register(model);
        return ResponseEntity.status(result.getStatus()).body(result);
    }

    @Override
    @PostMapping("/login")
    public ResponseEntity<ServiceResult<AuthenticationModel.LoginViewModel>> login(@Valid @RequestBody AuthenticationModel.LoginModel model) {
        var result = _authenticationService.login(model);
        return ResponseEntity.status(result.getStatus()).body(result);
    }
}
