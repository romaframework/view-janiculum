<#-- Code HTML -->

<#if codeToPrint = "html">

<#-- Part ALL -->
<#if part = "" || part = "all"  >

<div class="${janiculum.cssClass(null)}" id="${janiculum.id(null)}">

<span id="${janiculum.id("content")}" class="${janiculum.cssClass("content")}" >
<img id="${janiculum.id("content")}" class="${janiculum.cssClass("content")}" src="chart.png?imagePojo=${janiculum.imageId()}" />
</span>
</div>

</#if>


<#-- Part CONTENT -->
<#if part = "content"  >
<img id="${janiculum.id("content")}" class="${janiculum.cssClass("content")}" src="chart.png?imagePojo=${janiculum.imageId()}" />
${janiculum.content()!""}
</span>
</#if>

<#-- Part LABEL -->
<#if part = "label"  >

<label id="${janiculum.id("label")}" class="${janiculum.cssClass("label")}" for="${janiculum.id("content")}">${janiculum.i18NLabel()}</label>

</#if>


</#if>