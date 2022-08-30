package com.github.kolizey72.onlineshop.validation;

import com.github.kolizey72.onlineshop.dto.UserRegistrationForm;
import com.github.kolizey72.onlineshop.repository.UserRepository;
import org.springframework.stereotype.Component;
import org.springframework.validation.Errors;
import org.springframework.validation.Validator;

@Component
public class RegistrationValidation implements Validator {

    private final UserRepository userRepository;

    public RegistrationValidation(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @Override
    public boolean supports(Class<?> clazz) {
        return UserRegistrationForm.class.equals(clazz);
    }

    @Override
    public void validate(Object target, Errors errors) {
        UserRegistrationForm user = (UserRegistrationForm) target;

        if (!user.getPassword().equals(user.getMatchingPassword())) {
            errors.rejectValue("matchingPassword", "", "Password didn't match");
        }

        if (userRepository.findByUsername(user.getUsername()).isPresent()) {
            errors.rejectValue("username", "", "Username is already taken");
        }

        if (userRepository.findByEmail(user.getEmail()).isPresent()) {
            errors.rejectValue("email", "", "Email is already taken");
        }
    }
}
