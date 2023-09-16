package com.lrb.smediafr.controller;

import  com.lrb.smediafr.communication.Communication;
import com.lrb.smediafr.dao.PublicationRepository;
import com.lrb.smediafr.dao.UserRepository;

import com.lrb.smediafr.entity.Publication;
import com.lrb.smediafr.entity.User;
import com.lrb.smediafr.service.PublicationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
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
import java.util.UUID;


@Controller
@RequestMapping("/")
public class SMController {

    @Value("${upload.path}")
    private String uploadPath;

    @Value("${page.size}")
    private int pagesize;

    @Autowired
    private Communication communication;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private PublicationRepository publicationRepository;

    @Autowired
    private PublicationService publicationService;

    @GetMapping("/")
    public String goToMain(@AuthenticationPrincipal User currentUser,
                           Model model,
                           @PageableDefault(sort = {"id"}, direction = Sort.Direction.DESC) Pageable pageable
    ) {
        // Внедрение пагинации:
        Page<Publication> page = publicationRepository.findAll(pageable);
        model.addAttribute("page", page);
        model.addAttribute("url", "/");

        model.addAttribute("currentuser", currentUser);
        model.addAttribute("mysubscribers", currentUser.getSubscribers());
        model.addAttribute("subscriptions", currentUser.getSubscriptions());
        model.addAttribute("subscriptionsCount", currentUser.getSubscriptions().size());
        model.addAttribute("subscribersCount", currentUser.getSubscribers().size());
//        return "mainPage";
        return "redirect:/main/" + currentUser.getId() + "?page=0&size=" + pagesize;
    }

    @GetMapping("/main/{user}")
    public String showMain(@AuthenticationPrincipal User currentUser,
                           @PathVariable User user,
                           Model model,
                           @PageableDefault(sort = {"id"}, direction = Sort.Direction.DESC) Pageable pageable
    ){
        // Внедрение пагинации:
        Page<Publication> page = publicationRepository.findAll(pageable);

        System.out.println(page.getTotalPages());

        // Внедрение пагинации:
        model.addAttribute("page", page);
        model.addAttribute("url", "/main/" + user.getId());

        model.addAttribute("currentuser", currentUser);
        model.addAttribute("mysubscribers", user.getSubscribers());
        model.addAttribute("subscriptions", user.getSubscriptions());
        model.addAttribute("subscriptionsCount", user.getSubscriptions().size());
        model.addAttribute("subscribersCount", user.getSubscribers().size());
        model.addAttribute("pagesize", pagesize);
        return "mainPage";
    }


    @GetMapping("/createPublication")
    public String goToCreatePublicationPage(
    ){
        return "createPublicationPage";
    }

    @PostMapping ("/savePublication")
    public String savePublication(
            @AuthenticationPrincipal User user,
            @Valid Publication publication,
            BindingResult bindingResult,
            Model model,
            @RequestParam("file") MultipartFile file
    ){
        publication.setAuthor(user);
        if(bindingResult.hasErrors()){
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
        return "redirect:/";
    }

    void saveFile(Publication publication, MultipartFile file) {
        if(file != null && !file.getOriginalFilename().isEmpty()){
            File uploadDir = new File(uploadPath);
            if(!uploadDir.exists()){
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
            @RequestParam(required = false) Publication publication,
            @PageableDefault(sort = {"id"}, direction = Sort.Direction.DESC) Pageable pageable
    ){
        Page<Publication> page = publicationService.getPublicationsByUser(pageable, user);
        model.addAttribute("page", page);
        model.addAttribute("isCurrentUser", currentUser.equals(user));
        model.addAttribute("publication", publication);
        model.addAttribute("userChannel", user); // todo ?
        model.addAttribute("subscriptionsCount", user.getSubscriptions().size());
        model.addAttribute("subscribersCount", user.getSubscribers().size());
        model.addAttribute("mysubscribers", user.getSubscribers());
        model.addAttribute("subscriptions", user.getSubscriptions());
        model.addAttribute("isSubscriber", user.getSubscribers().contains(currentUser));
        model.addAttribute("url", "/user-publications/" + user.getId());
        model.addAttribute("pagesize", pagesize);
        return "userPublications";
    }


    @PostMapping("/user-publications/{user}")
    public String updatePublication(
            @AuthenticationPrincipal User currentUser,
            @PathVariable Long id,
            @RequestParam("id") Publication publication,
            @RequestParam("content") String content,
            @RequestParam("title") String title,
            @RequestParam("file") MultipartFile file
    ){
        if(publication.getAuthor().equals(currentUser)){
            if (!StringUtils.isEmpty(content)){
                publication.setContent(content);
            }

            if (!StringUtils.isEmpty(title)){
                publication.setTitle(title);
            }
            saveFile(publication, file);
            publicationRepository.save(publication);
        }
        return "redirect:/user-publications/" + id + "?page=0&size=" + pagesize;
    }

    @GetMapping("/editPublication/{user}")
    public String publicationEditPage (
            @AuthenticationPrincipal User currentUser,
            @PathVariable User user,
            @RequestParam("publicationId") Publication publication,
            Model model
    ){
        model.addAttribute("publication", publication);
        model.addAttribute("isCurrentUser", currentUser.equals(user));
        return "createPublicationPage";
    }
}