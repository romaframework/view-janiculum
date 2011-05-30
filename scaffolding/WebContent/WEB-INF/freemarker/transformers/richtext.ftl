<#-- Code HTML -->

<#if codeToPrint = "html">

<#-- Part ALL -->
<#if part = "" || part = "all"  >

<div class="${janiculum.cssClass(null)}" id="${janiculum.id(null)}">
<textarea id="${janiculum.id("content")}" class="${janiculum.cssClass(null)}" name="${janiculum.fieldName()}" <#if janiculum.disabled()> disabled="disabled" </#if> >${janiculum.content()!""}</textarea>
<#if janiculum.isValid() = false>
	<span class="${janiculum.cssClass("validation_message")}"></span>	
</#if>

</div>

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

<@js>
	window.onload = function(){ 
		var oFCKeditor = new FCKeditor( '${janiculum.id("content")}' ); 
		oFCKeditor.BasePath = 'static/base/js/';
		oFCKeditor.ReplaceTextarea(); 
	}
</@js>