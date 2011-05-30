<div class="${janiculum.cssClass(null)}" style="${janiculum.inlineStyle(null)}" id="${janiculum.id(null)}">
<#if janiculum.isField()>
<a class="${janiculum.cssClass("content")}" id="${janiculum.id("content")}" value="${janiculum.content(true)!""}" href="javascript:void(0)"
<#if janiculum.disabled()> disabled="disabled" </#if>
<#list janiculum.availableEvents() as event>
	<#if event == ".default_event" ><#assign eventName = "click">
	<#elseif event == ".DEFAULT_EVENT" ><#assign eventName = "click">
	<#else><#assign eventName = event></#if>
	on${eventName}="romaFieldChanged('${janiculum.fieldName()}'); romaEvent('${janiculum.fieldName()}', '${event}')"
</#list>
>
${janiculum.content(true)!""}
</a></#if>
<#if janiculum.isAction()>
<a class="${janiculum.cssClass("content")}" id="${janiculum.id("content")}" value="${janiculum.i18NLabel()}" href="javascript:void(0)" title="${janiculum.i18NHint()}"
<#if janiculum.disabled()> disabled="disabled"</#if>
onclick="romaAction('${janiculum.actionName()}')"
>
${janiculum.i18NLabel()}
</a></#if>
</div>