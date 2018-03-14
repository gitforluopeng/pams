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
    <title>用户工资</title>
    <script src="resources/layui/layui.js"></script>
    <link rel="stylesheet" href="resources/layui/css/layui.css">
    <script src="resources/jquery-3.2.1.min.js"></script>
    
</head>
<body>
<div style="width:100%;height: 98%;border-bottom: solid 1px silver;margin: 0px auto;" class="layui-form" lay-filter="selectdiv">
    <div id="div-toolbar" style="width: 100%;height: 60px;background-color: #DFEFEF"></div>
    <div id="div-add-toolbar" style="width: 100%;height: 70px;background-color: #F3F3F3;">
        <div class="layui-inline" style="font-size: 18px;margin-top: 26px;margin-left: 20px">
        <div class="layui-inline"><img style="width:3px;height:15px;margin-bottom: 4px" src="resources/images/辅助线.png"></div>&nbsp;数据列表</div>
        <div class="layui-inline" style="float: right;margin-top: 15px;margin-right: 40px">
        </div>
    </div>
    <div id="div-table" style="width: 98%;margin-left: 10px;margin-right: 10px">
        <table id="table" lay-filter="tabletool"></table>
    </div>
</div>
</body>
<script type="text/javascript">
	layui.use(['form','table'],function(){
		var form = layui.form;
		var table = layui.table;
		
		init();
		function init(){
			init_table();
		} 
		
		function init_table(){
			 table.render({
            id: 'reload',
            elem: '#table',
            height: 'full-280',
            url: 'salaryManage/load_mySalaries',
            cellMinWidth : 50,
            method: 'get',
            page: true,
            limit: 10,
            limits: [10,15,20,25,30],
            cols: [[
                {field: 'personName',align:'center', title: '姓名'},
                {field: 'unitName', align:'center',title: '所属单位'},
                {field: 'deptName', align:'center',title: '所属部门'},
                {field: 'payTime', align:'center',title: '日期'},
                {field: 'shouldPay', align:'center',title: '应发工资'},
                {field: 'realPay', align:'center',title: '实发工资'},
                {field: 'deductions', align:'center',title: '扣减项目'}
            ]]
        })
		}
	})
</script>
</html>