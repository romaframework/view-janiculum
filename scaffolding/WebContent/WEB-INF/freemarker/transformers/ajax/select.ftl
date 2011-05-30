<#if part = "" || part = "all" || part = "content" >
	<div class="${janiculum.cssClass(null)}" id="${janiculum.id(null)}" style="${janiculum.inlineStyle(null)}">
		<span class="${janiculum.cssClass('content')}" id="${janiculum.id('content')}">
			<#if janiculum.getChildren()?? >
				<select id="${janiculum.id('select')}"
				name="${janiculum.fieldName()}"
				<#if janiculum.disabled()> disabled="disabled" </#if>
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
				onchange="romaFieldChanged('${janiculum.fieldName()}'); romaSendAjaxRequest()"
				</#if>
				style="<#if janiculum.isValid() = false>border-color:red;"</#if>"
				>
				
				<option value="${janiculum.fieldName()}_-1" ></option>
				<#assign i = 0 >
				<#assign selected = false >
				<#foreach opt in janiculum.getChildren()>
					<option value="${janiculum.fieldName()}_${i}" <#if janiculum.isSelected(i)>	selected="selected"	</#if>> 
					 <@raw component=opt />
					</option>	
					<#assign i = i+1 >	
				</#foreach>
				</select>
				<#if janiculum.isValid() = false>
                    <span class="${janiculum.cssClass("validation_message")}">${janiculum.validationMessage()!"Invalid"}</span> 
                </#if>
			</#if>
		</span>
	</div>
</#if>


<#if part="label">
	${janiculum.i18NLabel()}
</#if>
