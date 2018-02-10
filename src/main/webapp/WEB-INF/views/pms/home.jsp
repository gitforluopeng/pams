<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%
String path = request.getContextPath();
String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path+"/";
%>

<!DOCTYPE html>
<html style="height: 100%; overflow-x: hidden; overflow-y: hidden;">
  <head>
    <base href="<%=basePath%>">
    
    <title>主页</title>
    
	<link rel="stylesheet" href="resources/layui/css/layui.css"  media="all">
	<script type="text/javascript" src="resources/layui/layui.js"></script>
	<script type="text/javascript" src="resources/home/script/mainView.js"></script>
	
	 <style>
        .hover:hover{
            background-color: #017be3;
        }
        #check::-webkit-input-placeholder { 
            color: #a3e4ff;
        }
        #check:-moz-placeholder {
            color: #a3e4ff;
        }
        #check::-moz-placeholder {
            color: #a3e4ff;
        }
        #check:-ms-input-placeholder {
            color: #a3e4ff;
        }
    </style>
	
</head>
  
<body style="height: 100%;overflow-y: hidden;">
<div id="div-toubu" class="layui-header" style="width: 100%;height: 93px;background-color: #008cef">
    <div id="div-logo" class="layui-inline" style="margin-top: 12px;margin-left: 30px;">
        <img style="height: 60px" height="60" width="58.5" src="resources/images/mini.png">
    </div>
    <div id="div-fuzhu" class="layui-inline" style="margin-left: 10px;margin-top: 15px">
        <img style="" src="resources/images/辅助线2.png">
    </div>
    <div id="div-font" style="margin-top: 6px;margin-left: 15px; margin-right: 30px" class="layui-inline">
        <label style="color:white;font-size: 33px;font-weight: 600" >
          ${systemName}
        </label>
    </div>
    
    <div id="checkinput" class="layui-inline"
         style="position:absolute;top:25%;left:70%;background-color: #017be3;
            width: 300px;height: 50px;border-radius:40px">
        <input id="check" style="color: #a3e4ff;width: 230px;border:0px;margin-left: 20px;margin-top: 18px;background-color: #017be3;"
               placeholder="请输入案件名或公诉人名" type="text">
        <a id="checkBtn" style="cursor:pointer;">
            <img src="resources/images/搜索.png">
        </a>
    </div>
    <div style="float:right;margin-right:20px;margin-top: 1.8%;" class="layui-inline">
        <div class="layui-inline">
            <label id="user" role="${role}" userId="${user.id}" style="color: #ffffff" userName="${user.username}">${myUser.personName}</label>
        </div>
        <div class="layui-inline" style="margin-left: 30px">
            <img src="resources/images/辅助线.png">
        </div>
        <div class="layui-inline" id="logoutBtn" style="margin-left: 20px;cursor:pointer;">
            <a href="logout" onclick="return confirm('确认退出登录吗？')">
                <img src="resources/images/关闭.png">
            </a>
        </div>
    </div>
</div>
<div class="layui-side layui-bg-black" id="leftNav" style="margin-top:90px;background-color:#242e46 !important;width:224px;">
       <div class="layui-side-scroll"  style="background:#242e46">
           <ul class="layui-nav layui-nav-tree layui-inline" lay-filter="leftNav" id="leftNavBody" style="width:224px;background:#242e46">
              
           </ul>
       </div>
</div>
<div id="body" style="border: solid 1px silver; height: 100%; overflow-y: auto">
	<div id="tabContent" lay-filter="tabContent" class="layui-tab layui-tab-card" style="margin-left: 225px; margin-top: 0px; height: 95%" lay-allowclose="true">
	  <ul id="tabTitleContent" class="layui-tab-title">
	  </ul>
	  <div class="layui-tab-content" style="height: 90%; padding: 0;">
	  </div>
	</div>
</div>
<script type="text/html" id="navTpl">
	<div id="{id}" class="layui-inline"
         style="cursor:pointer;margin-left:20px;width: 114px;height: 93px;background-color: #008cef;">
        <div class="hover" style="width: 100%;height: 100%" >
            <img id="live-icon" src="{icon}" style="margin-top: 24px;margin-left: 45px">
            <div style="margin-top: 10px;text-align: center">
                <label style="color:white">
                    {text}
                </label>
            </div>
        </div>
    </div>
</script>
<script type="text/html" id="leftTemp">
	<li class="layui-nav-item layui-nav-itemed">
        <a href="javascript:;" style="font-size: 16px;"><img id="live-icon" src="{icon}" style="height: 13px; margin-top: -3px"> {menuName}</a>
        <dl class="layui-nav-child">
            
    	</dl>
    </li>
</script>
<script type="text/html" id="leftItemTemp">
	<dd><a href="javascript:;">{leftMenuName}</a></dd>
</script>
<script type="text/javascript" src="${pageContext.request.contextPath}/resources/layui/beginne/plugins/layui/jquery-1.7.2.js"></script>
<script type="text/javascript">
function parenttest(msg) {
	var tab = $('.layui-tab-item');
	$($(tab).find("iframe")).each(function(i,val){
		try {
			$(tab).find("iframe")[i].contentWindow.childtest(msg)
		} catch (e) {
			// TODO: handle exception
		}
	})
}
</script>
</body>
</html>