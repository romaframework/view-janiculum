<#if part != "raw" && part!="label">
	<div class="${janiculum.cssClass(null)}" style="${janiculum.inlineStyle(null)} width:400px;" id="${janiculum.id(null)}">
	<#-- Part ALL -->
	<#if part = "" || part = "all"  >
	<table>
		<tr>
			<td id="${janiculum.id("content")}" />
			<td>${janiculum.content(true)!0}%</td>
		</tr>
	</table>
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
	$(function() {
		$( "#${janiculum.id("content")}" ).progressbar({
			value: parseFloat(String(${janiculum.content(true)!0}).replace(',','.'))
		});
	});
</@js>