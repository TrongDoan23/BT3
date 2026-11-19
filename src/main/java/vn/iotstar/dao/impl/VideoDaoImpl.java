package vn.iotstar.dao.impl;

import java.util.List;

import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityTransaction;
import jakarta.persistence.TypedQuery;
import vn.iotstar.configs.JPAConfig;
import vn.iotstar.dao.VideoDao;
import vn.iotstar.entity.Video;

public class VideoDaoImpl implements VideoDao {
    private static final String FETCH = "SELECT v FROM Video v JOIN FETCH v.user LEFT JOIN FETCH v.category ";

    @Override
    public void insert(Video video) {
        EntityManager em = JPAConfig.getEntityManager();
        EntityTransaction trans = em.getTransaction();
        try {
            trans.begin();
            em.persist(video);
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
    public void update(Video video) {
        EntityManager em = JPAConfig.getEntityManager();
        EntityTransaction trans = em.getTransaction();
        try {
            trans.begin();
            em.merge(video);
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
    public void delete(int id) {
        EntityManager em = JPAConfig.getEntityManager();
        EntityTransaction trans = em.getTransaction();
        try {
            trans.begin();
            Video v = em.find(Video.class, id);
            if (v != null) em.remove(v);
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
    public Video get(int id) {
        EntityManager em = JPAConfig.getEntityManager();
        try {
            TypedQuery<Video> q = em.createQuery(FETCH + "WHERE v.id = :id", Video.class);
            q.setParameter("id", id);
            List<Video> list = q.getResultList();
            return list.isEmpty() ? null : list.get(0);
        } finally {
            em.close();
        }
    }

    @Override
    public List<Video> findNewest(int limit) {
        EntityManager em = JPAConfig.getEntityManager();
        try {
            TypedQuery<Video> q = em.createQuery(
                    FETCH + "WHERE v.active = true ORDER BY v.createdDate DESC, v.id DESC", Video.class);
            q.setMaxResults(limit);
            return q.getResultList();
        } finally {
            em.close();
        }
    }

    @Override
    public List<Video> findByUser(int userId, int page, int pageSize) {
        EntityManager em = JPAConfig.getEntityManager();
        try {
            TypedQuery<Video> q = em.createQuery(
                    FETCH + "WHERE v.user.id = :uid ORDER BY v.createdDate DESC, v.id DESC", Video.class);
            q.setParameter("uid", userId);
            q.setFirstResult((page - 1) * pageSize);
            q.setMaxResults(pageSize);
            return q.getResultList();
        } finally {
            em.close();
        }
    }

    @Override
    public long countByUser(int userId) {
        EntityManager em = JPAConfig.getEntityManager();
        try {
            TypedQuery<Long> q = em.createQuery("SELECT COUNT(v) FROM Video v WHERE v.user.id = :uid", Long.class);
            q.setParameter("uid", userId);
            return q.getSingleResult();
        } finally {
            em.close();
        }
    }

    @Override
    public void increaseViews(int id) {
        EntityManager em = JPAConfig.getEntityManager();
        EntityTransaction trans = em.getTransaction();
        try {
            trans.begin();
            em.createQuery("UPDATE Video v SET v.views = v.views + 1 WHERE v.id = :id")
                    .setParameter("id", id).executeUpdate();
            trans.commit();
        } catch (RuntimeException e) {
            e.printStackTrace();
            if (trans.isActive()) trans.rollback();
        } finally {
            em.close();
        }
    }
}
