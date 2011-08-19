<%@page import="org.romaframework.aspect.view.html.constants.TransformerConstants"%>
<%@page import="org.romaframework.aspect.view.html.transformer.jsp.directive.JspTransformerHelper"%>
<%@ page language="java" contentType="text/html; charset=UTF-8"	pageEncoding="UTF-8"%><%@taglib uri="http://java.sun.com/jstl/core" prefix="c" %><%@page import="java.util.Set"%><%@page import="org.romaframework.aspect.view.html.transformer.jsp.JspTransformer"%><%@page import="org.romaframework.aspect.view.html.transformer.helper.JaniculumWrapper"%><%@page import="org.romaframework.aspect.view.html.constants.RequestConstants"%><%@page import="java.util.Map"%><%
	Map<String, Object> ctx = (Map<String, Object>) request.getAttribute(RequestConstants.CURRENT_CONTEXT_IN_TRANSFORMER);
	JaniculumWrapper janiculum = (JaniculumWrapper)ctx.get(JspTransformer.JANICULUM);
	pageContext.setAttribute("janiculum", janiculum);
	String part = (String) ctx.get(JspTransformer.PART_TO_PRINT);
	pageContext.setAttribute("part", part);
%>
<div id="<%=janiculum.id(null)%>" class="<%=janiculum.cssClass(null)%>" style="<%=janiculum.inlineStyle(null)%>">
	<form id="<%=janiculum.id("form")%>" action="<%=janiculum.contextPath()%>/fileUpload" sent="0" method="post" enctype="multipart/form-data" target="<%=janiculum.id("iframe")%>">
		<input name="<%=janiculum.fieldName()%>" type="file" onchange="jQuery('#<%=janiculum.id("form")%>').attr('sent', 1); submit()"/>
	</form>
	<iframe name="<%=janiculum.id("iframe")%>" width="0" height="0" frameborder="0" onload="if(jQuery('#<%=janiculum.id("form")%>').attr('sent') == '1'){jQuery('#<%=janiculum.id("form")%>').attr('sent', 0); romaEvent('<%=janiculum.fieldName()%>', '.DEFAULT_EVENT')%>"></iframe>
</div>