<%@ page language="java" import="java.util.*" pageEncoding="utf-8"%>
<%
	String path = request.getContextPath();
	String basePath = request.getScheme() + "://"+ request.getServerName() + ":" + request.getServerPort()+ path + "/";
%>
    <link href="<%=path %>/static/global/plugins/bootstrap-select/css/bootstrap-select.min.css" rel="stylesheet"
          type="text/css"/>
    <link href="<%=path %>/static/global/plugins/jquery-multi-select/css/multi-select.css" rel="stylesheet"
          type="text/css"/>
    <link href="<%=path %>/static/global/plugins/bootstrap-switch/css/bootstrap-switch.min.css" rel="stylesheet"
          type="text/css"/>
    <link href="<%=path %>/static/global/plugins/bootstrap-datetimepicker/css/bootstrap-datetimepicker.min.css" rel="stylesheet"
          type="text/css"/>
    <link href="<%=path %>/static/global/plugins/select2/css/select2-bootstrap.min.css" rel="stylesheet"
          type="text/css"/>
    <link href="<%=path %>/static/global/plugins/select2/css/select2.min.css" rel="stylesheet"
          type="text/css"/>
    <link href="<%=path %>/static/global/plugins/jquery-sortable/style.css" rel="stylesheet"
          type="text/css"/>
    <link href="<%=path %>/static/global/plugins/imgdrag/css/UserStyle.css" rel="stylesheet"
          type="text/css"/>
    
    <style type="text/css">
        body {
            margin: 0px;
        }

        .actions .blue-hoki{
            padding: 4px 10px;
        }

        .btn-group-sm>.btn{
            padding: 4px 10px;
            font-size: 14px;
        }

        #form_attr_img img {
            height: 100%;
        }
        .form-control{height:28px}
        .form-control select{padding: 0px 0px 0px 0px;margin: 0px 0px 0px 0px;}
        select.form-control {
			padding: 0px 0px 0px 0px;
			margin: 0px 0px 0px 0px;
		}
    </style>
<script type="text/javascript">
	var rootpath="<%=path%>";
	//alert(rootpath);
	//var rootpath=location.protocol+"//"+location.host;
	//alert(rootpath);
</script>