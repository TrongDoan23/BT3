package vn.iotstar.util;

import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;

import jakarta.servlet.http.Part;

public class UploadUtil {
    public static final String IMAGE_EXT = "png|jpg|jpeg|gif|webp";
    public static final String VIDEO_EXT = "mp4|webm|ogg";

    private UploadUtil() {}

    /** Không chọn file => hợp lệ; có chọn file thì phải đúng đuôi cho phép. */
    public static boolean isAllowed(Part part, String allowedExt) {
        if (part == null || part.getSize() == 0 || part.getSubmittedFileName() == null) return true;
        String n = Paths.get(part.getSubmittedFileName()).getFileName().toString();
        int idx = n.lastIndexOf('.');
        return idx >= 0 && n.substring(idx + 1).toLowerCase().matches(allowedExt);
    }

    public static boolean hasFile(Part part) {
        return part != null && part.getSize() > 0;
    }

    public static String saveImage(Part part) throws IOException {
        return save(part, IMAGE_EXT);
    }

    public static String saveVideo(Part part) throws IOException {
        return save(part, VIDEO_EXT);
    }

    /** Lưu file vào thư mục upload với tên mới (timestamp). Trả về tên file, hoặc null nếu không có file / sai định dạng. */
    public static String save(Part part, String allowedExt) throws IOException {
        if (part == null || part.getSize() == 0 || part.getSubmittedFileName() == null) {
            return null;
        }
        String original = Paths.get(part.getSubmittedFileName()).getFileName().toString();
        int idx = original.lastIndexOf('.');
        String ext = idx >= 0 ? original.substring(idx + 1).toLowerCase() : "";
        if (!ext.matches(allowedExt)) {
            return null;
        }
        Files.createDirectories(Paths.get(Constant.DIR));
        String name = System.nanoTime() + "." + ext;
        try (InputStream in = part.getInputStream()) {
            Files.copy(in, Paths.get(Constant.DIR, name), StandardCopyOption.REPLACE_EXISTING);
        }
        return name;
    }

    public static void deleteImage(String fileName) {
        deleteFile(fileName);
    }

    /** Xóa file trong thư mục upload (bỏ qua link http và ảnh mặc định). */
    public static void deleteFile(String fileName) {
        if (fileName == null || fileName.isBlank() || fileName.startsWith("http")
                || fileName.equals(Constant.DEFAULT_ICON)) {
            return;
        }
        try {
            Path p = Paths.get(Constant.DIR, Paths.get(fileName).getFileName().toString());
            Files.deleteIfExists(p);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
