package com.lrb.smediafr.controller;

import com.lrb.smediafr.entity.Role;
import com.lrb.smediafr.entity.User;
import com.lrb.smediafr.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
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
        model.addAttribute("user", user);
        return "userProfile";
    }

    @PostMapping("/profile")
    public String updateProfile(
            @RequestParam String password,
            @RequestParam String email,
            @AuthenticationPrincipal @Valid User user,
            Model model
    ){
        model.addAttribute("username", user.getUsername());

        boolean isEmptyPassword = StringUtils.isEmpty(password);
        if (isEmptyPassword){
            model.addAttribute("passwordError", ">> Password can't be empty <<");
            return "userProfile";
        }

        boolean isEmptyEmail = StringUtils.isEmpty(email);
        if (isEmptyEmail){
            model.addAttribute("emailError", ">> Email can't be empty <<");
            return "userProfile";
        }

        int updateResult = userService.updateProfile(user, password, email);

        if(updateResult == 1){
            model.addAttribute("messageType", "warning");
            model.addAttribute("message", "Please visit your mailbox to confirm your email change");
        }
        else if (updateResult == 2){
            model.addAttribute("messageType", "success");
            model.addAttribute("message", "The password has been updated successfully.");
        }
        else if (updateResult == 3){
            model.addAttribute("messageType", "warning");
            model.addAttribute("message", "The password has been updated successfully. \nAlso visit your mailbox to confirm your email change.");
        }
        else {
            model.addAttribute("messageType", "danger");
            model.addAttribute("message", "Something was wrong during updating!");
        }
        return "updateProfileNotification";
    }

    @GetMapping("/confirm/{code}")
    public String confirmEmail(Model model, @PathVariable String code){

        boolean isActivated = userService.activateUser(code);
        if(isActivated){
            model.addAttribute("messageType", "success");
            model.addAttribute("message", "Email change successfully confirmed");
        } else {
            model.addAttribute("messageType", "danger");
            model.addAttribute("message", "Activation code is not found!");
        }
        return "updateEmailNotification";
    }
}
