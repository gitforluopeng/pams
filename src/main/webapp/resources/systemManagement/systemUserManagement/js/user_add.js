layui.use('form',function(){
	var $=layui.$;
    var form=layui.form;
    var saveBtn = $('#saveBtn');
    var closeBtn = $('#canBtn');
    var table=window.parent.document.layuiTable;

    init();
    function init(){
        pageIndexLoad();
        init_saveBtn();
        init_closeBtn();
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
            var uType=$('#userType');
            uType.empty();
            var opt = $('<option></option>');
            opt.attr("value",0);
            opt.html('请选择');
            uType.append(opt);
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
        
        form.on('select(userDept)',function (data){
            var dept = data.value;
            var obj=$('#userType');
            obj.html('');
            if(dept==0){
            	return;
            }
            else{
            $.ajax({
                type : "get",
                async : true,
                url : "systemManagement/load_shiro_roles",
                data:{deptId:dept},
                success : function(data) {
                	var opt = $('<option></option>');
                	opt.attr("value",0);
                    opt.html('请选择');
                    obj.append(opt);
                	if(data==""||data==null||data==undefined){
                		return;
                	}
                    var roles=data["roles"];
                    for(var i = 0;i < roles.length; i++)
                    {
                    	var rolename=roles[i].name;
                    	var roleid=roles[i].id;
                    	var option = $('<option></option>');
                        option.attr("value",roleid);
                        option.html(rolename);
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

    function init_saveBtn(){
        saveBtn.on('click',function(){
        	var username= $('#accountNumber').val();
            var personName = $('#userName').val();
            var userNumber = $('#userCode').val();
            var userEmail = $('#userEmail').val();
            var userPhone = $('#userPhone').val();
            var password = $('#password').val();
            var aginpassword=$('#againPwd').val();
        	if(!(checkIsNotNull(username)&&checkIsNotNull(personName)&&checkIsNotNull(userNumber)&&checkIsNotNull(userEmail)&&checkIsNotNull(userPhone)&&checkIsNotNull(password)&checkIsNotNull(aginpassword))){
        		return;
        	}
        	if(!(checkIsNumber(userPhone))){
        		return;
        	}
        	if(!checkEmail(userEmail)){
        		return;
        	}
        	if(!(checkLength(username,5,15)&&checkLength(personName,0,13)&&checkLength(userPhone,10,12)&&checkLength(userNumber,0,15)&&checkLength(password,5,15)&&checkLength(userEmail,5,21))){
        		return;
        	}
        	if(!checkPassWord(password)){
        		return;
        	}
        	if(!checkIsSame(password,aginpassword)){
        		return;
        	}
            if($("#userType").val()==0||$("#userType").val()==null||$("#userType").val()==undefined){
                layer.msg('请选择用户类型',{icon:7});
            }
            else if($("#userUntil").val()==0||$("#userUntil").val()==null||$("#userUntil").val()==undefined){
                layer.msg('请选择所属单位',{icon:7});
            }
            else if($("#userDept").val()==0||$("#userDept").val()==null||$("#userDept").val()==undefined){
                layer.msg('请选择所属部门',{icon:7});
            }
            else{
                saveData();
            }
        })
    }
    
    function reset(){
    	$('#accountNumber').val('');
    	$('#userName').val('');
    	$('#userCode').val('');
    	$('#userPhone').val('');
    	$('#userEmail').val('');
    	$('#password').val('');
    	$('#againPwd').val('');
    	$('#remarks').val('');
    	var obj=$('#userDept');
        obj.val("0");
        var usertype=$("#userType");
        usertype.val("0");
        var unitl = $('#userUntil');
        unitl.val("0");
        obj.empty();
        var opt = $('<option></option>');
        opt.attr("value",0);
        opt.html('请选择');
        obj.append(opt);
        form.render('select', 'selectdiv');
    }
    
    function saveData(){
        var username= $('#accountNumber').val();
        var groupId=$('#userDept').val();
        var personName = $('#userName').val();
        var userNumber = $('#userCode').val();
        var userEmail = $('#userEmail').val();
        var userPhone = $('#userPhone').val();
        var otherInfo = $('#remarks').val();
        var shrioRoleId = $('#userType').val();
        var password = $('#password').val();
        var pwd = hex_md5(password).toUpperCase();
        loading = layer.load(1, {
            shade: [0.1,'#fff']
        });
        $.ajax({
            type: 'post',
            url: 'systemManagement/add_user',
            data:{'shiroUser.username':username,'shiroUser.groupId':groupId,'password':pwd,'systemUser.personName':personName,'systemUser.userNumber':userNumber,'systemUser.userEmail':userEmail,'systemUser.userPhone':userPhone,'systemUser.otherInfo':otherInfo,'myShiroUserGroupRole.shrioRoleId':shrioRoleId},
            success: function(data){
                if(data.status == 1){
                    layer.msg('保存成功',{icon:1});
                    reset();
                }
                else if(data.status == 0){
                	layer.msg('此用户名已经被使用了，请换个用户名试试.',{icon:2});
                }
                else{
                    layer.msg('啊喔，保存失败了，请重新保存试试.',{icon:5});
                }
                layer.close(loading);
            },
            error: function(){
                layer.msg('啊喔，出现问题了，保存失败，请重新保存或刷新页面试试.',{icon:5});
                layer.close(loading);
            }
        })

    }
    function init_closeBtn(){
    	closeBtn.on('click',function(){
        	close();
        })
    }
    function close(){
        var index = parent.layer.getFrameIndex(window.name);
        parent.layer.close(index);
    }
})