<%@ taglib uri="jakarta.tags.core" prefix="c"%>
<%@ taglib uri="jakarta.tags.fmt" prefix="fmt"%>
<%@ taglib uri="jakarta.tags.functions" prefix="fn"%>
<%@ page language="java" contentType="text/html; charset=utf-8"
	pageEncoding="utf-8"%>
<!doctype html>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>JBlog</title>
<Link rel="stylesheet"
	href="${pageContext.request.contextPath}/assets/css/jblog.css">
<script
	src="${pageContext.request.contextPath}/assets/js/jquery/jquery-1.9.0.js"></script>
<script>
	$(function() {
		$("#btn-add")
				.click(
						function() {
							var name = $("#name").val();
							var description = $("#description").val();
							if (name == "" || description == "") {
								return;
							}

							$
									.ajax({
										url : "${pageContext.request.contextPath}/${authUser.id}/api/category/add",
										data : {
											name : name,
											description : description
										},
										type : "post",
										dataType : "json",
										success : function(response) {
											if (response.result != "success") {
												console.error(response.message);
												return;
											}

											if (!response.data.success) {
												alert("카테고리 추가 실패");
												$("#name").val("");
												$("#description").val("");

												$("#name").focus();

												return;
											}

											location.reload();
										},
										error : function(xhr, status, err) {
											console.error(err);
										}
									});
						});

		$(".btn-delete")
				.click(
						function() {
							if(${categoryList.size() == 1}) {
								alert("카테고리는 최소 1개 이상 있어야 합니다.");
								return;
							}

							var id = $(this).data("id");

							console.log("id: " + id);

							if (!confirm("정말 삭제하시겠습니까?")) {
								return;
							}

							$
									.ajax({
										url : "${pageContext.request.contextPath}/${authUser.id}/api/category/delete",
										data : {
											category_id : id
										},
										type : "post",
										dataType : "json",
										success : function(response) {
											if (response.result != "success") {
												alert(response.message);
												return;
											}

											if (!response.data.success) {
												alert("카테고리 삭제 실패");
												return;
											}

											location.reload();
										},
										error : function(xhr, status, err) {
											console.error(err);
										}
									});
						});
	});
</script>
</head>
<body>
	<div id="container">
		<c:import url="/WEB-INF/views/includes/blog-header.jsp" />
		<div id="wrapper">
			<div id="content" class="full-screen">
				<ul class="admin-menu">
					<li><a
						href="${pageContext.request.contextPath }/${authUser.id }/admin">기본설정</a></li>
					<li class="selected">카테고리</li>
					<li><a
						href="${pageContext.request.contextPath }/${authUser.id }/admin/write">글작성</a></li>
				</ul>
				<table class="admin-cat">
					<tr>
						<th>번호</th>
						<th>카테고리명</th>
						<th>포스트 수</th>
						<th>설명</th>
						<th>삭제</th>
					</tr>
					<c:forEach items="${categoryList}" var="vo">
						<tr>
							<td>${vo.idx }</td>
							<td>${vo.name }</td>
							<td>${vo.postCnt }</td>
							<td>${vo.description }</td>
							<td><c:if test="${vo.postCnt == 0 }">
									<img data-id="${vo.id}" class="btn-delete"
										style="cursor: pointer;"
										src="${pageContext.request.contextPath}/assets/images/delete.jpg">
								</c:if></td>
						</tr>
					</c:forEach>
				</table>

				<h4 class="n-c">새로운 카테고리 추가</h4>
				<table id="admin-cat-add">
					<tr>
						<td class="t">카테고리명</td>
						<td><input type="text" id="name" name="name"></td>
					</tr>
					<tr>
						<td class="t">설명</td>
						<td><input type="text" id="description" name="description"></td>
					</tr>
					<tr>
						<td class="s">&nbsp;</td>
						<td><input id="btn-add" type="button" value="카테고리 추가"></td>
					</tr>
				</table>
			</div>
		</div>
		<c:import url="/WEB-INF/views/includes/blog-footer.jsp" />
	</div>
</body>
</html>