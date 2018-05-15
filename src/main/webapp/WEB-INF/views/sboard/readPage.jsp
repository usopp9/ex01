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
						<input type="hidden" name="searchType" value="${cri.searchType}">
						<input type="hidden" name="keyword" value="${cri.keyword}">
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
	<div class="row">
		<div class="col-md-12">
			<div class="box box-success">
				<div class="box-header">
					<h3 class="box-title">ADD NEW REPLY</h3>
				</div>
				<div class="box-body">
					<label>Writer</label>
					<input class="form-control" type="text" placeholder="User ID" id="newReplyWriter">
					
					<label>Reply Text</label>
					<input class="form-control" type="text" placeholder="Reply Text" id="newReplyText">
				</div>
				<div class="box-footer">
					<button class="btn btn-primary" id="replyAddBtn">Add Reply</button>
				</div>
			</div>
		</div>
	</div>
</section>
<script>
	$("#replyAddBtn").click(function(){
		var bnoVal = ${boardVO.bno };
		var replyerVal = $("#newReplyWriter").val();
		var replytextVal = $("#newReplyText").val();
		
		var sendData = {bno:bnoVal, replyer:replyerVal, replytext:replytextVal};
		//이형태는 ?bno=bnoVal&replyer=........
						
		//@requestBody, JSON.stringify, headers-Content-Type
		$.ajax({
			type:"post",
			/* url:"/ex02/replies" */
			url:"${pageContext.request.contextPath}/replies",
			data: JSON.stringify(sendData), //json 형태로 바꿔줌
			dataType:"text",//xml,text,json
			headers:{"Content-Type":"application/json"},
			success:function(result){
				console.log(result);
				if(result == "success"){             
					alert("등록되었습니다.");      
					$("#newReplyWriter").val("");        
					$("#newReplyText").val("");
				}
			}
		})
	})
</script>


<%@ include file="../include/footer.jsp"%>