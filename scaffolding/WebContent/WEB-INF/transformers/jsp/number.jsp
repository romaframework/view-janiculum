<%@ page language="java" contentType="text/html; charset=UTF-8"	pageEncoding="UTF-8"%><%@taglib uri="http://java.sun.com/jstl/core" prefix="c" %><%@page import="java.util.Set"%><%@page import="org.romaframework.aspect.view.html.transformer.jsp.JspTransformer"%><%@page import="org.romaframework.aspect.view.html.transformer.helper.JaniculumWrapper"%><%@page import="org.romaframework.aspect.view.html.constants.RequestConstants"%><%@page import="java.util.Map"%><%
	Map<String, Object> ctx = (Map<String, Object>) request.getAttribute(RequestConstants.CURRENT_CONTEXT_IN_TRANSFORMER);
	JaniculumWrapper janiculum = (JaniculumWrapper)ctx.get(JspTransformer.JANICULUM);
	pageContext.setAttribute("janiculum", janiculum);
	String part = (String) ctx.get(JspTransformer.PART_TO_PRINT);
	pageContext.setAttribute("part", part);
	if(!("raw".equals(part)||"label".equals(part))){
%>
	<div class="<%=janiculum.cssClass(null)%>" style="<%=janiculum.inlineStyle(null)%>" id="<%=janiculum.id(null)%>">
	
	<input id="<%=janiculum.id("content")%>" class="<%=janiculum.cssClass(null)%>" style="<%=janiculum.isValid()?"":"border-color:red;"%><%=janiculum.inlineStyle(null)%>" type="text" 
	name="<%=janiculum.fieldName()%>" value="<%=janiculum.formatNumberContent()==null?"":janiculum.formatNumberContent()%>" <%=janiculum.disabled()?"disabled=\"disabled\"":""%>
	<%
	boolean existsChangeEvent=false;
	for(String event: janiculum.availableEvents()){
		if(!"change".equals(event)){
	%> 
		on<%=event%>="romaFieldChanged('<%=janiculum.fieldName()%>'); romaEvent('<%=janiculum.fieldName()%>', '<%=event%>')"
		<%}else{ 
			existsChangeEvent=true;
		}
	}
	if(existsChangeEvent){
		%>
		
		onchange="romaFieldChanged('<%=janiculum.fieldName()%>'); romaEvent('<%=janiculum.fieldName()%>', 'change')"
		<%}else{ %>
		onchange="romaFieldChanged('<%=janiculum.fieldName()%>')"
		<%} %>
		/>
	<%if(!janiculum.isValid()){%>
		<span class="<%=janiculum.cssClass("validation_message")%>"><%=janiculum.validationMessage()==null?"Invalid":janiculum.validationMessage()%></span>
	<%} %>
	
	</div>
<%}else if("raw".equals(part)){ %><%=janiculum.formatNumberContent()==null?"":janiculum.formatNumberContent()%><%
}else if("label".equals(part)){%>
<label id="<%=janiculum.id("label")%>" class="<%=janiculum.cssClass("label")%>" for="<%=janiculum.id("content")%>"><%=janiculum.i18NLabel()%></label>
<%}%>