package org.romaframework.aspect.view.html.transformer.jsp;

import java.io.IOException;
import java.io.OutputStream;
import java.util.HashMap;
import java.util.Map;

import org.romaframework.aspect.view.feature.ViewActionFeatures;
import org.romaframework.aspect.view.feature.ViewFieldFeatures;
import org.romaframework.aspect.view.html.area.HtmlViewBinder;
import org.romaframework.aspect.view.html.area.HtmlViewRenderable;
import org.romaframework.aspect.view.html.area.ViewHtmlBinderFactory;
import org.romaframework.aspect.view.html.component.HtmlViewGenericComponent;
import org.romaframework.aspect.view.html.transformer.Transformer;
import org.romaframework.core.Roma;

public class JspTransformer implements Transformer {

	private static final String	HTML					= "html";
	private static final String	JS						= "js";
	private static final String	CSS						= "css";

	public static final String	FILE_SUFFIX		= ".jsp";
	private static final String	JANICULUM			= "janiculum";
	private static final String	PART_TO_PRINT	= "part";
	private static final String	CODE_TO_PRINT	= "codeToPrint";
	private String							renderName;
	private String							type;

	public JspTransformer(String fileName) {
		renderName = fileName;
	}

	public HtmlViewBinder getBinder(HtmlViewRenderable renderable) {
		return Roma.component(ViewHtmlBinderFactory.class).getBinder(renderable);

	}

	public void transform(HtmlViewRenderable component, OutputStream out) throws IOException {
		transform(component, null, out);
	}

	public void transformPart(HtmlViewRenderable component, String part, OutputStream out) throws IOException {
		transform(component, part, out);
	}

	private void transform(HtmlViewRenderable component, String part, OutputStream out) throws IOException {
		String styles = "";
		if (component instanceof HtmlViewGenericComponent) {
			if (((HtmlViewGenericComponent) component).getSchemaField() != null) {
				styles = ((HtmlViewGenericComponent) component).getSchemaField().getFeature(ViewFieldFeatures.STYLE);
			} else {
				styles = (String) ((HtmlViewGenericComponent) component).getSchemaElement().getFeature(ViewActionFeatures.STYLE);
			}
		}

		Map<String, Object> context = getGeneralContext(part, component, styles);
//		context.put(JS, JsFreemarkerDirective.getInstance());
//		context.put(CSS, CssFreemarkerDirective.getInstance());
//		context.put("delegate", DelegateDirective.getInstance());
//		context.put("raw", RawDirective.getInstance());
		transformHtml(context, out);

	}

	private void transformHtml(Map<String, Object> context, OutputStream out) throws IOException {
		String string = HTML;
		printCode(context, string, out);
	}

	private void printCode(Map<String, Object> context, String codeType, OutputStream out) throws IOException {
		context.put(CODE_TO_PRINT, codeType);
//		FreeMarkerTemplateManager mgr = Roma.component(FreeMarkerTemplateManager.class);
//		mgr.execute(getTemplateName(), context, out);
		//TODO!!!
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
//		ctx.put(JANICULUM, new JaniculumWrapper(this, renderable, styles));
		//TODO!!!

		// Add the part info

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
