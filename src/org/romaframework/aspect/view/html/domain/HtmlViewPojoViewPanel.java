package org.romaframework.aspect.view.html.domain;

import java.lang.reflect.Method;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.romaframework.aspect.core.annotation.AnnotationConstants;
import org.romaframework.aspect.core.annotation.CoreField;
import org.romaframework.aspect.flow.FlowAspect;
import org.romaframework.aspect.view.ViewAspect;
import org.romaframework.aspect.view.ViewCallback;
import org.romaframework.aspect.view.ViewConstants;
import org.romaframework.aspect.view.annotation.ViewField;
import org.romaframework.aspect.view.feature.ViewElementFeatures;
import org.romaframework.core.Roma;
import org.romaframework.core.flow.ObjectContext;

public class HtmlViewPojoViewPanel implements ViewCallback {
	protected static Log	log	= LogFactory.getLog(HtmlViewPojoViewPanel.class);

	@CoreField(useRuntimeType = AnnotationConstants.TRUE)
	@ViewField(layout = ViewConstants.LAYOUT_EXPAND)
	protected Object			content;

	protected Object			backObject;
	protected String			callback;
	protected boolean			edit;

	public HtmlViewPojoViewPanel(final Object content) {
		this(content, false, null, null);
	}

	public HtmlViewPojoViewPanel(final Object content, final boolean edit) {
		this(content, edit, null, null);
	}

	public HtmlViewPojoViewPanel(final Object content, final boolean edit, final Object backObject, final String callback) {
		this.content = content;
		this.backObject = backObject;
		this.callback = callback;
		this.edit = edit;
	}

	public void ok() {
		Roma.aspect(FlowAspect.class).back();
		if (callback == null || content == null) {
			return;
		}

		try {
			Method method = null;
			Class<?> clazz = content.getClass();
			while (method == null) {
				try {// TODO avoid exceptions... cycle over methods...?
					method = backObject.getClass().getMethod(callback, new Class[] { clazz });
					break;
				} catch (final NoSuchMethodException ex) {
				}
				if (clazz.equals(Object.class)) {
					break;
				}
				clazz = clazz.getSuperclass();
			}
			if (method != null) {
				method.invoke(backObject, content);
			}
		} catch (final Throwable e) {
			log.error("could not confirm object modification: " + e);
			log.debug("", e);
		}
	}

	public void cancel() {
		Roma.aspect(FlowAspect.class).back();
	}

	public Object getContent() {
		return content;
	}

	public void setContent(final Object content) {
		this.content = content;
	}

	public void onDispose() {
	}

	public void onShow() {
		if (!edit) {
			ObjectContext.getInstance().setActionFeature(this, ViewAspect.ASPECT_NAME, "ok", ViewElementFeatures.VISIBLE, false);
		}
	}

}
