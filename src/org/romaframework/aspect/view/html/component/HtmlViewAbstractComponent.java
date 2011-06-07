package org.romaframework.aspect.view.html.component;

import java.io.IOException;
import java.io.OutputStream;
import java.util.Collection;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.romaframework.aspect.view.ViewAspect;
import org.romaframework.aspect.view.area.AreaComponent;
import org.romaframework.aspect.view.feature.ViewFieldFeatures;
import org.romaframework.aspect.view.form.ViewComponent;
import org.romaframework.aspect.view.html.HtmlViewAspectHelper;
import org.romaframework.aspect.view.html.HtmlViewSession;
import org.romaframework.aspect.view.html.area.HtmlViewRenderable;
import org.romaframework.aspect.view.html.area.HtmlViewScreenArea;
import org.romaframework.aspect.view.html.transformer.Transformer;
import org.romaframework.aspect.view.html.transformer.helper.TransformerHelper;
import org.romaframework.aspect.view.html.transformer.manager.HtmlViewTransformerManager;
import org.romaframework.aspect.view.screen.Screen;
import org.romaframework.core.Roma;
import org.romaframework.core.domain.type.TreeNode;
import org.romaframework.core.schema.SchemaClassDefinition;
import org.romaframework.core.schema.SchemaClassElement;
import org.romaframework.core.schema.SchemaField;
import org.romaframework.core.schema.SchemaHelper;
import org.romaframework.core.schema.SchemaObject;

public abstract class HtmlViewAbstractComponent implements HtmlViewGenericComponent {
	protected static final String				SEPARATOR	= TransformerHelper.SEPARATOR;
	protected String										htmlString;
	protected HtmlViewContentComponent	containerComponent;
	protected Log												log				= LogFactory.getLog(this.getClass());
	protected HtmlViewScreenArea				screenArea;
	protected SchemaClassElement				schemaElement;
	protected boolean										dirty			= true;
	protected Long											id;

	public HtmlViewAbstractComponent(final HtmlViewContentComponent containerComponent, final HtmlViewScreenArea screenArea,
			final SchemaClassElement schemaElement) {
		super();
		this.containerComponent = containerComponent;
		this.screenArea = screenArea;
		this.schemaElement = schemaElement;
	}

	public String getHtmlId() {
		return ((HtmlViewRenderable) containerComponent).getHtmlId() + SEPARATOR + getSchemaElement().getName();
	}

	/**
	 * @return the containerComponent
	 */
	public ViewComponent getContainerComponent() {
		return containerComponent;
	}

	/**
	 * @param screenArea
	 *          the screenArea to set
	 */
	public void setScreenArea(final HtmlViewScreenArea screenArea) {
		this.screenArea = screenArea;
	}

	/**
	 * @return the screenArea
	 */
	public String getScreenArea() {
		if (screenArea != null) {
			return screenArea.getName();
		} else {
			return null;
		}
	}

