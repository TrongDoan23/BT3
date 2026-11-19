package vn.iotstar.controller;

import java.io.IOException;
import java.util.LinkedHashMap;
import java.util.Map;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.MultipartConfig;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.*;
import vn.iotstar.entity.Category;
import vn.iotstar.entity.User;
import vn.iotstar.entity.Video;
import vn.iotstar.form.VideoForm;
import vn.iotstar.service.CategoryService;
import vn.iotstar.service.VideoService;
import vn.iotstar.service.impl.CategoryServiceImpl;
import vn.iotstar.service.impl.VideoServiceImpl;
import vn.iotstar.util.Constant;
import vn.iotstar.util.UploadUtil;
import vn.iotstar.util.ValidationUtil;

@WebServlet(urlPatterns = { "/video/add" })
@MultipartConfig(fileSizeThreshold = 1024 * 1024, maxFileSize = 1024L * 1024 * 200, maxRequestSize = 1024L * 1024 * 220)
public class VideoAddController extends HttpServlet {
    private static final long serialVersionUID = 1L;
    private static final String VIEW = "/WEB-INF/views/video/form-video.jsp";
    private final VideoService videoService = new VideoServiceImpl();
    private final CategoryService categoryService = new CategoryServiceImpl();

    private void showForm(HttpServletRequest req, HttpServletResponse resp, Map<String, String> errors)
            throws ServletException, IOException {
        req.setAttribute("formAction", req.getContextPath() + "/video/add");
        req.setAttribute("formTitle", "Thêm video");
        req.setAttribute("categories", categoryService.getAll());
        if (errors != null) req.setAttribute("errors", errors);
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

        VideoForm form = new VideoForm(req.getParameter("title"), req.getParameter("description"),
                req.getParameter("videoLink"));
        Map<String, String> errors = new LinkedHashMap<>(ValidationUtil.validate(form));

        Part videoPart = null;
        Part posterPart = null;
        try {
            videoPart = req.getPart("videoFile");
            posterPart = req.getPart("poster");
        } catch (IllegalStateException e) {
            errors.put("videoFile", "File quá lớn (video tối đa 200MB, ảnh tối đa 200MB tổng cộng)");
        }

        boolean hasFile = UploadUtil.hasFile(videoPart);
        if (!errors.containsKey("videoFile")) {
            if (!UploadUtil.isAllowed(videoPart, UploadUtil.VIDEO_EXT)) {
                errors.put("videoFile", "Video chỉ nhận định dạng mp4, webm, ogg");
            } else if (!hasFile && form.getVideoLink().isEmpty()) {
                errors.put("videoFile", "Hãy chọn file video hoặc nhập link video bên dưới");
            }
        }
        if (!UploadUtil.isAllowed(posterPart, UploadUtil.IMAGE_EXT)) {
            errors.put("poster", "Ảnh chỉ nhận định dạng png, jpg, jpeg, gif, webp");
        }
        if (!errors.isEmpty()) {
            showForm(req, resp, errors);
            return;
        }

        Video video = new Video();
        video.setTitle(form.getTitle());
        video.setDescription(form.getDescription());
        video.setVideoUrl(hasFile ? UploadUtil.saveVideo(videoPart) : form.getVideoLink());
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
}
