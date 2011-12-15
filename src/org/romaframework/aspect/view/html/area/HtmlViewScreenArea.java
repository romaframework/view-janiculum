package org.romaframework.aspect.view.html.area;

import org.romaframework.aspect.view.area.AreaComponent;
import org.romaframework.aspect.view.area.AreaMode;
import org.romaframework.aspect.view.html.component.HtmlViewContentForm;

public interface HtmlViewScreenArea extends HtmlViewRenderable, AreaComponent {

	/**
	 * @return the areaMode
	 */
	public AreaMode getAreaMode();
	
	public void bindPojo(Object iPojo);

	public void bindForm(HtmlViewContentForm iPojo);

	public HtmlViewContentForm getForm();

}
