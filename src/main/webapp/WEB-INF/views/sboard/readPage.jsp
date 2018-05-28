<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ include file="../include/header.jsp"%>

<script src="${pageContext.request.contextPath}/resources/handlebars-v4.0.10.js"></script>

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
						<c:forEach var="file" items="${boardVO.files }">
							<input type="hidden" name="files" value="${file}">
						</c:forEach>
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
					<div class="form-group">
						<c:forEach var="file" items="${boardVO.files }">
							<input type="hidden" name="files" value="${file}">
							<img src="displayFile?filename=${file}">    
						</c:forEach>
					</div>
				</div>
				<div class="box-footer">
					<c:if test="${boardVO.writer == login.uid }">
					<button type="submit" class="btn btn-warning" id="modifyBtn">Modify</button>
					<button type="submit" class="btn btn-danger" id="deleteBtn">Delete</button>
					</c:if>
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
					<input class="form-control" type="text" readonly="readonly" value="${login.uid }" id="newReplyWriter">
					
					<label>Reply Text</label>
					<input class="form-control" type="text" placeholder="Reply Text" id="newReplyText">
				</div>
				<div class="box-footer">
					<button class="btn btn-primary" id="replyAddBtn">Add Reply</button>
				</div>
			</div>
			<ul class="timeline">
				<li class="time-label" id="repliesDiv">
					<span class="bg-green">Replies List [<span id="replyCnt">${boardVO.replycnt }</span>]</span>
				</li>
			</ul>
			<div class="text-center">
				<ul id="pagination" class="pagination pagination-sm no-margin">
				
				</ul>
			</div>
		</div>
	</div>
</section>


<div id="modifyModal" class="modal fade" role="dialog">
  <div class="modal-dialog">

    <!-- Modal content-->
    <div class="modal-content">
      <div class="modal-header">
        <button type="button" class="close" data-dismiss="modal">&times;</button>
        <h4 class="modal-title">댓글 수정</h4>
      </div>
      <div class="modal-body">
        <div class="form-group">
			  <label for="replyText">수정내용:</label>
			  <input type="text" class="form-control" id="replyText">
			</div>
		
		<div class="form-group">
		<button type="button" class="btn btn-success" id="modifyBtn" data-dismiss="modal">수정</button>	  
		</div>  
      </div>
      <div class="modal-footer">
        <button type="button" class="btn btn-default" data-dismiss="modal">Close</button>
      </div>
    </div>

  </div>
</div>  



<script id="template" type="text/x-handlerbars-template">
{{#each.}}
<li class="replyli" data-rno={{rno}}>
	<i class="fa fa-comments bg-blue"></i>
	<div class="timeline-item">
		<span class="time">
			<i class="fa fa-clock-o"></i>{{prettifyDate regdate}}
		</span>
		<h3 class="timeline-header"><strong>{{rno}}</strong>-{{replyer}}</h3>
		<div class="timeline-body">{{replytext}}</div>

		{{#if replyer}}
		<div class="timeline-footer">
			<a class="btn btn-primary btn-xs" data-toggle="modal" data-target="#modifyModal" id="replyModifyBtn">Modify</a>
			<a class="btn btn-danger btn-xs" id="replyBtn">Delete</a>
		</div>
		{{/if}}
	</div>  
</li>
{{/each}}    
</script>
<script>
	Handlebars.registerHelper("if",function(replyer, options){
		if(replyer == "${login.uid}"){
			return options.fn(this);
		}else{
			return '';
		}
	})

	Handlebars.registerHelper("prettifyDate",function(value){   
		var dataObj = new Date(value);
		var year = dataObj.getFullYear();
		var month = dataObj.getMonth()+1;
		var date = dataObj.getDate();
		
		return year +"/" + month + "/" + date;
	})

	var source = $("#template").html();
	var tFunc = Handlebars.compile(source);  
	      
	
	var bnoVal = ${boardVO.bno };
	var pageNumber = 1;
	
	
	$("#replyAddBtn").click(function(){
		
		var replyerVal = $("#newReplyWriter").val();
		var replytextVal = $("#newReplyText").val();
		
		var replyCnt = $("#replyCnt").html(); 
		
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
				if(result == "succes"){               
					alert("등록되었습니다.");              
					$("#newReplyText").val("");
					$("#repliesDiv").trigger("click");  
				    
					$("#replyCnt").html(Number(replyCnt)+1);      
				}
			}
		})
	})
	
	$("#repliesDiv").click(function(){
		
		// ex01/replies/5/1
		$.ajax({    
			url:"${pageContext.request.contextPath}/replies/"+bnoVal+"/"+pageNumber,    
			type:"get",
			dataType:"json",
			success:function(result){
				console.log(result);
				 $("#replyli").empty();
				//list     
				 
				displayList(result.list);
				
				//pagination
				displayPaging(result);
			}
		})
	})
	
	var rno;
	$(document).on("click","#replyBtn",function(){
		var replyCnt = $("#replyCnt").html(); 
		rno = $(this).parent().parent().find("strong").html();
		// var removeno = $(this).parent().parent().parent();        
		var con = confirm("삭제하시겠습니까??");
		if(con){
			
			$.ajax({
				type:"DELETE",
				url:"${pageContext.request.contextPath}/replies/"+rno,   
				dataType:"text",
				success:function(result){
					console.log(result);
					if(result=="success"){
						alert("삭제되었습니다.");   
					//	removeno.remove();         
						$("#repliesDiv").trigger("click");
						$("#replyCnt").html(Number(replyCnt)-1);       
					}
				}	
			})
		}
	})
	    
	$(document).on("click","#replyModifyBtn",function(){
		rno = $(this).parent().parent().find("strong").html();  
	})  
	
	$(document).on("click","#modifyBtn",function(){
		//var rno = $("#rno").val();    
    	var rcontext = $("#replyText").val();      
    	var sendData = {replytext:rcontext};   
    	
    	$.ajax({
			type:"PUT",     
			url:"${pageContext.request.contextPath}/replies/"+rno,   
			data: JSON.stringify(sendData),  
			dataType:"text",  
			headers:{"Content-Type":"application/json"},     
			success:function(result){
				console.log(result);
				$("#repliesDiv").trigger("click");               
				 $("#replyText").val("");       
   
			}	
		})
	}) 
	
	function displayList(data){
		var str = tFunc(data);
		$(".replyli").remove();
		$(".timeline").append(str);
	}
	
	function displayPaging(result){
		
		var str="";
		if(result.pageMaker.prev){
			str+="<li><a href='#'> << </a></li>";
		}
		
		for(var i = result.pageMaker.startPage; i<=result.pageMaker.endPage;i++){
			str+="<li><a href='#'>"+i+"</a></li>";
		}
		
		if(result.pageMaker.next){
			str+="<li><a href='#'> >> </a></li>";
		}
		       
		$(".pagination").html(str);    
	}
	$(document).on("click",".pagination a",function(e){
		e.preventDefault(); //link막기
		
		
		// 해당 a태그 값이 들어가면 됨
		pageNumber = $(this).text();
		
		$("#repliesDiv").click();    
		/* $("#repliesDiv").trigger("click"); */
	})
</script>


<%@ include file="../include/footer.jsp"%>