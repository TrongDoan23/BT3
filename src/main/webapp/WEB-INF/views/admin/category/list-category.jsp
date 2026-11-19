<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ taglib uri="jakarta.tags.core" prefix="c" %>
<%@ taglib uri="jakarta.tags.functions" prefix="fn" %>
<%@ taglib prefix="f" tagdir="/WEB-INF/tags" %>
<!DOCTYPE html>
<html lang="vi">
<head>
<title>Quản lý danh mục</title>
</head>
<body>
<div class="d-flex flex-wrap justify-content-between align-items-center gap-2 mb-3">
  <h1 class="h3 mb-0">Quản lý danh mục</h1>
  <a class="btn btn-primary" href="${pageContext.request.contextPath}/admin/category/add"><i class="bi bi-plus-circle"></i> Thêm danh mục</a>
</div>
<form class="row g-2 mb-3" method="get" action="${pageContext.request.contextPath}/admin/category/list" role="search">
  <div class="col-sm-8 col-md-6"><input type="search" name="keyword" class="form-control" placeholder="Tìm theo tên danh mục" maxlength="255" value="<c:out value='${keyword}'/>" aria-label="Từ khóa"></div>
  <div class="col-auto"><button class="btn btn-outline-secondary" type="submit"><i class="bi bi-search"></i> Tìm</button></div>
</form>
<div class="card shadow-sm"><div class="table-responsive">
  <table class="table table-hover align-middle mb-0">
    <thead class="table-light"><tr><th style="width:70px">STT</th><th style="width:120px">Hình ảnh</th><th>Tên danh mục</th><th class="text-end" style="width:160px">Hành động</th></tr></thead>
    <tbody>
    <c:forEach items="${cateList}" var="cate" varStatus="STT">
      <c:choose>
          <c:when test="${fn:startsWith(cate.icon, 'http')}"><c:set var="imgUrl" value="${cate.icon}"/></c:when>
          <c:otherwise><c:url var="imgUrl" value="/image"><c:param name="fname" value="${cate.icon}"/></c:url></c:otherwise>
        </c:choose>
      <tr>
        <td>${STT.index + 1}</td>
        <td><img class="img-thumbnail" style="width:90px;height:60px;object-fit:cover" src="${imgUrl}" alt=""></td>
        <td><c:out value="${cate.catename}"/></td>
        <td class="text-end">
          <a class="btn btn-outline-secondary btn-sm" href="<c:url value='/admin/category/edit'><c:param name='id' value='${cate.cateid}'/></c:url>"><i class="bi bi-pencil"></i> Sửa</a>
          <a class="btn btn-outline-danger btn-sm" href="<c:url value='/admin/category/delete'><c:param name='id' value='${cate.cateid}'/></c:url>" onclick="return confirm('Xóa danh mục này?');"><i class="bi bi-trash"></i> Xóa</a>
        </td>
      </tr>
    </c:forEach>
    <c:if test="${empty cateList}"><tr><td colspan="4" class="text-center text-muted py-4">Chưa có danh mục nào.</td></tr></c:if>
    </tbody>
  </table>
</div></div>
</body>
</html>
