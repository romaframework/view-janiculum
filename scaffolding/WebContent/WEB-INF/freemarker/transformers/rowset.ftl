<#-- HTML -->
<div class="${janiculum.cssClass(null)} id="${janiculum.id(null)} >

	<table>
		<#list janiculum.getChildren() as child>
			<tr>
				<td>
					<@delegate component=child></@delegate>
					<hr />
				</td>								
			</tr>			
		</#list>
	</table>
</div>
