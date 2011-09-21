<%@page import="org.romaframework.aspect.view.html.transformer.jsp.directive.JspTransformerHelper"%>
<%@page import="org.romaframework.aspect.view.html.area.HtmlViewRenderable"%>
<%@page import="org.romaframework.aspect.view.html.transformer.helper.JaniculumWrapper"%>
<%@page import="org.romaframework.aspect.view.html.transformer.jsp.JspTransformer"%>
<%@page import="org.romaframework.aspect.view.html.constants.RequestConstants"%>
<%@page import="java.util.Map"%>
<%@ page language="java" contentType="text/html; charset=UTF-8"	pageEncoding="UTF-8"%>
<%@page import="org.romaframework.aspect.view.html.area.HtmlViewRenderable"%>
<%@page import="org.romaframework.aspect.view.html.constants.RequestConstants"%>
<%
	
	HtmlViewRenderable component = (HtmlViewRenderable)request.getAttribute(RequestConstants.CURRENT_COMPONENT_IN_TRANSFORMER);
	
	String part = (String) request.getAttribute(RequestConstants.CURRENT_COMPONENT_PART_IN_TRANSFORMER);
	pageContext.setAttribute("part", part);

	if ("".equals(part) || "all".equals(part)|| "content".equals(part)){   %>
	<div class="<%=JaniculumWrapper.cssClass(component, "radio", null)%>" style="<%=JaniculumWrapper.inlineStyle(component, null)%>" id="<%=JaniculumWrapper.id(component, null)%>">
		<span class="<%=JaniculumWrapper.cssClass(component, "radio", "content")%>" id="<%=JaniculumWrapper.id(component, "content")%>">
			<%if(JaniculumWrapper.getChildren(component)!=null){
				int i = 0;
				boolean selected = false;
				for(Object o : JaniculumWrapper.getChildren(component)){
					HtmlViewRenderable opt =(HtmlViewRenderable)o;
					%>
					<%=JspTransformerHelper.raw(opt)%>
				 	<input type="radio" name="<%=JaniculumWrapper.fieldName(component)%>" value="<%=JaniculumWrapper.fieldName(component)%>_<%=i%>" 
				 	<%=JaniculumWrapper.isSelected(component, i)?" checked=\"checked\" ":""%> 
				 	<%=JaniculumWrapper.disabled(component)?" disabled=\"disabled\" ":""%>
				 	<%
				 	boolean existsChangeEvent=false;
					for(String event: JaniculumWrapper.availableEvents(component)){
						if(!"change".equals(event)){%>
							on<%=event%>="romaFieldChanged('<%=JaniculumWrapper.fieldName(component)%>'); romaEvent('<%=JaniculumWrapper.fieldName(component)%>', '<%=event%>')"
						<%}else{
							existsChangeEvent=true;
						}
					}
					if(existsChangeEvent){
					%>
						onchange="romaFieldChanged('<%=JaniculumWrapper.fieldName(component)%>'); romaEvent('<%=JaniculumWrapper.fieldName(component)%>', 'change')"
					<%}else{ %>
						onchange="romaFieldChanged('<%=JaniculumWrapper.fieldName(component)%>'); romaSendAjaxRequest(component);"
					<%}
					i++;
					%>
				  	/> 
				<%} %>	
				</select>
			<%} %>
		</span>
	</div>
<%}else if("label".equals(part)){ %><%=JaniculumWrapper.i18NLabel(component)%><%} %>