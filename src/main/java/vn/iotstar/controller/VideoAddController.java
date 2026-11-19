package vn.iotstar.controller;

import java.io.IOException;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.MultipartConfig;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.*;
import vn.iotstar.entity.Category;
import vn.iotstar.entity.User;
import vn.iotstar.entity.Video;
import vn.iotstar.service.CategoryService;
import vn.iotstar.service.VideoService;
import vn.iotstar.service.impl.CategoryServiceImpl;
import vn.iotstar.service.impl.VideoServiceImpl;
import vn.iotstar.util.Constant;
import vn.iotstar.util.UploadUtil;
import vn.iotstar.util.VideoUtil;

@WebServlet(urlPatterns = { "/video/add" })
@MultipartConfig(fileSizeThreshold = 1024 * 1024, maxFileSize = 1024L * 1024 * 200, maxRequestSize = 1024L * 1024 * 220)
public class VideoAddController extends HttpServlet {
    private static final long serialVersionUID = 1L;
    private static final String VIEW = "/WEB-INF/views/video/form-video.jsp";
    private final VideoService videoService = new VideoServiceImpl();
    private final CategoryService categoryService = new CategoryServiceImpl();

    private void showForm(HttpServletRequest req, HttpServletResponse resp, String error)
            throws ServletException, IOException {
        req.setAttribute("formAction", req.getContextPath() + "/video/add");
        req.setAttribute("formTitle", "Thêm video");
        req.setAttribute("categories", categoryService.getAll());
        if (error != null) req.setAttribute("alert", error);
        req.getRequestDispatcher(VIEW).forward(req, resp);
    }

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        showForm(req, resp, null);
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        req.setCharacterEncoding("UTF-8");
        User account = (User) req.getSession().getAttribute(Constant.SESSION_ACCOUNT);

        String title;
        String link;
        Part videoPart;
        Part posterPart;
        try {
            title = trim(req.getParameter("title"));
            link = trim(req.getParameter("videoLink"));
            videoPart = req.getPart("videoFile");
            posterPart = req.getPart("poster");
        } catch (IllegalStateException e) {
            showForm(req, resp, "File quá lớn (video tối đa 200MB).");
            return;
        }

        boolean hasFile = videoPart != null && videoPart.getSize() > 0;
        if (title.isEmpty()) {
            showForm(req, resp, "Tiêu đề không được rỗng");
            return;
        }
        if (!hasFile && link.isEmpty()) {
            showForm(req, resp, "Hãy chọn file video hoặc nhập link video.");
            return;
        }
        if (!hasFile && !VideoUtil.isHttp(link)) {
            showForm(req, resp, "Link video phải bắt đầu bằng http:// hoặc https://");
            return;
        }

        String videoUrl = link;
        if (hasFile) {
            videoUrl = UploadUtil.saveVideo(videoPart);
            if (videoUrl == null) {
                showForm(req, resp, "Định dạng video không hợp lệ (chỉ nhận mp4, webm, ogg).");
                return;
            }
        }

        Video video = new Video();
        video.setTitle(title);
        video.setDescription(trim(req.getParameter("description")));
        video.setVideoUrl(videoUrl);
        video.setPoster(UploadUtil.saveImage(posterPart));
        video.setActive("on".equals(req.getParameter("active")));
        video.setUser(account);
        video.setCategory(parseCategory(req.getParameter("cateid")));

        videoService.insert(video);
        resp.sendRedirect(req.getContextPath() + "/video");
    }

    private Category parseCategory(String s) {
        try {
            return (s == null || s.isBlank()) ? null : categoryService.get(Integer.parseInt(s));
        } catch (NumberFormatException e) {
            return null;
        }
    }

    private static String trim(String s) {
        return s == null ? "" : s.trim();
    }
}
