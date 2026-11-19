package vn.iotstar.controller;

import java.io.IOException;
import java.util.LinkedHashMap;
import java.util.Map;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.*;
import vn.iotstar.form.ResetForm;
import vn.iotstar.service.OtpResult;
import vn.iotstar.service.UserService;
import vn.iotstar.service.impl.UserServiceImpl;
import vn.iotstar.util.OtpMessages;
import vn.iotstar.util.ValidationUtil;

/** Nhập OTP + mật khẩu mới để đặt lại mật khẩu. */
@WebServlet(urlPatterns = "/reset-password")
public class ResetPasswordController extends HttpServlet {
    private static final long serialVersionUID = 1L;
    private static final String VIEW = "/WEB-INF/views/reset-password.jsp";
    private final UserService service = new UserServiceImpl();

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        if (req.getParameter("sent") != null) {
            req.setAttribute("info", "Nếu email tồn tại trong hệ thống, mã OTP đã được gửi (hiệu lực 5 phút).");
        }
        req.getRequestDispatcher(VIEW).forward(req, resp);
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        req.setCharacterEncoding("UTF-8");
        ResetForm form = new ResetForm(req.getParameter("email"), req.getParameter("otp"),
                req.getParameter("password"), req.getParameter("confirm"));
        Map<String, String> errors = ValidationUtil.validate(form);

        if ("resend".equals(req.getParameter("action"))) {
            // Gửi lại mã: chỉ cần email hợp lệ
            if (errors.containsKey("email")) {
                Map<String, String> only = new LinkedHashMap<>();
                only.put("email", errors.get("email"));
                req.setAttribute("errors", only);
            } else {
                OtpResult r = service.sendOtp(form.getEmail(), "RESET");
                if (r == OtpResult.OK || r == OtpResult.NOT_FOUND) {
                    req.setAttribute("info", "Nếu email tồn tại trong hệ thống, mã OTP mới đã được gửi.");
                } else {
                    req.setAttribute("alert", OtpMessages.of(r));
                }
            }
            req.getRequestDispatcher(VIEW).forward(req, resp);
            return;
        }

        if (!errors.containsKey("confirm") && !form.getPassword().equals(form.getConfirm())) {
            errors.put("confirm", "Mật khẩu nhập lại không khớp");
        }
        if (!errors.isEmpty()) {
            req.setAttribute("errors", errors);
            req.getRequestDispatcher(VIEW).forward(req, resp);
            return;
        }

        OtpResult r = service.resetPassword(form.getEmail(), form.getOtp(), form.getPassword());
        if (r == OtpResult.OK) {
            resp.sendRedirect(req.getContextPath() + "/login?reset=1");
        } else {
            req.setAttribute("alert", r == OtpResult.NOT_FOUND ? "Mã OTP không hợp lệ." : OtpMessages.of(r));
            req.getRequestDispatcher(VIEW).forward(req, resp);
        }
    }
}
