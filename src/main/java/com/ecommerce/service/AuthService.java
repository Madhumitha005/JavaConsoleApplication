package com.ecommerce.service;

import com.ecommerce.exception.AuthenticationException;
import com.ecommerce.model.User;
import com.ecommerce.repository.UserRepository;
import com.ecommerce.util.PasswordUtil;
import com.ecommerce.util.StringUtil;

public class AuthService {

    private final UserRepository memoryRepository;
    private final UserRepository jdbcRepository;
    private final PasswordUtil passwordUtil;
    private final StringUtil stringUtil;

    // Constructor Injection
    public AuthService(UserRepository memoryRepository,
                       UserRepository jdbcRepository) {

        this.memoryRepository = memoryRepository;
        this.jdbcRepository = jdbcRepository;
        this.passwordUtil = PasswordUtil.getInstance();
        this.stringUtil = StringUtil.getInstance();
    }

    // SIGNUP
    public boolean signup(User user) {

        if (user == null) {
            throw new NullPointerException("User cannot be null.");
        }

        user.setName(stringUtil.clean(user.getName()));
        user.setEmail(stringUtil.cleanEmail(user.getEmail()));
        user.setPassword(passwordUtil.hash(user.getPassword()));

        // Save to Memory
        boolean memorySaved = memoryRepository.save(user);

        // Save to Database
        boolean jdbcSaved = jdbcRepository.save(user);

        return memorySaved && jdbcSaved;
    }

    // LOGIN
    public User login(User user) {

        if (user == null) {
            throw new NullPointerException("User cannot be null.");
        }

        String email = stringUtil.cleanEmail(user.getEmail());

        // Check Memory first
        User existingUser = memoryRepository.findByEmail(email);

        // If not found, check Database
        if (existingUser == null) {
            existingUser = jdbcRepository.findByEmail(email);

        }

        if (existingUser == null) {
            throw new AuthenticationException("User not found.");
        }

        String enteredPassword = passwordUtil.hash(user.getPassword());

        if (!existingUser.getPassword().equals(enteredPassword)) {
            throw new AuthenticationException("Invalid Password.");
        }

        return existingUser;
    }

    // Get User By Email
    public User getUserByEmail(String email) {

        if (email == null) {
            throw new NullPointerException("Email cannot be null.");
        }

        email = stringUtil.cleanEmail(email);

        User user = memoryRepository.findByEmail(email);

        if (user == null) {
            user = jdbcRepository.findByEmail(email);
        }

        return user;
    }
}