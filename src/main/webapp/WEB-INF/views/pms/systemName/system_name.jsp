﻿<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%
String path = request.getContextPath();
String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path+"/";
%>

<!DOCTYPE html>
<html lang="en">
<head>
	<base href="<%=basePath%>">
    <meta charset="UTF-8">
    <title>系统名称配置</title>
    <script src="resources/layui/layui.js"></script>
    <link rel="stylesheet" href="resources/layui/css/layui.css">
    <script src="resources/jquery-3.2.1.min.js"></script>
    <script src="resources/systemManagement/systemName/js/system_name.js"></script>
</head>
<body>
<div style="width:100%;height: 98%;border-bottom: solid 1px silver;margin: 0px auto;" class="layui-form">
    <div id="div-toolbar" style="width: 100%;height: 60px;background-color: #DFEFEF">
        <div class="layui-inline" style="margin-top: 10px;margin-left:20px;width: 700px">输入搜索:
            <div style="margin-left: 10px;width: 580px" class="layui-inline">
                <input id="check_username" placeholder="请输入内容 例:达州市达川区人民检察院" class="layui-input">
            </div>
        </div>
        <div class="layui-inline" style="margin-top: 10px">
        <button id="addBtn" class="layui-btn layui-btn-normal">新增</button>
    </div>
</div>
<div id="div-table" style="width: 98%;margin-left: 10px;margin-right: 10px">
    <table id="table" lay-filter="tool"></table>
</div>
</div>
</body>
<script type="text/javascript">
	Date.prototype.Format = function (fmt) { //author: meizz 
	    var o = {
	        "M+": this.getMonth() + 1, //月份 
	        "d+": this.getDate(), //日 
	        "h+": this.getHours(), //小时 
	        "m+": this.getMinutes(), //分 
	        "s+": this.getSeconds(), //秒 
	        "q+": Math.floor((this.getMonth() + 3) / 3), //季度 
	        "S": this.getMilliseconds() //毫秒 
	    };
	    if (/(y+)/.test(fmt)) fmt = fmt.replace(RegExp.$1, (this.getFullYear() + "").substr(4 - RegExp.$1.length));
	    for (var k in o)
	    if (new RegExp("(" + k + ")").test(fmt)) fmt = fmt.replace(RegExp.$1, (RegExp.$1.length == 1) ? (o[k]) : (("00" + o[k]).substr(("" + o[k]).length)));
	    return fmt;
	}
</script>
<script type="text/html" id="operationtime">
	{{# 
		var date = new Date(d.changeTime);
		date.Format = Date.prototype.Format;
		d.time = date.Format('yyyy-MM-dd hh:mm:ss');
	}}
	{{d.time}}
</script>
<script type="text/html" id="barDemo">
    <a style="color: #33cc78;cursor: pointer" lay-event="edit">编辑</a>
	<a style="color: #33cc78;cursor: pointer" lay-event="del">删除</a>
</script>
<script type="text/html" id="titleTpl">
{{#if(d.isUse == true){}}
<a id="{{d.id}}a" style="cursor: pointer" lay-event="use"><img id="{{d.id}}img" src="resources/images/启用.png"></a>
{{#}else{}}
<a id="{{d.id}}a" style="cursor: pointer" lay-event="unuse"><img id="{{d.id}}img" src="resources/images/不启用.png"></a>
{{#}}}
</script>
</html>