package vn.iotstar.util;

import vn.iotstar.service.OtpResult;

public class OtpMessages {
    private OtpMessages() {}

    public static String of(OtpResult r) {
        switch (r) {
            case NOT_FOUND:      return "Không tìm thấy tài khoản với email này.";
            case INVALID:        return "Chưa có mã OTP hợp lệ. Hãy bấm \"Gửi lại mã\".";
            case WRONG:          return "Mã OTP không đúng.";
            case EXPIRED:        return "Mã OTP đã hết hạn. Hãy bấm \"Gửi lại mã\".";
            case TOO_MANY:       return "Bạn nhập sai quá nhiều lần. Hãy bấm \"Gửi lại mã\" để nhận mã mới.";
            case TOO_SOON:       return "Vui lòng đợi khoảng 1 phút trước khi yêu cầu gửi lại mã.";
            case MAIL_ERROR:     return "Không gửi được email. Kiểm tra cấu hình mail hoặc thử lại sau.";
            case ALREADY_ACTIVE: return "Tài khoản đã được kích hoạt, hãy đăng nhập.";
            default:             return "";
        }
    }
}
