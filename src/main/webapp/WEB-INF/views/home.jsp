<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ taglib uri="jakarta.tags.core" prefix="c" %>
<%@ taglib prefix="f" tagdir="/WEB-INF/tags" %>
<!DOCTYPE html>
<html lang="vi">
<head>
<title>Trang chủ</title>
</head>
<body>
<div class="d-flex justify-content-between align-items-center mb-3">
  <h1 class="h3 mb-0"><i class="bi bi-fire text-danger"></i> 10 video mới nhất</h1>
</div>
<c:if test="${empty videos}">
  <div class="alert alert-info">Chưa có video nào.
    <c:if test="${sessionScope.account != null}"><a class="alert-link" href="${pageContext.request.contextPath}/video/add">Thêm video đầu tiên</a></c:if></div>
</c:if>
<div class="row row-cols-1 row-cols-sm-2 row-cols-lg-3 row-cols-xl-5 g-3">
<c:forEach items="${videos}" var="v">
  <c:if test="${not empty v.poster}"><c:url var="posterUrl" value="/image"><c:param name="fname" value="${v.poster}"/></c:url></c:if>
    <c:url var="watchUrl" value="/watch"><c:param name="id" value="${v.id}"/></c:url>
  <div class="col">
    <article class="card h-100 shadow-sm">
      <a class="video-thumb text-decoration-none text-muted" href="${watchUrl}" aria-label="Xem video">
          <c:choose>
            <c:when test="${not empty v.poster}"><img class="thumb-cover" src="${posterUrl}" alt=""></c:when>
            <c:otherwise><i class="bi bi-play-circle fs-1"></i></c:otherwise>
          </c:choose>
        </a>
      <div class="card-body">
        <h2 class="card-title h6"><a href="${watchUrl}"><c:out value="${v.title}"/></a></h2>
        <p class="card-text small text-muted mb-0"><i class="bi bi-person"></i> <c:out value="${v.user.fullName}"/></p>
        <p class="card-text small text-muted mb-0"><i class="bi bi-eye"></i> ${v.views} · ${v.createdDateText}</p>
      </div>
    </article>
  </div>
  <c:remove var="posterUrl"/>
</c:forEach>
</div>
</body>
</html>
