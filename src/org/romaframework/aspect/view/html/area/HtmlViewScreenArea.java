package org.romaframework.aspect.view.html.area;

import org.romaframework.aspect.view.area.AreaComponent;
import org.romaframework.aspect.view.html.component.HtmlViewContentForm;

public interface HtmlViewScreenArea extends HtmlViewRenderable, AreaComponent {

	public void bindPojo(Object iPojo);

	public void bindForm(HtmlViewContentForm iPojo);

	public HtmlViewContentForm getForm();

	public void clearArea();

}
