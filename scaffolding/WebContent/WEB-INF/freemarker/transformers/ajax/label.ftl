<#if part != "raw" && part!="label">
<div class="${janiculum.cssClass(null)}" style="${janiculum.inlineStyle(null)}" id="${janiculum.id(null)}" 
	<#if !janiculum.disabled() >
		<#list janiculum.availableEvents() as event>
    		<#if event == ".default_event" ><#assign eventName = "click">
    		<#elseif event == ".DEFAULT_EVENT" ><#assign eventName = "click">
    		<#else><#assign eventName = event></#if>
    		on${eventName}="romaEvent('${janiculum.fieldName()}', '${event}')"
		</#list>
	</#if>
	 <span id="${janiculum.id("content")}" class="${janiculum.cssClass("content")}" >
    	${janiculum.formattedContent()!""}
	 </span>
</div>
</#if>

<#if part = "raw">${janiculum.formattedContent()!""}</#if>

<#if part = "label"  >
	<label id="${janiculum.id("label")}" class="${janiculum.cssClass("label")}" for="${janiculum.id("content")}">${janiculum.i18NLabel()}</label>
</#if>
