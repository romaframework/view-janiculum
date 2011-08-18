package org.romaframework.aspect.view.html.transformer.helper;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Date;
import java.util.HashSet;
import java.util.Iterator;
import java.util.Set;

import javax.servlet.http.HttpServletRequest;

import org.romaframework.aspect.view.FormatHelper;
import org.romaframework.aspect.view.area.AreaComponent;
import org.romaframework.aspect.view.feature.ViewBaseFeatures;
import org.romaframework.aspect.view.feature.ViewElementFeatures;
import org.romaframework.aspect.view.feature.ViewFieldFeatures;
import org.romaframework.aspect.view.html.HtmlViewAspectHelper;
import org.romaframework.aspect.view.html.actionhandler.EventHelper;
import org.romaframework.aspect.view.html.area.HtmlViewFormArea;
import org.romaframework.aspect.view.html.area.HtmlViewFormAreaInstance;
import org.romaframework.aspect.view.html.area.HtmlViewRenderable;
import org.romaframework.aspect.view.html.area.HtmlViewScreenArea;
import org.romaframework.aspect.view.html.area.mode.HtmlViewAreaMode;
import org.romaframework.aspect.view.html.area.mode.HtmlViewAreaModeImpl;
import org.romaframework.aspect.view.html.component.HtmlViewAbstractComponent;
import org.romaframework.aspect.view.html.component.HtmlViewAbstractContentComponent;
import org.romaframework.aspect.view.html.component.HtmlViewActionComponent;
import org.romaframework.aspect.view.html.component.HtmlViewComposedComponent;
import org.romaframework.aspect.view.html.component.HtmlViewConfigurableEntityForm;
import org.romaframework.aspect.view.html.component.HtmlViewContentComponent;
import org.romaframework.aspect.view.html.component.HtmlViewContentComponentImpl;
import org.romaframework.aspect.view.html.component.HtmlViewGenericComponent;
import org.romaframework.aspect.view.html.component.composed.list.HtmlViewCollectionComposedComponent;
import org.romaframework.aspect.view.html.transformer.Transformer;
import org.romaframework.aspect.view.html.transformer.freemarker.Griddable;
import org.romaframework.core.Roma;
import org.romaframework.core.schema.SchemaAction;
import org.romaframework.core.schema.SchemaClassElement;
import org.romaframework.core.schema.SchemaEvent;
import org.romaframework.core.schema.SchemaField;
import org.romaframework.web.session.HttpAbstractSessionAspect;
import org.romaframework.web.view.HttpUtils;

public class JaniculumWrapper {
	private static long																				counter			= 0;
	private static final ArrayList<HtmlViewGenericComponent>	EMPTY_LIST	= new ArrayList<HtmlViewGenericComponent>();
	private Transformer																				transformer;
	private HtmlViewRenderable																component;
	private static TransformerHelper													helper			= TransformerHelper.getInstance();

	public JaniculumWrapper(Transformer transformer, HtmlViewRenderable component, String styles) {
		// TODO manage style configurations
		this.transformer = transformer;
		this.component = component;
	}

	public String id(String part) {
		return helper.getHtmlId(component, part);
	}

	public Long progressiveLong() {
		return counter++;
	}

	public HtmlViewGenericComponent getContainerComponent() {
		if (component instanceof HtmlViewGenericComponent) {
			return (HtmlViewGenericComponent) ((HtmlViewGenericComponent) component).getContainerComponent();
		} else if (component instanceof HtmlViewAreaModeImpl) {
			return (HtmlViewGenericComponent) ((HtmlViewAreaModeImpl) component).getAreaContainer();
		}
		return null;
	}

	public Collection<?> getChildren() {
		if (component instanceof HtmlViewGenericComponent) {
			return ((HtmlViewGenericComponent) component).getChildren();
		} else if (component instanceof HtmlViewAreaModeImpl) {
			return ((HtmlViewAreaModeImpl) component).getRenderables();
		}
		return EMPTY_LIST;
	}

	public boolean haveChildren() {
		Collection<?> children = this.getChildren();
		if (children != null && children.size() > 0)
			return true;
		else
			return false;
	}

	public Object content() {
		return content(false);
	}

