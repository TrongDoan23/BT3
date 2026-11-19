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
import vn.iotstar.util.Constant;
import vn.iotstar.util.UploadUtil;
import vn.iotstar.util.ValidationUtil;

@WebServlet(urlPatterns = { "/admin/category/add" })
@MultipartConfig(fileSizeThreshold = 1024 * 1024, maxFileSize = 1024 * 1024 * 5, maxRequestSize = 1024 * 1024 * 25)
public class CategoryAddController extends HttpServlet {
    private static final long serialVersionUID = 1L;
    private static final String VIEW = "/WEB-INF/views/admin/category/add-category.jsp";
    private final CategoryService cateService = new CategoryServiceImpl();

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        req.getRequestDispatcher(VIEW).forward(req, resp);
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        req.setCharacterEncoding("UTF-8");
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
        if (!errors.containsKey("catename") && cateService.get(form.getCatename()) != null) {
            errors.put("catename", "Tên danh mục đã tồn tại");
        }
        if (!errors.isEmpty()) {
            req.setAttribute("errors", errors);
            req.getRequestDispatcher(VIEW).forward(req, resp);
            return;
        }

        Category category = new Category();
        category.setCatename(form.getCatename());
        String fname = UploadUtil.saveImage(icon);
        category.setIcon(fname != null ? fname : Constant.DEFAULT_ICON);

        cateService.insert(category);
        resp.sendRedirect(req.getContextPath() + "/admin/category/list");
    }
}
