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

<table cellpaddign="0" cellspacing="0" id="${janiculum.id(null)}" class="${janiculum.cssClass(null)} grid-table" style="${janiculum.inlineStyle(null)}">
    <#assign row = -1 >
	<#assign col = 0 >
	<#list janiculum.getChildren() as child>
		<#if col%janiculum.areaSize()==0>
			<tr>
            <#assign row = row + 1 >
		</#if>
		
		<@css selector="#${janiculum.id(null)}_${row}_${col}" property="vertical-align">${janiculum.areaVerticalAlignment(child)}</@css>
      	<#-- <@css selector="#${janiculum.id(null)}_${row}_${col}" property="text-align">${janiculum.areaHorizontalAlignment(child)}</@css> -->
      
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

    	<#-- <@css selector="#${janiculum.id(null)}_${row}_${col} > table" property="text-align">left</@css> -->
    	<@css selector="#${janiculum.id(null)}_${row}_${col} > table" property="margin-left">${marginLeftChild}</@css>
		<@css selector="#${janiculum.id(null)}_${row}_${col} > table" property="margin-right">${marginRightChild}</@css>
    	<#-- <@css selector="#${janiculum.id(null)}_${row}_${col} > div.POJO > table.area_main" property="text-align">left</@css> -->
    	<@css selector="#${janiculum.id(null)}_${row}_${col} > div.POJO > table.area_main" property="margin-left">${marginLeftChild}</@css>
		<@css selector="#${janiculum.id(null)}_${row}_${col} > div.POJO > table.area_main" property="margin-right">${marginRightChild}</@css>
		
		<#if hAlignChild == "justify">
			<@css selector="#${janiculum.id(null)}_${row}_${col} > table" property="width">100%</@css>
			<@css selector="#${janiculum.id(null)}_${row}_${col} > div.POJO > table.area_main" property="width">100%</@css>
    	</#if>
		
		<td id="${janiculum.id(null)}_${row}_${col}" class="row_${row} col_${col}"><@delegate component=child/></td>
		<#if col%janiculum.areaSize()==janiculum.areaSize()-1>
			</tr>
			<#assign col = 0 >
		<#else>
			<#assign col = col + 1 >	
		</#if>
	</#list>
	
	<#if col%janiculum.areaSize() != 0>
		<#list 0..janiculum.areaSize()-(col%janiculum.areaSize())-1 as next>
			<td></td>
		</#list>
		</tr>
	</#if>
</table>
