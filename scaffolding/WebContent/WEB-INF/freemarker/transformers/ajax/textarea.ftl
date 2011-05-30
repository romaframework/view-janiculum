<#if part != "raw" && part!="label">
	<div class="${janiculum.cssClass(null)}" style="${janiculum.inlineStyle(null)}" id="${janiculum.id(null)}">
		<textarea id="${janiculum.id("content")}" class="${janiculum.cssClass(null)}" style="<#if janiculum.isValid() = false>border-color:red;"</#if>${janiculum.inlineStyle(null)}" name="${janiculum.fieldName()}" <#if janiculum.disabled()> disabled="disabled" </#if> 
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
		>${janiculum.content(true)!""}</textarea>
		<#if janiculum.isValid() = false>
			<span class="${janiculum.cssClass("validation_message")}">${janiculum.validationMessage()!"Invalid"}</span>
		</#if>
	
	</div>
</#if>

<#-- Part RAW -->
<#if part = "raw"  >
	${janiculum.content(true)!""}
</#if>

<#-- Part LABEL -->
<#if part = "label"  >
	<label id="${janiculum.id("label")}" class="${janiculum.cssClass("label")}" for="${janiculum.id("content")}">${janiculum.i18NLabel()}</label>
</#if>
