<#-- HTML -->
<#if codeToPrint = "html">
	<div id="${janiculum.id(null)}"	class="${janiculum.cssClass(null)}">
		<table id="${janiculum.id("content")}"	class="${janiculum.cssClass("content")}">
			<thead id="${janiculum.id("header")}" class="${janiculum.cssClass("header")}">
				<tr class="${janiculum.cssClass("header_row")}">
					<#if janiculum.isMultiSelection()>
							<th>#</th>
					</#if>
					<#if janiculum.isSingleSelection()>
							<th id="${janiculum.id("selectionCheck")}_-1"><span> <input class="table_selection elementName_reservoirDogs" name="${janiculum.fieldName()}" type="radio" value="${janiculum.fieldName()}_-1"> </span></th>
					</#if>
					<#list janiculum.headers() as header>
						<th>${header}</th>
					</#list>
				</tr>
			</thead>
			<tbody>
				<#assign rowIndex = 0>
				<#list janiculum.getChildren() as rows>		
					<tr id="${janiculum.id("row")}_${rowIndex}" class="${janiculum.tableRowCssClass(rowIndex)}">
					
						<#if janiculum.isMultiSelection()>
							<td id="${janiculum.id("selectionCheck")}_${rowIndex}"><span> <input class="${janiculum.cssClass("selection")}" name="${janiculum.fieldName()}_${rowIndex}" <#if janiculum.isSelected(rowIndex)> checked="checked" </#if>  type="checkbox"> </span></td>
						</#if>
						<#if janiculum.isSingleSelection()>
							<td id="${janiculum.id("selectionCheck")}_${rowIndex}"><span> <input class="${janiculum.cssClass("selection")}" name="${janiculum.fieldName()}" type="radio" <#if janiculum.isSelected(rowIndex)> checked="checked" </#if> value="${janiculum.fieldName()}_${rowIndex}"> </span></td>
						</#if>
						<#list rows.getChildren() as col>
							<td class="${janiculum.cssSpecificClass(column,null)}"><@raw component=col ></@raw></td>
						</#list >					
					</tr>
					<#assign rowIndex = rowIndex+1>
				</#list >
			</tbody>
		</table>
	</div>
	<table id="${janiculum.id("table_actions")}">
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

<#-- JS -->
<@js>
	$("#${janiculum.id("content")}").tablesorter({  
        headers: { 
            0: {  
                sorter: false 
            } 
        } 
    });
</@js>