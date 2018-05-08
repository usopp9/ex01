<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ include file="../include/header.jsp"%>

<section class="content">
	<div class="row">
		<div class="col-md-12">
			<div class="box box-primary">
				<div class="box-header">
					<h3 class=box-title>Read Board</h3>
				</div>
				<div class="box-body">
					<form method="get" id="f1">
						<input type="hidden" name="bno" value="${boardVO.bno }">
						<input type="hidden" name="page" value="${cri.page}">
					</form>
					<div class="form-group">
						<label>TITLE</label>
						<input type="text" name="title" class="form-control" value="${boardVO.title }" readonly="readonly">
					</div>
					<div class="form-group">
						<label>Content</label>
						<textarea rows="5" cols="30" class="form-control" readonly="readonly">${boardVO.content }</textarea>
					</div>
					<div class="form-group">
						<label>WRITER</label>
						<input type="text" name="writer" class="form-control" value="${boardVO.writer }" readonly="readonly">
					</div>
				</div>
				<div class="box-footer">
					<button type="submit" class="btn btn-warning" id="modifyBtn">Modify</button>
					<button type="submit" class="btn btn-danger" id="deleteBtn">Delete</button>
					<button type="submit" class="btn btn-primary" id="goListBtn">GO LIST</button>
				</div>
				<script type="text/javascript">
					$("#goListBtn").click(function(){
						/* location.href="${pageContext.request.contextPath}/board/listPage?page=${cri.page}";   */
						$("#f1").attr("action","listPage");
						$("#f1").submit();    
					})
					$("#modifyBtn").click(function(){
						$("#f1").attr("action","modifyPage");
						$("#f1").submit();
					})
					
					$("#deleteBtn").click(function(){
						var con = confirm("정말 삭제하시겠습니까?");
						if(con==false){ 
							return false;
						}
						$("#f1").attr("action","removePage");
						$("#f1").submit();
					})
				</script>
			</div>
		</div>
	</div>
</section>

<%@ include file="../include/footer.jsp"%>