package org.romaframework.aspect.view.html.transformer.freemarker;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.OutputStreamWriter;
import java.io.Writer;
import java.util.HashMap;

import org.romaframework.aspect.view.html.area.HtmlViewRenderable;
import org.romaframework.aspect.view.html.component.HtmlViewConfigurableEntityForm;
import org.romaframework.aspect.view.html.component.HtmlViewGenericComponent;
import org.romaframework.aspect.view.html.template.ViewTemplateManager;
import org.romaframework.aspect.view.html.transformer.helper.JaniculumWrapper;
import org.romaframework.aspect.view.html.transformer.plain.HtmlViewPojoTransformer;
import org.romaframework.core.Roma;

public class HtmlViewFreeMarkerPojoTransformer extends HtmlViewPojoTransformer {

	
	public void transformPart(final HtmlViewRenderable component, final String part, Writer writer) throws IOException {
		final HtmlViewConfigurableEntityForm form = (HtmlViewConfigurableEntityForm) component;
		String htmlClass = helper.getHtmlClass(this.toString(), null, (HtmlViewGenericComponent) component);
		String htmlId = helper.getHtmlId(form, null);
		ViewTemplateManager mgr = Roma.component(ViewTemplateManager.class);
		HashMap<String, Object> ctx = new HashMap<String, Object>();
		ctx.put("htmlClass", htmlClass);
		ctx.put("htmlId", htmlId);
		ByteArrayOutputStream content = new ByteArrayOutputStream();
		form.getRootArea().render(new OutputStreamWriter(content));
		ctx.put("content", content.toString());
		ctx.put("janiculum", new JaniculumWrapper(this, form, ""));
		mgr.execute("pojo.ftl", ctx, writer);

	}
}
