package vn.iotstar.controller;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.MultipartConfig;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.*;
import vn.iotstar.util.Constant;
import vn.iotstar.util.UploadUtil;

/** Demo upload file bằng @MultipartConfig + Part (theo tài liệu 10_UploadFile_servlet_jakarta). */
@WebServlet(name = "MultiPartServlet", urlPatterns = { "/multiPartServlet" })
@MultipartConfig(fileSizeThreshold = 1024 * 1024, maxFileSize = 1024 * 1024 * 5, maxRequestSize = 1024 * 1024 * 5 * 5)
public class MultipartServlet extends HttpServlet {
    private static final long serialVersionUID = 1L;
    private static final String ALLOWED = "png|jpg|jpeg|gif|webp|mp4|webm|pdf|txt|zip|docx";

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        req.getRequestDispatcher("/WEB-INF/views/upload-demo.jsp").forward(req, resp);
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        try {
            Files.createDirectories(Paths.get(Constant.DIR));
            StringBuilder saved = new StringBuilder();
            for (Part part : req.getParts()) {
                if (part.getSubmittedFileName() == null || part.getSize() == 0) continue; // bỏ qua field text
                String name = UploadUtil.save(part, ALLOWED);
                if (name == null) {
                    req.setAttribute("message", "File không hợp lệ hoặc định dạng không được phép (" + ALLOWED + ").");
                    req.getRequestDispatcher("/WEB-INF/views/result.jsp").forward(req, resp);
                    return;
                }
                saved.append(part.getSubmittedFileName()).append(" -> ").append(name).append(' ');
            }
            req.setAttribute("message", saved.length() == 0
                    ? "Bạn chưa chọn file."
                    : "Upload thành công: " + saved + "(thư mục " + Constant.DIR + ")");
        } catch (IllegalStateException e) {
            req.setAttribute("message", "File quá lớn (tối đa 5MB).");
        }
        req.getRequestDispatcher("/WEB-INF/views/result.jsp").forward(req, resp);
    }
}
