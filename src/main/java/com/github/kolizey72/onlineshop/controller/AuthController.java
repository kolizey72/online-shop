package com.github.kolizey72.onlineshop.controller;

import com.github.kolizey72.onlineshop.dto.UserRegistrationForm;
import com.github.kolizey72.onlineshop.service.UserService;
import com.github.kolizey72.onlineshop.validation.RegistrationValidation;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.validation.Valid;

import static com.github.kolizey72.onlineshop.util.SessionUtil.checkStillEnabled;

@Controller
@RequestMapping("/auth")
public class AuthController {

    private final UserService userService;

    private final RegistrationValidation registrationValidation;

    public AuthController(UserService userService, RegistrationValidation registrationValidation) {
        this.userService = userService;
        this.registrationValidation = registrationValidation;
    }

    @GetMapping("/login")
    public String loginPage(HttpServletRequest request, HttpServletResponse response) {
        checkStillEnabled(request, response);

        return "auth/login";
    }

    @GetMapping("/registration")
    public String registrationPage(HttpServletRequest request, HttpServletResponse response,
                                   @ModelAttribute("user") UserRegistrationForm user) {
        checkStillEnabled(request, response);

        return "auth/registration";
    }

    @PostMapping("/registration")
    public String registration(HttpServletRequest request, HttpServletResponse response,
                               @ModelAttribute("user") @Valid UserRegistrationForm user, BindingResult bindingResult) {
        checkStillEnabled(request, response);

        registrationValidation.validate(user, bindingResult);
        if (bindingResult.hasErrors()) {
            return "auth/registration";
        }

        userService.register(user);
        return "redirect:/auth/login";
    }
}
