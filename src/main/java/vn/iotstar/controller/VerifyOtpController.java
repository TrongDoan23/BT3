package vn.iotstar.controller;

import java.io.IOException;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.*;
import vn.iotstar.service.OtpResult;
import vn.iotstar.service.UserService;
import vn.iotstar.service.impl.UserServiceImpl;
import vn.iotstar.util.OtpMessages;

/** Nhập OTP để kích hoạt tài khoản (và gửi lại OTP). */
@WebServlet(urlPatterns = "/verify-otp")
public class VerifyOtpController extends HttpServlet {
    private static final long serialVersionUID = 1L;
    private static final String VIEW = "/WEB-INF/views/verify-otp.jsp";
    private final UserService service = new UserServiceImpl();

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        req.setAttribute("email", req.getParameter("email"));
        if (req.getParameter("sent") != null) {
            req.setAttribute("info", "Mã OTP đã được gửi tới email của bạn (hiệu lực 5 phút).");
        } else if (req.getParameter("mailfail") != null) {
            req.setAttribute("alert", "Tạo tài khoản thành công nhưng chưa gửi được email. Hãy bấm \"Gửi lại mã\".");
        }
        req.getRequestDispatcher(VIEW).forward(req, resp);
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        req.setCharacterEncoding("UTF-8");
        String email = req.getParameter("email") == null ? "" : req.getParameter("email").trim();
        req.setAttribute("email", email);

        if ("resend".equals(req.getParameter("action"))) {
            OtpResult r = service.sendOtp(email, "ACTIVATE");
            if (r == OtpResult.OK) {
                req.setAttribute("info", "Đã gửi lại mã OTP. Hãy kiểm tra email.");
            } else if (r == OtpResult.ALREADY_ACTIVE) {
                resp.sendRedirect(req.getContextPath() + "/login?verified=1");
                return;
            } else {
                req.setAttribute("alert", OtpMessages.of(r));
            }
            req.getRequestDispatcher(VIEW).forward(req, resp);
            return;
        }

        OtpResult r = service.verifyActivation(email, req.getParameter("otp"));
        if (r == OtpResult.OK || r == OtpResult.ALREADY_ACTIVE) {
            resp.sendRedirect(req.getContextPath() + "/login?verified=1");
        } else {
            req.setAttribute("alert", OtpMessages.of(r));
            req.getRequestDispatcher(VIEW).forward(req, resp);
        }
    }
}