	public Object content(boolean quoteDblquotes) {
		if (component instanceof HtmlViewContentComponent) {
			Object result = ((HtmlViewContentComponent) component).getContent();
			if (result != null && result instanceof String && quoteDblquotes) {
				result = ((String) result).replaceAll("\"", "&quot;");
			}
			return result;
		}
		return null;
	}

	public Object formattedContent() {
		if (component instanceof HtmlViewContentComponent) {
			SchemaField field = ((HtmlViewContentComponent) component).getSchemaField();
			Object content = ((HtmlViewContentComponent) component).getContent();
			return FormatHelper.format(content, field, true);
		}
		return null;
	}

	public String contentAsString() {
		Object content = content();
		if (content == null) {
			return "";
		}
		return content.toString();
	}

	public HtmlViewRenderable getComponent() {
		return component;
	}

	public String imageLabel() {
		SchemaClassElement schema = getSchemaElement();
		String result = null;
		if (schema instanceof SchemaField) {
			Object content = content();
			if (content != null) {
				result = content.toString().replaceAll("\\$", "");
			}
		} else {

			result = ((String) ((HtmlViewGenericComponent) component).getSchemaElement().getFeature(ViewBaseFeatures.LABEL)).replaceAll(
					"\\$", "");

		}
		return result;
	}

	public String cssClass(String part) {
		return cssSpecificClass(component, part);
	}

	public String cssSpecificClass(HtmlViewRenderable thisComponent, String part) {
		if (thisComponent instanceof HtmlViewGenericComponent) {
			SchemaField schemaField = ((HtmlViewGenericComponent) thisComponent).getSchemaField();

			if (schemaField != null) {
				Object feature = schemaField.getFeature(ViewFieldFeatures.STYLE);
				if (feature != null && !feature.toString().isEmpty() && feature.toString().charAt(0) != '{') {
					// CLASS NAME: USE IT
					return feature.toString();
				}
			}

			return helper.getHtmlClass(transformer, part, (HtmlViewGenericComponent) thisComponent);
		} else if (thisComponent instanceof HtmlViewAreaMode) {
			String areaName = ((HtmlViewAreaMode) thisComponent).getAreaName();
			if (areaName != null) {
				return "area_" + areaName;
			}
		}
		return "";
	}

	public String tableRowCssClass(int rowIndex) {
		return tableRowCssSpecificClass(component, rowIndex);
	}

	public String tableRowCssSpecificClass(HtmlViewRenderable thisComponent, int rowIndex) {
		if (thisComponent instanceof HtmlViewCollectionComposedComponent) {
			HtmlViewCollectionComposedComponent tableComponent = (HtmlViewCollectionComposedComponent) thisComponent;
			if (tableComponent.getChildren().size() > rowIndex) {
				HtmlViewGenericComponent rowComponent = new ArrayList<HtmlViewGenericComponent>(tableComponent.getChildren()).get(rowIndex);
				if (rowComponent.getSchemaObject() != null) {
					Object feature = rowComponent.getSchemaObject().getFeature(ViewFieldFeatures.STYLE);
					if (feature != null && !feature.toString().isEmpty() && feature.toString().charAt(0) != '{') {
						// CLASS NAME: USE IT
						return feature.toString();
					}
				}
			}
		}
		return cssClass("body_row");
	}

	public String inlineStyle(String part) {
		if (component instanceof HtmlViewGenericComponent) {
			SchemaField schemaField = ((HtmlViewGenericComponent) component).getSchemaField();
			if (schemaField != null) {
				Object feature = schemaField.getFeature(ViewFieldFeatures.STYLE);
				if (feature != null) {
					String style = ((String) feature).trim();
					if (style.isEmpty() || style.charAt(0) != '{')
						return "";

					return style.substring(1, style.length() - 2);
				}
			}
		} else if (component instanceof HtmlViewAreaModeImpl) {
			if (((HtmlViewAreaModeImpl) component).getAreaContainer() instanceof HtmlViewFormAreaInstance) {
				HtmlViewFormAreaInstance instance = (HtmlViewFormAreaInstance) ((HtmlViewAreaModeImpl) component).getAreaContainer();
				if (instance.getAreaStyle() != null) {
					String style = instance.getAreaStyle();
					if (style.isEmpty() || style.charAt(0) != '{')
						return "";

					return style.substring(1, style.length() - 2);
				}
			}
		}
		return "";
	}

