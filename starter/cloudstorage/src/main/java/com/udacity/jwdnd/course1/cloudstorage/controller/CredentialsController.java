package com.udacity.jwdnd.course1.cloudstorage.controller;

import com.udacity.jwdnd.course1.cloudstorage.mapper.UserMapper;
import com.udacity.jwdnd.course1.cloudstorage.model.Credentials;
import com.udacity.jwdnd.course1.cloudstorage.model.User;
import com.udacity.jwdnd.course1.cloudstorage.services.CredentialsService;
import com.udacity.jwdnd.course1.cloudstorage.services.EncryptionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.security.SecureRandom;
import java.util.Base64;

@Controller
@RequestMapping("/credential")
public class CredentialsController {
    @Autowired
    private CredentialsService credentialsService;
    @Autowired
    private UserMapper userMapper;
    @Autowired
    private EncryptionService encryptionService;
    @PostMapping
    public String handleAddUpdateCredentials(Authentication authentication, Credentials credential){
        User currentUser = userMapper.getUser(authentication.getName());
        int userId = currentUser.getUserId();

        SecureRandom random = new SecureRandom();
        byte[] key = new byte[16];
        random.nextBytes(key);
        String encodeKey = Base64.getEncoder().encodeToString(key);
        String encryptedPassword = encryptionService.encryptValue(credential.getPassword(), encodeKey);
        credential.setKey(encodeKey);
        credential.setPassword(encryptedPassword);
        
        if (credential.getCredentialid() == 0) {
            credential.setUserid(userId);
            credentialsService.addCredentials(credential);
        } else {
            credentialsService.editCredentials(credential);
        }

        return "redirect:/result?success";
    }

    @GetMapping("/delete/{id}")
    public String deleteCredential(@PathVariable int id) {
        credentialsService.deleteCredentialById(id);
        return "redirect:/result?success";
    }
}
