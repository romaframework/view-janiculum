package org.romaframework.aspect.view.html.transformer.plain;

import java.io.IOException;
import java.io.Writer;

import org.romaframework.aspect.view.ViewConstants;
import org.romaframework.aspect.view.form.ViewComponent;
import org.romaframework.aspect.view.html.area.HtmlViewBinder;
import org.romaframework.aspect.view.html.area.HtmlViewRenderable;
import org.romaframework.aspect.view.html.component.HtmlViewActionComponent;
import org.romaframework.aspect.view.html.component.HtmlViewConfigurableEntityForm;
import org.romaframework.aspect.view.html.component.HtmlViewContentForm;
import org.romaframework.aspect.view.html.transformer.AbstractHtmlViewTransformer;
import org.romaframework.aspect.view.html.transformer.Transformer;
import org.romaframework.aspect.view.html.transformer.manager.TransformerManager;
import org.romaframework.core.Roma;

public class HtmlViewDefaultTransformer extends AbstractHtmlViewTransformer implements Transformer {

	private static final String	DEFAULT_ACTION_TRANSFORMER	= ViewConstants.RENDER_LINK;
	private static final String	DEFAULT_FIELD_TRANSFORMER		= ViewConstants.RENDER_TEXT;

	@Override
	public String toString() {
		return ViewConstants.RENDER_DEFAULT;
	}

	@Override
	public void transform(final HtmlViewRenderable component, Writer writer) throws IOException {
		
		if (component instanceof HtmlViewContentForm) {
			final HtmlViewConfigurableEntityForm cForm = (HtmlViewConfigurableEntityForm) component;
			final HtmlViewRenderable rootArea = cForm.getRootArea();
			if (rootArea == null) {
				return;
			} else {
				rootArea.render(writer);
			}
		} else if (component instanceof HtmlViewContentForm) {
			// return the default field transformer
			final TransformerManager manager = Roma.component(TransformerManager.class);
			final Transformer transformer = manager.getComponent(DEFAULT_FIELD_TRANSFORMER);
			transformer.transform(component, writer);
		} else if (component instanceof HtmlViewActionComponent) {
			// return the default action transformer
			final TransformerManager manager = Roma.component(TransformerManager.class);
			final Transformer transformer = manager.getComponent(DEFAULT_ACTION_TRANSFORMER);
			transformer.transform(component, writer);
		}
	}

	public void transformPart(final HtmlViewRenderable component, final String part, Writer writer) throws IOException {
		final TransformerManager manager = Roma.component(TransformerManager.class);
		Transformer transformer = null;
		if (component instanceof ViewComponent) {
			// return the default field transformer
			transformer = manager.getComponent(DEFAULT_FIELD_TRANSFORMER);
		} else if (component instanceof HtmlViewActionComponent) {
			// return the default action transformer
			transformer = manager.getComponent(DEFAULT_ACTION_TRANSFORMER);
		}
		if (transformer != null) {
			transformer.transformPart(component, part, writer);
		}
	}

	public HtmlViewBinder getBinder(HtmlViewRenderable renderable) {
		final TransformerManager manager = Roma.component(TransformerManager.class);
		return manager.getComponent(DEFAULT_FIELD_TRANSFORMER).getBinder(renderable);
	}

	public String getType() {
		return Transformer.PRIMITIVE;
	}
}
