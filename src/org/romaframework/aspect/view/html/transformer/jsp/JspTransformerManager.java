package org.romaframework.aspect.view.html.transformer.jsp;

import java.io.File;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.romaframework.aspect.view.html.transformer.Transformer;
import org.romaframework.aspect.view.html.transformer.manager.TransformerManager;
import org.romaframework.core.Roma;
import org.romaframework.core.config.RomaApplicationContext;

public class JspTransformerManager implements TransformerManager {

	private Map<String, Transformer>	transformers	= new HashMap<String, Transformer>();

	public JspTransformerManager() {
		JspTemplateManager mgr = Roma.component(JspTemplateManager.class);
		File file = new File(RomaApplicationContext.getApplicationPath() + "/" + mgr.getTemplatesPath());

		for (String fileName : file.list()) {

			if (fileName.toLowerCase().endsWith(".grid" + JspTransformer.FILE_SUFFIX)) {
				fileName = fileName.replaceAll("\\.grid\\" + JspTransformer.FILE_SUFFIX, "");
				String type = Transformer.GRID;
				newTransformer(fileName, type);
			} else if (fileName.toLowerCase().endsWith(".list" + JspTransformer.FILE_SUFFIX)) {
				String type = Transformer.LIST;
				fileName = fileName.replaceAll("\\.list\\" + JspTransformer.FILE_SUFFIX, "");
				newTransformer(fileName, type);
			} else if (fileName.toLowerCase().endsWith(JspTransformer.FILE_SUFFIX)) {
				String type = Transformer.PRIMITIVE;
				fileName = fileName.replaceAll("\\" + JspTransformer.FILE_SUFFIX, "");
				newTransformer(fileName, type);
			}
		}

	}

	private void newTransformer(String fileName, String type) {
		JspTransformer transformer = new JspTransformer(fileName);
		transformer.setType(type);
		transformers.put(fileName, transformer);
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
