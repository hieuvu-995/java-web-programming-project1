package com.udacity.jwdnd.course1.cloudstorage.services;

import com.udacity.jwdnd.course1.cloudstorage.mapper.CredentialsMapper;
import com.udacity.jwdnd.course1.cloudstorage.model.Credentials;
import org.springframework.stereotype.Service;

import java.security.SecureRandom;
import java.util.Base64;
import java.util.List;

@Service
public class CredentialsService {
    private CredentialsMapper credentialsMapper;
    private EncryptionService encryptionService;

    public CredentialsService(CredentialsMapper credentialsMapper, EncryptionService encryptionService) {
        this.credentialsMapper = credentialsMapper;
        this.encryptionService = encryptionService;
    }

    public List<Credentials> getCredentialsByUserId(int userid){
        return credentialsMapper.getCredentialByUserId(userid);
    }

    public void addCredentials(Credentials credentials){
        credentialsMapper.insertCredentilas(credentials);
    }

    public void deleteCredentialById(int credentialid){credentialsMapper.deleteCredentilas(credentialid);
    }

    public void editCredentials(Credentials credentials){
        credentialsMapper.updateCredentilas(credentials);
    }
}
