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

    public boolean isUsernameAvailable(String username) {
        return userMapper.getUser(username) == null;
    }

    public int createUser(User user) {
        String encodedSalt = generateSalt();
        String hashedPassword = hashService.getHashedValue(user.getPassword(), encodedSalt);
        User newUser = new User(null, user.getUsername(), encodedSalt, hashedPassword, user.getFirstName(), user.getLastName());
        return userMapper.insert(newUser);
    }

    public User getUser(String username) {
        return userMapper.getUser(username);
    }

    public User getUser(Integer userId) {
        return userMapper.getUserById(userId);
    }

    // New method to generate salt
    private String generateSalt() {
        SecureRandom random = new SecureRandom();
        byte[] salt = new byte[16];
        random.nextBytes(salt);
        return Base64.getEncoder().encodeToString(salt);
    }
}
