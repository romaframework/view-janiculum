package org.romaframework.aspect.view.html;

import java.util.HashSet;
import java.util.IdentityHashMap;
import java.util.Map;
import java.util.Set;

import org.romaframework.aspect.view.area.AreaComponent;
import org.romaframework.aspect.view.form.ViewComponent;
import org.romaframework.aspect.view.html.area.HtmlViewArea;
import org.romaframework.aspect.view.html.area.HtmlViewFormArea;
import org.romaframework.aspect.view.html.area.HtmlViewRenderable;
import org.romaframework.aspect.view.html.area.HtmlViewScreenArea;
import org.romaframework.aspect.view.html.component.HtmlViewGenericComponent;
import org.romaframework.core.Roma;

public class DirtyHelper {

	private Map<Object, Set<HtmlViewRenderable>>	dirtyObjects	= new IdentityHashMap<Object, Set<HtmlViewRenderable>>();

	public static DirtyHelper getInstance() {
		DirtyHelper de = Roma.session().getProperty("DirtyHelper");
		if (de == null) {
			de = new DirtyHelper();
			Roma.session().setProperty("DirtyHelper", de);
		}
		return de;
	}

	public void makeDirty(Object object, ViewComponent component) {
		addDirty(object, (HtmlViewRenderable) component);
	}

	public void makeDirty(Object object, AreaComponent component) {
		addDirty(object, (HtmlViewRenderable) component);
	}

	private void addDirty(Object object, HtmlViewRenderable component) {
		Set<HtmlViewRenderable> comps = dirtyObjects.get(object);
		if (comps == null) {
			comps = new HashSet<HtmlViewRenderable>();
			dirtyObjects.put(object, comps);
		}
		comps.add(component);
	}

	public Set<HtmlViewRenderable> getChanges() {
		Set<HtmlViewRenderable> all = new HashSet<HtmlViewRenderable>();
		for (Set<HtmlViewRenderable> cur : dirtyObjects.values())
			all.addAll(cur);
		Set<HtmlViewRenderable> toRemove = new HashSet<HtmlViewRenderable>();
		for (HtmlViewRenderable htmlViewRenderable : all) {
			HtmlViewRenderable cur = htmlViewRenderable;
			while (cur != null) {
				if (cur instanceof HtmlViewFormArea) {
					if (((HtmlViewArea) cur).getParentArea() != null)
						cur = ((HtmlViewArea) cur).getParentArea();
					else
						cur = ((HtmlViewArea) cur).getForm();
				} else if (cur instanceof HtmlViewScreenArea) {
					cur = ((HtmlViewScreenArea) cur).getParentArea();
				} else if (cur instanceof HtmlViewGenericComponent) {
					if (((HtmlViewGenericComponent) cur).getContainerArea() != null)
						cur = ((HtmlViewGenericComponent) cur).getContainerArea();
					else if ((HtmlViewRenderable) ((HtmlViewGenericComponent) cur).getContainerComponent() != null)
						cur = (HtmlViewRenderable) ((HtmlViewGenericComponent) cur).getContainerComponent();
					else
						cur = ((HtmlViewGenericComponent) cur).getScreenAreaObject();
				}
				if (all.contains(cur)) {
					toRemove.add(htmlViewRenderable);
					break;
				}
			}
		}
		all.removeAll(toRemove);
		return all;
	}

	public void clear() {
		dirtyObjects.clear();
	}
}
