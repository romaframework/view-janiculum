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
	String part = (String) ctx.get(JspTransformer.PART_TO_PRINT);
	pageContext.setAttribute("part", part);
%>
<div class="<%=janiculum.cssClass(null)%>" style="<%=janiculum.inlineStyle(null)%>" id="<%=janiculum.id(null)%>">


<% if( "".equals(part) || "all".equals(part)) {%>


<span id="<%=janiculum.id("content") %>" class="<%=janiculum.cssClass("content") %>" >
<img id="<%=janiculum.id("content")%>_img" class="<%=janiculum.cssClass("content")%>" src="chart.png?imagePojo=<%=janiculum.imageId()%>&t=<%=janiculum.currentTime()%>"
	<%for(String event: janiculum.getAvailableEvents()){ %> 
		
		on<%=event%>="romaFieldChanged('<%=janiculum.fieldName() %>'); romaEvent('<%=janiculum.fieldName() %>', '<%=event%>')"
	<%} %>
	/>
</span>

<%} %>



<%if ("content".equals(part)){ %>
<img id="<%=janiculum.id("content")%>" class="<%=janiculum.cssClass("content")%>" src="chart.png?imagePojo=<%=janiculum.imageId()%>&t=<%=janiculum.currentTime()%>" 
	<%for(String event: janiculum.getAvailableEvents()){ %>
	
		on<%=event%>="romaFieldChanged('<%=janiculum.fieldName() %>'); romaEvent('<%=janiculum.fieldName() %>', '<%=event%>')"
	<%} %>
	
	/>
<%=janiculum.content()==null?"":janiculum.content()%>

<%} %>


<%if (part.equals("label")){%>
<label id="<%=janiculum.id("label")%>" class="<%=janiculum.cssClass("label")%>" for="<%=janiculum.id("content")%>"><%=janiculum.i18NLabel()%></label>
<% } %>

</div>