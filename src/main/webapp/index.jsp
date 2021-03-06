<%@ page pageEncoding="UTF-8" %>
<html>
<head>
    <meta charset="utf-8">
    <title>航天智慧工作流管理系统</title>
    <link rel="stylesheet" type="text/css" href="themes/default/easyui.css">
    <link rel="stylesheet" type="text/css" href="themes/icon.css">
    <link rel="icon" href="themes/icons/casic.ico" type="image/x-icon" />
    <script type="text/javascript" src="js/jquery.min.js"></script>
    <script type="text/javascript" src="js/jquery.easyui.min.js"></script>
    <script type="text/javascript">
        function login() {
            var username = $("#j_username").val();
            var passwd = $("#j_password").val();
            $("#msg").html("");
            if (username == "") {
                $("#msg").html("用户名为空");
                return;
            }
            if (passwd == "") {
                $("#msg").html("密码为空");
                return;
            }

            $.ajax({
                url: "login.do",
                type: "post",
                data: {
                    username: username,
                    password: passwd
                },

                dataType: "json",
                error: function () {
                    $("#msg").html("登录失败");
                },
                success: function (result) {
                    if (result.status == 0) {
                        window.location.href = 'logon.do';
                    } else if(result.status==1){
                        $("#msg").html("密码不对");
                    } else {
                        $("#msg").html("用户不存在");
                    }
                }
            });
        }

        function movePanel() {

            var left = document.body.clientWidth / 2 - 200;
            var top = document.body.clientHeight / 2 - 120;
            if (left < 0)
                left = 0;
            if (top < 0)
                top = 0;
            $('#loginPanel').panel('move', {
                left: left,
                top: top
            });
        }
        $("#j_username,#j_password").on('keyup', function (event) {
            if (event.keyCode == 13) {
                login();
            }
        });
    </script>
</head>
<body onload="movePanel()" onresize="movePanel()">
<div class="easyui-panel" id="loginPanel"
     data-options="iconCls:'icon-login',style:{position:'absolute'}"
     title="航天智慧工作流程管理系统"
     style="width: 100%; max-width: 400px; max-height: 300px; padding: 30px 60px; fit: false">
    <div style="margin-bottom: 10px">
        <input class="easyui-textbox" id="j_username" name="j_username"
               style="width: 100%; height: 40px; padding: 12px"
               data-options="label:'用户名',prompt:'用户名',iconCls:'icon-man',iconWidth:38">
    </div>
    <div style="margin-bottom: 10px">
        <input class="easyui-textbox" id="j_password" name="j_password"
               type="password" style="width: 100%; height: 40px; padding: 12px"
               data-options="label:'密码',iconCls:'icon-lock',iconWidth:38">
    </div>
    <div style="margin-bottom: 10px">
        <span id="msg"></span>
    </div>
    <div>
        <a href="javascript:login()" class="easyui-linkbutton"
           data-options="iconCls:'icon-ok'"
           style="padding: 5px 0px; width: 100%;"> <span
                style="font-size: 14px;">登录</span>
        </a>
    </div>
</div>
</body>

</html>
