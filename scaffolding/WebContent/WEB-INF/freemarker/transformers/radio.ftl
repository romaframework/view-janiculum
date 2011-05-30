<#-- Code HTML -->
<#if codeToPrint = "html">

	<#if part = "" || part = "all"  >
		<div class="${janiculum.cssClass(null)}" id="${janiculum.id(null)}">
	</#if>
	<#if part = "" || part = "all" || part = "content" >
			<span class="${janiculum.cssClass('content')}" id="${janiculum.id('content')}">
				<#if janiculum.getChildren()?? >
					<#assign i = 0 >
					<#assign selected = false >
					<#foreach opt in janiculum.getChildren()>
					 ${opt}	<input type="radio" name="${janiculum.fieldName()}" value="${janiculum.fieldName()}_${i}" <#if janiculum.isSelected(i)> checked="checked" </#if> <#if janiculum.disabled()> disabled="disabled" </#if> /> 
						<#assign i = i+1 >	
					</#foreach>
					
					 
					</select>
				</#if>
			</span>
			
			<h2>test</h2>
			single selection: <#if janiculum.isSingleSelection()>true<#else>false</#if><br>
			multi selection: <#if janiculum.isMultiSelection()>true<#else>false</#if><br>
			selected indexes as string: ${janiculum.selectedIndexesAsString()}<br>
			1 is selected: <#if janiculum.isSelected(1)>true<#else>false</#if><br>		
	</#if>
	<#if part = "" || part = "all"  >
		</div>
	</#if>
	
	
	<#if part="label">
		${janiculum.i18NLabel()}
	</#if>
</#if>