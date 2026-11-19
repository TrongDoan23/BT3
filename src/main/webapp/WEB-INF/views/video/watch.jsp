<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ taglib uri="jakarta.tags.core" prefix="c" %>
<!DOCTYPE html>
<html lang="vi">
<head>
<meta charset="UTF-8">
<meta name="viewport" content="width=device-width, initial-scale=1">
<title><c:out value="${video.title}"/></title>
</head>
<body>
<%@ include file="/WEB-INF/views/_topbar.jspf" %>
<main class="wrap"><div class="wide" style="max-width:900px">
  <c:if test="${not empty video.poster}"><c:url var="posterUrl" value="/image"><c:param name="fname" value="${video.poster}"/></c:url></c:if>
  <c:choose>
    <c:when test="${embedUrl != null}">
      <iframe class="player" src="${embedUrl}" title="<c:out value='${video.title}'/>"
              allow="accelerometer; autoplay; encrypted-media; picture-in-picture; fullscreen" allowfullscreen></iframe>
    </c:when>
    <c:when test="${isLink}">
      <video class="player" controls preload="metadata" poster="${posterUrl}" src="<c:out value='${video.videoUrl}'/>"></video>
    </c:when>
    <c:otherwise>
      <c:url var="videoSrc" value="/media"><c:param name="fname" value="${video.videoUrl}"/></c:url>
      <video class="player" controls preload="metadata" poster="${posterUrl}"><source src="${videoSrc}">Trình duyệt không hỗ trợ phát video.</video>
    </c:otherwise>
  </c:choose>

  <div class="card" style="margin-top:16px">
    <h1 style="margin-top:0"><c:out value="${video.title}"/></h1>
    <p class="muted">${video.views} lượt xem · ${video.createdDateText} · Đăng bởi <strong><c:out value="${video.user.fullName}"/></strong>
      <c:if test="${video.category != null}"> · <span class="badge"><c:out value="${video.category.catename}"/></span></c:if>
      <c:if test="${not video.active}"> · <span class="badge">Đã ẩn</span></c:if></p>
    <p style="white-space:pre-wrap"><c:out value="${video.description}"/></p>
    <p><a href="${pageContext.request.contextPath}/home">« Về trang chủ</a></p>
  </div>
</div></main>
</body>
</html>
