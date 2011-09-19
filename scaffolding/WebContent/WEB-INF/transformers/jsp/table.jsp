<%@page import="java.util.Collection"%>
<%@page import="org.romaframework.aspect.view.html.component.HtmlViewGenericComponent"%>
<%@page import="org.romaframework.aspect.view.html.constants.TransformerConstants"%>
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
	String codeToPrint = (String) ctx.get(JspTransformer.CODE_TO_PRINT);

	if ("html".equals(codeToPrint)){
	%>

    <div id="<%=janiculum.id(null)%>" class="<%=janiculum.cssClass(null)%>" style="<%=janiculum.inlineStyle(null)%>">
        <table id="<%=janiculum.id("content")%>"  class="<%=janiculum.cssClass("content")%>">
            <thead id="<%=janiculum.id("header")%>" class="<%=janiculum.cssClass("header")%>">
                <tr class="<%=janiculum.cssClass("header_row")%>">
                    <%if(janiculum.selectionAviable()){
                        if(janiculum.isMultiSelection()){ %>
                                <th class="table_selection">#</th>
                        <%}
                        if(janiculum.isSingleSelection()){
                        %>
                        
                                <th id="<%=janiculum.id("selectionCheck")%>_-1"><span> 
                                <input class="table_selection elementName_reservoirDogs" name="<%=janiculum.fieldName()%>" type="radio" 
                                value="<%=janiculum.fieldName()%>_-1"
                                onclick="romaFieldChanged('<%=janiculum.fieldName()%>'); romaSendAjaxRequest()" /> 
                                </span></th>
                        <%}
                       }
                    for(String header: janiculum.headers()){
                        %><th><%=header%></th>
                    <%} %>
                </tr>
            </thead>
            <tbody>
            <%
            int  rowIndex = 0;
            String evenOdd = "";
            for(Object r:janiculum.getChildren()){
            	HtmlViewGenericComponent rows = (HtmlViewGenericComponent)r;
            	if(rowIndex % 2 == 0){
            		evenOdd ="even";
            	}else{
            		evenOdd ="odd";
            	}
            %>
    
                    <tr id="<%=janiculum.id("row")%>_<%=rowIndex%>" class="<%=janiculum.tableRowCssClass(rowIndex)%> <%=evenOdd%>">
                        <%if(janiculum.selectionAviable()){%>
                            <td id="<%=janiculum.id("selectionCheck")%>_<%=rowIndex%>" class="table_selection">
                                <span>
                                <%if(janiculum.isMultiSelection()){%>
                                    <input class="<%=janiculum.cssClass("selection")%>" name="<%=janiculum.fieldName()%>_<%=rowIndex%>" 
                                    <%if(janiculum.isSelected(rowIndex)){%> checked="checked" <%} %>  type="checkbox" 
                                    onclick="romaMultiSelectChanged('<%=janiculum.fieldName()%>_<%=rowIndex%>'); romaSendAjaxRequest()" />
                                <%}
                                if(janiculum.isSingleSelection()){
                                %>
                                
                                    <input class="<%=janiculum.cssClass("selection")%>" name="<%=janiculum.fieldName()%>" type="radio" 
                                    <%if(janiculum.isSelected(rowIndex)){%> checked="checked" <%} %> value="<%=janiculum.fieldName()%>_<%=rowIndex %>" 
                                    onclick="romaFieldChanged('<%=janiculum.fieldName()%>'); romaSendAjaxRequest()" />
                                <%} %>
                                </span>
                            </td>
                        <%} 
                        for(HtmlViewGenericComponent column:rows.getChildrenFilled()){
                        %>
                        
                            <td class="<%=janiculum.cssSpecificClass(column,null)%>">
                            <%=JspTransformerHelper.raw(column) %>
                            </td>
                        <%} %>
                    </tr>
                    <%
                    	rowIndex++;
            }
                    %>
                    
            </tbody>
        </table>
        <%if(!janiculum.disabled() && janiculum.selectionAviable()){%>
            <table id="<%=janiculum.id("table_actions")%>">
                <tr>
                    <td>
                        <input type="button" name="<%=janiculum.event("add")%>" value="<%=janiculum.i18N("DynaComponent.add.label")%>" 
                        id="<%=janiculum.id("add_button")%>" class="table_actions_add"
                        onclick="romaEvent('<%=janiculum.fieldName()%>', 'add')" />
                    </td>
                    <td>
                        <input type="button" name="<%=janiculum.event("edit")%>" value="<%=janiculum.i18N("DynaComponent.edit.label")%>" 
                        id="<%=janiculum.id("edit_button")%>" class="table_actions_edit"
                        onclick="romaEvent('<%=janiculum.fieldName()%>', 'edit')" />
                    </td>           
                    <td>
                        <input type="button" name="<%=janiculum.event("view")%>" value="<%=janiculum.i18N("DynaComponent.view.label")%>" 
                        id="<%=janiculum.id("view_button")%>" class="table_actions_view"
                        onclick="romaEvent('<%=janiculum.fieldName()%>', 'view')" />
                    </td>           
                    <td>
                        <input type="button" name="<%=janiculum.event("remove")%>" value="<%=janiculum.i18N("DynaComponent.remove.label")%>" 
                        id="<%=janiculum.id("remove_button")%>" class="table_actions_remove"
                        onclick="romaEvent('<%=janiculum.fieldName()%>', 'remove')" />
                    </td>           
                    <td>
                        <input type="button" name="<%=janiculum.action("up")%>" value="<%=janiculum.i18N("DynaComponent.up.label")%>" 
                        id="<%=janiculum.id("up_button")%>" class="table_actions_up"
                        onclick="romaEvent('<%=janiculum.fieldName()%>', 'up')" />
                    </td>
                    <td>
                        <input type="button" name="<%=janiculum.action("down")%>" value="<%=janiculum.i18N("DynaComponent.down.label")%>" 
                        id="<%=janiculum.id("down_button")%>" class="table_actions_down"
                        onclick="romaEvent('<%=janiculum.fieldName()%>', 'down')" />
                    </td>       
                </tr>       
            </table>
        <%} %>
    </div>
<%} 
	
	JspTransformerHelper.addJs(janiculum.id(TransformerConstants.PART_ALL), "$(\"#"+janiculum.id("content")+"\").tablesorter({ headers: { 0: { sorter: false } } });");
%>