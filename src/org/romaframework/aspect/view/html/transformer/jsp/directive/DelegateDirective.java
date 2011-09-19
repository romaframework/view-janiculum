package org.romaframework.aspect.view.html.transformer.jsp.directive;

import java.io.IOException;
import java.io.Writer;
import java.util.Map;

import org.romaframework.aspect.view.html.area.HtmlViewRenderable;

import freemarker.core.Environment;
import freemarker.ext.beans.StringModel;
import freemarker.template.TemplateDirectiveBody;
import freemarker.template.TemplateDirectiveModel;
import freemarker.template.TemplateException;
import freemarker.template.TemplateModel;

public class DelegateDirective implements TemplateDirectiveModel {

	private static final String	PARAM_NAME_COMPONENT	= "component";

	static DelegateDirective		instance							= new DelegateDirective();

	private DelegateDirective() {
	}

	public void execute(Environment env, Map params, TemplateModel[] loopVars, TemplateDirectiveBody body) throws TemplateException, IOException {

		StringModel model = (StringModel) params.get(PARAM_NAME_COMPONENT);
		TemplateModel part = (TemplateModel) params.get("part");
		HtmlViewRenderable component = (HtmlViewRenderable) model.getAdaptedObject(HtmlViewRenderable.class);
		Writer writer = env.getOut();

		if (part == null || part.toString().length() == 0) {
			component.render(writer);
		} else {
			component.renderPart(part.toString(), writer);
		}
	}

	public static DelegateDirective getInstance() {
		return instance;
	}

}
