<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ taglib uri="jakarta.tags.core" prefix="c" %>
<!DOCTYPE html>
<html lang="vi">
<head>
<%@ include file="/WEB-INF/decorators/_head.jspf" %>
<title><sitemesh:write property="title"/> | JPA Shopping</title>
<sitemesh:write property="head"/>
</head>
<body class="bg-body-tertiary">
<div class="container min-vh-100 d-flex flex-column justify-content-center py-4">
  <div class="row justify-content-center">
    <div class="col-12 col-sm-10 col-md-7 col-lg-5">
      <a class="d-block text-center text-decoration-none text-dark fs-3 fw-bold mb-3" href="${pageContext.request.contextPath}/home">
        <i class="bi bi-play-btn-fill text-danger"></i> JPA Shopping
      </a>
      <div class="card shadow-sm border-0">
        <div class="card-body p-4">
          <sitemesh:write property="body"/>
        </div>
      </div>
    </div>
  </div>
</div>
<%@ include file="/WEB-INF/decorators/_scripts.jspf" %>
</body>
</html>
