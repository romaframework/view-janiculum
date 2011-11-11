<%@page import="org.romaframework.aspect.view.html.area.HtmlViewRenderable"%><%@page import="org.romaframework.aspect.view.html.transformer.jsp.directive.JspTransformerHelper"%><%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%> <%@page import="java.util.Set"%><%@page import="org.romaframework.aspect.view.html.transformer.jsp.JspTransformer"%><%@page import="org.romaframework.aspect.view.html.transformer.helper.JaniculumWrapper"%><%@page import="org.romaframework.aspect.view.html.constants.RequestConstants"%><%@page import="java.util.Map"%><%
	
	HtmlViewRenderable component = (HtmlViewRenderable)request.getAttribute(RequestConstants.CURRENT_COMPONENT_IN_TRANSFORMER);
	
	String vAlign = JaniculumWrapper.areaVerticalAlignment(component);
	String hAlign = JaniculumWrapper.areaHorizontalAlignment(component);
	String marginLeft = "";
	if("left".equals(hAlign)){
		marginLeft = "0";
	}else if("right".equals(hAlign)){
		marginLeft = "auto";
	}
	
	String marginRight = "";
	if("right".equals(hAlign)){
		marginRight = "0";
	}else if("left".equals(hAlign)){
		marginRight = "auto";
	}
	
	JspTransformerHelper.addCss(JaniculumWrapper.id(component, null)+" > div.POJO > table.area_main", "margin-left", marginLeft);
	JspTransformerHelper.addCss(JaniculumWrapper.id(component, null)+" > div.POJO > table.area_main", "margin-right", marginRight);
	JspTransformerHelper.addCss(JaniculumWrapper.id(component, null)+" > table", "margin-left", marginLeft);
	JspTransformerHelper.addCss(JaniculumWrapper.id(component, null)+" > table", "margin-right", marginRight);
%>
<table id="<%=JaniculumWrapper.id(component, null)%>" class="<%=JaniculumWrapper.cssClass(component, "row", null)%>" style="<%=JaniculumWrapper.inlineStyle(component, null)%>" cellpadding="0" cellspacing="0">
  <tr>
  <%
  int col = 0;
  for(Object c:JaniculumWrapper.getChildren(component)){
  	HtmlViewRenderable child=(HtmlViewRenderable) c;
 	JspTransformerHelper.addCss(JaniculumWrapper.id(component, null)+"_"+col, "vertical-align", JaniculumWrapper.areaVerticalAlignment(child));
 	String hAlignChild = JaniculumWrapper.areaHorizontalAlignment(child);
 	String marginLeftChild = "";
 	String marginRightChild = "";
 	if("left".equals(hAlignChild)){
 		marginLeftChild = "0";
 		marginRightChild = "auto";
 	}else if("right".equals(hAlignChild)){
 		marginLeftChild = "auto";	
 		marginRightChild = "0";
 	}
 	JspTransformerHelper.addCss(JaniculumWrapper.id(component, null)+"_"+col+" > table", "margin-left", marginLeftChild);
 	JspTransformerHelper.addCss(JaniculumWrapper.id(component, null)+"_"+col+" > table", "margin-right", marginRightChild);
 	
 	JspTransformerHelper.addCss(JaniculumWrapper.id(component, null)+"_"+col+" > div.POJO > table.area_main", "margin-left", marginLeftChild);
 	JspTransformerHelper.addCss(JaniculumWrapper.id(component, null)+"_"+col+" > div.POJO > table.area_main", "margin-right", marginRightChild);
 	
 	if("justify".equals(hAlignChild)){
 		JspTransformerHelper.addCss(JaniculumWrapper.id(component, null)+"_"+col+" > table", "width", "100%");
 		JspTransformerHelper.addCss(JaniculumWrapper.id(component, null)+"_"+col+" > div.POJO > table.area_main", "width", "100%");
 	}
  %>
      <td id="<%=JaniculumWrapper.id(component, null)%>_<%=col%>" class="col_<%=col %> <%=JaniculumWrapper.cssClass(child, null, null)%>"><%JspTransformerHelper.delegate(child, null,pageContext.getOut()); %></td>
  <%
  
	col++;
  }
  %>
  </tr>
</table>