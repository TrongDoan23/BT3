<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ taglib uri="jakarta.tags.core" prefix="c" %>
<%@ taglib prefix="f" tagdir="/WEB-INF/tags" %>
<!DOCTYPE html>
<html lang="vi">
<head>
<title>Tạo tài khoản</title>
</head>
<body>
<h1 class="h4 text-center mb-4">Tạo tài khoản mới</h1>
<c:if test="${info != null}"><div class="alert alert-success" role="alert"><c:out value="${info}"/></div></c:if>
<c:if test="${alert != null}"><div class="alert alert-danger" role="alert"><c:out value="${alert}"/></div></c:if>
<form action="${pageContext.request.contextPath}/register" method="post" class="needs-validation" novalidate>
  <f:field name="username" label="Tài khoản" value="${param.username}" isRequired="true" attrs='minlength="3" maxlength="50" pattern="[A-Za-z0-9_.]{3,50}" autocomplete="username"' msg="Tài khoản 3–50 ký tự, chỉ gồm chữ không dấu, số, . và _"/>
  <f:field name="fullname" label="Họ tên" value="${param.fullname}" isRequired="true" attrs='maxlength="100"' msg="Vui lòng nhập họ tên"/>
  <f:field name="email" label="Email" type="email" value="${param.email}" isRequired="true" attrs='maxlength="255" autocomplete="email"' msg="Vui lòng nhập email hợp lệ"/>
  <f:field name="phone" label="Số điện thoại" type="tel" value="${param.phone}" attrs='pattern="(0|\+84)[0-9]{9,10}" placeholder="0912345678"' msg="Số điện thoại không hợp lệ (vd: 0912345678)"/>
  <f:field name="password" label="Mật khẩu" type="password" isRequired="true" attrs='minlength="6" maxlength="50" autocomplete="new-password"' msg="Mật khẩu từ 6 đến 50 ký tự" hint="Tối thiểu 6 ký tự"/>
  <f:field name="confirm" label="Nhập lại mật khẩu" type="password" isRequired="true" attrs='data-match="password" autocomplete="new-password"' msg="Mật khẩu nhập lại không khớp"/>
  <button type="submit" class="btn btn-primary w-100">Tạo tài khoản</button>
</form>
<p class="text-center text-muted small mt-3 mb-0">Đã có tài khoản? <a href="${pageContext.request.contextPath}/login">Đăng nhập</a></p>
</body>
</html>
