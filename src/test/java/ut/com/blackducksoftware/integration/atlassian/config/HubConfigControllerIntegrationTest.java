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
package ut.com.blackducksoftware.integration.atlassian.config;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertNull;
import static org.junit.Assert.assertTrue;

import java.io.IOException;
import java.io.InputStream;
import java.net.URL;
import java.util.Properties;

import javax.ws.rs.core.Response;
import javax.ws.rs.core.Response.Status;

import org.junit.BeforeClass;
import org.junit.Test;

import com.atlassian.sal.api.pluginsettings.PluginSettings;
import com.blackducksoftware.integration.atlassian.config.HubConfigController;
import com.blackducksoftware.integration.atlassian.config.HubServerConfigSerializable;
import com.blackducksoftware.integration.atlassian.mocks.HttpServletRequestMock;
import com.blackducksoftware.integration.atlassian.mocks.PluginSettingsFactoryMock;
import com.blackducksoftware.integration.atlassian.mocks.TransactionTemplateMock;
import com.blackducksoftware.integration.atlassian.mocks.UserManagerMock;
import com.blackducksoftware.integration.atlassian.utils.HubConfigKeys;
import com.blackducksoftware.integration.hub.encryption.PasswordEncrypter;

public class HubConfigControllerIntegrationTest {

	private static Properties testProperties;

	@BeforeClass
	public static void init() throws Exception {
		testProperties = new Properties();
		final ClassLoader classLoader = Thread.currentThread().getContextClassLoader();
		final InputStream is = classLoader.getResourceAsStream("test.properties");
		try {
			testProperties.load(is);
		} catch (final IOException e) {
			System.err.println("reading test.properties failed!");
		}
	}

	@Test
	public void testTestConnectionConfigNoUpdate() throws Exception {
		final String testUrl = testProperties.getProperty("TEST_HUB_SERVER_URL");
		final String username = testProperties.getProperty("TEST_USERNAME");
		final String passwordClear = testProperties.getProperty("TEST_PASSWORD");
		final String passwordEnc = PasswordEncrypter.encrypt(passwordClear);
		final String passwordMasked = HubServerConfigSerializable.getMaskedString(passwordClear.length());
		final String timeout = "120";

		final UserManagerMock managerMock = new UserManagerMock();
		managerMock.setRemoteUsername("User");
		managerMock.setIsSystemAdmin(true);
		final PluginSettingsFactoryMock settingsFactory = new PluginSettingsFactoryMock();
		final PluginSettings settings = settingsFactory.createGlobalSettings();
		settings.put(HubConfigKeys.CONFIG_HUB_URL, testUrl);
		settings.put(HubConfigKeys.CONFIG_HUB_USER, username);
		settings.put(HubConfigKeys.CONFIG_HUB_PASS, passwordEnc);
		settings.put(HubConfigKeys.CONFIG_HUB_PASS_LENGTH, String.valueOf(passwordClear.length()));
		settings.put(HubConfigKeys.CONFIG_HUB_TIMEOUT, timeout);

		final TransactionTemplateMock transactionManager = new TransactionTemplateMock();
		final HttpServletRequestMock requestMock = new HttpServletRequestMock();

		final HubConfigController controller = new HubConfigController(managerMock, settingsFactory,
				transactionManager);

		final HubServerConfigSerializable config = new HubServerConfigSerializable();
		config.setHubUrl(testUrl);
		config.setUsername(username);
		config.setPassword(passwordMasked);
		config.setTimeout(timeout);

		final Response response = controller.testConnection(config, requestMock);
		assertNotNull(response);
		assertEquals(Integer.valueOf(Status.NO_CONTENT.getStatusCode()), Integer.valueOf(response.getStatus()));

		assertEquals(testUrl, config.getHubUrl());
		assertEquals(username, config.getUsername());
		assertEquals(passwordMasked, config.getPassword());
		assertEquals(Integer.valueOf(0), Integer.valueOf(config.getPasswordLength()));
		assertEquals(timeout, config.getTimeout());
		assertNull(config.getHubProxyHost());
		assertNull(config.getHubProxyUser());
		assertNull(config.getHubProxyPassword());
		assertEquals(Integer.valueOf(0), Integer.valueOf(config.getHubProxyPasswordLength()));

		assertNull(config.getHubUrlError());
		assertNull(config.getUsernameError());
		assertNull(config.getPasswordError());
		assertNull(config.getTimeoutError());
		assertNull(config.getHubProxyHostError());
		assertNull(config.getHubProxyUserError());
		assertNull(config.getHubProxyPasswordError());
		assertNull(config.getTestConnectionError());
		assertFalse(config.hasErrors());
	}

