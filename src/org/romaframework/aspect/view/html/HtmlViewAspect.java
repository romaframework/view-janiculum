/*
 * Copyright 2006 Giordano Maestro (giordano.maestro--at--assetdata.it)
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package org.romaframework.aspect.view.html;

import java.lang.annotation.Annotation;
import java.util.Arrays;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import javax.servlet.http.HttpServletRequest;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.romaframework.aspect.core.CoreAspect;
import org.romaframework.aspect.core.feature.CoreFieldFeatures;
import org.romaframework.aspect.flow.FlowAspect;
import org.romaframework.aspect.session.SessionAspect;
import org.romaframework.aspect.session.SessionInfo;
import org.romaframework.aspect.view.ViewAspect;
import org.romaframework.aspect.view.ViewAspectAbstract;
import org.romaframework.aspect.view.ViewConstants;
import org.romaframework.aspect.view.ViewException;
import org.romaframework.aspect.view.ViewHelper;
import org.romaframework.aspect.view.area.AreaComponent;
import org.romaframework.aspect.view.command.ViewCommand;
import org.romaframework.aspect.view.command.impl.DownloadReaderViewCommand;
import org.romaframework.aspect.view.command.impl.DownloadStreamViewCommand;
import org.romaframework.aspect.view.command.impl.OpenWindowViewCommand;
import org.romaframework.aspect.view.command.impl.RedirectViewCommand;
import org.romaframework.aspect.view.command.impl.RefreshViewCommand;
import org.romaframework.aspect.view.command.impl.ReportingDownloadViewCommand;
import org.romaframework.aspect.view.command.impl.ShowViewCommand;
import org.romaframework.aspect.view.feature.ViewBaseFeatures;
import org.romaframework.aspect.view.feature.ViewElementFeatures;
import org.romaframework.aspect.view.feature.ViewFieldFeatures;
import org.romaframework.aspect.view.form.ContentForm;
import org.romaframework.aspect.view.form.FormViewer;
import org.romaframework.aspect.view.form.ViewComponent;
import org.romaframework.aspect.view.html.area.HtmlViewFormAreaInstance;
import org.romaframework.aspect.view.html.area.HtmlViewScreenAreaInstance;
import org.romaframework.aspect.view.html.component.HtmlViewAbstractComponent;
import org.romaframework.aspect.view.html.component.HtmlViewConfigurableEntityForm;
import org.romaframework.aspect.view.html.component.HtmlViewContentComponent;
import org.romaframework.aspect.view.html.component.HtmlViewContentForm;
import org.romaframework.aspect.view.html.component.HtmlViewGenericComponent;
import org.romaframework.aspect.view.html.component.HtmlViewInvisibleContentComponent;
import org.romaframework.aspect.view.html.domain.HtmlViewPojoViewPanel;
import org.romaframework.aspect.view.html.form.helper.FormUtils;
import org.romaframework.aspect.view.html.screen.HtmlViewScreen;
import org.romaframework.aspect.view.html.transformer.plain.HtmlViewPopupTransformer;
import org.romaframework.aspect.view.screen.Screen;
import org.romaframework.core.Roma;
import org.romaframework.core.Utility;
import org.romaframework.core.binding.Bindable;
import org.romaframework.core.flow.Controller;
import org.romaframework.core.flow.UserObjectEventListener;
import org.romaframework.core.schema.SchemaAction;
import org.romaframework.core.schema.SchemaClassElement;
import org.romaframework.core.schema.SchemaClassResolver;
import org.romaframework.core.schema.SchemaEvent;
import org.romaframework.core.schema.SchemaFeaturesChangeListener;
import org.romaframework.core.schema.SchemaField;
import org.romaframework.core.schema.SchemaHelper;
import org.romaframework.core.schema.SchemaObject;
import org.romaframework.core.schema.xmlannotations.XmlEventAnnotation;
import org.romaframework.web.session.HttpAbstractSessionAspect;

/**
 * The implementation of the metaframework view aspect interface
 * 
 * @author Giordano Maestro (giordano.maestro--at--assetdata.it)
 * 
 */
