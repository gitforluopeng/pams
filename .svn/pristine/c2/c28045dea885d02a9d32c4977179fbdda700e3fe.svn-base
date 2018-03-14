<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%
String path = request.getContextPath();
String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path+"/";
%>

<!DOCTYPE html>
<html lang="en" style="overflow-x: hidden; overflow-y: hidden;">
<head>
	<base href="<%=basePath%>">
    <meta charset="UTF-8">
    <title>工资详情</title>
    <script src="resources/layui/layui.js"></script>
    <link rel="stylesheet" href="resources/layui/css/layui.css">
    <script src="resources/jquery-3.2.1.min.js"></script>
</head>
<body>
<div class="layui-form" id="div-all" lay-filter="selectdiv">
	<table id="table" lay-filter="tabletool"></table>
</div>
</body>
<script type="text/html" id="barDemo">
    <a style="color: #33cc78;cursor: pointer;margin-left:10px;" lay-event="edit">编辑</a>
    <a style="color: #33cc78;cursor: pointer;margin-left:10px;" lay-event="del">删除</a>
</script>
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
<script type="text/html" id="createTime">
	{{# 
		var date = new Date(d.createTime);
		date.Format = Date.prototype.Format;
		d.createTime = date.Format('yyyy-MM-dd');
	}}
	{{d.createTime}}
</script>
<script type="text/javascript">
	layui.use(['form','table'],function(){
		var form = layui.form;
		var table = layui.table;
		
		init();
		function init(){
			var url = '';
			if(window.parent.document.data == null || window.parent.document.data == ""){
				url = 'salaryManage/load_salaries'
				console.info("查看全部"+url);
			} else {
				url = 'salaryManage/load_salaries?salary.userId='+window.parent.document.data.shiroUserId;
				window.parent.document.data = null;
				console.info("查看我的"+url);
			}
			init_table(url);
			init_toolsclick();
		} 
		
		function init_toolsclick() {
			table.on('tool(tabletool)', function(obj) {
				var data = obj.data;
				var layEvent = obj.event;
				if (layEvent == "edit") {
					window.parent.document.data = data;
					parent.layer.open({
						type : 2,
						title : '编辑',
						shadeClose : false,
						shade : 0.8,
						area : [ '50%', '55%' ],
						content : 'salaryManage/salary_edit', //iframe的url
						end : function() {
							table.reload('reload');
						}
					})
				} else if (layEvent == "del") {
					window.parent.document.data = data;
					layer.confirm('确定删除吗?',function(index){
            			$.ajax({
            				type: "get",
            				url: "salaryManage/delete_salary",
            				data: {
            					"salary.id" : data.id
            				},
            				success: function (data){
            					if(data.status == 1){
            						layer.msg("操作成功！",{icon : 1});
            						obj.del();
            						
            					} else {
            						layer.msg("操作失败！请稍后再试。。。",{icon : 2});
            					}
            				},
            				error: function (){
            					layer.msg("操作失败！请联系管理员。。。",{icon : 2});
            				}
            			})
            			window.parent.document.data = null;
            		})
				}
			})
		}
		
		function init_table(url){
			 table.render({
            id: 'reload',
            elem: '#table',
            height: 'full-10',
            url: url,
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
                {field: 'deductions', align:'center',title: '扣减项目'},
                {field: 'createPersonName', align:'center',title: '创建人'},
                {field: 'createTime', align:'center',title: '创建时间',templet:'#createTime'},
                {title: '操作', align:'center', field: 'right', toolbar: '#barDemo'}
            ]]
        })
		}
	})
</script>
</html>