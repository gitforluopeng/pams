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
    <title>考勤信息</title>
    <script src="resources/layui/layui.js"></script>
    <link rel="stylesheet" href="resources/layui/css/layui.css">
    <script src="resources/jquery-3.2.1.min.js"></script>
</head>
<body>
<div class="layui-form" id="div-all" lay-filter="selectdiv">
	<table id="table" lay-filter="tabletool"></table>
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
<script type="text/html" id="attTime">
	{{# 
		var date = new Date(d.attendanceTime);
		date.Format = Date.prototype.Format;
		d.createTime = date.Format('yyyy-MM-dd');
	}}
	{{d.createTime}}
</script>
<script type="text/html" id="createTime">
	{{# 
		var date = new Date(d.createTime);
		date.Format = Date.prototype.Format;
		d.createTime = date.Format('yyyy-MM-dd');
	}}
	{{d.createTime}}
</script>
<script type="text/html" id="status">
	{{#var a1 = "已到岗";
		var a2 = "未到岗";
		var a3 = "已请假";
		var a4 = "迟到";
		var a5 = "早退";
		if(d.attendanceSituation == 1){}}
{{a1}}
{{#}else if(d.attendanceSituation == 2){}}
{{a2}}
{{#}else if(d.attendanceSituation == 3){}}
{{a3}}
{{#}else if(d.attendanceSituation == 4){}}
{{a4}}
{{#}else if(d.attendanceSituation == 5){}}
{{a5}}
{{#}}}
</script>
<script type="text/javascript">
	layui.use(['form','table'],function(){
		var form = layui.form;
		var table = layui.table;
		
		init();
		function init(){
			var url = '';
			if(window.parent.document.data == null || window.parent.document.data == "" || window.parent.document.data ==undefined){
				url = 'attendanceManage/load_attendances'
				console.info("查看全部"+url);
			} else {
				url = 'attendanceManage/load_attendances?attendance.userId='+window.parent.document.data.shiroUserId;
				window.parent.document.data = null;
				console.info("查看我的"+url);
			}
			init_table(url);
		} 
		
		function init_table(url){
			 table.render({
            id: 'reload',
            elem: '#table',
            height: 'full-100',
            url: url,
            cellMinWidth : 50,
            method: 'get',
            page: true,
            limit: 10,
            limits: [10,15,20,25,30],
            cols: [[
                {field: 'personName',align:'center', title: '姓名'},
                {field: 'attendanceTime',align:'center', title: '考勤时间',templet:'#attTime'},
                {field: 'attendanceSituation', align:'center',title: '是否到岗',templet:'#status'},
                {field: 'unitName', align:'center',title: '所属单位'},
                {field: 'deptName', align:'center',title: '所属部门'},
                {field: 'remark', align:'center',title: '备注'},
                {field: 'createPersonName', align:'center',title: '创建人'},
                {field: 'createTime', align:'center',title: '创建时间',templet:'#createTime'},
            ]]
        })
		}
	})
</script>
</html>