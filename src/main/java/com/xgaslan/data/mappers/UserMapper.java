package com.xgaslan.data.mappers;

import com.xgaslan.data.entities.User;
import com.xgaslan.data.models.AuthenticationModel;
import com.xgaslan.data.models.UserModel;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

public class UserMapper {
    public static UserModel.UserViewModel toUserViewModel(User entity) {
        if (entity == null) {
            return null;
        }

        return new UserModel.UserViewModel(entity.getId(), entity.getEmail(), entity.getUsername());
    }

    public static User toUser(AuthenticationModel.RegisterModel model, BCryptPasswordEncoder passwordEncoder) {
        if (model == null) {
            return null;
        }

        User user = new User();
        user.setUsername(model.getUsername());
        user.setEmail(model.getEmail());
        user.setPassword(passwordEncoder.encode(model.getPassword())); // Password should be hashed in a real application
        return user;
    }
}