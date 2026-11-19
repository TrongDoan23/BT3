<%@ tag body-content="empty" pageEncoding="UTF-8" %>
<%@ taglib uri="jakarta.tags.core" prefix="c" %>
<%@ attribute name="name" required="true" %>
<%@ attribute name="label" required="true" %>
<%@ attribute name="type" %>
<%@ attribute name="value" %>
<%@ attribute name="isRequired" %>
<%@ attribute name="attrs" %>
<%@ attribute name="msg" %>
<%@ attribute name="hint" %>
<c:set var="err" value="${errors[name]}"/>
<div class="mb-3">
  <label for="${name}" class="form-label fw-semibold"><c:out value="${label}"/><c:if test="${isRequired == 'true'}"> <span class="text-danger">*</span></c:if></label>
  <input type="${empty type ? 'text' : type}" id="${name}" name="${name}" class="form-control${not empty err ? ' is-invalid' : ''}"
         <c:if test="${not empty value}">value="<c:out value='${value}'/>"</c:if>
         <c:if test="${isRequired == 'true'}">required</c:if> ${attrs}>
  <div class="invalid-feedback"><c:out value="${not empty err ? err : (empty msg ? 'Giá trị không hợp lệ' : msg)}"/></div>
  <c:if test="${not empty hint}"><div class="form-text"><c:out value="${hint}"/></div></c:if>
</div>
