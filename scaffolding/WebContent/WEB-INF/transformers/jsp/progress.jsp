<%@page import="org.romaframework.aspect.view.html.constants.TransformerConstants"%><%@page import="org.romaframework.aspect.view.html.transformer.jsp.directive.JspTransformerHelper"%><%@ page language="java" contentType="text/html; charset=UTF-8"	pageEncoding="UTF-8"%><%@taglib uri="http://java.sun.com/jstl/core" prefix="c" %><%@page import="java.util.Set"%><%@page import="org.romaframework.aspect.view.html.transformer.jsp.JspTemplateManager"%><%@page import="org.romaframework.aspect.view.html.transformer.jsp.JspTransformer"%><%@page import="org.romaframework.aspect.view.html.transformer.helper.JaniculumWrapper"%><%@page import="org.romaframework.aspect.view.html.constants.RequestConstants"%><%@page import="java.util.Map"%><%
	Map<String, Object> ctx = (Map<String, Object>) request.getAttribute(RequestConstants.CURRENT_CONTEXT_IN_TRANSFORMER);
	JaniculumWrapper janiculum = (JaniculumWrapper)ctx.get(JspTransformer.JANICULUM);
	pageContext.setAttribute("janiculum", janiculum);
	String part = (String) ctx.get(JspTransformer.PART_TO_PRINT);
	pageContext.setAttribute("part", part);

	if (!("raw".equals(part) || "label".equals(part))){   %>
	<div class="<%=janiculum.cssClass(null)%>" style="<%=janiculum.inlineStyle(null)%> width:400px;" id="<%=janiculum.id(null)%>">
	<%if("".equals(part) || "all".equals(part)){ %>
	<table>
		<tr>
			<td id="<%=janiculum.id("content")%>" />
			<td><%=janiculum.content(true)==null?"0":janiculum.content(true)%>%</td>
		</tr>
	</table>
	<%} %>
	</div>
<%}
if("raw".equals(part)){
%><%=janiculum.content(true)==null?"":janiculum.content(true)%><%
}else if("label".equals(part)){
%><label id="<%=janiculum.id("label")%>" class="<%=janiculum.cssClass("label")%>" for="<%=janiculum.id("content")%>"><%=janiculum.i18NLabel()%></label>
<%
}
StringBuffer buffer = new StringBuffer();
buffer.append("$(function() {\n");
buffer.append("\t$( \"#"+janiculum.id("content")+"\" ).progressbar({\n");
buffer.append("\t\tvalue: parseFloat(String(" + (janiculum.content(true)==null ? "0" : janiculum.content(true))+").replace(',','.'))\n");
buffer.append("\t});\n");
buffer.append("});\n");
System.out.println(buffer);
JspTransformerHelper.addJs(janiculum.id(TransformerConstants.PART_ALL), buffer.toString());
%>