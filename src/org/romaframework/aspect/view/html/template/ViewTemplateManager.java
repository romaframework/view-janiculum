package org.romaframework.aspect.view.html.template;

import java.io.Writer;
import java.util.Map;

public interface ViewTemplateManager {

	public void execute(String templateName, Map<String, Object> ctx, Writer writer);
	
	public String getTemplatesPath();
	
	public void setTemplatesPath(String templatesPath);
	
	public boolean isCacheTemplates();
	
	public void setCacheTemplates(boolean cacheTemplates);


}
