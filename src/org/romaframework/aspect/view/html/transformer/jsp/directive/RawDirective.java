package org.romaframework.aspect.view.html.transformer.jsp.directive;

import java.io.IOException;
import java.io.Writer;
import java.util.Date;
import java.util.Map;

import org.romaframework.aspect.view.FormatHelper;
import org.romaframework.aspect.view.html.area.HtmlViewRenderable;
import org.romaframework.aspect.view.html.component.HtmlViewAbstractContentComponent;
import org.romaframework.aspect.view.html.component.HtmlViewContentComponent;
import org.romaframework.core.Roma;

import freemarker.core.Environment;
import freemarker.ext.beans.StringModel;
import freemarker.template.TemplateDirectiveBody;
import freemarker.template.TemplateDirectiveModel;
import freemarker.template.TemplateException;
import freemarker.template.TemplateModel;


public class RawDirective implements TemplateDirectiveModel {

	private static final String	PARAM_NAME_COMPONENT	= "component";
	private static final String	PARAM_NAME_CONTENT		= "content";

	private static RawDirective	instance							= new RawDirective();

	private RawDirective() {

	}

	public void execute(Environment env, Map params, TemplateModel[] loopVars, TemplateDirectiveBody body) throws TemplateException,
			IOException {
		String toRender = "";
		StringModel model = (StringModel) params.get(PARAM_NAME_COMPONENT);
		HtmlViewRenderable component = (HtmlViewRenderable) model.getAdaptedObject(HtmlViewRenderable.class);
		StringModel itemContent = (StringModel) params.get(PARAM_NAME_CONTENT);
		if (itemContent != null) {
			if (component instanceof HtmlViewContentComponent) {
				toRender = FormatHelper.format(itemContent.getAdaptedObject(Object.class),
						((HtmlViewContentComponent) component).getSchemaField());
			}
		} else {
			Object content = null;
			if (component instanceof HtmlViewContentComponent) {
				if (((HtmlViewContentComponent) component).getContent() != null) {
					content = ((HtmlViewContentComponent) component).getContent();
				}
			} else if (component instanceof HtmlViewAbstractContentComponent) {
				if (((HtmlViewAbstractContentComponent) component).getContent() != null) {
					content = ((HtmlViewAbstractContentComponent) component).getContent();
				}
			}
			if (component instanceof HtmlViewContentComponent) {
				toRender = FormatHelper.format(content, ((HtmlViewContentComponent) component).getSchemaField());
			} else {
				if (content != null) {
					if (content instanceof Date) {
						toRender = Roma.i18n().getDateFormat().format(content);
					} else {
						toRender = content.toString();
					}
				}

			}

		}
		Writer writer = env.getOut();

		writer.append(toRender);

	}

	public static RawDirective getInstance() {
		return instance;
	}

}
