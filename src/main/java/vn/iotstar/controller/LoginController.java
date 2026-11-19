package vn.iotstar.controller;

import java.io.IOException;
import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;
import java.util.Map;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.*;
import vn.iotstar.entity.User;
import vn.iotstar.form.LoginForm;
import vn.iotstar.service.UserService;
import vn.iotstar.service.impl.UserServiceImpl;
import vn.iotstar.util.Constant;
import vn.iotstar.util.ValidationUtil;

@WebServlet(urlPatterns = "/login")
public class LoginController extends HttpServlet {
    private static final long serialVersionUID = 1L;
    private static final String VIEW = "/WEB-INF/views/login.jsp";
    private final UserService service = new UserServiceImpl();

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        // 1. Đã đăng nhập (Session)
        HttpSession session = req.getSession(false);
        if (session != null && session.getAttribute(Constant.SESSION_ACCOUNT) != null) {
            resp.sendRedirect(req.getContextPath() + "/waiting");
            return;
        }
        // 2. Đăng nhập tự động bằng Cookie (token ngẫu nhiên, không phải username)
        Cookie[] cookies = req.getCookies();
        if (cookies != null) {
            for (Cookie cookie : cookies) {
                if (Constant.COOKIE_REMEMBER.equals(cookie.getName())) {
                    User user = service.getByRememberToken(cookie.getValue());
                    if (user != null && user.isEnabled()) {
                        req.getSession(true).setAttribute(Constant.SESSION_ACCOUNT, user);
                        resp.sendRedirect(req.getContextPath() + "/waiting");
                        return;
                    }
                }
            }
        }
        req.getRequestDispatcher(VIEW).forward(req, resp);
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        resp.setContentType("text/html;charset=UTF-8");
        req.setCharacterEncoding("UTF-8");

        boolean remember = "on".equals(req.getParameter("remember"));
        LoginForm form = new LoginForm(req.getParameter("username"), req.getParameter("password"));
        Map<String, String> errors = ValidationUtil.validate(form);
        if (!errors.isEmpty()) {
            req.setAttribute("errors", errors);
            req.getRequestDispatcher(VIEW).forward(req, resp);
            return;
        }
        String username = form.getUsername();
        String password = form.getPassword();

        User user = service.login(username, password);
        if (user == null) {
            req.setAttribute("alert", "Tài khoản hoặc mật khẩu không đúng");
            req.getRequestDispatcher(VIEW).forward(req, resp);
            return;
        }
        if (!user.isEnabled()) {
            req.setAttribute("alert", "Tài khoản chưa được kích hoạt. Hãy nhập mã OTP đã gửi qua email.");
            req.setAttribute("activateEmail", URLEncoder.encode(user.getEmail(), StandardCharsets.UTF_8));
            req.getRequestDispatcher(VIEW).forward(req, resp);
            return;
        }

        // Chống session fixation: đổi session id sau khi đăng nhập
        HttpSession old = req.getSession(false);
        if (old != null) old.invalidate();
        req.getSession(true).setAttribute(Constant.SESSION_ACCOUNT, user);

        if (remember) {
            Cookie cookie = new Cookie(Constant.COOKIE_REMEMBER, service.createRememberToken(user));
            cookie.setMaxAge(30 * 60);
            cookie.setPath("/");
            cookie.setHttpOnly(true);
            resp.addCookie(cookie);
        }
        resp.sendRedirect(req.getContextPath() + "/waiting");
    }
}
