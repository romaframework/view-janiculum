<div class="${janiculum.cssClass(null)}" style="${janiculum.inlineStyle(null)}" id="${janiculum.id(null)}">
<#if janiculum.isField()>
<button class="${janiculum.cssClass("content")}" id="${janiculum.id("content")}" type="button" value="${janiculum.content(true)!""}" name="${janiculum.event("change")}"
<#if janiculum.disabled()> disabled="disabled" </#if>
<#list janiculum.availableEvents() as event>
    <#if event == ".default_event" ><#assign eventName = "click">
    <#elseif event == ".DEFAULT_EVENT" ><#assign eventName = "click">
    <#else><#assign eventName = event></#if>
    on${eventName}="romaEvent('${janiculum.fieldName()}', '${event}')"
</#list>
>
${janiculum.content(true)!""}
</button></#if>
<#if janiculum.isAction()>
<button class="${janiculum.cssClass("content")}" id="${janiculum.id("content")}" type="button" value="${janiculum.i18NLabel()}" name="${janiculum.actionName()}"
<#if janiculum.disabled()> disabled="disabled"</#if>
onclick="romaAction('${janiculum.actionName()}')"
>
${janiculum.i18NLabel()}
</button></#if>
</div>