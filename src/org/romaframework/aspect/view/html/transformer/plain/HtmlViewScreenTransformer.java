package org.romaframework.aspect.view.html.transformer.plain;

import java.io.IOException;
import java.io.OutputStream;

import org.romaframework.aspect.view.html.area.HtmlViewBinder;
import org.romaframework.aspect.view.html.area.HtmlViewRenderable;
import org.romaframework.aspect.view.html.binder.NullBinder;
import org.romaframework.aspect.view.html.screen.HtmlViewScreen;
import org.romaframework.aspect.view.html.transformer.AbstractHtmlViewTransformer;
import org.romaframework.aspect.view.html.transformer.Transformer;

public class HtmlViewScreenTransformer extends AbstractHtmlViewTransformer implements Transformer {

	public HtmlViewBinder getBinder(HtmlViewRenderable renderable) {
		return NullBinder.getInstance();
	}

	@Override
	public void transform(final HtmlViewRenderable component, OutputStream out) throws IOException {
		final HtmlViewScreen screen = (HtmlViewScreen) component;

		out.write(("<?xml version=\"1.0\"?>").getBytes());
		out.write(("<!DOCTYPE html PUBLIC \"-//W3C//DTD XHTML 1.0 Strict//EN\" \"http://www.w3.org/TR/xhtml1/DTD/xhtml1-strict.dtd\">")
				.getBytes());
		out.write(("<head>").getBytes());
		out.write(("<title></title>").getBytes());
		out.write(("</head>").getBytes());
		out.write(("<body>").getBytes());
		screen.getRootArea().render(out);
		out.write(("</body>").getBytes());

	}

	public void transformPart(final HtmlViewRenderable component, final String part, OutputStream out) throws IOException {
		transform(component, out);
	}

	@Override
	public String toString() {
		return "screen";
	}

	public String getType() {
		return Transformer.PRIMITIVE;
	}
}
