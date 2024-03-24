package com.udacity.jwdnd.course1.cloudstorage.controller;

import com.udacity.jwdnd.course1.cloudstorage.mapper.UserMapper;
import com.udacity.jwdnd.course1.cloudstorage.model.Credentials;
import com.udacity.jwdnd.course1.cloudstorage.model.User;
import com.udacity.jwdnd.course1.cloudstorage.services.CredentialsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

@RequestMapping("/credentials")
@Controller
public class CredentialsController {
    @Autowired
    private CredentialsService credentialsService;
    @Autowired
    private UserMapper userMapper;

    @PostMapping
    public String handleAddUpdateCredentials(Authentication authentication, Credentials credentials){
        String loggedInUserName = (String) authentication.getPrincipal();
        User user = userMapper.getUser(loggedInUserName);
        int userId = user.getUserId();

        if (credentials.getCredentialid() == 0) {
            credentialsService.editCredentials(credentials);
        } else {
            credentialsService.addCredentials(credentials, userId);
        }

        return "redirect:/result?success";
    }

    @GetMapping("/delete")
    public String deleteCredentials(@RequestParam("id") int credentialid){
        credentialsService.deleteCredentials(credentialid);
        return "redirect:/result?success";
    }
}
