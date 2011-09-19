<%@page import="org.romaframework.aspect.view.html.constants.TransformerConstants"%><%@page import="org.romaframework.aspect.view.html.area.HtmlViewRenderable"%><%@page import="org.romaframework.aspect.view.html.transformer.jsp.directive.JspTransformerHelper"%><%@ page language="java" contentType="text/html; charset=UTF-8"	pageEncoding="UTF-8"%>
<%@page import="java.util.Set"%><%@page import="org.romaframework.aspect.view.html.transformer.jsp.JspTransformer"%><%@page import="org.romaframework.aspect.view.html.transformer.helper.JaniculumWrapper"%><%@page import="org.romaframework.aspect.view.html.constants.RequestConstants"%><%@page import="java.util.Map"%><%
	Map<String, Object> ctx = (Map<String, Object>) request.getAttribute(RequestConstants.CURRENT_CONTEXT_IN_TRANSFORMER);
	JaniculumWrapper janiculum = (JaniculumWrapper)ctx.get(JspTransformer.JANICULUM);
	pageContext.setAttribute("janiculum", janiculum);
	String part = (String) ctx.get(JspTransformer.PART_TO_PRINT);
	pageContext.setAttribute("part", part);
	
	String vAlign = janiculum.areaVerticalAlignment();
	String hAlign = janiculum.areaHorizontalAlignment();
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
	JspTransformerHelper.addCss(janiculum.id(null)+" > div.POJO > table.area_main", "margin-left", marginLeft);
	JspTransformerHelper.addCss(janiculum.id(null)+" > div.POJO > table.area_main", "margin-right", marginRight);
	
	JspTransformerHelper.addCss(janiculum.id(null)+" > table", "margin-left", marginLeft);
	JspTransformerHelper.addCss(janiculum.id(null)+" > table", "margin-right", marginRight);
%>

<table cellpaddign="0" cellspacing="0" id="<%=janiculum.id(null)%>" class="<%=janiculum.cssClass(null)%> grid-table" style="<%=janiculum.inlineStyle(null)%>">
<%
int row = -1;
int col = 0;
for(Object c:janiculum.getChildren()){
	HtmlViewRenderable child =(HtmlViewRenderable)c;
	if(col%janiculum.areaSize()==0){

%>
<tr>
<%		row++;
	}
	JspTransformerHelper.addCss(janiculum.id(null)+"_"+row+"_"+col, "vertical-align", janiculum.areaVerticalAlignment(child));
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
	JspTransformerHelper.addCss(janiculum.id(null)+"_"+row+"_"+col+" > table", "margin-left", marginLeftChild);
	JspTransformerHelper.addCss(janiculum.id(null)+"_"+row+"_"+col+" > table", "margin-right", marginRightChild);
	
	JspTransformerHelper.addCss(janiculum.id(null)+"_"+row+"_"+col+" > div.POJO > table.area_main", "margin-left", marginLeftChild);
	JspTransformerHelper.addCss(janiculum.id(null)+"_"+row+"_"+col+" > div.POJO > table.area_main", "margin-right", marginRightChild);
	if("justify".equals(hAlignChild)){
		JspTransformerHelper.addCss(janiculum.id(null)+"_"+row+"_"+col+" > table", "width", "100%");
		JspTransformerHelper.addCss(janiculum.id(null)+"_"+row+"_"+col+" > div.POJO > table.area_main", "width", "100%");
	}
%>
<td id="<%=janiculum.id(null)%>_<%=row%>_<%=col%>" class="row_<%=row%> col_<%=col%>"><% JspTransformerHelper.delegate(child, null,pageContext.getOut());%></td>
<%
	if(col%janiculum.areaSize()==janiculum.areaSize()-1){
%>
</tr>
<%
	col = 0;
	}else{
		col++;
	}
}
if(col%janiculum.areaSize()!=0){
	for(int i=0; i<janiculum.areaSize() - (col%janiculum.areaSize())-1; i++){
		%><td></td><%
	}
	%></tr><%
}
%>
</table>