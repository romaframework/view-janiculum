<#-- HTML -->
<div class="${janiculum.cssClass(null)}" style="${janiculum.inlineStyle(null)}" id="${janiculum.id(null)}" inited="false">
	<#if janiculum.isAction()>
		<a class="${janiculum.cssClass("content")}" id="${janiculum.id("content")}" value="${janiculum.i18NLabel()}" href="javascript:void(0)" title="${janiculum.i18NHint()}"
		<#if janiculum.disabled()> disabled="disabled"</#if>
			onclick="romaAction('${janiculum.actionName()}')">
			<label for="${janiculum.id("content")}" id="${janiculum.id("label")}" class="${janiculum.cssClass("label")}">
			${janiculum.i18NLabel()}
			</label> 
		</a>
	<#else>
			<label for="${janiculum.id("content")}" id="${janiculum.id("label")}" class="${janiculum.cssClass("label")}">
			${janiculum.i18NLabel()}
			</label> 
	</#if>
	
	<#if janiculum.haveChildren()>
	<ul class="${janiculum.cssClass("content")}"  id="${janiculum.id("content")}">
		<#list janiculum.getChildren() as child>
			<li class="${janiculum.cssClass("content")}">
					<@delegate component=child ></@delegate>							
			</li>			
		</#list>
	</ul>
	</#if>
</div>
<#if part != "content">
	
	<@js>

		if(jQuery("#${janiculum.id(null)}").attr("inited") == "true"){
		// do nothing
		}else{
			jquerycssmenu.buildmenu("${janiculum.id(null)}", arrowimages);
			jQuery("#${janiculum.id(null)}").attr("inited", "true");
		}
		
	</@js>
</#if>