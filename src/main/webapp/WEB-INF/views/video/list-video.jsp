<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ taglib uri="jakarta.tags.core" prefix="c" %>
<%@ taglib prefix="f" tagdir="/WEB-INF/tags" %>
<!DOCTYPE html>
<html lang="vi">
<head>
<title>Video của tôi</title>
</head>
<body>
<div class="d-flex justify-content-between align-items-center mb-3">
  <h1 class="h3 mb-0">Video của tôi <span class="badge text-bg-secondary">${total}</span></h1>
  <a class="btn btn-primary" href="${pageContext.request.contextPath}/video/add"><i class="bi bi-plus-circle"></i> Thêm video</a>
</div>
<c:if test="${empty videos}"><div class="alert alert-info">Bạn chưa có video nào.</div></c:if>
<div class="row row-cols-1 row-cols-sm-2 row-cols-lg-3 g-3">
<c:forEach items="${videos}" var="v">
  <c:if test="${not empty v.poster}"><c:url var="posterUrl" value="/image"><c:param name="fname" value="${v.poster}"/></c:url></c:if>
    <c:url var="watchUrl" value="/watch"><c:param name="id" value="${v.id}"/></c:url>
  <c:url var="editUrl" value="/video/edit"><c:param name="id" value="${v.id}"/></c:url>
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
        <p class="card-text small text-muted mb-1"><i class="bi bi-eye"></i> ${v.views} · ${v.createdDateText}</p>
        <c:if test="${v.category != null}"><span class="badge text-bg-light border"><c:out value="${v.category.catename}"/></span></c:if>
        <span class="badge ${v.active ? 'text-bg-success' : 'text-bg-secondary'}"><c:out value="${v.active ? 'Đang hiện' : 'Đã ẩn'}"/></span>
      </div>
      <div class="card-footer bg-transparent d-flex gap-2 justify-content-end">
        <a class="btn btn-outline-secondary btn-sm" href="${editUrl}"><i class="bi bi-pencil"></i> Sửa</a>
        <form method="post" action="${pageContext.request.contextPath}/video/delete" onsubmit="return confirm('Xóa video này?');">
          <input type="hidden" name="id" value="${v.id}">
          <button type="submit" class="btn btn-outline-danger btn-sm"><i class="bi bi-trash"></i> Xóa</button>
        </form>
      </div>
    </article>
  </div>
  <c:remove var="posterUrl"/>
</c:forEach>
</div>
<c:if test="${totalPages > 1}">
<nav class="mt-4" aria-label="Phân trang">
  <ul class="pagination justify-content-center">
    <li class="page-item ${currentPage <= 1 ? 'disabled' : ''}"><a class="page-link" href="?page=${currentPage - 1}">« Trước</a></li>
    <c:forEach begin="1" end="${totalPages}" var="i">
      <li class="page-item ${i == currentPage ? 'active' : ''}"><a class="page-link" href="?page=${i}">${i}</a></li>
    </c:forEach>
    <li class="page-item ${currentPage >= totalPages ? 'disabled' : ''}"><a class="page-link" href="?page=${currentPage + 1}">Sau »</a></li>
  </ul>
</nav>
</c:if>
</body>
</html>
