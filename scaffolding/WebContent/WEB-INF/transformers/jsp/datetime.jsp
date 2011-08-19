<%@page import="org.romaframework.aspect.view.html.constants.TransformerConstants"%>
<%@page import="org.romaframework.aspect.view.html.area.HtmlViewRenderable"%>
<%@page import="org.romaframework.aspect.view.html.transformer.jsp.directive.JspTransformerHelper"%>
<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@taglib uri="http://java.sun.com/jstl/core" prefix="c" %> 
<%@page import="java.util.Set"%>
<%@page import="org.romaframework.aspect.view.html.transformer.jsp.JspTransformer"%>
<%@page import="org.romaframework.aspect.view.html.transformer.helper.JaniculumWrapper"%>
<%@page import="org.romaframework.aspect.view.html.constants.RequestConstants"%>
<%@page import="java.util.Map"%>
<%
	Map<String, Object> ctx = (Map<String, Object>) request.getAttribute(RequestConstants.CURRENT_CONTEXT_IN_TRANSFORMER);
	JaniculumWrapper janiculum = (JaniculumWrapper)ctx.get(JspTransformer.JANICULUM);
	pageContext.setAttribute("janiculum", janiculum);
%>

<div class="<%=janiculum.cssClass(null)%>" style="<%=janiculum.inlineStyle(null)%>" id="<%=janiculum.id(null)%>">

<%if(!janiculum.isValid()){%>
	<input id="<%=janiculum.id("content")%>" class="<%=janiculum.cssClass("content")%>_invalid" type="text" name="<%=janiculum.fieldName()%>" 
	value="<%=janiculum.formatDateContent()%>" <%=janiculum.disabled()?" disabled='disabled'":""%>
	<%
	boolean existsChangeEvent=false;
	for(String event: janiculum.availableEvents()){
		if(!"change".equals(event)){
	%>
			on<%=event%>="romaFieldChanged('<%=janiculum.fieldName()%>'); romaEvent('<%=janiculum.fieldName()%>', '<%=event%>')"
	<%	}else{
		existsChangeEvent = true;
		}
	} 
	if(existsChangeEvent){
	%>
	
		onchange="romaFieldChanged('<%=janiculum.fieldName()%>'); romaEvent('<%=janiculum.fieldName()%>', 'change')"
	<%}else{ %>
		onchange="romaFieldChanged('<%=janiculum.fieldName()%>')"
	<%} %> 
		/>
	<span class="<%=janiculum.cssClass("validation_message")%></span>"></span>
<%}else{ %>
<input id="<%=janiculum.id("content")%>" class="<%=janiculum.cssClass("content")%>" type="text" name="<%=janiculum.fieldName()%>" 
value="<%=janiculum.formatDateContent()%>" <%=janiculum.disabled()?"disabled=\"disabled\"":""%>
<%
	boolean existsChangeEvent=false;
	for(String event: janiculum.availableEvents()){
		if(!"change".equals(event)){
%> 
		on<%=event%>="romaFieldChanged('<%=janiculum.fieldName()%>'); romaEvent('<%=janiculum.fieldName()%>', '<%=event%>')"
		
<%
		}else{
			existsChangeEvent = true;
		}
	}
	if(existsChangeEvent){
%>
		
		onchange="romaFieldChanged('<%=janiculum.fieldName()%>'); romaEvent('<%=janiculum.fieldName()%>', 'change')"
<%
	}else{
%>
		onchange="romaFieldChanged('<%=janiculum.fieldName()%>')"
<%
	}
%>
		 />
		 
<input id="<%=janiculum.id("time")%>" class="<%=janiculum.cssClass("content")%>" type="text" name="<%=janiculum.fieldName()%>_time" 
value="<%=janiculum.formatDateContent("HH:mm:ss")%>" <%=janiculum.disabled()?"disabled=\"disabled\"":""%>
<%
	existsChangeEvent=false;
	for(String event:janiculum.availableEvents()){
		if(!"change".equals(event)){
%> 
		on<%=event%>="romaFieldChanged('<%=janiculum.fieldName()%>_time'); romaEvent('<%=janiculum.fieldName()%>_time', '<%=event%>')"
<%
		}else{
			existsChangeEvent=true;
		}
	} 
	if(existsChangeEvent){
%>
		onchange="romaFieldChanged('<%=janiculum.fieldName()%>_time'); romaEvent('<%=janiculum.fieldName()%>_time', 'change')"
<%
	}else{
%>
		onchange="romaFieldChanged('<%=janiculum.fieldName()%>_time')"
<%
	}
%> 
		/>
<%}
StringBuffer buffer = new StringBuffer();
buffer.append("jQuery('#"+janiculum.id("content")+"').datepicker({ dateFormat: 'dd/mm/yy' });\n");
buffer.append("jQuery('#"+janiculum.id("time")+"').timeEntry({spinnerImage: '"+janiculum.contextPath()+"/static/base/image/spinnerDefault.png', show24Hours: true, showSeconds: true});");

JspTransformerHelper.addJs(janiculum.id(TransformerConstants.PART_ALL), buffer.toString());
%>