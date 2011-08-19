<%@ page language="java" contentType="text/html; charset=UTF-8"	pageEncoding="UTF-8"%><%@taglib uri="http://java.sun.com/jstl/core" prefix="c" %><%@page import="java.util.Set"%><%@page import="org.romaframework.aspect.view.html.transformer.jsp.JspTemplateManager"%><%@page import="org.romaframework.aspect.view.html.transformer.jsp.JspTransformer"%><%@page import="org.romaframework.aspect.view.html.transformer.helper.JaniculumWrapper"%><%@page import="org.romaframework.aspect.view.html.constants.RequestConstants"%><%@page import="java.util.Map"%><%
	Map<String, Object> ctx = (Map<String, Object>) request.getAttribute(RequestConstants.CURRENT_CONTEXT_IN_TRANSFORMER);
	JaniculumWrapper janiculum = (JaniculumWrapper)ctx.get(JspTransformer.JANICULUM);
	pageContext.setAttribute("janiculum", janiculum);
	String part = (String) ctx.get(JspTransformer.PART_TO_PRINT);
	pageContext.setAttribute("part", part);
	String codeToPrint = (String) ctx.get(JspTransformer.CODE_TO_PRINT);
	pageContext.setAttribute("codeToPrint", codeToPrint);
	
	if("html".equals(codeToPrint)){
		if("".equals(part)|| "all".equals(part)){
%>
<div class="<%=janiculum.cssClass(null)%>" id="<%=janiculum.id(null)%>">
<%=janiculum.content()==null?"":janiculum.content()%>
</div>
<%		} else if("content".equals(part) || "raw".equals(part)){
			%><%=janiculum.content()==null?"":janiculum.content()%><%
		}else if("label".equals(part)){
			%><label id="<%=janiculum.id("label")%>" class="<%=janiculum.cssClass("label")%>" for="<%=janiculum.id("content")%>"><%=janiculum.i18NLabel()%></label><%
		}
	}
%>