package org.romaframework.aspect.view.html;

import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.romaframework.aspect.session.SessionAspect;
import org.romaframework.aspect.session.html.helper.HtmlSessionHelper;
import org.romaframework.aspect.view.ViewAspect;
import org.romaframework.aspect.view.form.FormViewer;
import org.romaframework.aspect.view.html.screen.HtmlViewScreenContainer;
import org.romaframework.aspect.view.screen.Screen;
import org.romaframework.aspect.view.screen.config.ScreenManager;
import org.romaframework.core.Roma;
import org.romaframework.core.config.ApplicationConfiguration;
import org.romaframework.core.flow.Controller;
import org.romaframework.web.service.rest.RestServiceHelper;

public abstract class RomaServlet extends HttpServlet {

	protected static Log	log	= LogFactory.getLog(RomaServlet.class);

	protected void startUserSession(final HttpServletRequest request, final HttpServletResponse response) {
		final HttpSession httpSession = request.getSession(true);

		final SessionAspect sessionAspect = Roma.session();

		sessionAspect.addSession(httpSession);

		initSessionAspect(request, response);

		// During session creation it create a controller context and invoke the
		// application starter
		FormViewer.getInstance().setScreenContainer(new HtmlViewScreenContainer());
		Screen screen = Roma.component(ScreenManager.class).getScreen("main-screen.xml");
		Roma.aspect(ViewAspect.class).setScreen(screen);

		final ApplicationConfiguration config = Roma.component(ApplicationConfiguration.class);
		Controller.getInstance().createContext();

		if (RestServiceHelper.existsServiceToInvoke(request)) {
			RestServiceHelper.invokeRestService(request, response);
			RestServiceHelper.clearSession(request);
		} else {
			config.createUserSession();
		}
		FormViewer.getInstance().sync();
	}

	protected void initSessionAspect(final HttpServletRequest request, final HttpServletResponse response) {
		initSession(request, response);
	}

	public static void initSession(final HttpServletRequest request, final HttpServletResponse response) {
		HtmlSessionHelper.getInstance().setActiveSession(request.getSession(true));
	}

	protected void deinitSessionAspect() {
		deinitSession();
	}

	public static void deinitSession() {
		final HtmlViewSession session = HtmlViewAspectHelper.getHtmlViewSession();

		if (session != null) {
			HtmlSessionHelper.getInstance().setActiveSession(null);
		}
	}
}
