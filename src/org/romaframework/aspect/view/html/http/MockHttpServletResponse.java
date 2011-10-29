package org.romaframework.aspect.view.html.http;

import java.io.IOException;
import java.io.PrintWriter;
import java.io.Writer;
import java.util.Locale;

import javax.servlet.ServletOutputStream;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletResponse;

public class MockHttpServletResponse implements HttpServletResponse {
	// private StringWriter stringWriter;
	private PrintWriter	writer;

	public MockHttpServletResponse(Writer writer) {
		// stringWriter = new StringWriter(new OutputStreamWriter(out));
		this.writer = new PrintWriter(writer);
	}

	public void addCookie(Cookie arg0) {
		// TODO Auto-generated method stub

	}

	public void addDateHeader(String arg0, long arg1) {
		// TODO Auto-generated method stub

	}

	public void addHeader(String arg0, String arg1) {
		// TODO Auto-generated method stub

	}

	public void addIntHeader(String arg0, int arg1) {
		// TODO Auto-generated method stub

	}

	public boolean containsHeader(String arg0) {
		// TODO Auto-generated method stub
		return false;
	}

	public String encodeRedirectURL(String arg0) {
		// TODO Auto-generated method stub
		return null;
	}

	public String encodeRedirectUrl(String arg0) {
		// TODO Auto-generated method stub
		return null;
	}

	public String encodeURL(String arg0) {
		// TODO Auto-generated method stub
		return null;
	}

	public String encodeUrl(String arg0) {
		// TODO Auto-generated method stub
		return null;
	}

	public void sendError(int arg0) throws IOException {
		throw new RuntimeException("" + arg0);
	}

	public void sendError(int arg0, String arg1) throws IOException {
		throw new RuntimeException("" + arg0 + " " + arg1);
	}

	public void sendRedirect(String arg0) throws IOException {
		// TODO Auto-generated method stub

	}

	public void setDateHeader(String arg0, long arg1) {
		// TODO Auto-generated method stub

	}

	public void setHeader(String arg0, String arg1) {
		// TODO Auto-generated method stub

	}

	public void setIntHeader(String arg0, int arg1) {
		// TODO Auto-generated method stub

	}

	public void setStatus(int arg0) {
		// TODO Auto-generated method stub

	}

	public void setStatus(int arg0, String arg1) {
		// TODO Auto-generated method stub

	}

	public void flushBuffer() throws IOException {
		// TODO Auto-generated method stub

	}

	public int getBufferSize() {
		// TODO Auto-generated method stub
		return 0;
	}

	public String getCharacterEncoding() {
		// TODO Auto-generated method stub
		return null;
	}

	public String getContentType() {
		// TODO Auto-generated method stub
		return null;
	}

	public Locale getLocale() {
		// TODO Auto-generated method stub
		return null;
	}

	public ServletOutputStream getOutputStream() throws IOException {
		throw new IOException();
	}

	public PrintWriter getWriter() throws IOException {
		// TODO Auto-generated method stub
		return writer;
	}

	public boolean isCommitted() {
		// TODO Auto-generated method stub
		return false;
	}

	public void reset() {
		// TODO Auto-generated method stub

	}

	public void resetBuffer() {
		// TODO Auto-generated method stub

	}

	public void setBufferSize(int arg0) {
		// TODO Auto-generated method stub

	}

	public void setCharacterEncoding(String arg0) {
		// TODO Auto-generated method stub

	}

	public void setContentLength(int arg0) {
		// TODO Auto-generated method stub

	}

	public void setContentType(String arg0) {
		// TODO Auto-generated method stub

	}

	public void setLocale(Locale arg0) {
		// TODO Auto-generated method stub

	}
	//
	// public StringWriter getStringWriter() {
	// return stringWriter;
	// }
	//
	// public void setStringWriter(StringWriter stringWriter) {
	// this.stringWriter = stringWriter;
	// }

}
