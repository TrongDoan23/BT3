package vn.iotstar.dao.impl;

import java.util.List;

import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityTransaction;
import jakarta.persistence.TypedQuery;
import vn.iotstar.configs.JPAConfig;
import vn.iotstar.dao.UserDao;
import vn.iotstar.entity.User;

public class UserDaoImpl implements UserDao {

    @Override
    public void insert(User user) {
        EntityManager em = JPAConfig.getEntityManager();
        EntityTransaction trans = em.getTransaction();
        try {
            trans.begin();
            em.persist(user);
            trans.commit();
        } catch (RuntimeException e) {
            e.printStackTrace();
            if (trans.isActive()) trans.rollback();
            throw e;
        } finally {
            em.close();
        }
    }

    @Override
    public void update(User user) {
        EntityManager em = JPAConfig.getEntityManager();
        EntityTransaction trans = em.getTransaction();
        try {
            trans.begin();
            em.merge(user);
            trans.commit();
        } catch (RuntimeException e) {
            e.printStackTrace();
            if (trans.isActive()) trans.rollback();
            throw e;
        } finally {
            em.close();
        }
    }

    @Override
    public User get(String username) {
        return findOne("userName", username);
    }

    @Override
    public User getByEmail(String email) {
        return findOne("email", email);
    }

    @Override
    public User getByRememberToken(String token) {
        return findOne("rememberToken", token);
    }

    @Override
    public boolean checkExistEmail(String email) {
        return exists("email", email);
    }

    @Override
    public boolean checkExistUsername(String username) {
        return exists("userName", username);
    }

    @Override
    public boolean checkExistPhone(String phone) {
        return exists("phone", phone);
    }

    private User findOne(String field, String value) {
        if (value == null) return null;
        EntityManager em = JPAConfig.getEntityManager();
        try {
            TypedQuery<User> q = em.createQuery("SELECT u FROM User u WHERE u." + field + " = :v", User.class);
            q.setParameter("v", value);
            q.setMaxResults(1);
            List<User> list = q.getResultList();
            return list.isEmpty() ? null : list.get(0);
        } finally {
            em.close();
        }
    }

    private boolean exists(String field, String value) {
        EntityManager em = JPAConfig.getEntityManager();
        try {
            TypedQuery<Long> q = em.createQuery("SELECT COUNT(u) FROM User u WHERE u." + field + " = :v", Long.class);
            q.setParameter("v", value);
            return q.getSingleResult() > 0;
        } finally {
            em.close();
        }
    }
}
