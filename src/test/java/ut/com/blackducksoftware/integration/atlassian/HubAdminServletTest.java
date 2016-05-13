/*******************************************************************************
 * Copyright (C) 2016 Black Duck Software, Inc.
 * http://www.blackducksoftware.com/
 *
 * Licensed to the Apache Software Foundation (ASF) under one
 * or more contributor license agreements. See the NOTICE file
 * distributed with this work for additional information
 * regarding copyright ownership. The ASF licenses this file
 * to you under the Apache License, Version 2.0 (the
 * "License"); you may not use this file except in compliance
 * with the License. You may obtain a copy of the License at
 *
 * http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing,
 * software distributed under the License is distributed on an
 * "AS IS" BASIS, WITHOUT WARRANTIES OR CONDITIONS OF ANY
 * KIND, either express or implied. See the License for the
 * specific language governing permissions and limitations
 * under the License.
 *******************************************************************************/
package ut.com.blackducksoftware.integration.atlassian;


import static org.junit.Assert.assertEquals;

import org.junit.Test;

import com.blackducksoftware.integration.atlassian.HubAdminServlet;
import com.blackducksoftware.integration.atlassian.mocks.HttpServletRequestMock;
import com.blackducksoftware.integration.atlassian.mocks.HttpServletResponseMock;
import com.blackducksoftware.integration.atlassian.mocks.LoginUriProviderMock;
import com.blackducksoftware.integration.atlassian.mocks.TemplateRendererMock;
import com.blackducksoftware.integration.atlassian.mocks.UserManagerMock;

public class HubAdminServletTest {

	@Test
	public void testDoGetUserNull() throws Exception {
		final String redirectUrl = "http://testRedirect";
		final StringBuffer requestUrl = new StringBuffer();
		requestUrl.append(redirectUrl);

		final UserManagerMock managerMock = new UserManagerMock();

		final LoginUriProviderMock loginProviderMock = new LoginUriProviderMock();

		final TemplateRendererMock rendererMock = new TemplateRendererMock();

		final HttpServletResponseMock responseMock = new HttpServletResponseMock();

		final HttpServletRequestMock requestMock = new HttpServletRequestMock();
		requestMock.setRequestURL(requestUrl);

		final HubAdminServlet servlet = new HubAdminServlet(managerMock, loginProviderMock, rendererMock);

		servlet.doGet(requestMock, responseMock);

		assertEquals(redirectUrl, responseMock.getRedirectedLocation());
	}

	@Test
	public void testDoGetUserNotAdmin() throws Exception {
		final String userName = "TestUser";
		final String redirectUrl = "http://testRedirect";
		final StringBuffer requestUrl = new StringBuffer();
		requestUrl.append(redirectUrl);

		final UserManagerMock managerMock = new UserManagerMock();
		managerMock.setRemoteUsername(userName);

		final LoginUriProviderMock loginProviderMock = new LoginUriProviderMock();

		final TemplateRendererMock rendererMock = new TemplateRendererMock();

		final HttpServletResponseMock responseMock = new HttpServletResponseMock();

		final HttpServletRequestMock requestMock = new HttpServletRequestMock();
		requestMock.setRequestURL(requestUrl);

		final HubAdminServlet servlet = new HubAdminServlet(managerMock, loginProviderMock, rendererMock);

		servlet.doGet(requestMock, responseMock);

		assertEquals(redirectUrl, responseMock.getRedirectedLocation());
	}

	@Test
	public void testDoGetUserAdmin() throws Exception {
		final String userName = "TestUser";
		final String redirectUrl = "http://testRedirect";
		final StringBuffer requestUrl = new StringBuffer();
		requestUrl.append(redirectUrl);

		final UserManagerMock managerMock = new UserManagerMock();
		managerMock.setRemoteUsername(userName);
		managerMock.setIsSystemAdmin(true);

		final LoginUriProviderMock loginProviderMock = new LoginUriProviderMock();

		final TemplateRendererMock rendererMock = new TemplateRendererMock();

		final HttpServletResponseMock responseMock = new HttpServletResponseMock();

		final HttpServletRequestMock requestMock = new HttpServletRequestMock();
		requestMock.setRequestURL(requestUrl);

		final HubAdminServlet servlet = new HubAdminServlet(managerMock, loginProviderMock, rendererMock);

		servlet.doGet(requestMock, responseMock);

		assertEquals("text/html;charset=utf-8", responseMock.getContentType());
		assertEquals("hub-admin.vm", rendererMock.getRenderedString());
	}
}
