<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ taglib uri="jakarta.tags.core" prefix="c" %>
<%@ taglib prefix="f" tagdir="/WEB-INF/tags" %>
<!DOCTYPE html>
<html lang="vi">
<head>
<title>Đăng nhập</title>
</head>
<body>
<h1 class="h4 text-center mb-4">Đăng nhập</h1>
<c:if test="${param.verified == '1'}"><div class="alert alert-success">Kích hoạt tài khoản thành công. Hãy đăng nhập.</div></c:if>
<c:if test="${param.reset == '1'}"><div class="alert alert-success">Đổi mật khẩu thành công. Hãy đăng nhập.</div></c:if>
<c:if test="${alert != null}"><div class="alert alert-danger" role="alert"><c:out value="${alert}"/>
  <c:if test="${activateEmail != null}"><a class="alert-link" href="${pageContext.request.contextPath}/verify-otp?email=${activateEmail}">Kích hoạt ngay</a></c:if></div></c:if>
<form action="${pageContext.request.contextPath}/login" method="post" class="needs-validation" novalidate>
  <f:field name="username" label="Tài khoản" value="${param.username}" isRequired="true" attrs='maxlength="100" autocomplete="username" autofocus' msg="Vui lòng nhập tài khoản"/>
  <f:field name="password" label="Mật khẩu" type="password" isRequired="true" attrs='maxlength="100" autocomplete="current-password"' msg="Vui lòng nhập mật khẩu"/>
  <div class="d-flex justify-content-between align-items-center mb-3">
    <div class="form-check"><input class="form-check-input" type="checkbox" id="remember" name="remember"><label class="form-check-label" for="remember">Nhớ tôi</label></div>
    <a class="small" href="${pageContext.request.contextPath}/forgot-password">Quên mật khẩu?</a>
  </div>
  <button type="submit" class="btn btn-primary w-100">Đăng nhập</button>
</form>
<p class="text-center text-muted small mt-3 mb-0">Chưa có tài khoản? <a href="${pageContext.request.contextPath}/register">Đăng ký</a></p>
</body>
</html>
