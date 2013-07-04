package org.romaframework.aspect.view.html.area;

import java.util.List;

import org.romaframework.aspect.view.area.AreaComponent;
import org.romaframework.aspect.view.html.component.HtmlViewContentForm;

public interface HtmlViewArea extends AreaComponent, HtmlViewRenderable {

	public List<HtmlViewRenderable> getComponents();

	public boolean isDirty();

	public void setDirty(boolean dirty);
	
	public HtmlViewContentForm getForm();
	
	public HtmlViewArea getParentArea();
}
