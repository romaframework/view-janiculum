<#-- HTML -->
<#if codeToPrint = "html">
<#assign headers = janiculum.headers()>

    <div id="${janiculum.id(null)}" class="${janiculum.cssClass(null)}" style="${janiculum.inlineStyle(null)}">
        <table id="${janiculum.id("content")}"  class="${janiculum.cssClass("content")}">
            <thead id="${janiculum.id("header")}" class="${janiculum.cssClass("header")}">
                <tr class="${janiculum.cssClass("header_row")}">
                    <#if janiculum.selectionAviable()>
                        <#if janiculum.isMultiSelection()>
                                <th id="${janiculum.id("selectionCheck")}_-1" class="table_selection"><span> 
                                <input class="table_selection elementName_reservoirDogs invisible_field" name="${janiculum.fieldName()}_-1" type="checkbox" 
                                 checked="checked" /> 
                                </span>#</th>
                        </#if>
                        <#if janiculum.isSingleSelection()>
                                <th id="${janiculum.id("selectionCheck")}_-1"> 
                                <input class="table_selection" name="${janiculum.fieldName()}" type="radio" 
                                value="${janiculum.fieldName()}_-1"
                                onclick="romaFieldChanged('${janiculum.fieldName()}'); romaSendAjaxRequest()"  /> 
                                </th>
                        </#if>
                    </#if>
                    <#list headers as header>
                        <th>${header}</th>
                    </#list>
                </tr>
            </thead>
            <tbody>
                <#assign rowIndex = 0>
                <#list janiculum.getChildren() as rows>
                    <tr id="${janiculum.id("row")}_${rowIndex}" class="${janiculum.tableRowCssClass(rowIndex)}">
                        <#if janiculum.selectionAviable()>
                            <td id="${janiculum.id("selectionCheck")}_${rowIndex}" class="table_selection">
                                 
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
              
                             </td>
                        </#if>
                        <#list rows.getChildrenFilled() as column>
                            <td class="${janiculum.cssSpecificClass(column,null)}"><@delegate component=column /></td>
                        </#list >
                    </tr>
                    <#assign rowIndex = rowIndex+1>
                </#list >
            </tbody>
        </table>
    
        <#if janiculum.disabled() == false  && janiculum.selectionAviable()>
            <table id="${janiculum.id("table_actions")}">
                <tr>
                    <td>
                        <input type="button" name="${janiculum.event("add")}" value="${janiculum.i18N("DynaComponent.add.label")}" 
                        id="${janiculum.cssClass("add_button")}" class="table_actions_add"
                        onclick="romaEvent('${janiculum.fieldName()}', 'add')" />
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
    jQuery("#${janiculum.id("content")}").tablesorter();
</@js>