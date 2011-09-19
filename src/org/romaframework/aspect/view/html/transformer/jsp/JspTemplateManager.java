/*
 * Copyright 2011 Luigi Dell'Aquila (luigi.dellaquila--at--assetdata.it)
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
package org.romaframework.aspect.view.html.transformer.jsp;

import java.io.IOException;
import java.io.Writer;
import java.util.Map;

import javax.servlet.ServletException;
import javax.servlet.ServletRequest;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.romaframework.aspect.view.html.HtmlViewAspectHelper;
import org.romaframework.aspect.view.html.constants.RequestConstants;
import org.romaframework.aspect.view.html.template.ViewTemplateManager;

public class JspTemplateManager implements ViewTemplateManager{

	private String		templatesPath		= "WEB-INF/transformers/jsp/";
	protected Log			log							= LogFactory.getLog(getClass());

	protected boolean	cacheTemplates	= true;

	public void execute(String templateName, Map<String, Object> ctx, Writer writer) {
		ServletRequest request = HtmlViewAspectHelper.getServletRequest();
		final String classJsp = getTemplatesPath() + templateName;
		final Object previousContext = request.getAttribute(RequestConstants.CURRENT_CONTEXT_IN_TRANSFORMER);
		request.setAttribute(RequestConstants.CURRENT_CONTEXT_IN_TRANSFORMER, ctx);
		try {
			HtmlViewAspectHelper.getHtmlFromJSP(request, classJsp, writer);
		} catch (ServletException e) {
			log.error("error in jsp transformer", e);
		} catch (IOException e) {
			log.error("maybe wrong render defined", e);
		}
		request.setAttribute(RequestConstants.CURRENT_CONTEXT_IN_TRANSFORMER, previousContext);
	}

	public String getTemplatesPath() {
		return templatesPath;
	}

	public void setTemplatesPath(String templatesPath) {
		this.templatesPath = templatesPath;
	}

	public boolean isCacheTemplates() {
		return false;
	}

	public void setCacheTemplates(boolean cacheTemplates) {
	}

}
