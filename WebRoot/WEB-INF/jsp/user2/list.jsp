<%@page import="com.xxbs.v1.util.web.AutoGenerater"%>
<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%
String path = request.getContextPath();
String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path+"/";
%>

<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
  <head>
<meta charset="utf-8" />
<title>泛小白后台管理系统</title>
<meta content="width=device-width, initial-scale=1" name="viewport" />
<!-- BEGIN GLOBAL MANDATORY STYLES -->
<link rel="shortcut icon" type="image/x-icon" href="<%=path %>/static/public/img/favicon.ico" media="screen" />
<link href="<%=path %>/static/global/plugins/font-awesome/css/font-awesome.min.css" rel="stylesheet" type="text/css" />
<link href="<%=path %>/static/global/plugins/bootstrap/css/bootstrap.min.css" rel="stylesheet" type="text/css" />
<link href="<%=path %>/static/global/plugins/uniform/css/uniform.default.css" rel="stylesheet" type="text/css" />
<link href="<%=path %>/static/global/plugins/jquery-multi-select/css/multi-select.css" rel="stylesheet" type="text/css" />
<!--<link href="global/plugins/bootstrap-switch/css/bootstrap-switch.min.css" rel="stylesheet" type="text/css" />-->
<!-- END GLOBAL MANDATORY STYLES -->
<!-- BEGIN PAGE LEVEL PLUGINS -->
<link href="<%=path %>/static/global/plugins/datatables/datatables.min.css" rel="stylesheet" type="text/css" />
<link href="<%=path %>/static/global/plugins/datatables/plugins/bootstrap/datatables.bootstrap.css" rel="stylesheet" type="text/css" />
<!-- END PAGE LEVEL PLUGINS -->
<!-- BEGIN THEME GLOBAL STYLES -->
<link href="<%=path %>/static/global/css/components.min.css" rel="stylesheet" id="style_components" type="text/css" />
<link href="<%=path %>/static/global/css/plugins.min.css" rel="stylesheet" type="text/css" />
<link href="<%=path %>/static/global/plugins/artdialog/ui-dialog.css" rel="stylesheet" type="text/css" />
<link href="<%=path %>/static/global/plugins/loading/showLoading.css" rel="stylesheet" type="text/css" />
<!-- END THEME GLOBAL STYLES -->
<!-- BEGIN THEME LAYOUT STYLES -->
<link href="<%=path %>/static/layouts/css/layout.min.css" rel="stylesheet" type="text/css" />
<link href="<%=path %>/static/layouts/css/themes/default.min.css" rel="stylesheet" type="text/css" id="style_color" />
<link href="<%=path %>/static/public/css/p_extend.css" rel="stylesheet" type="text/css" id="style_color" />
<!-- END THEME LAYOUT STYLES -->

<link href="<%=path %>/js/tree/dtree/dtree.css" rel="stylesheet" type="text/css"/>

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
<jsp:include page="../public/style.jsp"></jsp:include>
  </head>
  
  <body>
<input type="hidden" id="selectedUserId" />

    <!-- BEGIN PAGE CONTENT BODY -->

				<div class="container-fluid">
					<!-- BEGIN PAGE BREADCRUMBS -->
					<div class="row">
						<!-- <div class="col-md-6">
							<ul class="page-breadcrumb breadcrumb">
								<li><a href="{{ node.href }}">{{ node.name }}</a> <i class="fa fa-chevron-right"></i></li>
								<li><strong style="color: rgba(102, 102, 102, 0.87);">{{ node.name }}</strong></li>
							</ul>
						</div> -->
						<div class="col-md-6">
							<!-- {% block head_right %}
                    {% endblock %} -->
						</div>
					</div>

					<!-- END PAGE BREADCRUMBS -->
					<!-- BEGIN PAGE CONTENT INNER -->
					<div class="page-content-inner">
						<div class="row">
							<div class="col-md-12">
								<!-- BEGIN EXAMPLE TABLE PORTLET-->
								<div class="portlet light ">
									<!-- {% block portlet-title %}

                                {% endblock %} -->
                                	<jsp:include page="../public/userSearch.jsp"></jsp:include>
                                	
									<div class="portlet-body">
										<table class="table table-striped table-bordered table-hover table-checkable order-column table-checkable" id="sample_table">
											<thead>
												<tr class="heading">
													<%=AutoGenerater.getColumnHeaderHtml("user")%>
												</tr>
											</thead>
											<tbody>
											</tbody>
										</table>
									</div>
								</div>
								<!-- END EXAMPLE TABLE PORTLET-->
							</div>
						</div>
					</div>
					<!-- END PAGE CONTENT INNER -->
				</div>
			<!-- END PAGE CONTENT BODY -->
			<!-- END CONTENT BODY -->
		<jsp:include page="../public/userDialog.jsp"></jsp:include>
  </body>
  
 	<!-- END CONTAINER -->
	<!-- BEGIN CORE PLUGINS -->
	<script src="<%=path %>/static/global/plugins/artdialog/dialog-plus-min.js" type="text/javascript"></script>
	<script src="<%=path %>/static/global/plugins/bootstrap/js/bootstrap.min.js" type="text/javascript"></script>
	<script src="<%=path %>/static/global/plugins/jquery-slimscroll/jquery.slimscroll.min.js" type="text/javascript"></script>
	<script src="<%=path %>/static/global/plugins/jquery.blockui.min.js" type="text/javascript"></script>
	<script src="<%=path %>/static/global/plugins/uniform/jquery.uniform.min.js" type="text/javascript"></script>
	<script src="<%=path %>/static/global/plugins/jquery-multi-select/js/jquery.multi-select.js" type="text/javascript"></script>
	<script src="<%=path %>/static/global/plugins/loading/jquery.showLoading.min.js" type="text/javascript"></script>
	<!-- END CORE PLUGINS -->
	<!-- BEGIN PAGE LEVEL PLUGINS -->
	<!--<script src="global/js/datatable.js" type="text/javascript"></script>-->
	<script src="<%=path %>/static/global/plugins/datatables/datatables.min.js" type="text/javascript"></script>
	<script src="<%=path %>/static/global/plugins/datatables/plugins/bootstrap/datatables.bootstrap.js" type="text/javascript"></script>
	<script src="<%=path %>/static/global/plugins/jquery-validation/js/jquery.validate.min.js" type="text/javascript"></script>
	<script src="<%=path %>/static/public/js/validatorAdd.js" type="text/javascript"></script>
	<script src="<%=path %>/static/global/plugins/jquery-form/jquery.form.js" type="text/javascript"></script>
	<!-- END PAGE LEVEL PLUGINS -->
	<!-- BEGIN THEME GLOBAL SCRIPTS -->
	<script src="<%=path %>/static/global/js/app.min.js" type="text/javascript"></script>
	<!-- END THEME GLOBAL SCRIPTS -->
	<!-- BEGIN THEME LAYOUT SCRIPTS -->
	<script src="<%=path %>/static/layouts/js/layout.min.js" type="text/javascript"></script>
	<script src="<%=path %>/static/layouts/js/demo.min.js" type="text/javascript"></script>
	<script src="<%=path %>/static/public/js/p_dataTable.js" type="text/javascript"></script>

	<!-- END THEME LAYOUT SCRIPTS -->
	<jsp:include page="../public/script.jsp"></jsp:include>
	
	<script src="<%=path %>/static/user/js/picture.js" type="text/javascript"></script>
	<script src="<%=path %>/static/user/js/taskAll.js" type="text/javascript"></script>
	<jsp:include page="../public/ready.jsp"></jsp:include>
</html>
