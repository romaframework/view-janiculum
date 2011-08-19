<%@page import="org.romaframework.aspect.view.html.transformer.jsp.directive.JspTransformerHelper"%>
<%@page import="org.romaframework.aspect.view.html.area.HtmlViewRenderable"%>
<%@page import="org.romaframework.aspect.view.html.transformer.helper.JaniculumWrapper"%>
<%@page import="org.romaframework.aspect.view.html.transformer.jsp.JspTransformer"%>
<%@page import="org.romaframework.aspect.view.html.constants.RequestConstants"%>
<%@page import="java.util.Map"%>
<%@ page language="java" contentType="text/html; charset=UTF-8"	pageEncoding="UTF-8"%>
<%
	Map<String, Object> ctx = (Map<String, Object>) request.getAttribute(RequestConstants.CURRENT_CONTEXT_IN_TRANSFORMER);
	JaniculumWrapper janiculum = (JaniculumWrapper)ctx.get(JspTransformer.JANICULUM);
	pageContext.setAttribute("janiculum", janiculum);
	String part = (String) ctx.get(JspTransformer.PART_TO_PRINT);
	pageContext.setAttribute("part", part);

	if ("".equals(part) || "all".equals(part)|| "content".equals(part)){   %>

	<div class="<%=janiculum.cssClass(null)%>" id="<%=janiculum.id(null)%>" style="<%=janiculum.inlineStyle(null)%>">
		<span class="<%=janiculum.cssClass("content")%>" id="<%=janiculum.id("content")%>">
			<%if(janiculum.getChildren()!=null){%>
				<select id="<%=janiculum.id("select")%>"
				name="<%=janiculum.fieldName()%>"
				<%if(janiculum.disabled()){%> disabled="disabled" <%}
					boolean existsChangeEvent=false;
					for(String event: janiculum.availableEvents()){
						if(!"change".equals(event)){	
					
						%>
						
						on<%=event%>="romaFieldChanged('<%=janiculum.fieldName()%>'); romaEvent('<%=janiculum.fieldName()%>', '<%=event%>')"
						<%}else{
							existsChangeEvent = true;
						}
					}
					if(existsChangeEvent){
				%>
				
				
				onchange="romaFieldChanged('<%=janiculum.fieldName()%>'); romaEvent('<%=janiculum.fieldName()%>', 'change')"
				<%}else{ %>
				onchange="romaFieldChanged('<%=janiculum.fieldName()%>'); romaSendAjaxRequest()"
				<%} %>
				style="<%if(!janiculum.isValid()){%>border-color:red;<%} %>"
				>
				
				<option value="<%=janiculum.fieldName()%>_-1" ></option>
				<%
				int i = 0;
				boolean selected = false;
				for(Object o:janiculum.getChildren()){
					HtmlViewRenderable opt = (HtmlViewRenderable)o;
				
				%>
				
					<option value="<%=janiculum.fieldName()%>_<%=i%>" <%if(janiculum.isSelected(i)){%>	selected="selected"	<%} %>> 
					 <%=JspTransformerHelper.raw(opt) %>
					</option>	
					
				<%
					i++;
				} %>
				</select>
				
				<%if(!janiculum.isValid() ){ %>
                    <span class="<%=janiculum.cssClass("validation_message")%>"><%=janiculum.validationMessage()==null?"Invalid":janiculum.validationMessage()%></span> 
                <%} 
                }%>
			
		</span>
	</div>
<%} 
if ("label".equals(part) ){   %>

	<%=janiculum.i18NLabel()%>
<%} %>
