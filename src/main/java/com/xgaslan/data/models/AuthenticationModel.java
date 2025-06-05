package com.xgaslan.data.models;

import jakarta.validation.constraints.NotEmpty;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

public class AuthenticationModel {

    @Getter
    @Setter
    @NoArgsConstructor
    @AllArgsConstructor
    public static class LoginModel {
        @NotEmpty
        private String email;

        @NotEmpty
        private String password;
    }

    @Getter
    @Setter
    @NoArgsConstructor
    @AllArgsConstructor
    public static class LoginViewModel{
        private String token;
    }

    @Getter
    @Setter
    @NoArgsConstructor
    @AllArgsConstructor
    public static class RegisterModel {
        @NotEmpty
        private String username;

        @NotEmpty
        private String email;

        @NotEmpty
        private String password;
    }
}

