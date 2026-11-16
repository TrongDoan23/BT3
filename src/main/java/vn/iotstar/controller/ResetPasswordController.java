package vn.iotstar.controller;

import java.io.IOException;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.*;
import vn.iotstar.service.OtpResult;
import vn.iotstar.service.UserService;
import vn.iotstar.service.impl.UserServiceImpl;
import vn.iotstar.util.OtpMessages;

/** Nhập OTP + mật khẩu mới để đặt lại mật khẩu. */
@WebServlet(urlPatterns = "/reset-password")
public class ResetPasswordController extends HttpServlet {
    private static final long serialVersionUID = 1L;
    private static final String VIEW = "/WEB-INF/views/reset-password.jsp";
    private final UserService service = new UserServiceImpl();

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        req.setAttribute("email", req.getParameter("email"));
        if (req.getParameter("sent") != null) {
            req.setAttribute("info", "Nếu email tồn tại trong hệ thống, mã OTP đã được gửi (hiệu lực 5 phút).");
        }
        req.getRequestDispatcher(VIEW).forward(req, resp);
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        req.setCharacterEncoding("UTF-8");
        String email = req.getParameter("email") == null ? "" : req.getParameter("email").trim();
        req.setAttribute("email", email);

        if ("resend".equals(req.getParameter("action"))) {
            OtpResult r = service.sendOtp(email, "RESET");
            if (r == OtpResult.OK || r == OtpResult.NOT_FOUND) {
                req.setAttribute("info", "Nếu email tồn tại trong hệ thống, mã OTP mới đã được gửi.");
            } else {
                req.setAttribute("alert", OtpMessages.of(r));
            }
            req.getRequestDispatcher(VIEW).forward(req, resp);
            return;
        }

        String password = req.getParameter("password");
        String confirm = req.getParameter("confirm");
        String error = null;
        if (password == null || password.length() < 6) {
            error = "Mật khẩu mới phải có ít nhất 6 ký tự";
        } else if (!password.equals(confirm)) {
            error = "Mật khẩu nhập lại không khớp";
        }
        if (error != null) {
            req.setAttribute("alert", error);
            req.getRequestDispatcher(VIEW).forward(req, resp);
            return;
        }

        OtpResult r = service.resetPassword(email, req.getParameter("otp"), password);
        if (r == OtpResult.OK) {
            resp.sendRedirect(req.getContextPath() + "/login?reset=1");
        } else {
            req.setAttribute("alert", r == OtpResult.NOT_FOUND ? "Mã OTP không hợp lệ." : OtpMessages.of(r));
            req.getRequestDispatcher(VIEW).forward(req, resp);
        }
    }
}
