package vn.iotstar.controller;

import java.io.IOException;
import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;
import java.util.Map;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.*;
import vn.iotstar.form.RegisterForm;
import vn.iotstar.service.OtpResult;
import vn.iotstar.service.UserService;
import vn.iotstar.service.impl.UserServiceImpl;
import vn.iotstar.util.Constant;
import vn.iotstar.util.ValidationUtil;

@WebServlet(urlPatterns = "/register")
public class RegisterController extends HttpServlet {
    private static final long serialVersionUID = 1L;
    private static final String REGISTER = "/WEB-INF/views/register.jsp";
    private final UserService service = new UserServiceImpl();

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        HttpSession session = req.getSession(false);
        if (session != null && session.getAttribute(Constant.SESSION_ACCOUNT) != null) {
            resp.sendRedirect(req.getContextPath() + "/waiting");
            return;
        }
        req.getRequestDispatcher(REGISTER).forward(req, resp);
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        resp.setCharacterEncoding("UTF-8");
        req.setCharacterEncoding("UTF-8");

        RegisterForm form = new RegisterForm(req.getParameter("username"), req.getParameter("fullname"),
                req.getParameter("email"), req.getParameter("phone"), req.getParameter("password"),
                req.getParameter("confirm"));
        Map<String, String> errors = ValidationUtil.validate(form);

        if (!errors.containsKey("confirm") && !form.getPassword().equals(form.getConfirm())) {
            errors.put("confirm", "Mật khẩu nhập lại không khớp");
        }
        // Kiểm tra trùng (chỉ khi field hợp lệ)
        if (!errors.containsKey("username") && service.checkExistUsername(form.getUsername())) {
            errors.put("username", "Tài khoản đã tồn tại!");
        }
        if (!errors.containsKey("email") && service.checkExistEmail(form.getEmail())) {
            errors.put("email", "Email đã tồn tại!");
        }
        if (!errors.containsKey("phone") && !form.getPhone().isEmpty() && service.checkExistPhone(form.getPhone())) {
            errors.put("phone", "Số điện thoại đã tồn tại!");
        }
        if (!errors.isEmpty()) {
            req.setAttribute("errors", errors);
            req.getRequestDispatcher(REGISTER).forward(req, resp);
            return;
        }

        boolean ok = service.register(form.getUsername(), form.getPassword(), form.getEmail(),
                form.getFullname(), form.getPhone());
        if (ok) {
            // Gửi OTP kích hoạt qua email rồi chuyển sang trang nhập OTP
            OtpResult r = service.sendOtp(form.getEmail(), "ACTIVATE");
            String flag = (r == OtpResult.OK) ? "sent=1" : "mailfail=1";
            resp.sendRedirect(req.getContextPath() + "/verify-otp?" + flag
                    + "&email=" + URLEncoder.encode(form.getEmail(), StandardCharsets.UTF_8));
        } else {
            req.setAttribute("alert", "System error!");
            req.getRequestDispatcher(REGISTER).forward(req, resp);
        }
    }
}
