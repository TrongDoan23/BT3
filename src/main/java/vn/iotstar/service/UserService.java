package vn.iotstar.service;

import vn.iotstar.entity.User;

public interface UserService {
    /** Đăng nhập: kiểm tra hash. Trả về user nếu đúng mật khẩu (kể cả chưa kích hoạt – controller kiểm tra isEnabled). */
    User login(String username, String password);
    User get(String username);
    void insert(User user);
    /** Tạo tài khoản CHƯA kích hoạt, mật khẩu được hash. */
    boolean register(String username, String password, String email, String fullname, String phone);
    boolean checkExistEmail(String email);
    boolean checkExistUsername(String username);
    boolean checkExistPhone(String phone);

    /** Gửi (hoặc gửi lại) OTP. purpose = "ACTIVATE" hoặc "RESET". */
    OtpResult sendOtp(String email, String purpose);
    OtpResult verifyActivation(String email, String otp);
    OtpResult resetPassword(String email, String otp, String newPassword);

    String createRememberToken(User user);
    User getByRememberToken(String token);
    void clearRememberToken(User user);
}
