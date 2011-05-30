package org.romaframework.aspect.view.html.area;

import java.io.IOException;
import java.io.OutputStream;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import org.romaframework.aspect.view.area.AreaMode;
import org.romaframework.aspect.view.form.ContentForm;
import org.romaframework.aspect.view.form.ViewComponent;
import org.romaframework.aspect.view.html.area.mode.HtmlViewAreaMode;
import org.romaframework.aspect.view.html.area.mode.HtmlViewAreaModeImpl;
import org.romaframework.aspect.view.html.binder.NullBinder;
import org.romaframework.aspect.view.html.component.HtmlViewContentForm;
import org.romaframework.aspect.view.html.component.HtmlViewGenericComponent;
import org.romaframework.aspect.view.html.transformer.Transformer;
import org.romaframework.aspect.view.html.transformer.helper.TransformerHelper;
import org.romaframework.core.domain.type.TreeNodeLinkedHashMap;
import org.romaframework.core.schema.xmlannotations.XmlFormAreaAnnotation;

public class HtmlViewFormAreaInstance extends HtmlViewAbstractAreaInstance implements HtmlViewFormArea {

	protected List<HtmlViewGenericComponent>	components;
	protected HtmlViewScreenArea							screenArea;
	private final ContentForm									contentForm;

	public HtmlViewFormAreaInstance(final ContentForm form, final TreeNodeLinkedHashMap parent, final String name,
			final HtmlViewScreenArea screenArea) {
		super(parent, name);
		contentForm = form;
		areaMode = new HtmlViewAreaModeImpl(this, HtmlViewAreaMode.DEF_AREAMODE_NAME);
		this.screenArea = screenArea;
	}

	public HtmlViewFormAreaInstance(final ContentForm form, final HtmlViewFormAreaInstance iParentAreaInstance,
			final XmlFormAreaAnnotation iAreaTag, final HtmlViewScreenArea screenArea) {
		super(iParentAreaInstance, iAreaTag.getName());
		contentForm = form;
		this.screenArea = screenArea;
		// Set the size of the area
		areaSize = iAreaTag.getSize() != null ? iAreaTag.getSize() : 0;
		// Set the alignment of the area
		areaAlign = iAreaTag.getAlign() != null ? iAreaTag.getAlign() : null;
		// Set the style of the area
		areaStyle = iAreaTag.getStyle() != null ? iAreaTag.getStyle() : null;
		if (iAreaTag.getType() != null) {
			areaMode = new HtmlViewAreaModeImpl(this, iAreaTag.getType());
		} else {
			areaMode = new HtmlViewAreaModeImpl(this, HtmlViewAreaMode.DEF_AREAMODE_NAME);
		}
		((HtmlViewAreaModeImpl) areaMode).setAreaName(iAreaTag.getName());

		// BROWSE RECURSIVELY ALL CHILDREN
		for (final XmlFormAreaAnnotation areaDef : iAreaTag.getChildren()) {
			// value = filterNodeContent(areaDef.xmlText());
			final HtmlViewFormAreaInstance subArea = new HtmlViewFormAreaInstance(contentForm, this, areaDef, this.screenArea);

			if (areaDef.getName() != null) {
				addChild(areaDef.getName(), subArea);
			}
		}

	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.romaframework.aspect.view.html.area.HtmlViewRenderable#render()
	 */
	public void render(OutputStream out) throws IOException {
		getTransformer().transform(this, out);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.romaframework.aspect.view.html.area.HtmlViewFormArea#addComponent(org.romaframework.aspect.view.form.ViewComponent)
	 */
	public void addComponent(final HtmlViewGenericComponent component) {
		if (components == null) {
			components = new ArrayList<HtmlViewGenericComponent>();
		}
		components.add(component);

		if (component instanceof HtmlViewContentForm) {
			// ViewHelper.invokeOnShow(((ViewComponent) component).getContent(), "onShow area: ");
		}

	}

	public HtmlViewBinder getBinder(HtmlViewRenderable renderable) {
		// It uses no binder
		return NullBinder.getInstance();
	}

	public void transform(final HtmlViewRenderable component, OutputStream out) throws IOException {
		final List<HtmlViewRenderable> renderables = new ArrayList<HtmlViewRenderable>();
		final Collection<? extends HtmlViewRenderable> children = (Collection<? extends HtmlViewRenderable>) getChildren();
		if (children != null) {
			renderables.addAll(children);
		}
		final Collection<? extends HtmlViewRenderable> componentsToAdd = components;
		if (componentsToAdd != null) {
			renderables.addAll(componentsToAdd);
		}
		areaMode.addRenderables(this, renderables);
		areaMode.render(out);
	}

	public void transformPart(final HtmlViewRenderable component, final String part, OutputStream out) {
		// TODO Move here the Tag Library code
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.romaframework.aspect.view.html.area.HtmlViewRenderable#getHtmlId()
	 */
	public String getHtmlId() {
		if (parent == null) {
			return ((HtmlViewRenderable) contentForm).getHtmlId() + TransformerHelper.SEPARATOR + getName();
		} else {
			return ((HtmlViewRenderable) parent).getHtmlId() + TransformerHelper.SEPARATOR + getName();
		}
	}

	public HtmlViewScreenArea getScreenArea() {
		return screenArea;
	}

	public void setScreenArea(final HtmlViewScreenArea screenArea) {
		this.screenArea = screenArea;
		if (getChildren() != null) {
			for (final HtmlViewFormAreaInstance child : (Collection<HtmlViewFormAreaInstance>) getChildren()) {
				child.setScreenArea(screenArea);
			}
		}

	}

	public boolean removeComponent(final ViewComponent component) {
		return components.remove(component);
	}

	public void replaceComponent(final ViewComponent oldComponent, final HtmlViewGenericComponent newComponent) {
		int pos = components.indexOf(oldComponent);
		components.remove(pos);
		components.add(pos, newComponent);
	}

	public boolean validate() {
		boolean result = true;
		if (components != null) {
			for (final ViewComponent component : components) {
				if (component instanceof HtmlViewRenderable) {
					if (!((HtmlViewRenderable) component).validate()) {
						result = false;
					}
				}
			}
		}
		return result;
	}

	public void resetValidation() {
		if (components != null) {
			for (final ViewComponent component : components) {
				if (component instanceof HtmlViewRenderable) {
					((HtmlViewRenderable) component).resetValidation();
				}
			}
		}
	}

	public void renderPart(final String part, OutputStream out) throws IOException {
		render(out);
	}

	public void setAreaMode(final AreaMode areaMode) {
		this.areaMode = (HtmlViewAreaMode) areaMode;

	}

	public void setAreaSize(final Integer size) {
		areaSize = size;

	}

	public String getType() {
		// TODO Auto-generated method stub
		return Transformer.PRIMITIVE;
	}

	public void clear() {
		if (components != null)
			components.clear();
		if (areaMode != null)
			areaMode.clear();
	}

	// public boolean isDirty() {
	// if(super.isDirty()){
	// return true;
	// }
	// if(components!=null){
	// for (HtmlViewGenericComponent component:components){
	// if(component instanceof HtmlViewAbstractComponent && ((HtmlViewAbstractComponent)component).isDirty()){
	// return true;
	// }
	// }
	// }
	// return false;
	// }
}
