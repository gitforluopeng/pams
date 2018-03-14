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
    <title>编辑培训记录</title>
    <script src="resources/layui/layui.js"></script>
    <link rel="stylesheet" href="resources/layui/css/layui.css">
    <script src="resources/jquery-3.2.1.min.js"></script>
</head>
<body>
<div class="layui-form" id="div-all">
    <div id="div-form">
        <div style="margin-top: 20px;width: 100%;" class="layui-inline">
            <div style="margin-left: 10%;width:100px;" class="layui-inline"> <span style="color: red">*&nbsp;</span>培训开始时间:</div>
            <div style="width: 350px;height:40px;margin-left: 18px" class="layui-inline">
                <input id="startTime" required="required" class="layui-input">
            </div>
        </div>
        <div style="margin-top: 25px;width: 100%;" class="layui-inline">
            <div style="margin-left: 10%;width:100px;" class="layui-inline"> <span style="color: red">*&nbsp;</span>培训结束时间:</div>
            <div style="width: 350px;height:40px;margin-left: 18px" class="layui-inline">
                <input id="endTime" required="required" class="layui-input" placeholder="请输入部门名称">
            </div>
        </div>
        <div style="margin-top: 25px;width: 100%;" class="layui-inline">
            <div style="margin-left: 10%;width:100px;" class="layui-inline"> <span style="color: red">*&nbsp;</span>培训内容:</div>
            <div style="width: 351px;height:40px;margin-left: 18px;" class="layui-inline">
                <textarea required="required" id="trainContent" style="height:140px" name="" required  placeholder="请输入内容" class="layui-textarea"></textarea>
            </div>
        </div>
    </div>
    <div id="div-btn" style="width: 600px;height: 58px;margin-top:20%;">
        <div class="layui-inline" style="margin-left:39%">
            <button id="saveBtn" class="layui-btn layui-btn-normal">保存</button>
            <button id="closeBtn" class="layui-btn layui-btn-primary">关闭</button>
        </div>
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
<script type="text/javascript">
	layui.use(['form','laydate'],function(){
		var form = layui.form;
		var laydate = layui.laydate;
		
		var saveBtn = $('#saveBtn');
		var closeBtn = $('#closeBtn');
		var startTime = $('#startTime');
		var endTime = $('#endTime');
		var trainContent = $('#trainContent');
		
		var id = null;
		
		init();
		function init(){
			init_data();
			init_saveBtn();
			init_closeBtn();
		}

		function init_data() {
			laydate.render({
				elem : '#startTime', //指定元素
				theme : 'grid',
				type : 'datetime'
			});
			laydate.render({
				elem : '#endTime', //指定元素
				theme : 'grid',
				type : 'datetime'
			});
			var data =window.parent.document.data;
			id = data.id;
			var date = new Date(data.startTime);
			date.Format = Date.prototype.Format;
			startTime.val(date.Format('yyyy-MM-dd hh:mm:ss'));
			date = new Date(data.endTime);
			endTime.val(date.Format('yyyy-MM-dd hh:mm:ss'));
			trainContent.val(data.trainContent);
		}
		
		function init_saveBtn(){
			saveBtn.on('click',function(){
				if(startTime.val() == null || startTime.val() =="" 
				|| endTime.val() == null || endTime.val() == "" || trainContent.val() == null || trainContent.val() == ""){
					layer.msg("数据不能为空！",{icon : 2});
					return;
				}
				$.ajax({
					type: "post",
					url: "trainingPlan/update_trainPlan",
					data: {
						"trainPlan.startTime" : startTime.val(),
						"trainPlan.endTime" : endTime.val(),
						"trainPlan.trainContent" : trainContent.val(),
						"trainPlan.id" : id
					},
					success: function (data) {
						if(data.status == 1){
							var index = parent.layer.getFrameIndex(window.name);
							parent.layer.close(index);
							parent.layer.msg("保存成功！",{icon : 1});
						} else if(data.status == -1){
							layer.msg("保存失败！请重试。。。",{icon : 2});
						}
					},
					error: function (){
						layer.msg("保存失败！请联系管理员",{icon : 2});
					}
				})
			})
		}
		
		function init_closeBtn(){
			closeBtn.on('click',function(){
				var index = parent.layer.getFrameIndex(window.name);
				parent.layer.close(index);
			})
		}
	})
</script>
</html>