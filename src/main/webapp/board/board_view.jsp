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
			document.addEventListener("DOMContentLoaded", (event) => {
				var isSubmit = false;
				
				function updatePost() {
					if (isSubmit) {
						return;
					} else {
						let form = document.createElement("form");
						form.method = "POST";
						form.action = "<%=cp%>/board/update.do";

						let pwdInput = document.createElement("input");
						pwdInput.type = "hidden";
						pwdInput.name = "pwd";
						pwdInput.value = prompt("비밀번호를 입력해주세요", "");
						form.appendChild(pwdInput);

						let boardNoInput = document.createElement("input");
						boardNoInput.type = "hidden";
						boardNoInput.name = "boardNo";
						boardNoInput.value = "${item.boardNo}";
						form.appendChild(boardNoInput);

						let modeInput = document.createElement("input");
						modeInput.type = "hidden";
						modeInput.name = "mode";
						modeInput.value = "R";
						form.appendChild(modeInput);

						document.body.appendChild(form);
						form.submit();
					}
					
				}

				function deletePost() {
					if (isSubmit) {
						return;
					} else {
						let form = document.createElement("form");
						form.method = "POST";
						form.action = "<%=cp%>/board/delete.do";
	
						let pwdInput = document.createElement("input");
						pwdInput.type = "hidden";
						pwdInput.name = "pwd";
						pwdInput.value = prompt("비밀번호를 입력해주세요", "");
						form.appendChild(pwdInput);
	
						let boardNoInput = document.createElement("input");
						boardNoInput.type = "hidden";
						boardNoInput.name = "boardNo";
						boardNoInput.value = "${item.boardNo}";
						form.appendChild(boardNoInput);
						
						let fileNoInput = document.createElement("input");
						fileNoInput.type = "hidden";
						fileNoInput.name = "fileNo";
						fileNoInput.value = "${file.fileNo}";
						form.appendChild(fileNoInput);
	
						document.body.appendChild(form);
						form.submit();
					}
				}

				document.querySelector('button[value="수정"]').addEventListener("click", updatePost);
				document.querySelector('button[value="삭제"]').addEventListener("click", deletePost);
			});
		</script>
	</head>
	<body>
		<div class="demo-layout mdl-layout mdl-js-layout mdl-layout--fixed-drawer mdl-layout--fixed-header">
			<%@ include file="./sidebar.jsp" %>
			<div class="mdl-layout__content">
				<div class="wrapper">
					<form action="" method="post" name="myForm" id="postForm">
						<div class="mdl-grid">
							<label for="title" class="mdl-cell mdl-cell--1-col">제목</label>
							<input
								type="text"
								name="title"
								id="title"
								readonly="readonly"
								maxlength="100"
								class="mdl-cell mdl-cell--4-col mdl-textfield__input"
								value="${item.title}" />
						</div>
						<div class="mdl-grid">
							<label for="name" class="mdl-cell mdl-cell--1-col">작성자</label>
							<input
								type="text"
								name="name"
								id="name"
								readonly="readonly"
								maxlength="20"
								class="mdl-cell mdl-cell--2-col mdl-textfield__input"
								value="${item.name}" />
						</div>
						<div class="mdl-grid">
							<label for="content" class="mdl-cell mdl-cell--1-col">내용</label>
							<textarea
								rows="12"
								cols="63"
								name="content"
								class="mdl-cell mdl-cell--5-col mdl-textfield__input"
								id="content"
								readonly="readonly">
${item.content}</textarea
							>
						</div>
						<c:if test="${file.extension eq 'jpg'
										|| file.extension eq 'png'
										|| file.extension eq 'gif'
										|| file.extension eq 'jpeg'
										|| file.extension eq 'bmp'}">
							<div class="mdl-grid">
								<label for="content" class="mdl-cell mdl-cell--1-col">첨부파일</label>
       							<img 
       							src="${pageContext.request.contextPath}/${file.localPath}" 
       							alt="첨부 이미지" 
       							class="mdl-cell mdl-cell--4-col"/>
							</div>
						</c:if>
						<div class="mdl-grid">
							<div class="mdl-cell mdl-cell--3-col mdl-cell--4-offset">
								<button
									type="button"
									value="수정"
									class="mdl-button mdl-js-button mdl-button--raised mdl-button--colored mdl-js-ripple-effect">
									수정
								</button>
								<button
									type="button"
									value="목록으로"
									class="mdl-button mdl-js-button mdl-button--raised mdl-button--colored mdl-js-ripple-effect"
									onclick="javascript:location.href='<%=cp%>/board.do'">
									목록으로
								</button>
								<button
									type="button"
									id="delete"
									value="삭제"
									class="mdl-button mdl-js-button mdl-button--raised mdl-button--colored mdl-js-ripple-effect">
									삭제
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
