(function(layui){
	return window.formSelectsa = formSelectsa = {
		options:{
			layFilter: '',
			left:'【',
			right:'】',
			separator:'',
			arr:[],
		},
		$dom: null,
		arr: [],
		on(options){//开启
			if(!options || !options.layFilter){
				alert('请传入lay-filter');
				return ;
			}
			layui.use(['form', 'jquery'], function(){
				if(options.arr.length>0){
					for(var i=0;i<options.arr.length;i++){
						formSelectsa.arr.push(options.arr[i])
					}
				}
				var form = layui.form, $ = layui.jquery;
				$.extend(true, formSelectsa.options, options);
				formSelectsa.$dom = $('select[lay-filter="'+formSelectsa.options.layFilter+'"]').next();
				formSelectsa.$dom.find('dl').css('display', 'none');
				formSelectsa.show();
				formatJson(formSelectsa.options.layFilter,formSelectsa.arr);
				form.on('select('+formSelectsa.options.layFilter+')', function(data){
				  	var $choose = formSelectsa.exchange(data);
				  	//如果所选有值, 放到数组中
				  	if($choose){
				  		var include = false;
				  		for(var i in formSelectsa.arr){
				  			if(formSelectsa.arr[i] && formSelectsa.arr[i].val == $choose.val){
				  				formSelectsa.arr.splice(i,1);
				  				include = true;
				  			}
				  		}
				  		if(!include){
				  			formSelectsa.arr.push($choose);
				  		}
				  	}
				  	//调整渲染的Select显示
				  	formSelectsa.show();
				  	//取消收缩效果
					formSelectsa.$dom.find('dl').css('display', 'block');
				  	//这行代码是用于展示数据结果的
				  	formatJson(formSelectsa.options.layFilter,formSelectsa.arr);
				});
				
				$(document).on('click', 'select[lay-filter="'+formSelectsa.options.layFilter+'"] + div input', ()=>{
					formSelectsa.show();
				});
				$(document).on('click', 'body:not(select[lay-filter="'+formSelectsa.options.layFilter+'"] + div)', (e)=>{
					var showFlag = $(e.target).parents('.layui-form-select').prev().attr('lay-filter') == formSelectsa.options.layFilter;
					var thisFlag = formSelectsa.$dom.find('dl').css('display') == 'block';
					if(showFlag){//点击的input框
						formSelectsa.$dom.find('dl').css('display', thisFlag ? 'none' : 'block');
					}else{
						if(thisFlag){
							formSelectsa.$dom.find('dl').css('display', 'none');
						}
					}
				});
			});
		},
		show(){
		  	formSelectsa.$dom.find('.layui-this').removeClass('layui-this');
		  	var input_val = '';
		  	for(var i in formSelectsa.arr){
		  		var obj = formSelectsa.arr[i];
		  		if(obj){
		  			input_val += formSelectsa.options.separator + formSelectsa.options.left+obj.name+formSelectsa.options.right;
					formSelectsa.$dom.find('dd[lay-value="'+obj.val+'"]').addClass('layui-this');							  			
		  		}
		  	}
		  	if(formSelectsa.options.separator && formSelectsa.options.separator.length > 0 && input_val.startsWith(formSelectsa.options.separator)){
		  		input_val = input_val.substr(formSelectsa.options.separator.length);
		  	}
		  	formSelectsa.$dom.find('.layui-select-title input').val(input_val);
		},
		exchange(data){
			if(data.value){
				return {
					name: $(data.elem).find('option[value='+data.value+']').text(),
					val: data.value
				}
			}
		},
		getData(name,value){
				return {
					name: name,
					val: value
				}
		}
	};
})(layui);
