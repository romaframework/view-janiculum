package org.romaframework.aspect.view.html.transformer.plain;

import java.io.IOException;
import java.io.OutputStream;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.romaframework.aspect.view.html.area.HtmlViewBinder;
import org.romaframework.aspect.view.html.area.HtmlViewRenderable;
import org.romaframework.aspect.view.html.binder.NullBinder;
import org.romaframework.aspect.view.html.component.HtmlViewConfigurableEntityForm;
import org.romaframework.aspect.view.html.component.HtmlViewGenericComponent;
import org.romaframework.aspect.view.html.transformer.AbstractHtmlViewTransformer;
import org.romaframework.aspect.view.html.transformer.Transformer;

public class HtmlViewPojoTransformer extends AbstractHtmlViewTransformer implements Transformer {

	private static final Log		LOG		= LogFactory.getLog(HtmlViewPojoTransformer.class);

	public static final String	NAME	= "POJO";

	public HtmlViewBinder getBinder(HtmlViewRenderable renderable) {
		return NullBinder.getInstance();
	}

	public void transformPart(final HtmlViewRenderable component, final String part, OutputStream out) throws IOException {
		final HtmlViewConfigurableEntityForm form = (HtmlViewConfigurableEntityForm) component;
		String htmlClass = helper.getHtmlClass(this, null, (HtmlViewGenericComponent) component);
		String htmlId = helper.getHtmlId(form, null);

		out.write("<div class=\"".getBytes());
		out.write(htmlClass.getBytes());
		out.write("\" id=\"".getBytes());
		out.write(htmlId.getBytes());
		out.write("\">\n".getBytes());

		form.getRootArea().render(out);
		out.write("</div>\n".getBytes());
	}

	@Override
	public String toString() {
		return NAME;
	}

	public String getType() {
		return Transformer.PRIMITIVE;
	}
}