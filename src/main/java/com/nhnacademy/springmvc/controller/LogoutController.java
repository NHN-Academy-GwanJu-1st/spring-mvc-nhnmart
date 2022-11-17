package com.nhnacademy.springmvc.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.util.Objects;

@Controller
public class LogoutController {

    @GetMapping("/logout")
    public String doLogout(HttpServletRequest request) {
        HttpSession session = request.getSession();

        if (Objects.nonNull(session.getAttribute("admin"))) {
            session.removeAttribute("admin");
        }

        if (Objects.nonNull(session.getAttribute("client"))) {
            session.removeAttribute("client");
        }

        return "view/index";
    }
}
