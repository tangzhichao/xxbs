<%@page import="com.xxbs.v1.util.web.AutoGenerater"%>
<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%
	String path = request.getContextPath();
String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path+"/";
%>

<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
<head>
<jsp:include page="../style.jsp"></jsp:include>
<jsp:include page="userStyle.jsp"></jsp:include>
</head>
<body>
<input type="hidden" id="selected_row_ids" />
<div class="container-fluid">
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

	<div class="page-content-inner">
		<div class="row">
			<div class="col-md-12">
				<div class="portlet light ">
					<!-- {% block portlet-title %}
                            {% endblock %} -->
                           <div>AAA</div>
                            	<jsp:include page="userSearch.jsp"></jsp:include>
                            <div>BBB</div>
					<div class="portlet-body">
						<table class="table table-striped table-bordered table-hover table-checkable order-column table-checkable" id="data_table">
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
			</div>
		</div>
	</div>
</div>
<jsp:include page="userDialog.jsp"></jsp:include>
</body>
<jsp:include page="../script.jsp"></jsp:include>
<jsp:include page="userScript.jsp"></jsp:include>
<jsp:include page="ready.jsp"></jsp:include>
</html>
