/*
 * Copyright 2009 Luigi Dell'Aquila (luigi.dellaquila--at--assetdata.it)
 * 
 * Licensed under the Apache License, Version 2.0 (the "License"); you may not
 * use this file except in compliance with the License. You may obtain a copy of
 * the License at
 * 
 * http://www.apache.org/licenses/LICENSE-2.0
 * 
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS, WITHOUT
 * WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied. See the
 * License for the specific language governing permissions and limitations under
 * the License.
 */
package org.romaframework.aspect.view.html;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.json.CssJSONObject;
import org.json.JSONException;
import org.json.JSONObject;
import org.json.JsJSONObject;
import org.romaframework.aspect.validation.ValidationException;
import org.romaframework.aspect.view.ViewAspect;
import org.romaframework.aspect.view.command.impl.DownloadReaderViewCommand;
import org.romaframework.aspect.view.command.impl.DownloadStreamViewCommand;
import org.romaframework.aspect.view.command.impl.OpenWindowViewCommand;
import org.romaframework.aspect.view.command.impl.RedirectViewCommand;
import org.romaframework.aspect.view.command.impl.ReportingDownloadViewCommand;
import org.romaframework.aspect.view.html.area.HtmlViewRenderable;
import org.romaframework.aspect.view.html.screen.HtmlViewScreen;
import org.romaframework.core.Roma;
import org.romaframework.core.binding.BindingException;
import org.romaframework.core.exception.ExceptionHelper;
import org.romaframework.core.exception.UserException;
import org.romaframework.frontend.domain.message.ErrorMessageTextDetail;

public class AjaxServlet extends HtmlServlet {
	@Override
	public void service(final HttpServletRequest request, final HttpServletResponse response) throws ServletException, IOException {
		try {
			HttpSession httpSession = request.getSession(true);

			if (!isStarted(httpSession)) {
				try {
					log.info("trying to navigate on expired session!!!");
					// toDeInit = false;
					sendStopAjaxResponse(response, "");
					httpSession.invalidate();

				} catch (Exception e) {
					// log.error("unable to redirect ", e);
				}
				return;
			}

			HtmlViewScreen screen = (HtmlViewScreen) Roma.aspect(ViewAspect.class).getScreen();
			String oldScreenName = screen.getName();

			final String pageId = (String) request.getSession().getAttribute(PAGE_ID_PARAM);
			request.setAttribute(PAGE_ID_PARAM, pageId);

			HtmlViewAspectHelper.getCssBuffer().open();

			removePreviousPushCommands(request);

			// Add the request and the response to the ThreadLocal

			final RequestParser requestParser = Roma.component(RequestParser.class);

			boolean fieldsBound = false;
			try {

				response.setContentType("text/html");
				response.setCharacterEncoding("UTF-8");
				// response.setHeader("Cache-Control", "no-store, no-cache, must-revalidate"); //HTTP 1.1
				response.addHeader("Cache-Control", "must-revalidate");
				response.addHeader("Cache-Control", "no-cache");
				response.addHeader("Cache-Control", "no-store");
				response.setHeader("Pragma", "no-cache"); // HTTP 1.0
				response.setDateHeader("Expires", 0); // prevents caching at the proxy server
				fieldsBound = requestParser.parseRequest(request);
			} catch (final ValidationException e) {
				ErrorMessageTextDetail toShow = new ErrorMessageTextDetail("application.error", "Application Error", null, e.getMessage(), e);
				toShow.setDetail(ExceptionHelper.toString(e));
				log.info(e.getMessage(), e);
				Roma.flow().popup(toShow);
			} catch (final BindingException e) {
				ErrorMessageTextDetail toShow = new ErrorMessageTextDetail("application.error", "Application Error", null, e.getMessage(), e);
				toShow.setDetail(ExceptionHelper.toString(e));
				log.warn(e.getMessage(), e);
				Roma.flow().popup(toShow);
			} catch (UserException e) {
				ErrorMessageTextDetail toShow = new ErrorMessageTextDetail("application.error", "Application Error", null, e.getMessage(), e);
				toShow.setDetail(ExceptionHelper.toString(e));
				log.debug(e.getMessage(), e);
				Roma.flow().popup(toShow);
			} catch (final Throwable e) {
				ErrorMessageTextDetail toShow = new ErrorMessageTextDetail("application.error", "Application Error", e);
				toShow.setDetail(ExceptionHelper.toString(e));
				log.warn(e.getMessage(), e);
				Roma.flow().popup(toShow);
				// throw new ServletException(e);
			}

			Boolean redirected = (Boolean) request.getAttribute(HtmlViewAspectHelper.REDIRECTED);
			if (redirected != null && redirected) {
				request.setAttribute(HtmlViewAspectHelper.REDIRECTED, Boolean.FALSE);
				return;
			}

			screen = (HtmlViewScreen) Roma.view().getScreen();

			try {

				if (mustReload(screen, oldScreenName)) {
					sendReloadAjaxResponse(response, pageId);
				} else {
					JSONObject obj = new JsJSONObject();
					JSONObject changes = new CssJSONObject();
					obj.put("bindingExecuted", fieldsBound);
					obj.put("status", "ok");
					obj.put("pageId", pageId);

					for (HtmlViewRenderable render : DirtyHelper.getInstance().getChanges()) {
						changes.put(render.getHtmlId(), new ComponentWritable(render));
						if (log.isDebugEnabled())
							log.debug(render.getHtmlId());
					}
					obj.put("changes", changes);

					addPushCommands(obj, request);

					obj.write(response.getWriter());

				}

				DirtyHelper.getInstance().clear();

			} catch (JSONException jsonx) {
				jsonx.printStackTrace();
			}
		} finally {
			HtmlViewAspectHelper.removeCssBuffer();
			HtmlViewAspectHelper.removeJsBuffer();
		}
	}

