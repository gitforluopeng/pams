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
    <title>添加用户</title>
    <script src="resources/layui/layui.js"></script>
    <link rel="stylesheet" href="resources/layui/css/layui.css">
    <link rel="stylesheet" href="resources/systemManagement/systemUserManagement/css/user_add.css">
    <link rel="stylesheet" href="resources/systemManagement/systemUserManagement/css/SpryValidationPassword.css">
    <link rel="stylesheet" href="resources/systemManagement/systemUserManagement/css/SpryValidationConfirm.css">
    <link rel="stylesheet" href="resources/systemManagement/systemUserManagement/css/SpryValidationTextField.css">
    <script src="resources/jquery-3.2.1.min.js"></script>
    <script src="resources/CheckUtil.js"></script>
    <script src="resources/md5.js"></script>
    <script src="resources/systemManagement/systemUserManagement/js/user_add.js"></script>
    <script src="resources/systemManagement/systemUserManagement/js/SpryValidationConfirm.js"></script>
    <script src="resources/systemManagement/systemUserManagement/js/SpryValidationPassword.js"></script>
    <script src="resources/systemManagement/systemUserManagement/js/SpryValidationTextField.js"></script>
</head>
<body>
<div id="div-all" class="layui-form" lay-filter="selectdiv">
    <div id="div-form">
        <div style="margin-top: 10px;margin-left: auto;width: 100%;" class="layui-inline">
            <div style="margin-top: 10px;width: 100%;" class="layui-inline">
                <div style="margin-left: 50px;height:30px;width:100px;" class="layui-inline"><span style="color: red">*&nbsp;</span>账&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;号:
                </div>
                <div style="width: 250px;height:50px;margin-left: 10px" class="layui-inline"><span id="sprytextfield1">
                <input id="accountNumber" class="layui-input" onblur="checkUsername()" placeholder="6~14位字符">
                <span class="textfieldRequiredMsg">请填写账号。</span><span
                        class="textfieldMinCharsMsg">账号最少为6位字符。</span><span
                        class="textfieldMaxCharsMsg">账号最多为14位字符。</span></span>
                </div>
                <div style="margin-left: 20px;height:30px;width:100px;" class="layui-inline"><span style="color: red">*&nbsp;</span>姓&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;名:
                </div>
                <div style="width: 250px;height:50px;margin-left: 10px" class="layui-inline"><span id="sprytextfield2">
                <input id="userName" class="layui-input" placeholder="请填写姓名">
                <span class="textfieldRequiredMsg">请填写姓名。</span><span
                        class="textfieldMinCharsMsg">姓名最少为一个字符。</span><span
                        class="textfieldMaxCharsMsg">姓名最多为12个字符。</span></span>
                </div>
            </div>
        </div>
        <div style="margin-top: 10px;margin-left: auto;width: 100%;" class="layui-inline">
            <div style="margin-top: 10px;width: 100%;" class="layui-inline">
                <div style="margin-left: 50px;height:30px;width:100px;" class="layui-inline"><span style="color: red">*&nbsp;</span>用户编号:
                </div>
                <div style="width: 250px;height:50px;margin-left: 10px" class="layui-inline"><span id="sprytextfield3">
            <input id="userCode" class="layui-input" placeholder="请填写用户编号">
            <span class="textfieldRequiredMsg">请填写用户编号。</span><span
                        class="textfieldMinCharsMsg">用户编号最少为一个字符。</span><span
                        class="textfieldMaxCharsMsg">用户编号最少为14个字符。</span></span>
                </div>
                <div style="margin-left: 20px;height:30px;width:100px;" class="layui-inline"><span style="color: red">*&nbsp;</span>手机号码:
                </div>
                <div style="width: 250px;height:50px;margin-left: 10px" class="layui-inline"><span id="sprytextfield4">
            <input id="userPhone" class="layui-input" placeholder="11位手机号码">
            <span class="textfieldRequiredMsg">请填写手机号码。</span><span
                        class="textfieldInvalidFormatMsg">手机号码格式错误。</span><span
                        class="textfieldMinCharsMsg">手机号码格式错误。</span><span
                        class="textfieldMaxCharsMsg">手机号码格式错误。</span></span>
                </div>
            </div>
        </div>
        <div style="margin-top: 10px;margin-left: auto;width: 100%;" class="layui-inline">
            <div style="margin-top: 10px;width: 100%;" class="layui-inline">
                <div style="margin-left: 50px;height:30px;width:100px;" class="layui-inline"><span style="color: red">*&nbsp;</span>邮箱地址:
                </div>
                <div style="width: 250px;height:50px;margin-left: 10px" class="layui-inline"><span id="sprytextfield5">
            <input id="userEmail" class="layui-input" placeholder="请填写正确的邮箱地址">
            <span class="textfieldRequiredMsg">请填写邮箱地址。</span><span
                        class="textfieldInvalidFormatMsg">邮箱地址格式错误。</span><span
                        class="textfieldMinCharsMsg">邮箱地址格式错误。</span><span
                        class="textfieldMaxCharsMsg">已超过最大字符数。</span></span>
                </div>
                <div style="margin-left: 20px;height:30px;width:100px;" class="layui-inline"><span style="color: red">*&nbsp;</span>所属单位:
                </div>
                <div style="width: 250px;height:50px;margin-left: 10px" class="layui-inline">
                    <select id="userUntil" lay-filter="userUntil" class="layui-select">
                        <option value="0">请选择</option>
                    </select>
                </div>
            </div>
        </div>
        <div style="margin-top: 10px;margin-left: auto;width: 100%;" class="layui-inline">
            <div style="margin-top: 25px;width: 100%;" class="layui-inline">
                <div style="margin-left: 50px;height:30px;width:100px;" class="layui-inline"><span style="color: red">*&nbsp;</span>所属部门:
                </div>
                <div style="width: 250px;height:50px;margin-left: 10px" class="layui-inline">
                    <select id="userDept" lay-filter="userDept" class="layui-select">
                        <option value="0">请选择</option>
                    </select>
                </div>
                <div style="margin-left: 20px;height:30px;width:100px;" class="layui-inline"><span style="color: red">*&nbsp;</span>用户类别:
                </div>
                <div style="width: 250px;height:50px;margin-left: 10px" class="layui-inline">
                    <select id="userType" class="layui-select">
                        <option value="0">请选择</option>
                    </select>
                </div>
            </div>
        </div>
        <div style="margin-top: 10px;margin-left: auto;width: 100%;" class="layui-inline">
            <div style="margin-top: 30px;width: 100%;" class="layui-inline">
                <div style="margin-left: 50px;height:30px;width:100px;" class="layui-inline"><font color="red">*&nbsp;</font>登录密码:
                </div>
                <div style="width: 250px;height:50px;margin-left: 10px" class="layui-inline"><span id="sprypassword1">
            <input id="password" type="password" placeholder="6~14位以字母开头的密码" class="layui-input">
            <span class="passwordRequiredMsg">请填写登录密码。</span><span class="passwordMinCharsMsg">应以字母开头,最少6位字符。</span><span
                        class="passwordMaxCharsMsg">应以字母开头,最多14位字符。</span><span class="passwordInvalidStrengthMsg">应以字母开头，可包含数字或特殊字符。</span></span>
                </div>
                <div style="margin-left: 20px;height:30px;width:100px;" class="layui-inline"><font color="red">*&nbsp;</font>确认密码:
                </div>
                <div style="width: 250px;height:50px;margin-left: 10px" class="layui-inline"><span id="spryconfirm1">
              <input id="againPwd" type="password" class="layui-input" placeholder="6~14位以字母开头的密码">
            <span class="confirmRequiredMsg">请确认登录密码。</span><span class="confirmInvalidMsg">两次输入的密码不一致。</span></span>
                </div>
            </div>
        </div>
        <div style="margin-top: 15px;width: 100%;" class="layui-inline">
            <div style="margin-left: 57px;width:100px;" class="layui-inline">备注信息:</div>
            <div style="width: 76%;height:40px;margin-left: 3px;" class="layui-inline">
                <textarea id="remarks" style="height:140px" name="" required placeholder="请输入"
                          class="layui-textarea"></textarea>
            </div>
        </div>
    </div>
    <div id="div-btn" style="width:100%;height:70px;">
        <div style="margin-left: 40%;margin-top: 15px">
            <button id="saveBtn" class="layui-btn layui-btn-normal">保存</button>
            <button id="canBtn" class="layui-btn layui-btn-primary">关闭</button>
        </div>
    </div>
