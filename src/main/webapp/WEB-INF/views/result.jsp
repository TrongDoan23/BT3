<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ taglib uri="jakarta.tags.core" prefix="c" %>
<%@ taglib prefix="f" tagdir="/WEB-INF/tags" %>
<!DOCTYPE html>
<html lang="vi">
<head>
<title>Kết quả upload</title>
</head>
<body>
<div class="card shadow-sm" style="max-width:640px"><div class="card-body p-4">
  <h1 class="h4">Kết quả</h1>
  <div class="alert alert-info"><c:out value="${message}"/></div>
  <a href="${pageContext.request.contextPath}/multiPartServlet">Upload tiếp</a>
</div></div>
</body>
</html>
