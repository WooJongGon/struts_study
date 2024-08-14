<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<header class="demo-header mdl-layout__header mdl-color--grey-100 mdl-color-text--grey-600">
	<div class="mdl-layout__header-row">
		<span class="mdl-layout-title"><%= (String)request.getAttribute("pageName") %></span>
		<div class="mdl-layout-spacer"></div>
	</div>
</header>
<div class="demo-drawer mdl-layout__drawer mdl-color--blue-grey-900 mdl-color-text--blue-grey-50">
	<header class="demo-drawer-header">
		<span>&nbsp; &nbsp;</span>
		<img
			src="${pageContext.request.contextPath}/resource/images/bambu_board.png"
			class="demo-avatar"
			style="width: 50px; height: 64px; background-color: transparent" />
		<span style="font-size: 18px; font-weight: bolad">&nbsp; &nbsp; 익명 게시판</span>
	</header>
	<nav class="demo-navigation mdl-navigation mdl-color--blue-grey-800">
		<a class="mdl-navigation__link" href="${pageContext.request.contextPath}">
			<i class="mdl-color-text--blue-grey-400 material-icons" role="presentation">home</i>Home</a
		>
		<a class="mdl-navigation__link" href="${pageContext.request.contextPath}/board.do?page=1&title="
			><i class="mdl-color-text--blue-grey-400 material-icons" role="presentation">inbox</i>Board</a
		>
	</nav>
</div>
