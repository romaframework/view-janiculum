package org.romaframework.aspect.view.html.transformer.freemarker;

import java.io.IOException;
import java.io.StringWriter;
import java.util.Iterator;
import java.util.Map;

import org.romaframework.aspect.view.html.HtmlViewAspectHelper;
import org.romaframework.aspect.view.html.css.StyleBuffer;

import freemarker.core.Environment;
import freemarker.template.TemplateDirectiveBody;
import freemarker.template.TemplateDirectiveModel;
import freemarker.template.TemplateException;
import freemarker.template.TemplateModel;
import freemarker.template.TemplateModelException;

public class CssFreemarkerDirective implements TemplateDirectiveModel {

	private static final Object						PARAM_NAME_SELECTOR	= "selector";
	private static final Object						PARAM_NAME_PROPERTY	= "property";
	private static CssFreemarkerDirective	instance						= new CssFreemarkerDirective();

	private CssFreemarkerDirective() {

	}

	public void execute(Environment env, Map params, TemplateModel[] loopVars, TemplateDirectiveBody body) throws TemplateException,
			IOException {

		if (body == null)
			throw new TemplateModelException("The body of directive must contains the CSS property");

		final StyleBuffer jsBuffer = HtmlViewAspectHelper.getCssBuffer();

		StringWriter writer = new StringWriter();
		body.render(writer);
		String selector = null;// TODO
		String property = null;
		Iterator<?> paramIter = params.entrySet().iterator();
		while (paramIter.hasNext()) {
			Map.Entry<?, ?> ent = (Map.Entry<?, ?>) paramIter.next();

			String paramName = (String) ent.getKey();
			TemplateModel paramValue = (TemplateModel) ent.getValue();

			if (paramName.equals(PARAM_NAME_SELECTOR)) {
				// if (!(paramValue instanceof SimpleScalar)) {
				// throw new TemplateModelException("The \"" + PARAM_NAME_SELECTOR + "\" parameter must be a String.");
				// }
				selector = paramValue.toString();
			}

			if (paramName.equals(PARAM_NAME_PROPERTY)) {
				// if (!(paramValue instanceof StringModel)) {
				// throw new TemplateModelException("The \"" + PARAM_NAME_PROPERTY + "\" parameter must be a String.");
				// }
				// property = ((StringModel) paramValue).getAsString();
				property = paramValue.toString();
			}
		}
		if (selector == null || property == null) {
			throw new TemplateModelException("The \"" + PARAM_NAME_SELECTOR + "\" and \"" + PARAM_NAME_PROPERTY
					+ "\" parameters must be defined.");
		}
		String value = writer.getBuffer().toString();
		if (value == null || value.trim().length() == 0) {
			if (jsBuffer.containsRule(selector)) {
				jsBuffer.removeRule(selector, property);
			}

		} else {
			value = value.trim();
			if (!jsBuffer.containsRule(selector)) {
				jsBuffer.createRules(selector);
			}
			jsBuffer.addRule(selector, property, value);
		}
	}

	public static CssFreemarkerDirective getInstance() {
		return instance;
	}

}
