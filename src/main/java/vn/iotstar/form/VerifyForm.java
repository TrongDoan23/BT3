package vn.iotstar.form;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;

/** Dữ liệu form dùng để validate (Bean Validation). */
public class VerifyForm {
    @NotBlank(message = "Thiếu email cần kích hoạt")
    @Email(message = "Email không hợp lệ")
    private String email;

    @NotBlank(message = "Vui lòng nhập mã OTP")
    @Pattern(regexp = "^\\d{6}$", message = "Mã OTP gồm 6 chữ số")
    private String otp;

    public VerifyForm(String email, String otp) {
        this.email = email == null ? "" : email.trim();
        this.otp = otp == null ? "" : otp.trim();
    }

    public String getEmail() {
        return email;
    }

    public String getOtp() {
        return otp;
    }
}