	/**
	 * for form areas
	 * 
	 * @return
	 */
	public boolean isLabelRendered() {
		if (component instanceof HtmlViewConfigurableEntityForm) {
			HtmlViewFormArea area = ((HtmlViewConfigurableEntityForm) component).getAreaForComponentPlacement();
			return !"placeholder".equals(((HtmlViewFormAreaInstance) area).getType());
		}
		if (component instanceof HtmlViewScreenArea) {
			return false;
		}
		return true;
	}

	public String i18NLabel() {
		return HtmlViewAspectHelper.getI18NLabel(((HtmlViewGenericComponent) component).getSchemaElement());
	}

	public String i18NHint() {
		return HtmlViewAspectHelper.getI18NHint(((HtmlViewGenericComponent) component).getSchemaElement());
	}

	public String i18N(String string) {
		String text = Roma.i18n().getString(string);
		if (text == null) {
			text = "";
			if (string.endsWith(".label")) {
				String[] toParse = string.split("\\.");
				String tempText = toParse[toParse.length - 2];
				for (int i = 0; i < tempText.length(); i++) {
					if (i == 0) {
						text = "" + tempText.charAt(i);
						text = text.toUpperCase();
					} else {
						if (Character.isUpperCase(tempText.charAt(i)))
							text = text + " " + tempText.charAt(i);
						else
							text = text + tempText.charAt(i);
					}
				}

			} else
				text = string;
		}
		return text;
	}

	public boolean getField(){
		return isField();
	}
	
	public boolean isField() {
		SchemaClassElement el = getSchemaElement();
		if (el instanceof SchemaField) {
			return true;
		}
		return false;
	}

	public boolean isAction() {
		SchemaClassElement el = getSchemaElement();
		if (el instanceof SchemaAction) {
			return true;
		}
		return false;
	}

	public boolean isAction(HtmlViewAbstractComponent component) {
		return (component instanceof HtmlViewActionComponent);
	}

	private SchemaClassElement getSchemaElement() {
		SchemaClassElement el = ((HtmlViewGenericComponent) component).getSchemaElement();
		return el;
	}

	public String actionName() {
		HtmlViewGenericComponent actionComponent = (HtmlViewGenericComponent) component;

		return TransformerHelper.POJO_ACTION_PREFIX + TransformerHelper.SEPARATOR + actionComponent.getId()
				+ TransformerHelper.SEPARATOR + actionComponent.getSchemaElement().getName() + TransformerHelper.SEPARATOR
				+ actionComponent.getScreenArea();
	}

	public String event(String event) {
		HtmlViewGenericComponent actionComponent = (HtmlViewGenericComponent) component;
		return EventHelper.getEventHtmlName(actionComponent, event);
		// return "(PojoAction)_" + actionComponent.getId() + TransformerHelper.SEPARATOR + elementName(event)
		// + TransformerHelper.SEPARATOR + actionComponent.getScreenArea();
	}

	public Set<String> getAvailableEvents() {
		return this.availableEvents();
	}
	public Set<String> availableEvents() {
		Set<String> result = new HashSet<String>();
		SchemaClassElement element = this.getSchemaElement();
		if (element instanceof SchemaField) {
			Set<String> standardEvents = Roma.component(EventHelper.class).getStandardEvents();
			Iterator<SchemaEvent> eventIterator = ((SchemaField) element).getEventIterator();
			while (eventIterator.hasNext()) {
				String name = eventIterator.next().getName();
				if (standardEvents.contains(name))
					result.add(name);
			}
		}
		return result;
	}

	@Deprecated
	public String action(String action) {
		HtmlViewGenericComponent actionComponent = (HtmlViewGenericComponent) component;

		return TransformerHelper.POJO_ACTION_PREFIX + TransformerHelper.SEPARATOR + actionComponent.getId()
				+ TransformerHelper.SEPARATOR + action + TransformerHelper.SEPARATOR + actionComponent.getScreenArea();
	}

	public String fieldName() {
		return "" + (component).getId();
	}

	public boolean isDisabled() {
		return disabled();
	}

	public boolean disabled() {
		Boolean feature = ((HtmlViewGenericComponent) component).getSchemaElement().getFeature(ViewElementFeatures.ENABLED);
		if (feature == null) {
			return false;
		}
		return !feature;
	}

