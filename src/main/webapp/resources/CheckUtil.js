function CheckUtil($) {
	
	var $ = $;
	
	this.regex = {number: "/^[0-9]+$/", float: "/^([+-]?)\\d*\\.?\\d+$/"};
	/**
	 * 验证json数组中 json 对象属性值为指定类型
	 * 如果有为null 返回 数组索引， 和json属性名
	 */
	this.checkDatasValueType = function (datas, fieldName, regexName){
		for(var i = 0; i < datas.length; i++){
			var data = datas[i];
			var value = data[fieldName]+"";
			if(!value.match(eval(regexName))){
				return {status: false, field: fieldName, index: i};
			}
		}
		return {status: true};
	}
	
	/**
	 * 验证json数组中 json 对象属性是否有为空
	 * 如果有为null 返回 数组索引， 和json属性名
	 */
	this.checkDatasNull = function (datas){
		for(var i = 0; i < datas.length; i++){
			var data = datas[i];
			for(key in data){
				console.info((data[key] == null)+" : "+key);
				console.info((data[key]+"" == '')+" : "+key);
				if(data[key] == undefined
						|| data[key]+"" == ''){
					return {status: false, field: key, index: i};
				}
			}
		}
		return {status: true};
	}
	
    /**
     * 验证input内容是否为空
     * @param fields [{field: 'id'},{field: 'id'},...]
     * @returns {*} {status: '状态true 为正常', field: 'id'}
     */

    this.checkNull = function (fields){
        for(var i = 0; i < fields.length; i++){
            var field = $('#'+fields[i].field);
            if(field == null || field.val() == null
                || field.val() == ''){
                return {status: false, field: fields[i].field};
            }
        }
        return {status: true};
    }

    /**
     * 验证input内容长度是否有误
     * @param fields [{field: 'id',lengthMin:"最小长度",lengthMax:"最大长度"},....]
     * @returns {*} {status: '状态true 为正常', field: 'id'}
     */
    this.checkLength=function (fields) {
        for(var i = 0; i < fields.length; i++){
            var param = fields[i];
            var field = $('#'+param.field);
            if(field == null || field.val().length <= param.lengthMin
                || field.val().length >= param.lengthMax){
                return {status: false, field:param.field};
            }
        }
        return {status: true};
    }

    /**
     * 验证Email格式是否正确
     * @param fields [{field: 'id'},{field: 'id'}....]
     * @returns {*} {status: '状态true 为正常', field: 'id'}
     */
    this.checkEmail=function (fields) {
        for(var i = 0;i<fields.length; i++){
            var param = fields[i];
            var field = $('#'+param.field);
            var result=field.val().match(/^\w+((-\w+)|(\.\w+))*\@[A-Za-z0-9]+((\.|-)[A-Za-z0-9]+)*\.[A-Za-z0-9]+$/);
            if(result==null) return {status: false, field:param.field};
        }
        return {status: true};
    }

    /**
     * 验证input内容是否为纯数字
     * @param fields [{field: 'id'},{field: 'id'}....]
     * @returns {*} {status: '状态true 为正常', field: 'id'}
     */
    this.checkNumber=function (fields) {
        for(var i = 0;i<fields.length; i++) {
            var param = fields[i];
            var field = $('#' + param.field);

                var result = field.val().match(/^[0-9]+$/);
                if (result == null) return {status: false, field:param.field};
        }
        return {status: true};
    }

    /**
     * 验证input内容是否为Date：YYYY-MM-DD
     * @param fields [{field: 'id'},{field: 'id'}....]
     * @returns {*} {status: '状态true 为正常', field: 'id'}
     */
    this.checkDate=function (fields) {
        for(var i = 0; i < fields.length; i++)
        {
            var param=fields[i];
            var field=$("#"+param.field);
            var result=field.val().match(/^(\d{4})(-|\/)(\d{1,2})\2(\d{1,2})$/);
            if(result==null)
                return {status:false,field:param.field};
            else{
                var d=new Date(result[1], result[3]-1, result[4]);
                if((d.getFullYear()==result[1] && d.getMonth()+1==result[3] && d.getDate()==result[4])==false)
                    return {status:false,field:param.field};
            }
        }
        return {status:true};
    }
    /**
     * 验证身份证格式
     * @param fields [{field: 'id'},{field: 'id'}....]
     * @returns {*} {status: '状态true 为正常', field: 'id'}
     */
    this.checkCard=function (fields)  
    {  
       // 身份证号码为15位或者18位，15位时全为数字，18位前17位为数字，最后一位是校验位，可能为数字或字符X  
       var reg = /(^\d{15}$)|(^\d{18}$)|(^\d{17}(\d|X|x)$)/;  
       for(var i = 0;i<fields.length; i++){
    	   var param=fields[i];
           var field=$("#"+param.field);
           if(reg.test(field.val()) === false)  
           return  {status:false,field:param.field};
           
       }
        	   return {status:true};
    } 
}

function ajaxDownload(params,method,url,$){
	
	var form = $('<form></form');
	form.attr('action',url);
	form.attr('method', method);
	for(key in params){
		var input = $('input');
		input.attr('name', key);
		input.val(params[key]);
		form.append(input);
	}
	form.submit();
}
