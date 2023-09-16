package com.lrb.smediafr.controller;

import com.lrb.smediafr.entity.User;
import com.lrb.smediafr.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/user")
public class SubscribersController {

    @Autowired
    private UserService userService;


    @GetMapping("subscribe/{user}")
    public String subscribe(
            @AuthenticationPrincipal User currentUser,
            @PathVariable User user
    ){
        userService.subscribe(currentUser, user);
        return "redirect:/user-publications/" + user.getId();
    }


    @GetMapping("unsubscribe/{user}")
    public String unsubscribe(
            @AuthenticationPrincipal User currentUser,
            @PathVariable User user
    ){
        userService.unsubscribe(currentUser, user);
        return "redirect:/user-publications/" + user.getId();
    }
}
