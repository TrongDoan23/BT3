<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ taglib uri="jakarta.tags.core" prefix="c" %>
<!DOCTYPE html>
<html lang="vi">
<head>
<meta charset="UTF-8">
<meta name="viewport" content="width=device-width, initial-scale=1">
<title>Đặt lại mật khẩu</title>
<link rel="stylesheet" href="${pageContext.request.contextPath}/static/style.css">
</head>
<body>
<main class="card auth">
  <h1>Đặt lại mật khẩu</h1>
  <c:if test="${info != null}"><p class="alert ok"><c:out value="${info}"/></p></c:if>
  <c:if test="${alert != null}"><p class="alert"><c:out value="${alert}"/></p></c:if>
  <form action="${pageContext.request.contextPath}/reset-password" method="post">
    <label for="email">Email</label>
    <input type="email" id="email" name="email" value="<c:out value='${email}'/>" required>
    <label for="otp">Mã OTP</label>
    <input type="text" id="otp" name="otp" inputmode="numeric" pattern="[0-9]{6}" maxlength="6" autocomplete="one-time-code">
    <label for="password">Mật khẩu mới</label>
    <input type="password" id="password" name="password" autocomplete="new-password">
    <label for="confirm">Nhập lại mật khẩu mới</label>
    <input type="password" id="confirm" name="confirm" autocomplete="new-password">
    <p>
      <button type="submit" class="btn">Đổi mật khẩu</button>
      <button type="submit" class="btn secondary" name="action" value="resend" formnovalidate>Gửi lại mã</button>
    </p>
  </form>
  <p class="muted"><a href="${pageContext.request.contextPath}/login">Quay lại đăng nhập</a></p>
</main>
</body>
</html>
