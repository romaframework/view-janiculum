package org.romaframework.aspect.view.html.transformer.plain;

import java.io.IOException;
import java.io.OutputStream;

import org.romaframework.aspect.view.html.area.HtmlViewBinder;
import org.romaframework.aspect.view.html.area.HtmlViewRenderable;
import org.romaframework.aspect.view.html.transformer.AbstractHtmlViewTransformer;
import org.romaframework.aspect.view.html.transformer.Transformer;

public class HtmlViewInvisibleTransformer extends AbstractHtmlViewTransformer implements Transformer {

	String	htmlId;

	public HtmlViewInvisibleTransformer(String iHtmlId) {
		htmlId = iHtmlId;
	}

	public HtmlViewBinder getBinder(HtmlViewRenderable renderable) {
		return null;
	}

	public String getType() {
		return "";
	}

	public void transformPart(HtmlViewRenderable component, String part, OutputStream out) throws IOException {
		out.write("<div id=\"".getBytes());
		out.write(htmlId.getBytes());
		out.write("\" class=\"invisible_field\" ></div>".getBytes());
	}

}
