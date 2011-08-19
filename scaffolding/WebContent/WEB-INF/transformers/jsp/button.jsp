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
%>

<div class="<%=janiculum.cssClass(null)%>" style="<%=janiculum.inlineStyle(null)%>" id="<%=janiculum.id(null)%>">

<%if(janiculum.isField()){%>
	<button class="<%=janiculum.cssClass("content")%>" id="<%=janiculum.id("content")%>" type="button" value="<%=janiculum.content(true)==null?"":janiculum.content(true)%>" name="<%=janiculum.event("change")%>"
	<%if(janiculum.isDisabled()){%> disabled="disabled" <%} %>
	<%
	for(String event:janiculum.getAvailableEvents()){
		String eventType = event;	
		String eventName = event;
		if( ".default_event".equals(event) || ".DEFAULT_EVENT".equals(event) ){
			eventName = "click";
		}
 %>
		on<%=eventName %>="romaEvent('<%=janiculum.fieldName()%>', '<%=event%>')"
	<%} %>
	
	>
	<%=janiculum.content(true)==null?"":janiculum.content(true)%>
	</button>
<%} 
if(janiculum.isAction()){%>
	<button class="<%=janiculum.cssClass("content")%>" id="<%=janiculum.id("content")%>" type="button" value="<%=janiculum.i18NLabel()%>" name="<%=janiculum.actionName()%>"
	<%if(janiculum.isDisabled()){%> disabled="disabled" <%} %>
	onclick="romaAction('<%=janiculum.actionName()%>')"
	>
	<%=janiculum.i18NLabel()%>
	</button>
<%} %>
</div>




