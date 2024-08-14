<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%> <%@ taglib prefix="c"
uri="http://java.sun.com/jsp/jstl/core"%> <% request.setCharacterEncoding("UTF-8"); String cp =
request.getContextPath(); request.setAttribute("pageName", "대나무 숲"); %>
<!DOCTYPE html>
<html>
	<head>
		<meta charset="utf-8" />
		<title>익명 게시판입니다.</title>
		<link rel="stylesheet" href="https://fonts.googleapis.com/icon?family=Material+Icons" />
		<link rel="stylesheet" href="${pageContext.request.contextPath}/resource/css/material.min.css" />
		<script src="${pageContext.request.contextPath}/resource/js/material.min.js"></script>

		<style>
			#view-source {
				position: fixed;
				display: block;
				right: 0;
				bottom: 0;
				margin-right: 40px;
				margin-bottom: 40px;
				z-index: 900;
			}
			.bambu {
				width: 100%;
				height: 855px;
				filter: grayscale(50%) opacity(70%);
				z-index: -1;
			}
			.img-background {
			}

			.demo-card-wide.mdl-card {
				width: 600px;
			}
			.demo-card-wide > .mdl-card__title {
				color: #fff;
				height: 176px;
				background: url("${pageContext.request.contextPath}/resource/images/bamboo-1224035_1920.jpg") center /
					cover;
				filter: grayscale(50%) opacity(70%);
			}
			.demo-card-wide > .mdl-card__menu {
				color: #fff;
			}
		</style>
	</head>
	<body>
		<div class="demo-layout mdl-layout mdl-js-layout mdl-layout--fixed-drawer mdl-layout--fixed-header">
			<%@include file="/board/sidebar.jsp" %>
			<div class="mdl-layout__content" style="width: 1000px">
				<div class="demo-card-wide mdl-card mdl-shadow--2dp">
					<div class="mdl-card__title">
						<h2 class="mdl-card__title-text">Welcome</h2>
					</div>
					<div class="mdl-card__supporting-text">익명 대나무 숲입니다.</div>
					<div class="mdl-card__actions mdl-card--border">
						<a class="mdl-button mdl-button--colored mdl-js-button mdl-js-ripple-effect" href="${pageContext.request.contextPath}/board.do?page=1&title=">
							게시판 바로가기
						</a>
					</div>
				</div>
			</div>
		</div>
	</body>
</html>
