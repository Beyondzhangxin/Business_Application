<%--
  Created by IntelliJ IDEA.
  User: Zhangx
  Date: 2017/5/9
  Time: 10:03
  To change this template use File | Settings | File Templates.
--%>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<%@ page pageEncoding="UTF-8" %>

<%@ page contentType="text/html" language="java" %>
<html>
<head>
    <title>Title</title>
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
<div class="easyui-panel" title="出差申请" align="right" style="width:100%;max-width:400px;padding:30px 60px;">
    <form id="onBusiness" method="post">
        <div style="margin-bottom:20px">
            <input class="easyui-textbox" name="name" style="width:100%" data-options="label:'姓名:',required:true">
        </div>
        <div style="margin-bottom:20px">
            <select class="easyui-combobox" name="dept" label="部门" style="width:100%">
                <option value="bj">北京总部</option>
                <option value="cd">成都软件事业部</option>
                <option value="sh">上海事业部</option>
                <option value="nj">南京事业部</option>
                <option value="wh">佛山事业部</option>
            </select>
        </div>
        <div style="margin-bottom:20px">
            <input class="easyui-datebox" label="Start Date:" labelPosition="left" style="width:100%;">
        </div>
        <div style="margin-bottom:20px">
            <input class="easyui-datebox" label="End Date:" labelPosition="left" style="width:100%;">
        </div>
        <div style="margin-bottom:20px">
            <input class="easyui-textbox" name="message" style="width:100%;height:60px"
                   data-options="label:'出差原因:',multiline:true">
        </div>
        <div style="margin-bottom:20px">
            <select class="easyui-combobox" name="city" label="出差城市" style="width:100%">
                <option value="cd">成都</option>
                <option value="bj">北京</option>
                <option value="sh">上海</option>
                <option value="nj">南京</option>
                <option value="wh">武汉</option>
            </select>
        </div>
    </form>
    <div style="text-align:center;padding:5px 0">
        <a href="javascript:void(0)" class="easyui-linkbutton" onclick="submitForm()" style="width:80px">提交</a>
        <a href="javascript:void(0)" class="easyui-linkbutton" onclick="clearForm()" style="width:80px">重填</a>
    </div>
</div>
<script>
    function submitForm() {
        $('#onBusiness').form('submit');
    }
    function clearForm() {
        $('#onBusiness').form('clear');
    }
</script>
</body>
</html>

