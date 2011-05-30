<#assign valign = janiculum.areaVerticalAlignment()>
<#if valign == "center">
  <#assign valign = "middle">
</#if>
<#assign halign = janiculum.areaHorizontalAlignment()>

<table cellpaddign="0" cellspacing="0" id="${janiculum.id(null)}" class="${janiculum.cssClass(null)} colset-table" style="${janiculum.inlineStyle(null)}">
	<tr>
		<#assign col = 0 >
		<#list janiculum.getChildren() as child>
			<td id="${janiculum.id(null)}_${col}_td"><@delegate component=child/></td>
	  		<@css selector="#${janiculum.id(null)}_${col}_td" property="vertical-align">${valign}</@css>
      		<@css selector="#${janiculum.id(null)}_${col}_td" property="text-align">${halign}</@css>
      		<#assign col = col + 1 >
		</#list>
	</tr>
</table>
