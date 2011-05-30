<#if codeToPrint = "html">

<#if janiculum.isField()>
<button class="${janiculum.cssClass(null)}" id="${janiculum.id(null)}" type="submit" value="${janiculum.content()}" name="${janiculum.event("change")}"
<#if janiculum.disabled()> disabled="disabled" </#if>
>		
${janiculum.content()}
</button></#if>

<#if janiculum.isAction()>
<button class="${janiculum.cssClass(null)}" id="${janiculum.id(null)}" type="submit" value="${janiculum.i18NLabel()}" name="${janiculum.actionName()}"
<#if janiculum.disabled()> disabled="disabled" </#if>
>		
${janiculum.i18NLabel()}
</button></#if></#if>