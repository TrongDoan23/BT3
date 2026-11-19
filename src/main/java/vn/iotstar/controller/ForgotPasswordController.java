package vn.iotstar.controller;

import java.io.IOException;
import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;
import java.util.Map;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.*;
import vn.iotstar.form.ForgotForm;
import vn.iotstar.service.OtpResult;
import vn.iotstar.service.UserService;
import vn.iotstar.service.impl.UserServiceImpl;
import vn.iotstar.util.OtpMessages;
import vn.iotstar.util.ValidationUtil;

/** Quên mật khẩu: nhập email để nhận OTP. */
@WebServlet(urlPatterns = "/forgot-password")
public class ForgotPasswordController extends HttpServlet {
    private static final long serialVersionUID = 1L;
    private static final String VIEW = "/WEB-INF/views/forgot-password.jsp";
    private final UserService service = new UserServiceImpl();

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        req.getRequestDispatcher(VIEW).forward(req, resp);
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        req.setCharacterEncoding("UTF-8");
        ForgotForm form = new ForgotForm(req.getParameter("email"));
        Map<String, String> errors = ValidationUtil.validate(form);
        if (!errors.isEmpty()) {
            req.setAttribute("errors", errors);
            req.getRequestDispatcher(VIEW).forward(req, resp);
            return;
        }
        String email = form.getEmail();
        OtpResult r = service.sendOtp(email, "RESET");
        if (r == OtpResult.MAIL_ERROR || r == OtpResult.TOO_SOON) {
            req.setAttribute("alert", OtpMessages.of(r));
            req.getRequestDispatcher(VIEW).forward(req, resp);
            return;
        }
        // OK hoặc NOT_FOUND đều trả lời giống nhau để không lộ email nào đã đăng ký
        resp.sendRedirect(req.getContextPath() + "/reset-password?sent=1&email="
                + URLEncoder.encode(email, StandardCharsets.UTF_8));
    }
}
