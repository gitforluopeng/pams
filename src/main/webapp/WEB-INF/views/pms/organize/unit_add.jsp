﻿<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%
String path = request.getContextPath();
String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path+"/";
%>

<!DOCTYPE html>
<html lang="en" style="overflow-x: hidden; overflow-y: hidden;">
<head>
	<base href="<%=basePath%>">
    <meta charset="UTF-8">
    <title>添加单位/部门</title>
    <script src="resources/layui/layui.js"></script>
    <link rel="stylesheet" href="resources/layui/css/layui.css">
    <script src="resources/jquery-3.2.1.min.js"></script>
    <script src="resources/systemManagement/organizeManagement/js/unit_add.js"></script>
    <link rel="stylesheet" href="resources/systemManagement/organizeManagement/css/unit_add.css">
</head>
<body>
<div class="layui-form" id="div-all" lay-filter="selectdiv">
    <div id="div-form">
        <div style="margin-top: 25px;width: 100%;" class="layui-inline">
            <div style="margin-left: 10%;width:70px;" class="layui-inline"> <span style="color: red">*&nbsp;</span>单位名称:</div>
            <div style="width: 350px;height:40px;margin-left: 18px" class="layui-inline">
                <input id="unitname" class="layui-input" placeholder="请输入单位名称">
            </div>
        </div>
        <div style="margin-top: 25px;width: 100%;" class="layui-inline">
            <div style="margin-left: 10%;width:70px;" class="layui-inline"> <span style="color: red">*&nbsp;</span>职能描述:</div>
            <div style="width: 351px;height:40px;margin-left: 18px;" class="layui-inline">
                <textarea id="remarks" style="height:140px" required  placeholder="请输入内容" class="layui-textarea"></textarea>
            </div>
        </div>
    </div>
    <div id="div-btn" style="width: 600px;height: 58px">
        <div class="layui-inline" style="margin-left:39%">
            <button id="saveBtn" class="layui-btn layui-btn-normal">保存</button>
            <button id="closeBtn" class="layui-btn layui-btn-primary">关闭</button>
        </div>
    </div>
</div>
</body>
</html>