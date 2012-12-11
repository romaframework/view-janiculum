package org.romaframework.aspect.view.html.http;

import java.io.IOException;
import java.io.PrintWriter;
import java.io.Writer;

import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpServletResponseWrapper;

import org.romaframework.core.flow.ObjectContext;
import org.romaframework.web.session.HttpAbstractSessionAspect;

public class MockHttpServletResponse extends HttpServletResponseWrapper {
	private PrintWriter	writer;

	public MockHttpServletResponse(Writer writer) {
		super((HttpServletResponse) ObjectContext.getInstance().getContextComponent(HttpAbstractSessionAspect.CONTEXT_RESPONSE_PAR));
		this.writer = new PrintWriter(writer);
	}

	public PrintWriter getWriter() throws IOException {
		return writer;
	}
}
