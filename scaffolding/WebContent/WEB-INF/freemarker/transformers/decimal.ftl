<#-- Code HTML -->

<#if codeToPrint = "html">

<#-- Part ALL -->
<#if part = "" || part = "all"  >

<div class="${janiculum.cssClass(null)}" id="${janiculum.id(null)}">

<input id="${janiculum.id("content")}" class="${janiculum.cssClass(null)}" type="text" name="${janiculum.fieldName()}" value="${janiculum.formatNumberContent()!""}" <#if janiculum.disabled()> disabled="disabled" </#if> />
<#if janiculum.isValid() = false>
	<span class="${janiculum.cssClass("validation_message")}"></span>	
</#if>
</div>

</#if>

<#-- Part RAW -->
<#if part = "raw"  >
${janiculum.content()!""}"
</#if>

<#-- Part RAW -->
<#if part = "label"  >

<label id="${janiculum.id("label")}" class="${janiculum.cssClass("label")}" for="${janiculum.id("content")}">${janiculum.i18NLabel()}</label>

</#if>


</#if>


<@js>
	$("#${janiculum.id("content")}").keyup(function(){
		//alert($(this).attr("value"));
		re = /[^0-9\.,]/ ;
		re2 = /[^0-9\-]/ ;
		
		var beginning = $(this).attr("value").substring(0,1);
		while(beginning.match(re2)){
			beginning = beginning.replace(re2, "");
		}
		
		var end = $(this).attr("value").substring(1);
		
		while(end.match(re)){
			end = end.replace(re, "");
		}
		

		$(this).attr("value", beginning+end);
		
		
	}).keyup();
</@js>