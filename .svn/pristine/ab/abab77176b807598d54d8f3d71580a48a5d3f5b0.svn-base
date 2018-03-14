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
    <title>用户考勤</title>
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
            <button id="addBtn" class="layui-btn layui-btn-danger">只看我的</button>
            <button id="backBtn" class="layui-btn layui-btn-danger" style="display:none;">返回</button>
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
<script type="text/html" id="attTime">
	{{# 
		var date = new Date(d.attendanceTime);
		date.Format = Date.prototype.Format;
		d.createTime = date.Format('yyyy-MM-dd');
	}}
	{{d.createTime}}
</script>
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
		
		var addBtn = $('#addBtn');
		var backBtn = $('#backBtn');
		
		init();
		function init(){
			init_addBtn();
			init_backBtn();
			init_table();
		} 
		
		function init_addBtn(){
			addBtn.on('click',function(){
				addBtn.css("display","none");
				backBtn.css("display","");
				table.reload("reload",{
					url: 'attendanceManage/load_myAttendances'
				})
			})
		}
		
		function init_backBtn(){
			backBtn.on('click',function(){
				addBtn.css("display","");
				backBtn.css("display","none");
				table.reload("reload",{
					url: 'attendanceManage/load_attendances'
				})
			})
		}
		
		function init_table(url){
			 table.render({
            id: 'reload',
            elem: '#table',
            height: 'full-100',
            url: 'attendanceManage/load_attendances',
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
                {field: 'remark', align:'center',title: '备注'}
            ]]
        })
		}
	})
</script>
</html>