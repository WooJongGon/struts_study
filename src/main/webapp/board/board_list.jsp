<%@page import="org.apache.jasper.tagplugins.jstl.core.Param"%> 
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" session="true"%> 
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%> 
<%
	request.setCharacterEncoding("UTF-8"); 
	String cp = request.getContextPath(); 
	session.setAttribute("page", request.getParameter("page")); 
	session.setAttribute("title", request.getParameter("title")); 
%>
<!DOCTYPE html>
<html>
	<head>
		<meta charset="UTF-8" />
		<title>게시판</title>
		<link rel="stylesheet" href="https://fonts.googleapis.com/icon?family=Material+Icons" />
		<link rel="stylesheet" href="${pageContext.request.contextPath}/resource/css/material.min.css" />
		<script src="${pageContext.request.contextPath}/resource/js/material.min.js"></script>
		<script type="text/javascript">
			window.onload = function () {
				document.getElementById("search").addEventListener("keypress", searchPostEnter);
			};

			function searchPost() {
				let searchTitle = document.getElementById("search").value;
				location.href = "<%=cp%>/board.do?page=1&title=" + encodeURIComponent(searchTitle);
			}

			function searchPostEnter(e) {
				if (e.keyCode == 13) {
					let searchTitle = document.getElementById("search").value;
					location.href = "<%=cp%>/board.do?page=1&title=" + encodeURIComponent(searchTitle);
				}
			}
		</script>
	</head>
	<body>
		<div class="demo-layout mdl-layout mdl-js-layout mdl-layout--fixed-drawer mdl-layout--fixed-header">
			<%@ include file="./sidebar.jsp" %>
			<div class="mdl-layout__content" style="width: 800px">
				<div class="mdl-gird">
					<input
						class="mdl-cell mdl-cell--3-col mdl-cell--8-offset mdl-textfield__input"
						type="text"
						id="search"
						placeholder="제목을 입력해주세요"
						style="display: inline-flex"
						onkeypress="searchPostEnter(event)" />
					<label class="mdl-button mdl-js-button mdl-button--icon" for="search">
						<i class="material-icons" onclick="searchPost()">search</i>
					</label>
				</div>
				<div class="mdl-grid">
					<div class="mdl-cell mdl-cell--12-col">
						<table class="mdl-data-table mdl-js-data-table mdl-shadow--2dp" style="width: 100%">
							<tr class="">
								<th class="mdl-data-table__cell--non-numeric" style="width: 50px">번호</th>
								<th class="mdl-data-table__cell--non-numeric">제목</th>
								<th class="mdl-data-table__cell--non-numeric" style="width: 100px">글쓴이</th>
								<th class="mdl-data-table__cell--non-numeric" style="width: 100px">작성일</th>
								<th class="mdl-data-table__cell--non-numeric" style="width: 50px">조회수</th>
							</tr>
							<tbody class="">
								<c:forEach var="item" items="${lists}">
									<tr>
										<td class="mdl-data-table__cell--non-numeric">${item.boardNo}</td>
										<td class="mdl-data-table__cell--non-numeric">
											<a href="<%=cp%>/board/view.do?post=${item.boardNo}"> ${item.title} </a>
										</td>
										<td class="mdl-data-table__cell--non-numeric">${item.name}</td>
										<td class="mdl-data-table__cell--non-numeric">${item.created}</td>
										<td class="mdl-data-table__cell--non-numeric">${item.hitCount}</td>
									</tr>
								</c:forEach>
							</tbody>
						</table>
					</div>
				</div>
				<div class="mdl-cell mdl-cell--2-col mdl-cell--10-offset">
					<button
						class="mdl-button mdl-js-button mdl-button--raised mdl-button--colored mdl-js-ripple-effect"
						onclick="javascript:location.href='<%=cp%>/board/write.do';">
						글쓰기
					</button>
				</div>
				<div class="pagination">
					<ul class="">
						<c:if test="${currentPage > 1}">
							<li
								class="mdl-button mdl-js-button"
								onclick="javascript:location.href='<%=cp%>/board.do?page=${currentPage -1}&title=${title}';">
								<i class="material-icons">chevron_left</i>
							</li>
						</c:if>
						<c:forEach begin="1" end="${totalPages}" var="i">
							<c:if test="${i == currentPage}">
								<li
									class="mdl-button mdl-js-button mdl-button--primary"
									onclick="javascript:location.href='<%=cp%>/board.do?page=${i}&title=${title}';"
									style="font-weight: bold">
									${i}
								</li>
							</c:if>
							<c:if test="${i != currentPage}">
								<li
									class="mdl-button mdl-js-button"
									onclick="javascript:location.href='<%=cp%>/board.do?page=${i}&title=${title}';">
									${i}
								</li>
							</c:if>
						</c:forEach>
						<c:if test="${currentPage < totalPages}">
							<li
								class="mdl-button mdl-js-button"
								onclick="javascript:location.href='<%=cp%>/board.do?page=${currentPage + 1}&title=${title}';">
								<i class="material-icons">chevron_right</i>
							</li>
						</c:if>
					</ul>
				</div>
			</div>
		</div>
	</body>
	<style type="text/css">
		a {
			color: inherit;
			text-decoration: none;
		}
		.pagination {
			text-align: center;
		}
		.pagination li {
			min-width: 1px;
		}
	</style>
</html>
