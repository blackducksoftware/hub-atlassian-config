package ut.com.blackducksoftware.integration.atlassian.config;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertNull;
import static org.junit.Assert.assertTrue;

import javax.ws.rs.core.Response;
import javax.ws.rs.core.Response.Status;

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

public class HubConfigControllerTest {

	@Test
	public void testGetConfigNullUser() {
		final UserManagerMock managerMock = new UserManagerMock();
		final PluginSettingsFactoryMock settingsFactory = new PluginSettingsFactoryMock();
		final TransactionTemplateMock transactionManager = new TransactionTemplateMock();
		final HttpServletRequestMock requestMock = new HttpServletRequestMock();

		final HubConfigController controller = new HubConfigController(managerMock, settingsFactory,
				transactionManager);

		final Response response = controller.get(requestMock);
		assertNotNull(response);
		assertEquals(Integer.valueOf(Status.UNAUTHORIZED.getStatusCode()), Integer.valueOf(response.getStatus()));
	}

	@Test
	public void testGetConfigNotAdmin() {
		final UserManagerMock managerMock = new UserManagerMock();
		managerMock.setRemoteUsername("User");
		final PluginSettingsFactoryMock settingsFactory = new PluginSettingsFactoryMock();
		final TransactionTemplateMock transactionManager = new TransactionTemplateMock();
		final HttpServletRequestMock requestMock = new HttpServletRequestMock();

		final HubConfigController controller = new HubConfigController(managerMock, settingsFactory,
				transactionManager);

		final Response response = controller.get(requestMock);
		assertNotNull(response);
		assertEquals(Integer.valueOf(Status.UNAUTHORIZED.getStatusCode()), Integer.valueOf(response.getStatus()));
	}

	@Test
	public void testGetConfigEmpty() {
		final UserManagerMock managerMock = new UserManagerMock();
		managerMock.setRemoteUsername("User");
		managerMock.setIsSystemAdmin(true);
		final PluginSettingsFactoryMock settingsFactory = new PluginSettingsFactoryMock();
		final TransactionTemplateMock transactionManager = new TransactionTemplateMock();
		final HttpServletRequestMock requestMock = new HttpServletRequestMock();

		final HubConfigController controller = new HubConfigController(managerMock, settingsFactory,
				transactionManager);

		final Response response = controller.get(requestMock);
		assertNotNull(response);
		final Object configObject = response.getEntity();
		assertNotNull(configObject);
		final HubServerConfigSerializable config = (HubServerConfigSerializable) configObject;
		assertNull(config.getHubUrl());
		assertNull(config.getUsername());
		assertNull(config.getPassword());
		assertEquals(Integer.valueOf(0), Integer.valueOf(config.getPasswordLength()));
		assertNull(config.getTimeout());
		assertNull(config.getHubProxyHost());
		assertNull(config.getHubProxyUser());
		assertNull(config.getHubProxyPassword());
		assertEquals(Integer.valueOf(0), Integer.valueOf(config.getHubProxyPasswordLength()));

		assertNotNull(config.getHubUrlError());
		assertNotNull(config.getUsernameError());
		assertNotNull(config.getPasswordError());
		assertNotNull(config.getTimeoutError());
		assertNull(config.getHubProxyHostError());
		assertNull(config.getHubProxyUserError());
		assertNull(config.getHubProxyPasswordError());
		assertNull(config.getTestConnectionError());
		assertTrue(config.hasErrors());
	}

