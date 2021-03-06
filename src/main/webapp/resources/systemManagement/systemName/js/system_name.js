layui.use(['form','table'],function(){
    var form = layui.form;
    var table = layui.table;
    var $=layui.$;
    var addBtn = $('#addBtn');
    var context = '<div class="layui-form">\n' +
    '    <div class="layui-inline">\n' +
    '        <div class="layui-inline" style="margin-left: 20px;margin-top: 30px;">\n' +
    '            系统名称:\n' +
    '        </div>\n' +
    '        <div class="layui-inline">\n' +
    '            <input id="sysname" class="layui-input" style="margin-top: 30px;margin-left:10px;width:300px;" placeholder="请输入内容...">\n' +
    '        </div>\n' +
    '    </div>\n' +
    '    <div class="layui-inline" style="margin-top: 30px;margin-left:20px">\n' +
    '        <button id="saveBtn" class="layui-btn layui-btn-normal">保存</button>\n' +
    '        <button id="closeBtn" class="layui-btn layui-btn-primary">关闭</button>\n' +
    '    </div>\n' +
    '</div>';
    
    init();
    function init(){
        init_table();
        init_addBtn();
        init_tool();
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
                        url: 'systemManagement/delete_system_name',
                        data: {'systemName.id' : data.id,"systemName.name" : data.name,'systemName.isUse':data.isUse},
                        success: function(data){
                            if(1 == data.status){
                            	layer.msg('删除成功!',{icon:1});
                            	obj.del();
                            }
                            else if(0 == data.status){
                            	layer.msg('不能删除已启用的系统名称!',{icon:2});
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
            }else if(layEvent == 'unuse'){		//停用
            	layer.confirm("确定启用吗?",function(){
            		var _data = {"id" : data.id,"name" : data.name,"isUse" : true};
            		update_status(_data);
            	})
            }else if(layEvent == 'use'){		//启用
            	layer.confirm("确定停用吗?",function(){
            		var _data = {"id" : data.id,"name" : data.name,"isUse" : false};
            		update_status(_data);
            	})
            }
            else if(layEvent == 'edit'){
                window.parent.document.id = data.id;
                window.parent.document.sysName=data.name;
                var index=null;
                index=layer.open({
                    type: 1,
                    title: '编辑系统名称',
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
                                layer.msg('系统名称不能为空!',{icon:2});
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
            url: 'systemManagement/update_system_name',
            data: {'systemName.name' : sysname,'systemName.id':sid,'unEditName':uneditname},
            success: function(data){
                if(data.status == 1){
                    layer.msg('保存成功!',{icon:1});
                }else if(data.status == 0){
                    layer.msg('保存失败,已存在该系统名称!',{icon:2});
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
                layer.msg('请输入系统名称',{icon:2});
                return;
            }
            loading = layer.load(1, {
                shade: [0.1,'#fff']
            });
            $.ajax({
                type: 'post',
                url: 'systemManagement/add_system_name',
                data: {"systemName.name" : sysname},
                success : function(data){
                	if(data.status == 1){
                		layer.msg('添加成功!',{icon:1});
                		table.reload('reload');
                        $('#check_username') .val('');
                	}else if(data.status == 0){
                		layer.msg('添加失败,已存在该系统名称!',{icon:2});
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
		var beforeStatus = data.isUse;
    	loading = layer.load(1, {
            shade: [0.1,'#fff']
        });
		$.ajax({
			type: 'post',
			url: 'systemManagement/update_system_name_isuse',
			data: {"systemName.id" : data.id,"systemName.name" : data.name,"systemName.isUse":data.isUse},
			success: function(data){
				if(data.status == 1){
					layer.msg('修改成功',{icon:1});
					if(beforeStatus){
						imgDom.attr('src','resources/images/启用.png')
						aDom.attr('lay-event','use');
					}else{
						imgDom.attr('src','resources/images/不启用.png')
	            		aDom.attr('lay-event','unuse');
					}
				}
				else if(data.status == 0){
					layer.msg('只能启用一个系统名称',{icon:2});
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
            url: 'systemManagement/load_system_names',
            cellMinWidth: 50,
            page: true,
            limit: 10,
            limits: [10,15,20,25,30],
            cols: [[
                {field: 'id', title: '编号',align:'center'},
                {field: 'name', title: '系统名称',align:'center'},
                {field: 'changeTime', title: '操作日期',align:'center',templet: '#operationtime'},
                {field: 'isUse', title: '是否启用', align:'center', templet: '#titleTpl'},
                {fixed: 'right', title: '操作', align:'center', toolbar: '#barDemo'}
            ]]
        })
    }

})