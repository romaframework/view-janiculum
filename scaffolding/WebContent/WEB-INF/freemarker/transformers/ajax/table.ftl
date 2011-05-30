<#if codeToPrint = "html">
<#assign headers = janiculum.headers()>

    <div id="${janiculum.id(null)}" class="${janiculum.cssClass(null)}" style="${janiculum.inlineStyle(null)}">
        <table id="${janiculum.id("content")}"  class="${janiculum.cssClass("content")}">
            <thead id="${janiculum.id("header")}" class="${janiculum.cssClass("header")}">
                <tr class="${janiculum.cssClass("header_row")}">
                    <#if janiculum.selectionAviable()>
                        <#if janiculum.isMultiSelection()>
                                <th class="table_selection">#</th>
                        </#if>
                        <#if janiculum.isSingleSelection()>
                                <th id="${janiculum.id("selectionCheck")}_-1"><span> 
                                <input class="table_selection elementName_reservoirDogs" name="${janiculum.fieldName()}" type="radio" 
                                value="${janiculum.fieldName()}_-1"
                                onclick="romaFieldChanged('${janiculum.fieldName()}'); romaSendAjaxRequest()" /> 
                                </span></th>
                        </#if>
                    </#if>
                    <#list janiculum.headers() as header>
                        <th>${header}</th>
                    </#list>
                </tr>
            </thead>
            <tbody>
                <#assign rowIndex = 0>
                <#list janiculum.getChildren() as rows>
                    <#if rowIndex%2 = 0>
                        <#assign evenodd="even">
                    <#else>
                        <#assign evenodd="odd">
                    </#if>
                    <tr id="${janiculum.id("row")}_${rowIndex}" class="${janiculum.tableRowCssClass(rowIndex)} ${evenodd}">
                        <#if janiculum.selectionAviable()>
                            <td id="${janiculum.id("selectionCheck")}_${rowIndex}" class="table_selection">
                                <span>
                                <#if janiculum.isMultiSelection()>
                                    <input class="${janiculum.cssClass("selection")}" name="${janiculum.fieldName()}_${rowIndex}" 
                                    <#if janiculum.isSelected(rowIndex)> checked="checked" </#if>  type="checkbox" 
                                    onclick="romaMultiSelectChanged('${janiculum.fieldName()}_${rowIndex}'); romaSendAjaxRequest()" />
                                </#if>
                                <#if janiculum.isSingleSelection()>
                                    <input class="${janiculum.cssClass("selection")}" name="${janiculum.fieldName()}" type="radio" 
                                    <#if janiculum.isSelected(rowIndex)> checked="checked" </#if> value="${janiculum.fieldName()}_${rowIndex}" 
                                    onclick="romaFieldChanged('${janiculum.fieldName()}'); romaSendAjaxRequest()" />
                                </#if>
                                </span>
                            </td>
                        </#if>
                        
                        <#list rows.getChildrenFilled() as column>
                            <td class="${janiculum.cssSpecificClass(column,null)}"><@raw component=column /></td>
                        </#list >
                    </tr>
                    <#assign rowIndex = rowIndex+1>
                </#list >
            </tbody>
        </table>
        <#if janiculum.disabled() == false && janiculum.selectionAviable() >
            <table id="${janiculum.id("table_actions")}">
                <tr>
                    <td>
                        <input type="button" name="${janiculum.event("add")}" value="${janiculum.i18N("DynaComponent.add.label")}" 
                        id="${janiculum.cssClass("add_button")}" class="table_actions_add"
                        onclick="romaEvent('${janiculum.fieldName()}', 'add')" />
                    </td>
                    <td>
                        <input type="button" name="${janiculum.event("edit")}" value="${janiculum.i18N("DynaComponent.edit.label")}" 
                        id="${janiculum.cssClass("edit_button")}" class="table_actions_edit"
                        onclick="romaEvent('${janiculum.fieldName()}', 'edit')" />
                    </td>           
                    <td>
                        <input type="button" name="${janiculum.event("view")}" value="${janiculum.i18N("DynaComponent.view.label")}" 
                        id="${janiculum.cssClass("view_button")}" class="table_actions_view"
                        onclick="romaEvent('${janiculum.fieldName()}', 'view')" />
                    </td>           
                    <td>
                        <input type="button" name="${janiculum.event("remove")}" value="${janiculum.i18N("DynaComponent.remove.label")}" 
                        id="${janiculum.cssClass("remove_button")}" class="table_actions_remove"
                        onclick="romaEvent('${janiculum.fieldName()}', 'remove')" />
                    </td>           
                    <td>
                        <input type="button" name="${janiculum.action("up")}" value="${janiculum.i18N("DynaComponent.up.label")}" 
                        id="${janiculum.cssClass("up_button")}" class="table_actions_up"
                        onclick="romaEvent('${janiculum.fieldName()}', 'up')" />
                    </td>
                    <td>
                        <input type="button" name="${janiculum.action("down")}" value="${janiculum.i18N("DynaComponent.down.label")}" 
                        id="${janiculum.cssClass("down_button")}" class="table_actions_down"
                        onclick="romaEvent('${janiculum.fieldName()}', 'down')" />
                    </td>       
                </tr>       
            </table>
        </#if>      
    </div>
</#if>

<#-- JS -->
<@js>
    $("#${janiculum.id("content")}").tablesorter({ headers: { 0: { sorter: false } } });
</@js>