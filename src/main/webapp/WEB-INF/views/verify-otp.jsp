<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ taglib uri="jakarta.tags.core" prefix="c" %>
<!DOCTYPE html>
<html lang="vi">
<head>
<meta charset="UTF-8">
<meta name="viewport" content="width=device-width, initial-scale=1">
<title>Kích hoạt tài khoản</title>
<link rel="stylesheet" href="${pageContext.request.contextPath}/static/style.css">
</head>
<body>
<main class="card auth">
  <h1>Kích hoạt tài khoản</h1>
  <p class="muted">Nhập mã OTP gồm 6 chữ số đã gửi tới <strong><c:out value="${email}"/></strong>.</p>
  <c:if test="${info != null}"><p class="alert ok"><c:out value="${info}"/></p></c:if>
  <c:if test="${alert != null}"><p class="alert"><c:out value="${alert}"/></p></c:if>
  <form action="${pageContext.request.contextPath}/verify-otp" method="post">
    <input type="hidden" name="email" value="<c:out value='${email}'/>">
    <label for="otp">Mã OTP</label>
    <input type="text" id="otp" name="otp" inputmode="numeric" pattern="[0-9]{6}" maxlength="6" autocomplete="one-time-code" required>
    <p>
      <button type="submit" class="btn">Kích hoạt</button>
      <button type="submit" class="btn secondary" name="action" value="resend" formnovalidate>Gửi lại mã</button>
    </p>
  </form>
  <p class="muted"><a href="${pageContext.request.contextPath}/login">Quay lại đăng nhập</a></p>
</main>
</body>
</html>
