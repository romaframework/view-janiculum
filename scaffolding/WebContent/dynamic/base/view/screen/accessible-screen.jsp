<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>    
<%@ taglib uri="/WEB-INF/roma.tld" prefix="roma"%>
<%@ page buffer="none" %>
<%@ page import="org.romaframework.core.Roma"%>
<%@ page import="org.romaframework.core.config.ApplicationConfiguration"%>
<%@page import="org.romaframework.aspect.view.html.screen.HtmlViewScreen"%>
<%
response.setHeader("Cache-Control","no-cache"); //HTTP 1.1
response.setHeader("Pragma","no-cache"); //HTTP 1.0
response.setDateHeader ("Expires", 0); //prevents caching at the proxy server

String appName = Roma.component(ApplicationConfiguration.class).getApplicationName();
((HtmlViewScreen)Roma.view().getScreen()).setRenderSet("accessible");
%>

<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Strict//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-strict.dtd"> 
<html>
<head>
<link rel="stylesheet" type="text/css" href="<%=request.getContextPath() %>/static/base/css/style-sorter.css" />
<link rel="stylesheet" type="text/css" href="<%=request.getContextPath() %>/static/base/css/ui.datepicker.css" />
<link rel="stylesheet" type="text/css" href="<%=request.getContextPath() %>/static/base/css/uitabs.css" />
<link rel="stylesheet" type="text/css" href="<%=request.getContextPath() %>/static/base/css/style.css" />
<link rel="stylesheet" type="text/css" href="<%=request.getContextPath() %>/static/base/css/application-style.css" />
<!-- ADDITIONAL CSS -->
<link rel="icon" href="<%=request.getContextPath() %>/static/images/favicon.ico" />
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
<title><%=appName%></title>
<script type="text/javascript" src="<%=request.getContextPath() %>/static/base/js/jquery.js"></script>
<script type="text/javascript" src="<%=request.getContextPath() %>/static/base/js/jquery.timeentry.js"></script>
<script type="text/javascript" src="<%=request.getContextPath() %>/static/base/js/jquery-ui.js"></script>
<script type="text/javascript" src="<%=request.getContextPath() %>/static/base/js/jquerycssmenu.js"></script>  
<script type="text/javascript" src="<%=request.getContextPath() %>/static/base/js/jquery.tablesorter.min.js"></script>
<script type="text/javascript" src="<%=request.getContextPath() %>/static/base/js/jqDnR.js"></script>
<script type="text/javascript" src="<%=request.getContextPath() %>/static/base/js/romaAjax.js"></script>
<roma:inlinejs/>
<roma:inlinecss/>
</head>
<body>
<div id="janiculumWaitDiv" class="janiculumWaitImage" style="background-repeat: no-repeat; background-position: center center; background-image: url(<%=request.getContextPath()%>/static/base/image/wait.gif); position:absolute; width: 100%; height: 100%; left:-10000px; top: -10000px; z-index: -1000">
</div>
<div></div>
<form method="post" action="<%=request.getRequestURI() %>">
 <roma:screenArea name="/"/>
</form>
</body>
</html>
