<#-- HTML -->

<#if codeToPrint = "html">
	<#if janiculum.isField()>
		<input class="${janiculum.cssClass(null)}" id="${janiculum.id(null)}" type="image" src="static/base/image/${janiculum.imageLabel()}" alt="${janiculum.content()!""}" name="${janiculum.event("")} <#if janiculum.disabled()> disabled="disabled" </#if> />
	</#if>
	<#if janiculum.isAction()>
		<input class="${janiculum.cssClass(null)}" id="${janiculum.id(null)}" type="image" src="static/base/image/${janiculum.imageLabel()}" alt="${janiculum.i18NLabel()!""}" name="${janiculum.actionName()} <#if janiculum.disabled()> disabled="disabled" </#if> />
	</#if>
</#if>