package vn.iotstar.configs;

import java.time.LocalDateTime;

import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityTransaction;
import vn.iotstar.entity.Category;
import vn.iotstar.entity.User;
import vn.iotstar.entity.Video;

/** Run As > Java Application để test cấu hình JPA (cần đã chạy sql/JPAShopping.sql hoặc update_bai03.sql). */
public class Test {
    public static void main(String[] args) {
        EntityManager enma = JPAConfig.getEntityManager();
        EntityTransaction trans = enma.getTransaction();
        try {
            trans.begin();
            User admin = enma.createQuery("SELECT u FROM User u WHERE u.userName = 'admin'", User.class)
                    .getSingleResult();

            Category cate = new Category();
            cate.setCatename("Iphone");
            cate.setIcon("avatar.png");

            Video video = new Video();
            video.setTitle("Video test JPA");
            video.setVideoUrl("https://www.youtube.com/watch?v=dQw4w9WgXcQ");
            video.setCreatedDate(LocalDateTime.now());
            video.setUser(admin);
            video.setCategory(cate);

            enma.persist(cate);
            enma.persist(video);
            trans.commit();
            System.out.println("OK: video id = " + video.getId());
        } catch (Exception e) {
            e.printStackTrace();
            if (trans.isActive()) trans.rollback();
            throw e;
        } finally {
            enma.close();
        }
        System.exit(0);
    }
}
