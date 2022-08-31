package com.github.kolizey72.onlineshop.validation;

import com.github.kolizey72.onlineshop.entity.User;
import com.github.kolizey72.onlineshop.repository.UserRepository;
import org.springframework.stereotype.Component;
import org.springframework.validation.Errors;
import org.springframework.validation.Validator;

@Component
public class UserValidation implements Validator {

    private final UserRepository userRepository;

    public UserValidation(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @Override
    public boolean supports(Class<?> clazz) {
        return User.class.equals(clazz);
    }

    @Override
    public void validate(Object target, Errors errors) {
        User user = (User) target;

        userRepository.findByUsernameIgnoreCase(user.getUsername()).ifPresent(userFromDb -> {
            if (!user.getId().equals(userFromDb.getId())) {
                errors.rejectValue("username", "", "Username is already taken");
            }
        });


        userRepository.findByEmailIgnoreCase(user.getEmail()).ifPresent(userFromDb -> {
            if (!user.getId().equals(userFromDb.getId())) {
                errors.rejectValue("email", "", "Email is already taken");
            }
        });
    }
}
