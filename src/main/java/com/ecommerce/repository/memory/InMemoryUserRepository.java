package com.ecommerce.repository.memory;

import com.ecommerce.model.User;
import com.ecommerce.repository.UserRepository;
import java.util.ArrayList;
import java.util.Collection;

public class InMemoryUserRepository implements UserRepository {

    // In-Memory Storage
    private final Collection<User> users = new ArrayList<>();

    // Save User
    @Override
    public boolean save(User user) {

        if (user == null) {
            return false;
        }

        if (findByEmail(user.getEmail()) != null) {
            return false;
        }
        return users.add(user);
    }

    // Find user by email
    @Override
    public User findByEmail(String email) {

        if (email == null) {
            return null;
        }

        for (User user : users) {

            if (user.getEmail() != null &&user.getEmail().equalsIgnoreCase(email)) {

                return user;
            }
        }
        return null;
    }

    // Get all users
    @Override
    public Collection<User> findAll() {

        return new ArrayList<>(users);
    }
}