package org.romaframework.aspect.view.html.transformer.plain;

import java.io.IOException;
import java.io.OutputStream;

import org.romaframework.aspect.view.ViewConstants;
import org.romaframework.aspect.view.form.ViewComponent;
import org.romaframework.aspect.view.html.area.HtmlViewBinder;
import org.romaframework.aspect.view.html.area.HtmlViewRenderable;
import org.romaframework.aspect.view.html.component.HtmlViewActionComponent;
import org.romaframework.aspect.view.html.component.HtmlViewConfigurableEntityForm;
import org.romaframework.aspect.view.html.component.HtmlViewContentForm;
import org.romaframework.aspect.view.html.transformer.AbstractHtmlViewTransformer;
import org.romaframework.aspect.view.html.transformer.Transformer;
import org.romaframework.aspect.view.html.transformer.manager.HtmlViewTransformerManager;
import org.romaframework.core.Roma;

public class HtmlViewDefaultTransformer extends AbstractHtmlViewTransformer implements Transformer {

	private static final String	DEFAULT_ACTION_TRANSFORMER	= ViewConstants.RENDER_LINK;
	private static final String	DEFAULT_FIELD_TRANSFORMER		= ViewConstants.RENDER_TEXT;

	@Override
	public String toString() {
		return ViewConstants.RENDER_DEFAULT;
	}

	@Override
	public void transform(final HtmlViewRenderable component, OutputStream out) throws IOException {
		
		if (component instanceof HtmlViewContentForm) {
			final HtmlViewConfigurableEntityForm cForm = (HtmlViewConfigurableEntityForm) component;
			final HtmlViewRenderable rootArea = cForm.getRootArea();
			if (rootArea == null) {
				return;
			} else {
				rootArea.render(out);
			}
		} else if (component instanceof HtmlViewContentForm) {
			// return the default field transformer
			final HtmlViewTransformerManager manager = Roma.component(HtmlViewTransformerManager.class);
			final Transformer transformer = manager.getComponent(DEFAULT_FIELD_TRANSFORMER);
			transformer.transform(component, out);
		} else if (component instanceof HtmlViewActionComponent) {
			// return the default action transformer
			final HtmlViewTransformerManager manager = Roma.component(HtmlViewTransformerManager.class);
			final Transformer transformer = manager.getComponent(DEFAULT_ACTION_TRANSFORMER);
			transformer.transform(component, out);
		}
	}

	public void transformPart(final HtmlViewRenderable component, final String part, OutputStream out) throws IOException {
		final HtmlViewTransformerManager manager = Roma.component(HtmlViewTransformerManager.class);
		Transformer transformer = null;
		if (component instanceof ViewComponent) {
			// return the default field transformer
			transformer = manager.getComponent(DEFAULT_FIELD_TRANSFORMER);
		} else if (component instanceof HtmlViewActionComponent) {
			// return the default action transformer
			transformer = manager.getComponent(DEFAULT_ACTION_TRANSFORMER);
		}
		if (transformer != null) {
			transformer.transformPart(component, part, out);
		}
	}

	public HtmlViewBinder getBinder(HtmlViewRenderable renderable) {
		final HtmlViewTransformerManager manager = Roma.component(HtmlViewTransformerManager.class);
		return manager.getComponent(DEFAULT_FIELD_TRANSFORMER).getBinder(renderable);
	}

	public String getType() {
		return Transformer.PRIMITIVE;
	}
}
