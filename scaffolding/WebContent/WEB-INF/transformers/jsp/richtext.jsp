<%@page import="org.romaframework.aspect.view.html.constants.TransformerConstants"%>
<%@page import="org.romaframework.aspect.view.html.transformer.jsp.directive.JspTransformerHelper"%><%@page import="org.romaframework.aspect.view.html.area.HtmlViewRenderable"%><%@page import="org.romaframework.aspect.view.html.transformer.helper.JaniculumWrapper"%><%@page import="org.romaframework.aspect.view.html.transformer.jsp.JspTransformer"%><%@page import="org.romaframework.aspect.view.html.constants.RequestConstants"%><%@page import="java.util.Map"%><%@ page language="java" contentType="text/html; charset=UTF-8"	pageEncoding="UTF-8"%>
<%
	Map<String, Object> ctx = (Map<String, Object>) request.getAttribute(RequestConstants.CURRENT_CONTEXT_IN_TRANSFORMER);
	JaniculumWrapper janiculum = (JaniculumWrapper)ctx.get(JspTransformer.JANICULUM);
	pageContext.setAttribute("janiculum", janiculum);
	String part = (String) ctx.get(JspTransformer.PART_TO_PRINT);
	pageContext.setAttribute("part", part);

	if (!("raw".equals(part) || "label".equals(part))){   %>
	<div class="<%=janiculum.cssClass(null)%>" style="<%=janiculum.inlineStyle(null)%>" id="<%=janiculum.id(null)%>">
		<textarea id="<%=janiculum.id("content")%>" class="<%=janiculum.cssClass(null)%>" style="<%=janiculum.inlineStyle(null)%>" 
		name="<%=janiculum.fieldName()%>" <%=janiculum.disabled()?"disabled=\"disabled\"":""%>
		<%
		boolean existsChangeEvent=false;
		for(String event: janiculum.availableEvents()){
			if(!"change".equals(event)){
		%>
		on<%=event%>="romaFieldChanged('<%=janiculum.fieldName()%>'); romaEvent('<%=janiculum.fieldName()%>', '<%=event%>')"
		<%	}else{ 
		existsChangeEvent=true;
			}%>
	<% 	} 
		if(existsChangeEvent){
		%>
		onchange="romaFieldChanged('<%=janiculum.fieldName()%>'); romaEvent('<%=janiculum.fieldName()%>', 'change')"
		<%}else{ %>
		onchange="romaFieldChanged('<%=janiculum.fieldName()%>')"
		<%} %>
		><%=janiculum.content(true)==null?"":janiculum.content(true)%></textarea> 
		<%if(!janiculum.isValid()){%>
			<span class="<%=janiculum.cssClass("validation_message")%>"></span>	
		<%} %>
	</div>
<%}
	if("raw".equals(part)){
%><%=janiculum.content(true)==null?"":janiculum.content(true)%><%} 
if("label".equals(part)){
%>
	<label id="<%=janiculum.id("label")%>" class="<%=janiculum.cssClass("label")%>" for="<%=janiculum.id("content")%>"><%=janiculum.i18NLabel()%></label>
<%} 
StringBuffer buffer = new StringBuffer();
buffer.append("var ed = CKEDITOR.instances."+janiculum.id("content")+";\n");
buffer.append("\tif(ed != undefined){\n");
buffer.append("\t\tCKEDITOR.remove(ed);\n");
buffer.append("\t}	\n");
buffer.append("\t$('#"+janiculum.id("content")+"').ckeditor();\n");

buffer.append("\ted = CKEDITOR.instances."+janiculum.id("content")+";\n");
buffer.append("\ted.on('key', function ( e ) {\n");
buffer.append("\tvar myTextField = document.getElementById('"+janiculum.id("content")+"');\n");
buffer.append("\tmyTextField.value = ed.getData();\n");
buffer.append("\tvar fun = $('#"+janiculum.id("content")+"').attr(\"onChange\");\n");
buffer.append("\teval(\"\"+fun+\";\");\n");
buffer.append("} );\n");
JspTransformerHelper.addJs(janiculum.id(TransformerConstants.PART_ALL), buffer.toString());
%>