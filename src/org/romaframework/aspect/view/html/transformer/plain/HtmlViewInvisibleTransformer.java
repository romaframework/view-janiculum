package org.romaframework.aspect.view.html.transformer.plain;

import java.io.IOException;
import java.io.Writer;

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

	public void transformPart(HtmlViewRenderable component, String part, Writer writer) throws IOException {
		writer.write("<div id=\"");
		writer.write(htmlId);
		writer.write("\" class=\"invisible_field\" ></div>");
	}

}
