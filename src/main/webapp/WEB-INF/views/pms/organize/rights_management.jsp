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
    <title>权限管理</title>
    <script src="resources/layui/layui.js"></script>
    <link rel="stylesheet" href="resources/layui/css/layui.css">
    <script src="resources/jquery-3.2.1.min.js"></script>
    <script src="resources/systemManagement/organizeManagement/js/rights_management.js"></script>
    <link rel="stylesheet" href="resources/systemManagement/organizeManagement/css/rights_management.css">
</head>
<body>
<div class="layui-form" style="width: 98%;height: 100%;margin: 0px auto;font-size: 14px">
    <div id="div-bar" style="width: 100%;height: 50px;background-color: #F3F3F3;">
        <div class="layui-inline" style="margin-left: 10px;margin-top: 15px">当前位置:<label id="location">加载中...</label></div>
        <div class="layui-inline" style="float: right;margin-right: 20px;margin-top: 5px">
            <button id="cancelBtn" class="layui-btn layui-btn-primary layui-bg-blue"><i class="layui-icon">&#xe603;</i>返回</button>
            <button id="bushBtn" class="layui-btn layui-btn-primary layui-bg-blue"><i class="layui-icon">&#x1002;</i>刷新</button>
        </div>
    </div>
    <div style="width: 97%;height: 50px;margin-left: 27px;margin-top: 20px;background-color: #F9FAFC;border: solid 1px #E4E4E4">
        <div  style="margin-left: 10px;margin-top: 15px"><input id="index" name="check" lay-filter="choose" type="checkbox" lay-skin="primary" value="1" title="系统首页" ></div>
    </div>
    <div style="width: 97%;height: 50px;margin-left: 27px;margin-top: 30px;background-color: #F9FAFC;border: solid 1px #E4E4E4">
        <div    style="margin-left: 10px;margin-top: 15px" class="layui-inline"><input id="live" name="check"   type="checkbox" lay-skin="primary" value="1" title="庭审直播" checked></div>
        <div style="margin-left: 50px;margin-top: 15px" class="layui-inline"><input id="jishitong"   type="checkbox" lay-skin="primary" value="1" title="公诉人即时通" checked></div>
    </div>
    <div style="width: 97%;height: 50px;margin-left: 27px;margin-top: 30px;background-color: #F9FAFC;border: solid 1px #E4E4E4">
        <div   style="margin-left: 10px;margin-top: 15px" class="layui-inline"><input id="msg" lay-filter="msgchoose" type="checkbox" lay-skin="primary" value="1" title="庭审信息管理" checked></div>
    </div>
    <div  style="width: 98%;height: 50px;margin-left: 27px;border-: solid 1px #E4E4E4">
        <table class="msg" border="1px" style="border: solid 1px #E4E4E4">
            <tr style="height: 50px">
                <td style="width: 300px">
                    <div  style="margin-left: 10px;margin-top: 10px" class="layui-inline">
                        <input id="msgchage"  type="checkbox" name="checked" lay-skin="primary" value="1" title="庭审信息添加/编辑/查看" checked>
                    </div>
                </td>
                <td style="width: 227px">
                    <div  style="margin-left: 10px;margin-top: 10px" style="margin-left: 10px;margin-top: 10px" class="layui-inline">
                        <input id="count"  type="checkbox" lay-skin="primary" value="1" title="统计分析" checked>
                    </div>
                </td>
                <td style="width: 44%">
                    <div  style="margin-left: 10px;margin-top: 10px" style="margin-left: 10px;margin-top: 10px" class="layui-inline">
                        <input id="gread"  type="checkbox" lay-skin="primary" value="1" title="绩效考评添加/编辑/查看" checked>
                    </div>
                </td>
            </tr>
        </table>
    </div>
    <div style="width: 97%;height: 50px;margin-left: 27px;margin-top: 30px;background-color: #F9FAFC;border: solid 1px #E4E4E4">
        <div  style="margin-left: 10px;margin-top: 15px" class="layui-inline">
            <input id="sys"  type="checkbox" lay-skin="primary" value="1" title="系统管理" checked>
        </div>
    </div>
    <div style="width: 97%;height: 50px;margin-left: 27px;border-: solid 1px #E4E4E4">
        <table border="1px" style="border: solid 1px #E4E4E4">
            <tr style="height: 50px">
                <td style="width: 205px">
                    <div  style="margin-left: 10px;margin-top: 10px;width:194px; " class="layui-inline">
                        <input id="video"  type="checkbox" lay-skin="primary" value="1" title="视频通道添加/编辑/查看" checked>
                    </div>
                </td>
                <td style="width: 205px">
                    <div  style="margin-left: 10px;margin-top: 10px;width:194px " style="margin-left: 10px;margin-top: 10px" class="layui-inline">
                        <input id="org"  type="checkbox" lay-skin="primary" value="1" title="组织机构添加/编辑/查看" checked>
                    </div>
                </td>
                <td style="width: 205px">
                    <div  style="margin-left: 10px;margin-top: 10px;width:194px " style="margin-left: 10px;margin-top: 10px" class="layui-inline">
                        <input id="user"  type="checkbox" lay-skin="primary" value="1" title="系统用户添加/编辑/查看" checked>
                    </div>
                </td>
                <td style="width: 150px">
                    <div  style="margin-left: 10px;margin-top: 10px;width:140px " style="margin-left: 10px;margin-top: 10px" class="layui-inline">
                        <input id="log"  type="checkbox" lay-skin="primary" value="1" title="系统操作日志" checked>
                    </div>
                </td>
                <td style="width: 200px">
                    <div  style="margin-left: 10px;margin-top: 10px" style="margin-left: 10px;margin-top: 10px" class="layui-inline">
                        <input id="sysname"  type="checkbox" lay-skin="primary" value="1" title="系统名称添加/编辑" checked>
                    </div>
                </td>
            </tr>
        </table>
    </div>
    <div style="width: 97%;height: 50px;margin-left: 27px;margin-top: 30px;background-color: #F9FAFC;border: solid 1px #E4E4E4">
        <div  style="margin-left: 45%;margin-top: 15px" class="layui-inline">
            <input id="all" lay-filter="chooseAll" type="checkbox" lay-skin="primary" value="1" title="选择全部" checked>
        </div>
    </div>
    <div style="width: 97%;height: 50px;margin-left: 27px;margin-top: 30px;">
        <div  style="margin-left: 45%;" class="layui-inline">
            <button id="saveBtn" class="layui-btn layui-btn-normal">保存</button>
        </div>
    </div>
</div>
</body>
</html>