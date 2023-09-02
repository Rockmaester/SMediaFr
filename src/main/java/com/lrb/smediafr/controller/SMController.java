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
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;

import javax.validation.Valid;
import java.io.File;
import java.io.IOException;
import java.util.Map;
import java.util.UUID;


@Controller
@RequestMapping("/")
public class SMController {
    // TODO внедрить JSP Session - http://www.w3big.com/ru/jsp/jsp-session.html#gsc.tab=0

    @Value("C:/Users/r.litvinenko/OneDrive/J - Java/Projects/SocialMediaFront") // ищется в property upload.path и вставляет значение в переменную uploadPath
    private String uploadPath;

    @Autowired
    private Communication communication;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private PublicationRepository publicationRepository;

    @GetMapping("/")
    public String goToMain(@AuthenticationPrincipal User user, Model model) {

        Iterable<Publication> publications = publicationRepository.findAll();
        model.addAttribute("publications", publications);
        model.addAttribute("delimiter", "=====================================================");
        model.addAttribute("currentuser", user);

        return "mainPage";
    }

    @GetMapping("/createPublication")
    public String goToCreatePublicationPage(){
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

            model.addAttribute("publication", null);

            publicationRepository.save(publication);
        }

        Iterable<Publication> publications = publicationRepository.findAll();
        model.addAttribute("publications", publications);
        model.addAttribute("delimiter", "============================================================");

        return "mainPage";
    }





























/*
    @RequestMapping("/login")
    public String showLoginPage(){
        return "loginPage";
    }


    @RequestMapping("/addNewUser")
    public String addNewUser(Model model){
        User user = new User();
        model.addAttribute("user", user);
        return "registrationPage";
    }

    @RequestMapping("/saveNewUser")
    public String saveNewUser(@ModelAttribute("user") User user){
        communication.saveOrUpdate(user);
        return "mainWorkPage";
    }

    @RequestMapping("/checkLogin")
    public String checkLogin(@RequestParam("userName") String name, Model model*//*, RedirectAttributes redirectAttributes*//*){
        String result = "";
//        List<User> users = communication.getAllUsers();
        List<User> users = userRepository.findAll();
        User existingUser = null;
//        UserSession userSession = null;
        for(User obj:users){
            String objName = obj.getUsername();
            if(objName.equals(name)){
                existingUser = obj;
                userSession = new UserSession(existingUser);
//                result = "mainWorkPage"; // !!! * -> вынесено отсюда
                result = "redirect:/showMainPage";
//                result = "showMainPage";
                break;
            } else {
                result = "redirect:/wrongLogin";
            }
        }

        model.addAttribute("currentUser", existingUser);
        model.addAttribute("userSession", userSession);

//        redirectAttributes.addFlashAttribute("user", existingUser);
//        redirectAttributes.addFlashAttribute("session", userSession);

//        System.out.println("from method checkLogin: Session.name:    " + userSession.getUser().getName()); //TODO //////////////////////////////////////////////////////////////////////////////
//        System.out.println("from method checkLogin: userSession:    " + userSession); //TODO //////////////////////////////////////////////////////////////////////////////
//        System.out.println("userSessions: " + userSessions); // TODO ////////////////////////////////////////////////////////////////////////////////////

//        return "mainWorkPage";
        return result;
    }

    @RequestMapping("/showMainPage")
    public String showMainPage(Model model){
        // !!! здесь вынесено отдельно из-под метода checkLogin ->*, т.к. при вызове из формы регистрации нового
        // поста ссылки на возврат на mainPage выскакивала ошибка. Можно проверить по другому - если при логине повторно
        // нажать enter и страница сбросится, значит на нее невозможно будет вернуться и из нижестоящих страниц
        // TODO https://for-each.dev/lessons/b/-spring-redirect-and-forward /// Руководство по Spring Redirects /// https://www.evernote.com/shard/s709/u/0/sh/141b7ef3-9af4-4b2a-b75c-0d9b3ccf4b72/nfghT7vZaYK3DCFF48-WG7AL23Z6DYj-LcjjEIl6bzcrYVH1lnwK7rFGnA

        model.addAttribute("userSession", userSession);
        return "mainWorkPage";
    }

    @RequestMapping("/logOut")
    public String logOut(){
//        return "loginPage";
        return "redirect:/login";
    }

    @RequestMapping("wrongLogin")
    public String wrongLogin(){
        return "wrongLoginPage";
    }

    @PostMapping("createNewPublication")
    public String addNewPublication(Model model){
        Publication publication = new Publication();
        model.addAttribute("publication", publication);
        return "createNewPostPage";
    }

    *//*
    TODO - Хранение картинок в БД
     * https://qna.habr.com/q/462499
     * https://www.8host.com/blog/kak-xranit-izobrazheniya-v-mysql-s-pomoshhyu-blob/
     * https://phpdes.com/sql/hranenie-izobrazhenijj-v-baze-dannyh-mysql/#:~:text=%D0%94%D0%BB%D1%8F%20%D1%85%D1%80%D0%B0%D0%BD%D0%B5%D0%BD%D0%B8%D1%8F%20%D0%B8%D0%B7%D0%BE%D0%B1%D1%80%D0%B0%D0%B6%D0%B5%D0%BD%D0%B8%D0%B9%20%D0%B2%20%D0%B1%D0%B0%D0%B7%D0%B5,%D0%BC%D0%BE%D0%B6%D0%B5%D1%82%20%D1%85%D1%80%D0%B0%D0%BD%D0%B8%D1%82%D1%8C%20%D0%B4%D0%BE%20255%20%D0%B1%D0%B0%D0%B9%D1%82
     * Загрузка файлов - https://coderlessons.com/tutorials/java-tekhnologii/vyuchi-jsp/jsp-zagruzka-failov
    *//*

    @PostMapping("/savePost")
    public String savePost(@ModelAttribute("publication") Publication publication, Model model){

        System.out.println(publication.getName());
        System.out.println(publication.getContent());
        Object object = publication.getImage();
//        Image image = publication.getImage();
//        Graphics graphics = image.getGraphics();

        byte[] data = SerializationUtils.serialize(object);

        System.out.println(data.length);


        model.addAttribute("publicationName", publication.getName());
        model.addAttribute("publicationText", publication.getContent());
        model.addAttribute("publicationImage", publication.getImage());

//        return "redirect:/showMainPage";
        return "mainWorkPage";
    }*/


}
