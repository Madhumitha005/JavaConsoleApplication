package com.ecommerce.service;

import com.ecommerce.common.exception.AuthenticationException;
import com.ecommerce.model.User;
import com.ecommerce.repository.UserRepository;
import com.ecommerce.common.util.PasswordUtil;
import com.ecommerce.common.util.StringUtil;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

@Service
public class  AuthService {

    private final UserRepository memoryRepository;
    private final UserRepository jdbcRepository;
    private final PasswordUtil passwordUtil;
    private final StringUtil stringUtil;

    public AuthService(

            @Qualifier("inMemoryUserRepository")
            final UserRepository memoryRepository,

            @Qualifier("jdbcUserRepository")
            final UserRepository jdbcRepository,

            final PasswordUtil passwordUtil,
            final StringUtil stringUtil) {

        this.memoryRepository = memoryRepository;
        this.jdbcRepository = jdbcRepository;
        this.passwordUtil = passwordUtil;
        this.stringUtil = stringUtil;
    }

    // Signup
    public boolean signup(final User user) {

        if (user == null) {
            throw new NullPointerException("User cannot be null.");
        }

        user.setName(stringUtil.clean(user.getName()));
        user.setEmail(stringUtil.cleanEmail(user.getEmail()));
        user.setPassword(passwordUtil.hash(user.getPassword()));
        User existingUser = memoryRepository.findByEmail(user.getEmail());

        if (existingUser == null) {
            existingUser = jdbcRepository.findByEmail(user.getEmail());
        }

        if (existingUser != null) {
            throw new AuthenticationException("Email already exists.");
        }

        boolean memorySaved = memoryRepository.save(user);
        boolean jdbcSaved = jdbcRepository.save(user);

        return memorySaved && jdbcSaved;
    }

    // Login
    public User login(final User user) {

        if (user == null) {
            throw new NullPointerException("User cannot be null.");
        }

        String email = stringUtil.cleanEmail(user.getEmail());
        User existingUser = memoryRepository.findByEmail(email);

        if (existingUser == null) {
            existingUser = jdbcRepository.findByEmail(email);
        }

        if (existingUser == null) {
            throw new AuthenticationException("User not found.");
        }

        String enteredPassword = passwordUtil.hash(user.getPassword());

        if (!existingUser.getPassword().equals(enteredPassword)) {
            throw new AuthenticationException("Invalid password.");
        }

        return existingUser;
    }

    // Get User By Email
    public User getUserByEmail(final String email) {

        if (email == null) {
            throw new NullPointerException("Email cannot be null.");
        }

        String cleanEmail = stringUtil.cleanEmail(email);
        User user = memoryRepository.findByEmail(cleanEmail);

        if (user == null) {
            user = jdbcRepository.findByEmail(cleanEmail);
        }

        return user;
    }
}