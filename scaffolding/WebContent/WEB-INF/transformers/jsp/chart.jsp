<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@taglib uri="http://java.sun.com/jstl/core" prefix="c" %> 
<%@page import="java.util.Set"%>
<%@page import="org.romaframework.aspect.view.html.transformer.jsp.JspTemplateManager"%>
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


<% if( part.equals("") || part.equals("all")) {%>


<span id="<%=janiculum.id("content") %>" class="<%=janiculum.cssClass("content") %>" >
<img id="<%=janiculum.id("content")%>_img" class="<%=janiculum.cssClass("content")%>" src="chart.png?imagePojo=<%=janiculum.imageId()%>&t=<%=janiculum.currentTime()%>" 
		<c:forEach var="event" items="${janiculum.availableEvents}" >
		on<c:out value="${event}"/>="romaFieldChanged('<%=janiculum.fieldName() %>'); romaEvent('<%=janiculum.fieldName() %>', '<c:out value="${event}"/>')"
		</c:forEach>
	/>
</span>

<%} %>



<%if (part.equals("content")){ %>
<img id="<%=janiculum.id("content")%>" class="<%=janiculum.cssClass("content")%>" src="chart.png?imagePojo=<%=janiculum.imageId()%>&t=<%=janiculum.currentTime()%>" 
	
	<c:forEach var="event" items="${janiculum.availableEvents}" >
		on<c:out value="${event}"/>="romaFieldChanged('<%=janiculum.fieldName() %>'); romaEvent('<%=janiculum.fieldName() %>', '<c:out value="${event}"/>')"
	</c:forEach>
	
	/>
<%=janiculum.content()==null?"":janiculum.content()%>

<%} %>


<%if (part.equals("label")){%>
<label id="<%=janiculum.id("label")%>" class="<%=janiculum.cssClass("label")%>" for="<%=janiculum.id("content")%>"><%=janiculum.i18NLabel()%></label>
<% } %>

</div>