package org.romaframework.aspect.view.html.template;

import java.io.OutputStream;
import java.util.Map;

public interface TemplateManager {

	public void execute(String templateName, Map<String, Object> ctx, OutputStream out);
	
	public String getTemplatesPath();
	
	public void setTemplatesPath(String templatesPath);
	
	public boolean isCacheTemplates();
	
	public void setCacheTemplates(boolean cacheTemplates);


}
