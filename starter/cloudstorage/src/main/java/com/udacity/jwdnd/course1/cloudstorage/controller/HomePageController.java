package com.udacity.jwdnd.course1.cloudstorage.controller;

import com.udacity.jwdnd.course1.cloudstorage.mapper.UserMapper;
import com.udacity.jwdnd.course1.cloudstorage.model.User;
import com.udacity.jwdnd.course1.cloudstorage.services.*;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;

@Controller
public class HomePageController {
    private final UserMapper userMapper;
    private final FileService fileService;
    private final NoteService noteService;
    private final UserService userService;
    private final CredentialsService credentialService;
    private final EncryptionService encryptionService;

    public HomePageController(
            UserMapper userMapper,
            UserService userService,
            FileService fileService,
            NoteService noteService,
            CredentialsService credentialService,
            EncryptionService encryptionService) {
        this.userMapper = userMapper;
        this.userService = userService;
        this.fileService = fileService;
        this.noteService = noteService;
        this.credentialService = credentialService;
        this.encryptionService = encryptionService;
    }

    @GetMapping("")
    public String homepage() {
        return "redirect:/login";
    }
    
    @GetMapping("/home")
    public String home(Authentication auth, Model model) {
        String loggedInUserName = (String) auth.getPrincipal();
        User user = userMapper.getUser(loggedInUserName);

        model.addAttribute("files", fileService.getFileByUserId(user.getUserId()));
        model.addAttribute("notes", noteService.getNoteByUserId(user.getUserId()));
        model.addAttribute("credentials", credentialService.getCredentialsByUserId(user.getUserId()));
        model.addAttribute("encryptionService", encryptionService);
        return "home";
    }

    @GetMapping("/login")
    public String showLogin() {
        return "login";
    }

    @GetMapping("/signup")
    public String showSignup() {
        return "signup";
    }

    @PostMapping("/signup")
    public String signup(@ModelAttribute User user) {
        if (!userService.userNameExisted(user.getUsername())) {
            throw new RuntimeException("Username existed");
        }
        try {
            userService.createUser(user);
            return "redirect:/signup?success";
        } catch (Exception e) {
            return "redirect:/signup?fail";
        }
    }
}
