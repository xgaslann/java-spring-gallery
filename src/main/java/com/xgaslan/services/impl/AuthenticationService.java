package com.xgaslan.services.impl;

import com.xgaslan.data.configs.JwtConfig;
import com.xgaslan.data.entities.User;
import com.xgaslan.data.mappers.UserMapper;
import com.xgaslan.data.models.AuthenticationModel;
import com.xgaslan.data.models.UserModel;
import com.xgaslan.exceptions.ErrorCodes;
import com.xgaslan.repositories.IRefreshTokenRepository;
import com.xgaslan.repositories.IUserRepository;
import com.xgaslan.result.ServiceResult;
import com.xgaslan.security.config.AppConfig;
import com.xgaslan.security.jwt.JwtService;
import com.xgaslan.services.IAuthenticationService;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.stereotype.Service;

@Service
public class AuthenticationService implements IAuthenticationService {

    private final IUserRepository userRepository;
    private final IRefreshTokenRepository refreshTokenRepository;
    private final AppConfig appConfig;
    private final JwtService jwtService;
    private final JwtConfig jwtConfig;

    public AuthenticationService(IUserRepository userRepository, IRefreshTokenRepository refreshTokenRepository, AppConfig appConfig, JwtService jwtService, JwtConfig jwtConfig) {
        this.userRepository = userRepository;
        this.refreshTokenRepository = refreshTokenRepository;
        this.appConfig = appConfig;
        this.jwtService = jwtService;
        this.jwtConfig = jwtConfig;
    }

    @Override
    public ServiceResult<AuthenticationModel.LoginViewModel> login(AuthenticationModel.LoginModel model) {
        var auth = appConfig.authenticationProvider().authenticate(
                new UsernamePasswordAuthenticationToken(model.getEmail(), model.getPassword())
        );
        var user = (User) auth.getPrincipal();
        var token = jwtService.generateToken(user);
        return ServiceResult.success(new AuthenticationModel.LoginViewModel(token), 200);
    }

    @Override
    public ServiceResult<UserModel.UserViewModel> register(AuthenticationModel.RegisterModel model) {
        if (userRepository.findByEmail(model.getEmail()).isPresent()) {
            return ServiceResult.failure(
                    ErrorCodes.Business.Common.ALREADY_EXISTS_ERROR,
                    "Registration failed.",
                    "Email or password is invalid.",
                    400
            );
        }

        var entity = UserMapper.toUser(model, appConfig.passwordEncoder());
        userRepository.save(entity);
        return ServiceResult.success(UserMapper.toUserViewModel(entity), 201);
    }

    // WILL BE IMPLEMENTED LATER
//    public ServiceResult<AuthenticationModel.LoginViewModel> refreshToken(String token) {
//        if (token == null || token.isEmpty()) {
//            return ServiceResult.failure(
//                    ErrorCodes.Business.Common.INVALID_TOKEN_ERROR,
//                    "Token is invalid.",
//                    "The provided token is either null or empty.",
//                    400
//            );
//        }
//
//        var validatedToken = validateRefreshToken(token);
//
//        if (validatedToken.isEmpty()) {
//            return ServiceResult.failure(
//                    ErrorCodes.Business.Common.INVALID_TOKEN_ERROR,
//                    "Token is invalid.",
//                    "The provided token is either expired or does not exist.",
//                    400
//            );
//        }
//
//        var refreshToken = validatedToken.get();
//
//        var user = refreshToken.getUser();
//
//        if (user == null) {
//            return ServiceResult.failure(
//                    ErrorCodes.Business.Common.INVALID_TOKEN_ERROR,
//                    "User not found.",
//                    "The user associated with the token does not exist.",
//                    404
//            );
//        }
//
//        // Delete the old refresh token
//        deleteByToken(token);
//        var newToken = jwtService.generateToken(user);
//        // Save a new refresh token
//
//        return ServiceResult.success(new AuthenticationModel.LoginViewModel(newToken), 200);
//
//    }

//    public RefreshToken createRefreshToken(User user) {
//        var token = new RefreshToken();
//        token.setToken(UUID.randomUUID().toString());
//        token.setUser(user);
//        token.setExpiryDate(new Date(System.currentTimeMillis() + jwtConfig.getRefreshExpirationTimeMillis()));
//        return refreshTokenRepository.save(token);
//    }
//
//    // Token'ı kontrol et (var mı ve süresi geçmemiş mi)
//    public Optional<RefreshToken> validateRefreshToken(String token) {
//        return refreshTokenRepository.findByToken(token)
//                .filter(rt -> rt.getExpiryDate().after(new Date()));
//    }
//
//    // Logout: sadece bu token'ı sil
//    public void deleteByToken(String token) {
//        refreshTokenRepository.deleteByToken(token);
//    }
//
//    // Tüm refresh token'ları sil (tüm cihazlardan çık)
//    public void deleteAllByUserEmail(String email) {
//        refreshTokenRepository.deleteAllByUser_Email(email);
//    }
//
//    public void deleteByUserEmail(String email) {
//        refreshTokenRepository.deleteByUser_Email(email);
//    }
}

