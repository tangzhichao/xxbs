<%@page import="com.xxbs.v1.util.math.RandomUtil"%>
<%@ page language="java" import="java.util.*" pageEncoding="utf-8"%>
<%
	String path = request.getContextPath();
	String basePath = request.getScheme() + "://" + request.getServerName() + ":" + request.getServerPort() + path + "/";
%>
<meta charset="utf-8" />
<title>泛小白后台管理系统</title>
<meta content="width=device-width, initial-scale=1" name="viewport" />
<link rel="shortcut icon" type="image/x-icon" href="<%=path%>/static/public/img/favicon.ico" media="screen" />
<link href="<%=path%>/static/global/plugins/font-awesome/css/font-awesome.min.css" rel="stylesheet" type="text/css" />
<link href="<%=path%>/static/global/plugins/bootstrap/css/bootstrap.min.css" rel="stylesheet" type="text/css" />
<link href="<%=path%>/static/global/plugins/uniform/css/uniform.default.css?v=<%=RandomUtil.randomInt(0, 100) %>" rel="stylesheet" type="text/css" />
<link href="<%=path%>/static/global/plugins/jquery-multi-select/css/multi-select.css" rel="stylesheet" type="text/css" />

<link href="<%=path%>/static/global/plugins/datatables/datatables.min.css" rel="stylesheet" type="text/css" />
<link href="<%=path%>/static/global/plugins/datatables/plugins/bootstrap/datatables.bootstrap.css" rel="stylesheet" type="text/css" />
<link href="<%=path%>/static/global/css/components.min.css" rel="stylesheet" id="style_components" type="text/css" />
<link href="<%=path%>/static/global/css/plugins.min.css" rel="stylesheet" type="text/css" />
<link href="<%=path%>/static/global/plugins/artdialog/ui-dialog.css" rel="stylesheet" type="text/css" />
<link href="<%=path%>/static/global/plugins/loading/showLoading.css" rel="stylesheet" type="text/css" />
<link href="<%=path%>/static/layouts/css/layout.min.css" rel="stylesheet" type="text/css" />
<link href="<%=path%>/static/layouts/css/themes/default.min.css" rel="stylesheet" type="text/css" id="style_color" />
<link href="<%=path%>/static/public/css/p_extend.css" rel="stylesheet" type="text/css" id="style_color" />

<link href="<%=path%>/static/global/plugins/bootstrap-switch/css/bootstrap-switch.min.css" rel="stylesheet" type="text/css" />
<link href="<%=path%>/static/global/plugins/bootstrap-select/css/bootstrap-select.min.css" rel="stylesheet" type="text/css" />
<link href="<%=path%>/static/global/plugins/bootstrap-datetimepicker/css/bootstrap-datetimepicker.min.css" rel="stylesheet" type="text/css" />
<link href="<%=path%>/static/global/plugins/select2/css/select2-bootstrap.min.css" rel="stylesheet" type="text/css" />
<link href="<%=path%>/static/global/plugins/select2/css/select2.min.css" rel="stylesheet" type="text/css" />
<link href="<%=path%>/static/global/plugins/jquery-sortable/style.css" rel="stylesheet" type="text/css" />
<link href="<%=path%>/static/global/plugins/imgdrag/css/UserStyle.css" rel="stylesheet" type="text/css" />

<style type="text/css">
body {
	margin: 0px;
}

.actions .blue-hoki {
	padding: 4px 10px;
}

.btn-group-sm>.btn {
	padding: 4px 10px;
	font-size: 14px;
}

#form_attr_img img {
	height: 100%;
}

.form-control {
	height: 28px
}

.form-control select {
	padding: 0px 0px 0px 0px;
	margin: 0px 0px 0px 0px;
}

select.form-control {
	padding: 0px 0px 0px 0px;
	margin: 0px 0px 0px 0px;
}
</style>
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
<script src="<%=path %>/static/global/plugins/jquery.min.js" type="text/javascript"></script>
<script type="text/javascript">
	var rootpath="<%=path%>";
	//alert(rootpath);
	//var rootpath=location.protocol+"//"+location.host;
	//alert(rootpath);
</script>

