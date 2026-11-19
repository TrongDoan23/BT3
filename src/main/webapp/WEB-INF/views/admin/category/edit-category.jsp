<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ taglib uri="jakarta.tags.core" prefix="c" %>
<%@ taglib uri="jakarta.tags.functions" prefix="fn" %>
<%@ taglib prefix="f" tagdir="/WEB-INF/tags" %>
<!DOCTYPE html>
<html lang="vi">
<head>
<title>Sửa danh mục</title>
</head>
<body>
<h1 class="h3 mb-3">Sửa danh mục</h1>
<c:set var="cate" value="${category}"/>
<c:choose>
<c:when test="${fn:startsWith(cate.icon, 'http')}"><c:set var="imgUrl" value="${cate.icon}"/></c:when>
<c:otherwise><c:url var="imgUrl" value="/image"><c:param name="fname" value="${cate.icon}"/></c:url></c:otherwise>
        </c:choose>
<div class="card shadow-sm" style="max-width:640px"><div class="card-body p-4">
  <form action="${pageContext.request.contextPath}/admin/category/edit" method="post" enctype="multipart/form-data" class="needs-validation" novalidate>
    <input type="hidden" name="cateid" value="${category.cateid}">
    <f:field name="catename" label="Tên danh mục" value="${empty errors ? category.catename : param.catename}" isRequired="true" attrs='maxlength="255"' msg="Vui lòng nhập tên danh mục (tối đa 255 ký tự)"/>
    <img class="img-thumbnail mb-2" style="max-width:160px" src="${imgUrl}" alt="Ảnh hiện tại">
    <f:field name="icon" label="Ảnh đại diện mới" type="file" attrs='accept="image/*"' hint="Bỏ trống để giữ ảnh cũ. png, jpg, gif, webp – tối đa 5MB" msg="Ảnh chỉ nhận png, jpg, gif, webp"/>
    <button type="submit" class="btn btn-primary"><i class="bi bi-save"></i> Lưu</button>
    <a class="btn btn-outline-secondary" href="${pageContext.request.contextPath}/admin/category/list">Hủy</a>
  </form>
</div></div>
</body>
</html>
