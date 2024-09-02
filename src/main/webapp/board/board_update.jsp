<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%> 
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%> 
<% 
	request.setCharacterEncoding("UTF-8"); 
	String cp = request.getContextPath(); 
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
			document.addEventListener("DOMContentLoaded", (event) => {
				var isSubmit = false;
				
				function updatePost() {
					event.preventDefault();
					
					if (isSubmit) {
						return false;
					} else {
						isSubmit = true;
						
						let form = document.getElementById("postForm");
						form.method = "POST";
						form.action = "<%=cp%>/board/update.do";
	
						if (form.title.value.trim() === "") {
							alert("제목을 입력해주세요.");
							return;
						}
						if (form.content.value.trim() === "") {
							alert("본문을 입력해주세요.");
							return;
						}
						
						let boardNoInput = document.createElement("input");
						boardNoInput.type = "hidden";
						boardNoInput.name = "boardNo";
						boardNoInput.value = "${item.boardNo}";
						form.appendChild(boardNoInput);
	
						let modeInput = document.createElement("input");
						modeInput.type = "hidden";
						modeInput.name = "mode";
						modeInput.value = "U";
						form.appendChild(modeInput);
	
						document.body.appendChild(form);
						form.submit();
					}
				}
				
				function deleteImage() {
					event.preventDefault();
					let postImage = document.getElementById("postImage"); 
					postImage.remove();
					
					let wrap = document.getElementById("imageWrap");
					console.log(wrap);
					let imageInput = document.createElement("input");
					imageInput.type = "file";
					imageInput.name = "image";
					imageInput.id = "image";
					imageInput.maxlength="50";
					imageInput.class = "mdl-cell mdl-cell--4-col";
					wrap.appendChild(imageInput);					
				}

				document.querySelector('button[value="저장"]').addEventListener("click", updatePost);
				document.querySelector('button[id="deleteImage"]').addEventListener("click", deleteImage);
			});
		</script>
	</head>
	<body>
		<div class="demo-layout mdl-layout mdl-js-layout mdl-layout--fixed-drawer mdl-layout--fixed-header">
			<%@ include file="./sidebar.jsp" %>
			<div class="mdl-layout__content">
				<div class="wrapper">
					<form action="" method="post" name="myForm" id="postForm" enctype="multipart/form-data">
						<div class="mdl-grid">
							<label for="title" class="mdl-cell mdl-cell--1-col">제목</label>
							<input
								type="text"
								name="title"
								id="title"
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
								maxlength="20"
								class="mdl-cell mdl-cell--2-col mdl-textfield__input"
								value="${item.name}"
								disabled="disabled" />
						</div>
						<div class="mdl-grid">
							<label for="content" class="mdl-cell mdl-cell--1-col">내용</label>
							<textarea
								rows="12"
								cols="63"
								name="content"
								class="mdl-cell mdl-cell--5-col mdl-textfield__input"
								id="content">
${item.content}</textarea
							>
						</div>
						<div id="imageWrap">
							<c:choose>
								<c:when test="${file.extension eq 'jpg'
												|| file.extension eq 'png'
												|| file.extension eq 'gif'
												|| file.extension eq 'jpeg'
												|| file.extension eq 'bmp'}">
									<div class="mdl-grid" id="postImage">
										<label for="content" class="mdl-cell mdl-cell--1-col">첨부파일</label>
		       							<img 
		       							src="${pageContext.request.contextPath}/${file.localPath}" 
		       							alt="첨부 이미지" 
		       							class="mdl-cell mdl-cell--4-col"/>
		       							<button type="button" 
		       									class="mdl-button mdl-js-button"
		       									id="deleteImage"
		       									onclick="deleteImage">
		       								<i class="material-icons">delete</i>
		       							</button>
									<input
										type="hidden"
										name="fileNo"
										id="fileNo"
										maxlength="20"
										value="${item.fileNo}"/>
									</div>
								</c:when>
								<c:otherwise>
									<div class="mdl-grid">
										<input
											type="file"
											name="image"
											id="image"
											maxlength="50"
											class="mdl-cell mdl-cell--4-col" />
									</div>
								</c:otherwise>
							</c:choose>
						</div>
						<div class="mdl-grid">
							<div class="mdl-cell mdl-cell--3-col mdl-cell--4-offset">
								<button
									value="저장"
									class="mdl-button mdl-js-button mdl-button--raised mdl-button--colored mdl-js-ripple-effect"
									onclick="updatePost()">
									저장
								</button>
								<button
									type="button"
									value="취소"
									class="mdl-button mdl-js-button mdl-button--raised mdl-button--colored mdl-js-ripple-effect"
									onclick="javascript:history.go(-1);">
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
