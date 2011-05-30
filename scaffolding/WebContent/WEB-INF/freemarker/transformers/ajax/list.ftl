<div class="${janiculum.cssClass(null)}" style="${janiculum.inlineStyle(null)}" id="${janiculum.id(null)}">
        <#if janiculum.isMultiSelection() == false >
            <#assign selection = "single">
        <#else>
            <#assign selection = "multiple">
        </#if>
        <table>
        <tr>
        <td class="list_box">
        <select id="${janiculum.id("content")}" name="${janiculum.fieldName()}" class="${janiculum.cssClass("content")}"  size="5" selection="${selection}" >
        <#assign rowIndex = 0>
        <#foreach opt in janiculum.getChildren()>
                    <option id="${janiculum.id("item")}_${rowIndex}" value="${janiculum.fieldName()}_${rowIndex}"
                        <#if janiculum.isSelected(rowIndex)>    selected="selected" </#if>
                        <#if janiculum.isMultiSelection()>
                            onclick="romaMultiSelectChanged('${janiculum.fieldName()}_${rowIndex}'); romaSendAjaxRequest();"
                        </#if>              
                        <#if janiculum.isSingleSelection()>
                            onclick="romaFieldChanged('${janiculum.fieldName()}'); romaSendAjaxRequest();"
                        </#if>  
                    >
                    <@raw component=opt />
                    </option>
            <#assign rowIndex = rowIndex + 1>   
        </#foreach>
        </select>
        <#if janiculum.isValid() = false>
          <span class="${janiculum.cssClass("validation_message")}">${janiculum.validationMessage()!"Invalid"}</span> 
        </#if>
        </td>
    <#if janiculum.disabled() == false && janiculum.selectionAviable() >
        <td>
        <table id="${janiculum.id("list_actions")}">
            <tr>
                <td>
                    <input type="button" name="${janiculum.event("add")}" value="" 
                    id="${janiculum.cssClass("add_button")}" class="table_actions_add"
                    onclick="romaEvent('${janiculum.fieldName()}', 'add')" />
                </td>
            </tr>
            <tr>
                <td>
                    <input type="button" name="${janiculum.event("edit")}" value="" 
                    id="${janiculum.cssClass("edit_button")}" class="table_actions_edit"
                    onclick="romaEvent('${janiculum.fieldName()}', 'edit')" />
                </td>
            </tr>
            <tr>
                <td>
                    <input type="button" name="${janiculum.event("view")}" value="" 
                    id="${janiculum.cssClass("view_button")}" class="table_actions_view"
                    onclick="romaEvent('${janiculum.fieldName()}', 'view')" />
                </td>
            </tr>
            <tr>
                <td>
                    <input type="button" name="${janiculum.event("remove")}" value="" 
                    id="${janiculum.cssClass("remove_button")}" class="table_actions_remove"
                    onclick="romaEvent('${janiculum.fieldName()}', 'remove')" />
                </td>
            </tr>
            <tr>
                <td>
                    <input type="button" name="${janiculum.action("up")}" value="" 
                    id="${janiculum.cssClass("up_button")}" class="table_actions_up"
                    onclick="romaEvent('${janiculum.fieldName()}', 'up')" />
                </td>
            </tr>
            <tr>
                <td>
                    <input type="button" name="${janiculum.action("down")}" value="" 
                    id="${janiculum.cssClass("down_button")}" class="table_actions_down"
                    onclick="romaEvent('${janiculum.fieldName()}', 'down')" />
                </td>       
            </tr>       
        </table>
        </td>
        </tr>   
    </#if>
    </table>
</div>