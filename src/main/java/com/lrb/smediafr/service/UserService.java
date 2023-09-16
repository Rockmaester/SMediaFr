package com.lrb.smediafr.service;


import com.lrb.smediafr.dao.UserRepository;
import com.lrb.smediafr.entity.Role;
import com.lrb.smediafr.entity.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import java.util.*;
import java.util.stream.Collectors;

@Service
public class UserService implements UserDetailsService {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private MailSender mailSender;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Value("${hostname}")
    private String hostname;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        User user = userRepository.findByUsername(username);
        if(user == null){
            throw new UsernameNotFoundException("User not found!");
        }
        return user;
    }

    public boolean addUser(User user){
        User userFromDB = userRepository.findByUsername(user.getUsername());
        if(userFromDB != null){
            return false;
        }
        user.setActive(false);
        user.setRoles(Collections.singleton(Role.USER));
        user.setActivationCode(UUID.randomUUID().toString());
        user.setPassword(passwordEncoder.encode(user.getPassword()));
        userRepository.save(user);
        sendActivationMessage(user);
        return true;
    }


    private void sendActivationMessage(User user) {
        if(!StringUtils.isEmpty(user.getEmail())){
            String message = String.format(
                    "Hello, %s! \n" +
                            "Welcome to SocialMedia. Please visit next link: http://%s/activate/%s",
                    user.getUsername(), hostname, user.getActivationCode());
            mailSender.send(user.getEmail(), "Activation code", message);
        }
    }

    private void sendEmailChangeConfirmationMessage(User user) {
        if(!StringUtils.isEmpty(user.getEmail())){
            String message = String.format(
                    "Hello, %s! \n" +
                            "Please follow the link to confirm your email change: http://%s/administration/confirm/%s",
                    user.getUsername(), hostname, user.getActivationCode());
            mailSender.send(user.getEmail(), "Confirmation email code", message);
        }
    }


    public boolean activateUser(String code) {

        User user = userRepository.findByActivationCode(code);

        if(user == null){
            return false;
        }

        user.setActivationCode(null);
        user.setActive(true);

        userRepository.save(user);
        return true;
    }

    public List<User> findAll() {
        return userRepository.findAll();
    }

    public Optional<User> findById(Long id) {
        return userRepository.findById(id);
    }

    public void saveUser(User user, String username, Map<String, String> form) {
        user.setUsername(username);

        Set<String> roles = Arrays.stream(Role.values())
                .map(Role::name)
                .collect(Collectors.toSet());

        user.getRoles().clear();

        for(String key: form.keySet()){

            if(roles.contains(key)){
                user.getRoles().add(Role.valueOf(key));
            }
        }

        userRepository.save(user);
    }


    public int updateProfile(User user, String password, String email) {

        // апдейты: почта(1), пароль(2) или оба(3).
        int result = 0;

        String userEmail = user.getEmail();
        boolean isEmailChanged = (email != null && !email.equals(userEmail) ||
                (userEmail != null && !userEmail.equals(email)));


        if(isEmailChanged){
            user.setEmail(email);
            if(!StringUtils.isEmpty(email)){
                user.setActivationCode(UUID.randomUUID().toString());
            }
            result = 1;
        }

        if(!StringUtils.isEmpty(password)){
            user.setPassword(passwordEncoder.encode(password));
            result += 2;
        }

        userRepository.save(user);

        if(isEmailChanged){
            sendEmailChangeConfirmationMessage(user);
        }
        return result;
    }


    public void subscribe(User currentUser, User user) {
        user.getSubscribers().add(currentUser);
        userRepository.save(user);
    }


    public void unsubscribe(User currentUser, User user) {
        user.getSubscribers().remove(currentUser);
        userRepository.save(user);
    }
}
