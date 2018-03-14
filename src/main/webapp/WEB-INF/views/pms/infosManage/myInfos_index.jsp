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
    <title>奖惩用户界面</title>
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
            <button id="myBtn" class="layui-btn layui-btn-danger">只看我的</button>
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
<script type="text/html" id="publishTime">
	{{# 
		var date = new Date(d.createTime);
		date.Format = Date.prototype.Format;
		d.createTime = date.Format('yyyy-MM-dd');
	}}
	{{d.createTime}}
</script>
<script type="text/html" id="barDemo">
    <a style="color: #33cc78;cursor: pointer;margin-left:10px;" lay-event="detail">详情</a>
</script>
<script type="text/javascript">
	layui.use(['form','table'],function(){
    var form = layui.form;
    var table = layui.table;
    var myBtn = $('#myBtn');
    var backBtn = $('#backBtn');

    init();
    function init(){
        init_table();
        init_toolsclick();
        init_myBtn();
        init_backBtn();
		}
		
	function init_toolsclick(){
		 table.on('tool(tabletool)',function(obj){
		 	var data = obj.data;
            var layEvent = obj.event;
            if(layEvent == 'detail'){
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

    function init_myBtn(){
		myBtn.on('click',function(){
			table.reload("reload",{
				url: 'infosManagement/load_myInfos',
			})
			backBtn.css("display","");
			myBtn.css("display","none");
		})
    }
    
    function init_backBtn(){
    	backBtn.on('click',function(){
    		table.reload("reload",{
    			url: 'infosManagement/load_infos'
    		})
    		myBtn.css("display","");
    		backBtn.css("display","none");
    	})
    }

    function init_table() {
        table.render({
            id: 'reload',
            elem: '#table',
            height: 'full-280',
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
                {field: 'unitName', align:'center',title: '所属单位'},
                {field: 'deptName', align:'center',title: '所属部门'},
                {field: 'createTime',align:'center', title: '发布日期',templet:'#publishTime'},
                { title: '操作', align:'center', field: 'right', toolbar: '#barDemo'}
            ]]
        })
    }

})
</script>
</html>