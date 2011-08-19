<%@page import="org.romaframework.aspect.view.html.transformer.jsp.directive.JspTransformerHelper"%>
<%@ page language="java" contentType="text/html; charset=UTF-8"	pageEncoding="UTF-8"%><%@taglib uri="http://java.sun.com/jstl/core" prefix="c" %><%@page import="java.util.Set"%><%@page import="org.romaframework.aspect.view.html.transformer.jsp.JspTransformer"%><%@page import="org.romaframework.aspect.view.html.transformer.helper.JaniculumWrapper"%><%@page import="org.romaframework.aspect.view.html.constants.RequestConstants"%><%@page import="java.util.Map"%><%
	Map<String, Object> ctx = (Map<String, Object>) request.getAttribute(RequestConstants.CURRENT_CONTEXT_IN_TRANSFORMER);
	JaniculumWrapper janiculum = (JaniculumWrapper)ctx.get(JspTransformer.JANICULUM);
	pageContext.setAttribute("janiculum", janiculum);
	String part = (String) ctx.get(JspTransformer.PART_TO_PRINT);
%>
<div class="<%=janiculum.cssClass(null)%>" style="<%=janiculum.inlineStyle(null)%>" id="<%=janiculum.id(null)%>">
<% if(janiculum.isField()){%>
<a class="<%=janiculum.cssClass("content")%>" id="<%=janiculum.id("content")%>" value="<%=janiculum.content(true)==null?"":janiculum.content(true)%>" href="javascript:void(0)"
<%=janiculum.disabled()?"disabled=\"disabled\"":"" %>
<%
String eventName = null;
for(String event:janiculum.availableEvents()){
	if(".default_event".equals(event) || ".DEFAULT_EVENT".equals(event) ){
		eventName = "click";
	}else{
		eventName = "event";
	}
	%>
	on<%=eventName%>="romaFieldChanged('<%=janiculum.fieldName()%>'); romaEvent('<%=janiculum.fieldName()%>', '<%=event%>')"
<%} %>
>
<%=janiculum.content(true)==null?"":janiculum.content(true)%>
</a>
<%} else if(janiculum.isAction()){%>

<a class="<%=janiculum.cssClass("content")%>" id="<%=janiculum.id("content")%>" value="<%=janiculum.i18NLabel()%>" href="javascript:void(0)" title="<%=janiculum.i18NHint()%>"
<%=janiculum.disabled()? "disabled=\"disabled\"":""%>
onclick="romaAction('<%=janiculum.actionName()%>')" >
<%=janiculum.i18NLabel()%>
</a><%} %>
</div>