layui.use(['form','layer'],function(){
    var form = layui.form;
    var layer = layui.layer;
    var saveBtn = $('#saveBtn');
    var closeBtn = $('#closeBtn');


    init();
    function init(){
        pageIndexLoad();
        init_saveBtn();
        init_closeBtn();
    }

    function  pageIndexLoad(){
    	loading = layer.load(1, {
            shade: [0.1,'#fff']
        });
    	$.ajax({
            type : "get",
            async : true,
            url : "systemManagement/load_all_units",
            success : function(data) {
            	if(data==""||data==null||data==undefined){
            		return;
            	}
                var obj = $('#untilnames');
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
                layer.close(loading);
            },
            error: function(){
                layer.msg('啊喔，数据载入出现异常了，请刷新页面试试',{icon:5});
            }
        });
    }
    function init_saveBtn(){
        saveBtn.on('click',function(){
            var unitId = $('#untilnames').val();
            var deptname = $('#deptname').val();
            var remarks = $('#remarks').val();
            if(unitId == '请选择' || unitId == '' ||unitId == null){
                layer.msg('请选择单位',{icon:2});
                return;
            }
            if(deptname == '' || deptname == null
               || remarks == '' || remarks == null){
                layer.msg('内容不能为空!',{icon:2});
                return;
            }
            var _data = {"unitId" : unitId,
            			"deptname" : deptname,
            			"remarks" : remarks};
            loading = layer.load(1, {
                shade: [0.1,'#fff']
            });
            $.ajax({
                type : "POST",
                url : 'systemManagement/insert_dept',
                data : _data,
                success : function(data) {
                    if (data == 1) {
                    	$('#untilnames').val("");
                    	$('#deptname').val("");
                    	$('#remarks').val("");
                    	form.render('select', 'selectdiv');
                    	layer.alert('保存成功!', {
                    		icon: 1,
                    		skin: 'layer-ext-moon',
                    		success: function(){

                    			layer.close(loading);
                    		},
                    	})
                    } else if(data == 0) {
                        layer.msg('保存失败，请重试!',{icon:2});
                        layer.close(loading);
                    }
                    else if(data == 2){
                        layer.msg('该部门已存在,新增失败!',{icon:2});
                        layer.close(loading);
                    }
                },
                error : function() {
                    layer.msg('保存失败，请重试!',{icon:2});
                    layer.close(loading);
                }
            });
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