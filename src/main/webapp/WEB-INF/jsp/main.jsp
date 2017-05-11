<%--
  Created by IntelliJ IDEA.
  User: Zhangx
  Date: 2017/5/8
  Time: 11:23
  To change this template use File | Settings | File Templates.
--%>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<%@ page pageEncoding="UTF-8" %>
<html>
<head>
    <title>主页</title>
    <meta charset="utf-8">
    <link rel="stylesheet" type="text/css" href="/themes/default/easyui.css">
    <link rel="stylesheet" type="text/css" href="/themes/icon.css">
    <link rel="stylesheet" type="text/css" href="/themes/cms.css">
    <link rel="stylesheet" type="text/css" href="/css/common.css">
    <script type="text/javascript" src="/js/jquery.min.js"></script>
    <script type="text/javascript" src="/js/jquery.easyui.min.js"></script>
    <script type="text/javascript" src="/js/easyui-lang-zh_CN.js"></script>

</head>
<body>
<!--主面板，包含东西南北四个panel-->
<div id="main" class="easyui-layout" fit="true">
    <div region="north" style="height: 60px;background-color: #3c8b3c;text-align: center;vertical-align: middle">
        <h1>航天科工智慧产业发展有限公司工作流程管理系统</h1></div>
    <div region="west" title="导航" id="west" iconCls="" collapsible="true"
         split="false" style="width:100px;">
        <div id="aa" class="easyui-accordion" style="width:100%">
            <div title="工作流程" data-options="iconCls:'icon-workflow'">
                <a title="/html/流程申请.html">流程申请</a><br />
                <a title="/html/待办流程表.html">待办流程</a><br />
                <a title="/html/已办流程表.html">已办流程</a><br />
                <a title="/html/已申请流程表.html">已申请流程</a><br />
                <a title="/html/流程草稿.html">流程草稿</a>
            </div>
            <div title="用户管理" data-options="iconCls:'icon-user'"></div>
        </div>
    </div>
    <div region="center" class="easyui-tabs" split="false" id="mainTab">
    </div>
    <div data-options="region:'south',split:true" style="height:50px;"><p align="center" width="100%">Copyright
        ©2014-2017 航天科工智慧产业发展有限公司</p></div>
</div>
<script>
    $(function () {
        $('a').click(function () {
            var title = $(this).html();
            var href = $(this).attr('title');
            if ($('#mainTab').tabs('exists', title)) {
                $('#mainTab').tabs('select', title);
            } else {
                $('#mainTab').tabs('add', {
                    title: title,
                    closable: true,
                    href: href
                });
            }
        });
    });

</script>

</body>
</html>
