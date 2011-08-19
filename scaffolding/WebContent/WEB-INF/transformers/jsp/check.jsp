<%@ page language="java" contentType="text/html; charset=UTF-8"	pageEncoding="UTF-8"%><%@taglib uri="http://java.sun.com/jstl/core" prefix="c" %><%@page import="java.util.Set"%><%@page import="org.romaframework.aspect.view.html.transformer.jsp.JspTemplateManager"%><%@page import="org.romaframework.aspect.view.html.transformer.jsp.JspTransformer"%><%@page import="org.romaframework.aspect.view.html.transformer.helper.JaniculumWrapper"%><%@page import="org.romaframework.aspect.view.html.constants.RequestConstants"%><%@page import="java.util.Map"%><%
	Map<String, Object> ctx = (Map<String, Object>) request.getAttribute(RequestConstants.CURRENT_CONTEXT_IN_TRANSFORMER);
	JaniculumWrapper janiculum = (JaniculumWrapper)ctx.get(JspTransformer.JANICULUM);
	pageContext.setAttribute("janiculum", janiculum);
	String part = (String) ctx.get(JspTransformer.PART_TO_PRINT);
	pageContext.setAttribute("part", part);
%>
<%if (!(part.equals("raw") || part .equals("label"))){   %>
<div class="<%=janiculum.cssClass(null)%>" style="<%=janiculum.inlineStyle(null)%>" id="<%=janiculum.id(null)%>">

<%if (part.equals("") || part.equals("all")){%>
	<input id="<%=janiculum.id("reset")%>" type="hidden" name="<%=janiculum.fieldName()%>_reset" />
	<input id="<%=janiculum.id("content")%>" class="<%=janiculum.cssClass(null)%>" style="<%=janiculum.inlineStyle(null)%>" type="checkbox" name="<%=janiculum.fieldName()%>" 
	
	<%if(janiculum.checked()){%> checked="checked"<%}%> 
	<%if(janiculum.disabled()){%> disabled="disabled" <%}%> 
	
	<% 
	boolean existsChangeEvent = false;
	for(String event: janiculum.availableEvents()){
		if(!event.equals("change")){
	%>
			on<%=event%>="romaFieldChanged('<%=janiculum.fieldName()%>'); romaFieldChanged('<%=janiculum.fieldName()%>_reset'); romaEvent('<%=janiculum.fieldName()%>', '<%=event%>')"
	<%	}else{
			existsChangeEvent = true;
		}		
	} 
	
	
	if(existsChangeEvent){
	%>	
		onchange="romaFieldChanged('<%=janiculum.fieldName()%>'); romaFieldChanged('<%=janiculum.fieldName()%>_reset'); romaEvent('<%=janiculum.fieldName()%>', 'change')"
	<%}else{ %>
			
			onchange="romaFieldChanged('<%=janiculum.fieldName()%>'); romaFieldChanged('<%=janiculum.fieldName()%>_reset'); romaSendAjaxRequest()"
	<%} %> 
			/>
			
		<%if (!janiculum.isValid()){%>
			<span class="<%=janiculum.cssClass("validation_message")%>"></span>	
		<%} %>
	
	<%} %>
</div>
<%} %>
<%if (part.equals("raw")){  %><%= janiculum.content(true)==null?"":janiculum.content(true) %><%} %>

<%if (part.equals("label")){%>  
<label id="<%=janiculum.id("label")%>" class="<%=janiculum.cssClass("label")%>" for="<%=janiculum.id("content")%>"><%=janiculum.i18NLabel()%></label>
<%}%>