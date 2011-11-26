package org.romaframework.aspect.view.html.area.mode;

import java.util.List;

import org.romaframework.aspect.view.area.AreaComponent;
import org.romaframework.aspect.view.area.AreaMode;
import org.romaframework.aspect.view.area.AreaModeManager;
import org.romaframework.aspect.view.html.area.HtmlViewRenderable;

public interface HtmlViewAreaMode extends AreaMode, HtmlViewRenderable {

	public static String	DEF_AREAMODE_NAME	= AreaModeManager.DEF_AREAMODE_NAME;

	public void addRenderables(AreaComponent container, List<HtmlViewRenderable> iRenderables);

	public String getType();

	public String getAreaName();

	public void clear();
	
	public boolean isScreenArea();
	
	public AreaComponent getContainer();

}
