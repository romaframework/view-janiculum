<#assign vAlign = janiculum.areaVerticalAlignment()>
<#assign hAlign = janiculum.areaHorizontalAlignment()>
<#if hAlign == "left" >
  <#assign marginLeft = "0">
<#elseif hAlign == "right">
  <#assign marginLeft = "auto">
<#else>
  <#assign marginLeft = "">
</#if>

<#if hAlign == "right" >
  <#assign marginRight = "0">
<#elseif hAlign == "left">
  <#assign marginRight = "auto">
<#else>
  <#assign marginRight = "">
</#if>

<@css selector="#${janiculum.id(null)} > div.POJO > table.area_main" property="margin-left">${marginLeft}</@css>
<@css selector="#${janiculum.id(null)} > div.POJO > table.area_main" property="margin-right">${marginRight}</@css>

<@css selector="#${janiculum.id(null)} > table" property="margin-left">${marginLeft}</@css>
<@css selector="#${janiculum.id(null)} > table" property="margin-right">${marginRight}</@css>

<div id="${janiculum.id(null)}" class="${janiculum.cssClass(null)}" style="${janiculum.inlineStyle(null)}">
	<#list janiculum.getChildren() as child>
		<@delegate component=child></@delegate>
	</#list>
</div>
