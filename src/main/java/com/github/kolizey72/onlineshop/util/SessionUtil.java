package com.github.kolizey72.onlineshop.util;

import com.github.kolizey72.onlineshop.entity.User;
import com.github.kolizey72.onlineshop.service.UserService;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.util.NoSuchElementException;

@Component
public class SessionUtil {

    private static SessionUtil sessionUtil;
    private final UserService userService;

    public SessionUtil(UserService userService) {
        this.userService = userService;
        sessionUtil = this;
    }

    public static void checkStillEnabled(HttpServletRequest request, HttpServletResponse response) {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        if (authentication != null) {
            Object principal = authentication.getPrincipal();
            if (principal instanceof User userFromCtx) {
                try {
                    User userFromDb = sessionUtil.userService.findById(userFromCtx.getId());
                    if (userFromDb.getBanned()) {
                        deleteSession(request, response);
                    }

                } catch (NoSuchElementException e) {
                    deleteSession(request, response);
                }
            }
        }
    }

    public static void deleteSession(HttpServletRequest request, HttpServletResponse response) {
        Cookie cookie = new Cookie("JSESSIONID", null);
        cookie.setPath("/");
        cookie.setMaxAge(0);
        response.addCookie(cookie);

        HttpSession session = request.getSession(false);
        if (session != null) {
            session.invalidate();
            response.setHeader("Refresh", "0");
        }
    }
}