</div>
</body>
<script type="text/javascript">
    var sprytextfield1 = new Spry.Widget.ValidationTextField("sprytextfield1", "none", {
        validateOn: ["blur"],
        minChars: 6,
        maxChars: 14
    });
    var sprytextfield2 = new Spry.Widget.ValidationTextField("sprytextfield2", "none", {
        validateOn: ["blur"],
        minChars: 1,
        maxChars: 12
    });
    var sprytextfield3 = new Spry.Widget.ValidationTextField("sprytextfield3", "none", {
        validateOn: ["blur"],
        minChars: 1,
        maxChars: 14
    });
    var sprytextfield4 = new Spry.Widget.ValidationTextField("sprytextfield4", "integer", {
        minChars: 11,
        maxChars: 11,
        validateOn: ["blur"]
    });
    var sprytextfield5 = new Spry.Widget.ValidationTextField("sprytextfield5", "email", {
        validateOn: ["blur"],
        minChars: 6,
        maxChars: 20
    });
    var sprypassword1 = new Spry.Widget.ValidationPassword("sprypassword1", {
        minChars: 6,
        maxChars: 14,
        validateOn: ["blur"],
        minAlphaChars: 1,
        minNumbers: 0,
        minUpperAlphaChars: 0,
        minSpecialChars: 0,
        maxAlphaChars: 12,
        maxNumbers: 9,
        maxUpperAlphaChars: 12,
        maxSpecialChars: 9
    });
    var spryconfirm1 = new Spry.Widget.ValidationConfirm("spryconfirm1", "password", {validateOn: ["blur"]});
    function checkUsername() {
        var userN = $('#accountNumber').val();
        if (!(/[^\x00-\xff]/g.test(userN))) {
            return true;
        }
        else {
            userN = userN.replace(/[^\w\.\/]/ig, '');
            $('#accountNumber').val(userN);
            layer.msg('账号不能包含汉字', {icon: 2});
        }
    }
    function checkPassWord(str) {
        var patrn = /^[a-zA-Z]{1}([a-zA-Z0-9]|[!@#$%^&*._>-?:<]){5,13}$/;
        if (!patrn.exec(str)) {
            return false
        }
        return true
    }
    function isMobil(s) {
        var patrn = /^[+]{0,1}(\d){1,3}[ ]?([-]?((\d)|[ ]){1,12})+$/;
        if (!patrn.exec(s)) return false
        return true
    }

    function checkIsNumber(str) {
        return str.match(/^[0-9]+$/);
    }
    function checkIsNotNull(str) {
        if (str == null || str == "" || str == undefined) {
            return false;
        }
        else return true;
    }
    function checkEmail(str) {
        return (str.match(/^\w+((-\w+)|(\.\w+))*\@[A-Za-z0-9]+((\.|-)[A-Za-z0-9]+)*\.[A-Za-z0-9]+$/));
    }
    function checkLength(str, minLen, maxLen) {
        if (str == null || str == "" || str == undefined) {
            return false;
        }
        else if (str.length <= minLen || str.length >= maxLen) {
            return false;
        }
        else return true;
    }
    function checkIsSame(str1, str2) {
        return (str1 == str2);
    }
</script>
</html>