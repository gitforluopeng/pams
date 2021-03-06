layui.use(['element'], function(){
	var element = layui.element;
	$(document).ready(function (){
		
		var ajaxParam = {
				url: 'backstage/load_menus',
				method: 'GET',
				success: update,
				error: error
		}
		
		var headNav = $('#headNav');
		var leftNav = $('#leftNav');
		var tab = null;
		var container = $('#container');
		var curtain = $('#curtain');
		var leftNavChilds = [];
		var currentMenuId = null;
		var menusMap = {};
		
		init();
		
		function init(){
			$.ajax(ajaxParam);
			init_tab();
		}
		
		function update(data){
			var menus = [];
			var index = 0;
			for(var key in data){
				var menu = data[key];
				menus[index++] = menu;
			}
			menus = menus.sort(function (a,b){
				return a.order - b.order;
			});
			for(var i = 0; i < menus.length; i++){
				var li = createMenuDom(menus[i]);
				if(i == 0){
					var lis = leftNavChilds[menus[i].id];
					currentMenuId = menus[i].id;
					for(var j = 0; j < lis.length; j++){
						leftNav.append(lis[j]);
					}
				}
				menusMap[menus[i].id] = menus[i];
			}
			element.init();
		}
		
		function createMenuDom(menu){
			var li = $(document.createElement("li"));
			var clazz = "layui-nav-item";
			li.attr('class',clazz);
			var a = $(document.createElement("a"));
			a.attr('href','javascript:void(0);');
			a.append(menu.menuName);
			a.attr('id',menu.id);
			li.append(a);
			headMenuClick(li);
			headNav.append(li);
			var liMenus = createMenuChildMenu(menu);
			leftNavChilds[menu.id] = liMenus;
			return li;
		}
		
		function createMenuChildMenu(menu){
			
			var liMenus = [];
			var menuChilds = menu.childMenuVo;
			if(menuChilds == null || menuChilds.length == 0)return liMenus;
			for(var i = 0; i < menuChilds.length; i++){
				var li = createMenu(menuChilds[i]);
				liMenus[i] = li;
				menusMap[menuChilds[i].id] = menuChilds[i];
			}
			
			return liMenus;
			
			function createMenu(menu){
				var li = $(document.createElement("li"));
				var clazz = "layui-nav-item";
				li.attr('class',clazz);
				var a = $(document.createElement("a"));
				a.attr('href','javascript:void(0);');
				a.attr('id',menu.id);
				a.append(menu.menuName);
				li.append(a);
				leftMenuClick(li);
				return li;
			}
			
		}
		
		function init_tab(){
			
			tab = $('#tabContext');
			
			var tabId = 'mainContext';
		
			var pathName = window.document.location.pathname;
			pathName = pathName.substr(0, pathName.indexOf('/', 1));
			tab.tabChange = element.tabChange;
			var height = $('#container').height();
			height = height
			function addTab(menu){
				console.info(menu);
				curtain.css('visibility','visible');
				element.tabAdd(tabId, {
					title: menu.menuName,
					content: '<iframe src="'+pathName+menu.url+'" style="width: 100%;height:'+height+'px" scrolling="no" id="iframe'+menu.id+'"></iframe>',
					id:menu.id
				});
				element.tabChange(tabId, menu.id);
				$('#iframe'+menu.id).on('load',function (){
					curtain.css('visibility', 'hidden');
				});
			}
			
			tab.contextId = tabId;
			tab.addTab = addTab;
		}
		
		function headMenuClick(li){
			li.on('click', function (event){
				
				var id = $(event.target).attr('id');
				
				if(id != currentMenuId && leftNavChilds[id] != null){
					leftNav.empty();
					currentMenuId = id;
					var menus = leftNavChilds[id];
					for(var i = 0; i < menus.length; i++){
						leftNav.append(menus[i]);
						leftMenuClick(menus[i]);
					}
				}
			})
		}
		
		function leftMenuClick(li){
			li.on('click', function (event){
				var isCreate = false;
				var id = $(event.target).attr('id');
				var tabs = $('#tabTitles').children();
				for(var i = 0; i < tabs.length; i++){
					var dom = $(tabs[i]);
					if(dom.attr('lay-id') == id) isCreate = true;
				}
				if(isCreate){
					tab.tabChange(tab.contextId,id);
				}else{
					tab.addTab(menusMap[id]);
				}
				
			})
		}
		
		function error(event){
			
		}
		
	})

});