layui.use(['form','layer'],function(){
    var form = layui.form;
    var layer = layui.layer;
    var saveBtn = $('#saveBtn');
    var closeBtn = $('#closeBtn');
    var unitId = window.parent.document.id;


    init();
    function init(){
        getdata();
        init_saveBtn();
        init_closeBtn();
    }

    
    function getdata(){
    	loading = layer.load(1, {
            shade: [0.1,'#fff']
        });
        $.ajax({
            type : "post",
            url : "systemManagement/get_organize",
            data:{"id" : unitId},
            success : function(data){
            	var organize = data["organize"];
                $('#unitname').val(organize.name);
                $('#remarks').val(organize.description);
                layer.close(loading);
            }
        });
    }
    function init_saveBtn(){
        saveBtn.on('click',function(){
            var unitname = $('#unitname').val();
            var remarks = $('#remarks').val();
            if(unitname == '' || unitname == null
                || remarks == '' || remarks == null){
                layer.msg('内容不能为空!',{icon:2});
                return;
            }
            var _data = {};
            _data['unitId'] = unitId;
            _data['unitName'] = unitname;
            _data['remarks'] = remarks;
            loading = layer.load(1, {
                shade: [0.1,'#fff']
            });
            $.ajax({
                type : "POST",
                url : 'systemManagement/save_unit_edit',
                data : _data,
                success : function(data) {
                	console.info(window.name);
                    if (data == 1) {
                    	
                    	layer.alert('保存成功!', {
                  		  icon: 1,
                  		  skin: 'layer-ext-moon',
                  		  success: function(){
                  			  layer.close(loading);
                  		  },
                  		})
                  		var index = parent.layer.getFrameIndex(window.name);
                    	layer.close(index+1);
                    }else if(data == 2){
                    	layer.msg('保存失败，已存在部门名!',{icon:2});
                    	layer.close(loading);
                    } else {
                    	layer.msg('保存失败，请重试!',{icon:2});
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