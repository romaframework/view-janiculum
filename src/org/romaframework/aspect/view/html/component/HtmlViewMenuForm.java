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
import java.io.OutputStream;

import org.romaframework.aspect.view.html.HtmlViewAspectHelper;
import org.romaframework.aspect.view.html.area.HtmlViewScreenArea;
import org.romaframework.aspect.view.html.transformer.Transformer;
import org.romaframework.aspect.view.html.transformer.manager.TransformerManager;
import org.romaframework.core.Roma;
import org.romaframework.core.schema.SchemaField;
import org.romaframework.core.schema.SchemaObject;

public class HtmlViewMenuForm extends HtmlViewConfigurableEntityForm {

//	public HtmlViewMenuForm(final HtmlViewContentComponent containerComponent, final SchemaObject schemaObject,
//			final HtmlViewScreenArea screenArea) {
//		super(containerComponent, schemaObject, null,screenArea);
//	}

	public HtmlViewMenuForm(final HtmlViewContentComponent htmlViewConfigurableEntityForm, final SchemaObject schemaObject,
			final SchemaField field, final HtmlViewScreenArea screenArea,Integer rowIndex, Integer colIndex, String label) {
		super(htmlViewConfigurableEntityForm, schemaObject, field, screenArea, rowIndex,colIndex,label);

	}

	@Override
	public void render(OutputStream out) throws IOException{
		final TransformerManager transformerManager = Roma.component(TransformerManager.class);
		Transformer transformer = null;
		if (getSchemaElement() == null) {
			transformer = transformerManager.getComponent(HtmlViewAspectHelper.getDefaultRenderType(schemaObject));
		} else {
			transformer = transformerManager.getComponent(HtmlViewAspectHelper.getDefaultRenderType(getSchemaElement()));
		}
		transformer.transform(this, out);
	}

}
