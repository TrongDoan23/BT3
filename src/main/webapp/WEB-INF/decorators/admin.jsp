<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ taglib uri="jakarta.tags.core" prefix="c" %>
<!DOCTYPE html>
<html lang="vi">
<head>
<%@ include file="/WEB-INF/decorators/_head.jspf" %>
<title><sitemesh:write property="title"/> | Quản trị</title>
<sitemesh:write property="head"/>
</head>
<body class="bg-body-tertiary">
<nav class="navbar navbar-dark bg-primary">
  <div class="container-fluid">
    <a class="navbar-brand fw-bold" href="${pageContext.request.contextPath}/admin/home"><i class="bi bi-speedometer2"></i> Dashboard</a>
    <div class="d-flex align-items-center text-white gap-3">
      <span class="d-none d-sm-inline">Xin chào, <c:out value="${sessionScope.account.fullName}"/></span>
      <a class="btn btn-light btn-sm" href="${pageContext.request.contextPath}/logout"><i class="bi bi-box-arrow-right"></i> Đăng xuất</a>
    </div>
  </div>
</nav>
<div class="container-fluid">
  <div class="row">
    <aside class="col-md-3 col-lg-2 bg-white border-end py-3 min-vh-100">
      <div class="list-group list-group-flush">
        <a class="list-group-item list-group-item-action" href="${pageContext.request.contextPath}/admin/home"><i class="bi bi-house-door"></i> Tổng quan</a>
        <a class="list-group-item list-group-item-action" href="${pageContext.request.contextPath}/admin/category/list"><i class="bi bi-folder2-open"></i> Danh sách danh mục</a>
        <a class="list-group-item list-group-item-action" href="${pageContext.request.contextPath}/admin/category/add"><i class="bi bi-folder-plus"></i> Thêm danh mục</a>
        <a class="list-group-item list-group-item-action" href="${pageContext.request.contextPath}/video"><i class="bi bi-collection-play"></i> Video của tôi</a>
        <a class="list-group-item list-group-item-action" href="${pageContext.request.contextPath}/home"><i class="bi bi-box-arrow-up-right"></i> Xem trang web</a>
      </div>
    </aside>
    <main class="col-md-9 col-lg-10 p-4">
      <sitemesh:write property="body"/>
    </main>
  </div>
</div>
<%@ include file="/WEB-INF/decorators/_scripts.jspf" %>
</body>
</html>
