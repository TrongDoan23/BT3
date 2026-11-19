package vn.iotstar.form;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;

/** Dữ liệu form dùng để validate (Bean Validation). */
public class RegisterForm {
    @NotBlank(message = "Vui lòng nhập tài khoản")
    @Size(min = 3, max = 50, message = "Tài khoản từ 3 đến 50 ký tự")
    @Pattern(regexp = "^[A-Za-z0-9_.]*$", message = "Tài khoản chỉ gồm chữ không dấu, số, dấu . và _")
    private String username;

    @NotBlank(message = "Vui lòng nhập họ tên")
    @Size(max = 100, message = "Họ tên tối đa 100 ký tự")
    private String fullname;

    @NotBlank(message = "Vui lòng nhập email")
    @Email(message = "Email không hợp lệ")
    @Size(max = 255, message = "Email tối đa 255 ký tự")
    private String email;

    @Pattern(regexp = "^$|^(0|\\+84)\\d{9,10}$", message = "Số điện thoại không hợp lệ (vd: 0912345678)")
    private String phone;

    @NotBlank(message = "Vui lòng nhập mật khẩu")
    @Size(min = 6, max = 50, message = "Mật khẩu từ 6 đến 50 ký tự")
    private String password;

    @NotBlank(message = "Vui lòng nhập lại mật khẩu")
    private String confirm;

    public RegisterForm(String username, String fullname, String email, String phone, String password, String confirm) {
        this.username = username == null ? "" : username.trim();
        this.fullname = fullname == null ? "" : fullname.trim();
        this.email = email == null ? "" : email.trim();
        this.phone = phone == null ? "" : phone.trim();
        this.password = password == null ? "" : password;
        this.confirm = confirm == null ? "" : confirm;
    }

    public String getUsername() {
        return username;
    }

    public String getFullname() {
        return fullname;
    }

    public String getEmail() {
        return email;
    }

    public String getPhone() {
        return phone;
    }

    public String getPassword() {
        return password;
    }

    public String getConfirm() {
        return confirm;
    }
}
