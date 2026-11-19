<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ taglib uri="jakarta.tags.core" prefix="c" %>
<%@ taglib prefix="f" tagdir="/WEB-INF/tags" %>
<!DOCTYPE html>
<html lang="vi">
<head>
<title><c:out value="${video.title}"/></title>
</head>
<body>
<div class="row justify-content-center"><div class="col-lg-10">
<c:if test="${not empty video.poster}"><c:url var="posterUrl" value="/image"><c:param name="fname" value="${video.poster}"/></c:url></c:if>
<div class="ratio ratio-16x9 bg-black rounded overflow-hidden shadow-sm">
<c:choose>
  <c:when test="${embedUrl != null}">
    <iframe src="${embedUrl}" title="<c:out value='${video.title}'/>" allow="accelerometer; autoplay; encrypted-media; picture-in-picture; fullscreen" allowfullscreen></iframe>
  </c:when>
  <c:when test="${isLink}">
    <video controls preload="metadata" poster="${posterUrl}" src="<c:out value='${video.videoUrl}'/>"></video>
  </c:when>
  <c:otherwise>
    <c:url var="videoSrc" value="/media"><c:param name="fname" value="${video.videoUrl}"/></c:url>
    <video controls preload="metadata" poster="${posterUrl}"><source src="${videoSrc}">Trình duyệt không hỗ trợ phát video.</video>
  </c:otherwise>
</c:choose>
</div>
<div class="card shadow-sm mt-3"><div class="card-body">
  <h1 class="h4"><c:out value="${video.title}"/></h1>
  <p class="text-muted small">
    <i class="bi bi-eye"></i> ${video.views} lượt xem · ${video.createdDateText} · Đăng bởi <strong><c:out value="${video.user.fullName}"/></strong>
    <c:if test="${video.category != null}"> · <span class="badge text-bg-light border"><c:out value="${video.category.catename}"/></span></c:if>
    <c:if test="${not video.active}"> · <span class="badge text-bg-secondary">Đã ẩn</span></c:if>
  </p>
  <p style="white-space:pre-wrap"><c:out value="${video.description}"/></p>
  <a href="${pageContext.request.contextPath}/home"><i class="bi bi-arrow-left"></i> Về trang chủ</a>
</div></div>
</div></div>
</body>
</html>
