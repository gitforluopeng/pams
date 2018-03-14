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
    <title>添加用户</title>
    <script src="resources/layui/layui.js"></script>
    <link rel="stylesheet" href="resources/layui/css/layui.css">
    <script src="resources/jquery-3.2.1.min.js"></script>
</head>
<body>
<div id="div-all" class="layui-form" lay-filter="selectdiv">
    <div id="div-form">
        <div style="margin-top: 10px;margin-left: auto;width: 100%;" class="layui-inline">
            <div style="margin-top: 25px;width: 100%;" class="layui-inline">
                <div style="margin-left: 50px;height:30px;width:100px;" class="layui-inline"><span style="color: red">*&nbsp;</span>受训单位:
                </div>
                <div style="width: 250px;height:50px;margin-left: 10px" class="layui-inline">
                    <select id="userUntil" lay-filter="userUntil" class="layui-select">
                        <option value="0">请选择</option>
                    </select>
                </div>
                <div style="margin-left: 20px;height:30px;width:100px;" class="layui-inline"><span style="color: red">*&nbsp;</span>受训部门:
                </div>
                <div style="width: 250px;height:50px;margin-left: 10px" class="layui-inline">
                    <select id="userDept" class="layui-select">
                        <option value="0">请选择</option>
                    </select>
                </div>
            </div>
        </div>
        <div style="margin-top: 10px;margin-left: auto;width: 100%;" class="layui-inline">
            <div style="margin-top: 30px;width: 100%;" class="layui-inline">
                <div style="margin-left: 50px;height:30px;width:100px;" class="layui-inline"><font color="red">*&nbsp;</font>开始时间:
                </div>
                <div style="width: 250px;height:50px;margin-left: 10px" class="layui-inline">
            		<input id="startTime" type="text"  class="layui-input">
                </div>
                <div style="margin-left: 20px;height:30px;width:100px;" class="layui-inline"><font color="red">*&nbsp;</font>结束时间:
                </div>
                <div style="width: 250px;height:50px;margin-left: 10px" class="layui-inline">
              		<input id="endTime" type="text" class="layui-input">
                </div>
            </div>
        </div>
        <div style="margin-top: 15px;width: 100%;" class="layui-inline">
            <div style="margin-left: 50px;width:100px;" class="layui-inline"><font color="red">*&nbsp;</font>培训内容:</div>
            <div style="width: 67%;height:40px;margin-left: 3px;" class="layui-inline">
                <textarea id="trainContent" style="height:140px" placeholder="请输入"
                          class="layui-textarea"></textarea>
            </div>
        </div>
    </div>
    <div id="div-btn" style="width:100%;height:70px;">
        <div style="margin-left: 40%;margin-top: 15%;">
            <button id="saveBtn" class="layui-btn layui-btn-normal">保存</button>
            <button id="closeBtn" class="layui-btn layui-btn-primary">关闭</button>
        </div>
    </div>
</div>
</body>
<script type="text/javascript">
	layui.use(['form','laydate'],function(){
		var form = layui.form;
		var laydate = layui.laydate;
		
		var userUntil = $('#userUntil');
		var userDept = $('#userDept');
		var startTime = $('#startTime');
		var endTime = $('#endTime');
		var trainContent = $('#trainContent');
		var saveBtn = $('#saveBtn');
		var closeBtn = $("#closeBtn");
		
		
		laydate.render({
			elem : '#startTime' //指定元素
			,theme: 'grid'
			,type: 'datetime'
		});
		laydate.render({
			elem : '#endTime' //指定元素
			,theme: 'grid'
			,type: 'datetime'
		});
		
		init();
		function init(){
			init_select();
			init_deptSelect();
			init_saveBtn();
			init_closeBtn();
		}
		
		function init_closeBtn(){
			closeBtn.on('click',function(){
				var index = parent.layer.getFrameIndex(window.name);
				parent.layer.close(index);
			})
		}

		function init_select() {
			$.ajax({
				type : "get",
				async : true,
				url : "systemManagement/load_all_units",
				success : function(data) {
					if (data == "" || data == null || data == undefined) {
						return;
					}
					var obj = $('#userUntil');
					obj.empty();
					var init_option = $('<option></option>');
					init_option.attr("value",0);
					init_option.html("请选择");
					obj.append(init_option);
					var units = data["units"];
					for (var i = 0; i < units.length; i++) {
						var unitname = units[i].name;
						var unitid = units[i].id;
						var option = $('<option></option>');
						option.attr("value", unitid);
						option.html(unitname);
						obj.append(option);
					}
					form.render('select', 'selectdiv');
				},
				error : function() {
					layer.msg('啊喔，数据载入出现异常了，请刷新页面试试', {
						icon : 5
					});
				}
			});
		}
		
		function init_deptSelect(){
			form.on('select(userUntil)', function(data) {
				console.info("监听");
				var until = data.value;
				var obj = $('#userDept');
				obj.html('');
				var uType = $('#userType');
				uType.empty();
				var opt = $('<option></option>');
				opt.attr("value", 0);
				opt.html('请选择');
				uType.append(opt);
				if (until == 0) {
					return;
				} else {
					$.ajax({
						type : "get",
						async : true,
						url : "systemManagement/load_unit_depts",
						data : {
							unitId : until
						},
						success : function(data) {
							var opt = $('<option></option>');
							opt.attr("value", 0);
							opt.html('请选择');
							obj.append(opt);
							if (data == "" || data == null || data == undefined) {
								return;
							}
							var depts = data["depts"];
							for (var i = 0; i < depts.length; i++) {
								var deptname = depts[i].name;
								var deptid = depts[i].id;
								var option = $('<option></option>');
								option.attr("value", deptid);
								option.html(deptname);
								obj.append(option);
							}
							form.render('select', 'selectdiv');
						},
						error : function() {
							layer.msg('啊喔，数据载入出现异常了，请刷新页面试试', {
								icon : 5
							});
						}
					});
				}

			});
		}
		
		function init_saveBtn(){
			saveBtn.on('click',function(){
				if(userUntil.val() == 0){
					layer.msg("请选择受训单位！",{icon : 2});
					return;
				}
				if(userDept.val() == 0){
					layer.msg("请选择受训部门！",{icon : 2});
					return;
				}
				if(startTime.val() == null || startTime.val() == ""){
					layer.msg("请选择开始时间！",{icon : 2});
					return;
				}
				if(endTime.val() == null || endTime.val() == ""){
					layer.msg("请选择结束时间！",{icon : 2});
					return;
				}
				if(trainContent.val() == null || trainContent.val() == ""){
					layer.msg("请输入受训内容！",{icon : 2});
					return;
				}
				$.ajax({
					type: "post",
					url: "trainingPlan/add_trainPlan",
					data: {
						"trainPlan.trainContent" : trainContent.val(),
						"trainPlan.startTime" : startTime.val(),
						"trainPlan.endTime" : endTime.val(),
						"trainPlan.unitId" : userUntil.val(),
						"trainPlan.deptId" : userDept.val()
					},
					success: function (data) {
						if(data.status == 1){
							layer.msg("保存成功！",{icon : 1});
							trainContent.val();
							startTime.val();
							endTime.val();
							userUntil.empty();
							userDept.empty();
							init_select();
						} else if( data.status == -1){
							layer.msg("保存失败！请稍后再试。。。",{icon : 2});
						}
					},
					error: function(){
						layer.msg("保存失败！请联系管理员",{icon : 2});
					}
				})
			})
		}

	})
</script>
</html>