package vn.iotstar.controller;

import java.io.IOException;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.*;
import vn.iotstar.entity.User;
import vn.iotstar.entity.Video;
import vn.iotstar.service.VideoService;
import vn.iotstar.service.impl.VideoServiceImpl;
import vn.iotstar.util.Constant;
import vn.iotstar.util.VideoUtil;

/** /watch?id=... : chi tiết và phát video (công khai, từ trang chủ hoặc /video). */
@WebServlet(urlPatterns = { "/watch" })
public class VideoWatchController extends HttpServlet {
    private static final long serialVersionUID = 1L;
    private final VideoService videoService = new VideoServiceImpl();

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        Video v = null;
        try {
            v = videoService.get(Integer.parseInt(req.getParameter("id")));
        } catch (NumberFormatException ignored) {
        }
        HttpSession session = req.getSession(false);
        User account = session == null ? null : (User) session.getAttribute(Constant.SESSION_ACCOUNT);

        // video bị ẩn chỉ chủ video / admin mới xem được
        if (v == null || (!v.isActive() && !VideoEditController.canModify(account, v))) {
            resp.sendRedirect(req.getContextPath() + "/home");
            return;
        }

        videoService.increaseViews(v.getId());
        v.setViews(v.getViews() + 1);

        String url = v.getVideoUrl();
        boolean isLink = VideoUtil.isHttp(url);
        req.setAttribute("video", v);
        req.setAttribute("isLink", isLink);
        req.setAttribute("embedUrl", isLink ? VideoUtil.youtubeEmbed(url) : null);
        req.getRequestDispatcher("/WEB-INF/views/video/watch.jsp").forward(req, resp);
    }
}
