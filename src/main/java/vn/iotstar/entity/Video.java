package vn.iotstar.entity;

import java.io.Serializable;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotEmpty;

@Entity
@Table(name = "Videos")
@NamedQuery(name = "Video.findAll", query = "SELECT v FROM Video v")
public class Video implements Serializable {
    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private int id;

    @Column(name = "title", columnDefinition = "NVARCHAR(255) NOT NULL")
    @NotEmpty(message = "Tiêu đề không được rỗng")
    private String title;

    @Column(name = "description", columnDefinition = "NVARCHAR(MAX) NULL")
    private String description;

    /** Tên file ảnh đại diện (lưu trong thư mục upload). */
    @Column(name = "poster", columnDefinition = "NVARCHAR(255) NULL")
    private String poster;

    /** Tên file video đã upload, hoặc đường dẫn http(s) (YouTube, link mp4...). */
    @Column(name = "video_url", columnDefinition = "NVARCHAR(500) NULL")
    private String videoUrl;

    @Column(name = "views")
    private int views;

    @Column(name = "active")
    private boolean active = true;

    @Column(name = "created_date")
    private LocalDateTime createdDate;

    // n video thuộc 1 user
    @ManyToOne
    @JoinColumn(name = "user_id", nullable = false)
    private User user;

    // n video thuộc 1 category (không bắt buộc)
    @ManyToOne
    @JoinColumn(name = "cate_id")
    private Category category;

    public Video() {
    }

    /** Dùng cho JSP (không lưu DB vì entity dùng field access). */
    public String getCreatedDateText() {
        return createdDate == null ? "" : createdDate.format(DateTimeFormatter.ofPattern("dd/MM/yyyy HH:mm"));
    }

    public int getId() { return id; }
    public void setId(int id) { this.id = id; }
    public String getTitle() { return title; }
    public void setTitle(String title) { this.title = title; }
    public String getDescription() { return description; }
    public void setDescription(String description) { this.description = description; }
    public String getPoster() { return poster; }
    public void setPoster(String poster) { this.poster = poster; }
    public String getVideoUrl() { return videoUrl; }
    public void setVideoUrl(String videoUrl) { this.videoUrl = videoUrl; }
    public int getViews() { return views; }
    public void setViews(int views) { this.views = views; }
    public boolean isActive() { return active; }
    public void setActive(boolean active) { this.active = active; }
    public LocalDateTime getCreatedDate() { return createdDate; }
    public void setCreatedDate(LocalDateTime createdDate) { this.createdDate = createdDate; }
    public User getUser() { return user; }
    public void setUser(User user) { this.user = user; }
    public Category getCategory() { return category; }
    public void setCategory(Category category) { this.category = category; }
}
