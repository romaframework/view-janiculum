<%@page import="org.romaframework.aspect.view.html.component.HtmlViewContentComponent"%>
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
	String pId = janiculum.id(null);
	int index = 0;
%>
<div id="<%=janiculum.id(null)%>"	class="<%=janiculum.cssClass(null)%>" style="<%=janiculum.inlineStyle(null)%>" inited="false">
  <div id="<%=pId%>">
	  <ul>
	  <%index = tree(janiculum.getComponent(), janiculum.getChildren(), "open", out, janiculum, index); %>
    </ul>			
  </div>	
  <input id="<%=janiculum.id("hidden")%>" class="<%=janiculum.cssClass(null)%>" style="<%=janiculum.inlineStyle(null)%>" type="hidden" name="<%=janiculum.fieldName()%>"  />
</div>
<%!

private int tree(HtmlViewRenderable comp, Collection<?> children, String cssClass, JspWriter out, JaniculumWrapper janiculum, int index){
	try{
		if(children.size()==0){
			out.print("<li id=\""+comp.getHtmlId()+"_content\">");
			out.print("<a ");
			if(janiculum.isSelected(index)){
				out.print("class=\"clicked\" ");
			}
			HtmlViewContentComponent component = (HtmlViewContentComponent)comp;
			out.print("idx=\""+index+"\" >"+component.getContent()==null?"":component.getContent()+"</a></li>");
			index++;
		}else{
			out.print("<li id=\""+comp.getHtmlId()+"_content\" class=\""+cssClass+"\"><a ");
			if(janiculum.isSelected(index)){
				out.print("class=\"clicked\" ");
			}
			HtmlViewContentComponent component = (HtmlViewContentComponent)comp;
			out.print("idx=\""+index+"\" >"+component.getContent()==null?"":component.getContent()+"</a></li>");
			index++;
			out.print("<ul>");
			for(Object c:children){
				HtmlViewContentComponent child = (HtmlViewContentComponent)c;
				index = tree((HtmlViewRenderable)child, child.getChildren(), "open", out, janiculum, index);
			}
			out.print("</ul>");
			out.print("</li>");
		}
	}catch(Exception e){}
	return index;
}
%>

<%
StringBuffer buffer = new StringBuffer();
buffer.append("jQuery(\"#"+pId+"\").tree({");
buffer.append("	callback : {");
buffer.append(		"onselect : function() {");
buffer.append("			jQuery(\"#"+janiculum.id("hidden")+"\").attr('value', $.tree_reference('"+pId+"').selected.find(\"a\").attr(\"idx\"));");
buffer.append("			romaFieldChanged('"+janiculum.fieldName()+"');");
buffer.append("			romaSendAjaxRequest();");
buffer.append("		}");
buffer.append("	}");
buffer.append("});");
buffer.append("if ($.tree_reference(\""+pId+"\") != null) {");
buffer.append("	$.tree_reference(\""+pId+"\").open_all();");
buffer.append("}");
JspTransformerHelper.addJs(janiculum.id(TransformerConstants.PART_ALL), buffer.toString());
%>
