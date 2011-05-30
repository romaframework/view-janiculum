<#-- HTML -->
<#if codeToPrint = "html"> 
<table id="${janiculum.id("content")}" class="${janiculum.cssClass(null)}">
	<#if janiculum.isSingleSelection()>
		<tr>		
			<th>
				<input class="${janiculum.cssClass("selection")}" name="${janiculum.fieldName()}" value="${janiculum.fieldName()}_-1"  type="radio">
			</th>
			<th>${janiculum.i18N("Object.deselectAll.label")}</th>		
		</tr>
	</#if>
	<#assign rowIndex = 0>
	<#list janiculum.getChildren() as elems>
		<tr id="${janiculum.id("item")}_${rowIndex}">
			<td>
				<#if janiculum.isMultiSelection()>
					<input class="${janiculum.cssClass("selection")}" name="${janiculum.fieldName()}_${rowIndex}" <#if janiculum.isSelected(rowIndex)> checked="checked" </#if> type="checkbox">
				</#if>				
				<#if janiculum.isSingleSelection()>
					<input class="${janiculum.cssClass("selection")}" name="${janiculum.fieldName()}" <#if janiculum.isSelected(rowIndex)> checked="checked" </#if> value="${janiculum.fieldName()}_${rowIndex}" type="radio">
				</#if>	
			</td>
			<td>
				<span><@delegate component=elems /></span>
				<hr />
			</td>
		</tr>
		
		<#assign rowIndex = rowIndex + 1>	
	</#list>		
</table>
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