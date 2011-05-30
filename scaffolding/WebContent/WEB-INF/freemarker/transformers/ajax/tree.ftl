<#assign pId = janiculum.id(null) >
<#assign index = 0 >

<#-- HTML -->
<div id="${janiculum.id(null)}"	class="${janiculum.cssClass(null)}" style="${janiculum.inlineStyle(null)}" inited="false">
  <div id="${pId}">
	  <ul>
		  <@tree comp=janiculum.component children=janiculum.getChildren() class="open"></@tree>
    </ul>			
  </div>	
  <input id="${janiculum.id("hidden")}" class="${janiculum.cssClass(null)}" style="${janiculum.inlineStyle(null)}" type="hidden" name="${janiculum.fieldName()}"  />
</div>

<#macro tree comp children class>		
		<#if children?size = 0>
			
			<li id="${comp.htmlId}_content"><a <#if janiculum.isSelected(index)>
				class="clicked" 
			</#if> idx="${index}" >${comp.getContent()!""}</a></li>
			<#assign index = index +1 >
		<#else>
			<li id="${comp.htmlId}_content" class="${class}"><a <#if janiculum.isSelected(index)>
				class="clicked" 
			</#if> idx="${index}" >${comp.getContent()!""} </a>
			<#assign index = index +1 >						
				<ul>					
					<#list children as child>
						<@tree comp=child children=child.getChildren() class="open"></@tree>
					</#list>					
				</ul>														
			</li>
		</#if>			
</#macro >

<@js>
	jQuery("#${pId}").tree({
		callback : {
			onselect : function() {			    
			    jQuery("#${janiculum.id("hidden")}").attr('value', $.tree_reference('${pId}').selected.find("a").attr("idx"));			    
			    romaFieldChanged('${janiculum.fieldName()}');
			    romaSendAjaxRequest();				
			}			
		}	
	});
	
	if ($.tree_reference("${pId}") != null) {
		$.tree_reference("${pId}").open_all();
	}
</@js>
