<%@page import="org.romaframework.aspect.view.html.constants.TransformerConstants"%>
<%@page import="org.romaframework.aspect.view.html.transformer.jsp.directive.JspTransformerHelper"%>
<%@ page language="java" contentType="text/html; charset=UTF-8"	pageEncoding="UTF-8"%><%@taglib uri="http://java.sun.com/jstl/core" prefix="c" %><%@page import="java.util.Set"%><%@page import="org.romaframework.aspect.view.html.transformer.jsp.JspTemplateManager"%><%@page import="org.romaframework.aspect.view.html.transformer.jsp.JspTransformer"%><%@page import="org.romaframework.aspect.view.html.transformer.helper.JaniculumWrapper"%><%@page import="org.romaframework.aspect.view.html.constants.RequestConstants"%><%@page import="java.util.Map"%><%
	Map<String, Object> ctx = (Map<String, Object>) request.getAttribute(RequestConstants.CURRENT_CONTEXT_IN_TRANSFORMER);
	JaniculumWrapper janiculum = (JaniculumWrapper)ctx.get(JspTransformer.JANICULUM);
	pageContext.setAttribute("janiculum", janiculum);
	String part = (String) ctx.get(JspTransformer.PART_TO_PRINT);
	pageContext.setAttribute("part", part);
%>
<div class="<%=janiculum.cssClass(null)%>" style="<%=janiculum.inlineStyle(null)%>" id="<%=janiculum.id(null)%>">	
	<%
	boolean existsChangeEvent = true;
	if( !janiculum.isValid()){%>
		<input id="<%=janiculum.id("content")%>" class="<%=janiculum.cssClass("content")%>_invalid" type="text" name="<%=janiculum.fieldName()%>_time" value="<%=janiculum.formatDateContent()%>" <%if( janiculum.disabled()){%> disabled="disabled" <%}%> 
		<%
		existsChangeEvent = false;
		for(String event: janiculum.availableEvents()){
		%>
		
		
		<%if(!"change".equals(event)){%>
		on<%=event%>="romaFieldChanged('<%=janiculum.fieldName()%>_time'); romaEvent('<%=janiculum.fieldName()%>_time', '<%=event%>')"
		<%}else{
			existsChangeEvent = true;
		}%>
		<%}%>
		<%if( existsChangeEvent){%>
		onchange="romaFieldChanged('<%=janiculum.fieldName()%>_time'); romaEvent('<%=janiculum.fieldName()%>_time', 'change')"
		<%}else{%>
		onchange="romaFieldChanged('<%=janiculum.fieldName()%>_time');"
		<%}%>
		/>
		<span class="<%=janiculum.cssClass("validation_message")%>"></span>
	<%}else{%>	
	<input id="<%=janiculum.id("time")%>" class="<%=janiculum.cssClass("content")%>" type="text" name="<%=janiculum.fieldName()%>_time" value="<%=janiculum.formatDateContent("HH:MM:ss")%>" <%if( janiculum.disabled()){%> disabled="disabled" <%}%> 
	<%
		existsChangeEvent = false;
	for(String event: janiculum.availableEvents() ){
	%>
		
		<%if( !"change".equals(event)){%>
		on<%=event%>="romaFieldChanged('<%=janiculum.fieldName()%>_time'); romaEvent('<%=janiculum.fieldName()%>_time', '<%=event%>')"
		<%}else{
			existsChangeEvent=true;
		}}%>
		<%if( existsChangeEvent){%>
		onchange="romaFieldChanged('<%=janiculum.fieldName()%>_time'); romaEvent('<%=janiculum.fieldName()%>_time', 'change')"
		<%}else{%>
		onchange="romaFieldChanged('<%=janiculum.fieldName()%>_time');"
		<%}%>
	/>
	<%}%>
</div>

<%
JspTransformerHelper.addJs(janiculum.id(TransformerConstants.PART_ALL), "jQuery('#"+janiculum.id("time")+"').timeEntry({spinnerImage: 'static/base/image/spinnerDefault.png', show24Hours: true, showSeconds: true});");
%>
