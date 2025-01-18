<%@ taglib uri="jakarta.tags.core" prefix="c"%>
<%@ taglib uri="jakarta.tags.fmt" prefix="fmt"%>
<%@ taglib uri="jakarta.tags.functions" prefix="fn"%>
<%@ taglib uri="http://www.springframework.org/tags" prefix="spring"%>
<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<Link rel="stylesheet"
	href="${pageContext.request.contextPath}/assets/css/jblog.css">
<div id="header">
	<h1>
		<a href="${pageContext.request.contextPath }/${authUser.id }"
			style="color: white">${blogVo.title }</a>
	</h1>
	<ul>
		<c:choose>
			<c:when test="${empty authUser }">
				<li><a href="${pageContext.request.contextPath }/user/login">로그인</a></li>
			</c:when>
			<c:otherwise>
				<li><a href="${pageContext.request.contextPath }/user/logout">로그아웃</a></li>
				<c:if test="${authUser.id == blogVo.id}">
					<li><a
						href="${pageContext.request.contextPath }/${authUser.id }/admin">블로그
							관리</a></li>
				</c:if>
			</c:otherwise>
		</c:choose>
	</ul>
</div>
