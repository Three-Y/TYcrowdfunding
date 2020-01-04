<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
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
            <div><a class="navbar-brand" style="font-size:32px;" href="user.html">众筹平台 - 用户维护</a></div>
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
				  <li class="active">新增</li>
				</ol>
			<div class="panel panel-default">
              <div class="panel-heading">表单数据<div style="float:right;cursor:pointer;" data-toggle="modal" data-target="#myModal"><i class="glyphicon glyphicon-question-sign"></i></div></div>
			  <div class="panel-body">
				<form id="addForm">
				  <div class="form-group">
				  	<!-- label的for的值和input的id的值最好一致，相当于把这两个标签关联在了一块 -->
					<label for="floginacct">登陆账号</label>
					<input type="text" class="form-control" id="floginacct" placeholder="请输入登陆账号">
				  </div>
				  <div class="form-group">
					<label for="fusername">用户名称</label>
					<input type="text" class="form-control" id="fusername" placeholder="请输入用户名称">
				  </div>
				  <div class="form-group">
					<label for="femail">邮箱地址</label>
					<input type="email" class="form-control" id="femail" placeholder="请输入邮箱地址">
					<p class="help-block label label-warning">请输入合法的邮箱地址, 格式为： xxxx@xxxx.com</p>
				  </div>
				  <button id="addBtn" type="button" class="btn btn-success"><i class="glyphicon glyphicon-plus"></i> 新增</button>
				  <button id="resetBtn" type="button" class="btn btn-danger"><i class="glyphicon glyphicon-refresh"></i> 重置</button>
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
            
            $("#addBtn").click(function(){
            	var floginacct = $("#floginacct");
            	var fusername = $("#fusername");
            	var femail = $("#femail");
            	
            	//$.trim()去掉字符串的空格
            	//判断是否为空不要用null，用空串""，因为当内容为空时，值不是null，而是空串
            	if($.trim(floginacct.val())==""){
            		layer.msg("用户账号不能为空，请重新输入", {time:1500, icon:5, shift:6}, function(){
            			floginacct.val("");//将文本框清空
                		floginacct.focus();//将焦点集中到此标签
            		});
            		return false;
            	}else if(fusername.val()==""){
            		layer.msg("用户名不能为空，请重新输入", {time:1500, icon:5, shift:6}, function(){
            			fusername.focus();
            		});
            		return false;
            	}else if(femail.val()==""){
            		layer.msg("邮箱不能为空，请重新输入", {time:1500, icon:5, shift:6}, function(){
            			femail.focus();
            		});
            		return false;
            	}
            	
            	$.ajax({
            		type : "POST",
            		url : "${APP_PATH}/user/doAdd.do",
            		data : {
            			loginacct : floginacct.val(),
            			username : fusername.val(),
            			email : femail.val()
            		},
            		beforeSend : function(){
            			return true;
            		},
            		success : function(result){
            			if(result.success){
            				window.location.href="${APP_PATH}/user/toIndex.htm"
            			}else{
            				layer.msg(result.message,{time:1000,icon:5,shift:6})
            			}
            			
            		},
            		error : function(){
            			layer.msg("新增用户失败",{time:1000,icon:5,shift:6})
            		}
            	
            	});
            });
            
            $("#resetBtn").click(function(){
            	//jquery中没有reset函数，用$("#addForm")[0]，将jquery对象转换为dom对象，再调用reset函数
            	//$("#addForm")[0]相当于用$("xxxx")拿到的是一个数组，[0]指的是数组中第一个元素，也就是addForm表单
            	$("#addForm")[0].reset();
            });
            
        </script>
  </body>
</html>
