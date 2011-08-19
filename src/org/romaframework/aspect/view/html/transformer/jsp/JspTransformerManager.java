/*
 * Copyright 2011 Luigi Dell'Aquila (luigi.dellaquila--at--assetdata.it)
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package org.romaframework.aspect.view.html.transformer.jsp;

import java.io.File;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.romaframework.aspect.view.html.template.TemplateManager;
import org.romaframework.aspect.view.html.transformer.Transformer;
import org.romaframework.aspect.view.html.transformer.manager.TransformerManager;
import org.romaframework.core.Roma;
import org.romaframework.core.config.RomaApplicationContext;

public class JspTransformerManager implements TransformerManager {

	private Map<String, Transformer>	transformers	= new HashMap<String, Transformer>();

	public JspTransformerManager() {
		TemplateManager mgr = Roma.component(TemplateManager.class);
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
