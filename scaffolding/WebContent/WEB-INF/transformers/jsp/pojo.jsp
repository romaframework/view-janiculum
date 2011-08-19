<%@page import="org.romaframework.aspect.view.form.ViewComponent"%><%@page import="org.romaframework.aspect.view.html.HtmlViewAspectHelper"%><%@page import="java.io.OutputStream"%><%@page import="java.io.ByteArrayOutputStream"%><%@page import="java.util.HashMap"%><%@page import="org.romaframework.aspect.view.html.transformer.helper.TransformerHelper"%><%@ page language="java" contentType="text/html; charset=UTF-8"	pageEncoding="UTF-8"%><%@taglib uri="http://java.sun.com/jstl/core" prefix="c" %><%@page import="java.util.Set"%><%@page import="org.romaframework.aspect.view.html.transformer.jsp.JspTransformer"%><%@page import="org.romaframework.aspect.view.html.transformer.helper.JaniculumWrapper"%><%@page import="org.romaframework.aspect.view.html.constants.RequestConstants"%><%@page import="java.util.Map"%><%
	Map<String, Object> ctx = (Map<String, Object>) request.getAttribute(RequestConstants.CURRENT_CONTEXT_IN_TRANSFORMER);
	
	TransformerHelper	helper	= TransformerHelper.getInstance();
	JaniculumWrapper janiculum = (JaniculumWrapper)ctx.get(JspTransformer.JANICULUM);
	pageContext.setAttribute("janiculum", janiculum);
	String htmlClass= (String)ctx.get("htmlClass");
	String htmlId = (String)ctx.get("htmlId");
	String content = (String)ctx.get("content"); 
	
%><div class="<%=htmlClass%>" id="<%=htmlId %>"><%=content.toString()%></div>