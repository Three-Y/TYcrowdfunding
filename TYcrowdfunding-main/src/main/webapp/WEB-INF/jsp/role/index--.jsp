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
	<link rel="stylesheet" href="${APP_PATH }/jquery/pagination/pagination.css">
	<style>
	.tree li {
        list-style-type: none;
		cursor:pointer;
	}
	table tbody tr:nth-child(odd){background:#F4F4F4;}
	table tbody td:nth-child(even){color:#C00;}
	</style>
  </head>

  <body>

    <nav class="navbar navbar-inverse navbar-fixed-top" role="navigation">
      <div class="container-fluid">
        <div class="navbar-header">
          <div><a class="navbar-brand" style="font-size:32px;" href="#">众筹平台 - 角色维护</a></div>
        </div>
        <div id="navbar" class="navbar-collapse collapse">
          <ul class="nav navbar-nav navbar-right">
            <li style="padding-top:8px;">
				<jsp:include page="/WEB-INF/jsp/common/userInfo.jsp" flush="true" ></jsp:include>
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
				<jsp:include page="/WEB-INF/jsp/common/menu.jsp" flush="true" ></jsp:include>
			</div>
        </div>
        <div class="col-sm-9 col-sm-offset-3 col-md-10 col-md-offset-2 main">
			<div class="panel panel-default">
			  <div class="panel-heading">
				<h3 class="panel-title"><i class="glyphicon glyphicon-th"></i> 数据列表</h3>
			  </div>
			  <div class="panel-body">
<form class="form-inline" role="form" style="float:left;">
  <div class="form-group has-feedback">
    <div class="input-group">
      <div class="input-group-addon">查询条件</div>
      <input class="form-control has-success" type="text" placeholder="请输入查询条件">
    </div>
  </div>
  <button type="button" class="btn btn-warning"><i class="glyphicon glyphicon-search"></i> 查询</button>
</form>
<button type="button" class="btn btn-danger" style="float:right;margin-left:10px;"><i class=" glyphicon glyphicon-remove"></i> 删除</button>
<button type="button" class="btn btn-primary" style="float:right;" onclick="window.location.href='form.html'"><i class="glyphicon glyphicon-plus"></i> 新增</button>
<br>
 <hr style="clear:both;">
          <div class="table-responsive">
            <table class="table  table-bordered">
              <thead>
                <tr >
                  <th width="30">#</th>
				  <th width="30"><input type="checkbox"></th>
                  <th>名称</th>
                  <th width="100">操作</th>
                </tr>
              </thead>
              <tbody>
				<!-- 此处内容异步请求数据，动态局部刷新 -->
              </tbody>
			  <tfoot>
			     <tr >
				     <td colspan="6" align="center">
						<ul class="pagination">
							<!-- 显示分页的div -->
							<div id="Pagination" class="pagination">
						</ul>
					 </td>
				 </tr>

			  </tfoot>
            </table>
          </div>
			  </div>
			</div>
        </div>
      </div>
    </div>

    <script src="${APP_PATH }/jquery/jquery-2.1.1.min.js"></script>
    <script src="${APP_PATH }/bootstrap/js/bootstrap.min.js"></script>
	<script src="${APP_PATH }/script/docs.min.js"></script>
	<script src="${APP_PATH }/jquery/layer/layer.js"></script>
	<script src="${APP_PATH }/jquery/pagination/jquery.pagination.js"></script>
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
			    
			    queryPage(1);
            });
            
            $("tbody .btn-success").click(function(){
                window.location.href = "assignPermission.html";
            });
            
            var loadingIndex = -1;
            var json = {
            	"pageno" : 1,
            	"pageSize" : 3
            };
            
            function queryPage(pageno){
            	
            	json.pageno = pageno;
            	
	            $.ajax({
	            	type : "POST",
	            	url : "${APP_PATH}/role/doData.do",
	            	data : json,
	            	beforeSend : function(){
	            		loadingIndex = layer.load(2,{time:10*1000});
	        			return true;
	            	},
	            	success : function(result){
	            		
	            		layer.close(loadingIndex);
	            		
	            		if(result.success){
	            			
	            			var page = result.page ;
	            			var datas = page.datas ;
	            			var content = '';
	            			
	            			//表格内容
	            			$.each(datas,function(i,n){
							//i表示索引，n表示每个元素
		            			content += '<tr>';
		           				content += '  <td>'+n.id+'</td>';
		           				content += '  <td><input type="checkbox"></td>';
		           				content += '  <td>'+n.name+'</td>';
		           				content += '  <td>';
		           				content += '	  <button type="button" class="btn btn-success btn-xs" onclick="window.location.href=\'${APP_PATH}/role/toAssignPermission.htm?roleid='+n.id+'\'" ><i class=" glyphicon glyphicon-check"></i></button>';
		           				content += '	  <button type="button" class="btn btn-primary btn-xs"><i class=" glyphicon glyphicon-pencil"></i></button>';
		           				content += '	  <button type="button" class="btn btn-danger btn-xs"><i class=" glyphicon glyphicon-remove"></i></button>';
		           				content += '  </td>';
		           				content += '</tr>';
	            			});
	            			$("tbody").html(content);
	            		}else{
	            			layer.msg(result.message,{time:1000,icon:5,shift:6})
	            		}
	            		
	            		//上下页和页码按钮
	            		var contentBar = '';
	            		
	            		//上一页
	    				if(page.pageno==1){
	    					contentBar += '<li class="disabled"><a href="#">上一页</a></li>';
	    				}else{
	    					contentBar += '<li><a href="#" onclick="queryPage('+(page.pageno-1)+')">上一页</a></li>';
	    				};
						
						//页码
	    				for(var i = 1;i <= page.totalno ; i++){
	    					contentBar += '<li ';
	    					if(page.pageno==i){
	    						contentBar += 'class="active"';
	    					}
	    					contentBar += '><a href="#" onclick="queryPage('+i+')">'+i+'<span class="sr-only">(current)</span></a></li>';
	    				};
						
						//下一页
						if(page.pageno==page.totalno){
	    					contentBar += '<li class="disabled"><a href="#">下一页</a></li>';
	    				}else{
	    					contentBar += '<li><a href="#" onclick="queryPage('+(page.pageno+1)+')">下一页</a></li>';
	    				};
	    				
						$(".pagination").html(contentBar);
	            		
	            	},
	            	error : function(){
	            		layer.msg("加载数据失败！",{time:1000,icon:5,shift:6})
	            	}
	            });
            };
            
            //页码条（jequry的插件jquery.pagination.js）
            $(function(){
            	//这是一个非常简单的demo实例，让列表元素分页显示
            	//回调函数的作用是显示对应分页的列表项内容
            	//回调函数在用户每次点击分页链接的时候执行
            	//参数page_index{int整型}表示当前的索引页
            	var initPagination = function() {
            		var num_entries = $("#hiddenresult div.result").length;
            		// 创建分页
            		$("#Pagination").pagination(num_entries, {
            			num_edge_entries: 1, //边缘页数
            			num_display_entries: 2, //主体页数
            			callback: queryPage,
            			items_per_page:1 //每页显示1项
            		});
            	 }();
            	 
            	function pageselectCallback(page_index, jq){
            		var new_content = $("#hiddenresult div.result:eq("+page_index+")").clone();
            		$("#Searchresult").empty().append(new_content); //装载对应分页的内容
            		return false;
            	}
            });
            
        </script>
  </body>
</html>

