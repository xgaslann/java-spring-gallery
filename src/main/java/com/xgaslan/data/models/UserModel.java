package com.xgaslan.data.models;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.UUID;

public class UserModel {

    @Getter
    @Setter
    @AllArgsConstructor
    @NoArgsConstructor
    public static class UserViewModel{
        private UUID id;

        private String email;

        private String username;
    }
}