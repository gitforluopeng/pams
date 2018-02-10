layui.use('form',function(){
    var form = layui.form;
    var saveBtn = $('#saveBtn');
    var bushBtn = $('#bushBtn');
    var cancelBtn = $('#cancelBtn');
    var location =  window.parent.document.deptName;

    init();
    function init(){
        init_chooseAll();
        init_chooseMsg();
        getdata();
        init_saveBtn();
        init_closeBtn();
        init_bushBtn();
    }

    function init_chooseMsg(){
        form.on('checkbox(msgchoose)',function(data){
            console.info('选中');
            var child = $('.msg').find('input[type="checkbox"]');
            child.each(function(index, item){
                item.checked = data.elem.checked;
            });
            form.render('checkbox');
        });
    }

    function init_chooseAll(){
        form.on('checkbox(chooseAll)', function(data){
            var child = $('.layui-form').find('input[type="checkbox"]');
            child.each(function(index, item){
                item.checked = data.elem.checked;
            });
            form.render('checkbox');
        });

    }

    function getdata(){
    	loading = layer.load(1, {
            shade: [0.1,'#fff']
        });
    	$('#location').html(location);
        $.ajax({
            type: 'get',
            url: '',
            data: {untilname : location},
            success : function(data){
                for(var i = 0;i < data.length; i++){
                    if(1 == data[i].status)
                        $('#'+data[i].name).checked = true;
                    else
                        $('#'+data[i].name).checked = false;
                }
                form.render('checkbox');
                layer.close(loading);
            }
        })
    }

    function  init_saveBtn(){
        saveBtn.on('click',function(){
            var _data = {};
            var index = $('#index').val();
            var live = $('#live').val();
            var jishitong = $('#jishitong').val();
            var msg = $('#msg').val();
            var msgchage = $('#msgchage').val();
            var count = $('#count').val();
            var gread = $('#gread').val();
            var sys = $('#sys').val();
            var video = $('#video').val();
            var org = $('#org').val();
            var sysname = $('#sysname').val();

            $.ajax({
                type: 'post',
                url: '',
                data:{
                    index : index,
                    live : live,
                    jishitong : jishitong,
                    msg : msg,
                    msgchage : msgchage,
                    count :count,
                    gread :gread,
                    sys : sys,
                    video :video,
                    org : org,
                    sysname : sysname
                },
                success : function(data){
                    var status = data.status;
                    if(1 == status)
                        layer.msg('保存成功',{icon:1});
                    else
                        layer.msg('保存失败',{icon:2});
                }
            })
        })
    }
    function init_closeBtn(){
        cancelBtn.on('click',function(){
            var index = parent.layer.getFrameIndex(window.name);
            parent.layer.close(index);
        })
    }
    function init_bushBtn(){
        bushBtn.on('click',function(){
            form.render();
        })
    }
})