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

import java.io.IOException;
import java.io.Writer;
import java.util.HashMap;
import java.util.Map;

import org.romaframework.aspect.view.feature.ViewActionFeatures;
import org.romaframework.aspect.view.feature.ViewFieldFeatures;
import org.romaframework.aspect.view.html.area.HtmlViewBinder;
import org.romaframework.aspect.view.html.area.HtmlViewRenderable;
import org.romaframework.aspect.view.html.area.ViewHtmlBinderFactory;
import org.romaframework.aspect.view.html.component.HtmlViewGenericComponent;
import org.romaframework.aspect.view.html.template.ViewTemplateManager;
import org.romaframework.aspect.view.html.transformer.Transformer;
import org.romaframework.aspect.view.html.transformer.helper.JaniculumWrapper;
import org.romaframework.aspect.view.html.transformer.helper.TransformerHelper;
import org.romaframework.core.Roma;

public class JspTransformer implements Transformer {

	private static final String	HTML					= "html";
	private static final String	JS						= "js";
	private static final String	CSS						= "css";

	public static final String	FILE_SUFFIX		= ".jsp";
	public static final String	JANICULUM			= "janiculum";
	public static final String	PART_TO_PRINT	= "part";
	public static final String	CODE_TO_PRINT	= "codeToPrint";
	private String							renderName;
	private String							type;

	public JspTransformer(String fileName) {
		renderName = fileName;
	}

	public HtmlViewBinder getBinder(HtmlViewRenderable renderable) {
		return Roma.component(ViewHtmlBinderFactory.class).getBinder(renderable);

	}

	public void transform(HtmlViewRenderable component, Writer writer) throws IOException {
		transform(component, null, writer);
	}

	public void transformPart(HtmlViewRenderable component, String part, Writer writer) throws IOException {
		transform(component, part, writer);
	}

	private void transform(HtmlViewRenderable component, String part, Writer writer) throws IOException {
		String styles = "";
		if (component instanceof HtmlViewGenericComponent) {
			if (((HtmlViewGenericComponent) component).getSchemaField() != null) {
				styles = ((HtmlViewGenericComponent) component).getSchemaField().getFeature(ViewFieldFeatures.STYLE);
			} else {
				if(((HtmlViewGenericComponent) component).getSchemaElement()!=null){
					styles = (String) ((HtmlViewGenericComponent) component).getSchemaElement().getFeature(ViewActionFeatures.STYLE);
				}
			}
		}

		Map<String, Object> context = getGeneralContext(part, component, styles);
		// context.put(JS, JsFreemarkerDirective.getInstance());
		// context.put(CSS, CssFreemarkerDirective.getInstance());
		// context.put("delegate", DelegateDirective.getInstance());
		// context.put("raw", RawDirective.getInstance());
		transformHtml(context, writer);

	}

	private void transformHtml(Map<String, Object> context, Writer writer) throws IOException {
		String string = HTML;
		printCode(context, string, writer);
	}

	private void printCode(Map<String, Object> context, String codeType, Writer writer) throws IOException {
		context.put(CODE_TO_PRINT, codeType);
		ViewTemplateManager mgr = Roma.component(ViewTemplateManager.class);
		mgr.execute(getTemplateName(), context, writer);
	}

	/**
	 * Retrieve the template based on the style
	 * 
	 * @param style
	 * @return
	 */
	private String getTemplateName() {
		return renderName + FILE_SUFFIX;
	}

	@Override
	public String toString() {
		return renderName;
	}

	protected Map<String, Object> getGeneralContext(String part, HtmlViewRenderable renderable, String styles) {
		Map<String, Object> ctx = new HashMap<String, Object>();
		ctx.put(JANICULUM, new JaniculumWrapper(this, renderable, styles));
		// TODO!!!

		// Add the part info
		TransformerHelper helper = TransformerHelper.getInstance();
		if (renderable instanceof HtmlViewGenericComponent) {
			ctx.put("htmlClass", helper.getHtmlClass(this, null, (HtmlViewGenericComponent) renderable));
		}
		ctx.put("htmlId", helper.getHtmlId(renderable, null));

		if (part != null) {
			ctx.put(PART_TO_PRINT, part);
		} else {
			ctx.put(PART_TO_PRINT, "");
		}

		return ctx;
	}

	public String getRenderName() {
		return renderName;
	}

	public void setRenderName(String renderName) {
		this.renderName = renderName;
	}

	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}
}
