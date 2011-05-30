package org.romaframework.aspect.view.html.transformer.freemarker;

import java.io.IOException;
import java.io.StringWriter;
import java.util.Map;

import org.romaframework.aspect.view.html.HtmlViewAspectHelper;
import org.romaframework.aspect.view.html.HtmlViewCodeBuffer;
import org.romaframework.aspect.view.html.constants.TransformerConstants;

import freemarker.core.Environment;
import freemarker.ext.beans.StringModel;
import freemarker.template.TemplateDirectiveBody;
import freemarker.template.TemplateDirectiveModel;
import freemarker.template.TemplateException;
import freemarker.template.TemplateModel;

public class JsFreemarkerDirective implements TemplateDirectiveModel{

	private static JsFreemarkerDirective instance = new JsFreemarkerDirective();
	
	private JsFreemarkerDirective(){
		
	}
	
	public void execute(Environment env,
      Map params, TemplateModel[] loopVars,
      TemplateDirectiveBody body) throws TemplateException,
			IOException {
		
		JaniculumWrapper janiculum = (JaniculumWrapper)((StringModel)env.getVariable("janiculum")).getWrappedObject();
		final HtmlViewCodeBuffer jsBuffer = HtmlViewAspectHelper.getJsBuffer();
		
		StringWriter writer = new StringWriter();
		if (body != null) {
			body.render(writer);
		}
		jsBuffer.setScript(janiculum.id(TransformerConstants.PART_ALL), writer.getBuffer().toString());
	}

	public static JsFreemarkerDirective getInstance(){
		return instance;
	}
	
}
