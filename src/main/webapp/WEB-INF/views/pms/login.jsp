<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%
	String path = request.getContextPath();
	String basePath = request.getScheme() + "://" + request.getServerName() + ":" + request.getServerPort()
			+ path + "/";
%>

<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
<head>
<base href="<%=basePath%>">
<title>登录</title>
<meta name="renderer" content="webkit">
<meta http-equiv="X-UA-Compatible" content="IE=edge,chrome=1">
<meta name="viewport"
	content="width=device-width, initial-scale=1, maximum-scale=1">
<link rel="stylesheet" href="resources/layui/css/layui.css" media="all">
<script type="text/javascript" src="resources/md5.js"></script>
<script type="text/javascript" src="resources/layui/layui.js"></script>
<script type="text/javascript" src="resources/jquery-3.2.1.min.js"></script>
<style>
::-ms-clear, ::-ms-reveal {
	display: none;
}
.list{
    position: absolute;
    top: 100px;bottom: 0;left: 0;
    margin: auto;
    width: 100%;height: 240px;
    background: rgba(256,256,256,.2)
}
.list-text{
    position: absolute;
    top: 0;bottom: 0;left: 0;right: 0;
    width: auto;height: auto;
    text-align:center;
}
.list-text p{
    color: #fff;
}
</style>
</head>
<body>
	<div id="back"
		style="width: 100%;height: 810px;margin-top: 0px;position: absolute;background-image: url(resources/backstage/login/image/bg.jpg);background-size: 100% 100%">
		<div style="margin-top: 200px;">
		<div class="list">
		<div class="list-text">
			<p style="font-size: 40px;margin-top: 50px;letter-spacing: 0.1rem; margin-right: 28%">人事档案管理系统</p>
			<p style="font-size: 24px;margin-top: 20px;margin-right: 28%">Personnel Archives Management System</p>
		</div>
	   </div>
		<div id="div-form"
			style="width: 434px;height: 480px;background: white;margin-left: 63%;border: solid 1px silver">
			<div style="margin-top: 36px;">
				<div class="layui-inline" style="margin-left:165px;width: 40px">
					<img height="100" width="100" src="resources/backstage/login/image/mini.png">
				</div>
				
			</div>
			<div id="user_input"
				style="border: solid 1px #cccccc;border-radius:5px;width: 384px;height: 50px;margin-top: 60px;margin-left: 25px">
				<img style="margin-left: 14px" class="layui-inline"
					src="resources/backstage/login/image/用户名.png">
				<div class="layui-inline">
					<input
						style="font-size: 18px;border: 0px;height: 47px;margin-top: 2px;margin-left: 22px;width: 270px"
						type="text" id="username" onfocus="user_input_focus_brder()"
						onblur="user_input_blur_brder()" placeholder="请输入用户名"
						autocomplete="off" class="layui-input">
				</div>
			</div>
			<div id="pwd_input"
				style="border: solid 1px #cccccc;border-radius:5px;width: 384px;height: 50px;margin-top: 30px;margin-left: 25px">
				<img style="margin-left: 14px" class="layui-inline"
					src="resources/backstage/login/image/密码.png">
				<div class="layui-inline">
					<input
						style="font-size: 18px;border: 0px;height: 47px;margin-top: 2px;margin-left: 25px;width: 270px"
						type="password" id="password" onfocus="pwd_input_focus_brder()"
						onblur="pwd_input_blur_brder()" placeholder="请输入密码"
						autocomplete="off" class="layui-input">
				</div>
				<i onclick="changepwd()" class="layui-inline"
					style="margin-right: 10px;cursor:pointer"> <img id="pwdsee"
					src="resources/backstage/login/image/隐藏.png">
				</i>
			</div>
			<div style="margin-top: 10px;text-align: center">
				<i id="logingInfo"
					style="font-style: normal; color: red; margin-top: 20px;visibility: hidden">用户名或密码错误</i>
			</div>
			<div
				style="margin-top: 15px;height: 58px;width: 384px;margin-left: 25px;">
				<button id="loginBtn"
					style="width: 100%;height: 100%;font-size: 22px"
					class="layui-btn layui-btn-normal">立&nbsp;即&nbsp;登&nbsp;录</button>
			</div>
		</div>
		</div>
		<div id="corpyright"
			style="margin-top: 150px;width: 100%;height: 50px">
			<div
				style="width: 100%;text-align: center;height: 15px;font-size: 14px;font-family:Microsoft YaHei;color: #999999">
				@2017-2020 海克斯科技有限公司 版权所有</div>
			<div
				style="margin-top: 10px;width: 100%;text-align: center;height: 15px;font-size: 14px;font-family:Microsoft YaHei;color: #999999">
				电话：028-86119898 传真：028-88888888 www.haikesi.com</div>
		</div>
	</div>
	<script>
		$(document).ready(function() {
			window.document.status = 0;
		});
		layui.use('layer', function() {
			var layer = layui.layer;
			var logingInfo = $('#logingInfo');
			var loginBtn = $('#loginBtn');
			init();
			function init() {
				init_loginBtn();
				init_keybrod();
			}
	
			function init_keybrod() {
				$(document).keyup(function(event) {
					if (event.keyCode == 13) {
						login();
					}
				});
			}
	
			function init_loginBtn() {
				loginBtn.on('click', function() {
					loading = layer.load(1, {
						shade : [ 0.1, '#fff' ]
					});
					login();
				})
			}
			function login() {
				loading = layer.load(1, {
					shade : [ 0.1, '#fff' ]
				});
				logingInfo.css('visibility', 'hidden');
				var username = $('#username').val();
				var pwd = $('#password').val();
				if (username == null || username == '' || pwd == null || pwd == '') {
					layer.msg('用户名和密码不能为空', {
						icon : '&#x1006;',
						anim : 6
					});
					layer.close(loading);
					return;
				}
				var salt = null;
				$.ajax({
					url : 'loging',
					method : 'POST',
					dataType : 'json',
					async : false,
					data : {
						username : username,
						step : true
					},
					success : function(event) {
						if (event.status == 'success') {
							salt = event.token;
						} else {
							errorInfo({
								type : event.status
							});
							layer.close(loading);
						}
					},
					error : function(event) {
						errorInfo({
							type : event.error
						});
						layer.close(loading);
					}
				});
				pwd = hex_md5(pwd).toUpperCase();
				salt = hex_md5(salt).toUpperCase();
				pwd = hex_md5(pwd + salt).toUpperCase();
				$.ajax({
					url : 'loging',
					method : 'POST',
					dataType : 'json',
					data : {
						username : username,
						password : pwd,
						step : false
					},
					success : function(event) {
						layer.close(loading);
						if (event.loginStatus) {
							window.location.href = event.loginSuccessUrl;
						} else {
							layer.close(loading);
							errorInfo({
								type : event.error
							});
						}
					},
					error : function() {
						layer.close(loading);
						errorInfo({
							type : event.error
						});
					}
				})
			}
			function errorInfo(error) {
				var info = "";
				if (error.type == 'loginLimit') {
					info = '登陆次数超过限制，请一小时后再试';
				} else if (error.type == '500') {
					info = '服务器错误，请稍后再试';
				} else if (error.type == 'userIsLock') {
					info = '用户已锁定，请联系管理员';
				} else if (error.type == 'notFountUser') {
					info = '用户名或密码错误';
				} else if (error.type == 'userNotFind') {
					info = '用户名或密码错误';
				}
				console.info(info);
				logingInfo.css('visibility', 'visible');
				logingInfo.html(info);
			}
		});
	
	
		function changepwd() {
			var pwdsee = $('#pwdsee');
			var password = $('#password');
			if (window.document.status == 0) {
				pwdsee.attr('src', 'resources/backstage/login/image/显示.png');
				password.attr('type', 'text');
				window.document.status = 1;
				return;
			}
			if (window.document.status == 1) {
				pwdsee.attr('src', 'resources/backstage/login/image/隐藏.png');
				password.attr('type', 'password');
				window.document.status = 0;
				return;
			}
		}
	
		function user_input_focus_brder() {
			var user_input = $('#user_input');
			user_input.css('border', 'solid 1px #000000');
		}
		function user_input_blur_brder() {
			var user_input = $('#user_input');
			user_input.css('border', 'solid 1px #cccccc');
		}
	
		function pwd_input_focus_brder() {
			var pwd_input = $('#pwd_input');
			pwd_input.css('border', 'solid 1px #000000');
		}
		function pwd_input_blur_brder() {
			var pwd_input = $('#pwd_input');
			pwd_input.css('border', 'solid 1px #cccccc');
		}
	</script>
</body>
</html>
