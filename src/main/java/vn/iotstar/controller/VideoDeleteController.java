package vn.iotstar.controller;

import java.io.IOException;

import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.*;
import vn.iotstar.entity.User;
import vn.iotstar.entity.Video;
import vn.iotstar.service.VideoService;
import vn.iotstar.service.impl.VideoServiceImpl;
import vn.iotstar.util.Constant;

/** Xóa bằng POST (tránh bị xóa nhầm qua link). */
@WebServlet(urlPatterns = { "/video/delete" })
public class VideoDeleteController extends HttpServlet {
    private static final long serialVersionUID = 1L;
    private final VideoService videoService = new VideoServiceImpl();

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws IOException {
        User account = (User) req.getSession().getAttribute(Constant.SESSION_ACCOUNT);
        try {
            Video v = videoService.get(Integer.parseInt(req.getParameter("id")));
            if (VideoEditController.canModify(account, v)) {
                videoService.delete(v.getId());
            }
        } catch (NumberFormatException ignored) {
        }
        resp.sendRedirect(req.getContextPath() + "/video");
    }

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws IOException {
        resp.sendRedirect(req.getContextPath() + "/video");
    }
}
