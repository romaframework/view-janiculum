package org.romaframework.aspect.view.html;

import java.io.IOException;
import java.io.Writer;

import org.json.JSONException;
import org.json.JSONWriteable;
import org.romaframework.aspect.view.html.area.HtmlViewScreenAreaInstance;
import org.romaframework.aspect.view.html.component.HtmlViewAbstractComponent;
import org.romaframework.aspect.view.html.constants.TransformerConstants;

public class ComponentWritable implements JSONWriteable {

	private HtmlViewScreenAreaInstance	area;
	private HtmlViewAbstractComponent		component;

	public ComponentWritable(HtmlViewScreenAreaInstance area) {
		this.area = area;
	}

	public ComponentWritable(HtmlViewAbstractComponent component) {
		this.component = component;
	}

	public Writer write(Writer writer) throws JSONException {
		try {
			if (area != null) {
				area.render(writer);
			} else if (component != null) {
				component.getTransformer().transformPart(component, TransformerConstants.PART_ALL, writer);
			}
		} catch (IOException e) {
			throw new JSONException(e);
		}
		return writer;
	}

}
