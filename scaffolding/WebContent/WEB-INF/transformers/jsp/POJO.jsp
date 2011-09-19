<%@ page language="java" contentType="text/html; charset=UTF-8"pageEncoding="UTF-8"%><%@page import="org.romaframework.aspect.view.html.component.HtmlViewConfigurableEntityForm"%><%@page import="org.romaframework.aspect.view.html.transformer.jsp.JspTransformer"%><%@page import="java.util.Map"%><%@page import="org.romaframework.aspect.view.html.transformer.helper.JaniculumWrapper"%><%@page import="org.romaframework.aspect.view.html.constants.RequestConstants"%><%
	Map<String, Object> ctx = (Map<String, Object>) request.getAttribute(RequestConstants.CURRENT_CONTEXT_IN_TRANSFORMER);
	JaniculumWrapper janiculum = (JaniculumWrapper) ctx.get(JspTransformer.JANICULUM);
%><div class="<%=janiculum.cssClass(null)%>" id="<%=janiculum.id(null)%>"><%
	((HtmlViewConfigurableEntityForm) janiculum.getComponent()).getRootArea().render(pageContext.getOut());
%></div>