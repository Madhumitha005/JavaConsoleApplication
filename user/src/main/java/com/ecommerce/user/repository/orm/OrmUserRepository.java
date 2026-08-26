package com.ecommerce.user.repository.orm;

import java.util.Collection;

import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import jakarta.persistence.TypedQuery;

import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import com.ecommerce.user.entity.User;
import com.ecommerce.user.repository.UserRepository;

@Repository
@Transactional
public class OrmUserRepository implements UserRepository {

    @PersistenceContext
    private EntityManager entityManager;

    @Override
    public boolean save(final User user) {
        entityManager.persist(user);
        return true;
    }

    @Override
    public boolean update(final User user) {
        entityManager.merge(user);
        return true;
    }

    @Override
    public boolean delete(final Integer userId) {

        User entity = entityManager.find(User.class, userId);

        if (entity != null) {
            entityManager.remove(entity);
            return true;
        }

        return false;
    }

    @Override
    public User findById(final Integer userId) {
        return entityManager.find(User.class, userId);
    }

    @Override
    public User findByEmail(final String email) {

        TypedQuery<User> query = entityManager.createQuery(
                "SELECT e FROM User e WHERE e.email = :email",
                User.class
        );

        query.setParameter("email", email);

        Collection<User> results = query.getResultList();

        return results.isEmpty() ? null : results.iterator().next();
    }

    @Override
    public boolean existsByEmail(final String email) {

        TypedQuery<Long> query = entityManager.createQuery(
                "SELECT COUNT(e) FROM User e WHERE e.email = :email",
                Long.class
        );

        query.setParameter("email", email);

        return query.getSingleResult() > 0;
    }

    @Override
    public Collection<User> findAll() {

        return entityManager.createQuery(
                "SELECT e FROM User e",
                User.class
        ).getResultList();
    }
}