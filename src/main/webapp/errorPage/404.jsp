<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%
String path = request.getContextPath();
String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path+"/";
%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<base href="<%=basePath%>">
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
<title>404</title>
</head>

<body>
<center>
<table width="83%" height="299" border="0">
  <tr>
    <td width="34%" rowspan="2"><img src="errorPage/image/404.gif" width="500" height="318" alt="图片不见啦！" /></td>
    <td width="66%" height="98" valign="bottom" id=1 ><font size="+2" color="#990000">您访问的页面不知道跑到哪里去啦！</font></td>
  </tr>
  <tr>
    <td height="40" id=2><font color="#0099FF"><p>很抱歉，你来晚了一步，它已经不在了...<br />你要坚强些，继续发掘别的页面去吧</p></font></td>
  </tr>
</table>
</center>
</body>
</html>
