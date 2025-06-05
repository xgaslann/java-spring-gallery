package com.xgaslan.data.entities;

import com.xgaslan.data.entities.base.BaseUUIDKeyEntity;
import com.xgaslan.data.utils.annotations.PasswordValid;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Table;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotEmpty;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.Collection;
import java.util.List;

@Entity
@Table(name = "users")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class User extends BaseUUIDKeyEntity implements UserDetails {

    private String name;

    private String lastName;

    @NotBlank(message = "Username cannot be blank")
    @Column(nullable = false, unique = true)
    private String username;

    @NotBlank(message = "Email cannot be blank")
    @Email(message = "Email must be valid")
    @Column(nullable = false, unique = true)
    private String email;

    @NotBlank(message = "Password cannot be blank")
    @Column(nullable = false)
    @PasswordValid
    private String password;

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return List.of();
    }
}
