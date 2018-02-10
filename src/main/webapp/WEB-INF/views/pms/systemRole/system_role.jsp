<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%
String path = request.getContextPath();
String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path+"/";
%>

<!DOCTYPE html>
<html lang="en">
<head>
	<base href="<%=basePath%>">
    <meta charset="UTF-8">
    <title>角色管理</title>
    <script src="resources/layui/layui.js"></script>
    <link rel="stylesheet" href="resources/layui/css/layui.css">
    <script src="resources/jquery-3.2.1.min.js"></script>
    <script src="resources/systemManagement/systemRole/js/system_role.js"></script>
</head>
<body>
<div style="width:100%;height: 98%;border-bottom: solid 1px silver;margin: 0px auto;" class="layui-form">
    <div id="div-toolbar" style="width: 100%;height: 60px;background-color: #DFEFEF">
        <div class="layui-inline" style="margin-top: 10px;margin-left:20px;width: 700px">请输入:
            <div style="margin-left: 10px;width: 580px" class="layui-inline">
                <input id="check_username" placeholder="请输入角色名称 例:检察长" class="layui-input">
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
<script type="text/html" id="barmore">
    <a style="color: #33cc78;cursor: pointer" lay-event="seemore" >撤销</a>
    <a style="color: #33cc78;cursor: pointer" lay-event="sourceset">添加</a>
</script>
<script type="text/html" id="barCanc">
    <a style="color: #33cc78;cursor: pointer" lay-event="cancleitsource" >撤销资源权限</a>
</script>
<script type="text/html" id="barAdd">
    <a style="color: #33cc78;cursor: pointer" lay-event="additsource" >添加资源权限</a>
</script>
<script type="text/html" id="barDemo">
    <a style="color: #33cc78;cursor: pointer" lay-event="edit">编辑</a>
	<a style="color: #33cc78;cursor: pointer" lay-event="del">删除</a>
</script>
<script type="text/html" id="titleTpl">
{{#if(d.lockStatus == false){}}
<a id="{{d.id}}a" style="cursor: pointer" lay-event="use"><img id="{{d.id}}img" src="resources/images/启用.png"></a>
{{#}else{}}
<a id="{{d.id}}a" style="cursor: pointer" lay-event="unuse"><img id="{{d.id}}img" src="resources/images/不启用.png"></a>
{{#}}}
</script>
</html>