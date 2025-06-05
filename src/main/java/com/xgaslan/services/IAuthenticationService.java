package com.xgaslan.services;

import com.xgaslan.data.models.AuthenticationModel;
import com.xgaslan.data.models.UserModel;
import com.xgaslan.result.ServiceResult;

public interface IAuthenticationService {

    ServiceResult<AuthenticationModel.LoginViewModel> login(AuthenticationModel.LoginModel model);

//    ServiceResult<AuthenticationModel.LoginViewModel> refreshToken(AuthenticationModel.LoginModel model);

    ServiceResult<UserModel.UserViewModel> register(AuthenticationModel.RegisterModel model);

}
