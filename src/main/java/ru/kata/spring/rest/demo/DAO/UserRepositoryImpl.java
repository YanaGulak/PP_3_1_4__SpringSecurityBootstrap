package ru.kata.spring.rest.demo.DAO;

import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Repository;
import ru.kata.spring.rest.demo.model.User;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import java.util.List;

@Repository
public class UserRepositoryImpl implements UserRepository {

    @PersistenceContext
    private EntityManager entityManager;

    @Override
    public List<User> findAll() {
        return entityManager.createQuery("from User", User.class).getResultList();
    }

    @Override
    public User findUserById(Long id) {
        return entityManager.find(User.class, id);
    }

    @Override
    public User findByUsername(String username) {
        String query = "Select u from User u left join fetch u.roles where u.username=:username";
        User user = entityManager.createQuery(query, User.class).setParameter("username", username).getSingleResult();
        if (user == null) {
            throw new UsernameNotFoundException("User " + username + " not found");
        }
        return user;
    }

    @Override
    public void updateUser(User updateUser) {
        entityManager.merge(updateUser);
    }

    @Override
    public void saveUser(User user) {
        entityManager.persist(user); // сохраняем сущность User
    }

    @Override
    public void deleteUserById(long id) {
        entityManager.remove(findUserById(id));
    }
}
