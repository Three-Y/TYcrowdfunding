<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE html>
<html lang="zh-CN">
  <head>
    <meta charset="UTF-8">
    <meta http-equiv="X-UA-Compatible" content="IE=edge">
    <meta name="viewport" content="width=device-width, initial-scale=1">
    <meta name="description" content="">
    <meta name="author" content="">

	<link rel="stylesheet" href="${APP_PATH }/bootstrap/css/bootstrap.min.css">
	<link rel="stylesheet" href="${APP_PATH }/css/font-awesome.min.css">
	<link rel="stylesheet" href="${APP_PATH }/css/main.css">
	<link rel="stylesheet" href="${APP_PATH }/css/doc.min.css">
	<style>
	.tree li {
        list-style-type: none;
		cursor:pointer;
	}
	</style>
  </head>

  <body>

    <nav class="navbar navbar-inverse navbar-fixed-top" role="navigation">
      <div class="container-fluid">
        <div class="navbar-header">
            <div><a class="navbar-brand" style="font-size:32px;" href="#">众筹平台 - 实名认证审核</a></div>
        </div>
        <div id="navbar" class="navbar-collapse collapse">
          <ul class="nav navbar-nav navbar-right">
            <li style="padding-top:8px;">
				<jsp:include page="/WEB-INF/jsp/common/userInfo.jsp"></jsp:include>
			</li>
            <li style="margin-left:10px;padding-top:8px;">
				<button type="button" class="btn btn-default btn-danger">
				  <span class="glyphicon glyphicon-question-sign"></span> 帮助
				</button>
			</li>
          </ul>
          <form class="navbar-form navbar-right">
            <input type="text" class="form-control" placeholder="Search...">
          </form>
        </div>
      </div>
    </nav>

    <div class="container-fluid">
      <div class="row">
        <div class="col-sm-3 col-md-2 sidebar">
			<div class="tree">
				<jsp:include page="/WEB-INF/jsp/common/menu.jsp"></jsp:include>
			</div>
        </div>
        <div class="col-sm-9 col-sm-offset-3 col-md-10 col-md-offset-2 main">
				<ol class="breadcrumb">
				  <li><a href="#">首页</a></li>
				  <li><a href="#">数据列表</a></li>
				  <li class="active">用户申请信息显示</li>
				</ol>
			<div class="panel panel-default">
              <div class="panel-heading">实名认证审核资料<div style="float:right;cursor:pointer;" data-toggle="modal" data-target="#myModal"><i class="glyphicon glyphicon-question-sign"></i></div></div>
			  <div class="panel-body">
				<form id="addForm">
				  <div class="form-group">
					<label for="frealname">真实姓名：</label>
					${member.realname }
				  </div>
				  <div class="form-group">
					<label for="fcardnum">身份证号：</label>
					${member.cardnum }
				  </div>
				  <div class="form-group">
					<label for="ftel">联系号码：</label>
					${member.tel }
				  </div><hr>
				  <c:forEach items="${certInfo }" var="map" >
					  <div class="form-group">
						<label for="femail">${map.certname }</label><br>
						<img src="${APP_PATH }/pics/cert/${map.iconpath }"> 
					  </div>
				  </c:forEach>
				  <button id="passBtn" type="button" class="btn btn-success"><i class="glyphicon glyphicon-plus"></i> 通过</button>
				  <button id="refuseBtn" type="button" class="btn btn-danger"><i class="glyphicon glyphicon-refresh"></i> 拒绝</button>
				</form>
			  </div>
			</div>
        </div>
      </div>
    </div>
	<div class="modal fade" id="myModal" tabindex="-1" role="dialog" aria-labelledby="myModalLabel" aria-hidden="true">
	  <div class="modal-dialog">
		<div class="modal-content">
		  <div class="modal-header">
			<button type="button" class="close" data-dismiss="modal"><span aria-hidden="true">&times;</span><span class="sr-only">Close</span></button>
			<h4 class="modal-title" id="myModalLabel">帮助</h4>
		  </div>
		  <div class="modal-body">
			<div class="bs-callout bs-callout-info">
				<h4>测试标题1</h4>
				<p>测试内容1，测试内容1，测试内容1，测试内容1，测试内容1，测试内容1</p>
			  </div>
			<div class="bs-callout bs-callout-info">
				<h4>测试标题2</h4>
				<p>测试内容2，测试内容2，测试内容2，测试内容2，测试内容2，测试内容2</p>
			  </div>
		  </div>
		  <!--
		  <div class="modal-footer">
			<button type="button" class="btn btn-default" data-dismiss="modal">Close</button>
			<button type="button" class="btn btn-primary">Save changes</button>
		  </div>
		  -->
		</div>
	  </div>
	</div>
    <script src="${APP_PATH }/jquery/jquery-2.1.1.min.js"></script>
    <script src="${APP_PATH }/bootstrap/js/bootstrap.min.js"></script>
	<script src="${APP_PATH }/script/docs.min.js"></script>
	<script type="text/javascript" src="${APP_PATH }/jquery/layer/layer.js"></script>
        <script type="text/javascript">
            $(function () {
			    $(".list-group-item").click(function(){
				    if ( $(this).find("ul") ) {
						$(this).toggleClass("tree-closed");
						if ( $(this).hasClass("tree-closed") ) {
							$("ul", this).hide("fast");
						} else {
							$("ul", this).show("fast");
						}
					}
				});
            });
            
            $("#passBtn").click(function(){
            	$.ajax({
            		type : "POST",
            		url : "${APP_PATH}/auth_cert/applyPass.do",
            		data : {
            			memberId : "${param.memberId}",
            			taskId : "${param.taskId}"
            		},
            		beforeSend : function(){ },
            		success : function(result){
            			if(result.success){
            				layer.msg(result.message,{time:1000,icon:6,shift:6})
            				window.location.href="${APP_PATH}/auth_cert/index.htm"
            			}else{
            				layer.msg(result.message,{time:1000,icon:5,shift:6})
            			}
            			
            		},
            		error : function(){
            			layer.msg("操作失败！",{time:1000,icon:5,shift:6})
            		}
            	});
            });
            
            $("#refuseBtn").click(function(){
            	$.ajax({
            		type : "POST",
            		url : "${APP_PATH}/auth_cert/applyRefuse.do",
            		data : {
            			memberId : "${param.memberId}",
            			taskId : "${param.taskId}"
            		},
            		beforeSend : function(){ },
            		success : function(result){
            			if(result.success){
            				layer.msg(result.message,{time:1000,icon:6,shift:6})
            				window.location.href="${APP_PATH}/auth_cert/index.htm"
            			}else{
            				layer.msg(result.message,{time:1000,icon:5,shift:6})
            			}
            			
            		},
            		error : function(){
            			layer.msg("操作失败！",{time:1000,icon:5,shift:6})
            		}
            	});
            });
            
        </script>
  </body>
</html>
