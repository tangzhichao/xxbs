<%@ page language="java" import="java.util.*" pageEncoding="utf-8"%>
<%
	String path = request.getContextPath();
	String basePath = request.getScheme() + "://" + request.getServerName() + ":" + request.getServerPort() + path + "/";
%>
<script src="<%=path%>/static/public/js/utils.js" type="text/javascript"></script>
<script src="<%=path%>/static/user/js/moduleConfig.js" type="text/javascript"></script>
<script src="<%=path%>/static/user/js/columnConfig.js" type="text/javascript"></script>
<script src="<%=path%>/static/user/js/columnHandle.js" type="text/javascript"></script>
<script src="<%=path%>/static/public/js/public.js" type="text/javascript"></script>
<script src="<%=path%>/static/user/js/datatable.js" type="text/javascript"></script>
<script src="<%=path%>/static/user/js/user.js" type="text/javascript"></script>
