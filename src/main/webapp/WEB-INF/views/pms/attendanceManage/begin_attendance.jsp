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
    <title>开始考勤</title>
    <script src="resources/layui/layui.js"></script>
    <link rel="stylesheet" href="resources/layui/css/layui.css">
    <script src="resources/jquery-3.2.1.min.js"></script>
</head>
<body>
<div class="layui-form" id="div-all" lay-filter="selectdiv">
    <div id="div-form">
        <div style="margin-top: 25px;width: 100%;" class="layui-inline">
            <div style="margin-left: 10%;width:90px;" class="layui-inline"> <span style="color: red">*&nbsp;</span>考勤情况:</div>
            <div style="width: 350px;height:40px;margin-left: 18px" class="layui-inline">
                <select id="att" class="layui-select">
                	<option value="">请选择</option>
                	<option value="1">已到岗</option>
                	<option value="2">未到岗</option>
                	<option value="3">已请假</option>
                	<option value="4">迟到</option>
                	<option value="5">早退</option>
                </select>
            </div>
        </div>
        <div style="margin-top: 25px;width: 100%;" class="layui-inline">
            <div style="margin-left: 10%;width:90px;" class="layui-inline"> <span style="color: red">*&nbsp;</span>考勤时间:</div>
            <div style="width: 351px;height:40px;margin-left: 18px;" class="layui-inline">
            	<input id="attTime" class="layui-input" placeholder="请选择考勤时间">
            </div>
        </div>
        <div style="margin-top: 25px;width: 100%;" class="layui-inline">
            <div style="margin-left: 16%;width:90px;" class="layui-inline">备注:</div>
            <div style="width: 70%;height:40px;margin-left: -30px;" class="layui-inline">
            	<textarea id="remark" style="height:140px" name="" required placeholder="请输入"
                          class="layui-textarea"></textarea>
            </div>
        </div>
    </div>
    <div id="div-btn" style="width: 600px;height: 58px">
        <div class="layui-inline" style="margin-left:55%;margin-top:20%;">
            <button id="saveBtn" class="layui-btn layui-btn-normal">确定</button>
            <button id="closeBtn" class="layui-btn layui-btn-primary">取消</button>
        </div>
    </div>
</div>
</body>
<script type="text/javascript">
	layui.use(['form','laydate'],function(){
		var form = layui.form;
		var laydate = layui.laydate;
		
		var saveBtn = $('#saveBtn');
		var closeBtn = $('#closeBtn');
		var time = $('#attTime');
		var att = $('#att');
		var remark = $('#remark');
		var infosData = window.parent.document.data;
		window.parent.document.data=null;
		laydate.render({
			elem: '#attTime'
		})
		
		
		init();
		function init(){
			init_closeBtn();
			init_saveBtn();
		} 
		
		function init_closeBtn(){
			closeBtn.on('click',function(){
				var index = parent.layer.getFrameIndex(window.name);
				parent.layer.close(index);
			})
		}
		
		function init_saveBtn(){
			saveBtn.on('click',function(){
				if(att.val() == ""){
					layer.msg("请选择是否到岗！",{icon : 2});
					return;
				}
				if(time.val() == "" || time.val() == null){
					layer.msg("请选择考勤时间",{icon : 2});
					return;
				}
				$.ajax({
					type: 'get',
					url: 'attendanceManage/add_attendance',
					data: {
						"attendance.userId" : infosData.shiroUserId,
						"attendance.userName" : infosData.userName,
						"attendance.personName" : infosData.personName,
						"attendance.unitId" : infosData.unitId,
						"attendance.unitName" : infosData.unitName,
						"attendance.deptId" : infosData.deptId,
						"attendance.deptName" : infosData.deptName,
						"attendance.attendanceTime" : time.val(),
						"attendance.attendanceSituation" : att.val(),
						"attendance.remark" : remark.val()
					},
					success: function(data){
						if(data.status == 1){
							var index = parent.layer.getFrameIndex(window.name);
							parent.layer.close(index);
							parent.layer.msg("保存成功！",{icon : 1});
							infosData = null;
						} else if(data.status == -1){
							layer.msg("保存失败！请稍后再试。。。",{icon : 2});
						} else if(data.status == 0){
							layer.msg("保存失败！重复考勤！",{icon : 2});
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