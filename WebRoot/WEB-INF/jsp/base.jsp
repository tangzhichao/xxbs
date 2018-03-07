<%@ page language="java" import="java.util.*" pageEncoding="utf-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%
	String path = request.getContextPath();
	String basePath = request.getScheme() + "://"+ request.getServerName() + ":" + request.getServerPort()+ path + "/";
%>

<!DOCTYPE html>
<html lang="zh-cmn-Hans">
<head>
<meta charset="utf-8" />
<title>泛小白后台管理系统</title>
<meta content="width=device-width, initial-scale=1" name="viewport" />
<link rel="shortcut icon" type="image/x-icon" href="<%=path %>/static/public/img/favicon.ico" media="screen" />
<link href="<%=path %>/static/global/plugins/font-awesome/css/font-awesome.min.css" rel="stylesheet" type="text/css" />
<link href="<%=path %>/static/global/plugins/bootstrap/css/bootstrap.min.css" rel="stylesheet" type="text/css" />
<link href="<%=path %>/static/global/plugins/uniform/css/uniform.default.css" rel="stylesheet" type="text/css" />
<link href="<%=path %>/static/global/plugins/jquery-multi-select/css/multi-select.css" rel="stylesheet" type="text/css" />
<!--<link href="global/plugins/bootstrap-switch/css/bootstrap-switch.min.css" rel="stylesheet" type="text/css" />-->
<link href="<%=path %>/static/global/plugins/datatables/datatables.min.css" rel="stylesheet" type="text/css" />
<link href="<%=path %>/static/global/plugins/datatables/plugins/bootstrap/datatables.bootstrap.css" rel="stylesheet" type="text/css" />
<link href="<%=path %>/static/global/css/components.min.css" rel="stylesheet" id="style_components" type="text/css" />
<link href="<%=path %>/static/global/css/plugins.min.css" rel="stylesheet" type="text/css" />
<link href="<%=path %>/static/global/plugins/artdialog/ui-dialog.css" rel="stylesheet" type="text/css" />
<link href="<%=path %>/static/global/plugins/loading/showLoading.css" rel="stylesheet" type="text/css" />
<link href="<%=path %>/static/layouts/css/layout.min.css" rel="stylesheet" type="text/css" />
<link href="<%=path %>/static/layouts/css/themes/default.min.css" rel="stylesheet" type="text/css" id="style_color" />
<link href="<%=path %>/static/public/css/p_extend.css" rel="stylesheet" type="text/css" id="style_color" />
<link href="<%=path %>/js/tree/dtree/dtree.css" rel="stylesheet" type="text/css" />
<script src="<%=path %>/static/global/plugins/jquery.min.js" type="text/javascript"></script>
<script type="text/javascript" src="<%=path %>/js/tree/dtree/dtree.js"></script>
<script type="text/javascript" src="<%=path %>/js/attention/zDialog/zDrag.js"></script>
<script type="text/javascript" src="<%=path %>/js/attention/zDialog/zDialog.js"></script>
<style>
.portlet_search,.portlet_search .portlet-title,.portlet_search .portlet-title .caption
	{
	margin-bottom: 0px;
	margin-top: 0px;
	padding-top: 0px;
	padding-bottom: 0px;
	min-height: 0px;
}
</style>
<jsp:include page="public/style.jsp"></jsp:include>
</head>
<body style="background-color:#E8F6FF;" class="page-container-bg-solid">
	<!-- <div id="BgDiv"></div> -->
	<div class="page-header">
		<div class="page-header-menu" style="height: 60px;padding-top: 5px;">
			<div class="container-fluid">
				<div class="hor-menu  " style="float: right">
					<ul class="nav navbar-nav">
						<li class="menu-dropdown classic-menu-dropdown "><a href="javascript:void(0);"> ${ user.realName } <span class="arrow"></span>
						</a>
							<ul class="dropdown-menu pull-left">
								<li class="" id="edit_password"><a class="nav-link  "> 密码修改 </a></li>
								<li class=" "><a href="/kaifa/showMySupplier/" class="nav-link  "> 我的供应商 </a></li>
							</ul></li>
						<li id="sign_out" class="menu-dropdown classic-menu-dropdown "><a>退出系统 <i class="fa fa-sign-out"></i> <span class="arrow"></span>
						</a></li>

					</ul>
				</div>
				<div class="hor-menu  ">
					<ul class="nav navbar-nav">
						<li id="nav-1-home" class="menu-dropdown classic-menu-dropdown">
							<a href="javascript:void(0);" style="cursor:default;background: #0593d3;">
								欢迎${user.realName}用户，今天是 
								<script>
				                  var weekDayLabels = new Array("星期日","星期一","星期二","星期三","星期四","星期五","星期六");
				                  var now = new Date();
				                  var year=now.getFullYear();
				                  var month=now.getMonth()+1;
				                  var day=now.getDate();
				                  var currentime = year+"年"+month+"月"+day+"日 "+weekDayLabels[now.getDay()];
				                  document.write(currentime);
				                </script> 
							</a>
						</li>
					</ul>
				</div>
			</div>
		</div>
	</div>
	<div class="container-fluid" style="height: 800px;">
		<div class="row">
			<div class="col-md-2" style="width: 15%;">
				<div style="text-align:center;">
					<br /> <a href="javascript: d.openAll();">展开所有</a> | <a href="javascript: d.closeAll();">关闭所有</a>
				</div>
				<script type="text/javascript">
				d = new dTree('d');

				d.add(0,-1,'操作目录');
						d.add(10,0,'用户管理',null,'','');
						d.add(100,10,'所有账号','<%=path %>/user/list2.do','所有账号','frmright');
				
				document.write(d);
				</script>
			</div>
			<div class="col-md-10" style="width: 85%;height:800px;overflow-y: hidden;overflow-x:hidden;border: 0px;margin: 0px;padding: 0px;background-color: white;">
				<IFRAME width="100%" height="100%" frameBorder="0" style="border: 0px;margin: 0px;padding: 0px;background-color: #DCEDFD;background-image: url('<%=path %>/skins/sky/mainframe/welcome.jpg');background-repeat:no-repeat;background-position:90% 80%;" id="frmright" name="frmright"
					src="<%=path %>/open.do" allowTransparency="true" > </IFRAME>
			</div>
		</div>
	</div>
	<div class="navbar-fixed-bottom" style="background-color:#0593D3;height: 50px;">
		<div style="text-align:center;font-size: 14px;color: white;">
			<p>
				版权所有&copy;
				<script>
                  var now = new Date();
                  var year=now.getFullYear();
                  document.write(year);
                </script>
				湖南电信分公司
			</p>
		</div>
	</div>
	<div class="modal fade" id="sample_modal_assign" tabindex="-1" role="dialog" aria-labelledby="myModalLabel">
		<div class="modal-dialog" role="document">
			<div class="modal-content">
				<form id="formvalidation_assign" onkeydown="if(event.keyCode==13)return false;">
					<div class="modal-header">
						<button type="button" class="close" data-dismiss="modal" aria-label="Close">
							<span aria-hidden="true">&times;</span>
						</button>
						<h4 class="modal-title" id="myModalLabel">任务分配</h4>
					</div>
					<div class="modal-body">
						<input type="hidden" id="taskId_assign"> 内部任务<input class="btn btn-default" type="radio" name="type" value="0" onclick="changeT(this)"
							checked="checked" /> 外部任务<input class="btn btn-default" type="radio" name="type" value="1" onclick="changeT(this)" /> <br /> <br />
						<div id="tempContainer">
							<span>分配给：</span> <select id="userId" name="userId" style="width:100px;">
								<option value="{{ u.id }}">{{ u.user_name }}</option>
							</select>
						</div>
					</div>
					<div class="modal-footer">
						<button type="button" class="btn btn-default" data-dismiss="modal">取消</button>
						<label type="button" class="btn btn-default" onclick="submitTaskAssign()">保存</label>
					</div>
					</from>
			</div>
		</div>
	</div>
	<div class="modal fade" id="sample_modal_examine" tabindex="-1" role="dialog" aria-labelledby="myModalLabel">
		<div class="modal-dialog" role="document">
			<div class="modal-content">
				<form id="formvalidation_examine" onkeydown="if(event.keyCode==13)return false;">
					<div class="modal-header">
						<button type="button" class="close" data-dismiss="modal" aria-label="Close">
							<span aria-hidden="true">&times;</span>
						</button>
						<h4 class="modal-title" id="myModalLabel">审核</h4>
					</div>
					<div class="modal-body">
						<input type="hidden" id="taskId_examine" />
						<div class="btn-group" data-toggle="buttons">
							通过<input class="btn btn-default" type="radio" name="examine" value="5" onclick="changeV(this)" checked="checked" /> 不通过<input class="btn btn-default"
								type="radio" name="examine" value="6" onclick="changeV(this)" />
						</div>
						<br /> <br /> 不通过原因：
						<textarea id="reason" name="reason" style="width:100%;" disabled="disabled"></textarea>
						<br />
					</div>
					<div class="modal-footer">
						<button type="button" class="btn btn-default" data-dismiss="modal">取消</button>
						<label type="button" class="btn btn-default" onclick="submitTaskExamine()">保存</label>
					</div>
				</form>
			</div>
		</div>
	</div>

	<script src="<%=path %>/static/global/plugins/artdialog/dialog-plus-min.js" type="text/javascript"></script>
	<script src="<%=path %>/static/global/plugins/bootstrap/js/bootstrap.min.js" type="text/javascript"></script>
	<script src="<%=path %>/static/global/plugins/jquery-slimscroll/jquery.slimscroll.min.js" type="text/javascript"></script>
	<script src="<%=path %>/static/global/plugins/jquery.blockui.min.js" type="text/javascript"></script>
	<script src="<%=path %>/static/global/plugins/uniform/jquery.uniform.min.js" type="text/javascript"></script>
	<script src="<%=path %>/static/global/plugins/jquery-multi-select/js/jquery.multi-select.js" type="text/javascript"></script>
	<script src="<%=path %>/static/global/plugins/loading/jquery.showLoading.min.js" type="text/javascript"></script>
	<!--<script src="global/js/datatable.js" type="text/javascript"></script>-->
	<script src="<%=path %>/static/global/plugins/datatables/datatables.min.js" type="text/javascript"></script>
	<script src="<%=path %>/static/global/plugins/datatables/plugins/bootstrap/datatables.bootstrap.js" type="text/javascript"></script>
	<script src="<%=path %>/static/global/plugins/jquery-validation/js/jquery.validate.min.js" type="text/javascript"></script>
	<script src="<%=path %>/static/public/js/validatorAdd.js" type="text/javascript"></script>
	<script src="<%=path %>/static/global/plugins/jquery-form/jquery.form.js" type="text/javascript"></script>
	<script src="<%=path %>/static/global/js/app.min.js" type="text/javascript"></script>
	<script src="<%=path %>/static/layouts/js/layout.min.js" type="text/javascript"></script>
	<script src="<%=path %>/static/layouts/js/demo.min.js" type="text/javascript"></script>
	<script src="<%=path %>/static/public/js/p_dataTable.js" type="text/javascript"></script>

	<jsp:include page="public/script.jsp"></jsp:include>

	<%-- <script src="<%=path %>/static/picture/js/picture.js" type="text/javascript"></script>
	<script src="<%=path %>/static/picture/js/taskAll.js" type="text/javascript"></script>
	<jsp:include page="public/ready.jsp"></jsp:include> --%>
	<script>
    </script>
</body>
</html>