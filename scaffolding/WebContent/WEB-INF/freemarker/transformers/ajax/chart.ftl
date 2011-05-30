<div class="${janiculum.cssClass(null)}" id="${janiculum.id(null)}" style="${janiculum.inlineStyle(null)}">

<#-- Part ALL -->
<#if part = "" || part = "all"  >


<span id="${janiculum.id("content")}" class="${janiculum.cssClass("content")}" >
<img id="${janiculum.id("content")}_img" class="${janiculum.cssClass("content")}" src="chart.png?imagePojo=${janiculum.imageId()}&t=${janiculum.currentTime()}" 
		<#list janiculum.availableEvents() as event>
		on${event}="romaFieldChanged('${janiculum.fieldName()}'); romaEvent('${janiculum.fieldName()}', '${event}')"
		</#list>/>
</span>

</#if>


<#-- Part CONTENT -->
<#if part = "content"  >
<img id="${janiculum.id("content")}" class="${janiculum.cssClass("content")}" src="chart.png?imagePojo=${janiculum.imageId()}&t=${janiculum.currentTime()}" 
<#list janiculum.availableEvents() as event>
		on${event}="romaFieldChanged('${janiculum.fieldName()}'); romaEvent('${janiculum.fieldName()}', '${event}')"
		</#list>/>
${janiculum.content()!""}
</span>
</#if>

<#-- Part LABEL -->
<#if part = "label"  >

<label id="${janiculum.id("label")}" class="${janiculum.cssClass("label")}" for="${janiculum.id("content")}">${janiculum.i18NLabel()}</label>

</#if>


</div>