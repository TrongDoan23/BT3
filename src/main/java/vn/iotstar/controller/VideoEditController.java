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

@WebServlet(urlPatterns = { "/video/edit" })
@MultipartConfig(fileSizeThreshold = 1024 * 1024, maxFileSize = 1024L * 1024 * 200, maxRequestSize = 1024L * 1024 * 220)
public class VideoEditController extends HttpServlet {
    private static final long serialVersionUID = 1L;
    private static final String VIEW = "/WEB-INF/views/video/form-video.jsp";
    private final VideoService videoService = new VideoServiceImpl();
    private final CategoryService categoryService = new CategoryServiceImpl();

    /** Chỉ chủ video hoặc admin được sửa/xóa. */
    static boolean canModify(User account, Video v) {
        return v != null && account != null && (account.getRoleid() == 1 || v.getUser().getId() == account.getId());
    }

    private void showForm(HttpServletRequest req, HttpServletResponse resp, Video v, Map<String, String> errors)
            throws ServletException, IOException {
        req.setAttribute("video", v);
        req.setAttribute("formAction", req.getContextPath() + "/video/edit");
        req.setAttribute("formTitle", "Sửa video");
        req.setAttribute("categories", categoryService.getAll());
        if (errors != null) req.setAttribute("errors", errors);
        req.getRequestDispatcher(VIEW).forward(req, resp);
    }

    private Video load(HttpServletRequest req, String param) {
        try {
            return videoService.get(Integer.parseInt(req.getParameter(param)));
        } catch (NumberFormatException e) {
            return null;
        }
    }

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        User account = (User) req.getSession().getAttribute(Constant.SESSION_ACCOUNT);
        Video v = load(req, "id");
        if (!canModify(account, v)) {
            resp.sendRedirect(req.getContextPath() + "/video");
            return;
        }
        showForm(req, resp, v, null);
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        req.setCharacterEncoding("UTF-8");
        User account = (User) req.getSession().getAttribute(Constant.SESSION_ACCOUNT);
        Video old = load(req, "id");
        if (!canModify(account, old)) {
            resp.sendRedirect(req.getContextPath() + "/video");
            return;
        }

        VideoForm form = new VideoForm(req.getParameter("title"), req.getParameter("description"),
                req.getParameter("videoLink"));
        Map<String, String> errors = new LinkedHashMap<>(ValidationUtil.validate(form));

        Part videoPart = null;
        Part posterPart = null;
        try {
            videoPart = req.getPart("videoFile");
            posterPart = req.getPart("poster");
        } catch (IllegalStateException e) {
            errors.put("videoFile", "File quá lớn (video tối đa 200MB)");
        }
        if (!errors.containsKey("videoFile") && !UploadUtil.isAllowed(videoPart, UploadUtil.VIDEO_EXT)) {
            errors.put("videoFile", "Video chỉ nhận định dạng mp4, webm, ogg");
        }
        if (!UploadUtil.isAllowed(posterPart, UploadUtil.IMAGE_EXT)) {
            errors.put("poster", "Ảnh chỉ nhận định dạng png, jpg, jpeg, gif, webp");
        }
        if (!errors.isEmpty()) {
            showForm(req, resp, old, errors);
            return;
        }

        String newVideoUrl = null; // null = giữ nguyên video cũ
        if (UploadUtil.hasFile(videoPart)) {
            newVideoUrl = UploadUtil.saveVideo(videoPart);
        } else if (!form.getVideoLink().isEmpty()) {
            newVideoUrl = form.getVideoLink();
        }

        Video in = new Video();
        in.setId(old.getId());
        in.setTitle(form.getTitle());
        in.setDescription(form.getDescription());
        in.setActive("on".equals(req.getParameter("active")));
        in.setVideoUrl(newVideoUrl);
        in.setPoster(UploadUtil.saveImage(posterPart)); // null = giữ ảnh cũ
        try {
            String c = req.getParameter("cateid");
            Category cat = (c == null || c.isBlank()) ? null : categoryService.get(Integer.parseInt(c));
            in.setCategory(cat);
        } catch (NumberFormatException e) {
            in.setCategory(null);
        }

        videoService.edit(in);
        resp.sendRedirect(req.getContextPath() + "/video");
    }
}
