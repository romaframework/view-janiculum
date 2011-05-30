<#if part != "raw" && part!="label">
	<div class="${janiculum.cssClass(null)}" style="${janiculum.inlineStyle(null)}" id="${janiculum.id(null)}">
		<textarea id="${janiculum.id("content")}" class="${janiculum.cssClass(null)}" style="${janiculum.inlineStyle(null)}" name="${janiculum.fieldName()}" <#if janiculum.disabled()> disabled="disabled" </#if> 
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
			<span class="${janiculum.cssClass("validation_message")}"></span>	
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

<@js>
	var ed = CKEDITOR.instances.${janiculum.id("content")};
	if(ed != undefined){
		CKEDITOR.remove(ed);
	}	
	$('#${janiculum.id("content")}').ckeditor();
	
	ed = CKEDITOR.instances.${janiculum.id("content")};
	ed.on('key', function ( e ) {
		var myTextField = document.getElementById('${janiculum.id("content")}');
		myTextField.value = ed.getData();
		var fun = $('#${janiculum.id("content")}').attr("onChange");
		eval(""+fun+";");
	} );
	
</@js>