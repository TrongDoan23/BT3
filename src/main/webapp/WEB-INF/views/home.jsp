<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ taglib uri="jakarta.tags.core" prefix="c" %>
<!DOCTYPE html>
<html lang="vi">
<head>
<meta charset="UTF-8">
<meta name="viewport" content="width=device-width, initial-scale=1">
<title>Trang chủ</title>
</head>
<body>
<%@ include file="/WEB-INF/views/_topbar.jspf" %>
<main class="wrap">
  <h1>10 video mới nhất</h1>
  <c:if test="${empty videos}"><div class="card muted">Chưa có video nào.
    <c:if test="${sessionScope.account != null}"><a href="${pageContext.request.contextPath}/video/add">Thêm video đầu tiên</a></c:if></div></c:if>
  <div class="grid">
  <c:forEach items="${videos}" var="v">
    <c:if test="${not empty v.poster}"><c:url var="posterUrl" value="/image"><c:param name="fname" value="${v.poster}"/></c:url></c:if>
    <c:url var="watchUrl" value="/watch"><c:param name="id" value="${v.id}"/></c:url>
    <article class="vcard">
      <a class="thumb-box" href="${watchUrl}" aria-label="Xem <c:out value='${v.title}'/>">
        <c:choose>
          <c:when test="${not empty v.poster}"><img src="${posterUrl}" alt=""></c:when>
          <c:otherwise><span>Video</span></c:otherwise>
        </c:choose>
      </a>
      <div class="body">
        <h3><a href="${watchUrl}"><c:out value="${v.title}"/></a></h3>
        <span class="meta"><c:out value="${v.user.fullName}"/> · ${v.views} lượt xem</span>
        <span class="meta">${v.createdDateText}</span>
      </div>
    </article>
    <c:remove var="posterUrl"/>
  </c:forEach>
  </div>
</main>
</body>
</html>
