<%@page import="org.romaframework.aspect.view.html.constants.TransformerConstants"%><%@page import="org.romaframework.aspect.view.html.area.HtmlViewRenderable"%><%@page import="org.romaframework.aspect.view.html.transformer.jsp.directive.JspTransformerHelper"%><%@ page language="java" contentType="text/html; charset=UTF-8"	pageEncoding="UTF-8"%><%@page import="java.util.Set"%><%@page import="org.romaframework.aspect.view.html.transformer.jsp.JspTransformer"%><%@page import="org.romaframework.aspect.view.html.transformer.helper.JaniculumWrapper"%><%@page import="org.romaframework.aspect.view.html.constants.RequestConstants"%><%@page import="java.util.Map"%><%
	Map<String, Object> ctx = (Map<String, Object>) request.getAttribute(RequestConstants.CURRENT_CONTEXT_IN_TRANSFORMER);
	JaniculumWrapper janiculum = (JaniculumWrapper)ctx.get(JspTransformer.JANICULUM);
	pageContext.setAttribute("janiculum", janiculum);
	String part = (String) ctx.get(JspTransformer.PART_TO_PRINT);
	pageContext.setAttribute("part", part);
	
	String vAlign = janiculum.areaVerticalAlignment();
	String hAlign = janiculum.areaHorizontalAlignment();
	String marginLeft = "";
	if("left".equals(hAlign)){
		marginLeft = "0";
	}else if("right".equals(hAlign)){
		marginLeft = "auto";
	}
	
	String marginRight = "";
	if("right".equals(hAlign)){
		marginRight = "0";
	}else if("left".equals(hAlign)){
		marginRight = "auto";
	}
	JspTransformerHelper.addCss(janiculum.id(null)+" > div.POJO > table.area_main", "margin-left", marginLeft);
	JspTransformerHelper.addCss(janiculum.id(null)+" > div.POJO > table.area_main", "margin-right", marginRight);
	
	JspTransformerHelper.addCss(janiculum.id(null)+" > table", "margin-left", marginLeft);
	JspTransformerHelper.addCss(janiculum.id(null)+" > table", "margin-right", marginRight);
%>
<div id="<%=janiculum.id(null)%>" class="<%=janiculum.cssClass(null)%>" style="<%=janiculum.inlineStyle(null)%>">
<%for(Object c:janiculum.getChildren()){
	HtmlViewRenderable child = (HtmlViewRenderable)c;
	
%><%JspTransformerHelper.delegate(child, null,pageContext.getOut()); %><%} %>
</div>