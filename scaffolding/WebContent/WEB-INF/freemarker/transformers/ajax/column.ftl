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

<table id="${janiculum.id(null)}" class="${janiculum.cssClass(null)} area-column" style="${janiculum.inlineStyle(null)}" cellpadding="0" cellspacing="0">
  <#assign row = 0 >
  <#list janiculum.getChildren() as child>
    <@css selector="#${janiculum.id(null)}_${row}" property="vertical-align">${janiculum.areaVerticalAlignment(child)}</@css>
    <#-- <@css selector="#${janiculum.id(null)}_${row}" property="text-align">${janiculum.areaHorizontalAlignment(child)}</@css> -->
      
		<#assign hAlignChild = janiculum.areaHorizontalAlignment(child)>
		
		<#if hAlignChild == "left" >
  			<#assign marginLeftChild = "0">
  			<#assign marginRightChild = "auto">
		<#elseif hAlignChild == "right">
  			<#assign marginLeftChild = "auto">
  			<#assign marginRightChild = "0">
		<#else>
  			<#assign marginLeftChild = "">
  			<#assign marginRightChild = "">
		</#if>

    	<#-- <@css selector="#${janiculum.id(null)}_${row} > table" property="text-align">left</@css> -->
    	<@css selector="#${janiculum.id(null)}_${row} > table" property="margin-left">${marginLeftChild}</@css>
		<@css selector="#${janiculum.id(null)}_${row} > table" property="margin-right">${marginRightChild}</@css>
    	<#-- <@css selector="#${janiculum.id(null)}_${row} > div.POJO > table.area_main" property="text-align">left</@css> -->
    	<@css selector="#${janiculum.id(null)}_${row} > div.POJO > table.area_main" property="margin-left">${marginLeftChild}</@css>
		<@css selector="#${janiculum.id(null)}_${row} > div.POJO > table.area_main" property="margin-right">${marginRightChild}</@css>
		
		<#if hAlignChild == "justify">
			<@css selector="#${janiculum.id(null)}_${row} > table" property="width">100%</@css>
			<@css selector="#${janiculum.id(null)}_${row} > div.POJO > table.area_main" property="width">100%</@css>
    	</#if>
    
    <tr><td id="${janiculum.id(null)}_${row}" class="row_${row}"><@delegate component=child/></td></tr>
    <#assign row = row + 1 >
  </#list>
</table>
