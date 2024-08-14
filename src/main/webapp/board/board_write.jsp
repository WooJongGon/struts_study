<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%> <%@ taglib prefix="c"
uri="http://java.sun.com/jsp/jstl/core"%> <% request.setCharacterEncoding("UTF-8"); String cp =
request.getContextPath(); %>
<!DOCTYPE html>
<html>
	<head>
		<meta charset="UTF-8" />
		<title>게시판</title>
		<link rel="stylesheet" href="https://fonts.googleapis.com/icon?family=Material+Icons" />
		<link rel="stylesheet" href="${pageContext.request.contextPath}/resource/css/material.min.css" />
		<script src="${pageContext.request.contextPath}/resource/js/material.min.js"></script>
		<script type="text/javascript">
			function savePost() {
				event.preventDefault();

				let form = document.getElementById("postForm");
				form.method = "POST";
				form.action = "<%=cp%>/board/write.do";

				if (form.title.value.trim() === "") {
					alert("제목을 입력해주세요.");
					return;
				}
				if (form.name.value.trim() === "") {
					alert("이름을 입력해주세요.");
					return;
				}
				if (form.pwd.value.trim() === "") {
					alert("비밀번호를 입력해주세요.");
					return;
				}
				if (form.content.value.trim() === "") {
					alert("본문을 입력해주세요.");
					return;
				}

				let modeInput = document.createElement("input");
				modeInput.type = "hidden";
				modeInput.name = "mode";
				modeInput.value = "I";
				form.appendChild(modeInput);

				document.body.appendChild(form);
				form.submit();
			}
		</script>
	</head>
	<body>
		<div class="demo-layout mdl-layout mdl-js-layout mdl-layout--fixed-drawer mdl-layout--fixed-header">
			<%@ include file="./sidebar.jsp" %>
			<div class="mdl-layout__content" style="width: 1000px">
				<div class="wrapper">
					<form action="" method="post" name="myForm" id="postForm">
						<div class="mdl-grid">
							<label for="title" class="mdl-cell mdl-cell--1-col">제목</label>
							<input
								type="text"
								name="title"
								id="title"
								placeholder="제목을 입력해주세요."
								maxlength="100"
								class="mdl-cell mdl-cell--7-col mdl-textfield__input" />
						</div>
						<div class="mdl-grid">
							<label for="name" class="mdl-cell mdl-cell--1-col">작성자</label>
							<input
								type="text"
								name="name"
								id="name"
								placeholder="이름을 입력해주세요."
								maxlength="20"
								class="mdl-cell mdl-cell--4-col mdl-textfield__input" />
						</div>
						<div class="mdl-grid">
							<label for="pwd" class="mdl-cell mdl-cell--1-col">비밀번호</label>
							<input
								type="password"
								name="pwd"
								id="pwd"
								placeholder="비밀번호를 입력해주세요."
								maxlength="50"
								class="mdl-cell mdl-cell--4-col mdl-textfield__input" />
						</div>
						<div class="mdl-grid">
							<label for="content" class="mdl-cell mdl-cell--1-col">내용</label>
							<textarea
								rows="12"
								cols="63"
								name="content"
								class="mdl-cell mdl-cell--7-col mdl-textfield__input"
								id="content"></textarea>
						</div>
						<div class="mdl-grid">
							<div class="mdl-cell mdl-cell--3-col mdl-cell--6-offset">
								<button
									type="button"
									value="등록"
									class="mdl-button mdl-js-button mdl-button--raised mdl-button--colored mdl-js-ripple-effect"
									onclick="savePost()">
									등록
								</button>
								<button
									type="button"
									value="취소"
									class="mdl-button mdl-js-button mdl-button--raised mdl-button--colored mdl-js-ripple-effect"
									onclick="javascript:location.href='<%=cp%>/board.do';">
									취소
								</button>
							</div>
						</div>
					</form>
				</div>
			</div>
		</div>
	</body>
	<style type="text/css"></style>
</html>
