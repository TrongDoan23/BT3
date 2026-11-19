package vn.iotstar.service;

import java.util.List;

import vn.iotstar.entity.Video;

public interface VideoService {
    void insert(Video video);
    /** Cập nhật: poster/videoUrl = null nghĩa là giữ nguyên giá trị cũ. */
    void edit(Video video);
    void delete(int id);
    Video get(int id);
    List<Video> getNewest(int limit);
    List<Video> getByUser(int userId, int page, int pageSize);
    long countByUser(int userId);
    void increaseViews(int id);
}
