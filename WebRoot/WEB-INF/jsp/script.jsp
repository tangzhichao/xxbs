<%@ page language="java" import="java.util.*" pageEncoding="utf-8"%>
<%
	String path = request.getContextPath();
	String basePath = request.getScheme() + "://"
			+ request.getServerName() + ":" + request.getServerPort()
			+ path + "/";
%>
<script src="<%=path%>/static/global/plugins/jquery.min.js" type="text/javascript"></script>

<script src="<%=path%>/static/global/plugins/bootstrap/js/bootstrap.min.js" type="text/javascript"></script>
<script src="<%=path%>/static/global/plugins/jquery-slimscroll/jquery.slimscroll.min.js" type="text/javascript"></script>
<script src="<%=path%>/static/global/plugins/jquery.blockui.min.js" type="text/javascript"></script>
<script src="<%=path%>/static/global/plugins/uniform/jquery.uniform.min.js" type="text/javascript"></script>
<script src="<%=path%>/static/global/plugins/jquery-multi-select/js/jquery.multi-select.js" type="text/javascript"></script>
<script src="<%=path%>/static/global/plugins/loading/jquery.showLoading.min.js" type="text/javascript"></script>
<script src="<%=path%>/static/global/plugins/datatables/datatables.min.js" type="text/javascript"></script>
<script src="<%=path%>/static/global/plugins/datatables/plugins/bootstrap/datatables.bootstrap.js" type="text/javascript"></script>
<script src="<%=path%>/static/global/plugins/jquery-validation/js/jquery.validate.min.js" type="text/javascript"></script>
<script src="<%=path%>/static/public/js/validatorAdd.js" type="text/javascript"></script>
<script src="<%=path%>/static/global/plugins/jquery-form/jquery.form.js" type="text/javascript"></script>
<script src="<%=path%>/static/global/js/app.min.js" type="text/javascript"></script>
<script src="<%=path%>/static/layouts/js/layout.min.js" type="text/javascript"></script>
<script src="<%=path%>/static/layouts/js/demo.min.js" type="text/javascript"></script>
<script src="<%=path%>/static/public/js/p_dataTable.js" type="text/javascript"></script>
<script src="<%=path%>/static/global/plugins/artdialog/dialog-plus-min.js" type="text/javascript"></script>
<script src="<%=path%>/js/attention/zDialog/zDrag.js" type="text/javascript"></script>
<script src="<%=path%>/js/attention/zDialog/zDialog.js" type="text/javascript"></script>
<script src="<%=path%>/static/global/plugins/bootstrap-select/js/bootstrap-select.min.js" type="text/javascript"></script>
<script src="<%=path%>/static/global/plugins/bootstrap-fileinput/bootstrap-fileinput.js" type="text/javascript"></script>
<script src="<%=path%>/static/global/plugins/bootstrap-switch/js/bootstrap-switch.min.js" type="text/javascript"></script>
<script src="<%=path%>/static/global/plugins/bootstrap-datetimepicker/js/bootstrap-datetimepicker.min.js" type="text/javascript"></script>
<script src="<%=path%>/static/global/plugins/bootstrap-datetimepicker/js/bootstrap-datetimepicker.zh-CN.js" type="text/javascript"></script>
<script src="<%=path%>/static/global/plugins/select2/js/select2.full.min.js" type="text/javascript"></script>

<script>
	$.extend($.validator.messages, {
		required : "该字段不能为空",
		remote : "请修正该字段",
		email : "电子邮件格式错误",
		url : "网址格式错误",
		date : "日期格式错误",
		dateISO : "日期格式错误",
		number : "请输入数字",
		digits : "只能输入整数",
		creditcard : "请输入合法的信用卡号",
		equalTo : "请再次输入相同的值",
		accept : "请输入合法的后缀名",
		maxlength : $.validator.format("字符串长度最大是{0}"),
		minlength : $.validator.format("字符串长度最少是{0}"),
		rangelength : $.validator.format("请输入一个长度介于{0}和{1}之间的字符串"),
		range : $.validator.format("请输入一个介于{0}和{1}之间的值"),
		max : $.validator.format("最大值为{0}"),
		min : $.validator.format("最小值为{0}")
	});
</script>