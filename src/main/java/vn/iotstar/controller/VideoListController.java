package vn.iotstar.controller;

import java.io.IOException;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.*;
import vn.iotstar.entity.User;
import vn.iotstar.service.VideoService;
import vn.iotstar.service.impl.VideoServiceImpl;
import vn.iotstar.util.Constant;

/** /video : tất cả video của user đang đăng nhập, 6 video / trang. */
@WebServlet(urlPatterns = { "/video" })
public class VideoListController extends HttpServlet {
    private static final long serialVersionUID = 1L;
    public static final int PAGE_SIZE = 6;
    private final VideoService videoService = new VideoServiceImpl();

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        User user = (User) req.getSession().getAttribute(Constant.SESSION_ACCOUNT);

        int page = 1;
        try {
            page = Math.max(1, Integer.parseInt(req.getParameter("page")));
        } catch (NumberFormatException ignored) {
        }
        long total = videoService.countByUser(user.getId());
        int totalPages = (int) Math.ceil(total / (double) PAGE_SIZE);
        if (totalPages > 0 && page > totalPages) page = totalPages;

        req.setAttribute("videos", videoService.getByUser(user.getId(), page, PAGE_SIZE));
        req.setAttribute("currentPage", page);
        req.setAttribute("totalPages", totalPages);
        req.setAttribute("total", total);
        req.getRequestDispatcher("/WEB-INF/views/video/list-video.jsp").forward(req, resp);
    }
}
