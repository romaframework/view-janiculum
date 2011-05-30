package org.romaframework.aspect.view.html.area.mode;

import java.io.IOException;
import java.io.OutputStream;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

import org.romaframework.aspect.view.area.AreaComponent;
import org.romaframework.aspect.view.html.HtmlViewAspectHelper;
import org.romaframework.aspect.view.html.HtmlViewSession;
import org.romaframework.aspect.view.html.area.HtmlViewRenderable;
import org.romaframework.aspect.view.html.component.HtmlViewComponentLabel;
import org.romaframework.aspect.view.html.component.HtmlViewContentComponent;
import org.romaframework.aspect.view.html.transformer.Transformer;
import org.romaframework.aspect.view.html.transformer.manager.HtmlViewTransformerManager;
import org.romaframework.core.flow.ObjectContext;

public class HtmlViewAreaModeImpl implements HtmlViewAreaMode {

	protected List<HtmlViewRenderable>	renderables	= new LinkedList<HtmlViewRenderable>();
	protected String										htmlString;
	protected AreaComponent							areaContainer;
	private Long												id;
	private String											areaName;
	private String											type;

	public HtmlViewAreaModeImpl() {
	}

	public HtmlViewAreaModeImpl(final AreaComponent iContainer, final String type) {
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

		for (final HtmlViewRenderable renderable : irenderables) {
			if (renderable instanceof HtmlViewContentComponent) {
				final HtmlViewContentComponent component = (HtmlViewContentComponent) renderable;
				renderables.add(createLabelForElement(component));
			}
			renderables.add(renderable);
		}
	}

	private HtmlViewRenderable createLabelForElement(final HtmlViewContentComponent element) {
		return new HtmlViewComponentLabel(element, element.getScreenAreaObject());
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

	public void render(OutputStream out) throws IOException {
		final Transformer transformer = getTransformer();
		transformer.transform(this, out);
	}

	public Transformer getTransformer() {
		final HtmlViewTransformerManager transformerManager = ObjectContext.getInstance()
				.getComponent(HtmlViewTransformerManager.class);
		final Transformer transformer = transformerManager.getComponent(type);
		return transformer;
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

	public void renderPart(final String part, OutputStream out) {
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

}
