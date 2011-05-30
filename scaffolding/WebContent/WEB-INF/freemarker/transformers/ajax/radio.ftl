<#if part = "" || part = "all" || part = "content" >
	<div class="${janiculum.cssClass(null)}" style="${janiculum.inlineStyle(null)}" id="${janiculum.id(null)}">
		<span class="${janiculum.cssClass('content')}" id="${janiculum.id('content')}">
			<#if janiculum.getChildren()?? >
				<#assign i = 0 >
				<#assign selected = false >
				<#foreach opt in janiculum.getChildren()>
				 	<@raw component=opt />	
				 	<input type="radio" name="${janiculum.fieldName()}" value="${janiculum.fieldName()}_${i}" 
				 	<#if janiculum.isSelected(i)> checked="checked" </#if> 
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
						onchange="romaFieldChanged('${janiculum.fieldName()}'); romaSendAjaxRequest();"
					</#if>
				  	/> 
					<#assign i = i+1 >	
				</#foreach>
				
				 
				</select>
			</#if>
		</span>
	</div>
</#if>


<#if part="label">
	${janiculum.i18NLabel()}
</#if>
