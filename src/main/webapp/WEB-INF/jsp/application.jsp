<%--
  Created by IntelliJ IDEA.
  User: Zhangx
  Date: 2017/5/8
  Time: 18:01
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
<table align="center" width="100%">
    <tr align="center">
        <td>
            <div id="flow1" class="easyui-panel" title="项目流程"
                 style="width: 500px; height: 200px;padding-top: 10px">
                <ul>
                    <li><a>项目合同审批
                    </a></li>
                </ul>
            </div>
        </td>
        <td>
            <div id="flow2" class="easyui-panel" title="行政流程" style="width: 500px; height: 200px;padding-bottom: 10px">
                <ul>
                    <li><a>出差申请</a></li>
                </ul>
            </div>
        </td>
    </tr>
    <tr style="height: 20px"></tr>
    <tr align="center">
        <td>
            <div id="flow3" class="easyui-panel" title="人事流程" style="width: 500px; height: 200px;padding: 10px">
                <ul>
                    <li><a>年假申请</a></li>
                </ul>
            </div>
        </td>
        <td>
            <div id="flow4" class="easyui-panel" title="财务流程" style="width:500px; height: 200px;padding: 10px">
                <ul>
                    <li><a>报销申请</a></li>
                </ul>
            </div>
        </td>
    </tr>

</table>
</body>
</html>
