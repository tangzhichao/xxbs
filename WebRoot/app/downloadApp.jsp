<%@ page language="java" import="java.util.*" pageEncoding="utf-8"%>
<%
	String path = request.getContextPath();
	String basePath = request.getScheme() + "://"
			+ request.getServerName() + ":" + request.getServerPort()
			+ path + "/";
%>

<!DOCTYPE html>
<!-- saved from url=(0041)http://ln212.huilongkj.com/msty/msty.html -->
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">

<title>泛小白客户端下载</title>
<meta charset="utf-8">
<meta name="apple-touch-fullscreen" content="YES">
<meta name="format-detection" content="telephone=no">
<meta name="apple-mobile-web-app-capable" content="yes">
<meta name="apple-mobile-web-app-status-bar-style" content="black">
<meta http-equiv="Expires" content="-1">
<meta http-equiv="pragram" content="no-cache">
<meta name="viewport"
	content="width=640, user-scalable=no, target-densitydpi=device-dpi">
<!-- <link rel="Shortcut Icon" href="http://ln212.huilongkj.com/msty/yyx.ico"> -->
<style>
* {
	margin: 0;
	padding: 0;
}

body,html {
	min-width: 320px;
	max-width: 640px;
	margin: 0 auto;
	width: 100%;
	background: #fff;
}

header {
	display: block;
	width: 34%;
	margin: 10% 0 0 33%;
}

header img {
	width: 100%;
}

footer {
	display: block;
	width: 90%;
	margin: 10% 0 10% 5%;
}

footer img {
	width: 100%;
}

section {
	text-align: center;
	margin: 10% 0 0 0;
}

a {
	display: inline-block;
	width: 45%;
}

a img {
	width: 90%;
}
</style>

<script type="text/javascript">
	var u = navigator.userAgent;
	var isAndroid = u.indexOf('Android') > -1 || u.indexOf('Adr') > -1; //android终端
	var isiOS = !!u.match(/\(i[^;]+;( U;)? CPU.+Mac OS X/); //ios终端

	function down(type) {
		if (type == "ios") {
			if (isiOS) {
				// window.location.href ="itms-services://?action=download-manifest&url=https://it.jisu8.cn/plist/yyx/yyx2.plist"; 
			} else {
				alert('只能ios设备下载安装');
			}
		} else if (type == "android") {
			if (isAndroid) {
				window.open("http://fxb.hn.189.cn/fxb/app/fxb-release.apk");
			} else {
				alert('只能android设备下载安装');
			}
		}
	}

	function isWeiXin() {
		var ua = window.navigator.userAgent.toLowerCase();
		if (ua.match(/MicroMessenger/i) == 'micromessenger') {
			return true;
		} else {
			return false;
		}
	}

	function aa() {
		if (isiOS) {
			document.getElementById("links").href = "itms-services://?action=download-manifest&url=https://raw.githubusercontent.com/zhichaotang/blog/master/fxb.plist";
		} else {
			document.getElementById("links").href = "javascript:down('ios');";
		}
	}
</script>
</head>

<body onload="aa()">

	<header>
		<img src="<%=path%>/resources/images/loginqrcode/top.png">
	</header>

	<section>
		<a class="btn_1" id="links" href="javascript:down('ios');"><img
			src="<%=path%>/resources/images/loginqrcode/btn_01.png"></a> <a
			class="btn_2" href="javascript:down('android');"><img
			src="<%=path%>/resources/images/loginqrcode/btn_02.png"></a>
	</section>

	<footer>
		<img src="<%=path%>/resources/images/loginqrcode/font.png">
	</footer>

</body>
</html>