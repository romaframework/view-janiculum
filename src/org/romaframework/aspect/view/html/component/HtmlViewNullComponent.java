package org.romaframework.aspect.view.html.component;

import java.io.OutputStream;
import java.util.Collection;

import org.romaframework.aspect.view.form.ViewComponent;
import org.romaframework.aspect.view.html.area.HtmlViewScreenArea;
import org.romaframework.aspect.view.html.transformer.Transformer;
import org.romaframework.core.schema.SchemaClassElement;
import org.romaframework.core.schema.SchemaField;
import org.romaframework.core.schema.SchemaObject;

public class HtmlViewNullComponent implements HtmlViewGenericComponent {

	public Collection<HtmlViewGenericComponent> getChildren() {
		return null;
	}

	public SchemaClassElement getSchemaElement() {
		return null;
	}

	public HtmlViewScreenArea getScreenAreaObject() {
		return null;
	}

	public void setScreenArea(HtmlViewScreenArea screenArea) {
	}

	public String getHtmlId() {
		return null;
	}

	public long getId() {
		return 0;
	}

	public Transformer getTransformer() {
		return null;
	}

	public void render(OutputStream out) {
	}

	public void renderPart(String part, OutputStream out) {
	}

	public void resetValidation() {
	}

	public boolean validate() {
		return false;
	}

	public void bind(SchemaField iSchemaField, Object iValue) {

	}

	public void bind(SchemaField iSchemaField, Object iValue, Object component) {

	}

	public void close() {
	}

	public String getScreenArea() {
		return null;
	}

	public void handleException(Throwable t) {
	}

	public void render(ViewComponent iFormToRender) {
	}

	public void renderContent() {
	}

	public void setScreenArea(String area) {
	}

	public void destroy() {
	}

	public ViewComponent getContainerComponent() {
		return null;
	}

	public Object getContent() {
		return null;
	}

	public Object getFieldComponent(String iName) {
		return null;
	}

	public SchemaField getSchemaField() {
		return null;
	}

	public SchemaObject getSchemaObject() {
		return null;
	}

	public void setContent(Object iContent) {

	}

	public void setSchemaField(SchemaField iSchemaField) {

	}

	public void setSchemaObject(SchemaObject iSchemaObject) {

	}

	public Collection<HtmlViewGenericComponent> getChildrenFilled() {
		return getChildren();
	}

	public void clearComponents() {
	}

	public void clearChildren() {
	}
}
