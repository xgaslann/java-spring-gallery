package com.xgaslan.controllers;

import com.xgaslan.data.models.AuthenticationModel;
import com.xgaslan.data.models.UserModel;
import com.xgaslan.result.ServiceResult;
import org.springframework.http.ResponseEntity;

public interface IAuthenticationController {
    ResponseEntity<ServiceResult<UserModel.UserViewModel>> register(AuthenticationModel.RegisterModel model);

    ResponseEntity<ServiceResult<AuthenticationModel.LoginViewModel>> login(AuthenticationModel.LoginModel model);
}
