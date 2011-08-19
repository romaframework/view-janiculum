<%@ page language="java" contentType="text/html; charset=UTF-8"	pageEncoding="UTF-8"%><%@taglib uri="http://java.sun.com/jstl/core" prefix="c" %><%@page import="java.util.Set"%><%@page import="org.romaframework.aspect.view.html.transformer.jsp.JspTransformer"%><%@page import="org.romaframework.aspect.view.html.transformer.helper.JaniculumWrapper"%><%@page import="org.romaframework.aspect.view.html.constants.RequestConstants"%><%@page import="java.util.Map"%><%
	Map<String, Object> ctx = (Map<String, Object>) request.getAttribute(RequestConstants.CURRENT_CONTEXT_IN_TRANSFORMER);
	JaniculumWrapper janiculum = (JaniculumWrapper)ctx.get(JspTransformer.JANICULUM);
	pageContext.setAttribute("janiculum", janiculum);
	String part = (String) ctx.get(JspTransformer.PART_TO_PRINT);
	pageContext.setAttribute("part", part);
	String codeToPrint = (String) ctx.get(JspTransformer.CODE_TO_PRINT);
	pageContext.setAttribute("codeToPrint", codeToPrint);
	
	
if( "html".equals(codeToPrint)){%>
<div id="<%=janiculum.id(null)%>" class="<%=janiculum.cssClass(null)%>" style="<%=janiculum.inlineStyle(null)%>">
<table>
<tr>
	<td>
		<input type="text" disabled="disabled" value="<%=janiculum.content(true)==null?"":janiculum.content(true)%>"
		<%for(String event:janiculum.availableEvents()){
			String eventName = null;
			if("default_event".equals(event)|| "DEFAULT_EVENT".equals(event)){
				eventName = "click";
			}else{
				eventName = event;
			}
		%>
    	
    	on<%=eventName%>="romaEvent('<%=janiculum.fieldName()%>', '<%=event%>')"
		<%} %> />
	</td>
	<%if(!janiculum.disabled()){%>
		<td>
			<input id="<%=janiculum.id("open")%>" class="<%=janiculum.cssClass("open")%>" value="" name="<%=janiculum.event("open")%>" type="button"
			onclick="romaEvent('<%=janiculum.fieldName()%>', 'open')"
			/>
		</td>
		<td>
			<input id="<%=janiculum.id("reset")%>" class="<%=janiculum.cssClass("reset")%>" value="" name="<%=janiculum.action("reset")%>" type="button"
			onclick="romaEvent('<%=janiculum.fieldName()%>', 'reset')"/
			>
		</td>
	<% }%>
</tr>
</table>
</div>                        
<%} %>