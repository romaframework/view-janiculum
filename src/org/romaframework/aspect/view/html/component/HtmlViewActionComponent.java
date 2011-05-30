package org.romaframework.aspect.view.html.component;

import java.util.ArrayList;
import java.util.Collection;

import org.romaframework.aspect.view.html.area.HtmlViewScreenArea;
import org.romaframework.core.schema.SchemaAction;
import org.romaframework.core.schema.SchemaObject;

public class HtmlViewActionComponent extends HtmlViewAbstractComponent {

	private static final ArrayList<HtmlViewGenericComponent>	children	= new ArrayList<HtmlViewGenericComponent>();

	public HtmlViewActionComponent(final HtmlViewContentComponent containerComponent, final HtmlViewScreenArea screenArea,
			final SchemaAction schemaElement, final Object iPojo) {
		super(containerComponent, screenArea, schemaElement);
	}

	public void close() {
		// TODO Auto-generated method stub

	}

	public SchemaAction getActionField() {
		return (SchemaAction) schemaElement;
	}

	public boolean validate() {
		return true;
	}

	public void resetValidation() {
	}

	public SchemaObject getSchemaInstance() {

		return null;
	}

	public void setMetaDataSchema(SchemaObject schemaObject) {

	}

	public void renderContent() {
		// makes nothing

	}

	public Collection<HtmlViewGenericComponent> getChildren() {

		return children;
	}

	public void clearComponents() {
		// TODO Auto-generated method stub

	}

	public void clearChildren() {
		if (children != null)
			children.clear();
	}
}
