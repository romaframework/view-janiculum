package org.romaframework.aspect.view.html.transformer.freemarker;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.util.HashMap;

import org.romaframework.aspect.view.html.area.HtmlViewRenderable;
import org.romaframework.aspect.view.html.component.HtmlViewConfigurableEntityForm;
import org.romaframework.aspect.view.html.component.HtmlViewGenericComponent;
import org.romaframework.aspect.view.html.template.freemarker.FreeMarkerTemplateManager;
import org.romaframework.aspect.view.html.transformer.plain.HtmlViewPojoTransformer;
import org.romaframework.core.Roma;

public class HtmlViewFreeMarkerPojoTransformer extends HtmlViewPojoTransformer {

	
	public void transformPart(final HtmlViewRenderable component, final String part, OutputStream out) throws IOException {
		final HtmlViewConfigurableEntityForm form = (HtmlViewConfigurableEntityForm) component;
		String htmlClass = helper.getHtmlClass(this, null, (HtmlViewGenericComponent) component);
		String htmlId = helper.getHtmlId(form, null);
		FreeMarkerTemplateManager mgr = Roma.component(FreeMarkerTemplateManager.class);
		HashMap<String, Object> ctx = new HashMap<String, Object>();
		ctx.put("htmlClass", htmlClass);
		ctx.put("htmlId", htmlId);
		ByteArrayOutputStream content = new ByteArrayOutputStream();
		form.getRootArea().render(content);
		ctx.put("content", content.toString());
		ctx.put("janiculum", new JaniculumWrapper(this, form, ""));
		mgr.execute("pojo.ftl", ctx, out);

	}
}
