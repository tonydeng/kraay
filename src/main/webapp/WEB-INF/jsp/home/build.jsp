<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" import="java.util.Properties,com.duoqu.commons.web.spring.SpringContextHolder"%>
<%@ include file="/WEB-INF/jsp/share/tag_head.jsp" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
	<head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
    	<title>Kraay</title>
    	<%@ include file="/WEB-INF/jsp/share/static.jsp" %>
  	</head>
    <div class="wrapper">

        <div class="header">
            <p class="head-left">Kraay 代码生成器 -- MyBatis代码生成</p>
            <p class="head-right">
                <span class="date"></span>
            </p>
            <div class="clr"></div>
        </div>

        <div class="main-box">

            <h1>Build Success!</h1>
            <a href="${downloadUrl}">${filename}</a>
        </div>

    </div>

    </body>
</html>
