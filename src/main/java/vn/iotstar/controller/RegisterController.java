package vn.iotstar.controller;

import java.io.IOException;
import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.*;
import vn.iotstar.service.OtpResult;
import vn.iotstar.service.UserService;
import vn.iotstar.service.impl.UserServiceImpl;
import vn.iotstar.util.Constant;

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

        String username = trim(req.getParameter("username"));
        String password = req.getParameter("password");
        String email = trim(req.getParameter("email"));
        String fullname = trim(req.getParameter("fullname"));
        String phone = trim(req.getParameter("phone"));

        String alert = null;
        if (username.isEmpty() || password == null || password.isEmpty() || email.isEmpty()) {
            alert = "Vui lòng nhập tài khoản, email và mật khẩu";
        } else if (password.length() < 6) {
            alert = "Mật khẩu phải có ít nhất 6 ký tự";
        } else if (service.checkExistEmail(email)) {
            alert = "Email đã tồn tại!";
        } else if (service.checkExistUsername(username)) {
            alert = "Tài khoản đã tồn tại!";
        } else if (!phone.isEmpty() && service.checkExistPhone(phone)) {
            alert = "Số điện thoại đã tồn tại!";
        }
        if (alert != null) {
            req.setAttribute("alert", alert);
            req.getRequestDispatcher(REGISTER).forward(req, resp);
            return;
        }

        boolean ok = service.register(username, password, email, fullname, phone);
        if (ok) {
            // Gửi OTP kích hoạt qua email rồi chuyển sang trang nhập OTP
            OtpResult r = service.sendOtp(email, "ACTIVATE");
            String flag = (r == OtpResult.OK) ? "sent=1" : "mailfail=1";
            resp.sendRedirect(req.getContextPath() + "/verify-otp?" + flag
                    + "&email=" + URLEncoder.encode(email, StandardCharsets.UTF_8));
        } else {
            req.setAttribute("alert", "System error!");
            req.getRequestDispatcher(REGISTER).forward(req, resp);
        }
    }

    private static String trim(String s) {
        return s == null ? "" : s.trim();
    }
}
