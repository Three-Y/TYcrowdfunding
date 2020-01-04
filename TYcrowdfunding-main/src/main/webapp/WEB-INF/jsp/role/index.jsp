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
				<!-- 显示每页内容 -->
              </tbody>
			  <tfoot>
			     <tr >
				     <td colspan="6" align="center">
						<!-- <ul class="pagination">
							
						</ul> -->
						<!-- 显示分页的div -->
						<div id="Pagination" class="pagination">
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
			    
			   queryPage(0);
			   
            });
            
            $("tbody .btn-success").click(function(){
                window.location.href = "assignPermission.html";
            });
            
            var loadingIndex = -1;
            var json = {
            	"pageno" : 1,
            	"pageSize" : 3
            };
            
            function queryPage(page_index){
            	
            	json.pageno = page_index+1;
            	
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
	            			
		            		//页码条的显示，使用插件jquery.pagination.js
		            		
		            		var maxentries = page.totalSize;//maxentries表示一共多少条数据
		            		
		            		$("#Pagination").pagination(maxentries, {
		            		    num_edge_entries : 2, //主体显示多少个页码
		            		    num_display_entries : 2, //边缘显示多少个页码
		            		    callback : queryPage, //加载每页内容的函数
		            		    items_per_page : page.pageSize, //每页显示多少条数据
		            		    current_page : (page.pageno-1), //当前页码，0代表第1页
		            		    prev_text : "上一页",
		            		    next_text : "下一页"
		            		});
		            		
		            		//注意：jquery.pagination.js中，以下这段代码要注释掉，否则会页面代码进入死循环
		            		// 回调函数
        					//opts.callback(current_page, this);
	            			
	            		}else{
	            			layer.msg(result.message,{time:1000,icon:5,shift:6})
	            		}
	            		
	            	},
	            	error : function(){
	            		layer.msg("加载数据失败！",{time:1000,icon:5,shift:6})
	            	}
	            });
            };
            
            
            
        </script>
  </body>
</html>

