package org.romaframework.aspect.view.html.transformer.plain;

import java.io.IOException;
import java.io.Writer;

import javax.servlet.ServletException;
import javax.servlet.ServletRequest;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.romaframework.aspect.view.ViewConstants;
import org.romaframework.aspect.view.html.HtmlViewAspectHelper;
import org.romaframework.aspect.view.html.area.HtmlViewBinder;
import org.romaframework.aspect.view.html.area.HtmlViewRenderable;
import org.romaframework.aspect.view.html.binder.NullBinder;
import org.romaframework.aspect.view.html.component.HtmlViewConfigurableEntityForm;
import org.romaframework.aspect.view.html.component.HtmlViewInvisibleContentComponent;
import org.romaframework.aspect.view.html.component.composed.list.HtmlViewCollectionComposedComponent;
import org.romaframework.aspect.view.html.transformer.AbstractHtmlViewTransformer;
import org.romaframework.aspect.view.html.transformer.Transformer;

public class HtmlViewObjectEmbeddedTransformer extends AbstractHtmlViewTransformer implements Transformer {

	private static final Log	LOG	= LogFactory.getLog(HtmlViewObjectEmbeddedTransformer.class);

	public HtmlViewBinder getBinder(HtmlViewRenderable renderable) {
		return NullBinder.getInstance();
	}

	public void transformPart(final HtmlViewRenderable component, final String part, Writer writer) throws IOException {
		if (component instanceof HtmlViewInvisibleContentComponent) {
			writer.write(((HtmlViewInvisibleContentComponent) component).getContent().toString());
			return;
		}
		if (component instanceof HtmlViewConfigurableEntityForm) {
			final HtmlViewConfigurableEntityForm contentComponent = (HtmlViewConfigurableEntityForm) component;
			if (!visible(contentComponent)) {
				return;
			}
			final ServletRequest request = HtmlViewAspectHelper.getServletRequest();
			// final HttpServletResponse response = HtmlViewAspectHelper.getHttpResponse();
			try {
				content(contentComponent, request, writer);
			} catch (final Exception e) {
				LOG.error("error rendering embedded object: " + e, e);
			}
		} else if (component instanceof HtmlViewCollectionComposedComponent) {
			LOG.error("Component " + component + " can't be of type: " + HtmlViewCollectionComposedComponent.class.getSimpleName());
		}
	}

	protected void content(final HtmlViewConfigurableEntityForm contentComponent, final ServletRequest request, Writer writer)
			throws ServletException, IOException {
		String htmlClass = helper.getHtmlClass(this.toString(), null, contentComponent);
		String htmlId = helper.getHtmlId(contentComponent, null);
		writer.write("<div class=\"");
		writer.write(htmlClass);
		writer.write("\" id=\"" );
		writer.write(htmlId);
		writer.write("\">\n");
		HtmlViewAspectHelper.renderByJsp(contentComponent, request, writer);
		writer.write("</div>\n");
	}

	@Override
	public String toString() {
		return ViewConstants.RENDER_OBJECTEMBEDDED;
	}

	public String getType() {
		return Transformer.PRIMITIVE;
	}
}