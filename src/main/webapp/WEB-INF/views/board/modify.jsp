<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ include file="../include/header.jsp"%>

<section class="content">
	<div class="row">
		<div class="col-md-12">
			<div class="box box-primary">
				<div class="box-header">
					<h3 class=box-title>Modify Board</h3>
				</div>
				<div class="box-body">
					<form method="post" id="f1">
						<input type="hidden" name="bno" value="${boardVO.bno }">
					
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
					</form>
				</div>
				<div class="box-footer">
					<button type="submit" class="btn btn-warning" id="modifyBtn">Modify</button>
					<button type="submit" class="btn btn-primary" id="goListBtn">Cancel</button>
				</div>
				<script type="text/javascript">
					$("#goListBtn").click(function(){
						location.href="${pageContext.request.contextPath}/board/read?bno=${boardVO.bno}";
					})
					$("#modifyBtn").click(function(){
						$("#f1").attr("action","modify");
						$("#f1").submit();
					})
				</script>
			</div>
		</div>
	</div>
</section>

<%@ include file="../include/footer.jsp"%>