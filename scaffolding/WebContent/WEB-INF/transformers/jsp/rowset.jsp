<%@page import="org.romaframework.aspect.view.html.area.HtmlViewRenderable"%>
<%@page import="org.romaframework.aspect.view.html.transformer.jsp.directive.JspTransformerHelper"%>
<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@page import="java.util.Set"%>
<%@page import="org.romaframework.aspect.view.html.transformer.jsp.JspTransformer"%>
<%@page import="org.romaframework.aspect.view.html.transformer.helper.JaniculumWrapper"%>
<%@page import="org.romaframework.aspect.view.html.constants.RequestConstants"%>
<%@page import="java.util.Map"%>
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
<table cellpadding="0" cellspacing="0" id="<%=janiculum.id(null)%>" class="<%=janiculum.cssClass(null)%> rowset-table" style="<%=janiculum.inlineStyle(null)%>">
	<%
	int row = 0;
	for(Object c:janiculum.getChildren()){
		HtmlViewRenderable child=(HtmlViewRenderable)c;
	
	%>
    <tr><td id="<%=janiculum.id(null)%>_<%=row%>_td"><%JspTransformerHelper.delegate(child, null,pageContext.getOut()); %></td></tr>
   <%
	   	JspTransformerHelper.addCss("#"+janiculum.id(null)+"_"+row+"_td", "vertical-align", valign);
	   	JspTransformerHelper.addCss("#"+janiculum.id(null)+"_"+row+"_td", "text-align", halign);
		row++;
	}
   %>
</table>