	private void sendReloadAjaxResponse(final HttpServletResponse response, final String pageId) throws JSONException, IOException {
		JSONObject obj = new JSONObject();
		obj.put("status", "reload");
		obj.put("pageId", pageId);
		response.getWriter().write(obj.toString());
	}

	private void sendStopAjaxResponse(final HttpServletResponse response, final String pageId) throws JSONException, IOException {
		JSONObject obj = new JSONObject();
		obj.put("status", "stop");
		obj.put("pageId", pageId);
		response.getWriter().write(obj.toString());
	}

	private void addPushCommands(JSONObject obj, final HttpServletRequest request) throws JSONException {
		DownloadStreamViewCommand command = (DownloadStreamViewCommand) request.getSession().getAttribute(DownloadStreamViewCommand.class.getSimpleName());
		if (command != null) {
			if (command.getFileName() != null) {
				obj.put("pushDownloadStream", command.getFileName());
			} else {
				obj.put("pushDownloadStream", "download");
			}
		}

		DownloadReaderViewCommand pushReaderCommand = (DownloadReaderViewCommand) request.getSession().getAttribute(DownloadReaderViewCommand.class.getSimpleName());
		if (pushReaderCommand != null) {
			if (pushReaderCommand.getFileName() != null) {
				obj.put("pushDownloadReader", pushReaderCommand.getFileName());
			} else {
				obj.put("pushDownloadReader", "download");
			}
		}

		ReportingDownloadViewCommand pushDownloadReport = (ReportingDownloadViewCommand) request.getSession().getAttribute(ReportingDownloadViewCommand.class.getSimpleName());
		if (pushDownloadReport != null) {
			if (pushDownloadReport.getFileName() != null) {
				obj.put("pushDownloadReport", pushDownloadReport.getFileName());
			} else {
				obj.put("pushDownloadReport", "download");
			}
		}

		OpenWindowViewCommand pushOpenWindowCommand = (OpenWindowViewCommand) request.getSession().getAttribute(OpenWindowViewCommand.class.getSimpleName());
		if (pushOpenWindowCommand != null) {
			JSONObject pushOpenJSONObject = new JSONObject();
			pushOpenJSONObject.put("location", pushOpenWindowCommand.getLocation());
			pushOpenJSONObject.put("name", pushOpenWindowCommand.getName());
			pushOpenJSONObject.put("options", pushOpenWindowCommand.getOptions());
			obj.put("pushOpenWindow", pushOpenJSONObject);
		}

		RedirectViewCommand pushRedirectViewCommand = (RedirectViewCommand) request.getSession().getAttribute(RedirectViewCommand.class.getSimpleName());
		if (pushRedirectViewCommand != null) {
			JSONObject pushOpenJSONObject = new JSONObject();
			pushOpenJSONObject.put("location", pushRedirectViewCommand.getLocation());
			pushOpenJSONObject.put("name", pushRedirectViewCommand.getName());
			pushOpenJSONObject.put("options", pushRedirectViewCommand.getOptions());
			obj.put("pushRedirectView", pushOpenJSONObject);
		}
	}

	private boolean mustReload(HtmlViewScreen currentScreen, String oldScreenName) {
		if (!currentScreen.getName().equals(oldScreenName)) {
			return true;
		}
		return false;
	}

}
