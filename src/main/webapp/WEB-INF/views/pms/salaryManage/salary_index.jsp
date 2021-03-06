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
    <title>工资管理</title>
    <script src="resources/layui/layui.js"></script>
    <link rel="stylesheet" href="resources/layui/css/layui.css">
    <script src="resources/jquery-3.2.1.min.js"></script>
    
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
            <button id="addBtn" class="layui-btn layui-btn-danger">历史工资明细</button>
        </div>
    </div>
    <div id="div-table" style="width: 98%;margin-left: 10px;margin-right: 10px">
        <table id="table" lay-filter="tabletool"></table>
    </div>
</div>
</body>
<script type="text/html" id="barDemo">
    <a style="color: #33cc78;cursor: pointer;margin-left:10px;" lay-event="start">发放工资</a>
    <a style="color: #33cc78;cursor: pointer;margin-left:10px;" lay-event="detail">工资详情</a>
</script>
<script type="text/javascript">
	layui.use(['form','table'],function(){
		var form = layui.form;
		var table = layui.table;
		
		var queryBtn = $('#queryBtn');
    	var resetBtn = $('#resetBtn');
    	var addBtn = $('#addBtn');
		
		init();
		function init(){
			init_addBtn();
			init_queryBtn();
        	init_resetBtn();
        	pageIndexLoad();
        	init_table();
       		init_toolsclick();
		}

		function init_toolsclick() {
			table.on('tool(tabletool)', function(obj) {
				var data = obj.data;
				var layEvent = obj.event;
				if (layEvent == "start") {
					window.parent.document.data = data;
					parent.layer.open({
						type : 2,
						title : '发放工资',
						shadeClose : false,
						shade : 0.8,
						area : [ '50%', '55%' ],
						content : 'salaryManage/publish_salary', //iframe的url
						end : function() {
							table.reload('reload');
						}
					})
				} else if (layEvent == "detail") {
					window.parent.document.data = data;
					parent.layer.open({
						type : 2,
						title : '详情',
						shadeClose : false,
						shade : 0.8,
						area : ['80%','90%'],
						content : 'salaryManage/show_salary', //iframe的url
						end : function() {
							table.reload('reload');
						}
					})
				}
			})
		}

		function init_addBtn(){
        addBtn.on('click',function(){
            parent.layer.open({
                type: 2,
                title: '历史工资详情',
                shadeClose: false,
                shade: 0.8,
                area: ['80%','90%'],
                content: 'salaryManage/show_salary',//iframe的url
                end: function () {
                    table.reload('reload');
                }
            })
        })
    }
		
		function init_resetBtn(){
       resetBtn.on('click',function(){
           $('#username').val('');
           var obj=$('#userDept');
           obj.val("0");
           var unitl = $('#userUntil');
           unitl.val("0");
           obj.empty();
           var opt = $('<option></option>');
           opt.attr("value",0);
           opt.html('请选择');
           obj.append(opt);
           form.render('select', 'selectdiv');
       })
    }

    function init_queryBtn(){
        queryBtn.on('click',function(){
        	var useruntilname = $('#userUntil').val();
            var userdeptname =  $('#userDept').val();
            var username = $('#username').val();
            table.reload('reload',{
                url: 'systemManagement/query_user_infos',
                page: {
                    curr: 1 //重新从第 1 页开始
                  },
                method:'POST',
                where: {'unitId' : useruntilname,
                        'deptId' : userdeptname,
                        'name' : username}
            })
        })
    }
		
		 function init_table() {
        table.render({
            id: 'reload',
            elem: '#table',
            height: 'full-280',
            url: 'systemManagement/load_user_infos',
            method: 'post',
			cellMinWidth : 50,
            page: true,
            limit: 10,
            limits: [10,15,20,25,30],
            cols: [[
                {field: 'personName',align:'center', title: '姓名'},
                {field: 'userNumber',align:'center', title: '用户编号'},
                {field: 'unitName', align:'center',title: '所属单位名称'},
                {field: 'deptName',align:'center', title: '所属部门名称'},
                {field: 'userEmail',align:'center', title: '邮箱地址'},
                {field: 'userPhone',align:'center', title: '联系电话'},
                {title: '操作', align:'center', field: 'right', toolbar: '#barDemo'}
            ]]

        })
    }
		
		 function pageIndexLoad()
    {
        $.ajax({
            type : "get",
            async : true,
            url : "systemManagement/load_all_units",
            success : function(data) {
            	if(data==""||data==null||data==undefined){
            		return;
            	}
                var obj = $('#userUntil');
                var units=data["units"];
                for(var i = 0;i < units.length; i++)
                {
                	var unitname=units[i].name;
                	var unitid=units[i].id;
                	var option = $('<option></option>');
                    option.attr("value",unitid);
                    option.html(unitname);
                    obj.append(option);
                }
                form.render('select', 'selectdiv');
            },
            error: function(){
                layer.msg('啊喔，数据载入出现异常了，请刷新页面试试',{icon:5});
            }
        });
        form.on('select(userUntil)',function (data){
            var until = data.value;
            var obj=$('#userDept');
            obj.html('');
            //var uType=$('#userType');
            //uType.html('');
            if(until==0){
            	return;
            }
            else{
            $.ajax({
                type : "get",
                async : true,
                url : "systemManagement/load_unit_depts",
                data:{unitId:until},
                success : function(data) {
                	var opt = $('<option></option>');
                	opt.attr("value",0);
                    opt.html('请选择');
                    obj.append(opt);
                	if(data==""||data==null||data==undefined){
                		return;
                	}
                    var depts=data["depts"];
                    for(var i = 0;i < depts.length; i++)
                    {
                    	var deptname=depts[i].name;
                    	var deptid=depts[i].id;
                    	var option = $('<option></option>');
                        option.attr("value",deptid);
                        option.html(deptname);
                        obj.append(option);
                    }
                    form.render('select', 'selectdiv');
                },
                error: function(){
                    layer.msg('啊喔，数据载入出现异常了，请刷新页面试试',{icon:5});
                }
            });
            }

        });
    }
	})
</script>
</html>