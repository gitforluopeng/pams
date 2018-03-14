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
    <title>奖惩信息</title>
    <script src="resources/layui/layui.js"></script>
    <link rel="stylesheet" href="resources/layui/css/layui.css">
    <script src="resources/jquery-3.2.1.min.js"></script>
    
</head>
<body>
<div style="width:100%;height: 98%;border-bottom: solid 1px silver;margin: 0px auto;" class="layui-form" lay-filter="selectdiv">
    <div id="div-table" style="width: 98%;margin-left: 10px;margin-right: 10px">
        <table id="allTable" lay-filter="tabletool"></table>
         <table id="myTable" lay-filter="tabletool"></table>
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
<script type="text/html" id="publishTime">
	{{# 
		var date = new Date(d.createTime);
		date.Format = Date.prototype.Format;
		d.createTime = date.Format('yyyy-MM-dd');
	}}
	{{d.createTime}}
</script>
<script type="text/html" id="infosType">
	{{#
		var data1 = "奖励";
		var data2 = "处分";
		if(d.infoType == 1){
	}}
	{{data1}}
	{{#}else{}}
	{{data2}}
	{{#}}}
</script>
<script type="text/html" id="barDemo">
    <a style="color: #33cc78;cursor: pointer;margin-left:10px;" lay-event="detail">详情</a>
    <a style="color: #33cc78;cursor: pointer;margin-left:10px;" lay-event="del">删除</a>
</script>
<script type="text/javascript">
	layui.use(['form','table'],function(){
    var form = layui.form;
    var table = layui.table;

    init();
    function init(){
    	if (window.parent.document.data == null || window.parent.document.data == ""){
    		console.info("全部页面");
        	init_allTable();
    	} else {
    		console.info("我的页面");
    		console.info(window.parent.document.data.shiroUserId);
    		init_myTable();
    	}
        init_toolsclick();
    }

    function init_toolsclick(){
        table.on('tool(tabletool)',function(obj){
            var data = obj.data;
            var layEvent = obj.event;
            if(layEvent == "del"){
            	layer.confirm('确定删除吗?',function(index){
            			$.ajax({
            				type: "post",
            				url: "infosManagement/delete_infos",
            				data: {
            					"infos.id" : data.id
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
            		})
				} else if (layEvent == "detail") {
					layer.open({
						type : 1, //Page层类型
						area : [ '500px', '300px' ],
						title : '详情',
						shade : 0.6, //遮罩透明度
						anim : 1, //0-6的动画形式，-1不开启
						content : '<div style="padding:50px;">'+data.infoContent+'</div>'
					}) ;
				}
			})
		}
    
    function init_myTable(){
    	$('#allTable').css("display","none");
    	$('#myTable').css("display","");
    	table.render({
            id: 'reloadMy',
            elem: '#myTable',
            height: 'full-100',
            url: 'infosManagement/load_infos?infos.userId='+window.parent.document.data.shiroUserId,
            cellMinWidth : 50,
            method: 'get',
            page: true,
            limit: 10,
            limits: [10,15,20,25,30],
            cols: [[
                {field: 'infoType',align:'center', title: '信息类型',templet:'#infosType'},
                {field: 'infoContent',align:'center', title: '信息内容'},
                {field: 'personName', align:'center',title: '被奖惩人'},
                {field: 'createTime',align:'center', title: '发布日期',templet:'#publishTime'},
                {field: 'createUserName',align:'center', title: '发布账号'},
                { title: '操作', align:'center', field: 'right', toolbar: '#barDemo'}
            ]]
        })
        window.parent.document.data = null;
    }

    function init_allTable() {
    	$('#myTable').css("display","none");
    	$('#allTable').css("display","");
        table.render({
            id: 'reloadAll',
            elem: '#allTable',
            height: 'full-100',
            url: 'infosManagement/load_infos',
            cellMinWidth : 50,
            method: 'get',
            page: true,
            limit: 10,
            limits: [10,15,20,25,30],
            cols: [[
                {field: 'infoType',align:'center', title: '信息类型',templet:'#infosType'},
                {field: 'infoContent',align:'center', title: '信息内容'},
                {field: 'personName', align:'center',title: '被奖惩人'},
                {field: 'createTime',align:'center', title: '发布日期',templet:'#publishTime'},
                {field: 'createUserName',align:'center', title: '发布账号'},
                { title: '操作', align:'center', field: 'right', toolbar: '#barDemo'}
            ]]

        })
    }

})
</script>
</html>