	public boolean checked() {
		Object content = content();
		if (content != null && content instanceof Boolean)
			return ((Boolean) content).booleanValue();
		return false;
	}

	public boolean isValid() {

		HtmlViewAbstractContentComponent component = (HtmlViewAbstractContentComponent) this.component;
		return component.isValid();

	}

	public String validationMessage() {
		HtmlViewAbstractContentComponent component = (HtmlViewAbstractContentComponent) this.component;
		return component.getValidationMessage();
	}

	public Collection<String> headers() {
		Collection<String> result = new ArrayList<String>();
		if (component instanceof HtmlViewComposedComponent) {
			return ((HtmlViewComposedComponent) component).getHeaders();
		}
		return result;
	}

	public Collection<String> headersRaw() {
		Collection<String> result = new ArrayList<String>();
		if (component instanceof HtmlViewComposedComponent) {
			return ((HtmlViewComposedComponent) component).getHeadersRaw();
		}
		return result;
	}

	public boolean isEvent(String eventName) {
		HtmlViewGenericComponent actionComponent = (HtmlViewGenericComponent) component;
		if (actionComponent.getSchemaField() != null)
			return false;
		return actionComponent.getSchemaField().getEvent(eventName) != null;
	}

	public String formatDateContent() {
		return formatDateContent("dd/MM/yyyy");
	}

	public String formatDateTimeContent() {
		return formatDateContent("HH:mm:ss");
	}

	public String formatTimeContent() {
		return formatDateContent("dd/MM/yyyy HH:mm:ss");
	}

	public String formatDateContent(String format) {
		Date content = (Date) content();
		if (content == null)
			return "";
		DateFormat formatter = new SimpleDateFormat(format);
		return formatter.format(content);
	}

	public String formatNumberContent() {
		return "" + formattedContent();
	}

	/**
	 * 
	 * @return true if this field is a collection or an array and its selection field is a single object
	 */
	public boolean isSingleSelection() {
		if (component instanceof HtmlViewContentComponent) {
			return ((HtmlViewContentComponent) component).isSingleSelection();
		}

		return false;
	}

	/**
	 * 
	 * @return true if this field is a collection or an array and its selection field a collection or an array
	 */
	public boolean isMultiSelection() {
		if (component instanceof HtmlViewContentComponent) {
			return ((HtmlViewContentComponent) component).isMultiSelection();
		}

		return false;
	}

	public Set<Integer> selectedIndexes() {
		Set<Integer> result = new HashSet<Integer>();
		if (component instanceof HtmlViewContentComponent) {
			result = ((HtmlViewContentComponent) component).selectedIndex();
			return result;
		}
		return result;
	}

	public String selectedIndexesAsString() {
		if (this.component instanceof HtmlViewCollectionComposedComponent) {
			HtmlViewCollectionComposedComponent collComponent = (HtmlViewCollectionComposedComponent) component;
			if (collComponent.isMap()) {
				return "" + collComponent.getSelectedMapIndex();
			}
		}
		Set<Integer> indexes = selectedIndexes();
		if (indexes == null) {
			return "";
		}
		StringBuffer result = new StringBuffer("");
		boolean firstElement = true;
		for (Integer index : indexes) {
			if (firstElement) {
				firstElement = false;
			} else {
				result.append(",");
			}
			result.append(index);
		}
		return result.toString();
	}

	public boolean isSelected(int index) {
		Set<Integer> sel = selectedIndexes();
		return sel != null && sel.contains(index);
	}

	public String imageId() {
		final Long longId = component.getId();

		if (longId == null) {
			return "";
		}
		return longId.toString();
	}

	public int areaSize() {
		int result = 1;
		if (component instanceof HtmlViewAreaMode) {
			return ((HtmlViewAreaModeImpl) component).getAreaContainer().getAreaSize();
		}

		if (component instanceof HtmlViewCollectionComposedComponent) {

			Object me = ((HtmlViewCollectionComposedComponent) component).getContainerComponent().getContent();
			if (me instanceof Griddable) {
				return ((Griddable) me).getSizeOfGrid();
			}
		}

		return result;
	}

	public String areaVerticalAlignment() {
		return areaVerticalAlignment(component);
	}

