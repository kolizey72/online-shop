package com.github.kolizey72.onlineshop.controller;

import com.github.kolizey72.onlineshop.entity.User;
import com.github.kolizey72.onlineshop.entity.UserClass;
import com.github.kolizey72.onlineshop.service.UserService;
import org.springframework.beans.propertyeditors.CustomDateEditor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.*;

import java.text.SimpleDateFormat;
import java.util.Date;

@Controller
@RequestMapping("/auth")
public class AuthController {

    private final UserService userService;

    public AuthController(UserService userService) {
        this.userService = userService;
    }

    @GetMapping("/login")
    public String loginPage() {
        return "auth/login";
    }

    @GetMapping("/registration")
    public String registrationPage(Model model, @ModelAttribute("user") User user) {
        model.addAttribute("classes", UserClass.values());
        return "auth/registration";
    }

    @PostMapping("/registration")
    public String registration(@ModelAttribute("user") User user) {
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
