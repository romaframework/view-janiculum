<%@page import="org.romaframework.aspect.view.html.area.HtmlViewRenderable"%>
<%@page import="org.romaframework.aspect.view.html.transformer.jsp.JspTransformer"%>
<%@page import="org.romaframework.aspect.view.html.transformer.helper.JaniculumWrapper"%>
<%@page import="org.romaframework.aspect.view.html.constants.RequestConstants"%>
<%@page import="java.util.Map"%>
<%@page import="org.romaframework.aspect.view.html.transformer.jsp.directive.JspTransformerHelper"%>
<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>

<%
Map<String, Object> ctx = (Map<String, Object>) request.getAttribute(RequestConstants.CURRENT_CONTEXT_IN_TRANSFORMER);
JaniculumWrapper janiculum = (JaniculumWrapper)ctx.get(JspTransformer.JANICULUM);
pageContext.setAttribute("janiculum", janiculum);

JspTransformerHelper.addCss(janiculum.id(null), "vertical-align", janiculum.areaVerticalAlignment());
JspTransformerHelper.addCss(janiculum.id(null), "text-align", janiculum.areaHorizontalAlignment());
%>

<div id="<%=janiculum.id(null) %>" class="<%=janiculum.cssClass(null)%>" style="<%=janiculum.inlineStyle(null)%>">
	<%
	for(Object child:janiculum.getChildren()){
		JspTransformerHelper.delegate((HtmlViewRenderable)child, null,pageContext.getOut());
	}
	%>
</div>
