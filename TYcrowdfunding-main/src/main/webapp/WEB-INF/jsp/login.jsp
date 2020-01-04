<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html lang="zh-CN">
  <head>
    <meta charset="UTF-8">
    <meta http-equiv="X-UA-Compatible" content="IE=edge">
    <meta name="viewport" content="width=device-width, initial-scale=1">
    <meta name="description" content="">
    <meta name="keys" content="">
    <meta name="author" content="">
	<link rel="stylesheet" href="${APP_PATH }/bootstrap/css/bootstrap.min.css">
	<link rel="stylesheet" href="${APP_PATH }/css/font-awesome.min.css">
	<link rel="stylesheet" href="${APP_PATH }/css/login.css">
	<style>

	</style>
  </head>
  <body>
    <nav class="navbar navbar-inverse navbar-fixed-top" role="navigation">
      <div class="container">
        <div class="navbar-header">
          <div><a class="navbar-brand" href="index.html" style="font-size:32px;">尚筹网-创意产品众筹平台</a></div>
        </div>
      </div>
    </nav>

    <div class="container">

      <form method="POST" id="loginForm" class="form-signin" role="form">
        ${exception.message }
        <h2 class="form-signin-heading"><i class="glyphicon glyphicon-log-in"></i> 用户登录</h2>
		  <div class="form-group has-success has-feedback">
			<input type="text" class="form-control" id="floginacct" value="ibbie" name="loginacct" placeholder="请输入登录账号" autofocus>
			<span class="glyphicon glyphicon-user form-control-feedback"></span>
		  </div>
		  <div class="form-group has-success has-feedback">
			<input type="password" class="form-control" id="fuserpswd" value="123" name="userpswd" placeholder="请输入登录密码" style="margin-top:10px;">
			<span class="glyphicon glyphicon-lock form-control-feedback"></span>
		  </div>
		  <div class="form-group has-success has-feedback">
			<select class="form-control" id="ftype" name="type">
                <option value="member" >会员</option>
                <option value="user" >管理</option>
            </select>
		  </div>
        <div class="checkbox">
          <label>
            <input id="rememberMe" type="checkbox" value="remember-me"> 记住我
          </label>
          <br>
          <label>
            忘记密码
          </label>
          <label style="float:right">
            <a href="reg.html">我要注册</a>
          </label>
        </div>
        <a class="btn btn-lg btn-success btn-block" onclick="dologin()" > 登录</a>
      </form>
    </div>
    <script src="${APP_PATH }/jquery/jquery-2.1.1.min.js"></script>
    <script src="${APP_PATH }/bootstrap/js/bootstrap.min.js"></script>
    <script type="text/javascript" src="${APP_PATH }/jquery/layer/layer.js"></script>
    <script>
    function dologin() {
    	//使用id选择器取到对应的标签对象
    	var floginacct = $("#floginacct");
    	var fuserpswd = $("#fuserpswd");
    	var ftype = $("#ftype");
    	var loadingIndex = -1;
    	var rememberMe = $("#rememberMe")[0].checked;//拿到的是布尔值
		
    	
    	//$.trim()去掉字符串的空格
    	//判断是否为空不要用null，用空串""，因为当内容为空时，值不是null，而是空串
    	if($.trim(floginacct.val())==""){
    		layer.msg("用户账号不能为空，请重新输入", {time:1500, icon:5, shift:6}, function(){
    			floginacct.val("");//将文本框清空
        		floginacct.focus();//将焦点集中到此标签
    		});
    		return false;
    	}else if(fuserpswd.val()==""){
    		layer.msg("密码不能为空，请重新输入", {time:1500, icon:5, shift:6}, function(){
    			fuserpswd.focus();
    		});
    		return false;
    	}
    	
    
		$.ajax({
			type : "POST",//请求方式post还是get
			data : {//用jackson对象传递数据，格式：属性名:属性值
				loginacct : floginacct.val(),
				userpswd : fuserpswd.val(),
				type : ftype.val(),
				rememberMe : rememberMe?"1":"0"
			},
			url : "${APP_PATH}/doLogin.do",//请求的url
			beforeSend : function(){//发送请求前的一些操作，一般进行表单数据校验
				loadingIndex = layer.msg('处理中', {icon: 16});
			},
			success : function(result){//请求成功时调用此函数
				layer.close(loadingIndex);
				if(result.success){
					if(ftype.val()=="member"){
						window.location.href="${APP_PATH}/member/toIndex.htm";
					}else if(ftype.val()=="user"){
						window.location.href="${APP_PATH}/main.htm";
					}else{
						layer.msg("用户类型不存在！", {time:1500, icon:2, shift:6});
					}
					//跳转到主页面
					//window.location.href="${APP_PATH}/main.htm";
				}else{
					layer.msg(result.message, {time:1500, icon:2, shift:6});
				}
			},
			error : function(){//请求失败时会调用此函数
				layer.msg("登陆失败！", {time:1500, icon:2, shift:6});
			}
			//complete : function(){},无论请求失败还是成功，最后都会调用此函数
		})
    	
    	
    	
        /* var type = $(":selected").val();
        if ( type == "user" ) {
            window.location.href = "main.html";
        } else {
            window.location.href = "index.html";
        } */
    }
    </script>
  </body>
</html>