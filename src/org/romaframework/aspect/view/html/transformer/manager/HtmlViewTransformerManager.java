package org.romaframework.aspect.view.html.transformer.manager;

import java.io.File;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.romaframework.aspect.view.html.template.freemarker.FreeMarkerTemplateManager;
import org.romaframework.aspect.view.html.transformer.Transformer;
import org.romaframework.aspect.view.html.transformer.freemarker.FreemarkerTransformer;
import org.romaframework.core.config.RomaApplicationContext;

public class HtmlViewTransformerManager {

	private Map<String, Transformer>	transformers	= new HashMap<String, Transformer>();

	public HtmlViewTransformerManager() throws Exception {
		File file = new File(RomaApplicationContext.getApplicationPath() + "/" + FreeMarkerTemplateManager.templatesPath);

		for (String fileName : file.list()) {

			if (fileName.toLowerCase().endsWith(".grid" + ".ftl")) {
				fileName = fileName.replaceAll("\\.grid\\.ftl", "");
				String type = Transformer.GRID;
				newTransformer(fileName, type);
			} else if (fileName.toLowerCase().endsWith(".list" + ".ftl")) {
				String type = Transformer.LIST;
				fileName = fileName.replaceAll("\\.list\\.ftl", "");
				newTransformer(fileName, type);
			} else if (fileName.toLowerCase().endsWith(".ftl")) {
				String type = Transformer.PRIMITIVE;
				fileName = fileName.replaceAll("\\.ftl", "");
				newTransformer(fileName, type);
			}
		}

	}

	private void newTransformer(String fileName, String type) {
		FreemarkerTransformer freemarkerTransformer = new FreemarkerTransformer(fileName);
		freemarkerTransformer.setType(type);
		transformers.put(fileName, freemarkerTransformer);
	}

	public Transformer getComponent(final String key) {
		return transformers.get(key);
	}

	public void setComponents(List<Transformer> components) {
		if (components == null) {
			return;
		}

		for (Transformer t : components) {
			transformers.put(t.toString(), t);
		}

	}

	public String getTypeByRender(String render) {
		return transformers.get(render).getType();
	}

}