	@Test
	public void testTestConnectionConfigUpdate() throws Exception {
		final String testUrl = testProperties.getProperty("TEST_HUB_SERVER_URL");
		final String username = testProperties.getProperty("TEST_USERNAME");
		final String passwordClear = testProperties.getProperty("TEST_PASSWORD");
		final String timeout = "120";

		final UserManagerMock managerMock = new UserManagerMock();
		managerMock.setRemoteUsername("User");
		managerMock.setIsSystemAdmin(true);
		final PluginSettingsFactoryMock settingsFactory = new PluginSettingsFactoryMock();

		final TransactionTemplateMock transactionManager = new TransactionTemplateMock();
		final HttpServletRequestMock requestMock = new HttpServletRequestMock();

		final HubConfigController controller = new HubConfigController(managerMock, settingsFactory,
				transactionManager);

		final HubServerConfigSerializable config = new HubServerConfigSerializable();
		config.setHubUrl(testUrl);
		config.setUsername(username);
		config.setPassword(passwordClear);
		config.setTimeout(timeout);

		final Response response = controller.testConnection(config, requestMock);
		assertNotNull(response);
		assertEquals(Integer.valueOf(Status.NO_CONTENT.getStatusCode()), Integer.valueOf(response.getStatus()));

		assertEquals(testUrl, config.getHubUrl());
		assertEquals(username, config.getUsername());
		assertEquals(passwordClear, config.getPassword());
		assertEquals(Integer.valueOf(0), Integer.valueOf(config.getPasswordLength()));
		assertEquals(timeout, config.getTimeout());
		assertNull(config.getHubProxyHost());
		assertNull(config.getHubProxyUser());
		assertNull(config.getHubProxyPassword());
		assertEquals(Integer.valueOf(0), Integer.valueOf(config.getHubProxyPasswordLength()));

		assertNull(config.getHubUrlError());
		assertNull(config.getUsernameError());
		assertNull(config.getPasswordError());
		assertNull(config.getTimeoutError());
		assertNull(config.getHubProxyHostError());
		assertNull(config.getHubProxyUserError());
		assertNull(config.getHubProxyPasswordError());
		assertNull(config.getTestConnectionError());
		assertFalse(config.hasErrors());
	}

	@Test
	public void testTestConnectionConfigUpdateInvalidUser() throws Exception {
		final String testUrl = testProperties.getProperty("TEST_HUB_SERVER_URL");
		final String username = "ASSERTNOTREALUSER";
		final String passwordClear = "DEFINITELYFAKEPASSWORD";
		final String timeout = "120";

		final UserManagerMock managerMock = new UserManagerMock();
		managerMock.setRemoteUsername("User");
		managerMock.setIsSystemAdmin(true);
		final PluginSettingsFactoryMock settingsFactory = new PluginSettingsFactoryMock();

		final TransactionTemplateMock transactionManager = new TransactionTemplateMock();
		final HttpServletRequestMock requestMock = new HttpServletRequestMock();

		final HubConfigController controller = new HubConfigController(managerMock, settingsFactory,
				transactionManager);

		HubServerConfigSerializable config = new HubServerConfigSerializable();
		config.setHubUrl(testUrl);
		config.setUsername(username);
		config.setPassword(passwordClear);
		config.setTimeout(timeout);

		final Response response = controller.testConnection(config, requestMock);
		assertNotNull(response);
		final Object configObject = response.getEntity();
		assertNotNull(configObject);
		config = (HubServerConfigSerializable) configObject;

		assertEquals(testUrl, config.getHubUrl());
		assertEquals(username, config.getUsername());
		assertEquals(passwordClear, config.getPassword());
		assertEquals(Integer.valueOf(0), Integer.valueOf(config.getPasswordLength()));
		assertEquals(timeout, config.getTimeout());
		assertNull(config.getHubProxyHost());
		assertNull(config.getHubProxyUser());
		assertNull(config.getHubProxyPassword());
		assertEquals(Integer.valueOf(0), Integer.valueOf(config.getHubProxyPasswordLength()));

		assertNull(config.getHubUrlError());
		assertNotNull(config.getUsernameError());
		assertNull(config.getPasswordError());
		assertNull(config.getTimeoutError());
		assertNull(config.getHubProxyHostError());
		assertNull(config.getHubProxyUserError());
		assertNull(config.getHubProxyPasswordError());
		assertNull(config.getTestConnectionError());
		assertTrue(config.hasErrors());
	}

