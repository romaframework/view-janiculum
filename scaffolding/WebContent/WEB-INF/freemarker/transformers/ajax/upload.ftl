<div id="${janiculum.id(null)}" class="${janiculum.cssClass(null)}" style="${janiculum.inlineStyle(null)}">
	<form id="${janiculum.id('form')}" action="${janiculum.contextPath()}/fileUpload" sent="0" method="post" enctype="multipart/form-data" target="${janiculum.id('iframe')}">
		<input name="${janiculum.fieldName()}" type="file" onchange="jQuery('#${janiculum.id('form')}').attr('sent', 1); submit()"/>
	</form>
	<iframe name="${janiculum.id('iframe')}" width="0" height="0" frameborder="0" onload="if(jQuery('#${janiculum.id('form')}').attr('sent') == '1'){jQuery('#${janiculum.id('form')}').attr('sent', 0); romaEvent('${janiculum.fieldName()}', 'upload')}"></iframe>
</div>