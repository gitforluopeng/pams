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
    <link rel="stylesheet" href="resources/gsHome/css/css2.css" media="all">
    <script type="text/javascript" src="resources/echarts.min.js"></script>
	<script type="text/javascript" src="resources/layui/layui.js"></script>
	<script type="text/javascript" src="resources/gsHome/script/home_js.js"></script>

</head>
<style>
	.layui-col-lg12 .layui-table th{
		background-color: #f9fbfc !important;
	}
	.laytable-cell-2-title_of_a_cause span{
		font-size: 19px;
	}
	.layui-table-cell span{
		font-size: 19px;
	}
</style>
<body style="background-color: #e8fff5">
    <div class="layui-container" style="margin-top: 30px">
        <div class="layui-row layui-col-space0">
            <div class="layui-col-lg7">
                <div class="layui-row">
                    <div class="layui-col-lg6 img_1">
                        <div class="img_1_number">
                            <div><span class="number" id="overCaseSumLable">0</span></div>
                            <div><span class="state">已审结</span></div>
                        </div>
                    </div>
                    <div class="layui-col-lg6 img_2">
                        <div class="img_1_number">
                            <div><span class="number" id="onCaseSumLable">0</span></div>
                            <div><span class="state">未审结</span></div>
                        </div>
                    </div>
                </div>
                <div class="layui-row">
                    <div class="layui-form-item select_year">
                        <div class="layui-input-inline layui-form">
                            <div class="layui-inline">
                                <input type="text" class="layui-input" id="home_year">
                            </div>
                        </div>
                    </div>
                    <div class="layui-col-lg6 upperPart_left_down" id="bar_Chart" ></div>
                </div>
            </div>
            <div class="layui-col-lg5 ">
                <div class="layui-row per_Ass_table_parent">
                    <div class="layui-col-lg12">
                        <div  class="img_3">公诉人绩效考核排名</div>
                    </div>
                </div>
                <div class="layui-row per_Ass_table_parent">
             		<table id="per_Ass_table" class="per_Ass_table"></table>
                </div>
            </div>
        </div>
        <div class="layui-row case_table_parent">
            <div class="layui-col-lg12" >
                <table id="case_table" class=" layui-body" lay-filter="caseTable"></table>
            </div>
        </div>
    </div>
    <script type="text/html" id="view">
        <a lay-event="detail" data-id="{{d.caseInformationId}}" class="examine">查看</a>
    </script>

    <script type="text/html" id="titleTpl">
        {{#
			switch(d.state){
        	case 0:
        		d.caseStatus = '<lable style="color: #33cc78">未开庭</lable>';
				break;
			case 1:
        		d.caseStatus = '<lable style="color: #33cc78">休庭中</lable>';
				break;
			case 2:
        		d.caseStatus = '<lable style="color: #33cc78">庭审中</lable>';
				break;
			case 3:
        		d.caseStatus = '<lable style="color: #33cc78">已审结</lable>';
				break;
        	}
        }}

        {{ d.caseStatus }}
    </script>
	<div id="corpyright" style="margin-top: 15px;width: 100%;height: 50px">
	    <div style="width: 100%;text-align: center;height: 15px;font-size: 14px;font-family:Microsoft YaHei;color: #999999">
	        @2017-2020 四川众合悦盛科技有限公司  版权所有
	    </div>
	    <div style="margin-top: 10px;width: 100%;text-align: center;height: 15px;font-size: 14px;font-family:Microsoft YaHei;color: #999999">
	        电话：028-85005467 传真：028-85005467  www.zhongheys.com
	    </div>
	</div>
<script type="text/html" id="courtAddress">
	{{#
	d.courtAddress = d.unitName + d.unitNum;
}}
{{d.courtAddress}}
</script>
</body>
</html>