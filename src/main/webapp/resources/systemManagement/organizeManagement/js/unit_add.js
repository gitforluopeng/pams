layui.use(['form','layer'],function(){
    var form = layui.form;
    var layer = layui.layer;
    var saveBtn = $('#saveBtn');
    var closeBtn = $('#closeBtn');


    init();
    function init(){
        init_saveBtn();
        init_closeBtn();
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
            var _data = {
            			"unitname" : unitname,
            			"remarks" : remarks};
            loading = layer.load(1, {
                shade: [0.1,'#fff']
            });
            $.ajax({
                type : "POST",
                url : 'systemManagement/insert_unit',
                data : _data,
                success : function(data) {
                    if (data == 1) {
                    	$('#unitname').val("");
                    	$('#remarks').val("");
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
                        layer.msg('该单位已存在,新增失败!',{icon:2});
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