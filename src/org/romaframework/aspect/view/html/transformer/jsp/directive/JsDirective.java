package org.romaframework.aspect.view.html.transformer.jsp.directive;

import java.io.IOException;
import java.util.Map;

import freemarker.core.Environment;
import freemarker.template.TemplateDirectiveBody;
import freemarker.template.TemplateDirectiveModel;
import freemarker.template.TemplateException;
import freemarker.template.TemplateModel;

//TODO REMOVE THIS CLASS!!!
public class JsDirective implements TemplateDirectiveModel{

	private static JsDirective instance = new JsDirective();
	
	private JsDirective(){
		
	}
	
	@Deprecated
	public void execute(Environment env,
      Map params, TemplateModel[] loopVars,
      TemplateDirectiveBody body) throws TemplateException,
			IOException {
		
//		JaniculumWrapper janiculum = (JaniculumWrapper)((StringModel)env.getVariable("janiculum")).getWrappedObject();
//		final HtmlViewCodeBuffer jsBuffer = HtmlViewAspectHelper.getJsBuffer();
//		
//		StringWriter writer = new StringWriter();
//		if (body != null) {
//			body.render(writer);
//		}
//		jsBuffer.setScript(janiculum.id(TransformerConstants.PART_ALL), writer.getBuffer().toString());
	}

	public static JsDirective getInstance(){
		return instance;
	}
	
}
