package vn.iotstar.controller;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.*;
import vn.iotstar.util.Constant;

/** /image?fname=abc.png (ảnh) và /media?fname=abc.mp4 (video, hỗ trợ Range để tua). */
@WebServlet(urlPatterns = { "/image", "/media" })
public class DownloadImageController extends HttpServlet {
    private static final long serialVersionUID = 1L;

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws IOException {
        String fname = req.getParameter("fname");
        if (fname == null || fname.isBlank()) {
            resp.sendError(HttpServletResponse.SC_NOT_FOUND);
            return;
        }
        Path file = Paths.get(Constant.DIR, Paths.get(fname).getFileName().toString());
        if (!Files.isRegularFile(file)) {
            resp.sendError(HttpServletResponse.SC_NOT_FOUND);
            return;
        }
        String mime = getServletContext().getMimeType(file.getFileName().toString());
        resp.setContentType(mime != null ? mime : "application/octet-stream");
        resp.setHeader("Accept-Ranges", "bytes");

        long length = Files.size(file);
        long start = 0;
        long end = length - 1;
        String range = req.getHeader("Range");
        if (range != null && range.startsWith("bytes=")) {
            try {
                String[] p = range.substring(6).split("-", 2);
                if (p[0].isEmpty()) {                       // bytes=-N (N byte cuối)
                    start = Math.max(0, length - Long.parseLong(p[1]));
                } else {
                    start = Long.parseLong(p[0]);
                    if (p.length > 1 && !p[1].isEmpty()) end = Math.min(Long.parseLong(p[1]), length - 1);
                }
            } catch (RuntimeException e) {
                start = length; // ép về nhánh 416 bên dưới
            }
            if (start > end || start >= length) {
                resp.setStatus(HttpServletResponse.SC_REQUESTED_RANGE_NOT_SATISFIABLE);
                resp.setHeader("Content-Range", "bytes */" + length);
                return;
            }
            resp.setStatus(HttpServletResponse.SC_PARTIAL_CONTENT);
            resp.setHeader("Content-Range", "bytes " + start + "-" + end + "/" + length);
        }

        long remaining = end - start + 1;
        resp.setContentLengthLong(remaining);
        try (InputStream in = Files.newInputStream(file); OutputStream out = resp.getOutputStream()) {
            in.skipNBytes(start);
            byte[] buf = new byte[16 * 1024];
            while (remaining > 0) {
                int n = in.read(buf, 0, (int) Math.min(buf.length, remaining));
                if (n < 0) break;
                out.write(buf, 0, n);
                remaining -= n;
            }
        } catch (IOException ignored) {
            // trình duyệt ngắt kết nối khi tua/đóng video – bình thường
        }
    }
}
