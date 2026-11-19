<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ taglib uri="jakarta.tags.core" prefix="c" %>
<%@ taglib prefix="f" tagdir="/WEB-INF/tags" %>
<!DOCTYPE html>
<html lang="vi">
<head>
<title>Đặt lại mật khẩu</title>
</head>
<body>
<h1 class="h4 text-center mb-4">Đặt lại mật khẩu</h1>
<c:if test="${info != null}"><div class="alert alert-success" role="alert"><c:out value="${info}"/></div></c:if>
<c:if test="${alert != null}"><div class="alert alert-danger" role="alert"><c:out value="${alert}"/></div></c:if>
<form action="${pageContext.request.contextPath}/reset-password" method="post" class="needs-validation" novalidate>
  <f:field name="email" label="Email" type="email" value="${param.email}" isRequired="true" attrs='maxlength="255"' msg="Vui lòng nhập email hợp lệ"/>
  <f:field name="otp" label="Mã OTP" value="${param.otp}" isRequired="true" attrs='inputmode="numeric" pattern="[0-9]{6}" maxlength="6" autocomplete="one-time-code"' msg="Mã OTP gồm 6 chữ số"/>
  <f:field name="password" label="Mật khẩu mới" type="password" isRequired="true" attrs='minlength="6" maxlength="50" autocomplete="new-password"' msg="Mật khẩu từ 6 đến 50 ký tự"/>
  <f:field name="confirm" label="Nhập lại mật khẩu mới" type="password" isRequired="true" attrs='data-match="password" autocomplete="new-password"' msg="Mật khẩu nhập lại không khớp"/>
  <div class="d-grid gap-2">
    <button type="submit" class="btn btn-primary">Đổi mật khẩu</button>
    <button type="submit" class="btn btn-outline-secondary" name="action" value="resend" formnovalidate>Gửi lại mã OTP</button>
  </div>
</form>
<p class="text-center small mt-3 mb-0"><a href="${pageContext.request.contextPath}/login">Quay lại đăng nhập</a></p>
</body>
</html>
