<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ taglib uri="jakarta.tags.core" prefix="c" %>
<%@ taglib prefix="f" tagdir="/WEB-INF/tags" %>
<!DOCTYPE html>
<html lang="vi">
<head>
<title>Thêm danh mục</title>
</head>
<body>
<h1 class="h3 mb-3">Thêm danh mục</h1>
<div class="card shadow-sm" style="max-width:640px"><div class="card-body p-4">
  <form action="${pageContext.request.contextPath}/admin/category/add" method="post" enctype="multipart/form-data" class="needs-validation" novalidate>
    <f:field name="catename" label="Tên danh mục" value="${param.catename}" isRequired="true" attrs='maxlength="255" autofocus' msg="Vui lòng nhập tên danh mục (tối đa 255 ký tự)"/>
    <f:field name="icon" label="Ảnh đại diện" type="file" attrs='accept="image/*"' hint="png, jpg, gif, webp – tối đa 5MB" msg="Ảnh chỉ nhận png, jpg, gif, webp"/>
    <button type="submit" class="btn btn-primary"><i class="bi bi-plus-circle"></i> Thêm</button>
    <a class="btn btn-outline-secondary" href="${pageContext.request.contextPath}/admin/category/list">Hủy</a>
  </form>
</div></div>
</body>
</html>
