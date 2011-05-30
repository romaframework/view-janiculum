package org.romaframework.aspect.view.html.template.freemarker;

import java.io.File;
import java.io.IOException;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.io.StringWriter;
import java.util.HashMap;
import java.util.Map;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.romaframework.core.Utility;
import org.romaframework.core.config.RomaApplicationContext;

import freemarker.template.Configuration;
import freemarker.template.DefaultObjectWrapper;
import freemarker.template.Template;

public class FreeMarkerTemplateManager {

	public static String						templatesPath	= "WEB-INF/freemarker/transformers/";
	protected Log										log						= LogFactory.getLog(getClass());
	protected Configuration					configuration;
	protected boolean								cacheTemplates = true;

	protected Map<String, Template>	cache					= new HashMap<String, Template>();

	public void execute(String templateName, Map<String, Object> ctx, OutputStream out) {
		try {
			Configuration conf = getConfiguration();
			Template template = null;
			if(!cacheTemplates){
				template = conf.getTemplate(templateName);
			}else{
				template = cache.get(templateName);
				if(template==null){
					template = conf.getTemplate(templateName);
					cache.put(templateName, template);
				}
			}
			Map<String, Object> root = ctx;
			OutputStreamWriter writer = new OutputStreamWriter(out);
			template.process(root, writer);
		} catch (Exception e) {
			log.warn("could not acquire FreeMarker configuration, maybe you are on an expired session", e);
		}
	}

	private Configuration getConfiguration() throws IOException {
		if (configuration == null) {
			configuration = new Configuration();
			File file = null;
			if (templatesPath.startsWith(Utility.PATH_SEPARATOR_STRING)) {
				file = new File(templatesPath);
			} else {
				file = new File(RomaApplicationContext.getApplicationPath() + "/" + templatesPath);
			}
			log.debug(file.getAbsolutePath());
			configuration.setDirectoryForTemplateLoading(file);
			configuration.setObjectWrapper(new DefaultObjectWrapper());
		}
		return configuration;
	}

	public String getTemplatesPath() {
		return templatesPath;
	}

	public void setTemplatesPath(String templatesPath) {
		FreeMarkerTemplateManager.templatesPath = templatesPath;
	}

	public boolean isCacheTemplates() {
		return cacheTemplates;
	}

	public void setCacheTemplates(boolean cacheTemplates) {
		this.cacheTemplates = cacheTemplates;
	}

	
}
