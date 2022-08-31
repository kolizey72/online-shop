package com.github.kolizey72.onlineshop.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import static com.github.kolizey72.onlineshop.util.SessionUtil.checkStillEnabled;

@Controller
public class HomeController {

    @GetMapping
    public String homePage(HttpServletRequest request, HttpServletResponse response) {
        checkStillEnabled(request, response);

        return "home";
    }
}
