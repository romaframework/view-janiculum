<@css selector="#${janiculum.id(null)}" property="vertical-align">${janiculum.areaVerticalAlignment()}</@css>
<@css selector="#${janiculum.id(null)}" property="text-align">${janiculum.areaHorizontalAlignment()}</@css>
<div id="${janiculum.id(null)}" class="${janiculum.cssClass(null)}" style="${janiculum.inlineStyle(null)}">
	<#list janiculum.getChildren() as child>
		<@delegate component=child></@delegate>
	</#list>
</div>
