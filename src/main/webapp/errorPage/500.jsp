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
<title>500</title>
</head>

<body>
<center>
<table width="57%" height="299" border="0">
  <tr>
    <td width="27%" rowspan="2" align="center"><img src="errorPage/image/500.jpg" width="149" height="147" alt="图片找不到啦！" /></td>
    <td width="73%" height="98" valign="bottom" id=1 ><font size="+2" color="#990000">服务器瘫痪啦！技术猿抢修中...</font></td>
  </tr>
  <tr>
    <td height="40" id=2><font color="#0099FF"><p>很抱歉，你来晚了一步，它已经不在了...<br />
    </p></font></td>
  </tr>
</table>
</center>
</body>
</html>
