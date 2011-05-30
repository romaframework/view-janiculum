/*
 * Copyright 2009 Luigi Dell'Aquila (luigi.dellaquila--at--assetdata.it)
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
package org.romaframework.aspect.view.html.transformer.freemarker;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.util.HashMap;
import java.util.Map;

import javax.servlet.ServletException;
import javax.servlet.ServletRequest;

import org.romaframework.aspect.view.ViewConstants;
import org.romaframework.aspect.view.html.HtmlViewAspectHelper;
import org.romaframework.aspect.view.html.component.HtmlViewConfigurableEntityForm;
import org.romaframework.aspect.view.html.template.freemarker.FreeMarkerTemplateManager;
import org.romaframework.aspect.view.html.transformer.plain.HtmlViewObjectEmbeddedTransformer;
import org.romaframework.core.Roma;

public class HtmlViewFreeMarkerObjectEmbeddedTransformer extends HtmlViewObjectEmbeddedTransformer {
	@Override
	protected void content(final HtmlViewConfigurableEntityForm contentComponent, final ServletRequest request, OutputStream out)
			throws ServletException, IOException {
		FreeMarkerTemplateManager mgr = Roma.component(FreeMarkerTemplateManager.class);

		Map<String, Object> ctx = new HashMap<String, Object>();
		ctx.put("htmlClass", helper.getHtmlClass(this, null, contentComponent));
		ctx.put("htmlId", helper.getHtmlId(contentComponent, null));
		OutputStream content = new ByteArrayOutputStream();
		HtmlViewAspectHelper.renderByJsp(contentComponent, request, content);
		ctx.put("content", content.toString());
		mgr.execute(ViewConstants.RENDER_OBJECTEMBEDDED + ".ftl", ctx, out);
	}
}
