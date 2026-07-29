package com.ecommerce.repository;

import com.ecommerce.model.User;

public interface UserRepository {

    boolean save(final User user);

    boolean update(final User user);

    boolean delete(final int userId);

    User findById(final int userId);

    User findByEmail(final String email);

    boolean existsByEmail(final String email);
}