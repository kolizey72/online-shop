package com.github.kolizey72.onlineshop.controller;

import com.github.kolizey72.onlineshop.dto.UserRegistrationForm;
import com.github.kolizey72.onlineshop.service.UserService;
import com.github.kolizey72.onlineshop.validation.RegistrationValidation;
import org.springframework.beans.propertyeditors.CustomDateEditor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.text.SimpleDateFormat;
import java.util.Date;

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
    public String loginPage() {
        return "auth/login";
    }

    @GetMapping("/registration")
    public String registrationPage(@ModelAttribute("user") UserRegistrationForm user) {
        return "auth/registration";
    }

    @PostMapping("/registration")
    public String registration(@ModelAttribute("user") @Valid UserRegistrationForm user, BindingResult bindingResult) {
        registrationValidation.validate(user, bindingResult);
        if (bindingResult.hasErrors()) {
            return "auth/registration";
        }

        userService.register(user);
        return "redirect:/auth/login";
    }

    @InitBinder
    private void dateBinder(WebDataBinder binder) {
        SimpleDateFormat dateFormat = new SimpleDateFormat("dd-MM-yyyy");
        CustomDateEditor editor = new CustomDateEditor(dateFormat, true);
        binder.registerCustomEditor(Date.class, editor);
    }
}
