package vn.iotstar.controller;

import java.io.IOException;
import java.util.Map;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.MultipartConfig;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.*;
import vn.iotstar.entity.Category;
import vn.iotstar.form.CategoryForm;
import vn.iotstar.service.CategoryService;
import vn.iotstar.service.impl.CategoryServiceImpl;
import vn.iotstar.util.UploadUtil;
import vn.iotstar.util.ValidationUtil;

@WebServlet(urlPatterns = { "/admin/category/edit" })
@MultipartConfig(fileSizeThreshold = 1024 * 1024, maxFileSize = 1024 * 1024 * 5, maxRequestSize = 1024 * 1024 * 25)
public class CategoryEditController extends HttpServlet {
    private static final long serialVersionUID = 1L;
    private static final String VIEW = "/WEB-INF/views/admin/category/edit-category.jsp";
    private final CategoryService cateService = new CategoryServiceImpl();

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        Category category = null;
        try {
            category = cateService.get(Integer.parseInt(req.getParameter("id")));
        } catch (NumberFormatException ignored) {
        }
        if (category == null) {
            resp.sendRedirect(req.getContextPath() + "/admin/category/list");
            return;
        }
        req.setAttribute("category", category);
        req.getRequestDispatcher(VIEW).forward(req, resp);
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        req.setCharacterEncoding("UTF-8");
        int id;
        Category old;
        try {
            id = Integer.parseInt(req.getParameter("cateid"));
            old = cateService.get(id);
        } catch (NumberFormatException e) {
            old = null;
            id = 0;
        }
        if (old == null) {
            resp.sendRedirect(req.getContextPath() + "/admin/category/list");
            return;
        }

        CategoryForm form = new CategoryForm(req.getParameter("catename"));
        Map<String, String> errors = ValidationUtil.validate(form);

        Part icon = null;
        try {
            icon = req.getPart("icon");
        } catch (IllegalStateException e) {
            errors.put("icon", "Ảnh quá lớn (tối đa 5MB)");
        }
        if (!errors.containsKey("icon") && !UploadUtil.isAllowed(icon, UploadUtil.IMAGE_EXT)) {
            errors.put("icon", "Ảnh chỉ nhận định dạng png, jpg, jpeg, gif, webp");
        }
        if (!errors.containsKey("catename")) {
            Category same = cateService.get(form.getCatename());
            if (same != null && same.getCateid() != id) {
                errors.put("catename", "Tên danh mục đã tồn tại");
            }
        }
        if (!errors.isEmpty()) {
            req.setAttribute("errors", errors);
            req.setAttribute("category", old);
            req.getRequestDispatcher(VIEW).forward(req, resp);
            return;
        }

        Category category = new Category();
        category.setCateid(id);
        category.setCatename(form.getCatename());
        category.setIcon(UploadUtil.saveImage(icon)); // null = giữ ảnh cũ

        cateService.edit(category);
        resp.sendRedirect(req.getContextPath() + "/admin/category/list");
    }
}
