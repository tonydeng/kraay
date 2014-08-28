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

            <form:form action="${appPath}/home/build.do" method="POST" modelAttribute="form">
            <div class="right-head">
                <div class="head-text">MySQL配置</div>
                <div class="clr"></div>
            </div>
            <div class="sub-rightbox">
                <div class="sub-left">
                    <div class="sub-content1">
                        <span class="title-span">用户名:</span>
                        <form:input path="mi.user" id="user"/>
                        <span class="err"></span>
                        <span class="backendErr"></span>
                        <div class="clr"></div>
                    </div>
                    <div class="sub-content1">
                        <span class="title-span">密码:</span>
                        <form:input path="mi.password" id="password"/>
                        <div class="clr"></div>
                        <span class="err"></span>
                        <span class="backendErr"></span>
                        <div class="clr"></div>
                    </div>

                    <div class="sub-content1">
                        <span class="title-span">主机名:</span>
                        <form:input path="mi.host" id="host"/>
                        <span class="err"></span>
                        <span class="backendErr"></span>
                        <div class="clr"></div>
                    </div>
                    <div class="sub-content1">
                        <span class="title-span">端口号:</span>
                        <form:input path="mi.port" id="port"/>
                        <span class="err"></span>
                        <span class="backendErr"></span>
                        <div class="clr"></div>
                    </div>
                    <div class="preview-bt">
                        <input type="button" value="连接数据库" onclick="getDatabase();" />
                    </div>
                    <div class="sub-content1">
                        <span class="title-span">Java类的基本包名:</span>
                        <form:input path="packaging" id="packaging"/>
                        <span class="err"></span>
                        <span class="backendErr"></span>
                        <div class="clr"></div>
                    </div>
                     <div class="sub-content1">
                        <span class="title-span">库:</span>
                        <form:select id="database" path="mi.database" onchange="getTables();">
                        </form:select>
                        <span class="err"></span>
                        <span class="backendErr"></span>
                        <div class="clr"></div>
                    </div>
                    <div class="sub-content1">
                        <span class="title-span">表:</span>
                        <form:select id="table" path="table">
                        </form:select>
                        <span class="err"></span>
                        <span class="backendErr"></span>
                        <div class="clr"></div>
                    </div>

                    <div class="preview-bt">
                        <input type="button" value="生成" onclick="build();"/>
                    </div>

                </div>
                <div class="clr"></div>
            </div>
            </form:form>

        </div>

    </div>



    </body>
</html>
