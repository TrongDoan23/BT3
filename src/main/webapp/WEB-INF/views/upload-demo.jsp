<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ taglib uri="jakarta.tags.core" prefix="c" %>
<%@ taglib prefix="f" tagdir="/WEB-INF/tags" %>
<!DOCTYPE html>
<html lang="vi">
<head>
<title>Upload demo</title>
</head>
<body>
<div class="card shadow-sm" style="max-width:640px"><div class="card-body p-4">
  <h1 class="h4">Upload file bằng Multipart</h1>
  <p class="text-muted small">Servlet dùng <code>@MultipartConfig</code> và <code>Part</code> (tối đa 5MB / file).</p>
  <form method="post" action="${pageContext.request.contextPath}/multiPartServlet" enctype="multipart/form-data" class="needs-validation" novalidate>
    <f:field name="multiPartServlet" label="Chọn file" type="file" isRequired="true" msg="Vui lòng chọn file"/>
    <button type="submit" class="btn btn-primary"><i class="bi bi-upload"></i> Upload</button>
  </form>
</div></div>
</body>
</html>
