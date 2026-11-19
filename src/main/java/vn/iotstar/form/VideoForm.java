package vn.iotstar.form;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;

/** Dữ liệu form dùng để validate (Bean Validation). */
public class VideoForm {
    @NotBlank(message = "Tiêu đề không được rỗng")
    @Size(max = 255, message = "Tiêu đề tối đa 255 ký tự")
    private String title;

    @Size(max = 2000, message = "Mô tả tối đa 2000 ký tự")
    private String description;

    @Pattern(regexp = "^$|^https?://\\S+$", message = "Link phải bắt đầu bằng http:// hoặc https://")
    @Size(max = 500, message = "Link tối đa 500 ký tự")
    private String videoLink;

    public VideoForm(String title, String description, String videoLink) {
        this.title = title == null ? "" : title.trim();
        this.description = description == null ? "" : description.trim();
        this.videoLink = videoLink == null ? "" : videoLink.trim();
    }

    public String getTitle() {
        return title;
    }

    public String getDescription() {
        return description;
    }

    public String getVideoLink() {
        return videoLink;
    }
}