	/**
	 * @return the screenArea
	 */
	public HtmlViewScreenArea getScreenAreaObject() {
		return screenArea;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.romaframework.aspect.view.html.area.HtmlViewRenderable#render()
	 */
	public void render(OutputStream out) throws IOException {
		final Transformer transformer = getTransformer();
		transformer.transform(this, out);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.romaframework.aspect.view.html.area.HtmlViewRenderable#renderPart(java.lang.String)
	 */
	public void renderPart(final String part, OutputStream out) throws IOException {
		final Transformer transformer = getTransformer();
		transformer.transformPart(this, part, out);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.romaframework.aspect.view.html.area.HtmlViewRenderable#getTransformer()
	 */
	public Transformer getTransformer() {
		final HtmlViewTransformerManager transformerManager = Roma.component(HtmlViewTransformerManager.class);
		// Transformer transformer = transformerManager.getComponent((String) schemaElement.getFeature(ViewAspect.ASPECT_NAME,
		// ViewElementFeatures.RENDER));
		final Transformer transformer = transformerManager.getComponent(HtmlViewAspectHelper.getDefaultRenderType(getSchemaElement()));
		return transformer;
	}

	/**
	 * @param screenArea
	 *          the screenArea to set
	 */
	@Deprecated
	public void setScreenArea(final String screenArea) {
		final Screen screen = Roma.aspect(ViewAspect.class).getScreen();
		final AreaComponent screenAreaToset = screen.getArea(screenArea);
		this.setScreenArea((HtmlViewScreenArea) screenAreaToset);
	}

	/**
	 * @return the htmlString
	 */
	String getHtmlString() {
		return htmlString;
	}

	public long getId() {
		if (id == null) {
			final HtmlViewSession session = HtmlViewAspectHelper.getHtmlViewSession();
			id = session.addRenderableBinding(this);
		}
		return id;

	}

	public void handleException(final Throwable t) {
		// TODO Manage Exception
		t.printStackTrace();
	}

	public SchemaClassElement getSchemaElement() {
		return schemaElement;
	}

	public void bind(SchemaField schemaField, Object value, Object component) {
	}

	public void bind(SchemaField schemaField, Object value) {
	}

	public void close() {
	}

	public void render(ViewComponent formToRender) {
	}

	public void renderContent() {
	}

	public Object getContent() {
		return null;
	}

	public Object getFieldComponent(String name) {
		return null;
	}

	public SchemaField getSchemaField() {
		return null;
	}

	public SchemaObject getSchemaObject() {
		return null;
	}

	public void setContent(Object content) {
	}

	public void setSchemaField(SchemaField schemaField) {
	}

	public void setSchemaObject(SchemaObject schemaObject) {
	}

	public void destroy() {
	}

	@Override
	public String toString() {
		final StringBuilder buffer = new StringBuilder();
		buffer.append("schema: ");
		if (getSchemaElement() != null) {
			buffer.append(getSchemaElement().toString());
		}
		return buffer.toString();
	}

	public boolean isDirty() {
		return dirty;
	}

	public void setDirty(boolean dirty) {
		this.dirty = dirty;
	}

	protected boolean isCollectionField() {

		if (this instanceof HtmlViewContentComponent) {
			Class<?> typeClass = (Class<?>) getSchemaField().getLanguageType();
			if (SchemaHelper.isMultiValueObject(((HtmlViewContentComponent) this).getSchemaField())) {
				return true;
			} else if (TreeNode.class.isAssignableFrom(typeClass)) {
				return true;
			}
		}
		return false;
	}

	protected SchemaField getSelectionSchemaField() {
		if (this instanceof HtmlViewContentComponent) {
			if (isCollectionField()) {
				SchemaField schemaField = ((HtmlViewContentComponent) this).getSchemaField();
				String selectionFieldName = (String) schemaField.getFeature(ViewFieldFeatures.SELECTION_FIELD);
				if (selectionFieldName != null) {
					return schemaField.getEntity().getFields().get(selectionFieldName);
				}
			}
		}
		return null;
	}

	protected SchemaClassDefinition getSelectionSchemaClassDefinition() {
		if (this instanceof HtmlViewContentComponent) {
			if (isCollectionField()) {
				SchemaField schemaField = ((HtmlViewContentComponent) this).getSchemaField();
				String selectionFieldName = (String) schemaField.getFeature(ViewFieldFeatures.SELECTION_FIELD);
				if (selectionFieldName != null) {
					return schemaField.getEntity().getSchemaClass();
				}
			}
		}
		return null;
	}

	protected String getSelectionFieldName() {
		if (this instanceof HtmlViewContentComponent) {
			if (isCollectionField()) {
				SchemaField schemaField = ((HtmlViewContentComponent) this).getSchemaField();
				String selectionFieldName = (String) schemaField.getFeature(ViewFieldFeatures.SELECTION_FIELD);
				if (selectionFieldName != null) {
					return selectionFieldName;
				}
			}
		}
		return null;
	}

	public Collection<HtmlViewGenericComponent> getChildrenFilled() {
		return getChildren();
	}
}
