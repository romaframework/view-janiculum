/*
 * Copyright 2006-2007 Giordano Maestro (giordano.maestro--at--assetdata.it)
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package org.romaframework.aspect.view.html.component;

import java.io.IOException;
import java.io.Writer;
import java.util.ArrayList;
import java.util.Collection;

import org.romaframework.aspect.view.form.ViewComponent;
import org.romaframework.aspect.view.html.area.HtmlViewRenderable;
import org.romaframework.aspect.view.html.area.HtmlViewScreenArea;
import org.romaframework.aspect.view.html.transformer.AbstractHtmlViewTransformer;
import org.romaframework.aspect.view.html.transformer.Transformer;
import org.romaframework.aspect.view.html.transformer.helper.JaniculumWrapper;
import org.romaframework.core.schema.SchemaObject;

/**
 * This component is used to render a label
 * 
 * @author Giordano Maestro (giordano.maestro--at--assetdata.it)
 * 
 */
public class HtmlViewComponentLabel extends HtmlViewAbstractContentComponent {

	private static final ArrayList<HtmlViewGenericComponent>	children	= new ArrayList<HtmlViewGenericComponent>();
	private final ViewComponent																contentComponent;

	public HtmlViewComponentLabel(final ViewComponent iContentComponent, final HtmlViewScreenArea area) {
		super((HtmlViewContentComponent) iContentComponent.getContainerComponent(), iContentComponent.getSchemaField(), iContentComponent.getContent(), area);
		contentComponent = iContentComponent;
	}

	@Override
	public long getId() {
		return -10;
	}

	@Override
	public Transformer getTransformer() {
		return null;
	}

	@Override
	public void render(Writer writer) throws IOException {

		if (getSchemaElement() == null || contentComponent == null) {
			return;
		}

		if (!((HtmlViewContentComponent) contentComponent).hasLabel()) {
			return;
		}

		String label = JaniculumWrapper.i18NLabel(this);
		label = AbstractHtmlViewTransformer.getComponentLabel((HtmlViewContentComponent) contentComponent, label);

		writer.write(label);

	}

	public void resetValidation() {
	}


	@Override
	public void renderPart(final String part, Writer writer) {
		// Makes nothing
	}

	@Override
	public String getHtmlId() {
		return ((HtmlViewRenderable) containerComponent).getHtmlId() + SEPARATOR + getSchemaElement().getName();
	}

	public SchemaObject getSchemaInstance() {

		return null;
	}

	public void setMetaDataSchema(SchemaObject schemaObject) {

	}

	public Collection<HtmlViewGenericComponent> getChildren() {

		return children;
	}

	public Collection<HtmlViewGenericComponent> getChildrenFilled() {
		return getChildren();
	}

	public void clearChildren() {
		children.clear();
	}

}
