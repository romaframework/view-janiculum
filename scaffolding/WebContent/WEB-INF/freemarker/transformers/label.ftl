<#-- Code HTML -->

<#if codeToPrint = "html">

<#-- Part ALL -->
<#if part = "" || part = "all"  >

<div class="${janiculum.cssClass(null)}" id="${janiculum.id(null)}">

<span id="${janiculum.id("content")}" class="${janiculum.cssClass("content")}" >
${janiculum.formattedContent()!""}
</span>
</div>

</#if>


<#-- Part CONTENT -->
<#if part = "content"  >
<span id="${janiculum.id("content")}" class="${janiculum.cssClass("content")}" >
${janiculum.formattedContent()!""}
</span>
</#if>

<#-- Part RAW -->
<#if part = "raw"  >
${janiculum.formattedContent()!""}
</#if>

<#-- Part LABEL -->
<#if part = "label"  >

<label id="${janiculum.id("label")}" class="${janiculum.cssClass("label")}" for="${janiculum.id("content")}">${janiculum.i18NLabel()}</label>

</#if>


</#if>