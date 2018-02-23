layui.use(['form','table'],function(){
    var form = layui.form;
    var table = layui.table;
    var queryBtn = $('#queryBtn');
    var resetBtn = $('#resetBtn');
    var addBtn = $('#addBtn');

    init();
    function init(){
    	pageIndexLoad();
        init_table();
        init_toolsclick();
        init_queryBtn();
        init_resetBtn();
        init_addBtn();
    }

    function init_toolsclick(){
        table.on('tool(tabletool)',function(obj){
            var data = obj.data;
            var layEvent = obj.event;
            if(layEvent == 'permissions'){
                permissions(data);
            }else if(layEvent == 'edit'){
                edit(data);
            }else if(layEvent == 'del'){
            	var shiroUserId=data.shiroUserId;
            	var systemUserId=data.systemUserId;
            	var myRoleId=data.myRoleId;
                layer.confirm('是否删除数据', function(index) {
                    $.ajax({
                        type : "post",
                        async : true,
                        data : {
                            'shiroUser.id' : shiroUserId,
                            'systemUser.id' : systemUserId,
                            "shiroUser.username":data.userName,
                            'myShiroUserGroupRole.id' : myRoleId
                        },
                        url : "systemManagement/delete_user",
                        success : function(data) {
                        	if(data.status==1) {
                        		obj.del();
                        		layer.close(index);
                        		layer.msg('删除成功',{icon:1});
                        	}
                        	else {
                        		layer.close(index);
                        		layer.msg('删除失败，该用户下存在案件！',{icon:5});
                        	}
                        },
                        error: function(){
                        	layer.close(index);
                            layer.msg('啊喔，出现问题了，请重试',{icon:5});
                        }
                    });
                });
            }else if(layEvent == 'unuse'){		//启用
            	layer.confirm("确定启用吗?",function(){
            		var _data = {"id" : data.shiroUserId,"username":data.userName,"accountLockStatus" : false};
            		update_status(_data);
            	})
            }else if(layEvent == 'use'){		//停用
            	layer.confirm("确定停用吗?",function(){
            		var _data = {"id" : data.shiroUserId,"username":data.userName,"accountLockStatus" : true};
            		update_status(_data);
            	})
            }
        })
    }
    
    function update_status(data){			//更改是否启用方法
    	var imgDom = $("#"+data.id+"img");
		var aDom = $("#"+data.id+"a");
		var beforeStatus = data.accountLockStatus;
    	loading = layer.load(1, {
            shade: [0.1,'#fff']
        });
		$.ajax({
			type: 'post',
			url: 'systemManagement/update_user_islock',
			data: data,
			success: function(data){
				if(data.status == 1){
					layer.msg('修改成功',{icon:1});
					console.info(beforeStatus);
					if(beforeStatus){
						imgDom.attr("src","resources/images/不启用.png");
						aDom.attr('lay-event','unuse');
					}else{
						imgDom.attr("src","resources/images/启用.png")
						aDom.attr('lay-event','use');
					}
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
    
    function permissions(data){
    	window.parent.document.data=data;
        parent.layer.open({
            type: 2,
            title: '权限设置页面',
            shadeClose: false,
            shade: 0.8,
            area: ['60%', '90%'],
            content: '',//iframe的url
            end: function () {
                table.reload('reload');
            }
        })
    }
    function edit(data){
    	window.parent.document.layuiTable=table;
        window.parent.document.data=data;
        parent.layer.open({
            type: 2,
            title: '编辑用户信息',
            shadeClose: false,
            shade: 0.8,
            area: ['900px','790px'],
            content: 'systemManagement/user_edit',//iframe的url
            end: function () {
                table.reload('reload');
            }
        })
    }

    function pageIndexLoad()
    {

        $.ajax({
            type : "get",
            async : true,
            url : "systemManagement/load_all_units",
            success : function(data) {
            	if(data==""||data==null||data==undefined){
            		return;
            	}
                var obj = $('#userUntil');
                var units=data["units"];
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
        form.on('select(userUntil)',function (data){
            var until = data.value;
            var obj=$('#userDept');
            obj.html('');
            //var uType=$('#userType');
            //uType.html('');
            if(until==0){
            	return;
            }
            else{
            $.ajax({
                type : "get",
                async : true,
                url : "systemManagement/load_unit_depts",
                data:{unitId:until},
                success : function(data) {
                	var opt = $('<option></option>');
                	opt.attr("value",0);
                    opt.html('请选择');
                    obj.append(opt);
                	if(data==""||data==null||data==undefined){
                		return;
                	}
                    var depts=data["depts"];
                    for(var i = 0;i < depts.length; i++)
                    {
                    	var deptname=depts[i].name;
                    	var deptid=depts[i].id;
                    	var option = $('<option></option>');
                        option.attr("value",deptid);
                        option.html(deptname);
                        obj.append(option);
                    }
                    form.render('select', 'selectdiv');
                },
                error: function(){
                    layer.msg('啊喔，数据载入出现异常了，请刷新页面试试',{icon:5});
                }
            });
            }

        });
        
    }

    function init_addBtn(){
    	window.parent.document.layuiTable=table;
        addBtn.on('click',function(){
            parent.layer.open({
                type: 2,
                title: '添加用户',
                shadeClose: false,
                shade: 0.8,
                area: ['900px','790px'],
                content: 'systemManagement/user_add',//iframe的url
                end: function () {
                    table.reload('reload');
                }
            })
        })
    }

    function init_resetBtn(){
       resetBtn.on('click',function(){
           $('#username').val('');
           var obj=$('#userDept');
           obj.val("0");
           var unitl = $('#userUntil');
           unitl.val("0");
           obj.empty();
           var opt = $('<option></option>');
           opt.attr("value",0);
           opt.html('请选择');
           obj.append(opt);
           form.render('select', 'selectdiv');
       })
    }

    function init_queryBtn(){
        queryBtn.on('click',function(){
        	var useruntilname = $('#userUntil').val();
            var userdeptname =  $('#userDept').val();
            var username = $('#username').val();
            table.reload('reload',{
                url: 'systemManagement/query_user_infos',
                page: {
                    curr: 1 //重新从第 1 页开始
                  },
                method:'POST',
                where: {'unitId' : useruntilname,
                        'deptId' : userdeptname,
                        'name' : username}
            })
        })
    }

    function init_table() {
        table.render({
            id: 'reload',
            elem: '#table',
            height: 'full-180',
            url: 'systemManagement/load_user_infos',
            method: 'post',
            width: 'full',
            page: true,
            limit: 10,
            limits: [10,15,20,25,30],
            cols: [[
                {field: 'userName',align:'center', title: '账号', width:140},
                {field: 'personName',align:'center', title: '姓名', width:140},
                {field: 'userType', align:'center',title: '用户类型', width:102},
                {field: 'unitName', align:'center',title: '所属单位名称', width:145},
                {field: 'deptName',align:'center', title: '所属部门名称', width:119},
                {field: 'stationName',align:'center', title: '所属岗位', width:119},
                {field: 'education',align:'center', title: '学历', width:119},
                {field: 'userEmail',align:'center', title: '邮箱地址', width:160},
                {field: 'userPhone',align:'center', title: '联系电话', width:160},
                {field: 'createTime',align:'center', title: '添加时间', width:150,templet:'#usercreattime'},
                {field: 'lastLoginTime',align:'center', title: '最后登录时间', width:150,templet:'#logintime'},
                {field: 'accountLockStatus',title: '是否启用', align:'center',width: 140, templet: '#titleTpl'},
                {fixed: 'right', title: '操作', width:144, align:'center', field: 'right', toolbar: '#barDemo'}
            ]]

        })
    }

})