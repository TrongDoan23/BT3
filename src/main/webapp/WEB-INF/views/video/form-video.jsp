<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ taglib uri="jakarta.tags.core" prefix="c" %>
<%@ taglib uri="jakarta.tags.functions" prefix="fn" %>
<!DOCTYPE html>
<html lang="vi">
<head>
<meta charset="UTF-8">
<meta name="viewport" content="width=device-width, initial-scale=1">
<title><c:out value="${formTitle}"/></title>
</head>
<body>
<%@ include file="/WEB-INF/views/_topbar.jspf" %>
<main class="wrap"><div class="card wide">
  <h1><c:out value="${formTitle}"/></h1>
  <c:if test="${alert != null}"><p class="alert"><c:out value="${alert}"/></p></c:if>
  <c:set var="titleVal" value="${not empty param.title ? param.title : video.title}"/>
  <c:set var="descVal" value="${not empty param.description ? param.description : video.description}"/>
  <c:set var="isActive" value="${video == null ? true : video.active}"/>
  <c:set var="linkVal" value="${not empty param.videoLink ? param.videoLink : (video != null && fn:startsWith(video.videoUrl, 'http') ? video.videoUrl : '')}"/>
  <form action="${formAction}" method="post" enctype="multipart/form-data">
    <c:if test="${video != null}"><input type="hidden" name="id" value="${video.id}"></c:if>

    <label for="title">Tiêu đề</label>
    <input type="text" id="title" name="title" value="<c:out value='${titleVal}'/>" required>

    <label for="description">Mô tả</label>
    <textarea id="description" name="description"><c:out value="${descVal}"/></textarea>

    <label for="cateid">Danh mục</label>
    <select id="cateid" name="cateid">
      <option value="">-- Không chọn --</option>
      <c:forEach items="${categories}" var="c">
        <option value="${c.cateid}" ${(not empty param.cateid ? param.cateid == c.cateid : video.category.cateid == c.cateid) ? 'selected' : ''}><c:out value="${c.catename}"/></option>
      </c:forEach>
    </select>

    <label for="videoFile">File video (mp4, webm, ogg – tối đa 200MB)</label>
    <input type="file" id="videoFile" name="videoFile" accept="video/mp4,video/webm,video/ogg">
    <label for="videoLink">Hoặc link video (YouTube hoặc link .mp4)</label>
    <input type="text" id="videoLink" name="videoLink" placeholder="https://www.youtube.com/watch?v=..." value="<c:out value='${linkVal}'/>">
    <c:if test="${video != null}"><p class="hint">Để trống cả hai ô để giữ video hiện tại.</p></c:if>

    <label for="poster">Ảnh đại diện</label>
    <c:if test="${video != null && not empty video.poster}">
      <c:url var="posterUrl" value="/image"><c:param name="fname" value="${video.poster}"/></c:url>
      <p><img class="thumb" style="width:160px;height:90px" src="${posterUrl}" alt="Ảnh hiện tại"></p>
    </c:if>
    <input type="file" id="poster" name="poster" accept="image/*">

    <label class="check"><input type="checkbox" name="active" ${isActive ? 'checked' : ''}> Hiển thị công khai</label>

    <p>
      <button type="submit" class="btn">Lưu</button>
      <a class="btn secondary" href="${pageContext.request.contextPath}/video">Hủy</a>
    </p>
  </form>
</div></main>
</body>
</html>
