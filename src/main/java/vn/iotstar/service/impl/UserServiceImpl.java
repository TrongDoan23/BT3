package vn.iotstar.service.impl;

import java.time.Duration;
import java.time.LocalDateTime;
import java.util.Date;

import vn.iotstar.dao.UserDao;
import vn.iotstar.dao.impl.UserDaoImpl;
import vn.iotstar.entity.User;
import vn.iotstar.service.OtpResult;
import vn.iotstar.service.UserService;
import vn.iotstar.util.MailUtil;
import vn.iotstar.util.OtpUtil;
import vn.iotstar.util.PasswordUtil;

public class UserServiceImpl implements UserService {
    public static final String ACTIVATE = "ACTIVATE";
    public static final String RESET = "RESET";
    private static final int OTP_MINUTES = 5;
    private static final int RESEND_SECONDS = 60;
    private static final int MAX_ATTEMPTS = 5;

    private final UserDao userDao = new UserDaoImpl();

    // ---------------- Đăng nhập (mật khẩu hash) ----------------
    @Override
    public User login(String username, String password) {
        User user = this.get(username);
        if (user == null || !PasswordUtil.matches(password, user.getPassWord())) {
            return null;
        }
        // Tự nâng cấp mật khẩu cũ (plain text) lên hash
        if (!PasswordUtil.isHashed(user.getPassWord())) {
            user.setPassWord(PasswordUtil.hash(password));
            userDao.update(user);
        }
        return user;
    }

    @Override
    public User get(String username) {
        return userDao.get(username);
    }

    @Override
    public void insert(User user) {
        userDao.insert(user);
    }

    // ---------------- Đăng ký ----------------
    @Override
    public boolean register(String username, String password, String email, String fullname, String phone) {
        if (userDao.checkExistUsername(username)) {
            return false;
        }
        User user = new User();
        user.setEmail(email);
        user.setUserName(username);
        user.setFullName(fullname);
        user.setPassWord(PasswordUtil.hash(password));
        user.setRoleid(5);
        user.setPhone(phone);
        user.setCreatedDate(new Date());
        user.setActive(false); // chờ kích hoạt bằng OTP
        userDao.insert(user);
        return true;
    }

    @Override
    public boolean checkExistEmail(String email) {
        return userDao.checkExistEmail(email);
    }

    @Override
    public boolean checkExistUsername(String username) {
        return userDao.checkExistUsername(username);
    }

    @Override
    public boolean checkExistPhone(String phone) {
        return userDao.checkExistPhone(phone);
    }

    // ---------------- OTP ----------------
    @Override
    public OtpResult sendOtp(String email, String purpose) {
        User u = userDao.getByEmail(email);
        if (u == null) return OtpResult.NOT_FOUND;
        if (ACTIVATE.equals(purpose) && u.isEnabled()) return OtpResult.ALREADY_ACTIVE;

        LocalDateTime now = LocalDateTime.now();
        if (u.getOtpExpiry() != null && purpose.equals(u.getOtpPurpose())) {
            LocalDateTime sentAt = u.getOtpExpiry().minusMinutes(OTP_MINUTES);
            if (now.isBefore(sentAt.plusSeconds(RESEND_SECONDS))) return OtpResult.TOO_SOON;
        }

        String otp = OtpUtil.generateOtp();
        u.setOtp(PasswordUtil.hash(otp));
        u.setOtpPurpose(purpose);
        u.setOtpExpiry(now.plusMinutes(OTP_MINUTES));
        u.setOtpAttempts(0);
        userDao.update(u);

        String subject = ACTIVATE.equals(purpose) ? "Mã OTP kích hoạt tài khoản" : "Mã OTP đặt lại mật khẩu";
        String body = "Xin chào " + (u.getFullName() == null ? "" : u.getFullName()) + ",\n\n"
                + "Mã OTP của bạn là: " + otp + "\n"
                + "Mã có hiệu lực trong " + OTP_MINUTES + " phút. Không chia sẻ mã này cho bất kỳ ai.\n\n"
                + "Nếu bạn không thực hiện yêu cầu này, hãy bỏ qua email.";
        try {
            MailUtil.send(u.getEmail(), subject, body);
        } catch (Exception e) {
            e.printStackTrace();
            return OtpResult.MAIL_ERROR;
        }
        return OtpResult.OK;
    }

    /** Kiểm tra OTP. Nếu đúng thì xóa OTP (caller tự update). Nếu sai thì tăng số lần thử và lưu. */
    private OtpResult verify(User u, String otp, String purpose) {
        if (u == null) return OtpResult.NOT_FOUND;
        if (u.getOtp() == null || u.getOtpExpiry() == null || !purpose.equals(u.getOtpPurpose())) {
            return OtpResult.INVALID;
        }
        if (LocalDateTime.now().isAfter(u.getOtpExpiry())) return OtpResult.EXPIRED;
        int attempts = u.getOtpAttempts() == null ? 0 : u.getOtpAttempts();
        if (attempts >= MAX_ATTEMPTS) return OtpResult.TOO_MANY;

        if (otp == null || !PasswordUtil.matches(otp.trim(), u.getOtp())) {
            u.setOtpAttempts(attempts + 1);
            userDao.update(u);
            return OtpResult.WRONG;
        }
        u.setOtp(null);
        u.setOtpPurpose(null);
        u.setOtpExpiry(null);
        u.setOtpAttempts(0);
        return OtpResult.OK;
    }

    @Override
    public OtpResult verifyActivation(String email, String otp) {
        User u = userDao.getByEmail(email);
        if (u != null && u.isEnabled() && u.getOtp() == null) return OtpResult.ALREADY_ACTIVE;
        OtpResult r = verify(u, otp, ACTIVATE);
        if (r == OtpResult.OK) {
            u.setActive(true);
            userDao.update(u);
        }
        return r;
    }

    @Override
    public OtpResult resetPassword(String email, String otp, String newPassword) {
        User u = userDao.getByEmail(email);
        OtpResult r = verify(u, otp, RESET);
        if (r == OtpResult.OK) {
            u.setPassWord(PasswordUtil.hash(newPassword));
            u.setActive(true); // đã chứng minh sở hữu email
            u.setRememberToken(null); // đăng xuất các thiết bị đã "nhớ tôi"
            userDao.update(u);
        }
        return r;
    }

    // ---------------- Nhớ tôi ----------------
    @Override
    public String createRememberToken(User user) {
        User u = userDao.get(user.getUserName());
        if (u == null) return null;
        String token = OtpUtil.generateToken();
        u.setRememberToken(token);
        userDao.update(u);
        return token;
    }

    @Override
    public User getByRememberToken(String token) {
        return (token == null || token.isBlank()) ? null : userDao.getByRememberToken(token);
    }

    @Override
    public void clearRememberToken(User user) {
        User u = userDao.get(user.getUserName());
        if (u != null && u.getRememberToken() != null) {
            u.setRememberToken(null);
            userDao.update(u);
        }
    }
}
