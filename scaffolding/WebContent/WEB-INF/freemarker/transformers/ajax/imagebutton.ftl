<#if part != "raw" && part!="label">
    <#if janiculum.content()??>
        <div class="${janiculum.cssClass(null)}" style="${janiculum.inlineStyle(null)}" id="${janiculum.id(null)}">
        
            <img id="${janiculum.id("content")}" alt="${janiculum.i18NHint()!""}" title="${janiculum.i18NHint()!""}" class="${janiculum.cssClass("content")}" src="${janiculum.contextPath()}/image.png?imagePojo=${janiculum.imageId()}" 
            <#list janiculum.availableEvents() as event>
            on${event}="romaEvent('${janiculum.fieldName()}', '${event}')"
            </#list>
            />
    
        </div>
    </#if>
</#if>

<#-- Part LABEL -->
<#if part = "label">
    <label id="${janiculum.id("label")}" class="${janiculum.cssClass("label")}" for="${janiculum.id("content")}">${janiculum.i18NLabel()}</label>
</#if>
