<%@page import="org.romaframework.aspect.view.html.area.HtmlViewRenderable"%>
<%@page import="org.romaframework.aspect.view.html.transformer.jsp.directive.JspTransformerHelper"%>
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
	String vAlign = janiculum.areaVerticalAlignment();
	String hAlign = janiculum.areaHorizontalAlignment();
	String marginLeft = "";
	if("left".equals(hAlign)){
		marginLeft = "0";
	}else if("right".equals(hAlign)){
		marginLeft = "right";
	}
	
	String marginRight = "";
	if("right".equals(hAlign)){
		marginRight = "0";
	}else if("left".equals(hAlign)){
		marginRight = "auto";
	}
	
	JspTransformerHelper.addCss(janiculum.id(null)+" > div.POJO > table.area_main", "margin-left", marginLeft);
	JspTransformerHelper.addCss(janiculum.id(null)+" > div.POJO > table.area_main", "margin-right", marginRight);
	JspTransformerHelper.addCss(janiculum.id(null)+" > table", "margin-left", marginLeft);
	JspTransformerHelper.addCss(janiculum.id(null)+" > table", "margin-right", marginRight);
%>

<table id="<%=janiculum.id(null)%>" class="<%=janiculum.cssClass(null)%> area-column" style="<%=janiculum.inlineStyle(null)%>" cellpadding="0" cellspacing="0">
  <%
  int row = 0;
  for(Object c:janiculum.getChildren()){
  	HtmlViewRenderable child = (HtmlViewRenderable) c;
  	JspTransformerHelper.addCss(janiculum.id(null)+"_"+row, "vertical-align", janiculum.areaVerticalAlignment(child));
  	
  	String hAlignChild = janiculum.areaHorizontalAlignment(child);
  	
  	String marginLeftChild = "";
  	String marginRightChild = "";
  	if("left".equals(hAlignChild)){
  		marginLeftChild = "0";
  		marginRightChild = "auto";
  	}else if("right".equals(hAlignChild)){
  		marginLeftChild = "auto";
  		marginRightChild = "0";
  	}
  	
  	JspTransformerHelper.addCss(janiculum.id(null)+"_"+row+" > table", "margin-left", marginLeftChild);
  	JspTransformerHelper.addCss(janiculum.id(null)+"_"+row+" > table", "margin-right", marginRightChild);
  	
  	JspTransformerHelper.addCss(janiculum.id(null)+"_"+row+" > div.POJO > table.area_main", "margin-left", marginLeftChild);
  	JspTransformerHelper.addCss(janiculum.id(null)+"_"+row+" > div.POJO > table.area_main", "margin-right", marginRightChild);
  	
  	if("justify".equals(hAlignChild)){
  		JspTransformerHelper.addCss(janiculum.id(null)+"_"+row+" > table", "width", "100%");
  		JspTransformerHelper.addCss(janiculum.id(null)+"_"+row+" > div.POJO > table.area_main", "width", "100%");
  	}
	  %>
	    <tr><td id="<%=janiculum.id(null)%>_<%=row%>" class="row_<%=row%>">
	    <%=JspTransformerHelper.delegate(child, null) %>
		</td></tr>
	  <%
	row++;
  } %>
</table>