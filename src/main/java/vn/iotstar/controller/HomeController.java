package vn.iotstar.controller;

import java.io.IOException;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.*;
import vn.iotstar.service.VideoService;
import vn.iotstar.service.impl.VideoServiceImpl;

@WebServlet(urlPatterns = { "/home", "/admin/home", "/manager/home" })
public class HomeController extends HttpServlet {
    private static final long serialVersionUID = 1L;
    private final VideoService videoService = new VideoServiceImpl();

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String path = req.getServletPath();
        String view = "/WEB-INF/views/home.jsp";
        if (path.startsWith("/admin")) {
            view = "/WEB-INF/views/admin/home.jsp";
        } else if (path.startsWith("/manager")) {
            view = "/WEB-INF/views/manager/home.jsp";
        } else {
            // Trang chủ: 10 video mới nhất
            req.setAttribute("videos", videoService.getNewest(10));
        }
        req.getRequestDispatcher(view).forward(req, resp);
    }
}
