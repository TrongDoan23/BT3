<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ taglib uri="jakarta.tags.core" prefix="c" %>
<!DOCTYPE html>
<html lang="vi">
<head>
<meta charset="UTF-8">
<meta name="viewport" content="width=device-width, initial-scale=1">
<title>Kết quả upload</title>
</head>
<body>
<%@ include file="/WEB-INF/views/_topbar.jspf" %>
<main class="wrap"><div class="card wide">
  <h1>Kết quả</h1>
  <p class="alert ok"><c:out value="${message}"/></p>
  <p><a href="${pageContext.request.contextPath}/multiPartServlet">Upload tiếp</a></p>
</div></main>
</body>
</html>
