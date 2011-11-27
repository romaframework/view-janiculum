package org.romaframework.aspect.view.html.binder;

import java.util.Map;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.romaframework.aspect.view.FormatHelper;
import org.romaframework.aspect.view.feature.ViewFieldFeatures;
import org.romaframework.aspect.view.form.ViewComponent;
import org.romaframework.aspect.view.html.area.HtmlViewBinder;
import org.romaframework.aspect.view.html.area.HtmlViewRenderable;
import org.romaframework.core.schema.SchemaField;
import org.romaframework.core.schema.SchemaHelper;

public class TextBinder implements HtmlViewBinder {

	private static Log	log	= LogFactory.getLog(TextBinder.class);

	public void bind(final HtmlViewRenderable renderable, final Map<String, Object> values) {
		final String baseParam = values.keySet().iterator().next().split("_")[0];
		final String value = (String) values.get(baseParam);
		log.debug("binding " + renderable);
		final ViewComponent contentComponent = (ViewComponent) renderable;
		final SchemaField schemaField = contentComponent.getSchemaField();
		final Object enabled = schemaField.getFeature(ViewFieldFeatures.ENABLED);
		if (enabled != null && Boolean.FALSE.equals(enabled)) {
			return;
		}

		try {
			if ("".equals(value)) {
				Object actVal = SchemaHelper.getFieldValue(schemaField, contentComponent.getContainerComponent().getContent());
				if (actVal == null)
					return;
			}
			Object val = FormatHelper.parse(value, schemaField);
			SchemaHelper.setFieldValue(schemaField, contentComponent.getContainerComponent().getContent(), val);
			contentComponent.setContent(SchemaHelper.getFieldValue(schemaField, contentComponent.getContainerComponent().getContent()));
		} catch (final Exception e) {
			log.error("could not bind value: " + e);
		}
	}
}
