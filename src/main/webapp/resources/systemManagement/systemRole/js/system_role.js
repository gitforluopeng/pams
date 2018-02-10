layui.use(['form','table'],function(){
    var form = layui.form;
    var table = layui.table;
    var $=layui.$;
    var addBtn = $('#addBtn');
    var contextsel = '<div class="layui-form" lay-filter="selectdiv">\n' +
    '<div>\n'+
    '    <div class="layui-inline" style="margin-left: 15%;">\n' +
    '        <div class="layui-inline" style="margin-top: 30px;">\n' +
    '            设置可访问资源:\n' +
    '        </div>\n' +
    '        <div class="layui-inline" style="margin-top: 30px;margin-left:10px;width:300px;">\n' +
    '            <select id="userDept" lay-filter="userDept" class="layui-select">\n' +
    '                <option value="0">请选择</option>\n' +
    '            </select>\n' +
    '        </div>\n' +
    '    </div>\n' +
    '    </div>\n'+
    '<div>\n'+
    '    <div class="layui-inline" style="margin-top: 30px;margin-left: 40%;">\n' +
    '        <button id="zsaveBtn" class="layui-btn layui-btn-normal">保存</button>\n' +
    '        <button id="zcloseBtn" class="layui-btn layui-btn-primary">取消</button>\n' +
    '    </div>\n' +
    '</div>\n'+
    '</div>';
    var contextadd = '<div class="layui-form" lay-filter="sourceadd">\n' +
    '<div id="div-add-table" style="width: 98%;margin-left: 10px;margin-right: 10px">\n'+
    '   <table id="addtable" lay-filter="addsour"></table>\n'+
    '</div>\n'+
    '</div>';
    var context = '<div class="layui-form">\n' +
    '    <div class="layui-inline">\n' +
    '        <div class="layui-inline" style="margin-left: 20px;margin-top: 30px;">\n' +
    '            角色名称:\n' +
    '        </div>\n' +
    '        <div class="layui-inline">\n' +
    '            <input id="sysname" class="layui-input" style="margin-top: 30px;margin-left:10px;width:300px;" placeholder="请输入内容...">\n' +
    '        </div>\n' +
    '    </div>\n' +
    '    <div class="layui-inline" style="margin-top: 30px;margin-left:20px">\n' +
    '        <button id="saveBtn" class="layui-btn layui-btn-normal">保存</button>\n' +
    '        <button id="closeBtn" class="layui-btn layui-btn-primary">取消</button>\n' +
    '    </div>\n' +
    '</div>';
    var contexttab = '<div class="layui-form" lay-filter="sourceinfos">\n' +
    '<div id="div-table" style="width: 98%;margin-left: 10px;margin-right: 10px">\n'+
    '   <table id="more" lay-filter="more"></table>\n'+
    '</div>\n'+
    '</div>';
    init();
    function init(){
        init_table();
        init_addBtn();
        init_tool();
        
    }
    function pageIndexLoad()
    {

        $.ajax({
            type : "get",
            async : true,
            url : "systemManagement/load_all_resources",
            success : function(data) {
            	if(data==""||data==null||data==undefined){
            		return;
            	}
                var obj = $('#userDept');
                var units=data["res"];
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
    }
    function init_tool(){
        table.on('tool(tool)',function(obj){
            var data = obj.data;
            var layEvent = obj.event;
            if(layEvent === 'del'){
                layer.confirm('确定删除吗?',function(index){
                	loading = layer.load(1, {
                        shade: [0.1,'#fff']
                    });
                    $.ajax({
                        type: 'post',
                        url: 'systemManagement/delete_system_role',
                        data: {'shiroRole.id' : data.id,"shiroRole.name" : data.name},
                        success: function(data){
                            if(1 == data.status){
                            	layer.msg('删除成功!',{icon:1});
                            	obj.del();
                            }
                            else if(0 == data.status){
                            	layer.msg('不能删除该系统设定角色!',{icon:2});
                            }else{
                            	layer.msg('删除失败,请重试!',{icon:5});
                            }
                        },
                        error: function(){
                            layer.msg('删除失败,请重试!',{icon:5});
                        }
                    })
                    layer.close(loading);
                })
            }
            else if(layEvent == 'seemore'){		//详细信息
            	window.parent.document.id = data.id;
            	var mytittle=data.name+"角色已有资源-详细信息";
                var index=null;
                index=layer.open({
                    type: 1,
                    title: mytittle,
                    shadeClose: false,
                    shade: 0.8,
                    area: ['846px', '80%'],
                    content: contexttab,
                    success: function(){
                    	table.render({
                            id: 'moreinfo',
                            elem: '#more',
                            height: 'full-100',
                            where:{id:window.parent.document.id},
                            method: 'post',
                            url: 'systemManagement/load_it_resources',
                            cols: [[
                                {field: 'name', title: '资源名称',align:'center', width:560},
                                {fixed: 'right', title: '操作', align:'center', width:240, toolbar: '#barCanc'}
                            ]]
                        });
                    	table.on('tool(more)',function(objc){
                            var data = objc.data;
                            console.info(data);
                            console.info(window.parent.document.id);
                            var layEvent = objc.event;
                            if(layEvent === 'cancleitsource'){
                                layer.confirm('确定删除吗?',function(index){
                                	loading = layer.load(1, {
                                        shade: [0.1,'#fff']
                                    });
                                    $.ajax({
                                        type: 'post',
                                        url: 'systemManagement/delete_role_resource',
                                        data: {'shiroResourcesRoles.shiroResourcesId' : data.id,"shiroResourcesRoles.shiroRroleId" : window.parent.document.id,"shiroResourcesRoles.name" : data.name},
                                        success: function(data){
                                            if(1 == data.status){
                                            	layer.msg('删除成功!',{icon:1});
                                            	objc.del();
                                            }
                                            else{
                                            	layer.msg('删除失败,请重试!',{icon:5});
                                            }
                                        },
                                        error: function(){
                                            layer.msg('删除失败,请重试!',{icon:5});
                                        }
                                    })
                                    layer.close(loading);
                                })
                            }
                    	});
                    },
                    end: function () {
                        table.reload('reload');
                    }
                })
            }else if(layEvent == 'sourceset'){		//资源设置
            	window.parent.document.id = data.id;
            	var tittleadd=data.name+"角色资源管理-添加资源权限";
                var index=null;
                index=layer.open({
                    type: 1,
                    title: tittleadd,
                    shadeClose: false,
                    shade: 0.8,
                    area: ['846px', '80%'],
                    content: contextadd,
                    success: function(){
                    	table.render({
                            id: 'addinfo',
                            elem: '#addtable',
                            height: 'full-100',
                            where:{id:window.parent.document.id},
                            method: 'post',
                            url: 'systemManagement/load_notadd_resources',
                            cols: [[
                                {field: 'name', title: '资源名称',align:'center', width:560},
                                {fixed: 'right', title: '操作', align:'center', width:240, toolbar: '#barAdd'}
                            ]]
                        });
                    	$.ajax({
                            type: 'post',
                            url: 'systemManagement/load_notadd_resources',
                            data: {'id':window.parent.document.id},
                            success: function(data){
                                if(data.count == 0){
                                	layer.msg('该角色已添加所有系统资源权<br>限，无其它资源权限可添加！',{icon:2,time:5000});
                                }else{
                                	
                                }
                            }
                        });
                    	table.on('tool(addsour)',function(obja){
                            var data = obja.data;
                            console.info(data);
                            console.info(window.parent.document.id);
                            var layEvent = obja.event;
                            if(layEvent === 'additsource'){
                            	loading = layer.load(1, {
                                    shade: [0.1,'#fff']
                                });
                                $.ajax({
                                    type: 'post',
                                    url: 'systemManagement/add_role_resource',
                                    data: {'shiroResourcesRoles.shiroResourcesId' : data.id,"shiroResourcesRoles.shiroRroleId" : window.parent.document.id,"shiroResourcesRoles.name" : data.name},
                                    success: function(data){
                                        if(data.status == 1){
                                            layer.msg('添加成功!',{icon:1});
                                        }else if(data.status == 0){
                                            layer.msg('添加失败,该角色已有该资源!',{icon:2});
                                        }else{
                                        	layer.msg('添加失败,请重试',{icon:5});
                                        }
                                        table.reload('addinfo');
                                        layer.close(loading);
                                    },
                                    error: function(){
                                        layer.msg('保存失败,请重试',{icon:5});
                                        table.reload('addinfo');
                                        layer.close(loading);
                                    }
                                });
                            }
                    	});
                    },
                    end: function () {
                        table.reload('reload');
                    }
                });
                    /*	pageIndexLoad();
                    	var editSaveBtn = $('#zsaveBtn');
                    	var editCloseBtn = $('#zcloseBtn');
                    	editSaveBtn.on('click',function(){
                    		var souceid = $('#userDept').val();
                    		var roleid=window.parent.document.id;
                            if(souceid==0||souceid == null || souceid == ''||souceid==undefined){
                                layer.msg('请选择资源!',{icon:2});
                                return;
                            }
                            else {
                            	loading = layer.load(1, {
                                    shade: [0.1,'#fff']
                                });
                                $.ajax({
                                    type: 'post',
                                    url: 'systemManagement/add_role_resource',
                                    data: {'shiroResourcesRoles.shiroResourcesId' : souceid,'shiroResourcesRoles.shiroRroleId':roleid},
                                    success: function(data){
                                        if(data.status == 1){
                                            layer.msg('保存成功!',{icon:1});
                                        }else if(data.status == 0){
                                            layer.msg('保存失败,该角色已有该资源!',{icon:2});
                                        }else{
                                        	layer.msg('保存失败,请重试',{icon:5});
                                        }
                                        layer.close(loading);
                                    },
                                    error: function(){
                                        layer.msg('保存失败,请重试',{icon:5});
                                        layer.close(loading);
                                    }
                                });
                            }
                    	});
                    	editCloseBtn.on('click',function(){
                    		layer.close(index);
                            index=null;
                    	});
                    },
                    end: function () {
                        table.reload('reload');
                    }
                })*/
            }else if(layEvent == 'unuse'){		//停用
            	layer.confirm("确定启用吗?",function(){
            		var _data = {"id" : data.id,"name" : data.name,"lockStatus" : false};
            		update_status(_data);
            	})
            }else if(layEvent == 'use'){		//启用
            	layer.confirm("确定停用吗?",function(){
            		var _data = {"id" : data.id,"name" : data.name,"lockStatus" : true};
            		update_status(_data);
            	})
            }
            else if(layEvent == 'edit'){
                window.parent.document.id = data.id;
                window.parent.document.sysName=data.name;
                var index=null;
                index=layer.open({
                    type: 1,
                    title: '编辑角色名称',
                    shadeClose: false,
                    shade: 0.8,
                    area: ['40%', '20%'],
                    content: context,
                    success: function(){
                    	$('#sysname').val(window.parent.document.sysName);
                    	
                    	var editSaveBtn = $('#saveBtn');
                    	var editCloseBtn = $('#closeBtn');
                    	console.info(window.parent.document.sysName);
                    	var uneditname=window.parent.document.sysName;
                    	editSaveBtn.on('click',function(){
                    		var sysname = $('#sysname').val();
                    		var sid=window.parent.document.id;
                    		if(window.document.sysname==sysname){
                            	layer.msg('您没有修改任何信息，操作被拒绝了喔',{icon:2});
                            	return;
                            }
                            if(sysname == null || sysname == ''||sysname==undefined){
                                layer.msg('角色名称不能为空!',{icon:2});
                                return;
                            }
                            else {
                            	savad(uneditname);
                            }
                    	});
                    	editCloseBtn.on('click',function(){
                    		layer.close(index);
                            index=null;
                    	});
                    },
                    end: function () {
                        table.reload('reload');
                    }
                })
            }
        })
    }
    
    function savad(uneditname){
    	var uneditname=uneditname;
    	var sysname = $('#sysname').val();
    	var sid=window.parent.document.id;
    	window.document.sysname=sysname;
    	loading = layer.load(1, {
            shade: [0.1,'#fff']
        });
        $.ajax({
            type: 'post',
            url: 'systemManagement/update_system_role',
            data: {'shiroRole.name' : sysname,'shiroRole.id':sid,'unEditName':uneditname},
            success: function(data){
                if(data.status == 1){
                    layer.msg('保存成功!',{icon:1});
                }else if(data.status == 0){
                    layer.msg('保存失败,已存在该角色名称!',{icon:2});
                }else if(data.status == -2){
                    layer.msg('不能修改该系统设定角色!',{icon:2});
                }else{
                	layer.msg('保存失败,请重试',{icon:5});
                }
                layer.close(loading);
            },
            error: function(){
                layer.msg('保存失败,请重试',{icon:5});
                layer.close(loading);
            }
        });
        
    }
    function init_addBtn(){
        addBtn.on('click',function(){
            var sysname = $('#check_username') .val();
            if(sysname == null || sysname == ''){
                layer.msg('请输入角色名称',{icon:2});
                return;
            }
            loading = layer.load(1, {
                shade: [0.1,'#fff']
            });
            $.ajax({
                type: 'post',
                url: 'systemManagement/add_system_role',
                data: {"shiroRole.name" : sysname},
                success : function(data){
                	if(data.status == 1){
                		layer.msg('添加成功!',{icon:1});
                		table.reload('reload');
                        $('#check_username') .val('');
                	}else if(data.status == 0){
                		layer.msg('添加失败,已存在该角色名称!',{icon:2});
                	}else{
                		layer.msg('添加失败,请重试',{icon:2});
                	}
                    layer.close(loading);
                },
                error: function(){
                	layer.msg('添加失败,请重试',{icon:2});
                	layer.close(loading);
                }
            })
        })
    }
    
    function update_status(data){			//更改是否启用方法
    	var imgDom = $("#"+data.id+"img");
		var aDom = $("#"+data.id+"a");
		var beforeStatus = data.lockStatus;
    	loading = layer.load(1, {
            shade: [0.1,'#fff']
        });
		$.ajax({
			type: 'post',
			url: 'systemManagement/update_system_role_isuse',
			data: {"id" : data.id,"name" : data.name,"lockStatus":data.lockStatus},
			success: function(data){
				if(data.status == 1){
					layer.msg('修改成功',{icon:1});
					if(beforeStatus){
						imgDom.attr("src","resources/images/不启用.png");
						aDom.attr('lay-event','unuse');
					}else{
						imgDom.attr("src","resources/images/启用.png")
						aDom.attr('lay-event','use');
					}
				}
				else if(data.status == 0){
					layer.msg('不能停用该系统设定角色!',{icon:2});
				}
				else{
					layer.msg('修改失败,请重试!',{icon:2});
				}
				layer.close(loading);
			},
			error: function(){
				layer.msg('修改失败,请重试!',{icon:2});
				layer.close(loading);
			}
		})
    }

    function init_table(){
        table.render({
            id: 'reload',
            elem: '#table',
            height: 'full-110',
            method: 'post',
            url: 'systemManagement/load_system_roles',
            cellMinWidth: 50,
            page: true,
            limit: 10,
            limits: [10,15,20,25,30],
            cols: [[
                {field: 'id', title: '编号',align:'center'},
                {field: 'name', title: '角色名称',align:'center'},
                {field: 'lockStatus', title: '是否启用', align:'center', templet: '#titleTpl'},
                {fixed: 'right', title: '资源管理', align:"center",toolbar:'#barmore'}
            ]]
        })
    }

})