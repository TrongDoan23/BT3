# JPAShoppingMVC – Bài tập 02

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
