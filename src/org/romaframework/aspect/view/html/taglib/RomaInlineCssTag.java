package org.romaframework.aspect.view.html.taglib;

import javax.servlet.jsp.tagext.TagSupport;

import static org.romaframework.aspect.view.html.HtmlServlet.ROMA_CSS;

public class RomaInlineCssTag extends TagSupport {

	public static final String ROMA_INLINE_CSS_ID = "romaInlineCss";
	@Override
	public int doStartTag() {
		try {
			pageContext.getOut().flush();
			pageContext.getOut().print("<style id=\""+ROMA_INLINE_CSS_ID+"\" type=\"text/css\">"+ROMA_CSS+" </style>\n");
			pageContext.getOut().flush();
		} catch (final Exception e) {
			e.printStackTrace();// TODO handle exception
		}

		return SKIP_BODY;
	}
}
