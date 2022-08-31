package com.github.kolizey72.onlineshop.controller;

import com.github.kolizey72.onlineshop.service.UserService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import static com.github.kolizey72.onlineshop.util.SessionUtil.checkStillEnabled;

@Controller
@RequestMapping("/admin")
public class AdminController {

    private final UserService userService;

    public AdminController(UserService userService) {
        this.userService = userService;
    }

    @GetMapping
    public String adminPage() {
        return "admin/home";
    }

    @GetMapping("/users")
    public String users(HttpServletRequest request, HttpServletResponse response, Model model) {
        checkStillEnabled(request, response);

        model.addAttribute("users", userService.findAll());
        return "admin/users";
    }

    @DeleteMapping("/{id}")
    public String deleteUser(HttpServletRequest request, HttpServletResponse response, @PathVariable long id) {
        checkStillEnabled(request, response);

        userService.delete(id);
        return "redirect:/admin/users";
    }


}
