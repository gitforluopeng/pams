<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%
String path = request.getContextPath();
String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path+"/";
%>

<!DOCTYPE html>
<html lang="en">
<head>
	<base href="<%=basePath%>">
    <meta charset="UTF-8">
    <title>简历信息</title>
    <script src="resources/layui/layui.js"></script>
    <link rel="stylesheet" href="resources/layui/css/layui.css">
    <script src="resources/jquery-3.2.1.min.js"></script>
</head>
<style>
    tr{
        text-align: center;
    }
</style>
<body>
<div style="margin-left: 10%;width: 80%;height: 80%;" class="layui-form" lay-filter="selectdiv">
    <div id="table" style="margin-top: 5%;margin-left: 20%;height: 70%;" class="layui-inline">
        <table class="layui-table" border="1">
            <tr >
                <td>姓名：</td>
                <td><input class="layui-input" id="personName"></td>
                <td>性别：</td>
                <td><input class="layui-input" id="sex"></td>
                <td rowspan="6">
                    <p style="text-align: center">图片信息：</p>
                    <div style="border: solid 1px;width: 200px;height: 200px;"><img id="img" height="200" width="300"></div>
                    <p style="text-align: center;margin-top: 20px;">
                        <button id="uploadBtn" class="layui-btn layui-btn-danger">上传图片</button>
                    </p>
                </td>
            </tr>
            <tr >
                <td>民族：</td>
                <td><select class="layui-select" id="nation">
                	<option value="">请选择</option>
                </select>
                </td>
                <td>籍贯：</td>
                <td><input id="address" class="layui-input"></td>
            </tr>
            <tr>
                <td>出生年月：</td>
                <td><input id="birthday" class="layui-input"></td>
                <td>学历：</td>
                <td><input class="layui-input" id="education"></td>
            </tr>
            <tr>
                <td>毕业院校：</td>
                <td><input id="university" class="layui-input"></td>
                <td>专业：</td>
                <td><input class="layui-input" id="professional"></td>
            </tr>
            <tr>
                <td>电话：</td>
                <td><input id="phone" class="layui-input"></td>
                <td>邮箱：</td>
                <td><input class="layui-input" id="email"></td>
            </tr>
            <tr>
                <td>工作年限：</td>
                <td><input id="workYear" class="layui-input"></td>
                <td>面试职位：</td>
                <td><input class="layui-input" id="expectJob"></td>
            </tr>
            <tr>
               <td colspan="5">
                  <p style="text-align: left">专业技能：</p>
                   <textarea id="professionalSkills" class="layui-textarea"></textarea>
               </td>
            </tr>
            <tr>
                <td colspan="5">
                    <p style="text-align: left">工作经历：</p>
                    <textarea id="workExperience" class="layui-textarea"></textarea>
                </td>
            </tr>
            <tr>
                <td colspan="5">
                    <p style="text-align: left">简短的描述你自己：</p>
                    <textarea id="showYourSelf" class="layui-textarea"></textarea>
                </td>
            </tr>
            <tr style="border: 0px;">
                <td colspan="5">
                    <button id="saveBtn" class="layui-btn">保存</button>
                    <button id="closeBtn" class="layui-btn">关闭</button>
                </td>
            </tr>
        </table>
    </div>
