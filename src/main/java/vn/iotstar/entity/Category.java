package vn.iotstar.entity;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotEmpty;

@Entity
@Table(name = "Category")
@NamedQuery(name = "Category.findAll", query = "SELECT c FROM Category c")
public class Category implements Serializable {
    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "cate_id")
    private int cateid;

    @Column(name = "cate_name", columnDefinition = "NVARCHAR(255) NOT NULL")
    @NotEmpty(message = "Tên danh mục không được rỗng")
    private String catename;

    @Column(name = "icons", columnDefinition = "NVARCHAR(255) NULL")
    private String icon;

    // bi-directional many-to-one association to Video
    @OneToMany(mappedBy = "category")
    private List<Video> videos = new ArrayList<>();

    public Video addVideo(Video video) {
        videos.add(video);
        video.setCategory(this);
        return video;
    }

    public Video removeVideo(Video video) {
        videos.remove(video);
        video.setCategory(null);
        return video;
    }

    public Category() {
    }

    public int getCateid() {
        return cateid;
    }

    public void setCateid(int cateid) {
        this.cateid = cateid;
    }

    public String getCatename() {
        return catename;
    }

    public void setCatename(String catename) {
        this.catename = catename;
    }

    public String getIcon() {
        return icon;
    }

    public void setIcon(String icon) {
        this.icon = icon;
    }

    public List<Video> getVideos() {
        return videos;
    }

    public void setVideos(List<Video> videos) {
        this.videos = videos;
    }
}
