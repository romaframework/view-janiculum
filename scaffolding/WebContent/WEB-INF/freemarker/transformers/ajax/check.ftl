<#if part != "raw" && part != "label"  >
<div class="${janiculum.cssClass(null)}" style="${janiculum.inlineStyle(null)}" id="${janiculum.id(null)}">

<#if part = "" || part = "all">
<input id="${janiculum.id("reset")}" type="hidden" name="${janiculum.fieldName()}_reset" />
<input id="${janiculum.id("content")}" class="${janiculum.cssClass(null)}" style="${janiculum.inlineStyle(null)}" type="checkbox" name="${janiculum.fieldName()}" <#if janiculum.checked()> checked="checked" </#if> <#if janiculum.disabled()> disabled="disabled" </#if> 
<#assign existsChangeEvent=false>
		<#list janiculum.availableEvents() as event>
		<#if event != "change">
		on${event}="romaFieldChanged('${janiculum.fieldName()}'); romaFieldChanged('${janiculum.fieldName()}_reset'); romaEvent('${janiculum.fieldName()}', '${event}')"
		<#else>
		<#assign existsChangeEvent=true>
		</#if>
		</#list>
		<#if existsChangeEvent>
		onchange="romaFieldChanged('${janiculum.fieldName()}'); romaFieldChanged('${janiculum.fieldName()}_reset'); romaEvent('${janiculum.fieldName()}', 'change')"
		<#else>
		onchange="romaFieldChanged('${janiculum.fieldName()}'); romaFieldChanged('${janiculum.fieldName()}_reset'); romaSendAjaxRequest()"
		</#if> />
<#if janiculum.isValid() = false>
	<span class="${janiculum.cssClass("validation_message")}"></span>	
</#if>
</#if>
</div>
</#if>

<#if part = "raw">${janiculum.content(true)!""}"</#if>

<#if part = "label">
<label id="${janiculum.id("label")}" class="${janiculum.cssClass("label")}" for="${janiculum.id("content")}">${janiculum.i18NLabel()}</label>
</#if>