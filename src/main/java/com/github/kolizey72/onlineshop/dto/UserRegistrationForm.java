package com.github.kolizey72.onlineshop.dto;

import com.github.kolizey72.onlineshop.entity.UserClass;
import com.github.kolizey72.onlineshop.entity.UserProfession;
import lombok.Getter;
import lombok.Setter;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;

public class UserRegistrationForm {

    @Size(min = 3, max = 32, message = "Username length must be 3-32")
    @Getter @Setter
    private String username;

    @NotBlank(message = "Email must not be empty")
    @Email(message = "Email must be valid")
    @Getter @Setter
    private String email;

    @Size(min = 5, message = "Password must be at least 5 characters long")
    @Getter @Setter
    private String password;

    @Getter @Setter
    private String matchingPassword;

    @Getter @Setter
    private UserClass userClass;

    @Getter @Setter
    private UserProfession userProfession;
}
