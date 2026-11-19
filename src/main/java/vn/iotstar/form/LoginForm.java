package vn.iotstar.form;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;

/** Dữ liệu form dùng để validate (Bean Validation). */
public class LoginForm {
    @NotBlank(message = "Vui lòng nhập tài khoản")
    @Size(max = 100, message = "Tài khoản tối đa 100 ký tự")
    private String username;

    @NotBlank(message = "Vui lòng nhập mật khẩu")
    @Size(max = 100, message = "Mật khẩu tối đa 100 ký tự")
    private String password;

    public LoginForm(String username, String password) {
        this.username = username == null ? "" : username.trim();
        this.password = password == null ? "" : password;
    }

    public String getUsername() {
        return username;
    }

    public String getPassword() {
        return password;
    }
}
