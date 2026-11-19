package vn.iotstar.form;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;

/** Dữ liệu form dùng để validate (Bean Validation). */
public class CategoryForm {
    @NotBlank(message = "Tên danh mục không được rỗng")
    @Size(max = 255, message = "Tên danh mục tối đa 255 ký tự")
    private String catename;

    public CategoryForm(String catename) {
        this.catename = catename == null ? "" : catename.trim();
    }

    public String getCatename() {
        return catename;
    }
}
