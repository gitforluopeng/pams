layui.use(['element','layer','form'], function(){
	
	var $ = layui.$;
	var layer = layui.layer;
	var form = layui.form;
	var element = layui.element;
	var navTpl = $('#navTpl').html();
	var body = $('#body');
	var tabContent = $('#tabContent');
	var iframe = '<iframe src="{url}" style="width: 100%;height: {height}px; border: 0"  id="iframe{id}"></iframe>';
	var leftTemp = $('#leftTemp').html();
	var leftItemTemp = $('#leftItemTemp').html();
	var leftINavObjCache = {};
	var pathName = window.document.location.pathname;
	pathName = pathName.substr(0, pathName.indexOf('/', 1));
	
	document.LayuiForm = form;
	document.Layui$ = $;
	var user = {
			id: $("#user").attr("userId"),
			nickName: $("#user").html(),
			role: $("#user").attr("role"),
			name: $('#user').attr("userName")
	};
	window.document.user = user;
	
	$(document).ready(function (){
		
		var ajaxParam = {
				url: 'load_menus',
				method: 'GET',
				success: update,
				error: error
		}
		
		var headNav = $('#div-font');
		var leftNav = $('#leftNav');
		leftNav.body = $('#leftNavBody');
		var tab = null;
		var container = $('#container');
		var curtain = $('#curtain');
		var leftNavChilds = [];
		var currentMenuId = null;
		var menusMap = {};
		var currentMenuName = '首页';
		var currentTabId = null;
		var homeMenu = null;
		var tinfoMenu=null;
		
		init();
		
		function init(){
			$.ajax(ajaxParam);
			init_tab();
			init_serchBtn();
		}
		
		function update(data){
			
			var menus = [];
			var index = 0;
			for(var key in data){
				var menu = data[key];
				menus[index++] = menu;
			}
			menus = menus.sort(function (a,b){
				return a.order > b.order;
			});
			
			for(var i = 0; i < menus.length; i++){
				if(menus[i].menuName == '首页'){
					menus[i].initView = home_init_view;
				}
				if(menus[i].menuName == '庭审直播'){
					menus[i].onClick = live_menu_click;
				}else{
					menus[i].onClick = menu_click;
					if(menus[i].menuName=='庭审信息管理'){
						tinfoMenu=menus[i];
					}
				}
				
				init_menu(menus[i]);
			}
			
			element.init();
			
			element.on('nav(leftNav)', function(elem){
				var menu = elem[0].menu;
				menu.onClick(menu);
			});
		}
		
		function menu_click(menu){
			
			if(menu.menuName == currentMenuName){
				return;
			}
			
			if(menu.menuName == '首页'){
				menu.body.show();
				leftNav.hide();
				tabContent.hide();
				currentMenuName = menu.menuName;
				return;
			}
			
			if(currentMenuName == '首页'){
				homeMenu.body.hide();
			}
			
			var leftNavObj = leftINavObjCache[menu.menuName];
			var menuChilds = menu.childMenuVo;
			
			if(leftNavObj == null && menuChilds != null){
				
				var mainMenu = leftTemp.replace("{menuName}", menu.menuName);
				mainMenu = $(mainMenu.replace('{icon}', menu.icon.attr('src')));
				leftNavObj = {menu:mainMenu, menuChild: []};
				
				mainMenu.addChildNav = function (menu){
					this.children(".layui-nav-child").append(menu);
				}
				
				for(var i = 0; i < menuChilds.length; i++){
					var menuChild = menuChilds[i];
					var leftNavChild = $(leftItemTemp.replace("{leftMenuName}", menuChild.menuName));
					leftNavChild[0].menu = menuChild;
					leftNavObj.menuChild.push(leftNavChild);
					mainMenu.addChildNav(leftNavChild);
					if(menuChild.onClick == null){
						menuChild.onClick = leftMenu_click;
					}
				}
				
				leftINavObjCache[menu.menuName] = leftNavObj;
			}
			
			leftNav.body.html("");
			if(leftNavObj != null && leftNavObj.menu != null){
				leftNav.body.append(leftNavObj.menu);
			}
			element.render('nav', 'leftNav');
			leftNav.show();
			tabContent.show();
			
			currentMenuName = menu.menuName;
		}
		
		function leftMenu_click(menu){
			tabContent.addTab(menu);
			element.tabChange("tabContent", menu.id);
			currentTabId = menu.id;
		}
		
		function live_menu_click(menu){
			
			var previous = null;
			
			$.ajax({
				url: 'caseinfo/caseInformation/queryCaseInformation',
				success: function (data){
					if(data != null && data.length != 0){
						var menuChilds = createMenu(data);
						menu.childMenuVo = menuChilds;
						menu_click(menu);
					}
				},
				error: function (error){
					layer.msg("加载资源出错，稍后再试");
				}
			})
			
			var menuName = menu.menuName;
			menu.menuName = " ";
			menu_click(menu);
			menu.menuName = menuName;
			leftNav.body.html("");
			
			function createMenu(data){
				var menus = [];
				for(var i = 0; i < data.length; i++){
					var menu = {};
					menu.menuName = data[i].caseName;
					menu.id = "case"+data[i].caseInformationId;
					menu.caseId = data[i].caseInformationId;
					menu.caseNum = data[i].caseNum;
					menus.push(menu);
					menu.onClick = casemMenu_click;
				}
				return menus;
			}
			
			function casemMenu_click(menu){
				
				window.document.caseInfo = {
					caseNum: menu.caseNum,
					id: menu.id,
					caseName: menu.menuName
				};
				var url = "";
				window.parent.document.caseId = menu.caseId;
				if(user.role == '指挥中心'){
					url = "caseinfo/caseInformation/liveStreamCaseInformation?caseInformationId="+menu.caseId;
				}else if(user.role == '公诉人' || user.role == '书记员'){
					window.parent.document.caseName = menu.menuName;
					url = "systemManagement/instant";
				}
				if(previous != null){
					tabContent.closeTab(previous);
					delete tabContent.tabIds[previous];
				}
				previous = menu.id;
				menu.url = url;
				leftMenu_click(menu);
			}
		}
		
		function home_init_view(menu){
			leftNav.hide();
			tabContent.hide();
			var content = iframe.replace('{url}', pathName+menu.url);
			content = content.replace('{height}', body.height());
			content = content.replace('{id}', menu.id);
			menu.body = $(content);
			menu.body.css('overflow-y','auto');
			homeMenu = menu;
			body.prepend(menu.body);
		}
		
		function init_menu(menu){
			
			var navMouesOver = {'nav44': 'resources/images/庭审直播－选中.png'
				,'nav45': 'resources/images/首页－选中.png'
				,'nav43' : 'resources/images/庭审信息管理－选中.png'
				,'nav41': 'resources/images/系统管理－选中.png'};

			var navMouesOut = {'nav44': 'resources/images/庭审直播－默认.png'
				,'nav45': 'resources/images/首页－默认.png'
				,'nav43' : 'resources/images/庭审信息管理－默认.png'
				,'nav41': 'resources/images/系统管理－默认.png'};
			
			init();
			
			function init(){
				init_view();
				init_effect();
				init_click();
			}
			
			function init_view(){
				
				var content = navTpl.replace('{id}', "nav"+menu.id);
				content = content.replace('{icon}', navMouesOut["nav"+menu.id]);
				content = content.replace('{text}',menu.menuName);
				menu.content = $(content);
				menu.content.css('overflow-y','hidden');
				menu.content.css('overflow-x','hidden');
				var menuIcon = menu.content.children('.hover').children('#live-icon');
				menu.icon = menuIcon;
				content.menuVo = menu;
				headNav.after(menu.content);
				if(menu.initView != null){
					menu.initView(menu);
				}
			}
			
			function init_effect(){
				var content = menu.content;
				content.mouseover('click', function (){
					menu.icon.attr('src', navMouesOver["nav"+menu.id]);
				});
				
				content.mouseout('click', function (){
					menu.icon.attr('src', navMouesOut["nav"+menu.id]);
				});
			}
			
			function init_click(){
				if(menu.onClick != null){
					menu.content.on('click', function (){
						menu.onClick(menu);
					});
				}
			}
			
		}

		function init_tab(){
			
			tabContent.tabIds = {};
			
			tabContent.init = function (){
				var me = this;
				var filter = 'tabDelete('+me.attr('lay-filter')+')';
				element.on(filter, function(data){
					var layid = $(this).parent().attr("lay-id");
					delete me.tabIds[layid];
				})
			}
			
			tabContent.init();
			
			tabContent.addTab = function(menu){
				if(this.tabIds[menu.id] != null){
					return;
				}
				var content = iframe.replace('{url}', menu.url);
				content = content.replace('{height}', this.height() - 50);
				content = content.replace('{id}', menu.id);
				element.tabAdd(this.attr('lay-filter'), {
					  title: menu.menuName
					  ,content: content
					  ,id: menu.id
				});
				this.tabIds[menu.id] = menu.menuName;
			}
			
			tabContent.closeTab = function (menuId){
				element.tabDelete(this.attr('lay-filter'),menuId);
			}
			
		}
		function init_serchBtn(){
			var searBtn = $('#checkBtn');
			searBtn.on('click',function(){
				var searData = $('#check').val();
				if(searData==""||searData==null||searData==undefined){
					return;
				}
				window.document.searData=searData;
				var menuChilds = tinfoMenu.childMenuVo;
				menu_click(tinfoMenu);
				for(var i=0;i<menuChilds.length;i++){
					if(menuChilds[i].menuName=='庭审信息管理'){
						if(tabContent.tabIds[menuChilds[i].id] != null){
							tabContent.closeTab(menuChilds[i].id);
							delete tabContent.tabIds[menuChilds[i].id];
						}
						if(menuChilds[i].baseUrl == null){
							menuChilds[i].baseUrl = menuChilds[i].url;
						}
						menuChilds[i].url = menuChilds[i].baseUrl + '?caseName='+searData;
						leftMenu_click(menuChilds[i]);
						break;
					}
				}
			});
		}
		function error(){
			
		}
	})

});