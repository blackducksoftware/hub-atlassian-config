package com.blackducksoftware.integration.atlassian.mocks;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.Locale;

import javax.servlet.ServletOutputStream;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletResponse;

public class HttpServletResponseMock implements HttpServletResponse {

	String redirectedLocation;

	String contentType;

	@Override
	public String getCharacterEncoding() {

		return null;
	}

	@Override
	public String getContentType() {

		return contentType;
	}

	@Override
	public ServletOutputStream getOutputStream() throws IOException {

		return null;
	}

	@Override
	public PrintWriter getWriter() throws IOException {

		return null;
	}

	@Override
	public void setCharacterEncoding(final String charset) {

	}

	@Override
	public void setContentLength(final int len) {

	}

	@Override
	public void setContentType(final String type) {
		contentType = type;
	}

	@Override
	public void setBufferSize(final int size) {

	}

	@Override
	public int getBufferSize() {

		return 0;
	}

	@Override
	public void flushBuffer() throws IOException {

	}

	@Override
	public void resetBuffer() {

	}

	@Override
	public boolean isCommitted() {

		return false;
	}

	@Override
	public void reset() {

	}

	@Override
	public void setLocale(final Locale loc) {


	}

	@Override
	public Locale getLocale() {

		return null;
	}

	@Override
	public void addCookie(final Cookie cookie) {

	}

	@Override
	public boolean containsHeader(final String name) {

		return false;
	}

	@Override
	public String encodeURL(final String url) {

		return null;
	}

	@Override
	public String encodeRedirectURL(final String url) {

		return null;
	}

	@Override
	public String encodeUrl(final String url) {

		return null;
	}

	@Override
	public String encodeRedirectUrl(final String url) {

		return null;
	}

	@Override
	public void sendError(final int sc, final String msg) throws IOException {

	}

	@Override
	public void sendError(final int sc) throws IOException {

	}

	@Override
	public void sendRedirect(final String location) throws IOException {
		redirectedLocation = location;
	}

	public String getRedirectedLocation() {
		return redirectedLocation;
	}

	@Override
	public void setDateHeader(final String name, final long date) {

	}

	@Override
	public void addDateHeader(final String name, final long date) {

	}

	@Override
	public void setHeader(final String name, final String value) {


	}

	@Override
	public void addHeader(final String name, final String value) {

	}

	@Override
	public void setIntHeader(final String name, final int value) {

	}

	@Override
	public void addIntHeader(final String name, final int value) {

	}

	@Override
	public void setStatus(final int sc) {

	}

	@Override
	public void setStatus(final int sc, final String sm) {

	}

}
