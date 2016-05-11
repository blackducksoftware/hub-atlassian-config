package com.blackducksoftware.integration.atlassian.mocks;

import java.io.IOException;
import java.io.Writer;
import java.util.Map;

import com.atlassian.templaterenderer.RenderingException;
import com.atlassian.templaterenderer.TemplateRenderer;

public class TemplateRendererMock implements TemplateRenderer {

	String renderedString;

	public String getRenderedString() {
		return renderedString;
	}

	@Override
	public void render(final String arg0, final Writer arg1) throws RenderingException, IOException {
		renderedString = arg0;
	}

	@Override
	public void render(final String arg0, final Map<String, Object> arg1, final Writer arg2) throws RenderingException, IOException {

	}

	@Override
	public String renderFragment(final String arg0, final Map<String, Object> arg1) throws RenderingException {
		return null;
	}

	@Override
	public boolean resolve(final String arg0) {
		return false;
	}

}
