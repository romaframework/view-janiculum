/*
 * Copyright 2008 Luigi Dell'Aquila (luigi.dellaquila@assetdata.it)
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
package org.romaframework.aspect.session.html.helper;

import java.util.Locale;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.romaframework.core.flow.ObjectContext;
import org.romaframework.frontend.RomaFrontend;
import org.romaframework.web.session.HttpAbstractSessionAspect;

public final class HtmlSessionHelper {

	protected static final Log	log											= LogFactory.getLog(HtmlSessionHelper.class);

	private static final String	CURRENT_LOCALE					= "HttpSessionAspect_current_locale";
	private static final String	ACTIVE_SESSION					= "HttpSessionAspect_active_session";

	private static final String	CURRENT_SESSION_STARTED	= "HttpSessionAspect_current_session_started";

	private HtmlSessionHelper() {
	}

	private static HtmlSessionHelper	instance	= new HtmlSessionHelper();

	public static HtmlSessionHelper getInstance() {
		return instance;
	}

	public void setCurrentLocale(final Locale locale) {
		RomaFrontend.session().setProperty(CURRENT_LOCALE, locale);
	}

	public Locale getCurrentLocale() {
		try {
			Locale result = (Locale) RomaFrontend.session().getProperty(CURRENT_LOCALE);
			if (result == null) {
				HttpServletRequest request = ObjectContext.getInstance().getContextComponent(HttpAbstractSessionAspect.CONTEXT_REQUEST_PAR);
				result = request.getLocale();
			}
			return result;
		} catch (Exception e) {
			return Locale.getDefault();
		}
	}

	public HttpSession getActiveSession() {
		final HttpSession httpSession = ObjectContext.getInstance().getContextComponent(ACTIVE_SESSION);
		return httpSession;
	}

	public void setActiveSession(final HttpSession session) {
		ObjectContext.getInstance().setContextComponent(ACTIVE_SESSION, session);
	}

	public boolean isStarted(final HttpSession session) {
		if (session == null) {
			return false;
		}
		final Object started = session.getAttribute(CURRENT_SESSION_STARTED);
		return Boolean.TRUE.equals(started);
	}

	public void setStarted(final HttpSession session) {
		if (session == null) {
			return;
		}
		session.setAttribute(CURRENT_SESSION_STARTED, Boolean.TRUE);
	}
}
