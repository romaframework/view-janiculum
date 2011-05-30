<#if codeToPrint = "html">
<div id="${janiculum.id(null)}" class="${janiculum.cssClass(null)}" style="${janiculum.inlineStyle(null)}">
<table>
<tr>
	<td>
		<input type="text" disabled="disabled" value="${janiculum.content(true)!""}"
		<#list janiculum.availableEvents() as event>
    	<#if event == ".default_event" ><#assign eventName = "click">
    	<#elseif event == ".DEFAULT_EVENT" ><#assign eventName = "click">
    	<#else><#assign eventName = event></#if>
    	on${eventName}="romaEvent('${janiculum.fieldName()}', '${event}')"
		</#list>
	</td>
	<#if !janiculum.disabled()>
		<td>
			<input id="${janiculum.id("open")}" class="${janiculum.cssClass("open")}" value="" name="${janiculum.event("open")}" type="button"
			onclick="romaEvent('${janiculum.fieldName()}', 'open')"
			/>
		</td>
		<td>
			<input id="${janiculum.id("reset")}" class="${janiculum.cssClass("reset")}" value="" name="${janiculum.action("reset")}" type="button"
			onclick="romaEvent('${janiculum.fieldName()}', 'reset')"/
			>
		</td>
	</#if>	
</tr>
</table>
</div>                        
</#if>