public class HtmlViewAspect extends ViewAspectAbstract implements SchemaFeaturesChangeListener, UserObjectEventListener {

	private static Log					log	= LogFactory.getLog(HtmlViewAspect.class);
	private Map<String, String>	typeRenders;

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.romaframework.aspect.view.ViewAspectAbstract#startup()
	 */
	@Override
	public void startup() {
		super.startup();

		Roma.component(SchemaClassResolver.class).addDomainPackage(Utility.getRomaAspectPackage(aspectName()) + ".html");

		Roma.component(SchemaClassResolver.class).addDomainPackage(Utility.getRomaAspectPackage(aspectName()) + ".html.domain");
		Roma.component(SchemaClassResolver.class).addDomainPackage("org.romaframework.aspect.view.html.domain");
		Roma.component(SchemaClassResolver.class).addPackage("org.romaframework.aspect.view.html.domain");

		Roma.schema().createSchemaClass(HtmlViewPojoViewPanel.class, null);
		//

		// Add the listener for the management of the field changing on the form
		Controller.getInstance().registerListener(UserObjectEventListener.class, this);
		// Add the listener for the management of the schema feature changes
		Controller.getInstance().registerListener(SchemaFeaturesChangeListener.class, this);

	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.romaframework.aspect.view.ViewAspect#createForm(org.romaframework.core.schema.SchemaObject,
	 * org.romaframework.core.schema.SchemaField, org.romaframework.aspect.view.form.ViewComponent)
	 */
	public ContentForm createForm(final SchemaObject schemaClass, final SchemaField schemaField, final ViewComponent parent) {
		return new HtmlViewConfigurableEntityForm(null, schemaClass, null, null, null, null, null);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.romaframework.aspect.view.ViewAspect#releaseForm(org.romaframework.aspect.view.form.ContentForm)
	 */
	public void releaseForm(final ContentForm formInstance) {
		if (formInstance == null) {
			return;
		}

		try {
			// REMOVE THE ASSOCIATION FROM THE USER OBJECT AND THE FORM
			((HtmlViewContentForm)formInstance).clearComponents();
			removeObjectFormAssociation(formInstance.getContent(), null);
		} catch (final Exception e) {
			log.error("[FormPool.releaseForm] Error", e);
		}
	}

	@Override
	public void show(Object content, String position, Screen screen, SessionInfo session, SchemaObject schema) throws ViewException {
		super.show(content, position, screen, session, schema);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.romaframework.aspect.view.ViewAspect#showForm(org.romaframework.aspect.view.form.ContentForm, java.lang.String,
	 * org.romaframework.aspect.view.screen.Screen)
	 */
	public String showForm(final ContentForm form, final String where, final Screen desktop) {

		final String renderFeature = (String) form.getSchemaObject().getFeature(ViewAspect.ASPECT_NAME, ViewBaseFeatures.RENDER);
		final String renderLayout = (String) form.getSchemaObject().getFeature(ViewAspect.ASPECT_NAME, ViewBaseFeatures.LAYOUT);
		if (ViewConstants.LAYOUT_POPUP.equals(renderFeature) || ViewConstants.LAYOUT_POPUP.equals(renderLayout)
				|| ViewConstants.RENDER_POPUP.equals(renderFeature)) {
			((HtmlViewScreen) desktop).addPopup(form, renderLayout);
			// ViewHelper.invokeOnShow(form.getContent());
			return renderLayout;
		} else if (where != null && (where.startsWith(HtmlViewScreen.SCREEN_POPUP) || where.startsWith(HtmlViewScreen.POPUP))) {
			((HtmlViewScreen) desktop).addPopup(form, where);
			// ViewHelper.invokeOnShow(form.getContent());
			return where;
		}

		if (where.startsWith("form:")) {
			HtmlViewConfigurableEntityForm parentComponent = (HtmlViewConfigurableEntityForm) desktop.getComponentInArea("body");

			AreaComponent area = parentComponent.searchAreaForRendering(where, null);

			((HtmlViewFormAreaInstance) area).addComponent((HtmlViewGenericComponent) form);
			form.setScreenArea("body");
			parentComponent.setDirty(true);
			((HtmlViewFormAreaInstance) area).setDirty(true);
			return area.getName();

		} else {
			HtmlViewScreenAreaInstance area = (HtmlViewScreenAreaInstance) desktop.getArea(where);
			if (area == null && Controller.getInstance().getContext().getActiveArea() == null) {
				area = (HtmlViewScreenAreaInstance) ((HtmlViewScreen) desktop).getDefaultArea();
				Controller.getInstance().getContext().setActiveArea(area.getName());
			}
			area.bindForm((HtmlViewContentForm) form);
			form.setScreenArea(where);
			return area.getName();
		}
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.romaframework.aspect.view.ViewAspectAbstract#onSessionDestroying(org.romaframework.aspect.session.SessionInfo)
	 */
	@Override
	public void onSessionDestroying(final SessionInfo iSession) {
		super.onSessionDestroying(iSession);
		HtmlViewAspectHelper.destroyHtmlViewSession();

	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.romaframework.core.schema.SchemaFeaturesChangeListener#signalChangeAction(java.lang.Object, java.lang.String,
	 * java.lang.String, java.lang.String, java.lang.Object, java.lang.Object)
	 */
	public void signalChangeAction(Object userObject, final String aspectName, String actionName, final String featureName,
			final Object oldValue, final Object featureValue) {

		userObject = SchemaHelper.getFieldObject(userObject, actionName);
		final HtmlViewContentForm form = (HtmlViewContentForm) Roma.aspect(ViewAspect.class).getFormByObject(userObject);

		// There id no form for the component
		if (form == null) {
			log.warn("The form for the object " + userObject + " doesn't exist");
			return;
		}

		if (oldValue == null && featureValue == null) {
			return;
		}

		if (oldValue != null && oldValue.equals(featureValue) || featureValue != null && featureValue.equals(oldValue)) {
			return;
		}

		if (featureName.equals(ViewElementFeatures.VISIBLE) || featureName.equals(ViewBaseFeatures.RENDER)) {
			int pos = actionName.lastIndexOf(Utility.PACKAGE_SEPARATOR_STRING);
			if (pos != -1) {
				actionName = actionName.substring(pos + 1);
			}
			SchemaAction action = form.getSchemaObject().getAction(actionName);
			if (action != null) {
				if (featureValue instanceof Boolean && (Boolean) featureValue) {
					FormUtils.createActionComponent(action, form);
				} else {
					form.removeFieldComponent(actionName);
					form.setDirty(true);
				}

			}
		}

		if (featureName.equals(ViewElementFeatures.ENABLED)) {
			((HtmlViewConfigurableEntityForm) form).setDirty(true);
		}
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.romaframework.core.schema.SchemaFeaturesChangeListener#signalChangeClass(java.lang.Object, java.lang.String,
	 * java.lang.String, java.lang.Object, java.lang.Object)
	 */
	public void signalChangeClass(final Object userObject, final String aspectName, final String featureName, final Object oldValue,
			final Object featureValue) {
		final HtmlViewContentForm form = (HtmlViewContentForm) Roma.aspect(ViewAspect.class).getFormByObject(userObject);

		// There id no form for the component
		if (form == null) {
			log.warn("The form for the object " + userObject + " doesn't exist");
			return;
		}

		if (oldValue == null && featureValue == null) {
			return;
		}

		if (oldValue != null && oldValue.equals(featureValue) || featureValue != null && featureValue.equals(oldValue)) {
			return;
		}

		if (featureName.equals(ViewElementFeatures.VISIBLE) || featureName.equals(ViewBaseFeatures.RENDER)) {
			form.placeComponents();
		}

		if (featureName.equals(ViewElementFeatures.ENABLED) || featureName.equals(ViewBaseFeatures.STYLE)) {
			((HtmlViewConfigurableEntityForm) form).setDirty(true);
		}

	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.romaframework.core.schema.SchemaFeaturesChangeListener#signalChangeField(java.lang.Object, java.lang.String,
	 * java.lang.String, java.lang.String, java.lang.Object, java.lang.Object)
	 */
	public void signalChangeField(Object userObject, final String aspectName, String fieldName, final String featureName,
			final Object oldValue, final Object featureValue) {

		userObject = SchemaHelper.getFieldObject(userObject, fieldName);
		final HtmlViewContentForm form = (HtmlViewContentForm) Roma.aspect(ViewAspect.class).getFormByObject(userObject);

		// There id no form for the component
		if (form == null) {
			log.warn("The form for the object " + userObject + " doesn't exist");
			return;
		}

		if (oldValue == null && featureValue == null) {
			return;
		}

		if (featureName.equals(ViewFieldFeatures.DEPENDS)) {
			changeFieldDepends(fieldName, form, featureValue);
			return;
		} else if (featureName.equals(ViewFieldFeatures.DEPENDS_ON)) {
			changeFieldDependsOn(fieldName, form, oldValue, featureValue);
			return;
		}

		if (oldValue != null && oldValue.equals(featureValue) || featureValue != null && featureValue.equals(oldValue)) {
			return;
		}

		if (featureName.equals(ViewElementFeatures.VISIBLE) || featureName.equals(ViewBaseFeatures.RENDER)) {
			int pos = fieldName.lastIndexOf(Utility.PACKAGE_SEPARATOR_STRING);
			if (pos != -1) {
				fieldName = fieldName.substring(pos + 1);
			}
			SchemaField field = form.getSchemaObject().getField(fieldName);
			FormUtils.createFieldComponent(field, form);
		}

		if (featureName.equals(ViewElementFeatures.ENABLED)) {
			form.setDirty(true);
		}

	}

	// Methods from interface UserObjectEventListener //

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.romaframework.core.flow.UserObjectEventListener#onAfterActionExecution(java.lang.Object,
	 * org.romaframework.core.schema.SchemaElement, java.lang.Object)
	 */
	public void onAfterActionExecution(final Object content, final SchemaClassElement action, final Object returnedValue) {
		// TODO Auto-generated method stub
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.romaframework.core.flow.UserObjectEventListener#onAfterFieldRead(java.lang.Object,
	 * org.romaframework.core.schema.SchemaField, java.lang.Object)
	 */
	public Object onAfterFieldRead(final Object content, final SchemaField field, final Object currentValue) {
		return currentValue;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.romaframework.core.flow.UserObjectEventListener#onAfterFieldWrite(java.lang.Object,
	 * org.romaframework.core.schema.SchemaField, java.lang.Object)
	 */
	@SuppressWarnings("unchecked")
	public Object onAfterFieldWrite(final Object content, final SchemaField field, final Object currentValue) {
		Set<String> dependencies = (Set<String>) field.getFeature(ASPECT_NAME, ViewFieldFeatures.DEPENDS);
		if (dependencies != null) {
			Roma.fieldChanged(content, dependencies.toArray(new String[dependencies.size()]));
		}
		return currentValue;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.romaframework.core.flow.UserObjectEventListener#onBeforeActionExecution(java.lang.Object,
	 * org.romaframework.core.schema.SchemaElement)
	 */
	public boolean onBeforeActionExecution(final Object content, final SchemaClassElement action) {
		return true;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.romaframework.core.flow.UserObjectEventListener#onBeforeFieldRead(java.lang.Object,
	 * org.romaframework.core.schema.SchemaField, java.lang.Object)
	 */
	public Object onBeforeFieldRead(final Object content, final SchemaField field, final Object currentValue) {
		return IGNORED;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.romaframework.core.flow.UserObjectEventListener#onBeforeFieldWrite(java.lang.Object,
	 * org.romaframework.core.schema.SchemaField, java.lang.Object)
	 */
	public Object onBeforeFieldWrite(final Object content, final SchemaField field, final Object currentValue) {
		return currentValue;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.romaframework.core.flow.UserObjectEventListener#onFieldRefresh(org.romaframework.aspect.session.SessionInfo,
	 * java.lang.Object, org.romaframework.core.schema.SchemaField)
	 */
	public void onFieldRefresh(final SessionInfo iSession, final Object iContent, final SchemaField iField) {
		final HtmlViewContentForm form = (HtmlViewContentForm) Roma.aspect(ViewAspect.class).getFormByObject(iSession, iContent);

		if (form == null) {
			// FORM NOT YET CREATED: JUST RETURN
			return;
		}

		final HtmlViewContentComponent componentToUpdate = form.getFieldComponent(iField.getName());

		if (componentToUpdate != null) {
			// TODO TEST IT!!!
			if (Boolean.TRUE.equals(iField.getFeature(CoreAspect.ASPECT_NAME, CoreFieldFeatures.USE_RUNTIME_TYPE))) {
				FormUtils.createFieldComponent(iField, form);
				return;
			}

			Object displayWith = iField.getFeature(ViewAspect.ASPECT_NAME, ViewFieldFeatures.DISPLAY_WITH);
			if (displayWith != null && !Bindable.class.equals(displayWith)) {
				Object content = componentToUpdate.getContent();
				if (content != null) {
					((Bindable) content).setSource(iContent, iField.getName());
				}
				return;
			}
			// manage appear/disappear of null ObjectEmbedded
			if (ViewConstants.RENDER_OBJECTEMBEDDED.equals(iField.getFeature(ViewAspect.ASPECT_NAME, ViewFieldFeatures.RENDER))) {
				Object content = componentToUpdate.getContent();

				if (componentToUpdate instanceof HtmlViewInvisibleContentComponent || content == null) {
					form.removeFieldComponent(iField.getName());
					FormUtils.createFieldComponent(iField, form);
					return;
				}
			}

			final Object value = SchemaHelper.getFieldValue(form.getSchemaObject(), iField.getName(), iContent);
			if (componentToUpdate instanceof ContentForm) {
				ViewHelper.invokeOnShow(value);
			}
			((HtmlViewAbstractComponent) componentToUpdate).setDirty(true);

			componentToUpdate.setContent(value);
			// FIX FOR FIELD REFRESH OF EXPANDED COMPONENTS
		} else if (ViewConstants.LAYOUT_EXPAND.equals(iField.getFeature(ViewAspect.ASPECT_NAME, ViewFieldFeatures.LAYOUT))) {
			boolean first = true;
			for (String fieldName : iField.getClassInfo().getFields().keySet()) {
				SchemaField iSubField = iField.getClassInfo().getFields().get(fieldName);
				if (((Boolean) iSubField.getFeature(ViewAspect.ASPECT_NAME, ViewFieldFeatures.VISIBLE))) {
					HtmlViewContentComponent expandedComponentToUpdate = form.getFieldComponent(fieldName);
					if (first) {
						expandedComponentToUpdate.getContainerComponent().setContent(
								SchemaHelper.getFieldValue(form.getSchemaObject(), iField.getName(), iContent));
						first = false;
					}
					if (expandedComponentToUpdate != null) {
						// TODO TEST IT!!!
						if (Boolean.TRUE.equals(iSubField.getFeature(CoreAspect.ASPECT_NAME, CoreFieldFeatures.USE_RUNTIME_TYPE))) {
							FormUtils.createFieldComponent(iSubField, form);
							return;
						}

						// manage appear/disappear of null ObjectEmbedded
						if (ViewConstants.RENDER_OBJECTEMBEDDED.equals(iSubField.getFeature(ViewAspect.ASPECT_NAME, ViewFieldFeatures.RENDER))) {
							Object content = expandedComponentToUpdate.getContent();
							if (expandedComponentToUpdate instanceof HtmlViewInvisibleContentComponent || content == null) {
								form.removeFieldComponent(fieldName);
								FormUtils.createFieldComponent(iSubField, form);
								return;
							}
						}

						final Object value = SchemaHelper.getFieldValue(form.getSchemaObject(), iField.getName() + "." + fieldName, iContent);
						if (componentToUpdate instanceof ContentForm) {
							ViewHelper.invokeOnShow(value);
						}
						((HtmlViewAbstractComponent) expandedComponentToUpdate).setDirty(true);

						expandedComponentToUpdate.setContent(value);
					}
				}
			}
		}

	}

	public void cleanDirtyComponents() {
		cleanDirtyComponents(getScreen());
	}

	public void cleanDirtyComponents(Screen iScreen) {
		HtmlViewScreen screen = (HtmlViewScreen) iScreen;
		if (screen != null) {
			HtmlViewScreenAreaInstance area = screen.getRootArea();
			cleanDirtyArea(area);
		}
	}

	private void changeFieldDepends(String fieldName, final HtmlViewContentForm form, final Object featureValue) {
		SchemaField field = form.getSchemaObject().getField(fieldName);
		Set<String> dependencies = new HashSet<String>();
		String[] depends = (String[]) featureValue;
		for (String dependency : depends) {
			dependencies.add(dependency);
		}
		field.setFeature(ASPECT_NAME, ViewFieldFeatures.DEPENDS, dependencies);
		updateFieldDependencies(form.getSchemaObject().getSchemaClass());
	}

	@SuppressWarnings("unchecked")
	private void changeFieldDependsOn(String fieldName, final HtmlViewContentForm form, final Object oldValue,
			final Object featureValue) {
		SchemaField field = form.getSchemaObject().getField(fieldName);
		if (oldValue != null) {
			String[] currentDependsOn = (String[]) oldValue;
			for (String dependsFieldName : currentDependsOn) {
				SchemaField dependsField = form.getSchemaObject().getField(dependsFieldName);
				((Set<String>) dependsField.getFeature(ASPECT_NAME, ViewFieldFeatures.DEPENDS)).remove(fieldName);
			}
		}
		List<String> dependsOn = Arrays.asList((String[]) featureValue);
		for (String dependsFieldName : dependsOn) {
			SchemaField dependsField = form.getSchemaObject().getField(dependsFieldName);
			((Set<String>) dependsField.getFeature(ASPECT_NAME, ViewFieldFeatures.DEPENDS)).add(fieldName);
		}
		field.setFeature(ASPECT_NAME, ViewFieldFeatures.DEPENDS_ON, dependsOn);
	}

	private void cleanDirtyArea(HtmlViewScreenAreaInstance area) {
		area.setDirty(false);
		if (area.getChildren() != null)
			for (Object child : area.getChildren()) {
				HtmlViewScreenAreaInstance childArea = (HtmlViewScreenAreaInstance) child;
				cleanDirtyArea(childArea);
			}
		if (area.getComponentInArea() != null)
			cleanDirtyForm((HtmlViewConfigurableEntityForm) area.getComponentInArea());
	}

	private void cleanDirtyForm(HtmlViewConfigurableEntityForm component) {
		component.setDirty(false);
		if (component.getChildren() != null)
			for (HtmlViewGenericComponent child : component.getChildren()) {
				if (child instanceof HtmlViewAbstractComponent)
					cleanDirtyComponent((HtmlViewAbstractComponent) child);
				else if (child instanceof HtmlViewConfigurableEntityForm)
					cleanDirtyForm((HtmlViewConfigurableEntityForm) child);
			}
	}

	private void cleanDirtyComponent(HtmlViewAbstractComponent component) {
		component.setDirty(false);
		if (component.getChildren() != null)
			for (HtmlViewGenericComponent child : component.getChildren()) {
				if (child instanceof HtmlViewAbstractComponent)
					cleanDirtyComponent((HtmlViewAbstractComponent) child);
				else if (child instanceof HtmlViewConfigurableEntityForm)
					cleanDirtyForm((HtmlViewConfigurableEntityForm) child);
			}
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.romaframework.aspect.view.ViewAspectAbstract#close(java.lang.Object)
	 */
	@Override
	public void close(final Object iUserObject) {
		final Screen screen = getScreen();

		screen.close(iUserObject);
		// TODO REVIEW THIS LOGIC!!!
		HtmlViewPopupTransformer.removeCss();
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.romaframework.aspect.view.ViewAspect#pushCommand(org.romaframework.aspect.view.command.ViewCommand)
	 */
	public void pushCommand(final ViewCommand command) {
		if (command instanceof RedirectViewCommand) {
			pushRedirectView((RedirectViewCommand) command);
		} else if (command instanceof ShowViewCommand) {
			ShowViewCommand cmd = (ShowViewCommand) command;
			pushShowView(cmd);
		} else if (command instanceof OpenWindowViewCommand) {
			OpenWindowViewCommand cmd = (OpenWindowViewCommand) command;
			pushOpenWindow(cmd);
		} else if (command instanceof RefreshViewCommand) {
			RefreshViewCommand cmd = (RefreshViewCommand) command;
			pushRefreshView(cmd);
		} else if (command instanceof DownloadStreamViewCommand) {
			DownloadStreamViewCommand cmd = (DownloadStreamViewCommand) command;
			pushDownloadStream(cmd);
		} else if (command instanceof DownloadReaderViewCommand) {
			DownloadReaderViewCommand cmd = (DownloadReaderViewCommand) command;
			pushDownloadReader(cmd);
		} else if (command instanceof ReportingDownloadViewCommand) {
			ReportingDownloadViewCommand cmd = (ReportingDownloadViewCommand) command;
			pushDownloadReporting(cmd);
		}

	}

	private void pushRefreshView(RefreshViewCommand cmd) {
		// TODO test it!!!!
		Roma.aspect(ViewAspect.class).releaseForm((ContentForm) cmd.getForm());
	}

	private void pushShowView(ShowViewCommand cmd) {
		Roma.aspect(FlowAspect.class).forward(cmd.getForm(), cmd.getWhere(), FormViewer.getInstance().getScreen(cmd.getSession()));
	}

	private void pushDownloadReader(DownloadReaderViewCommand command) {

		Roma.component(SessionAspect.class).setProperty(DownloadReaderViewCommand.class.getSimpleName(), command);
	}

	private void pushDownloadStream(DownloadStreamViewCommand command) {
		Roma.component(SessionAspect.class).setProperty(DownloadStreamViewCommand.class.getSimpleName(), command);
	}

	private void pushOpenWindow(OpenWindowViewCommand command) {
		Roma.component(SessionAspect.class).setProperty(OpenWindowViewCommand.class.getSimpleName(), command);
	}

	private void pushRedirectView(final RedirectViewCommand command) {
		Roma.component(SessionAspect.class).setProperty(RedirectViewCommand.class.getSimpleName(), command);
	}

	private void pushDownloadReporting(final ReportingDownloadViewCommand command) {
		Roma.component(SessionAspect.class).setProperty(ReportingDownloadViewCommand.class.getSimpleName(), command);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.romaframework.core.flow.UserObjectEventListener#getPriority()
	 */
	public int getPriority() {
		return 0;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.romaframework.core.flow.UserObjectEventListener#onException(java.lang.Object,
	 * org.romaframework.core.schema.SchemaElement, java.lang.Throwable)
	 */
	public Object onException(final Object content, final SchemaClassElement element, final Throwable throwed) {
		return null;
	}

	public void configEvent(SchemaEvent event, Annotation eventAnnotation, Annotation genericAnnotation, XmlEventAnnotation node) {
	}

	public Object getUnderlyingComponent() {
		return null;
	}

	public String getContextPath() {
		return ((HttpServletRequest) Roma.context().component(HttpAbstractSessionAspect.CONTEXT_REQUEST_PAR)).getContextPath();
	}

	public Map<String, String> getTypeRenders() {
		return typeRenders;
	}

	public void setTypeRenders(Map<String, String> typeRenders) {
		this.typeRenders = typeRenders;
	}

	public String getRender(String typeName) {
		if (typeRenders != null) {
			typeRenders.get(typeName);
		}
		return null;
	}

}
