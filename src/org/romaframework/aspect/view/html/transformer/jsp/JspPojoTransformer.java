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

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.util.HashMap;

import org.romaframework.aspect.view.html.area.HtmlViewRenderable;
import org.romaframework.aspect.view.html.component.HtmlViewConfigurableEntityForm;
import org.romaframework.aspect.view.html.component.HtmlViewGenericComponent;
import org.romaframework.aspect.view.html.transformer.helper.JaniculumWrapper;
import org.romaframework.aspect.view.html.transformer.plain.HtmlViewPojoTransformer;
import org.romaframework.core.Roma;

public class JspPojoTransformer extends HtmlViewPojoTransformer {

	public void transformPart(final HtmlViewRenderable component, final String part, OutputStream out) throws IOException {
		final HtmlViewConfigurableEntityForm form = (HtmlViewConfigurableEntityForm) component;
		String htmlClass = helper.getHtmlClass(this, null, (HtmlViewGenericComponent) component);
		String htmlId = helper.getHtmlId(form, null);
		JspTemplateManager mgr = Roma.component(JspTemplateManager.class);
		HashMap<String, Object> ctx = new HashMap<String, Object>();
		ctx.put("htmlClass", htmlClass);
		ctx.put("htmlId", htmlId);
		ByteArrayOutputStream content = new ByteArrayOutputStream();
		form.getRootArea().render(content);
		ctx.put("content", content.toString());
		ctx.put("janiculum", new JaniculumWrapper(this, form, ""));
		mgr.execute("pojo" + JspTransformer.FILE_SUFFIX, ctx, out);

	}
}
