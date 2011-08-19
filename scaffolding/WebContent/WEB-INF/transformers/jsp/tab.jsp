<%@page import="org.romaframework.aspect.view.html.component.HtmlViewInvisibleContentComponent"%>
<%@page import="org.romaframework.aspect.view.html.constants.TransformerConstants"%>
<%@page import="org.romaframework.aspect.view.html.component.HtmlViewConfigurableEntityForm"%>
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

%>
<div id="<%=janiculum.id(null)%>" class="<%=janiculum.cssClass(null)%>" style="<%=janiculum.inlineStyle(null)%>">
	<div id="<%=janiculum.id("tabs")%>" class="<%=janiculum.cssClass("tabs")%> ui-tabs-nav">
		<ul>
		<%
			String selected_id ="";
			boolean empty = true;
			int childIndex = 0;
			String currentClass = "";
			for(Object c:janiculum.getChildren() ){
				HtmlViewRenderable child = (HtmlViewRenderable) c;
				empty = false;
				if(janiculum.isSelected(childIndex)){
					currentClass = "ui-tabs-selected";
					selected_id =""+childIndex;
				}
				String childLabel = "";
				if(child instanceof HtmlViewConfigurableEntityForm){
					childLabel = ((HtmlViewConfigurableEntityForm)child).getLabel();
				}else if(child instanceof HtmlViewInvisibleContentComponent){
					childLabel = ((HtmlViewInvisibleContentComponent)child).getLabel();
				}
		%>
				<li class="<%=currentClass%>" onclick="changeTab(<%=janiculum.fieldName()%>,<%=childIndex%>)"><a><span ><%=childLabel%></span></a></li>
			<%
				currentClass = "";
				childIndex++;
			} %>
		</ul>
	</div>
	<input type="hidden" id="<%=janiculum.id(null)%>_selected" name="<%=janiculum.fieldName()%>" value="<%=janiculum.fieldName()%>_<%=selected_id%>"  />

<%	
	childIndex = 0;
	for(Object c:janiculum.getChildren() ){
		HtmlViewRenderable child = (HtmlViewRenderable) c;
	
		if((janiculum.isSelected(childIndex))){
			currentClass = "ui-tabs-panel";
		}else{
			currentClass = "ui-tabs-panel ui-tabs-hide";
		}
	 %>

		<div class="<%=currentClass%>" ><%=JspTransformerHelper.delegate(child, null) %></div>
	<%
		childIndex++;
	} %>
</div>
<%if(!empty && "".equals(selected_id)){

	JspTransformerHelper.addJs(janiculum.id(TransformerConstants.PART_ALL), "changeTab("+janiculum.fieldName()+", "+janiculum.selectedIndexesAsString()+");");
}
%>