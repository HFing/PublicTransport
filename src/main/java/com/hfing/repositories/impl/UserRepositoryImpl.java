package com.hfing.repositories.impl;

import com.hfing.pojo.User;
import com.hfing.repositories.UserRepository;
import jakarta.persistence.Query;
import org.hibernate.Session;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.orm.hibernate5.LocalSessionFactoryBean;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Repository
@Transactional
public class UserRepositoryImpl implements UserRepository {
    @Autowired
    private LocalSessionFactoryBean factory;
    @Autowired
    private BCryptPasswordEncoder passwordEncoder;

    @Override
    public User addUser(User u) {
        Session s = this.factory.getObject().getCurrentSession();
        s.persist(u);

        return u;
    }

    @Override
    public boolean existsByEmail(String email) {
        Session s = this.factory.getObject().getCurrentSession();
        String hql = "SELECT COUNT(u) FROM User u WHERE u.email = :email";
        Long count = (Long) s.createQuery(hql)
                .setParameter("email", email)
                .uniqueResult();
        return count != null && count > 0;
    }

    @Override
    public User getUserByUsername(String username) {
        Session s = this.factory.getObject().getCurrentSession();
        Query q = s.createNamedQuery("User.findByEmail", User.class);
        q.setParameter("email", username);

        return (User) q.getSingleResult();
    }

    @Override
    public List<User> getUsers() {
        Session s = this.factory.getObject().getCurrentSession();
        Query q = s.createQuery("FROM User", User.class);
        return q.getResultList();
    }

    @Override
    public User getUserById(int id) {
        Session session = factory.getObject().getCurrentSession();
        return session.get(User.class, id);
    }

    @Override
    public void deleteUser(User user) {
        Session session = factory.getObject().getCurrentSession();
        session.delete(user);
    }

    @Override
    public User updateUser(User u) {
        Session s = factory.getObject().getCurrentSession();
        s.update(u);
        return u;
    }
}
