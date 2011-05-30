<#if codeToPrint = "html">
<div id="${janiculum.id(null)}" class="${janiculum.cssClass(null)}">
	<span>
		<input type="text" disabled="disabled" value="${janiculum.content()!""}"/>
	</span>
	<#if !janiculum.disabled()>
	<span>
		<input id="${janiculum.id("open")}" class="${janiculum.cssClass("open")}" value="open" name="${janiculum.action("open")}" type="submit">
	</span>
	<span>
		<input id="${janiculum.id("reset")}" class="${janiculum.cssClass("reset")}" value="reset" name="${janiculum.action("reset")}" type="submit">
	</span>
	</#if>	
</div>                        
</#if>