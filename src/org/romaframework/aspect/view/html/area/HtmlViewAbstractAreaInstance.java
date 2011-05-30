package org.romaframework.aspect.view.html.area;

import org.romaframework.aspect.view.area.AreaComponent;
import org.romaframework.aspect.view.area.AreaMode;
import org.romaframework.aspect.view.html.HtmlViewAspectHelper;
import org.romaframework.aspect.view.html.HtmlViewSession;
import org.romaframework.aspect.view.html.area.mode.HtmlViewAreaMode;
import org.romaframework.aspect.view.html.transformer.Transformer;
import org.romaframework.core.domain.type.TreeNode;
import org.romaframework.core.domain.type.TreeNodeLinkedHashMap;

public abstract class HtmlViewAbstractAreaInstance extends TreeNodeLinkedHashMap implements Transformer {

	/**
	 * The previosly generated html
	 */
	protected String						htmlString;
	protected boolean						visible;
	protected int								areaSize;
	protected String						areaAlign;
	protected String						areaStyle;
	protected HtmlViewAreaMode	areaMode;
	protected boolean						dirty	= true;
	protected Long							id;

	public HtmlViewAbstractAreaInstance(final TreeNodeLinkedHashMap parent, final String name) {
		super(parent, name);

	}

	/**
	 * @return the visible
	 */
	public boolean isVisible() {
		return visible;
	}

	/**
	 * @param visible
	 *          the visible to set
	 */
	public void setVisible(final boolean visible) {
		this.visible = visible;
	}

	/**
	 * @return the areaSize
	 */
	public Integer getAreaSize() {
		return areaSize;
	}

	/**
	 * @param areaSize
	 *          the areaSize to set
	 */
	public void setAreaSize(final int areaSize) {
		this.areaSize = areaSize;
	}

	/**
	 * @return the areaAlign
	 */
	public String getAreaAlign() {
		return areaAlign;
	}

	/**
	 * @param areaAlign
	 *          the areaAlign to set
	 */
	public void setAreaAlign(final String areaAlign) {
		this.areaAlign = areaAlign;
	}
	
	/**
	 * @return the areaStyle
	 */
	public String getAreaStyle() {
		return areaStyle;
	}

	/**
	 * @param areaStyle
	 *          the areaAlign to set
	 */
	public void setAreaStyle(final String areaStyle) {
		this.areaStyle = areaStyle;
	}

	public AreaMode getAreaMode() {
		return areaMode;
	}

	/**
	 * @return the htmlString
	 */
	public String getHtmlString() {
		return htmlString;
	}

	public AreaComponent searchArea(final String areaName) {
		return (AreaComponent) searchNode(areaName);
	}

	public long getId() {
		if (id == null) {
			final HtmlViewSession session = HtmlViewAspectHelper.getHtmlViewSession();
			id = session.addRenderableBinding((HtmlViewRenderable) this);
		}
		return id;
	}

	public Transformer getTransformer() {
		return this;
	}

	@Override
	public String toString() {
		final StringBuilder buffer = new StringBuilder();
		buffer.append(getName());
		if (areaMode != null) {
			buffer.append(":");
			buffer.append(areaMode.toString());
		}

		if (childrenMap != null) {
			buffer.append("{");
			for (final TreeNode c : childrenMap.values()) {
				if (buffer.charAt(buffer.length() - 1) != '{') {
					buffer.append(",");
				}
				buffer.append(c.toString());
			}
			buffer.append("}");
		}
		return buffer.toString();
	}

	public boolean isDirty() {
		return dirty;
	}

	public void setDirty(boolean dirty) {
		this.dirty = dirty;
	}

	public String getStyle() {
		return areaStyle;
	}

	public void setStyle(String areaStyle) {
		this.areaStyle = areaStyle;
	}
}
