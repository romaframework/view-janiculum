<%@page import="org.romaframework.aspect.view.html.transformer.jsp.directive.JspTransformerHelper"%>
<%@ page language="java" contentType="text/html; charset=UTF-8"	pageEncoding="UTF-8"%><%@page import="java.util.Set"%><%@page import="org.romaframework.aspect.view.html.transformer.jsp.JspTransformer"%><%@page import="org.romaframework.aspect.view.html.transformer.helper.JaniculumWrapper"%><%@page import="org.romaframework.aspect.view.html.constants.RequestConstants"%><%@page import="java.util.Map"%><%
	Map<String, Object> ctx = (Map<String, Object>) request.getAttribute(RequestConstants.CURRENT_CONTEXT_IN_TRANSFORMER);
	JaniculumWrapper janiculum = (JaniculumWrapper)ctx.get(JspTransformer.JANICULUM);
	pageContext.setAttribute("janiculum", janiculum);
	String part = (String) ctx.get(JspTransformer.PART_TO_PRINT);
			
	if(!("raw".equals(part) || "label".equals(part))){
    	if(janiculum.content()!=null){%>
        <div class="<%=janiculum.cssClass(null)%>" style="<%=janiculum.inlineStyle(null)%>" id="<%=janiculum.id(null)%>">
        
            <img id="<%=janiculum.id("content")%>" class="<%=janiculum.cssClass("content")%>" src="<%=janiculum.contextPath()%>/image.png?imagePojo=<%=janiculum.imageId()%>"
            <%for(String event:janiculum.availableEvents()){ %> 
            on<%=event%>="romaEvent('<%=janiculum.fieldName()%>', '<%=event%>')"
            <%} %>
            />
    
        </div>
    <% }
}

if("label".equals(part)){%>
    <label id="<%=janiculum.id("label")%>" class="<%=janiculum.cssClass("label")%>" for="<%=janiculum.id("content")%>"><%=janiculum.i18NLabel()%></label>
<% }%>
