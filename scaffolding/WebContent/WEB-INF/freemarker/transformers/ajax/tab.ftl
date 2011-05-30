<div id="${janiculum.id(null)}" class="${janiculum.cssClass(null)}" style="${janiculum.inlineStyle(null)}">
	<div id="${janiculum.id("tabs")}" class="${janiculum.cssClass("tabs")} ui-tabs-nav">
		<ul>
			<#assign selected_id ="">
			<#assign empty = true>
			<#list janiculum.getChildren() as child>
				<#assign empty = false>
				<#if janiculum.isSelected(child_index) >
					<#assign current_class = "ui-tabs-selected">
					<#assign selected_id ="${child_index}">
				<#else>
					<#assign current_class = "">
				</#if>
				<li class="${current_class}" onclick="changeTab(${janiculum.fieldName()},${child_index})"><a><span >${child.label}</span></a></li>
			</#list>
		</ul>
	</div>
	<input type="hidden" id="${janiculum.id(null)}_selected" name="${janiculum.fieldName()}" value="${janiculum.fieldName()}_${selected_id}"  />

	<#list janiculum.getChildren() as child >
		<#if janiculum.isSelected(child_index) >
			<#assign current_class = "ui-tabs-panel">
		<#else>
			<#assign current_class = "ui-tabs-panel ui-tabs-hide">
		</#if>
		<div class="${current_class}" ><@delegate component=child/></div>
	</#list>
</div>

<@js>
<#if empty == false >
	<#if selected_id == "" >
		changeTab(${janiculum.fieldName()},${janiculum.selectedIndexesAsString()})
	</#if>
</#if>
</@js>
