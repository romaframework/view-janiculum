package org.romaframework.aspect.view.html.transformer.helper;

import java.util.List;

import org.romaframework.aspect.view.feature.ViewClassFeatures;
import org.romaframework.aspect.view.form.ViewComponent;
import org.romaframework.aspect.view.html.area.HtmlViewRenderable;
import org.romaframework.aspect.view.html.component.HtmlViewActionComponent;
import org.romaframework.aspect.view.html.component.HtmlViewContentComponent;
import org.romaframework.aspect.view.html.component.HtmlViewContentForm;
import org.romaframework.aspect.view.html.component.HtmlViewGenericComponent;
import org.romaframework.aspect.view.html.constants.TransformerConstants;
import org.romaframework.core.schema.SchemaAction;
import org.romaframework.core.schema.SchemaClass;
import org.romaframework.core.schema.SchemaField;
import org.romaframework.core.schema.SchemaObject;

public class TransformerHelper {

	private static final String			CLASS_NAME_		= "className_";

	private static final String			ELEMENT_NAME_	= "elementName_";

	public static TransformerHelper	instance			= new TransformerHelper();

	public static final String			SEPARATOR			= "_";

	public static TransformerHelper getInstance() {
		return instance;
	}

	public static final String	POJO_ACTION_PREFIX	= "(PojoAction)";
	public static final String	POJO_EVENT_PREFIX		= "(PojoEvent)";

	/**
	 * Return the id to use in the HTML chunk
	 * 
	 * @param contentComponent
	 * @param part
	 * @return
	 */
	public String getHtmlId(final HtmlViewRenderable contentComponent, final String part) {
		final String idPrefix = contentComponent.getHtmlId();
		String result = null;
		if (part == null || part.equals("") || part.equals(TransformerConstants.PART_ALL)) {
			result = idPrefix;
		} else {
			result = idPrefix + SEPARATOR + part;
		}
		return result;
	}

	/**
	 * Return the class attribute to use in the HTML code
	 * 
	 * @param transformer
	 * @param part
	 * @return
	 */
	public String getHtmlClass(final String transformerName, final String part, final HtmlViewGenericComponent iGenericComponent) {

		final String classPrefix = transformerName;
		String result = "";
		if (part == null) {
			result = classPrefix;
		} else {
			result = classPrefix + SEPARATOR + part;
		}

		if (iGenericComponent != null) {
			result = result + " " + getMultiClass(iGenericComponent);
		}

		return result;
	}

	private String getMultiClass(final HtmlViewGenericComponent genericComponent) {
		String result = "";

		if (genericComponent instanceof HtmlViewActionComponent) {
			final SchemaAction schemaAction = (SchemaAction) genericComponent.getSchemaElement();
			if (schemaAction != null) {
				Object feature = schemaAction.getFeature(ViewClassFeatures.STYLE);
				if (feature != null) {
					result = result + " " + schemaAction.getName() + " " + feature.toString();
				} else {
					result = result + " " + schemaAction.getName();
				}
			}
			
			
		} else if (genericComponent instanceof HtmlViewContentForm) {
			
			final SchemaObject schemaObject = genericComponent.getSchemaObject();
			if (schemaObject != null) {
				Object feature = schemaObject.getFeature(ViewClassFeatures.STYLE);
				if (feature != null) {
					result = result + " " + getAllClassHierarchy(schemaObject.getSchemaClass()) + " " + feature.toString();
				} else {
					result = result + " " + getAllClassHierarchy(schemaObject.getSchemaClass());
				}
			}

		} else if (genericComponent instanceof HtmlViewContentComponent) {
			final SchemaField schemaField = (SchemaField) genericComponent.getSchemaElement();
			result = result + " " + ELEMENT_NAME_ + schemaField.getName();
		}

		return result;
	}

	private String getAllClassHierarchy(final SchemaClass contentClass) {
		String result = "";

		if (contentClass != null) {

			result = getAllClassHierarchy(contentClass.getSuperClass()) + " " + CLASS_NAME_ + contentClass.getName();

		} else {
			result = CLASS_NAME_ + "Object";
		}

		return result;
	}

	/**
	 * 
	 * @param contentComponent
	 * @param type
	 * @return
	 */
	public String actions(final ViewComponent contentComponent, final String type) {
		final StringBuffer result = new StringBuffer();
		result.append("<div class=\"" + type + "_actions\">\n");
		final String contentId = getHtmlId((HtmlViewRenderable) contentComponent, TransformerConstants.PART_CONTENT);
		final long componentName = ((HtmlViewRenderable) contentComponent).getId();
		result.append(action(type, contentId, componentName, "add", contentComponent.getScreenArea()));
		result.append(action(type, contentId, componentName, "edit", contentComponent.getScreenArea()));
		result.append(action(type, contentId, componentName, "view", contentComponent.getScreenArea()));
		result.append(action(type, contentId, componentName, "remove", contentComponent.getScreenArea()));
		if (contentComponent.getContent() instanceof List<?>) {
			result.append(action(type, contentId, componentName, "up", contentComponent.getScreenArea()));
			result.append(action(type, contentId, componentName, "down", contentComponent.getScreenArea()));
		}
		result.append("</div>\n");
		return result.toString();
	}

	private StringBuffer action(final String className, final String contentId, final Long componentName, final String action,
			final String screenArea) {
		final StringBuffer result = new StringBuffer();
		final String upperCaseAction = action.substring(0, 1).toUpperCase() + action.substring(1);
		result.append("<span>\n");
		result.append("<input class=\"" + className + "_actions_" + action + "\" id=\"" + contentId + "_" + action
				+ "_button\" type=\"submit\" value=\"" + action + "\" name=\"" + POJO_ACTION_PREFIX + "_" + componentName + "_on"
				+ upperCaseAction + SEPARATOR + screenArea + "\" />\n");
		result.append("</span>\n");
		return result;
	}

}
