package example.presentation.controller;

import example.domain.model.user.UserSummaries;

import example.application.service.UserService;

import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;

@Controller
@RequestMapping("/")
class TopController {

    @Autowired
    UserService userService;

    @ModelAttribute("users")
    UserSummaries users() {
        return userService.list();
    }

    @RequestMapping
    String show() {
        return "top";
    }

}
