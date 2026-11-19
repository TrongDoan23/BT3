<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ taglib uri="jakarta.tags.core" prefix="c" %>
<!DOCTYPE html>
<html lang="vi">
<head>
<meta charset="UTF-8">
<meta name="viewport" content="width=device-width, initial-scale=1">
<title>Video của tôi</title>
</head>
<body>
<%@ include file="/WEB-INF/views/_topbar.jspf" %>
<main class="wrap">
  <div class="toolbar">
    <h1 style="flex:1;margin:0">Video của tôi <span class="muted" style="font-size:1rem">(${total})</span></h1>
    <a class="btn" href="${pageContext.request.contextPath}/video/add">Thêm video</a>
  </div>
  <c:if test="${empty videos}"><div class="card muted">Bạn chưa có video nào.</div></c:if>
  <div class="grid">
  <c:forEach items="${videos}" var="v">
    <c:if test="${not empty v.poster}"><c:url var="posterUrl" value="/image"><c:param name="fname" value="${v.poster}"/></c:url></c:if>
    <c:url var="watchUrl" value="/watch"><c:param name="id" value="${v.id}"/></c:url>
    <c:url var="editUrl" value="/video/edit"><c:param name="id" value="${v.id}"/></c:url>
    <article class="vcard">
      <a class="thumb-box" href="${watchUrl}" aria-label="Xem <c:out value='${v.title}'/>">
        <c:choose>
          <c:when test="${not empty v.poster}"><img src="${posterUrl}" alt=""></c:when>
          <c:otherwise><span>Video</span></c:otherwise>
        </c:choose>
      </a>
      <div class="body">
        <h3><a href="${watchUrl}"><c:out value="${v.title}"/></a></h3>
        <span class="meta">${v.views} lượt xem · ${v.createdDateText}</span>
        <span class="meta"><c:out value="${v.category.catename}"/></span>
        <div class="actions">
          <span class="badge"><c:out value="${v.active ? 'Đang hiện' : 'Đã ẩn'}"/></span>
          <span style="flex:1"></span>
          <a class="btn secondary small" href="${editUrl}">Sửa</a>
          <form class="inline" method="post" action="${pageContext.request.contextPath}/video/delete"
                onsubmit="return confirm('Xóa video này?');">
            <input type="hidden" name="id" value="${v.id}">
            <button class="btn danger small" type="submit">Xóa</button>
          </form>
        </div>
      </div>
    </article>
    <c:remove var="posterUrl"/>
  </c:forEach>
  </div>

  <c:if test="${totalPages > 1}">
  <nav class="pager" aria-label="Phân trang">
    <c:if test="${currentPage > 1}"><a href="?page=${currentPage - 1}">« Trước</a></c:if>
    <c:forEach begin="1" end="${totalPages}" var="i">
      <c:choose>
        <c:when test="${i == currentPage}"><span class="current" aria-current="page">${i}</span></c:when>
        <c:otherwise><a href="?page=${i}">${i}</a></c:otherwise>
      </c:choose>
    </c:forEach>
    <c:if test="${currentPage < totalPages}"><a href="?page=${currentPage + 1}">Sau »</a></c:if>
  </nav>
  </c:if>
</main>
</body>
</html>
