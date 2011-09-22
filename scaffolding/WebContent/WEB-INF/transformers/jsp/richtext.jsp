<%@page import="org.romaframework.aspect.view.html.constants.TransformerConstants"%>
<%@page import="org.romaframework.aspect.view.html.transformer.jsp.directive.JspTransformerHelper"%><%@page import="org.romaframework.aspect.view.html.area.HtmlViewRenderable"%><%@page import="org.romaframework.aspect.view.html.transformer.helper.JaniculumWrapper"%><%@page import="org.romaframework.aspect.view.html.transformer.jsp.JspTransformer"%><%@page import="org.romaframework.aspect.view.html.constants.RequestConstants"%><%@page import="java.util.Map"%><%@ page language="java" contentType="text/html; charset=UTF-8"	pageEncoding="UTF-8"%>
<%@page import="org.romaframework.aspect.view.html.area.HtmlViewRenderable"%>
<%@page import="org.romaframework.aspect.view.html.constants.RequestConstants"%>
<%
	
	HtmlViewRenderable component = (HtmlViewRenderable)request.getAttribute(RequestConstants.CURRENT_COMPONENT_IN_TRANSFORMER);
	
	String part = (String) request.getAttribute(RequestConstants.CURRENT_COMPONENT_PART_IN_TRANSFORMER);
	pageContext.setAttribute("part", part);

	if (!("raw".equals(part) || "label".equals(part))){   %>
	<div class="<%=JaniculumWrapper.cssClass(component, "richtext", null)%>" style="<%=JaniculumWrapper.inlineStyle(component, null)%>" id="<%=JaniculumWrapper.id(component, null)%>">
		<textarea id="<%=JaniculumWrapper.id(component, "content")%>" class="<%=JaniculumWrapper.cssClass(component, "richtext", null)%>" style="<%=JaniculumWrapper.inlineStyle(component, null)%>" 
		name="<%=JaniculumWrapper.fieldName(component)%>" <%=JaniculumWrapper.disabled(component)?"disabled=\"disabled\"":""%>
		<%
		boolean existsChangeEvent=false;
		for(String event: JaniculumWrapper.availableEvents(component)){
			if(!"change".equals(event)){
		%>
		on<%=event%>="romaFieldChanged('<%=JaniculumWrapper.fieldName(component)%>'); romaEvent('<%=JaniculumWrapper.fieldName(component)%>', '<%=event%>')"
		<%	}else{ 
		existsChangeEvent=true;
			}%>
	<% 	} 
		if(existsChangeEvent){
		%>
		onchange="romaFieldChanged('<%=JaniculumWrapper.fieldName(component)%>'); romaEvent('<%=JaniculumWrapper.fieldName(component)%>', 'change')"
		<%}else{ %>
		onchange="romaFieldChanged('<%=JaniculumWrapper.fieldName(component)%>')"
		<%} %>
		><%=JaniculumWrapper.content(component, true)==null?"":JaniculumWrapper.content(component, true)%></textarea> 
		<%if(!JaniculumWrapper.isValid(component)){%>
			<span class="<%=JaniculumWrapper.cssClass(component, "richtext", "validation_message")%>"></span>	
		<%} %>
	</div>

<%
		StringBuffer buffer = new StringBuffer();
		buffer.append("var ed = CKEDITOR.instances."+JaniculumWrapper.id(component, "content")+";\n");
		buffer.append("\tif(ed != undefined){\n");
		buffer.append("\t\tCKEDITOR.remove(ed);\n");
		buffer.append("\t}	\n");
		buffer.append("\t$('#"+JaniculumWrapper.id(component, "content")+"').ckeditor();\n");
		
		buffer.append("\ted = CKEDITOR.instances."+JaniculumWrapper.id(component, "content")+";\n");
		buffer.append("\ted.on('key', function ( e ) {\n");
		buffer.append("\tvar myTextField = document.getElementById('"+JaniculumWrapper.id(component, "content")+"');\n");
		buffer.append("\tmyTextField.value = ed.getData();\n");
		buffer.append("\tvar fun = $('#"+JaniculumWrapper.id(component, "content")+"').attr(\"onChange\");\n");
		buffer.append("\teval(\"\"+fun+\";\");\n");
		buffer.append("} );\n");
		JspTransformerHelper.addJs(JaniculumWrapper.id(component, TransformerConstants.PART_ALL), buffer.toString());

	}
	if("raw".equals(part)){
%><%=JaniculumWrapper.content(component, true)==null?"":JaniculumWrapper.content(component, true)%><%} 
if("label".equals(part)){
%>
	<label id="<%=JaniculumWrapper.id(component, "label")%>" class="<%=JaniculumWrapper.cssClass(component, "richtext", "label")%>" for="<%=JaniculumWrapper.id(component, "content")%>"><%=JaniculumWrapper.i18NLabel(component)%></label>
<%} 
%>