function utils($) {
	var $=$;

	/**
	 * Task:数据表格导出Excel
	 * Instruction：依赖JQuery
	 * @param cols  数组类型   格式['序号','姓名','地址']
	 * @Param colsFiled     格式{'序号':'id','姓名':'name','地址':'address'}
	 * @param data  数组类型   格式[{'id':1,'name':张三,'address':成都},{'id':2,'name':李四,'address':成都}]
	 * @returns
	 */
	this.createExcle=function (cols,colsFiled,data){

		var table = $('<table></table>');
		var th = $('<tr></tr>');

		var index = 0;

		for(var i = 0; i < data.length; i++){

			var tr = $('<tr></tr>');
			var values = data[i];

			for(var j = 0; j < cols.length; j++){

				var fliedName = cols[j];
				var flied = colsFiled[fliedName];
				var value = values[flied];
				var td = $('<td></td>');
				td.html(value);

				if(index == 0){
					var tdTh = $('<td></td>');
					tdTh.html(fliedName);
					th.append(tdTh);
				}

				tr.append(td);
			}
			if(index == 0){
				table.append(th);
			}
			table.append(tr);
			index++;
		}
		table.attr('id','datatab');
		table.attr("style", "display: none");
		table.appendTo('body');
		method5('datatab');
		datatab.remove();
		function method5(tableid) {  

			var idTmr;

			var tableToExcel = (function() {  
				var uri = 'data:application/vnd.ms-excel;base64,',  
				template = '<html><head><meta charset="UTF-8"></head><body><table>{table}</table></body></html>',  
				base64 = function(s) { return window.btoa(unescape(encodeURIComponent(s))) },  
				format = function(s, c) {  
					return s.replace(/{(\w+)}/g,  
							function(m, p) { return c[p]; }) }  
				return function(table, name) {  
					if (!table.nodeType) table = document.getElementById(table)  
					var ctx = {worksheet: name || 'Worksheet', table: table.innerHTML}  
					window.location.href = uri + base64(format(template, ctx))  
				}  
			})()

			if(getExplorer()=='ie')  
			{  
				var curTbl = document.getElementById(tableid);  
				var oXL = new ActiveXObject("Excel.Application");  
				var oWB = oXL.Workbooks.Add();  
				var xlsheet = oWB.Worksheets(1);  
				var sel = document.body.createTextRange();  
				sel.moveToElementText(curTbl);  
				sel.select();  
				sel.execCommand("Copy");  
				xlsheet.Paste();  
				oXL.Visible = true;  

				try {  
					var fname = oXL.Application.GetSaveAsFilename("Excel.xls", "Excel Spreadsheets (*.xls), *.xls");  
				} catch (e) {  
					print("Nested catch caught " + e);  
				} finally {  
					oWB.SaveAs(fname);  
					oWB.Close(savechanges = false);  
					oXL.Quit();  
					oXL = null;  
					idTmr = window.setInterval("Cleanup();", 1);  
				}  

			}  
			else  
			{  
				tableToExcel(tableid)  
			}

			function Cleanup() {  
				window.clearInterval(idTmr);  
				CollectGarbage();  
			}

			function  getExplorer() {  
				var explorer = window.navigator.userAgent ;  
				//ie  
				if (explorer.indexOf("MSIE") >= 0) {  
					return 'ie';  
				}  
				//firefox  
				else if (explorer.indexOf("Firefox") >= 0) {  
					return 'Firefox';  
				}  
				//Chrome  
				else if(explorer.indexOf("Chrome") >= 0){  
					return 'Chrome';  
				}  
				//Opera  
				else if(explorer.indexOf("Opera") >= 0){  
					return 'Opera';  
				}  
				//Safari  
				else if(explorer.indexOf("Safari") >= 0){  
					return 'Safari';  
				}  
			}
		} 
	}
	
	
	/**
     *Task:下载控件
     *Instruction：强依赖于Layui，依赖JQuery（Layui本身就依赖JQuery，因此引入Layui，就不用引入JQuery）；
     * @param layui   layui
     * @param cols    设置表头                   二位数组类型
     * @param loadtaburl     载入表格数据的请求路径      String类型
     * @param method  请求方式，post或者get       String类型
     * @param where   请求数据的额外参数          key:value类型
     * @param done    表格渲染完的回调            function类型  格式：function(res, curr, count){}
     * @param page    是否开启分页，true或者false boolean类型
     * @param limit   每页默认显示的数量          int类型
     * @param limits  每页数据量可选项            数组类型
     * @param loading 是否显示加载条              boolean类型
     * @param tabid   设定容器唯一ID              String类型
     * @param even    是否开启隔行背景            boolean类型
     * @param downloadfilehandler 下载文件的处理函数  function类型
     * @param deletefilehandler   删除文件的处理函数  function类型
     * @returns       文件下载控件
     */

    this.opendownloadpage=function(layui,cols,loadtaburl,method,where,done,page,limit,limits,loading,tabid,even,downloadfilehandler,deletefilehandler) {
        var layui=layui;
        var url=loadtaburl;
        var method=method;
        var where=where;
        var done=done;
        var page=page;
        var limit=limit;
        var limits=limits;
        var loading=loading;
        var tabid=tabid;
        var even=even;
        var downloadfilehandler=downloadfilehandler;
        var deletefilehandler=deletefilehandler;
        var index=null;
        layui.use('layer', function () {
            var layer = layui.layer;
            var downloadTemp = '<div class="downloadfiles">' +
                '<div class="download-container" style="width: 800px;margin: 0 auto;">' +
                '<table id="downloadtab" class="layui-table" style="width: 98%;margin: 0 auto;">' +
                '</table>' +
                '</div>' +
                '<div class="dowmload-footer" style="width: 800px;margin: 0 auto;">' +
                '<div class="layui-input-block" style="float: right;margin-top: 10px;margin-bottom: 10px">' +
                '<button id="downloadfileBtn" class="layui-btn layui-btn-normal"  lay-filter="">下载</button>' +
                '<button id="deletefileBtn" class="layui-btn layui-btn-normal"  lay-filter="">删除</button>' +
                '<button id="closethispageBtn" class="layui-btn layui-btn-primary"  lay-filter="">关闭</button>' +
                '</div>' +
                '</div>' +
                '</div>'
            index=layer.open({
                type: 1,
                title: '下载',
                maxmin:true,
                shadeClose: false,
                shade: 0.8,
                area: ['72%', '62%'],
                offset: ['0px', '150px'],
                closeBtn:1,
                content: downloadTemp
            });
        });
        layui.use(['table','layer','form'], function () {
            var table = layui.table;
            var layer = layui.layer;
            var closethispageBtn = $('#closethispageBtn');
            var downloadfileBtn=$('#downloadfileBtn');
            var deletefileBtn=$('#deletefileBtn');
            init();
            function init() {
                init_table();
                closethispage();
                downloadfile();
                deletefile();
            }
            function downloadfile(){
                downloadfileBtn.on('click', function () {
                    downloadfilehandler();
                });
            }
            function deletefile(){
                deletefileBtn.on('click', function () {
                    deletefilehandler();
                })
            }
            function init_table() {
                table.render({
                    elem: '#downloadtab'
                    ,cols:cols
                    ,height:'full-410'
                    ,even:even
                    ,url:url
                    ,method:method
                    ,where:where
                    ,done:done
                    ,page:page
                    ,limit:limit
                    ,limits:limits
                    ,loading:loading
                    ,id:tabid
                });
            }
            function closethispage() {
                closethispageBtn.on('click', function () {
                    layer.close(index);
                    index=null;
                });
            }
        });
    }
    
    

	this.uploadServer=function uploadServer(params){
		
		var me = this;
		layui.use(['upload','element', 'layer'], function (){
			var $dom = params['$dom'];
			var uploadURL = params['uploadURL'];
			var uploadProgressURL = params['uploadProgressURL'];
			var data = params['data'] || {};
			var $=layui.$;
			var btu=$dom;
			var upload=null;
			var layer = null;
			var element = null;
			upload = layui.upload;
			layer = layui.layer;
			element = layui.element;
			var context = null;
			var divContext = null;
			var tableContext = null;
			var uploadIndex = 0;
			var loading=null;
			var uploadfiles=null;
			var uploadService=null;
			var uploadProgress=null;
			var useTime=null;
			var speed_k=null;
			var remainTime=null;
			var progressPercent=null;
			var uploadStatus=null;
			var usetime=null;
			var speed=null;
			var remaintime=null;
			var percent=null;
			var parentContext = null;
			var mask = null;
			var nowProgress = null;
			var chooseFileBtn=null;
			var uploadFileBtn=null;
			var clearBtn=null;
			var closeBtn=null;
			var uploadServerOption = null;
			var currentUploadIndex = 0;
			var contextTemp = '<style type="text/css">#tableContext th{background: #c9c9c9;}</style><div id="divContext" style="width: 100%;height: 85%;overflow:auto;">'+
			'<table id="tableContext" class="layui-table" style="width: 100%">'+
			'<th>文件名</th><th>类型</th><th>大小</th><th>操作</th></table></div><hr>'+
			'<div class="layui-inline" style="width: 30%;">进度:&nbsp;<div class="layui-inline" id="percent"></div></div>'+
			'<div class="layui-inline" style="width: 50%;margin-left: 10%">'+
			'<div class="layui-inline">已用时间:&nbsp;<div class="layui-inline" id="usetime"></div></div>'+
			'<div class="layui-inline" style="margin-left: 10px">速度:&nbsp;<div class="layui-inline" id="speed"></div></div>'+
			'<div class="layui-inline" style="margin-left: 10px">剩余时间:&nbsp;<div class="layui-inline" id="remaintime"></div></div>'+
			'</div>'+
			'<div class="layui-progress" style="margin-top: 10px" lay-filter="progress">'+
			'<div class="layui-progress-bar layui-bg-cyan" lay-percent="0%" ></div>'+
			'</div>';
			var trTemp='<tr><td id="fileName"></td>'+
			'<td id="fileType"></td>'+
			'<td id="fileSize"></td>'+
			'<td id="upload">'+
			'<button class="layui-btn layui-btn-small layui-btn-danger" upload="upload">移除</button>'+
			'</td></tr>';
			
			init();
			
			function init(){
				init_click();
				init_upload();
			}

			function init_upload(){
				uploadService=upload.render({
					elem: '#btn0',
					data: data,
					url:uploadURL,
					auto: false,
					accept:'file',
					size:'20480',
					multiple:true,
					exts:'doc|docx|xls|xlsx|pdf',
					choose: function(obj){
						chooseFile(obj);
					},
					before: function(obj){
						chooseFileBefore(obj);
					}
					,done: function(res){
						
					}
					,error: function(){
						mask.close();
						layer.msg("上传失败!!");
					}
				});
			}

			function saveFileInfo(){

				console.info("文件名:"+uploadfiles.name+"  文件类型:"+uploadfiles.type+"  文件大小"+
						uploadfiles.size+""
				)
			}

			function chooseFileBefore(obj){
				var files = obj.pushFile();
				if(files.length==0){
					layer.msg("请先添加数据!");
					return;
				}else{
					$(chooseFileBtn).hide();
					$(uploadFileBtn).hide();
					$(clearBtn).hide();
					mask = new Mask(parentContext, $);
					nowProgress = setInterval(getProgress, 500);//每隔一秒执行请求
				}
			}

			function chooseFile(obj){
				uploadServerOption = obj;
				var files = obj.pushFile();
				uploadfiles=files;
				var fileChanged = null;
				obj.preview(function(index, file, result){

					fileChanged = file;
					var pervit = $(trTemp);
					pervit.attr("id",index);
					var name=fileChanged.name;
					var size=null;
					var fileName = getChildForId('fileName',pervit);
					var fileType = getChildForId('fileType',pervit);
					var fileSize = getChildForId('fileSize',pervit);
					var uploadBtn = getChildForId('upload', pervit);
					uploadBtn = getChildUploadBtn(uploadBtn);
					uploadBtn.attr("value", index);
					uploadBtn.attr('index', uploadIndex);
					uploadBtn.on('click', function (){
						var index = $(this).attr('value');
						var uploadIndex = $(this).attr('index')+"uploadFile";
						$('#'+uploadIndex).remove();
						obj.removeFile(index);
					})

					if(file.size/1024 > 1 && file.size/1024<1024){size=(file.size/1024).toFixed(2)+"kb";}
					else {size=(file.size/1024/1024).toFixed(2)+"mb";}
					fileName.html(file.name);
					fileType.html(file.type);
					fileSize.html(size);
					pervit.attr('id',(uploadIndex++)+"uploadFile")
					tableContext.append(pervit);
				});
			}

			function init_click(){
				console.info($dom)
				if($dom != null)
					btu.on('click',openUploadView);
			}

			function openUploadView(){
				layer.open({
					type: 1,
					title: '附件',
					shadeClose: false,
					shade:0.8,
					resiza: false,
					maxmin: true, //开启最大化最小化按钮
					area: ['800px', '600px'],
					offset: ['20px', '30%'],
					content: contextTemp
					,btn: ['添加文件', '开始上传','清空','关闭']
				,yes: function(index, layero){
					console.info(layero+"我的天");
				}
				,btn2: function(index, layero){
					return false;
				}
				,btn3: function(index, layero){
					return false;
				}
				,btn4: function(index, layero){
					return false;
				}
				,cancel: function(){
					clearAll();
				}
				,success: function(layero, index){
					init_View(layero, index);
					
				}
				});
			}

			function init_View(layero, index){
				var document = layero.context;
				chooseFileBtn = document.getElementsByClassName("layui-layer-btn0");
				uploadFileBtn=document.getElementsByClassName("layui-layer-btn1");
				clearBtn=document.getElementsByClassName("layui-layer-btn2");
				closeBtn=document.getElementsByClassName("layui-layer-btn3");
				var div = $(document.getElementById('divContext'));
				parentContext = $(div.parent());
				$(chooseFileBtn).attr('id', 'btn0');
				$(uploadFileBtn).attr('id','btn1');
				$(clearBtn).attr('id','btn2');
				$(closeBtn).attr('id','btn3');
				usetime=$(document.getElementById("usetime"));
				speed=$(document.getElementById("speed"));
				remaintime=$(document.getElementById("remaintime"));
				percent=$(document.getElementById("percent"));
				$('#btn3').on('click',function(){
					clearAll();
				})
				$('#btn2').on('click',function(){
					clearAll();
				})
				$('#btn1').on('click',function(){
					var isEmty=false;
					for(key in uploadfiles)
					{
						isEmty = true;
					}
					if(uploadfiles==null || !isEmty)
					{
						layer.msg("请先添加文件!");
						return;
					}
					currentUploadIndex = 0;
					uploadService.upload();
				})
				init_upload();
				context = $(layero.context);
				divContext = $(document.getElementById("divContext"));
				tableContext=$(document.getElementById("tableContext"));
				element.init();
			}

			function getChildForId(id,dom){

				var childs = dom.children();
				for(var i = 0; i < childs.length; i++){
					var child = $(childs[i]);
					if(child.attr('id') == id){
						return child;
					}
				}
				return null;
			}

			function getChildUploadBtn(dom){

				var childs = dom.children();
				for(var i = 0; i < childs.length; i++){
					var child = $(childs[i]);
					if(child.attr('upload') == "upload"){
						return child;
					}
				}
				return null;
			}
			function clearAll(){
				percent.html("");
				usetime.html("");
				speed.html("");
				remaintime.html("");
				element.progress('progress', '0%');
				$("#tableContext  tr:not(:first)").html("");
				if(uploadServerOption!=null)
					uploadServerOption.removeAllFile();
				uploadfiles = null;
			}

			function getProgress(){//设置每秒请求获得上传信
				var fileLength = 0;
				for(key in uploadfiles){
					fileLength++;
				}
				$.ajax({
					type:"get",
					url:uploadProgressURL,
					success:function(data){
						uploadStatus=data.uploadStatus;
						if(uploadStatus == 0){
							currentUploadIndex++;
							console.info(currentUploadIndex);
						}
						if(uploadStatus == 0 /*&& (currentUploadIndex == fileLength)*/){
							window.clearInterval(nowProgress);
							mask.close();
							layer.msg("上传成功!!");
							$(chooseFileBtn).show();
							$(uploadFileBtn).show();
							$(clearBtn).show();
							return;
						}else{
							uploadProgress = data["uploadProgress"];
							useTime = uploadProgress.userTime;
							speed_k = uploadProgress.speed_k;
							remainTime = uploadProgress.remainTime;
							progressPercent = uploadProgress.percent;
							var speedb = null;
							var uTime=null;
							var rTime=null;
							if(speed_k>1024){
								speedb = (speed_k/1024).toFixed(2)+"&nbsp;mb/s";
							}else{
								speedb = speed_k+"&nbsp;kb/s";
							}
							if(useTime>60){
								uTime=(useTime/60).toFixed(2)+"&nbsp;min";
							}else{
								uTime=useTime+"&nbsp;s"
							}
							if(remainTime>60){
								rTime=(remainTime/60).toFixed(2)+"&nbsp;min";
							}else{
								rTime=remainTime+"&nbsp;s"
							}
							percent.html(progressPercent+"%");
							usetime.html(uTime);
							speed.html(speedb);
							remaintime.html(rTime);
							element.progress('progress', progressPercent+'%');
						}
					},
					error:function(){
						layer.msg("上传错误!!");
						clearAll();
						window.clearInterval(nowProgress);
					}
				});
			}

			function Mask($parentDom, $){

				var parent = $parentDom;
				var top = parent.height() / 2 - 30;
				var left = parent.width() / 2 - 30;
				console.info(parent);
				var me = this;
				var divDom = $('<div></div>');
				var style = "position: absolute; top: 0; " +
				"background-color:rgba(255, 255, 255, 0.5);" +
				" width: 100%; height: 100%";
				divDom.attr("style",style);

				var icon = $('<i></i>');
				icon.attr('class', 'layui-icon layui-anim layui-anim-rotate layui-anim-loop');
				icon.attr('style', 'position: relative;font-size: 60px; color: #1E9FFF; margin: 0 auto; z-index: 999999');
				icon.html('&#xe63e;');
				icon.css('top', top+"px");
				icon.css('left', left+"px");
				divDom.append(icon);
				parent.append(divDom);
				me.close = function (){
					divDom.remove();
				}

				return me;
			}

			me.openView=openUploadView;
		})
	}
	
	this.ajaxDownload=function(params,method,url,$){
    	
    	var dowform = document.createElement("form");
    	dowform.setAttribute('action',url);
    	dowform.setAttribute('method', method);
    	for(key in params){
    		console.info(dowform.innerHTML);
    		var input = document.createElement("input");
    		input.setAttribute('name', key);
    		input.value = params[key];
    		dowform.appendChild(input);
    	}
    	dowform.setAttribute("style", "display: none");
    	$(dowform).appendTo('body');
    	dowform.submit();
    }

}

function DateFormat(fmt) {
	
	this.fmt = fmt;
	
	this.Format = function (date){
		var o = {
				"M+": date.getMonth() + 1, //月份 
				"d+": date.getDate(), //日 
				"h+": date.getHours(), //小时 
				"m+": date.getMinutes(), //分 
				"s+": date.getSeconds(), //秒 
				"q+": Math.floor((date.getMonth() + 3) / 3), //季度 
				"S": date.getMilliseconds() //毫秒 
		};
		if (/(y+)/.test(this.fmt)) this.fmt = this.fmt.replace(RegExp.$1, (date.getFullYear() + "").substr(4 - RegExp.$1.length));
		for (var k in o)
			if (new RegExp("(" + k + ")").test(this.fmt)) this.fmt = this.fmt.replace(RegExp.$1, (RegExp.$1.length == 1) ? (o[k]) : (("00" + o[k]).substr(("" + o[k]).length)));
		return this.fmt;
	}
}