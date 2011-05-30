<#-- Code HTML -->

<#if codeToPrint = "html">

<#-- Part ALL -->
<#if part = "" || part = "all"  >

<div class="${janiculum.cssClass(null)}" id="${janiculum.id(null)}">

<input id="${janiculum.id("content")}" class="${janiculum.cssClass(null)}" type="text" name="${janiculum.fieldName()}" value="${janiculum.content()!""}" <#if janiculum.disabled()> disabled="disabled" </#if> />
<#if janiculum.isValid() = false>
	<span class="${janiculum.cssClass("validation_message")}"></span>	
</#if>

</div>

</#if>

<#-- Part RAW -->
<#if part = "raw"  >
${janiculum.content()!""}"
</#if>

<#-- Part LABEL -->
<#if part = "label"  >

<label id="${janiculum.id("label")}" class="${janiculum.cssClass("label")}" for="${janiculum.id("content")}">${janiculum.i18NLabel()}</label>

</#if>


</#if>