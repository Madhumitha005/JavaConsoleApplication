package com.ecommerce.repository;

import com.ecommerce.model.User;
import java.util.Collection;

public interface UserRepository {

    boolean save(User user);

    User findByEmail(String email);

    Collection<User> findAll();
}