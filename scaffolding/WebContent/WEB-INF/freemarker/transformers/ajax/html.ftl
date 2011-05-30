<#-- Code HTML -->

<#if codeToPrint = "html">

<#-- Part ALL -->
<#if part = "" || part = "all"  >

<div class="${janiculum.cssClass(null)}" id="${janiculum.id(null)}">

${janiculum.content()!""}
</div>

</#if>


<#-- Part CONTENT -->
<#if part = "content"  >
${janiculum.content()!""}
</#if>

<#-- Part RAW -->
<#if part = "raw"  >
${janiculum.content()!""}
</#if>

<#-- Part LABEL -->
<#if part = "label"  >

<label id="${janiculum.id("label")}" class="${janiculum.cssClass("label")}" for="${janiculum.id("content")}">${janiculum.i18NLabel()}</label>

</#if>


</#if>