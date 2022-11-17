package com.nhnacademy.springmvc.controller;

import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.util.Objects;

@Slf4j
@Controller
public class HomeController {

    @GetMapping("/")
    public String home(HttpServletRequest request) {
        log.info("HomeController get home");

        HttpSession session = request.getSession();

        if (Objects.nonNull(session.getAttribute("admin"))) {
            return "view/admin/index";
        }

        if (Objects.nonNull(session.getAttribute("client"))) {
            return "view/client/index";
        }
        return "view/index";
    }
}
