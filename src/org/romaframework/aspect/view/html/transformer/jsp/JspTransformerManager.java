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

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.romaframework.aspect.view.html.screen.HtmlViewScreen;
import org.romaframework.aspect.view.html.template.ViewTemplateManager;
import org.romaframework.aspect.view.html.transformer.Transformer;
import org.romaframework.aspect.view.html.transformer.manager.TransformerManager;
import org.romaframework.core.Roma;
import org.romaframework.core.config.RomaApplicationContext;

public class JspTransformerManager implements TransformerManager {

	private Map<String, Transformer>							transformers		= new HashMap<String, Transformer>();
	private Map<String, Map<String, Transformer>>	setTransformers	= new HashMap<String, Map<String, Transformer>>();

	@SuppressWarnings({ "unchecked", "rawtypes" })
	public JspTransformerManager() {
		ViewTemplateManager mgr = Roma.component(ViewTemplateManager.class);
		Set<String> resources = (Set) RomaApplicationContext.getResourceAccessor().getResourcePaths(mgr.getTemplatesPath());

		for (String fileName : resources) {
			fileName = fileName.substring(mgr.getTemplatesPath().length());
			if (fileName.contains("/")) {
				int index = fileName.indexOf('/');
				String setName = fileName.substring(0, index);
				fileName = fileName.substring(index + 1);
				Map<String, Transformer> trans = setTransformers.get(setName);
				if (trans == null) {
					trans = new HashMap<String, Transformer>();
					setTransformers.put(setName, trans);
				}
				addTransformer(trans, fileName);
			} else {
				addTransformer(transformers, fileName);
			}
		}

	}

	private void addTransformer(Map<String, Transformer> set, String fileName) {
		if (fileName.contains("/"))
			return;
		if (fileName.toLowerCase().endsWith(".grid" + JspTransformer.FILE_SUFFIX)) {
			fileName = fileName.replaceAll("\\.grid\\" + JspTransformer.FILE_SUFFIX, "");
			String type = Transformer.GRID;
			newTransformer(set, fileName, type);
		} else if (fileName.toLowerCase().endsWith(".list" + JspTransformer.FILE_SUFFIX)) {
			String type = Transformer.LIST;
			fileName = fileName.replaceAll("\\.list\\" + JspTransformer.FILE_SUFFIX, "");
			newTransformer(set, fileName, type);
		} else if (fileName.toLowerCase().endsWith(JspTransformer.FILE_SUFFIX)) {
			String type = Transformer.PRIMITIVE;
			fileName = fileName.replaceAll("\\" + JspTransformer.FILE_SUFFIX, "");
			newTransformer(set, fileName, type);
		}

	}

	private void newTransformer(Map<String, Transformer> set, String fileName, String type) {
		JspTransformer transformer = new JspTransformer(fileName);
		transformer.setType(type);
		set.put(fileName, transformer);
	}

	public Transformer getComponent(final String key) {
		String renderSet = ((HtmlViewScreen) Roma.view().getScreen()).getRenderSet();
		if (renderSet != null && !renderSet.trim().isEmpty()) {
			Map<String, Transformer> transSet = setTransformers.get(renderSet);
			Transformer trans = transSet.get(key);
			if (trans != null)
				return trans;
		}
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
