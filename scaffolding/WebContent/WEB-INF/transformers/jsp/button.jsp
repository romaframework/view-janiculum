<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@taglib uri="http://java.sun.com/jstl/core" prefix="c" %> 
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

<c:if test="${janiculum.field}">
	<button class="<%=janiculum.cssClass("content")%>" id="<%=janiculum.id("content")%>" type="button" value="<%=janiculum.content(true)==null?"":janiculum.content(true)%>" name="<%=janiculum.event("change")%>"
	<c:if test="${janiculum.disabled}"> disabled="disabled" </c:if>
	<c:forEach var="item" items="${janiculum.availableEvents}">
		<c:set var="eventType" value="${event}"/>
		<c:choose>
			<c:when test="${event == '.default_event'}">
				<c:set var="eventName" value="click"/>
			</c:when>
			<c:when test="${event == '.DEFAULT_EVENT'}">
				<c:set var="eventName" value="click"/>
			</c:when>
			<c:otherwise>
				<c:set var="eventName" value="${event}"/>
			</c:otherwise>
		</c:choose>
		on<c:out value="${eventName}"/>="romaEvent('<%=janiculum.fieldName()%>', '<c:out value="${event}"/>')"
	</c:forEach>
	
	>
	<%=janiculum.content(true)==null?"":janiculum.content(true)%>
	</button>
</c:if>


<c:if test="${janiculum.action}">
	<button class="<%=janiculum.cssClass("content")%>" id="<%=janiculum.id("content")%>" type="button" value="<%=janiculum.i18NLabel()%>" name="<%=janiculum.actionName()%>"
	<c:if test="${janiculum.disabled}"> disabled="disabled" </c:if>
	onclick="romaAction('<%=janiculum.actionName()%>')"
	>
	<%=janiculum.i18NLabel()%>
	</button>
</c:if>
</div>