	@Test
	public void testTestConnectionConfigUpdatePassThroughProxyIgnored() throws Exception {
		final String testUrl = testProperties.getProperty("TEST_HUB_SERVER_URL");
		final String username = testProperties.getProperty("TEST_USERNAME");
		final String passwordClear = testProperties.getProperty("TEST_PASSWORD");
		final String timeout = "120";

		final URL hubUrl = new URL(testUrl);

		final String proxyHost = "fakeProxyHost";
		final String proxyPort = "8456";
		final String noProxyHosts = hubUrl.getHost();

		final UserManagerMock managerMock = new UserManagerMock();
		managerMock.setRemoteUsername("User");
		managerMock.setIsSystemAdmin(true);
		final PluginSettingsFactoryMock settingsFactory = new PluginSettingsFactoryMock();

		final TransactionTemplateMock transactionManager = new TransactionTemplateMock();
		final HttpServletRequestMock requestMock = new HttpServletRequestMock();

		final HubConfigController controller = new HubConfigController(managerMock, settingsFactory,
				transactionManager);

		final HubServerConfigSerializable config = new HubServerConfigSerializable();
		config.setHubUrl(testUrl);
		config.setUsername(username);
		config.setPassword(passwordClear);
		config.setTimeout(timeout);
		config.setHubProxyHost(proxyHost);
		config.setHubProxyPort(proxyPort);
		config.setHubNoProxyHosts(noProxyHosts);

		final Response response = controller.testConnection(config, requestMock);
		assertNotNull(response);
		assertEquals(Integer.valueOf(Status.NO_CONTENT.getStatusCode()), Integer.valueOf(response.getStatus()));

		assertEquals(testUrl, config.getHubUrl());
		assertEquals(username, config.getUsername());
		assertEquals(passwordClear, config.getPassword());
		assertEquals(Integer.valueOf(0), Integer.valueOf(config.getPasswordLength()));
		assertEquals(timeout, config.getTimeout());
		assertEquals(proxyHost, config.getHubProxyHost());
		assertEquals(proxyPort, config.getHubProxyPort());
		assertNull(config.getHubProxyUser());
		assertNull(config.getHubProxyPassword());
		assertEquals(Integer.valueOf(0), Integer.valueOf(config.getHubProxyPasswordLength()));

		assertNull(config.getHubUrlError());
		assertNull(config.getUsernameError());
		assertNull(config.getPasswordError());
		assertNull(config.getTimeoutError());
		assertNull(config.getHubProxyHostError());
		assertNull(config.getHubProxyUserError());
		assertNull(config.getHubProxyPasswordError());
		assertNull(config.getTestConnectionError());
		assertFalse(config.hasErrors());
	}

	@Test
	public void testTestConnectionConfigUpdatePassThroughProxy() throws Exception {
		final String testUrl = testProperties.getProperty("TEST_HUB_SERVER_URL");
		final String username = testProperties.getProperty("TEST_USERNAME");
		final String passwordClear = testProperties.getProperty("TEST_PASSWORD");
		final String timeout = "120";

		final String proxyHost = testProperties.getProperty("TEST_PROXY_HOST_PASSTHROUGH");
		final String proxyPort = testProperties.getProperty("TEST_PROXY_PORT_PASSTHROUGH");

		final UserManagerMock managerMock = new UserManagerMock();
		managerMock.setRemoteUsername("User");
		managerMock.setIsSystemAdmin(true);
		final PluginSettingsFactoryMock settingsFactory = new PluginSettingsFactoryMock();

		final TransactionTemplateMock transactionManager = new TransactionTemplateMock();
		final HttpServletRequestMock requestMock = new HttpServletRequestMock();

		final HubConfigController controller = new HubConfigController(managerMock, settingsFactory,
				transactionManager);

		final HubServerConfigSerializable config = new HubServerConfigSerializable();
		config.setHubUrl(testUrl);
		config.setUsername(username);
		config.setPassword(passwordClear);
		config.setTimeout(timeout);
		config.setHubProxyHost(proxyHost);
		config.setHubProxyPort(proxyPort);

		final Response response = controller.testConnection(config, requestMock);
		assertNotNull(response);
		assertEquals(Integer.valueOf(Status.NO_CONTENT.getStatusCode()), Integer.valueOf(response.getStatus()));

		assertEquals(testUrl, config.getHubUrl());
		assertEquals(username, config.getUsername());
		assertEquals(passwordClear, config.getPassword());
		assertEquals(Integer.valueOf(0), Integer.valueOf(config.getPasswordLength()));
		assertEquals(timeout, config.getTimeout());
		assertEquals(proxyHost, config.getHubProxyHost());
		assertEquals(proxyPort, config.getHubProxyPort());
		assertNull(config.getHubProxyUser());
		assertNull(config.getHubProxyPassword());
		assertEquals(Integer.valueOf(0), Integer.valueOf(config.getHubProxyPasswordLength()));

		assertNull(config.getHubUrlError());
		assertNull(config.getUsernameError());
		assertNull(config.getPasswordError());
		assertNull(config.getTimeoutError());
		assertNull(config.getHubProxyHostError());
		assertNull(config.getHubProxyUserError());
		assertNull(config.getHubProxyPasswordError());
		assertNull(config.getTestConnectionError());
		assertFalse(config.hasErrors());
	}
}
