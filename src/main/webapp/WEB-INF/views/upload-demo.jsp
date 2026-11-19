<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ taglib uri="jakarta.tags.core" prefix="c" %>
<!DOCTYPE html>
<html lang="vi">
<head>
<meta charset="UTF-8">
<meta name="viewport" content="width=device-width, initial-scale=1">
<title>Upload demo</title>
</head>
<body>
<%@ include file="/WEB-INF/views/_topbar.jspf" %>
<main class="wrap"><div class="card wide">
  <h1>Upload file bằng Multipart</h1>
  <p class="muted">Servlet dùng <code>@MultipartConfig</code> và <code>Part</code> (tối đa 5MB / file).</p>
  <form method="post" action="${pageContext.request.contextPath}/multiPartServlet" enctype="multipart/form-data">
    <label for="multiPartServlet">Chọn file</label>
    <input type="file" id="multiPartServlet" name="multiPartServlet">
    <p><button type="submit" class="btn">Upload</button></p>
  </form>
</div></main>
</body>
</html>
