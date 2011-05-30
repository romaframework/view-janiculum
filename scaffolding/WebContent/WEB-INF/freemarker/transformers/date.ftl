<#-- Code HTML -->

<#if codeToPrint = "html">

<div class="${janiculum.cssClass(null)}" id="${janiculum.id(null)}">

<#if janiculum.isValid() = false>
	<input id="${janiculum.id("content")}" class="${janiculum.cssClass("content")}_invalid" type="text" name="${janiculum.fieldName()}" value="${janiculum.formatDateContent()}" <#if janiculum.disabled()> disabled="disabled" </#if> />
	<span class="${janiculum.cssClass("validation_message")}"></span>
<#else>	
<input id="${janiculum.id("content")}" class="${janiculum.cssClass("content")}" type="text" name="${janiculum.fieldName()}" value="${janiculum.formatDateContent()}" <#if janiculum.disabled()> disabled="disabled" </#if> />
</#if>

</div>


</#if>

<@js>
	$('#${janiculum.id("content")}').datepicker({ dateFormat: 'dd/mm/yy' });
</@js>