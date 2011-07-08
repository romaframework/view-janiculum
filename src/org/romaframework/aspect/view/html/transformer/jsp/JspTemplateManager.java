package org.romaframework.aspect.view.html.transformer.jsp;

import java.io.OutputStream;
import java.util.Map;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

public class JspTemplateManager {

	private String		templatesPath		= "WEB-INF/freemarker/transformers/";
	protected Log			log							= LogFactory.getLog(getClass());

	protected boolean	cacheTemplates	= true;

	public void execute(String templateName, Map<String, Object> ctx, OutputStream out) {
		// TODO eliminate this or implement...?
	}

	public String getTemplatesPath() {
		return templatesPath;
	}

	public void setTemplatesPath(String templatesPath) {
		this.templatesPath = templatesPath;
	}

}
