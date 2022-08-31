package com.github.kolizey72.onlineshop.controller;

import com.github.kolizey72.onlineshop.entity.User;
import com.github.kolizey72.onlineshop.service.UserService;
import com.github.kolizey72.onlineshop.validation.UserValidation;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.validation.Valid;

import static com.github.kolizey72.onlineshop.util.SessionUtil.checkStillEnabled;

@Controller
@RequestMapping("/admin")
public class AdminController {

    private final UserService userService;
    private final UserValidation userValidation;

    public AdminController(UserService userService, UserValidation userValidation) {
        this.userService = userService;
        this.userValidation = userValidation;
    }

    @GetMapping
    public String adminPage() {
        return "admin/home";
    }

    @GetMapping("/users")
    public String users(HttpServletRequest request, HttpServletResponse response,
                        Model model) {
        checkStillEnabled(request, response);

        model.addAttribute("users", userService.findAllOrdered());

        return "admin/users";
    }

    @PatchMapping("/users/{id}")
    public String updateUser(HttpServletRequest request, HttpServletResponse response,
                             RedirectAttributes redirectAttributes,
                             @PathVariable long id,
                             @ModelAttribute("updatedUser") @Valid User user, BindingResult bindingResult) {
        checkStillEnabled(request, response);

        userValidation.validate(user, bindingResult);
        if (bindingResult.hasErrors()) {
            redirectAttributes.addFlashAttribute("org.springframework.validation.BindingResult.updatedUser", bindingResult);
            redirectAttributes.addFlashAttribute("updatedUser", user);
            return "redirect:/admin/users?error=" + id;
        }

        userService.update(id, user);

        return "redirect:/admin/users";
    }

    @PatchMapping("/users/{id}/ban")
    public String toggleBanned(HttpServletRequest request, HttpServletResponse response, @PathVariable long id) {
        checkStillEnabled(request, response);

        userService.toggleBanned(id);
        return "redirect:/admin/users";
    }

    @DeleteMapping("/users/{id}")
    public String deleteUser(HttpServletRequest request, HttpServletResponse response, @PathVariable long id) {
        checkStillEnabled(request, response);

        userService.delete(id);
        return "redirect:/admin/users";
    }

    @ModelAttribute("updatedUser")
    public User user() {
        return new User();
    }
}