	@Test
	public void testGetConfig() throws Exception {
		final String testUrl = "https://www.google.com";
		final String username = "username";
		final String passwordClear = "password";
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


		final Response response = controller.get(requestMock);
		assertNotNull(response);
		final Object configObject = response.getEntity();
		assertNotNull(configObject);
		final HubServerConfigSerializable config = (HubServerConfigSerializable) configObject;
		assertEquals(testUrl, config.getHubUrl());
		assertEquals(username, config.getUsername());
		assertEquals(passwordMasked, config.getPassword());
		assertEquals(Integer.valueOf(passwordClear.length()), Integer.valueOf(config.getPasswordLength()));
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
	public void testSaveConfigNullUser() {
		final UserManagerMock managerMock = new UserManagerMock();
		final PluginSettingsFactoryMock settingsFactory = new PluginSettingsFactoryMock();
		final TransactionTemplateMock transactionManager = new TransactionTemplateMock();
		final HttpServletRequestMock requestMock = new HttpServletRequestMock();

		final HubConfigController controller = new HubConfigController(managerMock, settingsFactory,
				transactionManager);

		final HubServerConfigSerializable config = new HubServerConfigSerializable();

		final Response response = controller.put(config, requestMock);
		assertNotNull(response);
		assertEquals(Integer.valueOf(Status.UNAUTHORIZED.getStatusCode()), Integer.valueOf(response.getStatus()));
	}

	@Test
	public void testSaveConfigNotAdmin() {
		final UserManagerMock managerMock = new UserManagerMock();
		managerMock.setRemoteUsername("User");
		final PluginSettingsFactoryMock settingsFactory = new PluginSettingsFactoryMock();
		final TransactionTemplateMock transactionManager = new TransactionTemplateMock();
		final HttpServletRequestMock requestMock = new HttpServletRequestMock();

		final HubConfigController controller = new HubConfigController(managerMock, settingsFactory,
				transactionManager);

		final HubServerConfigSerializable config = new HubServerConfigSerializable();

		final Response response = controller.put(config, requestMock);
		assertNotNull(response);
		assertEquals(Integer.valueOf(Status.UNAUTHORIZED.getStatusCode()), Integer.valueOf(response.getStatus()));
	}

	@Test
	public void testSaveConfigEmpty() {
		final UserManagerMock managerMock = new UserManagerMock();
		managerMock.setRemoteUsername("User");
		managerMock.setIsSystemAdmin(true);
		final PluginSettingsFactoryMock settingsFactory = new PluginSettingsFactoryMock();
		final TransactionTemplateMock transactionManager = new TransactionTemplateMock();
		final HttpServletRequestMock requestMock = new HttpServletRequestMock();

		final HubConfigController controller = new HubConfigController(managerMock, settingsFactory,
				transactionManager);

		HubServerConfigSerializable config = new HubServerConfigSerializable();

		final Response response = controller.put(config, requestMock);
		assertNotNull(response);
		final Object configObject = response.getEntity();
		assertNotNull(configObject);
		config = (HubServerConfigSerializable) configObject;
		assertNull(config.getHubUrl());
		assertNull(config.getUsername());
		assertNull(config.getPassword());
		assertEquals(Integer.valueOf(0), Integer.valueOf(config.getPasswordLength()));
		assertNull(config.getTimeout());
		assertNull(config.getHubProxyHost());
		assertNull(config.getHubProxyUser());
		assertNull(config.getHubProxyPassword());
		assertEquals(Integer.valueOf(0), Integer.valueOf(config.getHubProxyPasswordLength()));

		assertNotNull(config.getHubUrlError());
		assertNotNull(config.getUsernameError());
		assertNotNull(config.getPasswordError());
		assertNotNull(config.getTimeoutError());
		assertNull(config.getHubProxyHostError());
		assertNull(config.getHubProxyUserError());
		assertNull(config.getHubProxyPasswordError());
		assertNull(config.getTestConnectionError());
		assertTrue(config.hasErrors());
	}

	@Test
	public void testSaveConfigResetToBlank() throws Exception {
		final String testUrl = "https://www.google.com";
		final String username = "username";
		final String passwordClear = "password";
		final String passwordEnc = PasswordEncrypter.encrypt(passwordClear);
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

		HubServerConfigSerializable config = new HubServerConfigSerializable();

		final Response response = controller.put(config, requestMock);
		assertNotNull(response);
		final Object configObject = response.getEntity();
		assertNotNull(configObject);
		config = (HubServerConfigSerializable) configObject;

		assertNull(config.getHubUrl());
		assertNull(config.getUsername());
		assertNull(config.getPassword());
		assertEquals(Integer.valueOf(0), Integer.valueOf(config.getPasswordLength()));
		assertNull(config.getTimeout());
		assertNull(config.getHubProxyHost());
		assertNull(config.getHubProxyUser());
		assertNull(config.getHubProxyPassword());
		assertEquals(Integer.valueOf(0), Integer.valueOf(config.getHubProxyPasswordLength()));

		assertNotNull(config.getHubUrlError());
		assertNotNull(config.getUsernameError());
		assertNotNull(config.getPasswordError());
		assertNotNull(config.getTimeoutError());
		assertNull(config.getHubProxyHostError());
		assertNull(config.getHubProxyUserError());
		assertNull(config.getHubProxyPasswordError());
		assertNull(config.getTestConnectionError());
		assertTrue(config.hasErrors());

		assertNull(settings.get(HubConfigKeys.CONFIG_HUB_URL));
		assertNull(settings.get(HubConfigKeys.CONFIG_HUB_USER));
		assertNull(settings.get(HubConfigKeys.CONFIG_HUB_PASS));
		assertNull(settings.get(HubConfigKeys.CONFIG_HUB_PASS_LENGTH));
		assertNull(settings.get(HubConfigKeys.CONFIG_HUB_TIMEOUT));

	}

	@Test
	public void testSaveConfigNoUpdate() throws Exception {
		final String testUrl = "https://www.google.com";
		final String username = "username";
		final String passwordClear = "password";
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

		final Response response = controller.put(config, requestMock);
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
	public void testSaveConfigChange() throws Exception {
		final String testUrl1 = "https://www.google.com";
		final String username1 = "username";
		final String passwordClear1 = "password";
		final String passwordEnc1 = PasswordEncrypter.encrypt(passwordClear1);
		final String timeout1 = "120";

		final String testUrl2 = "fakeUrl";
		final String username2 = "user";
		final String passwordClear2 = "pass";
		final String passwordEnc2 = PasswordEncrypter.encrypt(passwordClear2);
		final String timeout2 = "300";

		final UserManagerMock managerMock = new UserManagerMock();
		managerMock.setRemoteUsername("User");
		managerMock.setIsSystemAdmin(true);
		final PluginSettingsFactoryMock settingsFactory = new PluginSettingsFactoryMock();
		final PluginSettings settings = settingsFactory.createGlobalSettings();
		settings.put(HubConfigKeys.CONFIG_HUB_URL, testUrl1);
		settings.put(HubConfigKeys.CONFIG_HUB_USER, username1);
		settings.put(HubConfigKeys.CONFIG_HUB_PASS, passwordEnc1);
		settings.put(HubConfigKeys.CONFIG_HUB_PASS_LENGTH, String.valueOf(passwordClear1.length()));
		settings.put(HubConfigKeys.CONFIG_HUB_TIMEOUT, timeout1);

		final TransactionTemplateMock transactionManager = new TransactionTemplateMock();
		final HttpServletRequestMock requestMock = new HttpServletRequestMock();

		final HubConfigController controller = new HubConfigController(managerMock, settingsFactory,
				transactionManager);

		HubServerConfigSerializable config = new HubServerConfigSerializable();
		config.setHubUrl(testUrl2);
		config.setUsername(username2);
		config.setPassword(passwordClear2);
		config.setTimeout(timeout2);

		final Response response = controller.put(config, requestMock);
		assertNotNull(response);
		final Object configObject = response.getEntity();
		assertNotNull(configObject);
		config = (HubServerConfigSerializable) configObject;

		assertEquals(testUrl2, config.getHubUrl());
		assertEquals(username2, config.getUsername());
		assertEquals(passwordClear2, config.getPassword());
		assertEquals(Integer.valueOf(0), Integer.valueOf(config.getPasswordLength()));
		assertEquals(timeout2, config.getTimeout());
		assertNull(config.getHubProxyHost());
		assertNull(config.getHubProxyUser());
		assertNull(config.getHubProxyPassword());
		assertEquals(Integer.valueOf(0), Integer.valueOf(config.getHubProxyPasswordLength()));

		assertNotNull(config.getHubUrlError());
		assertNull(config.getUsernameError());
		assertNull(config.getPasswordError());
		assertNull(config.getTimeoutError());
		assertNull(config.getHubProxyHostError());
		assertNull(config.getHubProxyUserError());
		assertNull(config.getHubProxyPasswordError());
		assertNull(config.getTestConnectionError());
		assertTrue(config.hasErrors());

		assertEquals(testUrl2, settings.get(HubConfigKeys.CONFIG_HUB_URL));
		assertEquals(username2, settings.get(HubConfigKeys.CONFIG_HUB_USER));
		assertEquals(passwordEnc2, settings.get(HubConfigKeys.CONFIG_HUB_PASS));
		assertEquals(String.valueOf(passwordClear2.length()), settings.get(HubConfigKeys.CONFIG_HUB_PASS_LENGTH));
		assertEquals(timeout2, settings.get(HubConfigKeys.CONFIG_HUB_TIMEOUT));
	}

	@Test
	public void testTestConnectionConfigNullUser() {
		final UserManagerMock managerMock = new UserManagerMock();
		final PluginSettingsFactoryMock settingsFactory = new PluginSettingsFactoryMock();
		final TransactionTemplateMock transactionManager = new TransactionTemplateMock();
		final HttpServletRequestMock requestMock = new HttpServletRequestMock();

		final HubConfigController controller = new HubConfigController(managerMock, settingsFactory,
				transactionManager);

		final HubServerConfigSerializable config = new HubServerConfigSerializable();

		final Response response = controller.testConnection(config, requestMock);
		assertNotNull(response);
		assertEquals(Integer.valueOf(Status.UNAUTHORIZED.getStatusCode()), Integer.valueOf(response.getStatus()));
	}

	@Test
	public void testTestConnectionConfigNotAdmin() {
		final UserManagerMock managerMock = new UserManagerMock();
		managerMock.setRemoteUsername("User");
		final PluginSettingsFactoryMock settingsFactory = new PluginSettingsFactoryMock();
		final TransactionTemplateMock transactionManager = new TransactionTemplateMock();
		final HttpServletRequestMock requestMock = new HttpServletRequestMock();

		final HubConfigController controller = new HubConfigController(managerMock, settingsFactory,
				transactionManager);

		final HubServerConfigSerializable config = new HubServerConfigSerializable();

		final Response response = controller.testConnection(config, requestMock);
		assertNotNull(response);
		assertEquals(Integer.valueOf(Status.UNAUTHORIZED.getStatusCode()), Integer.valueOf(response.getStatus()));
	}

	@Test
	public void testTestConnectionConfigEmpty() {
		final UserManagerMock managerMock = new UserManagerMock();
		managerMock.setRemoteUsername("User");
		managerMock.setIsSystemAdmin(true);
		final PluginSettingsFactoryMock settingsFactory = new PluginSettingsFactoryMock();
		final TransactionTemplateMock transactionManager = new TransactionTemplateMock();
		final HttpServletRequestMock requestMock = new HttpServletRequestMock();

		final HubConfigController controller = new HubConfigController(managerMock, settingsFactory,
				transactionManager);

		HubServerConfigSerializable config = new HubServerConfigSerializable();

		final Response response = controller.testConnection(config, requestMock);
		assertNotNull(response);
		final Object configObject = response.getEntity();
		assertNotNull(configObject);
		config = (HubServerConfigSerializable) configObject;
		assertNull(config.getHubUrl());
		assertNull(config.getUsername());
		assertNull(config.getPassword());
		assertEquals(Integer.valueOf(0), Integer.valueOf(config.getPasswordLength()));
		assertNull(config.getTimeout());
		assertNull(config.getHubProxyHost());
		assertNull(config.getHubProxyUser());
		assertNull(config.getHubProxyPassword());
		assertEquals(Integer.valueOf(0), Integer.valueOf(config.getHubProxyPasswordLength()));

		assertNotNull(config.getHubUrlError());
		assertNotNull(config.getUsernameError());
		assertNotNull(config.getPasswordError());
		assertNotNull(config.getTimeoutError());
		assertNull(config.getHubProxyHostError());
		assertNull(config.getHubProxyUserError());
		assertNull(config.getHubProxyPasswordError());
		assertNull(config.getTestConnectionError());
		assertTrue(config.hasErrors());
	}

	@Test
	public void testTestConnectionConfigBlank() throws Exception {
		final String testUrl = "https://www.google.com";
		final String username = "username";
		final String passwordClear = "password";
		final String passwordEnc = PasswordEncrypter.encrypt(passwordClear);
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

		HubServerConfigSerializable config = new HubServerConfigSerializable();

		final Response response = controller.testConnection(config, requestMock);
		assertNotNull(response);
		final Object configObject = response.getEntity();
		assertNotNull(configObject);
		config = (HubServerConfigSerializable) configObject;

		assertNull(config.getHubUrl());
		assertNull(config.getUsername());
		assertNull(config.getPassword());
		assertEquals(Integer.valueOf(0), Integer.valueOf(config.getPasswordLength()));
		assertNull(config.getTimeout());
		assertNull(config.getHubProxyHost());
		assertNull(config.getHubProxyUser());
		assertNull(config.getHubProxyPassword());
		assertEquals(Integer.valueOf(0), Integer.valueOf(config.getHubProxyPasswordLength()));

		assertNotNull(config.getHubUrlError());
		assertNotNull(config.getUsernameError());
		assertNotNull(config.getPasswordError());
		assertNotNull(config.getTimeoutError());
		assertNull(config.getHubProxyHostError());
		assertNull(config.getHubProxyUserError());
		assertNull(config.getHubProxyPasswordError());
		assertNull(config.getTestConnectionError());
		assertTrue(config.hasErrors());
	}
}
