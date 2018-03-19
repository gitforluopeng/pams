<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%
String path = request.getContextPath();
String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path+"/";
%>
<!DOCTYPE html>
<html>
<head>

	<base href="<%=basePath%>">
    <meta charset="UTF-8">
    <link rel="stylesheet" href="resources/layui/css/layui.css" media="all">
    <script type="text/javascript" src="resources/echarts.min.js"></script>
	<script type="text/javascript" src="resources/layui/layui.js"></script>
	<script type="text/javascript" src="resources/jquery-3.2.1.min.js"></script>
	<style type="text/css">
		html,body{
			margin: 0px;
			height: 100%;
			overflow-y: hidden;
		}
	</style>
</head>
<body style="background-color: #e8fff5">
  <div style="width: 100%;height: 100%;" class="layui-form">
  	<div class="layui-inline" style="width: 48%;height: 100%; border-right: 1px solid;">
  		<div style="width: 100%;height: 72%;margin-top: 8%;">
  			<div id="pie" style="width: 100%;height: 100%;">
  			</div>
  		</div>
  	</div>
  	<div class="layui-inline" style="width: 48%;height: 100%">
  			<div style="width: 100%;height: 6%;margin-top: 2%;margin-left: 10%">
  				<span class="layui-inline">开始时间：</span>
  				<div class="layui-inline">
  					<input class="layui-input" id="startTime">
  				</div>
  				<span class="layui-inline">结束时间：</span>
  				<div class="layui-inline">
  					<input class="layui-input" id="endTime">
  				</div>
  				<div class="layui-inline" style="margin-left: 10%;">
  					<button class="layui-btn layui-btn-normal" id="queryBtn">统计分析</button>
  				</div>
  			</div>
  			<div id="bar"  style="width: 100%;height: 70%;"> </div>
  	</div>
  	
 
  </div>
  
</body>
<script type="text/javascript">
	layui.use(['laydate','form'],function(){
		var form = layui.form;
		var laydate = layui.laydate;
		
		laydate.render({
			elem: "#startTime"
		})
		laydate.render({
			elem: "#endTime"
		})
		
		var pie = document.getElementById("pie");
		var bar = document.getElementById("bar");
		var pieChart = echarts.init(pie);
		var barChart = echarts.init(bar);
		var queryBtn = $("#queryBtn");
		
		var optionPie = null;
		var optionBar = null;
		optionPie = {
			title : {
				text : '员工学历分析统计图',
				x : 'center'
			},
			tooltip : {
				trigger : 'item',
				formatter : "{a} <br/>{b} : {c} ({d}%)"
			},
			legend : {
				orient : 'vertical',
				left : 'left',
				data : ""
			},
			series : [
				{
					name : '学历类型',
					type : 'pie',
					radius : '55%',
					center : [ '50%', '60%' ],
					data : "",
					itemStyle : {
						emphasis : {
							shadowBlur : 10,
							shadowOffsetX : 0,
							shadowColor : 'rgba(0, 0, 0, 0.5)'
						}
					}
				}
			]
		};

		barOptioin = {
			title : {
				text : '员工考勤分析统计图',
				x : 'center'
			},
			xAxis : {
				type : 'category',
				data : ""
			},
			yAxis : {
				type : 'value'
			},
			series : [ {
				data : "",
				type : 'bar'
			} ]
		};
		init();
		function init() {
			var startTime;
			var endTime;
			init_data(startTime,endTime);
			init_queryBtn();
		}
		
		function init_queryBtn(){
			queryBtn.on('click',function(){
				var startTime = $('#startTime').val();
				var endTime = $('#endTime').val();
				init_data(startTime,endTime);
			})
		}
		
		function init_data(startTime,endTime) {
			$.ajax({
				type: 'get',
				url: 'analyze/load_eduCountEcharData',
				success: function(data) {
					console.info(data);
					init_pieChart(data);
					$.ajax({
						type: "get",
						url: 'analyze/load_attCountEcharData',
						data: {
							"queStartTime": startTime,
							"queEndTime": endTime
						},
						success: function (data){
							init_barChart(data.name,data.value);
						}
					})
				},
				error: function () {
					layer.msg("加载数据失败！请刷新页面。。。",{icon : 2});
				}
			})
		}
		
		function init_pieChart(data) {
			optionPie.series[0].data = data;
			var title = [];
			for(var i = 0; i < data.length; i++){
				title.push(data[i].name);
			}
			optionPie.legend.data = title;
			pieChart.setOption(optionPie);
		}
		
		function init_barChart(name,value){
			var names = [];
			for(var i = 0; i < name.length; i++){
				if(name[i] == 1){
					names.push("已到岗");
				}
				if(name[i] == 2){
					names.push("未到岗");
				}
				if(name[i] == 3){
					names.push("已请假");
				}
				if(name[i] == 4){
					names.push("迟到");
				}
				if(name[i] == 5){
					names.push("早退");
				}
			}
			console.info(names);
			barOptioin.xAxis.data = names;
			barOptioin.series[0].data = value;
			barChart.setOption(barOptioin);
		}
	})
</script>
</html>