<#-- Code HTML -->
<#if codeToPrint = "html">

	<#if part = "" || part = "all"  >
		<div class="${janiculum.cssClass(null)}" id="${janiculum.id(null)}">
	</#if>
	<#if part = "" || part = "all" || part = "content" >
			<span class="${janiculum.cssClass('content')}" id="${janiculum.id('content')}">
				<#if janiculum.getChildren()?? >
					<select id="${janiculum.id('select')}"
					<#if janiculum.disabled()>
						disabled="disabled"
					</#if>
					name="${janiculum.fieldName()}">
					
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
				</#if>
			</span>
	</#if>
	<#if part = "" || part = "all"  >
		</div>
	</#if>
	
	
	<#if part="label">
		${janiculum.i18NLabel()}
	</#if>
</#if>