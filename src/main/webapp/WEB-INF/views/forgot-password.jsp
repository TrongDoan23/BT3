<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ taglib uri="jakarta.tags.core" prefix="c" %>
<%@ taglib prefix="f" tagdir="/WEB-INF/tags" %>
<!DOCTYPE html>
<html lang="vi">
<head>
<title>Quên mật khẩu</title>
</head>
<body>
<h1 class="h4 text-center mb-2">Quên mật khẩu</h1>
<p class="text-muted text-center small">Nhập email đã đăng ký, chúng tôi sẽ gửi mã OTP để đặt lại mật khẩu.</p>
<c:if test="${info != null}"><div class="alert alert-success" role="alert"><c:out value="${info}"/></div></c:if>
<c:if test="${alert != null}"><div class="alert alert-danger" role="alert"><c:out value="${alert}"/></div></c:if>
<form action="${pageContext.request.contextPath}/forgot-password" method="post" class="needs-validation" novalidate>
  <f:field name="email" label="Email" type="email" value="${param.email}" isRequired="true" attrs='maxlength="255" autocomplete="email" autofocus' msg="Vui lòng nhập email hợp lệ"/>
  <button type="submit" class="btn btn-primary w-100">Gửi mã OTP</button>
</form>
<p class="text-center small mt-3 mb-0"><a href="${pageContext.request.contextPath}/login">Quay lại đăng nhập</a></p>
</body>
</html>