	public String areaVerticalAlignment(final HtmlViewRenderable iComponent) {
		String align = null;

		if (iComponent instanceof HtmlViewFormAreaInstance) {
			align = ((HtmlViewFormAreaInstance) iComponent).getAreaAlign();

		} else if (iComponent instanceof HtmlViewAreaModeImpl) {
			HtmlViewAreaModeImpl c = (HtmlViewAreaModeImpl) iComponent;
			align = c.getAreaContainer().getAreaAlign();
		}

		if (align == null)
			return "";

		String[] aligns = align.split(" ");

		if (aligns[0] != null && aligns[0].equals("center"))
			aligns[0] = "middle";

		return aligns[0];
	}

	public String areaHorizontalAlignment() {
		return areaHorizontalAlignment(component);
	}

	public String areaHorizontalAlignment(final HtmlViewRenderable iComponent) {
		// if (iComponent == component)
		// System.out.println("areaHorizontalAlignment(!" + iComponent + ")->" + iComponent.getClass());
		// else
		// System.out.println("areaHorizontalAlignment(" + iComponent + ")->" + iComponent.getClass());

		AreaComponent area = null;

		if (iComponent instanceof AreaComponent)
			area = (AreaComponent) iComponent;
		else if (iComponent instanceof HtmlViewAreaModeImpl)
			area = ((HtmlViewAreaModeImpl) iComponent).getAreaContainer();
		else
			return "";

		String align = area.getAreaAlign();

		// System.out.println("= " + align);

		if (align == null)
			return "";

		String[] aligns = align.split(" ");
		return aligns.length > 1 ? aligns[1] : "";
	}

	public String loadAsUrl() {
		if (content() == null)
			return "";

		String url = contentAsString();
		if (url.length() == 0)
			return "";

		HttpServletRequest request = (HttpServletRequest) Roma.context().component(
				HttpAbstractSessionAspect.CONTEXT_REQUEST_PAR);

		boolean propagateSession = false;

		String serverName;

		if (url.contains(HttpUtils.VAR_LOCALHOST)) {
			serverName = "localhost";
			url = url.replace(HttpUtils.VAR_LOCALHOST, "");
		} else
			serverName = request.getServerName();

		if (url.contains(HttpUtils.VAR_APPLICATION)) {
			String app = HttpUtils.VAR_URL_HTTP + serverName + ":" + request.getServerPort() + request.getContextPath();
			url = url.replace(HttpUtils.VAR_APPLICATION, app);
		}

		StringBuilder buffer = null;
		if (url.contains(HttpUtils.VAR_CLIENT)) {
			// LET THE CLIENT LOAD THE CONTENT
			url = url.replace(HttpUtils.VAR_CLIENT, "");

			String width = "200px";
			String height = "200px";

			String scrolling = "no";

			buffer = new StringBuilder();
			buffer.append("<iframe frameborder='0' width='");
			buffer.append(width != null ? width.toString() : "100%");
			buffer.append("' height='");
			buffer.append(height != null ? height.toString() : "100%");
			buffer.append("' scrolling='");
			buffer.append(scrolling);
			buffer.append("' src='");
			buffer.append(url);
			buffer.append("'/>");
		} else {
			if (url.contains(HttpUtils.VAR_SESSION)) {
				url = url.replace(HttpUtils.VAR_SESSION, "");
				propagateSession = true;
			}

			buffer = HttpUtils.loadUrlResource(url, propagateSession, request);

		}
		if (buffer != null) {
			return buffer.toString();
		} else {
			return "";
		}
	}

	public String contextPath() {
		HttpServletRequest request = (HttpServletRequest) Roma.context().component(
				HttpAbstractSessionAspect.CONTEXT_REQUEST_PAR);
		return request.getContextPath();
	}

	public boolean selectionAviable() {
		if (component instanceof HtmlViewContentComponent) {
			return ((HtmlViewContentComponent) component).hasSelection();
		}

		return false;
	}

	public String imgContextPath() {
		return contextPath() + "/static/base/image/";
	}

	public long currentTime() {
		return System.currentTimeMillis();
	}

	public Object additionalInfo() {
		if (component instanceof HtmlViewContentComponentImpl) {
			return ((HtmlViewContentComponentImpl) component).getAdditionalInfo();
		}
		return new Object();
	}

}
