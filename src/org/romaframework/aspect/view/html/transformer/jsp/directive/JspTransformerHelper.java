package org.romaframework.aspect.view.html.transformer.jsp.directive;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.io.StringWriter;
import java.io.Writer;
import java.util.Date;

import org.romaframework.aspect.view.FormatHelper;
import org.romaframework.aspect.view.html.HtmlViewAspectHelper;
import org.romaframework.aspect.view.html.HtmlViewCodeBuffer;
import org.romaframework.aspect.view.html.area.HtmlViewRenderable;
import org.romaframework.aspect.view.html.component.HtmlViewAbstractContentComponent;
import org.romaframework.aspect.view.html.component.HtmlViewContentComponent;
import org.romaframework.aspect.view.html.constants.TransformerConstants;
import org.romaframework.aspect.view.html.css.StyleBuffer;
import org.romaframework.aspect.view.html.transformer.helper.JaniculumWrapper;
import org.romaframework.core.Roma;

import freemarker.ext.beans.StringModel;

public class JspTransformerHelper {

	public static void addCss(String selector, String property, String value) throws IOException {
		final StyleBuffer jsBuffer = HtmlViewAspectHelper.getCssBuffer();
		if (selector == null || property == null) {
			throw new IllegalArgumentException();
		}

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

	public static void addJs(String id, String script){
		final HtmlViewCodeBuffer jsBuffer = HtmlViewAspectHelper.getJsBuffer();
		jsBuffer.setScript(id, script);
	}
	
	
	public static final String delegate(HtmlViewRenderable component, String part) throws IOException {
		OutputStream out = new ByteArrayOutputStream();
		if (part == null || part.toString().length() == 0) {
			component.render(out);
		} else {
			component.renderPart(part.toString(), out);
		}
		return out.toString();
	}
	
	public static String raw(HtmlViewRenderable component){
		String toRender = "";
//		if (itemContent != null) {
//			if (component instanceof HtmlViewContentComponent) {
//				toRender = FormatHelper.format(itemContent.getAdaptedObject(Object.class),
//						((HtmlViewContentComponent) component).getSchemaField());
//			}
//		} else {
		
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

		return toRender;

	}
}
