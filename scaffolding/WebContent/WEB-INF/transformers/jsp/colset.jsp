<%@page import="org.romaframework.aspect.view.html.area.HtmlViewRenderable"%>
<%@page import="org.romaframework.aspect.view.html.transformer.jsp.directive.JspTransformerHelper"%>
<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@page import="java.util.Set"%>
<%@page import="org.romaframework.aspect.view.html.transformer.jsp.JspTransformer"%>
<%@page import="org.romaframework.aspect.view.html.transformer.helper.JaniculumWrapper"%>
<%@page import="org.romaframework.aspect.view.html.constants.RequestConstants"%>
<%@page import="java.util.Map"%>
<%@page import="java.io.Writer"%>
<%@page import="java.io.PrintWriter"%>
<%
	Map<String, Object> ctx = (Map<String, Object>) request.getAttribute(RequestConstants.CURRENT_CONTEXT_IN_TRANSFORMER);
	JaniculumWrapper janiculum = (JaniculumWrapper)ctx.get(JspTransformer.JANICULUM);
	pageContext.setAttribute("janiculum", janiculum);
	String part = (String) ctx.get(JspTransformer.PART_TO_PRINT);
	pageContext.setAttribute("part", part);
	String valign = janiculum.areaVerticalAlignment();
	if("center".equals(valign)){
		valign = "middle";
	}
	String halign = janiculum.areaHorizontalAlignment();
%>
<table cellpaddign="0" cellspacing="0" id="<%=janiculum.id(null)%>" class="<%=janiculum.cssClass(null)%> colset-table" style="<%=janiculum.inlineStyle(null)%>">
	<tr>
	<%
	int col = 0;
	for(Object child:janiculum.getChildren()){ %>
		<td id="<%=janiculum.id(null)%>_<%=col%>_td">
		<% JspTransformerHelper.delegate((HtmlViewRenderable)child, null,pageContext.getOut());%>
		</td>
	<%
		JspTransformerHelper.addCss(janiculum.id(null)+"_"+col+"_td", "vertical-align", valign);
		JspTransformerHelper.addCss(janiculum.id(null)+"_"+col+"_td", "vertexttical-align", halign);
		col++;
	} %>
	</tr>
</table>