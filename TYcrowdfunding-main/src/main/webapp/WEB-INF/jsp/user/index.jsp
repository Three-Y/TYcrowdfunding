<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
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
          <div><a class="navbar-brand" style="font-size:32px;" href="#">众筹平台 - 用户维护</a></div>
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
			<div class="panel panel-default">
			  <div class="panel-heading">
				<h3 class="panel-title"><i class="glyphicon glyphicon-th"></i> 数据列表</h3>
			  </div>
			  <div class="panel-body">
<form class="form-inline" role="form" style="float:left;">
  <div class="form-group has-feedback">
    <div class="input-group">
      <div class="input-group-addon">查询条件</div>
      <input id="queryText" class="form-control has-success" type="text" placeholder="请输入查询条件">
    </div>
  </div>
  <button id="queryBtn" type="button" class="btn btn-warning"><i class="glyphicon glyphicon-search"></i> 查询</button>
</form>
<button id="deletBatchBtn" type="button" class="btn btn-danger" style="float:right;margin-left:10px;"><i class=" glyphicon glyphicon-remove"></i> 删除</button>
<button id="addBtn" type="button" class="btn btn-primary" style="float:right;" onclick="window.location.href='${APP_PATH}/user/toAdd.htm'"><i class="glyphicon glyphicon-plus"></i> 新增</button>
<br>
 <hr style="clear:both;">
          <div class="table-responsive">
            <table class="table  table-bordered">
              <thead>
                <tr >
                  <th width="30">#</th>
				  <th width="30"><input id="allCheckBox" type="checkbox"></th>
                  <th>账号</th>
                  <th>名称</th>
                  <th>邮箱地址</th>
                  <th width="100">操作</th>
                </tr>
              </thead>
              <tbody>
              	<!-- 此处内容动态局部刷新 -->
              </tbody>
			  <tfoot>
			     <tr >
				     <td colspan="6" align="center">
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
	<!-- 使用了layer的小图标，记得导入layer.js文件 -->
	<script type="text/javascript" src="${APP_PATH }/jquery/layer/layer.js"></script>
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
			    queryPageUser(0);
            });
            $("tbody .btn-success").click(function(){
                window.location.href = "assignRole.html";
            });
            $("tbody .btn-primary").click(function(){
                window.location.href = "edit.html";
            });
            
            var loadingIndex = -1;
            var jsonObj = {
            	"pageno" : 1 ,
        		"pageSize" : 10	
            };
            
            function queryPageUser(page_index){
            	jsonObj.pageno = page_index+1;
            	$.ajax({
            		type : "POST",
            		url : "${APP_PATH}/user/doIndex.do",
            		data : jsonObj,
            		beforeSend : function(){
            			//发送请求前页面会显示转圈圈等等加载的小图标
            			loadingIndex = layer.load(2,{time:10*1000});
            			return true;
            		},
            		success : function(result){
            			//关闭转圈圈等加载的小图标
            			layer.close(loadingIndex);
            			if(result.success){
            				var page = result.page ;
            				var datas = page.datas ;
            				var content = '';

							$.each(datas,function(i,n){
							//i表示索引，n表示每个元素，这就是user	
	            				content += '<tr>';
	            				content += '	<td>'+(i+1)+'</td>';
	            				content += '	<td><input id=\"'+n.id+'\" type="checkbox"></td>';
	            				content += '	<td>'+n.loginacct+'</td>';
	            				content += '	<td>'+n.username+'</td>';
	            				content += '	<td>'+n.email+'</td>';
	            				content += '	<td>'
	            				content += '		<button type="button" onclick="window.location.href=\'${APP_PATH}/user/assignRole.do?id='+n.id+'\'" class="btn btn-success btn-xs"><i class=" glyphicon glyphicon-check"></i></button>';
	            				content += '		<button type="button" onclick="window.location.href=\'${APP_PATH}/user/toUpdate.htm?id='+n.id+'\'" class="btn btn-primary btn-xs"><i class=" glyphicon glyphicon-pencil"></i></button>';
	            				content += '		<button type="button" onclick="deleteUser('+n.id+',\''+n.loginacct+'\')" class="btn btn-danger btn-xs"><i class=" glyphicon glyphicon-remove"></i></button>';
	            				content += '	</td>';
	            				content += '</tr>';
            				
							});
							
							$("tbody").html(content);
            				
							//页码条的显示，使用插件jquery.pagination.js
							
							var maxentries = page.totalSize;//maxentries表示一共多少条数据
		            		
		            		$("#Pagination").pagination(maxentries, {
		            		    num_edge_entries : 2, //主体显示多少个页码
		            		    num_display_entries : 2, //边缘显示多少个页码
		            		    callback : queryPageUser, //加载每页内容的函数
		            		    items_per_page : page.pageSize, //每页显示多少条数据
		            		    current_page : (page.pageno-1), //当前页码，0代表第1页
		            		    prev_text : "上一页",
		            		    next_text : "下一页"
		            		});
            				
            			}else{
            				layer.msg(result.message,{time:1000,icon:5,shift:6})
            			}
            		},
            		error : function(){
            			layer.msg("加载数据失败",{time:1000,icon:5,shift:6})
            		}
            	});
            }
            
            $("#queryBtn").click(function(){
            	var queryText = $("#queryText").val();
            	jsonObj.queryText = queryText;
            	queryPageUser(0);
            });
            
            //注意loginnacct参数是一个字符串，上面的按钮的onclick函数中传递参数时用双引号括起来，否则会出错说loginnacct没有定义
            function deleteUser(id,loginacct){
            	//弹出确认窗口
            	layer.confirm("确定要删除["+loginacct+"]用户吗？",  {icon: 3, title:'提示'}, function(cindex){
            		//点击确认，则进行异步请求的操作
            		$.ajax({
                		type : "POST",
                		url : "${APP_PATH}/user/doDelete.do",
                		data : {
                			id : id
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
                			layer.msg("删除数据失败",{time:1000,icon:5,shift:6})
                		}
                	});
    			}, function(cindex){
    				//点击取消，则关闭窗口
    			    layer.close(cindex);
    			});
            }
            
            $("#allCheckBox").click(function(){
            	var checkedStatus = this.checked;
            	$("tbody tr td input[type='checkbox']").prop("checked",checkedStatus);
            });
            
            $("#deletBatchBtn").click(function(){
            	
            	var selectCheckbox = $("tbody tr td input:checked");
            	//var idStr = "";
            	
            	//如果没有被选中的checkbox，什么都不执行
            	if(selectCheckbox.length==0){
            		layer.msg("没有选中任何数据",{time:1000,icon:5,shift:6})
    				return false;
    			}
            	
            	/* $.each(selectCheckbox,function(i,n){
            		//要拼成的串的例子：id=1&id=2&id=3
            		if(i!=0){
            			idStr += "&"
            		}
            		idStr += "id="+n.id
            	}) */
            	
            	var jsonobj = {};
            	
            	$.each(selectCheckbox,function(i,n){
            		jsonobj["userList["+i+"].id"] = n.id;
            	})
            	
            	
            	layer.confirm("确定要批量删除这些用户吗？",  {icon: 3, title:'提示'}, function(cindex){
            		//点击确认，则进行异步请求的操作
            		$.ajax({
                		type : "POST",
                		url : "${APP_PATH}/user/doDeleteBatch.do",
                		data : jsonobj,
                		beforeSend : function(){
                			return true;
                		},
                		success : function(result){
                			if(result.success){
                				window.location.href="${APP_PATH}/user/toIndex.htm"
                			}else{
                				layer.msg("删除数据失败",{time:1000,icon:5,shift:6})
                			}
                		},
                		error : function(){
                			layer.msg("删除数据失败",{time:1000,icon:5,shift:6})
                		}
                	});
    			}, function(cindex){
    				//点击取消，则关闭窗口
    			    layer.close(cindex);
    			});
            });
            
        </script>
  </body>
</html>
