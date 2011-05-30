<#assign valign = janiculum.areaVerticalAlignment()>
<#if valign == "center">
  <#assign valign = "middle">
</#if>
<#assign halign = janiculum.areaHorizontalAlignment()>

<table cellpadding="0" cellspacing="0" id="${janiculum.id(null)}" class="${janiculum.cssClass(null)} rowset-table" style="${janiculum.inlineStyle(null)}">
    <#assign row = 0 >
    <#list janiculum.getChildren() as child>
        <tr><td id="${janiculum.id(null)}_${row}_td"><@delegate component=child/></td></tr>
    <@css selector="#${janiculum.id(null)}_${row}_td" property="vertical-align">${valign}</@css>
    <@css selector="#${janiculum.id(null)}_${row}_td" property="text-align">${halign}</@css>
    <#assign row = row + 1 >
  </#list>
</table>