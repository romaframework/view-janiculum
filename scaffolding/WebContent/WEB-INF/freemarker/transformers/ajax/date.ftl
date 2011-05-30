<div class="${janiculum.cssClass(null)}" style="${janiculum.inlineStyle(null)}" id="${janiculum.id(null)}">

<#if janiculum.isValid() = false>
    <input id="${janiculum.id("content")}" class="${janiculum.cssClass("content")}_invalid" type="text" name="${janiculum.fieldName()}" value="${janiculum.formatDateContent()}" <#if janiculum.disabled()> disabled="disabled" </#if>
    <#if janiculum.isValid() = false>style="border-color:red"</#if> 
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
        </#if>  />
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
</#if>
<#if janiculum.isValid() = false>
    <span class="${janiculum.cssClass("validation_message")}">${janiculum.validationMessage()!"Invalid"}</span>
</#if>
</div>

<@js>
    jQuery('#${janiculum.id("content")}').datepicker({ dateFormat: 'dd/mm/yy', yearRange: '1900:2050', changeYear: true, changeMonth: true });
</@js>
