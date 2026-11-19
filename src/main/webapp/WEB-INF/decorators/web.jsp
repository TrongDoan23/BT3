<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ taglib uri="jakarta.tags.core" prefix="c" %>
<!DOCTYPE html>
<html lang="vi">
<head>
<%@ include file="/WEB-INF/decorators/_head.jspf" %>
<title><sitemesh:write property="title"/> | JPA Shopping</title>
<sitemesh:write property="head"/>
</head>
<body class="d-flex flex-column min-vh-100 bg-body-tertiary">
<nav class="navbar navbar-expand-lg navbar-dark bg-dark sticky-top">
  <div class="container">
    <a class="navbar-brand fw-bold" href="${pageContext.request.contextPath}/home"><i class="bi bi-play-btn-fill text-danger"></i> JPA Shopping</a>
    <button class="navbar-toggler" type="button" data-bs-toggle="collapse" data-bs-target="#mainNav" aria-controls="mainNav" aria-expanded="false" aria-label="Mở menu">
      <span class="navbar-toggler-icon"></span>
    </button>
    <div class="collapse navbar-collapse" id="mainNav">
      <ul class="navbar-nav me-auto mb-2 mb-lg-0">
        <li class="nav-item"><a class="nav-link" href="${pageContext.request.contextPath}/home"><i class="bi bi-house"></i> Trang chủ</a></li>
        <c:if test="${sessionScope.account != null}">
          <li class="nav-item"><a class="nav-link" href="${pageContext.request.contextPath}/video"><i class="bi bi-collection-play"></i> Video của tôi</a></li>
          <li class="nav-item"><a class="nav-link" href="${pageContext.request.contextPath}/video/add"><i class="bi bi-plus-circle"></i> Thêm video</a></li>
          <li class="nav-item"><a class="nav-link" href="${pageContext.request.contextPath}/multiPartServlet"><i class="bi bi-upload"></i> Upload demo</a></li>
        </c:if>
        <c:if test="${sessionScope.account.roleid == 1}">
          <li class="nav-item"><a class="nav-link" href="${pageContext.request.contextPath}/admin/home"><i class="bi bi-speedometer2"></i> Quản trị</a></li>
        </c:if>
      </ul>
      <ul class="navbar-nav">
        <c:choose>
          <c:when test="${sessionScope.account == null}">
            <li class="nav-item"><a class="nav-link" href="${pageContext.request.contextPath}/login">Đăng nhập</a></li>
            <li class="nav-item"><a class="btn btn-danger btn-sm ms-lg-2 mt-1 mt-lg-0" href="${pageContext.request.contextPath}/register">Đăng ký</a></li>
          </c:when>
          <c:otherwise>
            <li class="nav-item"><span class="navbar-text me-lg-3"><i class="bi bi-person-circle"></i> <c:out value="${sessionScope.account.fullName}"/></span></li>
            <li class="nav-item"><a class="btn btn-outline-light btn-sm mt-1 mt-lg-0" href="${pageContext.request.contextPath}/logout"><i class="bi bi-box-arrow-right"></i> Đăng xuất</a></li>
          </c:otherwise>
        </c:choose>
      </ul>
    </div>
  </div>
</nav>

<main class="container py-4 flex-grow-1">
  <sitemesh:write property="body"/>
</main>

<footer class="bg-dark text-white-50 py-3 mt-auto">
  <div class="container small d-flex justify-content-between flex-wrap">
    <span>&copy; 2026 JPA Shopping – Lập trình Web</span>
    <span>Servlet · JSP · JPA · SiteMesh 3 · Bootstrap 5</span>
  </div>
</footer>
<%@ include file="/WEB-INF/decorators/_scripts.jspf" %>
</body>
</html>
