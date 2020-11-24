<%--<%@ page contentType="text/html;charset=UTF-8" pageEncoding="UTF-8" %>--%>
<%--<!DOCTYPE html>--%>
<%--<html>--%>
<%--<head>--%>
<%--    <meta http-equiv="Content-type" content="text/html; charset=UTF-8">--%>
<%--    <meta http-equiv="X-UA-Compatible" content="IE=edge,chrome=1"/>--%>
<%--    <title>单文件上传</title>--%>
<%--</head>--%>
<%--<body>--%>

<%--<form method="post" action="upload" enctype="multipart/form-data">--%>
<%--    <input type="file" name="file"><br>--%>
<%--    <input type="submit" value="提交">--%>
<%--</form>--%>
</body>
</html>

<%@ page language="java" contentType="text/html; charset=UTF-8"
         pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>

<head>
    <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
    <title>单文件上传</title>
    <style type="text/css">
        /*表单样式*/
        *{
            padding: 0;
            margin: 0;
        }
        .box {
            width: 700px;
            height: 400px;
            position: absolute;
            left: 50%;
            top: 50%;
            transform:translate(-50%,-50%);/*关键代码*/
        }

    </style>
</head>
<body>


<div class="box">
    <form method="post" action="upload" enctype="multipart/form-data">
        <div class="custom-file" style="width: 70%">
            <input type="file" name="file">
            <input type="submit" value="提交">
        </div>
    </form>
</div>


</body>
</html>
