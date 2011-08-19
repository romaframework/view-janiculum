<%@page import="org.romaframework.aspect.view.html.transformer.jsp.directive.JspTransformerHelper"%>
<%@ page language="java" contentType="text/html; charset=UTF-8"	pageEncoding="UTF-8"%><%@taglib uri="http://java.sun.com/jstl/core" prefix="c" %><%@page import="java.util.Set"%><%@page import="org.romaframework.aspect.view.html.transformer.jsp.JspTemplateManager"%><%@page import="org.romaframework.aspect.view.html.transformer.jsp.JspTransformer"%><%@page import="org.romaframework.aspect.view.html.transformer.helper.JaniculumWrapper"%><%@page import="org.romaframework.aspect.view.html.constants.RequestConstants"%><%@page import="java.util.Map"%><%
	Map<String, Object> ctx = (Map<String, Object>) request.getAttribute(RequestConstants.CURRENT_CONTEXT_IN_TRANSFORMER);
	JaniculumWrapper janiculum = (JaniculumWrapper)ctx.get(JspTransformer.JANICULUM);
	pageContext.setAttribute("janiculum", janiculum);
	String part = (String) ctx.get(JspTransformer.PART_TO_PRINT);
	if(!("raw".equals(part) || "label".equals(part))){%>

<div class="<%=janiculum.cssClass(null)%>" style="<%=janiculum.inlineStyle(null)%>" id="<%=janiculum.id(null)%>" 
	<%if(!janiculum.disabled()){
		String eventName = null;
		for(String event : janiculum.availableEvents()){
			if(".default_event".equals(event) || ".DEFAULT_EVENT".equals(event)){
				eventName="click";
			}else{
				eventName = event;
			}
			%>
    		on<%=eventName%>="romaEvent('<%=janiculum.fieldName()%>', '<%=event%>')"
		<%}%>
	>
	 <span id="<%=janiculum.id("content")%>" class="<%=janiculum.cssClass("content")%>" >
    	<%=janiculum.formattedContent()==null?"":janiculum.formattedContent()%>
	 </span>
</div>
<% }
	}
if("raw".equals(part)){
	%><%=janiculum.formattedContent()==null?"":janiculum.formattedContent()%><%
}
if("label".equals(part)){%>
	<label id="<%=janiculum.id("label")%>" class="<%=janiculum.cssClass("label")%>" for="<%=janiculum.id("content")%>"><%=janiculum.i18NLabel()%></label>
<%} %>
