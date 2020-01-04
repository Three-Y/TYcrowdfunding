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
          <div><a class="navbar-brand" style="font-size:32px;" href="#">众筹平台 - 流程管理</a></div>
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
      <input class="form-control has-success" type="text" placeholder="请输入查询条件">
    </div>
  </div>
  <button type="button" class="btn btn-warning"><i class="glyphicon glyphicon-search"></i> 查询</button>
</form>
<button id="uploadProcessDefFileBtn" type="button" class="btn btn-primary" style="float:right;" onclick=""><i class="glyphicon glyphicon-upload"></i> 上传流程定义文件</button>
<br>
 <hr style="clear:both;">
          <div class="table-responsive">
          <!-- 上传流程定义的表单 -->
          <form id="deployForm" method="post" action="" enctype="multipart/form-data" style="float:left;">
          	<input type="file" class="form-control" id="deployProcessDefFile" name="deployFile" placeholder="请上传流程定义文件" style="display:none;height:40px;width:250px" >
          </form>
            <table class="table  table-bordered">
              <thead>
                <tr >
                  <th width="30">#</th>
                  <th>流程名称</th>
                  <th>流程版本</th>
                  <th>流程key</th>
                  <th width="100">操作</th>
                </tr>
              </thead>
              <tbody>
                <!-- <tr>
                  <td>1</td>
                  <td>实名认证审批流程</td>
                  <td>2</td>
                  <td>人工审核</td>
                  <td>张三</td>
                  <td>
                      <button type="button" class="btn btn-success btn-xs"><i class=" glyphicon glyphicon-eye-open"></i></button>
                      <button type="button" class="btn btn-danger btn-xs"><i class=" glyphicon glyphicon-remove"></i></button>
				  </td>
                </tr> -->
                
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
	<script src="${APP_PATH }/jquery/layer/layer.js"></script>
	<script src="${APP_PATH }/jquery/pagination/jquery.pagination.js"></script>
	<script src="${APP_PATH }/jquery/jquery_form/jquery.form.js"></script>
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
            
            var jsonObj = {
            	"pageno" : 1 ,
            	"pageSize" : 10
            };
            
            function queryPage(page_index){
            	jsonObj.pageno = page_index+1;
            	$.ajax({
            		type : "POST",
            		url : "${APP_PATH}/process/doData.do",
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
							//i表示索引，n表示每个元素
								content += '<tr>';
								content += '	<td>'+(i+1)+'</td>';
								content += '	<td>'+n.name+'</td>';
								content += '	<td>'+n.version+'</td>';
								content += '	<td>'+n.key+'</td>';
								content += '	<td>';
								content += '		<button type="button" class="btn btn-success btn-xs" onclick="window.location.href=\'${APP_PATH}/process/showImg.htm?id='+n.id+'&name='+n.name+'\'"><i class=" glyphicon glyphicon-eye-open"></i></button>';
								content += '		<button type="button" class="btn btn-danger btn-xs" onclick="deleteProcessDef(\''+n.id+'\',\''+n.name+'\')"><i class=" glyphicon glyphicon-remove"></i></button>';
								content += '	</tda>';
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
            				
            			}else{
            				layer.msg(result.message,{time:1000,icon:5,shift:6})
            			}
            		},
            		error : function(){
            			layer.msg("加载数据失败",{time:1000,icon:5,shift:6})
            		}
            	});
            }
            
            $("#uploadProcessDefFileBtn").click(function(){
            	$("#deployProcessDefFile").click();
            })
            
            $("#deployProcessDefFile").change(function(){
            	var options = {
                		url : "${APP_PATH}/process/deploy.do",
                		beforeSubmit : function(){//注意不是beforeSend
                			loadingIndex = layer.msg('上传中...', {icon: 16});
                		},
                		success : function(result){
                			layer.close(loadingIndex);
                			if(result.success){
                				layer.msg(result.message, {time:1000, icon:6, shift:6});
                				$("#deployForm")[0].reset();
                				queryPage(0);
                				
                			}else{
                				layer.msg(result.message, {time:1000, icon:5, shift:6});
                			}	
                		}
                	}
                	//将options传递给此方法，发起异步请求
                	$("#deployForm").ajaxSubmit(options);
            })
            
            function deleteProcessDef(id,name){
            	
            	layer.confirm("确定要删除["+name+"]吗？",{icon: 3,title: '提示'},function(cindex){
            		layer.close(cindex);
            		$.ajax({
                		type : "POST",
                		url : "${APP_PATH}/process/delete.do",
                		data : {
                			"id" : id
                		},
                		beforeSend : function(){
                			loadingIndex = layer.msg('正在删除...', {icon: 16});
                		},
                		success : function(result){
               				layer.close(loadingIndex);
                			if(result.success){
                				layer.msg(result.message, {time:1000, icon:6, shift:6});
                				queryPage(0);
                			}else{
                				layer.msg(result.message, {time:1000, icon:6, shift:6});
                			}
                		},
                		error : function(){
                			layer.msg("删除失败！", {time:1000, icon:6, shift:6});
                		},
                	})	
            	},
            	function(cindex){
            		layer.close(cindex);
            	})
            	
            }
            
        </script>
  </body>
</html>
