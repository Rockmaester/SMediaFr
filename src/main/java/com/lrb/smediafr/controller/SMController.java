package com.lrb.smediafr.controller;


import com.lrb.smediafr.communication.Communication;
import com.lrb.smediafr.dao.PublicationRepository;
import com.lrb.smediafr.dao.UserRepository;
import com.lrb.smediafr.entity.Publication;
import com.lrb.smediafr.entity.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.util.StringUtils;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.validation.Valid;
import java.io.File;
import java.io.IOException;
import java.util.Map;
import java.util.Set;
import java.util.UUID;


@Controller
@RequestMapping("/")
public class SMController {
    // TODO внедрить JSP Session - http://www.w3big.com/ru/jsp/jsp-session.html#gsc.tab=0

    @Value("${upload.path}")
    // ищется в property upload.path и вставляет значение в переменную uploadPath
    private String uploadPath;

    @Autowired
    private Communication communication;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private PublicationRepository publicationRepository;

    @GetMapping("/")
    public String goToMain(@AuthenticationPrincipal User currentUser, Model model) {

        Iterable<Publication> publications = publicationRepository.findAll();
        model.addAttribute("publications", publications);
        model.addAttribute("currentuser", currentUser);
        model.addAttribute("mysubscribers", currentUser.getSubscribers());
        model.addAttribute("subscriptions", currentUser.getSubscriptions());
        model.addAttribute("subscriptionsCount", currentUser.getSubscriptions().size());
        model.addAttribute("subscribersCount", currentUser.getSubscribers().size());

//        return "mainPage";
        return "redirect:/main/" + currentUser.getId();
    }

    @GetMapping("/main/{user}")
    public String showMain(@AuthenticationPrincipal User currentUser,
                           @PathVariable User user,
                           Model model
    ){
        Iterable<Publication> publications = publicationRepository.findAll();
        model.addAttribute("publications", publications);
        model.addAttribute("delimiter", "=====================================================");
        model.addAttribute("currentuser", currentUser);
        model.addAttribute("mysubscribers", user.getSubscribers());
        model.addAttribute("subscriptions", user.getSubscriptions());
        model.addAttribute("subscriptionsCount", user.getSubscriptions().size());
        model.addAttribute("subscribersCount", user.getSubscribers().size());

        return "mainPage";
    }



    @GetMapping("/createPublication")
    public String goToCreatePublicationPage() {
        return "createPublicationPage";
    }

    @PostMapping("/savePublication")
    public String savePublication(
            @AuthenticationPrincipal User user,
            @Valid Publication publication,
            BindingResult bindingResult,
            Model model,
            @RequestParam("file") MultipartFile file
    ) {
        publication.setAuthor(user);

        if (bindingResult.hasErrors()) {

            Map<String, String> errorsMap = ControllerUtils.getErrors(bindingResult);
            model.mergeAttributes(errorsMap);
            model.addAttribute("publication", publication);
            return "createPublicationPage";

        } else {

            saveFile(publication, file);

            model.addAttribute("publication", null);

            publicationRepository.save(publication);
        }

        Iterable<Publication> publications = publicationRepository.findAll();
        model.addAttribute("publications", publications);
        model.addAttribute("delimiter", "============================================================");

        return "redirect:/";
    }

    private void saveFile(Publication publication, MultipartFile file) {
        if (file != null && !file.getOriginalFilename().isEmpty()) {
            File uploadDir = new File(uploadPath);
            if (!uploadDir.exists()) {
                uploadDir.mkdir();
            }

            String uuidFile = UUID.randomUUID().toString();
            String resultFileName = uuidFile + "." + file.getOriginalFilename();

            try {
                file.transferTo(new File(uploadPath + "/" + resultFileName));
            } catch (IOException e) {
                throw new RuntimeException(e);
            }

            publication.setFilename(resultFileName);
        }
    }

    @GetMapping("/user-publications/{user}")
    public String userPublications(
            @AuthenticationPrincipal User currentUser,
            @PathVariable User user,
            Model model,
            @RequestParam(required = false) Publication publication
    ) {
        Set<Publication> publications = user.getPublications();
        model.addAttribute("publications", publications);
        model.addAttribute("isCurrentUser", currentUser.equals(user));
        model.addAttribute("publication", publication);

        // Добавление подписок и подписчиков:
        model.addAttribute("userChannel", user);
        model.addAttribute("subscriptionsCount", user.getSubscriptions().size());
        model.addAttribute("subscribersCount", user.getSubscribers().size());
        model.addAttribute("mysubscribers", user.getSubscribers());
        model.addAttribute("subscriptions", user.getSubscriptions());
        model.addAttribute("isSubscriber", user.getSubscribers().contains(currentUser));
        return "userPublications";
    }


    // метод по сохранению сообщения (с тем же маппингом)
    @PostMapping("/user-publications/{user}")
    public String updatePublication(
            @AuthenticationPrincipal User currentUser,
            @PathVariable Long id, // получается id пользователя
            @RequestParam("id") Publication publication,
            @RequestParam("content") String content,
            @RequestParam("title") String title,
            @RequestParam("file") MultipartFile file
    ) {
        if (publication.getAuthor().equals(currentUser)) {

            if (!StringUtils.isEmpty(content)) {
                publication.setContent(content);
            }

            if (!StringUtils.isEmpty(title)) {
                publication.setTitle(title);
            }

            saveFile(publication, file);
            publicationRepository.save(publication);
        }
        return "redirect:/user-publications/" + id;
    }

    @GetMapping("/editPublication/{user}")
    public String publicationEditPage(
            @AuthenticationPrincipal User currentUser,
            @PathVariable User user,
            @RequestParam("publicationId") Publication publication,
            Model model
    ) {
        model.addAttribute("publication", publication);
        model.addAttribute("isCurrentUser", currentUser.equals(user));
        return "createPublicationPage";
    }
}










