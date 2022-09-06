<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>Spring detail 페이지</title>
<style>
     table * {margin:5px;}
     table {width:100%;}
</style>
</head>
<body>

    <jsp:include page="../common/header.jsp" />

    <div class="content">
        <br><br>
        <div class="innerOuter">
            <h2>게시글 상세보기</h2>
            <br>

            <a class="btn btn-secondary" style="float:right;" href="list.bo">목록으로</a>
            <br><br>

            <table id="contentArea" algin="center" class="table">
                <tr>
                    <th width="100">제목</th>
                    <td colspan="3">${ b.boardTitle }</td>
                </tr>
                <tr>
                    <th>작성자</th>
                    <td>${ b.boardWriter }</td>
                    <th>작성일</th>
                    <td>${ b.createDate }</td>
                </tr>
                <tr>
                    <th>첨부파일</th>
                    <td colspan="3">
						<c:choose>
						
							<c:when test="${ empty b.originName }">
								<!-- 첨부파일 없을 경우 -->
								첨부파일이 없습니다.
							</c:when>
							
							<c:otherwise>
								<!-- 첨부파일 있을 경우 -->
		                        <a href="${ b.changeName }" download="${ b.originName }">${ b.originName }</a>
							</c:otherwise>
							
						</c:choose>
                    </td>
                </tr>
                <tr>
                    <th>내용</th>
                    <td colspan="3"></td>
                </tr>
                <tr>
                    <td colspan="4"><p style="height:150px;">${ b.boardContent }</p></td>
                </tr>
            </table>
            <br>
            
			<c:if test="${ loginUser.userId eq b.boardWriter }">
	            <div align="center">
	                <!-- 수정하기, 삭제하기 버튼은 이 글이 본인이 작성한 글일 경우에만 보여져야 함 -->
	                <a class="btn btn-primary" onclick="postFormSubmit(1)">수정하기</a>
	                <a class="btn btn-danger" onclick="postFormSubmit(2)">삭제하기</a>
	            </div>
	            <br><br>
			</c:if>

			<form action="" method="post" id="postForm">
				<input type="hidden" value="${ b.boardNo }" name="bno">
				<input type="hidden" value="${ b.changeName }" name="filePath">
			</form>
			
			<script>
				function postFormSubmit(num){
					if(num == 1){
						// 수정하기 클릭시
						$('#postForm').attr('action', 'updateForm.bo').submit();
						
					}
					else{
						// 삭제하기 클릭시
						$('#postForm').attr('action', 'delete.bo').submit();
						
					}
				}
			</script>

            <table id="replyArea" class="table" align="center">
                <thead>
                <c:choose>
	                <c:when test="${ empty loginUser }">
	                    <tr>
	                        <th colspan="2">
	                            <textarea class="form-control" readonly cols="55" rows="2" style="resize:none; width:100%;">로그인 후 이용가능합니다.</textarea>
	                        </th>
	                        <th style="vertical-align:middle"><button class="btn btn-secondary disabled">등록하기</button></th>
	                    </tr>
	                    </c:when>
	                 <c:otherwise>
	         	        <tr>
	                        <th colspan="2">
	                            <textarea class="form-control" id="content" cols="55" rows="2" style="resize:none; width:100%;"></textarea>
	                        </th>
	                        <th style="vertical-align:middle"><button class="btn btn-secondary" onclick="addReply();">등록하기</button></th>
	                    </tr>
	                 </c:otherwise>
	              </c:choose>
                    <tr>
                        <td colspan="3">댓글(<span id="rcount">0</span>)</td>
                    </tr>
                </thead>
                <tbody>
                	
                </tbody>
            </table>
        </div>
        <br><br>

    </div>
    
    <script>
    	$(function(){
    		// 댓글조회하는 기능을 호출
    		selectReplyList();
    	})
    	
    	
    	function addReply(){
    		// 댓글작성용 ajax
    		
    		if($("#content").val().trim() != ""){
    			//console.log("키키")
    			
    			$.ajax({
    				
    				url : "rinsert.bo",
    				data : {
    					refBno : ${ b.boardNo },
    					replyContent : $("#content").val(),
    					replyWriter : "${ loginUser.userId }",
    				},
 					success : function(status){
 						if(status == "success"){
 							selectReplyList();
 							$("#content").val('');
 						}
 					},
 					error : function(){
 						console.log("댓글을 다시 입력해주세요.")
 					}
    			})
    		}
    		else{
    			alertify.alert("다시 써주세요!");
    		}
    	}
    	
    	function selectReplyList(){
    		
    		$.ajax({

    			url : 'rlist.bo', // 전체조회가 아님! 게시글에 딸린 댓글만 조회해야한다(현재 게시글 번호만 넘겨야한다.)
    			data : {bno : ${ b.boardNo }},
    			success : function(list){
    				
    				let value = '';
    				for(let i in list){
    					value += '<tr>'
							   + '<td>' + list[i].replyWriter + '</td>'
							   + '<td>' + list[i].replyContent + '</td>'
							   + '<td>' + list[i].createDate + '</td>'
							   + '</tr>'
    				}
    				$('#replyArea tbody').html(value);
    				$('#rcount').text(list.length);
    			
    			},
    			error : function(){
    				console.log('실패');
    			}
    			
    			
    			
    			
    		})
    	}
    </script>
    
    <jsp:include page="../common/footer.jsp" />


</body>
</html>