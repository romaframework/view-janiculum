<%@page import="org.romaframework.aspect.view.html.constants.TransformerConstants"%>
<%@page import="org.romaframework.aspect.view.html.transformer.jsp.directive.JspTransformerHelper"%>
<%@page import="org.romaframework.aspect.view.html.area.HtmlViewRenderable"%>
<%@ page language="java" contentType="text/html; charset=UTF-8"	pageEncoding="UTF-8"%><%@taglib uri="http://java.sun.com/jstl/core" prefix="c" %><%@page import="java.util.Set"%><%@page import="org.romaframework.aspect.view.html.transformer.jsp.JspTransformer"%><%@page import="org.romaframework.aspect.view.html.transformer.helper.JaniculumWrapper"%><%@page import="org.romaframework.aspect.view.html.constants.RequestConstants"%><%@page import="java.util.Map"%><%
	Map<String, Object> ctx = (Map<String, Object>) request.getAttribute(RequestConstants.CURRENT_CONTEXT_IN_TRANSFORMER);
	JaniculumWrapper janiculum = (JaniculumWrapper)ctx.get(JspTransformer.JANICULUM);
	pageContext.setAttribute("janiculum", janiculum);
	String part = (String) ctx.get(JspTransformer.PART_TO_PRINT);
	pageContext.setAttribute("part", part);
%>
<div class="<%=janiculum.cssClass(null)%>" style="<%=janiculum.inlineStyle(null)%>" id="<%=janiculum.id(null)%>" inited="false">
	<%if(janiculum.isAction()){%>
		<a class="<%=janiculum.cssClass("content")%>" id="<%=janiculum.id("content")%>" value="<%=janiculum.i18NLabel()%>" href="javascript:void(0)" title="<%=janiculum.i18NHint()%>"
		<%=janiculum.disabled()?"disabled=\"disabled\"":""%>
			onclick="romaAction('<%=janiculum.actionName()%>')">
			<label for="<%=janiculum.id("content")%>" id="<%=janiculum.id("label")%>" class="<%=janiculum.cssClass("label")%>">
			<%=janiculum.i18NLabel()%>
			</label> 
		</a>
	<%}else{ %>
			<label for="<%=janiculum.id("content")%>" id="<%=janiculum.id("label")%>" class="<%=janiculum.cssClass("label")%>">
			<%=janiculum.i18NLabel()%>
			</label> 
	<%} %>
	
	<%if(janiculum.haveChildren()){%>
	<ul class="<%=janiculum.cssClass("content")%>"  id="<%=janiculum.id("content")%>">
		<%for(Object child:janiculum.getChildren()){%>
			<li class="<%=janiculum.cssClass("content")%>">
					<%=JspTransformerHelper.delegate((HtmlViewRenderable)child, null) %>							
			</li>			
		<%} %>
	</ul>
	<%} %>
</div>
<%if(!"content".equals(part)){
	StringBuffer buffer = new StringBuffer();
	buffer.append("if(jQuery(\"#"+janiculum.id(null)+"\").attr(\"inited\") == \"true\"){");
	buffer.append("}else{");
	buffer.append("jquerycssmenu.buildmenu(\""+janiculum.id(null)+"\", arrowimages);");
	buffer.append("jQuery(\"#"+janiculum.id(null)+"\").attr(\"inited\", \"true\");");
	buffer.append("}");
	JspTransformerHelper.addJs(janiculum.id(TransformerConstants.PART_ALL), buffer.toString());
}
%>