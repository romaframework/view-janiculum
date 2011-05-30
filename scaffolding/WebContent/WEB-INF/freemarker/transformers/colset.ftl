<#-- HTML -->
<div class="${janiculum.cssClass(null)} id="${janiculum.id(null)} >

	<table>
		
			<tr>
			<#list janiculum.getChildren() as child>
				<td>
					<@delegate component=child></@delegate>					
				</td>
			</#list>								
			</tr>	
	</table>
</div>