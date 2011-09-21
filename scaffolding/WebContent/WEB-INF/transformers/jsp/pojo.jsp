<%@page import="org.romaframework.aspect.view.html.area.HtmlViewRenderable"%>
<%@ page language="java" contentType="text/html; charset=UTF-8"pageEncoding="UTF-8"%><%@page import="org.romaframework.aspect.view.html.component.HtmlViewConfigurableEntityForm"%><%@page import="org.romaframework.aspect.view.html.transformer.jsp.JspTransformer"%><%@page import="java.util.Map"%><%@page import="org.romaframework.aspect.view.html.transformer.helper.JaniculumWrapper"%><%@page import="org.romaframework.aspect.view.html.constants.RequestConstants"%><%
	
HtmlViewRenderable component = (HtmlViewRenderable)request.getAttribute(RequestConstants.CURRENT_COMPONENT_IN_TRANSFORMER);
%><div class="<%=JaniculumWrapper.cssClass(component, "pojo", null)%>" id="<%=JaniculumWrapper.id(component, null)%>"><%
	((HtmlViewConfigurableEntityForm) component).getRootArea().render(response.getWriter());
%></div>