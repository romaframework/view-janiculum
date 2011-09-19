<%@page import="org.romaframework.aspect.view.form.ViewComponent"%>
<%@page import="org.romaframework.aspect.view.html.HtmlViewAspectHelper"%>
<%@page import="java.io.OutputStream"%>
<%@page import="java.io.ByteArrayOutputStream"%>
<%@page import="java.util.HashMap"%>
<%@page import="org.romaframework.aspect.view.html.transformer.helper.TransformerHelper"%>
<%@ page language="java" contentType="text/html; charset=UTF-8"	pageEncoding="UTF-8"%><%@page import="java.util.Set"%><%@page import="org.romaframework.aspect.view.html.transformer.jsp.JspTransformer"%><%@page import="org.romaframework.aspect.view.html.transformer.helper.JaniculumWrapper"%><%@page import="org.romaframework.aspect.view.html.constants.RequestConstants"%><%@page import="java.util.Map"%><%
	Map<String, Object> ctx = (Map<String, Object>) request.getAttribute(RequestConstants.CURRENT_CONTEXT_IN_TRANSFORMER);
	JaniculumWrapper janiculum = (JaniculumWrapper)ctx.get(JspTransformer.JANICULUM);
	HtmlViewAspectHelper.renderByJsp((ViewComponent)janiculum.getComponent(), request, pageContext.getOut());
%>