<input style="display:none" id="chatUploadId">
</div>
</body>
<script>
    layui.use(['form','upload','laydate'],function(){
        var form = layui.form;
        var upload = layui.upload;
        var laydate = layui.laydate;
        
        laydate.render({
        	elem: '#birthday',
        	type: 'month'
        })
        
        var saveBtn = $('#saveBtn');
        var closeBtn = $('#closeBtn');
        var chatUploadId = $('#chatUploadId');
        var img = $('#img');
        
        var personName = $('#personName');
        var sex = $('#sex');
        var address = $('#address');
        var birthday = $('#birthday');
        var university = $('#university');
        var professional = $('#professional');
        var nation = $('#nation');
        var phone = $('#phone');
        var workYear = $('#workYear');
        var expectJob = $('#expectJob');
        var professionalSkills = $('#professionalSkills');
        var workExperience = $('#workExperience');
        var showYourSelf = $('#showYourSelf');
        var education = $('#education');
        var email = $('#email');
        

        init();
        function init(){
        	init_closeBtn();
        	init_saveBtn();
			init_upload();
			init_select();
        }
        
        function init_select(){
        	$.ajax({
        		type: 'get',
        		url: 'personResumeManage/load_all_nations',
        		success: function (data){
        		console.info(data);
        			var nations = data["nations"];
        			console.info(nations);
        			for(var i = 0; i < nations.length; i++){
        				var nationInfo = nations[i];
        				console.info(nationInfo.nationName);
						var option = $('<option></option>');
						option.attr("value", nationInfo.nationName);
						option.html(nationInfo.nationName);
						$('#nation').append(option);
						}
						form.render('select', 'selectdiv');
        		},
        		error: function(){
        			layer.msg("民族信息加载失败！请重试。。。",{icon : 2});
        		}
        	})
        }
        
         function init_saveBtn(){
        	saveBtn.on('click',function(){
        		if(chatUploadId.val() == "" || chatUploadId.val() == null){layer.msg("请上传图片！",{icon : 2});return;}
        		if(!((checkIsNotNull(personName.val()))&&checkIsNotNull(sex.val()))&&checkIsNotNull(address.val())&&checkIsNotNull(birthday.val())&&checkIsNotNull(university.val())
        		&&checkIsNotNull(professional.val())&&checkIsNotNull(nation.val())&&checkIsNotNull(phone.val())&&checkIsNotNull(workYear.val())&&checkIsNotNull(expectJob.val())
        		&&checkIsNotNull(professionalSkills.val())&&checkIsNotNull(workExperience.val())&&checkIsNotNull(showYourSelf.val())){
        			layer.msg("请将信息填写完整！",{icon : 2});return;
        		}
        		if(nation.val() == ""){
        			layer.msg("请选择民族！",{icon : 2});return;
        		}
        		if(!(checkIsNumber(phone.val()))){
        			layer.msg("请输入正确的电话号码！",{icon : 2});return;
        		}
        		$.ajax({
        			type: 'get',
        			url: 'personResumeManage/add_personResume',
        			data: {
        				"personResume.chatUploadId": chatUploadId.val(),
        				"personResume.personName": personName.val(),
        				"personResume.sex": sex.val(),
        				"personResume.nation": nation.val(),
        				"personResume.address": address.val(),
        				"personResume.birthday": birthday.val(),
        				"personResume.education": education.val(),
        				"personResume.professional": professional.val(),
        				"personResume.university": university.val(),
        				"personResume.expectJob": expectJob.val(),
        				"personResume.professionalSkills": professionalSkills.val(),
        				"personResume.workYear": workYear.val(),
        				"personResume.phone": phone.val(),
        				"personResume.email": email.val(),
        				"personResume.workExperience": workExperience.val(),
        				"personResume.showYourSelf": showYourSelf.val()
        			},
        			success: function (data){
        				if(data.status == 1){
        					var index = parent.layer.getFrameIndex(window.name);
							parent.layer.close(index);
							parent.layer.msg("保存成功！",{icon : 1});
        				} else if(data.status == -1){
        					layer.msg("保存失败！请稍后再试。。。");
        				} else if(data.status == 0){
        					layer.msg("已存在该简历！",{icon : 2});
        				}
        			},
        			error: function (){
        				layer.msg("保存失败！请联系管理员。。。",{icon : 2});
        			}
        		})
        	})
        }
        
        function init_upload(){
        	upload.render({
        		elem: '#uploadBtn',
        		url: 'common/upload_file',
        		done: function (data){
        			if(data.status == "success"){
        				layer.msg("上传成功！",{icon : 1});
        				console.info(data.file_info);
        				chatUploadId.val(data.file_info.id);
        				var url = "/"+data.file_info.filePath;
        				img.attr("src","resources/fileSave"+url);
        			} else {
        				layer.msg("上传失败！error:"+data.info,{icon : 2});
        			}
        		}
        	})
        }
        
        
        function init_closeBtn(){
        	closeBtn.on('click',function(){
        		var index = parent.layer.getFrameIndex(window.name);
				parent.layer.close(index);
        	})
        }
        
        function checkIsNotNull(str) {
        if (str == null || str == "" || str == undefined) {
            return false;
        }
        else return true;
    }
    function checkIsNumber(str) {
        return str.match(/^[0-9]+$/);
    }
       
    })
</script>
</html>