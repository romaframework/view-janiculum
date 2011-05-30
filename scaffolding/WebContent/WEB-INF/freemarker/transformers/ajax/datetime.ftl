
<div class="${janiculum.cssClass(null)}" style="${janiculum.inlineStyle(null)}" id="${janiculum.id(null)}">

<#if janiculum.isValid() = false>
	<input id="${janiculum.id("content")}" class="${janiculum.cssClass("content")}_invalid" type="text" name="${janiculum.fieldName()}" value="${janiculum.formatDateContent()}" <#if janiculum.disabled()> disabled="disabled" </#if> 
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
		</#if> />
	<span class="${janiculum.cssClass("validation_message")}"></span>
<#else>	
<input id="${janiculum.id("content")}" class="${janiculum.cssClass("content")}" type="text" name="${janiculum.fieldName()}" value="${janiculum.formatDateContent()}" <#if janiculum.disabled()> disabled="disabled" </#if> 
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
		</#if> />
<input id="${janiculum.id("time")}" class="${janiculum.cssClass("content")}" type="text" name="${janiculum.fieldName()}_time" value="${janiculum.formatDateContent("HH:mm:ss")}" <#if janiculum.disabled()> disabled="disabled" </#if> 
<#assign existsChangeEvent=false>
		<#list janiculum.availableEvents() as event>
		<#if event != "change">
		on${event}="romaFieldChanged('${janiculum.fieldName()}_time'); romaEvent('${janiculum.fieldName()}_time', '${event}')"
		<#else>
		<#assign existsChangeEvent=true>
		</#if>
		</#list>
		<#if existsChangeEvent>
		onchange="romaFieldChanged('${janiculum.fieldName()}_time'); romaEvent('${janiculum.fieldName()}_time', 'change')"
		<#else>
		onchange="romaFieldChanged('${janiculum.fieldName()}_time')"
		</#if> />
</#if>

<@js>
	jQuery('#${janiculum.id("content")}').datepicker({ dateFormat: 'dd/mm/yy' });
	jQuery('#${janiculum.id("time")}').timeEntry({spinnerImage: '${janiculum.contextPath()}/static/base/image/spinnerDefault.png', show24Hours: true, showSeconds: true});		
</@js>
