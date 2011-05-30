<#-- HTML -->
<#if codeToPrint = "html"> 
<table id="${janiculum.id("content")}" class="${janiculum.cssClass(null)}">
	<#if janiculum.isMultiSelection() == false >
			<#assign selection = "single">
		<#else>
			<#assign selection = "multiple">
		</#if>
		<select id="${janiculum.id("content")}" name="${janiculum.fieldName()}" class="${janiculum.cssClass("content")}" size="5" selection="${selection}" <#if janiculum.disabled()> disabled="disabled" </#if>>
	<#assign rowIndex = 0>
	<#foreach opt in janiculum.getChildren()>
					<option id="${janiculum.id("item")}_${rowIndex}" value="${janiculum.fieldName()}_${rowIndex}"
						<#if janiculum.isSelected(rowIndex)>	selected="selected" </#if>
						<#if janiculum.isMultiSelection()>
							name="${janiculum.fieldName()}_${rowIndex}"
						</#if>				
						<#if janiculum.isSingleSelection()>
							name="${janiculum.fieldName()}"
						</#if>	
					>
					<@raw component=opt />
					</option>
			<#assign rowIndex = rowIndex + 1>	
		</#foreach>
		</select>
<table id="${janiculum.id("list_actions")}">
		<tr>
			<td>
				<input type="submit" name="${janiculum.action("onAdd")}" value="${janiculum.i18N("DynaComponent.add.label")}" id="${janiculum.cssClass("add_button")}" class="table_actions_add"/>
			</td>
			<td>
				<input type="submit" name="${janiculum.action("onEdit")}" value="${janiculum.i18N("DynaComponent.edit.label")}" id="${janiculum.cssClass("edit_button")}" class="table_actions_edit"/>
			</td>			
			<td>
				<input type="submit" name="${janiculum.action("onView")}" value="${janiculum.i18N("DynaComponent.view.label")}" id="${janiculum.cssClass("view_button")}" class="table_actions_view"/>
			</td>			
			<td>
				<input type="submit" name="${janiculum.action("onRemove")}" value="${janiculum.i18N("DynaComponent.remove.label")}" id="${janiculum.cssClass("remove_button")}" class="table_actions_remove"/>
			</td>			
			<td>
				<input type="submit" name="${janiculum.action("onUp")}" value="${janiculum.i18N("DynaComponent.up.label")}" id="${janiculum.cssClass("up_button")}" class="table_actions_up"/>
			</td>
			<td>
				<input type="submit" name="${janiculum.action("onDown")}" value="${janiculum.i18N("DynaComponent.down.label")}" id="${janiculum.cssClass("down_button")}" class="table_actions_down"/>
			</td>		
		</tr>		
	</table>	
</#if>