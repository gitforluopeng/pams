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
    <title>修改</title>
    <script src="resources/layui/layui.js"></script>
    <link rel="stylesheet" href="resources/layui/css/layui.css">
    <script src="resources/jquery-3.2.1.min.js"></script>
</head>
<body>
<div id="div-all" class="layui-form" lay-filter="selectdiv">
    <div id="div-form">
        <div style="margin-top: 10px;margin-left: auto;width: 100%;" class="layui-inline">
            <div style="margin-top: 25px;width: 100%;" class="layui-inline">
                <div style="margin-left: 50px;height:30px;width:100px;" class="layui-inline"><span style="color: red">*&nbsp;</span>日期:
                </div>
                <div style="width: 250px;height:50px;margin-left: 10px" class="layui-inline">
                    <input id="payTime" class="layui-input">
                </div>
                <div style="margin-left: 20px;height:30px;width:100px;" class="layui-inline"><span style="color: red">*&nbsp;</span>应发工资:
                </div>
                <div style="width: 250px;height:50px;margin-left: 10px" class="layui-inline">
                    <input id="shouldPay" class="layui-input">
                </div>
            </div>
        </div>
        <div style="margin-top: 10px;margin-left: auto;width: 100%;" class="layui-inline">
            <div style="margin-top: 30px;width: 100%;" class="layui-inline">
                <div style="margin-left: 50px;height:30px;width:100px;" class="layui-inline"><font color="red">*&nbsp;</font>实际工资:
                </div>
                <div style="width: 250px;height:50px;margin-left: 10px" class="layui-inline">
            		<input id="realPay"class="layui-input" >
                </div>
            </div>
        </div>
        <div style="margin-top: 30px;margin-left: auto;width: 100%;" class="layui-inline">
        	<div style="margin-left: 50px;width:90px;" class="layui-inline"><font color="red">*&nbsp;</font><label style="width:60px">扣减项目:</label></div>
            <div style="width: 80%;height:40px;margin-left: 22px;" class="layui-inline">
            	<textarea id="deductions" style="height:140px" name="" required placeholder="请输入"
                          class="layui-textarea"></textarea>
            </div>
        </div>
    </div>
    <div id="div-btn" style="width:100%;height:70px;">
        <div style="margin-left: 40%;margin-top: 17%;">
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
		var pData = window.parent.document.data;
		window.parent.document.data=null;
		laydate.render({
			elem: '#payTime',
			type: 'month'
		})
		
		var payTime = $('#payTime');
		var shouldPay = $('#shouldPay');
		var realPay = $('#realPay');
		var deductions = $('#deductions');
		
		var saveBtn = $('#saveBtn');
		var closeBtn = $("#closeBtn");
		
		
		
		
		init();
		function init(){
			init_saveBtn();
			init_closeBtn();
		}
		
		
		function init_closeBtn(){
			closeBtn.on('click',function(){
				var index = parent.layer.getFrameIndex(window.name);
				parent.layer.close(index);
			})
		}

		
		
		function init_saveBtn(){
			saveBtn.on('click',function(){
				if(shouldPay.val() == "" || shouldPay.val() == null){layer.msg("应发工资不能为空！",{icon:2}); return;}
				if(realPay.val() == "" || realPay.val() == null){layer.msg("应发工资不能为空！",{icon:2}); return;}
				if(payTime.val() == "" || payTime.val() == null){layer.msg("日期不能为空！",{icon:2}); return;}
				if(deductions.val() == "" || deductions.val() == null){layer.msg("扣减项目不能为空！",{icon:2}); return;}
				$.ajax({
					type: "get",
					url: "salaryManage/add_salary",
					data: {
						"salary.userId" : pData.shiroUserId,
						"salary.userName" : pData.userName,
						"salary.personName" : pData.personName,
						"salary.unitId" : pData.unitId,
						"salary.unitName" : pData.unitName,
						"salary.deptId" : pData.deptId,
						"salary.deptName" : pData.deptName,
						"salary.shouldPay": shouldPay.val(),
						"salary.realPay": realPay.val(),
						"salary.deductions": deductions.val(),
						"salary.payTime": payTime.val()
					},
					success: function (data) {
						if(data.status == 1){
							var index = parent.layer.getFrameIndex(window.name);
							parent.layer.close(index);
							parent.layer.msg("保存成功！",{icon : 1});
							pData = null;
						} else if( data.status == -1){
							layer.msg("保存失败！请稍后再试。。。",{icon : 2});
						} else if(data.status == 0){
							layer.msg("保存失败！已有本月数据。",{icon : 2});
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