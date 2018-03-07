<%@ page language="java" import="java.util.*" pageEncoding="utf-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%
	String path = request.getContextPath();
	String basePath = request.getScheme() + "://"+ request.getServerName() + ":" + request.getServerPort()+ path + "/";
%>

<!DOCTYPE html>
<html>
<head>
<jsp:include page="style.jsp"></jsp:include>
<link href="<%=path%>/js/tree/dtree/dtree.css" rel="stylesheet" type="text/css" />
<script type="text/javascript" src="<%=path %>/js/tree/dtree/dtree.js"></script>
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
								欢迎${user.nickName}用户，今天是 
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
	<div class="container-fluid" style="height: 1000px;">
		<div class="row">
			<div class="col-md-2" style="width: 15%;">
				<div style="text-align:center;">
					<br /> <a href="javascript: d.openAll();">展开所有</a> | <a href="javascript: d.closeAll();">关闭所有</a>
				</div>
				<script type="text/javascript">
				d = new dTree('d');

				d.add(0,-1,'操作目录');
						d.add(10,0,'用户管理',null,'','');
						d.add(100,10,'所有账号','<%=path %>/user/list.do','所有账号','frmright');
				document.write(d);
				</script>
			</div>
			<div class="col-md-10" style="width: 85%;height:1000px;overflow-y: hidden;overflow-x:hidden;border: 0px;margin: 0px;padding: 0px;background-color: white;">
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
<jsp:include page="script.jsp"></jsp:include>
</body>
</html>