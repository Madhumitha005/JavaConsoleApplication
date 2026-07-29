package com.ecommerce.repository.memory;

import com.ecommerce.model.User;
import com.ecommerce.repository.UserRepository;
import com.ecommerce.common.util.IdGenerator;
import org.springframework.stereotype.Repository;
import java.util.ArrayList;
import java.util.Collection;

@Repository("inMemoryUserRepository")
public class InMemoryUserRepository implements UserRepository {

    private final Collection<User> users;

    public InMemoryUserRepository() {
        this.users = new ArrayList<>();
    }

    @Override
    public boolean save(final User user) {

        if (user == null) {
            return false;
        }

        if (existsByEmail(user.getEmail())) {
            return false;
        }

        user.setId(
                IdGenerator.getInstance().nextUserId()
        );

        return users.add(user);
    }

    @Override
    public boolean update(final User user) {

        if (user == null) {
            return false;
        }

        for (User existingUser : users) {

            if (existingUser.getId() == user.getId()) {

                existingUser.setName(user.getName());
                existingUser.setEmail(user.getEmail());
                existingUser.setPassword(user.getPassword());
                existingUser.setRole(user.getRole());

                return true;
            }
        }

        return false;
    }

    @Override
    public boolean delete(final int userId) {

        return users.removeIf(
                user -> user.getId() == userId
        );
    }

    @Override
    public User findById(final int userId) {

        for (User user : users) {

            if (user.getId() == userId) {
                return user;
            }
        }

        return null;
    }

    @Override
    public User findByEmail(final String email) {

        if (email == null) {
            return null;
        }

        for (User user : users) {

            if (user.getEmail() != null
                    && user.getEmail().equalsIgnoreCase(email)) {

                return user;
            }
        }

        return null;
    }

    @Override
    public boolean existsByEmail(final String email) {

        return findByEmail(email) != null;
    }
}