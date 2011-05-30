package org.romaframework.aspect.view.html.transformer;

import java.io.IOException;
import java.io.OutputStream;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.romaframework.aspect.view.ViewAspect;
import org.romaframework.aspect.view.feature.ViewElementFeatures;
import org.romaframework.aspect.view.html.HtmlViewAspectHelper;
import org.romaframework.aspect.view.html.area.HtmlViewRenderable;
import org.romaframework.aspect.view.html.component.HtmlViewContentComponent;
import org.romaframework.aspect.view.html.component.HtmlViewGenericComponent;
import org.romaframework.aspect.view.html.constants.TransformerConstants;
import org.romaframework.aspect.view.html.transformer.helper.TransformerHelper;

public abstract class AbstractHtmlViewTransformer implements Transformer {

	private static final Log						LOG			= LogFactory.getLog(AbstractHtmlViewTransformer.class);

	protected static TransformerHelper	helper	= TransformerHelper.getInstance();

	/**
	 * Render the HTML for the label of the component
	 * 
	 * @param contentComponent
	 * @return
	 */
	protected void label(final HtmlViewContentComponent contentComponent, final StringBuilder buffer) {
		try {
			final String label = HtmlViewAspectHelper.getI18NLabel(contentComponent.getSchemaField());
			if (label != null && !label.trim().equals("")) {
				buffer.append(getLabel(contentComponent, label));
			}

		} catch (final Exception e) {
			LOG.error("error rendering element label: " + e, e);
		}
	}

	protected String label(final HtmlViewContentComponent contentComponent) {
		final String label = HtmlViewAspectHelper.getI18NLabel(contentComponent.getSchemaField());
		if (label != null && !label.trim().equals("")) {
			return getLabel(contentComponent, label);
		}
		return "";
	}

	private String getLabel(final HtmlViewContentComponent contentComponent, final String label) {
		return getComponentLabel(contentComponent, label);
	}

	public static String getComponentLabel(final HtmlViewContentComponent contentComponent, final String label) {
		if(label==null || label.length()==0){
			return "";//TODO NOBODY SHOULD ARRIVE HERE!!!
		}
		return "<label for=\"" + helper.getHtmlId(contentComponent, TransformerConstants.PART_CONTENT) + "\" id=\""
				+ helper.getHtmlId(contentComponent, TransformerConstants.PART_LABEL) + "\" class=\""
				+ helper.getHtmlClass(contentComponent.getTransformer(), TransformerConstants.PART_LABEL, contentComponent) + "\" >"
				+ label + "</label>\n";
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.romaframework.aspect.view.html.transformer.Transformer#transform(org.romaframework.aspect.view.form.ViewComponent)
	 */
	public void transform(final HtmlViewRenderable component, OutputStream out) throws IOException {
		transformPart(component, TransformerConstants.PART_ALL, out);
	}

	protected boolean disabled(final HtmlViewGenericComponent contentComponent) {
		final Object enabled = contentComponent.getSchemaElement().getFeature(ViewAspect.ASPECT_NAME, ViewElementFeatures.ENABLED);
		return Boolean.FALSE.equals(enabled);
	}

	protected boolean visible(final HtmlViewGenericComponent contentComponent) {
		final Object visible = contentComponent.getSchemaElement().getFeature(ViewAspect.ASPECT_NAME, ViewElementFeatures.VISIBLE);
		return !Boolean.FALSE.equals(visible);
	}

}
