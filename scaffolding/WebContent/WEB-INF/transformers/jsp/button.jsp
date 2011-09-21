<%@page import="org.romaframework.aspect.view.html.area.HtmlViewRenderable"%>
<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%> 
<%@page import="java.util.Set"%>

<%@page import="org.romaframework.aspect.view.html.transformer.jsp.JspTransformer"%>
<%@page import="org.romaframework.aspect.view.html.transformer.helper.JaniculumWrapper"%>
<%@page import="org.romaframework.aspect.view.html.constants.RequestConstants"%>
<%@page import="java.util.Map"%>


<%
	
	HtmlViewRenderable component = (HtmlViewRenderable)request.getAttribute(RequestConstants.CURRENT_COMPONENT_IN_TRANSFORMER);
	
%>

<div class="<%=JaniculumWrapper.cssClass(component,"html", null)%>" style="<%=JaniculumWrapper.inlineStyle(component, null)%>" id="<%=JaniculumWrapper.id(component, null)%>">

<%if(JaniculumWrapper.isField(component)){%>
	<button class="<%=JaniculumWrapper.cssClass(component, "button", "content")%>" id="<%=JaniculumWrapper.id(component, "content")%>" type="button" value="<%=JaniculumWrapper.content(component, true)==null?"":JaniculumWrapper.content(component, true)%>" name="<%=JaniculumWrapper.event(component, "change")%>"
	<%if(JaniculumWrapper.isDisabled(component)){%> disabled="disabled" <%} %>
	<%
	for(String event:JaniculumWrapper.getAvailableEvents(component)){
		String eventType = event;	
		String eventName = event;
		if( ".default_event".equals(event) || ".DEFAULT_EVENT".equals(event) ){
			eventName = "click";
		}
 %>
		on<%=eventName %>="romaEvent('<%=JaniculumWrapper.fieldName(component)%>', '<%=event%>')"
	<%} %>
	
	>
	<%=JaniculumWrapper.content(component, true)==null?"":JaniculumWrapper.content(component, true)%>
	</button>
<%} 
if(JaniculumWrapper.isAction(component)){%>
	<button class="<%=JaniculumWrapper.cssClass(component, "button", "content")%>" id="<%=JaniculumWrapper.id(component,"content")%>" type="button" value="<%=JaniculumWrapper.i18NLabel(component)%>" name="<%=JaniculumWrapper.actionName(component)%>"
	<%if(JaniculumWrapper.isDisabled(component)){%> disabled="disabled" <%} %>
	onclick="romaAction('<%=JaniculumWrapper.actionName(component)%>')"
	>
	<%=JaniculumWrapper.i18NLabel(component)%>
	</button>
<%} %>
</div>




