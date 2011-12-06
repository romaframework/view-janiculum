package org.romaframework.aspect.view.html.area.mode;

import java.io.IOException;
import java.io.Writer;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

import org.romaframework.aspect.view.area.AreaComponent;
import org.romaframework.aspect.view.html.HtmlViewAspectHelper;
import org.romaframework.aspect.view.html.HtmlViewSession;
import org.romaframework.aspect.view.html.area.HtmlViewRenderable;
import org.romaframework.aspect.view.html.area.HtmlViewScreenArea;
import org.romaframework.aspect.view.html.component.HtmlViewComponentLabel;
import org.romaframework.aspect.view.html.component.HtmlViewContentComponent;
import org.romaframework.aspect.view.html.transformer.Transformer;
import org.romaframework.aspect.view.html.transformer.manager.TransformerManager;
import org.romaframework.core.Roma;

public class HtmlViewAreaModeImpl implements HtmlViewAreaMode {

	protected List<HtmlViewRenderable>	renderables	= new LinkedList<HtmlViewRenderable>();
	protected AreaComponent							area;
	protected String										htmlString;
	protected AreaComponent							areaContainer;
	private Long												id;
	private String											areaName;
	private String											type;

	public HtmlViewAreaModeImpl(final AreaComponent iContainer, final String type) {
		this.area = iContainer;
		this.type = type;
	}

	public Object createComponent(final int size) {
		// makes nothing
		return null;
	}

	public Object createComponentContainer(final Object componentContainer, final Object subComponentContainer, final String areaAlign) {
		// makes nothing
		return null;
	}

	public boolean isChildrenAllowed() {
		return true;
	}

	/**
	 * Place a component removing all the others previously added
	 * 
	 * @param iComponentContainer
	 *          the container of the component
	 * @param iComponentToPlace
	 *          the component to place
	 * @return
	 */
	public Object placeComponent(final Object componentContainer, final Object componentToPlace) {
		if (renderables == null) {
			renderables = new ArrayList<HtmlViewRenderable>();
		}
		renderables.clear();
		renderables.add((HtmlViewRenderable) componentToPlace);
		areaContainer = (AreaComponent) componentContainer;
		return componentToPlace;
	}

	public void addRenderables(final AreaComponent container, final List<HtmlViewRenderable> irenderables) {
		areaContainer = container;
		renderables.clear();
		renderables.addAll(irenderables);
	}

	public long getId() {
		if (id == null) {
			final HtmlViewSession session = HtmlViewAspectHelper.getHtmlViewSession();
			id = session.addRenderableBinding(this);
		}
		return id;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.romaframework.aspect.view.html.area.HtmlViewRenderable#render()
	 */

	public void render(Writer writer) throws IOException {
		getTransformer().transform(this, writer);
	}

	public Transformer getTransformer() {
		return Roma.component(TransformerManager.class).getComponent(type);
	}

	public List<HtmlViewRenderable> getRenderables() {
		return renderables;
	}

	public String getHtmlId() {
		return ((HtmlViewRenderable) areaContainer).getHtmlId();
	}

	public String getType() {
		return type;
	}

	@Override
	public String toString() {
		return type;
	}

	public AreaComponent getAreaContainer() {
		return areaContainer;
	}

	public boolean validate() {
		// Do nothing
		return true;
	}

	public void resetValidation() {
	}

	public void renderPart(final String part, Writer writer) {
		// TODO It should render only a sub area
	}

	public String getAreaName() {
		return areaName;
	}

	public void setAreaName(String areaName) {
		this.areaName = areaName;
	}

	public void clear() {
		renderables.clear();
	}

	public boolean isScreenArea() {
		return area instanceof HtmlViewScreenArea;
	}

	public AreaComponent getContainer() {
		return areaContainer;
	}
}
