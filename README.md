# JPAShoppingMVC – Bài tập 01 làm lại bằng JPA

Servlet/JSP (Jakarta EE 10) · JPA / Hibernate 7.4 + Hibernate Validator · SQL Server · Maven · Tomcat 10.1

Chức năng: Đăng nhập (Session + Cookie "Nhớ tôi"), Đăng ký, Phân quyền theo vai trò, CRUD Category (có upload ảnh).

## Chạy project
1. Chạy `sql/JPAShopping.sql` trong SQL Server (tạo DB, bảng, dữ liệu mẫu).
2. Sửa `src/main/resources/META-INF/persistence.xml` (url, user, password).
3. Thư mục ảnh upload mặc định: `<user.home>/upload` (đổi trong `util/Constant.java`). Đặt một ảnh `avatar.png` vào đó làm ảnh mặc định.
4. Add project vào Tomcat 10.1+ và chạy: `http://localhost:8080/JPAShoppingMVC/`.
5. Test JPA nhanh: chạy `src/test/java/vn/iotstar/configs/Test.java` (Run As > Java Application).

Tài khoản mẫu (mật khẩu 123456): `admin` (roleid 1), `manager` (2), `user` (5).

## Cấu trúc
`entity` → `dao` → `service` → `controller` (JSP trong `WEB-INF/views`).

## Bài 02 – bổ sung
1. **Kích hoạt tài khoản bằng OTP**: đăng ký → tài khoản `active = 0` → gửi OTP 6 số qua email → `/verify-otp`. OTP hết hạn sau 5 phút, tối đa 5 lần nhập sai, gửi lại cách nhau 60 giây.
2. **Mật khẩu mã hóa BCrypt** + **đăng xuất** (`/logout`: hủy session, xóa cookie và token "nhớ tôi" trong DB). Cookie "nhớ tôi" là token ngẫu nhiên, không chứa username.
3. **Quên mật khẩu**: `/forgot-password` → gửi OTP → `/reset-password` nhập OTP + mật khẩu mới.

Cấu hình email: sửa `src/main/resources/mail.properties` hoặc đặt biến môi trường `MAIL_USER`, `MAIL_PASSWORD` (Gmail dùng App password). Nếu chưa cấu hình, OTP được in ra console Tomcat để test.
DB cũ: chạy `sql/update_bai02.sql`. DB mới: chạy `sql/JPAShopping.sql`.

## Bài 03 – Videos
- Bảng `Videos` (1 user – n videos, tùy chọn thuộc 1 category). DB cũ: chạy `sql/update_bai03.sql` (sẽ xóa bảng Videos cũ). DB mới: `sql/JPAShopping.sql`.
- `/home`: 10 video mới nhất · `/video`: video của user đang đăng nhập, 6 video/trang (`?page=n`) · `/watch?id=`: chi tiết + phát video (file upload, link YouTube hoặc link mp4).
- CRUD: `/video/add`, `/video/edit?id=`, `/video/delete` (POST). Chỉ chủ video hoặc admin được sửa/xóa.
- Upload bằng Multipart (`@MultipartConfig` + `Part`): video tối đa 200MB (mp4, webm, ogg), ảnh đại diện (png, jpg, gif, webp). File lưu ở `Constant.DIR`, phát qua `/media` (hỗ trợ Range để tua), ảnh qua `/image`.
- Demo upload theo tài liệu: `/multiPartServlet`.
