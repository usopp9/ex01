<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ include file="../include/header.jsp"%>
<style>
	.imgDiv{
		float:left;
		position: relative;
		width:150px;
		height: 100px;
		margin:10px; 
		border: 1px solid gray;
	}
	.imgDiv img{
		width: 150px;
		height: 100px;
	}
	.deleteimg{
		position: absolute;
		top: 0px;
		right: 0px; 
		font-weight: bold;
		color:red;   
	}
	.fileimg{
		clear: both;
	}
</style>
<section class="content">
	<div class="row">
		<div class="col-md-12">
			<div class="box box-primary">
				<div class="box-header">
					<h3 class=box-title>Modify Board</h3>
				</div>
				<div class="box-body">
					<form method="post" id="f1" enctype="multipart/form-data">
						<input type="hidden" name="bno" value="${boardVO.bno }">
						<input type="hidden" name="page" value="${cri.page}">
						<input type="hidden" name="searchType" value="${cri.searchType}">
						<input type="hidden" name="keyword" value="${cri.keyword}">
					<div class="form-group">
						<label>TITLE</label>
						<input type="text" name="title" class="form-control" value="${boardVO.title }">
					</div>
					<div class="form-group">
						<label>Content</label>
						<textarea rows="5" cols="30" class="form-control" name="content">${boardVO.content }</textarea>
					</div>
					<div class="form-group">
						<label>WRITER</label>
						<input type="text" name="writer" class="form-control" value="${boardVO.writer }" readonly="readonly">
					</div>
					<div class="form-group">
						<c:forEach var="file" items="${boardVO.files }">
							<!-- <div class="imgDiv"> -->
							<%-- <input type="text" name="files" value="${file}">  --%>
							<div class="imgDiv" >
							<img src="displayFile?filename=${file}">  
							<span class="deleteimg">X</span>
							</div>
							<!-- </div>   -->
						</c:forEach>
					</div>
						<div id="inputhidden"></div>
					<div class="form-group fileimg">
						<label>Files</label>
							<input type="file" name="imageFiles" multiple="multiple" class="form-control">
					</div>
					</form>
				</div>
				<div class="box-footer">
					<button type="submit" class="btn btn-warning" id="modifyBtn">Modify</button>
					<button type="submit" class="btn btn-primary" id="goListBtn">Cancel</button>
				</div>
				<script type="text/javascript">
					$("#goListBtn").click(function(){
						location.href="${pageContext.request.contextPath}/sboard/readPage?bno=${boardVO.bno}&page=${cri.page}&searchType=${cri.searchType}&keyword=${cri.keyword}";
					})
					
					$(".deleteimg").click(function(){   
						var imgpath = $(this).parent().find("img").attr("src");  
						var img = imgpath.substring(imgpath.lastIndexOf("=") + 1);
						var taginput ="<input type='text' name='oldfiles' value='"+img+"'>";    
						$(this).parent().remove();    
						$("#inputhidden").append(taginput);
						
					})
					$("#modifyBtn").click(function(){ 
						$("#f1").attr("action","modifyPage");  
						$("#f1").submit();  
					})
				</script>
			</div>
		</div>
	</div>
</section>

<%@ include file="../include/footer.jsp"%>