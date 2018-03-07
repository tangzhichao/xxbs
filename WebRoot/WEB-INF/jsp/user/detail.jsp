<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%
String path = request.getContextPath();
String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path+"/";
%>

<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
  <head>
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
<style>
td.lab {
	text-align: right !important;
}
</style>
<jsp:include page="../public/style.jsp"></jsp:include>
  </head>
  
  <body>
	<input name="id" type="hidden" value="${role.id}"/>
	<table class="table table-striped table-bordered table-hover table-checkable order-column table-checkable">
		<tr  class="heading">
			<td style="width: 25%;" class="lab">账号：</td>
			<td style="width: 25%;">${user.userName}</td>
			<td style="width: 25%;" class="lab">姓名：</td>
			<td style="width: 25%;">${user.realName}</td>
		</tr>
		<tr>
			<td class="lab">手机号：</td>
			<td>${user.phone}</td>
			<td class="lab">账号角色等级：</td>
			<td>
				<c:if test="${user.lv == 1}">省管理员</c:if>
				<c:if test="${user.lv == 2}">市管理员</c:if>
				<c:if test="${user.lv == 3}">营业厅、代理商</c:if>
				<c:if test="${user.lv == 4}">一线、自有人员、分销经理</c:if>
				<c:if test="${user.lv == 5}">分销员</c:if>
			</td>
		</tr>
		<tr>
			<td class="lab">账号角色：</td>
			<td>${user.roleReamName}</td>
			<td class="lab">证件号码：</td>
			<td>${user.card}</td>
		</tr>
		<tr>
			<td class="lab">渠道：</td>
			<td>${user.channel_Id}</td>
			<td class="lab">网点编码：</td>
			<td>${user.branchCode}</td>
		</tr>
		<tr>
			<td class="lab">本地网：</td>
			<td>${user.city}</td>
			<td class="lab">本地网编码：</td>
			<td>${user.latnId}</td>
		</tr>
		<tr>
			<td class="lab">市县：</td>
			<td>${user.county}</td>
			<td class="lab">工号编码：</td>
			<td>${user.workNumber}</td>
		</tr>
		<tr>
			<td class="lab">工号部门：</td>
			<td>${user.workSection}</td>
			<td class="lab">工号姓名：</td>
			<td>${user.workName}</td>
		</tr>
		<tr>
			<td class="lab">工号状态：</td>
			<td>
				<c:if test="${user.lv == 0}">有效</c:if>
				<c:if test="${user.lv == 1}">无效</c:if>
			</td>
			<td class="lab">翼支付账号：</td>
			<td>${user.yzfAccount}</td>
		</tr>
		<tr>
			<td class="lab">结对营业厅：</td>
			<td>
				<c:if test="${user.upId != null}"><input class="btn blue" type="button" onclick="view(${user.upId});" value=" 查看结对账号 "/></c:if>
				<c:if test="${user.upId == null}">无结对账号信息</c:if>
			</td>
			<td class="lab">上级账号：</td>
			<td>
				<c:if test="${user.parentId != null}"><input class="btn blue" type="button" onclick="view(${user.parentId});" value=" 查看上级账号 "/></c:if>
				<c:if test="${user.parentId == null}">无上级账号信息</c:if>
			</td>
		</tr>
		<tr>
			<td class="lab">组长账号：</td>
			<td>
				<c:if test="${user.groupId != null}"><input class="btn blue" type="button" onclick="view(${user.groupId});" value=" 查看组长账号 "/></c:if>
				<c:if test="${user.groupId == null}">无组长账号信息</c:if>
			</td>
			<td class="lab">是否组长：</td>
			<td>
				<c:if test="${user.isGroupLeader == null}">无相关信息</c:if>
				<c:if test="${user.isGroupLeader == 0}">不是</c:if>
				<c:if test="${user.isGroupLeader == 1}">是</c:if>
			</td>
		</tr>
		<tr>
			<td class="lab">是否已实名：</td>
			<td>
				<c:if test="${user.isRealName == null}">无相关信息</c:if>
				<c:if test="${user.isRealName == 0}">未实名</c:if>
				<c:if test="${user.isRealName == 1}">已实名</c:if>
			</td>
			<td class="lab">创建时间：</td>
			<td>${user.createTime}</td>
		</tr>
		<tr>
			<td class="lab">cpsId：</td>
			<td>${user.cpsId}</td>
			<td class="lab">营业厅名称：</td>
			<td>${user.sealName}</td>
		</tr>
		<tr>
			<td class="lab">销售员编码：</td>
			<td>${user.salecode}</td>
			<td class="lab"></td>
			<td></td>
		</tr>
	</table>

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

	<jsp:include page="../public/script.jsp"></jsp:include>

	<%-- <script src="<%=path %>/static/picture/js/picture.js" type="text/javascript"></script>
	<script src="<%=path %>/static/picture/js/taskAll.js" type="text/javascript"></script>
	<jsp:include page="public/ready.jsp"></jsp:include> --%>	
</body>
</html>