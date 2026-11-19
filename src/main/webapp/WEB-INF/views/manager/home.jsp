<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ taglib uri="jakarta.tags.core" prefix="c" %>
<%@ taglib prefix="f" tagdir="/WEB-INF/tags" %>
<!DOCTYPE html>
<html lang="vi">
<head>
<title>Trang quản lý</title>
</head>
<body>
<div class="card shadow-sm"><div class="card-body">
  <h1 class="h3">Trang quản lý</h1>
  <p>Xin chào, <strong><c:out value="${sessionScope.account.fullName}"/></strong>. Bạn đang đăng nhập với quyền manager.</p>
  <a class="btn btn-primary" href="${pageContext.request.contextPath}/video">Video của tôi</a>
</div></div>
</body>
</html>
