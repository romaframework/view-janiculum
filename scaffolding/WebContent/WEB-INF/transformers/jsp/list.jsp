<%@page import="org.romaframework.aspect.view.html.transformer.jsp.directive.JspTransformerHelper"%>
<%@page import="org.romaframework.aspect.view.html.area.HtmlViewRenderable"%>
<%@ page language="java" contentType="text/html; charset=UTF-8"	pageEncoding="UTF-8"%><%@taglib uri="http://java.sun.com/jstl/core" prefix="c" %><%@page import="java.util.Set"%><%@page import="org.romaframework.aspect.view.html.transformer.jsp.JspTransformer"%><%@page import="org.romaframework.aspect.view.html.transformer.helper.JaniculumWrapper"%><%@page import="org.romaframework.aspect.view.html.constants.RequestConstants"%><%@page import="java.util.Map"%><%
	Map<String, Object> ctx = (Map<String, Object>) request.getAttribute(RequestConstants.CURRENT_CONTEXT_IN_TRANSFORMER);
	JaniculumWrapper janiculum = (JaniculumWrapper)ctx.get(JspTransformer.JANICULUM);
	pageContext.setAttribute("janiculum", janiculum);
	String part = (String) ctx.get(JspTransformer.PART_TO_PRINT);
	pageContext.setAttribute("part", part);
%>
<div class="<%=janiculum.cssClass(null)%>" style="<%=janiculum.inlineStyle(null)%>" id="<%=janiculum.id(null)%>">
<%
String selection = "single";
if(!janiculum.isMultiSelection()){
	selection = "multiple";
}
%>
        <table>
        <tr>
        <td class="list_box">
        <select id="<%=janiculum.id("content")%>" name="<%=janiculum.fieldName()%>" class="<%=janiculum.cssClass("content")%>"  size="5" selection="<%=selection%>" >
        <%
        int rowIndex = 0;
        for(Object o:janiculum.getChildren()){
        	HtmlViewRenderable opt = (HtmlViewRenderable) o;
        %>
                    <option id="<%=janiculum.id("item")%>_<%=rowIndex%>" value="<%=janiculum.fieldName()%>_<%=rowIndex%>"
                        <%=janiculum.isSelected(rowIndex)?"selected=\"selected\"":""%>
                        <%if(janiculum.isMultiSelection()){%>
                            onclick="romaMultiSelectChanged('<%=janiculum.fieldName()%>_<%=rowIndex%>'); romaSendAjaxRequest();"
                        <%}else{ %>
                        
                            onclick="romaFieldChanged('<%=janiculum.fieldName()%>'); romaSendAjaxRequest();"
                        <%} %>  
                    >
                    <%=JspTransformerHelper.raw(opt) %>
                    </option>
            <%
            rowIndex++;
        }
            %>   
        </select>
        <%if(!janiculum.isValid()){%>
          <span class="<%=janiculum.cssClass("validation_message")%>"><%=janiculum.validationMessage()==null?"Invalid":janiculum.validationMessage()%></span> 
        <% } %>
        </td>
    <%if(janiculum.disabled() == false && janiculum.selectionAviable()){%>
        <td>
        <table id="<%=janiculum.id("list_actions")%>">
            <tr>
                <td>
                    <input type="button" name="<%=janiculum.event("add")%>" value="" 
                    id="<%=janiculum.id("add_button")%>" class="table_actions_add"
                    onclick="romaEvent('<%=janiculum.fieldName()%>', 'add')" />
                </td>
            </tr>
            <tr>
                <td>
                    <input type="button" name="<%=janiculum.event("edit")%>" value="" 
                    id="<%=janiculum.id("edit_button")%>" class="table_actions_edit"
                    onclick="romaEvent('<%=janiculum.fieldName()%>', 'edit')" />
                </td>
            </tr>
            <tr>
                <td>
                    <input type="button" name="<%=janiculum.event("view")%>" value="" 
                    id="<%=janiculum.id("view_button")%>" class="table_actions_view"
                    onclick="romaEvent('<%=janiculum.fieldName()%>', 'view')" />
                </td>
            </tr>
            <tr>
                <td>
                    <input type="button" name="<%=janiculum.event("remove")%>" value="" 
                    id="<%=janiculum.id("remove_button")%>" class="table_actions_remove"
                    onclick="romaEvent('<%=janiculum.fieldName()%>', 'remove')" />
                </td>
            </tr>
            <tr>
                <td>
                    <input type="button" name="<%=janiculum.action("up")%>" value="" 
                    id="<%=janiculum.id("up_button")%>" class="table_actions_up"
                    onclick="romaEvent('<%=janiculum.fieldName()%>', 'up')" />
                </td>
            </tr>
            <tr>
                <td>
                    <input type="button" name="<%=janiculum.action("down")%>" value="" 
                    id="<%=janiculum.id("down_button")%>" class="table_actions_down"
                    onclick="romaEvent('<%=janiculum.fieldName()%>', 'down')" />
                </td>       
            </tr>       
        </table>
        </td>
        </tr>   
    <%} %>
    </table>
</div>