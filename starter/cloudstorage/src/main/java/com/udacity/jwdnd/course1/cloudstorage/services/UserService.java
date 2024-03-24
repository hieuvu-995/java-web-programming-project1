package com.udacity.jwdnd.course1.cloudstorage.services;

import com.udacity.jwdnd.course1.cloudstorage.mapper.UserMapper;
import com.udacity.jwdnd.course1.cloudstorage.model.User;
import org.springframework.stereotype.Service;

import java.security.SecureRandom;
import java.util.Base64;

@Service
public class UserService {
    private final UserMapper userMapper;
    private final HashService hashService;

    public UserService(UserMapper userMapper, HashService hashService) {
        this.userMapper = userMapper;
        this.hashService = hashService;
    }

    public boolean userNameExisted(String username) {
        return userMapper.getUser(username) == null;
    }

    public void createUser(User user) {
        SecureRandom random = new SecureRandom();
        byte[] key = new byte[16];
        random.nextBytes(key);
        String encodedKey = Base64.getEncoder().encodeToString(key);
        String hashedPassword = hashService.getHashedValue(user.getPassword(), encodedKey);
        user.setPassword(hashedPassword);
        user.setSalt(encodedKey);
        userMapper.insert(user);
    }

    public User getUserByUsername(String username) {
        return userMapper.getUser(username);
    }
}
