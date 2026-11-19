package vn.iotstar.service.impl;

import java.time.LocalDateTime;
import java.util.List;

import vn.iotstar.dao.VideoDao;
import vn.iotstar.dao.impl.VideoDaoImpl;
import vn.iotstar.entity.Video;
import vn.iotstar.service.VideoService;
import vn.iotstar.util.UploadUtil;

public class VideoServiceImpl implements VideoService {
    private final VideoDao dao = new VideoDaoImpl();

    @Override
    public void insert(Video video) {
        if (video.getCreatedDate() == null) {
            video.setCreatedDate(LocalDateTime.now());
        }
        dao.insert(video);
    }

    @Override
    public void edit(Video in) {
        Video old = dao.get(in.getId());
        if (old == null) return;
        old.setTitle(in.getTitle());
        old.setDescription(in.getDescription());
        old.setActive(in.isActive());
        old.setCategory(in.getCategory());
        if (in.getPoster() != null) {
            UploadUtil.deleteFile(old.getPoster());
            old.setPoster(in.getPoster());
        }
        if (in.getVideoUrl() != null) {
            UploadUtil.deleteFile(old.getVideoUrl());
            old.setVideoUrl(in.getVideoUrl());
        }
        dao.update(old);
    }

    @Override
    public void delete(int id) {
        Video v = dao.get(id);
        if (v == null) return;
        dao.delete(id);
        UploadUtil.deleteFile(v.getPoster());
        UploadUtil.deleteFile(v.getVideoUrl());
    }

    @Override
    public Video get(int id) {
        return dao.get(id);
    }

    @Override
    public List<Video> getNewest(int limit) {
        return dao.findNewest(limit);
    }

    @Override
    public List<Video> getByUser(int userId, int page, int pageSize) {
        return dao.findByUser(userId, page, pageSize);
    }

    @Override
    public long countByUser(int userId) {
        return dao.countByUser(userId);
    }

    @Override
    public void increaseViews(int id) {
        dao.increaseViews(id);
    }
}
