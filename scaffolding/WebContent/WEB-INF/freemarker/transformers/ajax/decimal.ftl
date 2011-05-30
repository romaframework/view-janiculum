<#if part != "raw" && part!="label">
	<div class="${janiculum.cssClass(null)}" style="${janiculum.inlineStyle(null)}" id="${janiculum.id(null)}">
	<#-- Part ALL -->
	<#if part = "" || part = "all"  >		
		<input id="${janiculum.id("content")}" class="${janiculum.cssClass(null)}" style="${janiculum.inlineStyle(null)}" type="text" name="${janiculum.fieldName()}" value="${janiculum.formatNumberContent()!""}" <#if janiculum.disabled()> disabled="disabled" </#if> 
		<#assign existsChangeEvent=false>
		<#list janiculum.availableEvents() as event>
		<#if event != "change">
		on${event}="romaFieldChanged('${janiculum.fieldName()}'); romaEvent('${janiculum.fieldName()}', '${event}')"
		<#else>
		<#assign existsChangeEvent=true>
		</#if>
		</#list>
		<#if existsChangeEvent>
		onchange="romaFieldChanged('${janiculum.fieldName()}'); romaEvent('${janiculum.fieldName()}', 'change')"
		<#else>
		onchange="romaFieldChanged('${janiculum.fieldName()}')"
		</#if>
		/>
		<#if janiculum.isValid() = false>
			<span class="${janiculum.cssClass("validation_message")}"></span>	
		</#if>
	</#if>
	</div>
</#if>

<#-- Part RAW -->
<#if part = "raw"  >
	${janiculum.content(true)!""}"
</#if>

<#-- Part label -->
<#if part = "label"  >
	<label id="${janiculum.id("label")}" class="${janiculum.cssClass("label")}" for="${janiculum.id("content")}">${janiculum.i18NLabel()}</label>
</#if>

<@js>
	jQuery("#${janiculum.id("content")}").keyup(function(){
		//alert(jQuery(this).attr("value"));
		re = /[^0-9\.,]/ ;
		re2 = /[^0-9\-]/ ;
		
		var beginning = jQuery(this).attr("value").substring(0,1);
		while(beginning.match(re2)){
			beginning = beginning.replace(re2, "");
		}
		
		var end = jQuery(this).attr("value").substring(1);
		
		while(end.match(re)){
			end = end.replace(re, "");
		}

		jQuery(this).attr("value", beginning+end);
	}).keyup();
</@js>