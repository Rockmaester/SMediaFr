package com.lrb.smediafr.controller;

import com.lrb.smediafr.entity.Role;
import com.lrb.smediafr.entity.User;
import com.lrb.smediafr.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.Map;
import java.util.Optional;

@Controller
@RequestMapping("/administration")
public class AdminController {

    @Autowired
    private UserService userService;

    @PreAuthorize("hasAuthority('ADMIN')")
    @GetMapping
    public String goToUsersList(Model model){
        model.addAttribute("users", userService.findAll());
        return "users";
    }

    // метод для вызова панели администрирования пользователей
    @PreAuthorize("hasAuthority('ADMIN')")
    @GetMapping("{id}")
    public String goToUserEditForm(@PathVariable Long id, Model model){

        User user = null;
        Optional<User> optional = userService.findById(id);

        if (optional.isPresent()){
            user = optional.get();
        }

        model.addAttribute("userToEdit", user);
        model.addAttribute("roles", Role.values());
        return "editUser";
    }



    // метод для сохранения результатов модификации в панели пользователей (вызов из формы userEdit)
    @PreAuthorize("hasAuthority('ADMIN')")
    @PostMapping
    public String userSave(
            @RequestParam String username,
            @RequestParam Map<String, String> form,
            @RequestParam("userId") User user
    ){
        userService.saveUser(user, username, form);
        return "redirect:/administration";
    }


    // функционал апдейта профиля (смены пароля, емейла, ...)
    @GetMapping("/profile")
    public String getProfile (Model model, @AuthenticationPrincipal User user){

        model.addAttribute("username", user.getUsername());
        model.addAttribute("email", user.getEmail());
        return "userProfile";
    }

    @PostMapping("/profile")
    public String updateProfile(@AuthenticationPrincipal User user, @RequestParam String password, @RequestParam String email){
        userService.updateProfile(user, password, email);
        return "redirect:/administration/profile";
    }
}
