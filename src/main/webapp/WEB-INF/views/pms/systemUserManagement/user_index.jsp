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
    <title>系统用户管理</title>
    <script src="resources/layui/layui.js"></script>
    <link rel="stylesheet" href="resources/layui/css/layui.css">
    <link rel="stylesheet" href="resources/systemManagement/systemUserManagement/css/user_index.css">
    <script src="resources/jquery-3.2.1.min.js"></script>
    <script src="resources/systemManagement/systemUserManagement/js/user_index.js"></script>
    
</head>
<body>
<div style="width:100%;height: 98%;border-bottom: solid 1px silver;margin: 0px auto;" class="layui-form" lay-filter="selectdiv">
    <div id="div-toolbar" style="width: 100%;height: 60px;background-color: #DFEFEF">
        <div class="layui-inline" style="margin-top: 10px;margin-left:20px;">输入搜索:
            <div style="margin-left: 10px;" class="layui-inline">
                <input id="username" placeholder="请输入用户名/姓名" type="text" autocomplete="off" class="layui-input">
            </div>
        </div>
        <div class="layui-inline" style="margin-left: 10%;margin-top: 10px">单位名称:
            <div style="margin-left: 10px;" class="layui-inline">
                <select id="userUntil" lay-filter="userUntil" class="layui-select">
                    <option value="0">请选择</option>
                </select>
            </div>
        </div>
        <div class="layui-inline" style="margin-left: 5%;margin-top: 10px">部门名称:
            <div style="margin-left: 10px;" class="layui-inline">
                <select id="userDept" lay-filter="userDept" class="layui-select">
                <option value="0">请选择</option>
                </select>
            </div>
        </div>
        <div class="layui-inline" style="margin-left: 20px;margin-top: 10px">
            <button id="queryBtn" class="layui-btn layui-btn-normal">查询</button>
            <button id="resetBtn" class="layui-btn layui-btn-normal">重置</button>
        </div>
    </div>
    <div id="div-add-toolbar" style="width: 100%;height: 70px;background-color: #F3F3F3;">
        <div class="layui-inline" style="font-size: 18px;margin-top: 26px;margin-left: 20px">
        <div class="layui-inline"><img style="width:3px;height:15px;margin-bottom: 4px" src="resources/images/辅助线.png"></div>&nbsp;数据列表</div>
        <div class="layui-inline" style="float: right;margin-top: 15px;margin-right: 40px">
            <button id="addBtn" class="layui-btn layui-btn-danger">添加</button>
        </div>
    </div>
    <div id="div-table" style="width: 98%;margin-left: 10px;margin-right: 10px">
        <table id="table" lay-filter="tabletool"></table>
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
<script type="text/html" id="usercreattime">
	{{# 
		var date = new Date(d.createTime);
		date.Format = Date.prototype.Format;
		d.createTime = date.Format('yyyy-MM-dd hh:mm:ss');
	}}
	{{d.createTime}}
</script>
<script type="text/html" id="logintime">
	{{# 
		var date = new Date(d.lastLoginTime);
		date.Format = Date.prototype.Format;
		d.lastLoginTime = date.Format('yyyy-MM-dd hh:mm:ss');
	}}
	{{d.lastLoginTime}}
</script>
<script type="text/html" id="barDemo">
    <a style="color: #33cc78;cursor: pointer;margin-left:10px;" lay-event="del">删除</a>
    <a style="color: #33cc78;cursor: pointer;margin-left:10px;" lay-event="edit">编辑</a>
</script>
<script type="text/html" id="titleTpl">
{{#if(d.accountLockStatus == false){}}
<a id="{{d.shiroUserId}}a" style="cursor: pointer" lay-event="use"><img id="{{d.shiroUserId}}img" src="resources/images/启用.png"></a>
{{#}else{}}
<a id="{{d.shiroUserId}}a" style="cursor: pointer" lay-event="unuse"><img id="{{d.shiroUserId}}img" src="resources/images/不启用.png"></a>
{{#}}}
</script>
</html>