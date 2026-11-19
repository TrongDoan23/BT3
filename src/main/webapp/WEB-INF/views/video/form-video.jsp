<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ taglib uri="jakarta.tags.core" prefix="c" %>
<%@ taglib uri="jakarta.tags.functions" prefix="fn" %>
<%@ taglib prefix="f" tagdir="/WEB-INF/tags" %>
<!DOCTYPE html>
<html lang="vi">
<head>
<title><c:out value="${formTitle}"/></title>
</head>
<body>
<div class="row justify-content-center"><div class="col-lg-8">
<div class="card shadow-sm"><div class="card-body p-4">
  <h1 class="h4 mb-4"><c:out value="${formTitle}"/></h1>
  <c:set var="useParam" value="${not empty errors || video == null}"/>
  <c:set var="titleVal" value="${useParam ? param.title : video.title}"/>
  <c:set var="descVal" value="${useParam ? param.description : video.description}"/>
  <c:set var="linkVal" value="${useParam ? param.videoLink : (fn:startsWith(video.videoUrl, 'http') ? video.videoUrl : '')}"/>
  <c:set var="cateVal" value="${useParam ? param.cateid : video.category.cateid}"/>
  <c:set var="isActive" value="${not empty errors ? param.active == 'on' : (video == null ? true : video.active)}"/>
  <form action="${formAction}" method="post" enctype="multipart/form-data" class="needs-validation" novalidate>
    <c:if test="${video != null}"><input type="hidden" name="id" value="${video.id}"></c:if>

    <f:field name="title" label="Tiêu đề" value="${titleVal}" isRequired="true" attrs='maxlength="255"' msg="Vui lòng nhập tiêu đề (tối đa 255 ký tự)"/>

    <div class="mb-3">
      <label for="description" class="form-label fw-semibold">Mô tả</label>
      <textarea id="description" name="description" rows="4" maxlength="2000" class="form-control${not empty errors.description ? ' is-invalid' : ''}"><c:out value="${descVal}"/></textarea>
      <div class="invalid-feedback"><c:out value="${not empty errors.description ? errors.description : 'Mô tả tối đa 2000 ký tự'}"/></div>
    </div>

    <div class="mb-3">
      <label for="cateid" class="form-label fw-semibold">Danh mục</label>
      <select id="cateid" name="cateid" class="form-select">
        <option value="">-- Không chọn --</option>
        <c:forEach items="${categories}" var="c">
          <option value="${c.cateid}" ${cateVal == c.cateid ? 'selected' : ''}><c:out value="${c.catename}"/></option>
        </c:forEach>
      </select>
    </div>

    <f:field name="videoFile" label="File video" type="file" attrs='accept="video/mp4,video/webm,video/ogg"' hint="mp4, webm, ogg – tối đa 200MB" msg="Video chỉ nhận mp4, webm, ogg"/>
    <f:field name="videoLink" label="Hoặc link video (YouTube / .mp4)" type="text" value="${linkVal}" attrs='maxlength="500" pattern="https?://\S+" placeholder="https://www.youtube.com/watch?v=..."' msg="Link phải bắt đầu bằng http:// hoặc https://" hint="${video != null ? 'Để trống cả hai ô để giữ video hiện tại.' : 'Bắt buộc chọn file hoặc nhập link.'}"/>

    <c:if test="${video != null && not empty video.poster}">
      <c:url var="posterUrl" value="/image"><c:param name="fname" value="${video.poster}"/></c:url>
      <img class="img-thumbnail mb-2" style="max-width:200px" src="${posterUrl}" alt="Ảnh hiện tại">
    </c:if>
    <f:field name="poster" label="Ảnh đại diện" type="file" attrs='accept="image/*"' msg="Ảnh chỉ nhận png, jpg, gif, webp"/>

    <div class="form-check form-switch mb-4">
      <input class="form-check-input" type="checkbox" role="switch" id="active" name="active" ${isActive ? 'checked' : ''}>
      <label class="form-check-label" for="active">Hiển thị công khai</label>
    </div>

    <button type="submit" class="btn btn-primary"><i class="bi bi-save"></i> Lưu</button>
    <a class="btn btn-outline-secondary" href="${pageContext.request.contextPath}/video">Hủy</a>
  </form>
</div></div>
</div></div>
</body>
</html>
