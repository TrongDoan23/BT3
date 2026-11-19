<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ taglib uri="jakarta.tags.core" prefix="c" %>
<%@ taglib prefix="f" tagdir="/WEB-INF/tags" %>
<!DOCTYPE html>
<html lang="vi">
<head>
<title>Tổng quan</title>
</head>
<body>
<h1 class="h3 mb-4">Trang quản trị</h1>
<div class="row g-3">
  <div class="col-md-6 col-xl-4">
    <div class="card shadow-sm h-100"><div class="card-body">
      <h2 class="h5"><i class="bi bi-folder2-open text-primary"></i> Danh mục</h2>
      <p class="text-muted">Thêm, sửa, xóa danh mục sản phẩm.</p>
      <a class="btn btn-primary" href="${pageContext.request.contextPath}/admin/category/list">Quản lý danh mục</a>
    </div></div>
  </div>
  <div class="col-md-6 col-xl-4">
    <div class="card shadow-sm h-100"><div class="card-body">
      <h2 class="h5"><i class="bi bi-collection-play text-danger"></i> Video</h2>
      <p class="text-muted">Quản lý video bạn đã đăng.</p>
      <a class="btn btn-outline-primary" href="${pageContext.request.contextPath}/video">Video của tôi</a>
    </div></div>
  </div>
</div>
</body>
</html>
