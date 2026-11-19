package vn.iotstar.form;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;

/** Dữ liệu form dùng để validate (Bean Validation). */
public class ForgotForm {
    @NotBlank(message = "Vui lòng nhập email")
    @Email(message = "Email không hợp lệ")
    private String email;

    public ForgotForm(String email) {
        this.email = email == null ? "" : email.trim();
    }

    public String getEmail() {
        return email;
    }
}
