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
    <title>系统岗位信息管理</title>
    <script src="resources/layui/layui.js"></script>
    <link rel="stylesheet" href="resources/layui/css/layui.css">
    <script src="resources/jquery-3.2.1.min.js"></script>
</head>
<body>
<div style="width:100%;height: 98%;border-bottom: solid 1px silver;margin: 0px auto;" class="layui-form">
    <div id="div-toolbar" style="width: 100%;height: 60px;background-color: #DFEFEF">
        <div class="layui-inline" style="margin-top: 10px;margin-left:20px;width: 700px">输入搜索:
            <div style="margin-left: 10px;width: 580px" class="layui-inline">
                <input id="stationName" placeholder="请输入：岗位名称" class="layui-input">
            </div>
        </div>
        <div class="layui-inline" style="margin-top: 10px">
        <button id="addBtn" class="layui-btn layui-btn-normal">新增</button>
        <button id="queryBtn" class="layui-btn layui-btn-normal">查询</button>
    </div>
</div>
<div id="div-table" style="width: 98%;margin-left: 10px;margin-right: 10px">
    <table id="table" lay-filter="tool"></table>
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
<script type="text/html" id="titleTpl">
	{{# 
		var date = new Date(d.createTime);
		date.Format = Date.prototype.Format;
		d.time = date.Format('yyyy-MM-dd hh:mm:ss');
	}}
	{{d.time}}
</script>
<script type="text/html" id=barDemo>
    <a style="color: #33cc78;cursor: pointer" lay-event="edit">编辑</a>
	<a style="color: #33cc78;cursor: pointer" lay-event="del">删除</a>
</script>
<script type="text/javascript">
	layui.use(['form','table'],function(){
		var form = layui.form;
		var table = layui.table;
		
		var addBtn = $('#addBtn');
		var queryBtn = $('#queryBtn');
		var stationName = $('#stationName');
		var context = '<div class="layui-form">\n' +
    '    <div class="layui-inline">\n' +
    '        <div class="layui-inline" style="margin-left: 20px;margin-top: 30px;">\n' +
    '            岗位名称:\n' +
    '        </div>\n' +
    '        <div class="layui-inline">\n' +
    '            <input id="editStationName" class="layui-input" style="margin-top: 30px;margin-left:10px;width:300px;" placeholder="请输入内容...">\n' +
    '        </div>\n' +
    '    </div>\n' +
    '    <div class="layui-inline" style="margin-top: 30px;margin-left:20px">\n' +
    '        <button id="saveBtn" class="layui-btn layui-btn-normal">保存</button>\n' +
    '        <button id="closeBtn" class="layui-btn layui-btn-primary">关闭</button>\n' +
    '    </div>\n' +
    '</div>';
		
		init();
		function init(){
			init_addBtn();
			init_queryBtn();
			init_table();
			init_toolClick();
		}
		
		function init_addBtn(){
			addBtn.on('click',function(){
				if(stationName.val() == null || stationName.val() == ""){
					layer.msg("请输入岗位名称！",{icon : 2});
				} else {
					$.ajax({
						type: "post",
						url: "systemManagement/add_station",
						data: {
							"station.stationName" : stationName.val()
						},
						success: function(data){
							if(data.status == 1){
								layer.msg("操作成功！",{icon : 1});
								table.reload("reload");
							} else if(data.status == 0){
								layer.msg("操作失败!已存在该名称!",{icon:2});
							} else if(data.status == -1){
								layer.msg("操作失败！请稍后再试。。。",{icon : 2});
							}
						},
						error: function (){
							layer.msg("系统出错，请联系管理员。");
						}
					})
				}
			})
		}
		
		function init_queryBtn(){
			queryBtn.on('click',function(){
				table.reload("reload",{
					url: "systemManagement/load_stations",
					where: {
						"station.stationName" : stationName.val()
					},
					page : {
						curr : 1 //重新从第 1 页开始
					}
				})
			})
		}
		
		function init_toolClick(){
			table.on('tool(tool)',function(obj){
				var data = obj.data;
            	var layEvent = obj.event;
            	if(layEvent == "edit"){
            		window.parent.document.stationName = data.stationName;
            		window.parent.document.stationId = data.id;
            		var index=layer.open({
                    type: 1,
                    title: '编辑岗位名称',
                    shadeClose: false,
                    shade: 0.8,
                    area: ['40%', '20%'],
                    content: context,
                    success: function(){
                    	$('#editStationName').val(window.parent.document.stationName);
                    	var editSaveBtn = $('#saveBtn');
                    	var editCloseBtn = $('#closeBtn');
                    	console.info(window.parent.document.stationName);
                    	var uneditname=window.parent.document.stationName;
                    	editSaveBtn.on('click',function(){
                    		var editStationName = $('#editStationName').val();
                    		var editId=window.parent.document.stationId;
                    		if(window.document.stationName==editStationName){
                            	layer.msg('您没有修改任何信息，操作被拒绝了喔',{icon:2});
                            	return;
                            }
                            if(editStationName == null || editStationName == ''||editStationName==undefined){
                                layer.msg('系统名称不能为空!',{icon:2});
                                return;
                            }
                            else {
                            	$.ajax({
                            		type: "post",
                            		url: "systemManagement/update_station",
                            		data:{
                            			"station.id" : editId,
                            			"station.stationName" : editStationName
                            		},
                            		success: function(data){
                            			if(data.status == 1){
                            				layer.msg("操作成功！",{icon : 1});
                            			} else if(data.status == 0){
                            				layer.msg("操作失败！该岗位名称已存在。",{icon : 2});
                            			} else if(data.status == -1){
                            				layer.msg("操作失败！请稍后再试。。。",{icon : 2});
                            			}
                            		},
                            		errpr: function (){
                            			layer.msg("操作失败！请联系管理员。",{icon : 2});
                            		}
                            	})
                            }
                    	});
                    	editCloseBtn.on('click',function(){
                    		layer.close(index);
                            index=null;
                    	});
                    },
                    end: function () {
                        table.reload('reload');
                    }
                })
            	} else if(layEvent == "del"){
            		layer.confirm('确定删除吗?',function(index){
            			$.ajax({
            				type: "post",
            				url: "systemManagement/delete_station",
            				data: {
            					"station.id" : data.id
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
            	}
			})
		}

		function init_table() {
			table.render({
				id: 'reload',
				elem : '#table',
				height : 'full-200',
				method : 'post',
				url : 'systemManagement/load_stations',
				cellMinWidth : 50,
				page : true,
				limit : 10,
				limits : [ 10, 15, 20, 25, 30 ],
				cols: [[
                {field: 'id', title: '编号',align:'center'},
                {field: 'stationName', title: '岗位名称',align:'center'},
                {field: 'createUserName', title: '创建用户名',align:'center'},
                {field: 'createPersonName', title: '创建人姓名', align:'center'},
                {field: 'createTime', title: '创建时间', align:'center', templet: '#titleTpl'},
                {fixed: 'right', title: '操作', align:'center', toolbar: '#barDemo'}
            	]]
			})
		}
	})
</script>