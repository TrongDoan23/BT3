<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ taglib uri="jakarta.tags.core" prefix="c" %>
<%@ taglib prefix="f" tagdir="/WEB-INF/tags" %>
<!DOCTYPE html>
<html lang="vi">
<head>
<title>Kích hoạt tài khoản</title>
</head>
<body>
<h1 class="h4 text-center mb-2">Kích hoạt tài khoản</h1>
<p class="text-muted text-center small">Nhập mã OTP gồm 6 chữ số đã gửi tới email của bạn.</p>
<c:if test="${info != null}"><div class="alert alert-success" role="alert"><c:out value="${info}"/></div></c:if>
<c:if test="${alert != null}"><div class="alert alert-danger" role="alert"><c:out value="${alert}"/></div></c:if>
<form action="${pageContext.request.contextPath}/verify-otp" method="post" class="needs-validation" novalidate>
  <f:field name="email" label="Email" type="email" value="${param.email}" isRequired="true" attrs='maxlength="255"' msg="Vui lòng nhập email hợp lệ"/>
  <f:field name="otp" label="Mã OTP" value="${param.otp}" isRequired="true" attrs='inputmode="numeric" pattern="[0-9]{6}" maxlength="6" autocomplete="one-time-code" autofocus' msg="Mã OTP gồm 6 chữ số"/>
  <div class="d-grid gap-2">
    <button type="submit" class="btn btn-primary">Kích hoạt</button>
    <button type="submit" class="btn btn-outline-secondary" name="action" value="resend" formnovalidate>Gửi lại mã</button>
  </div>
</form>
<p class="text-center small mt-3 mb-0"><a href="${pageContext.request.contextPath}/login">Quay lại đăng nhập</a></p>
</body>
</html>
