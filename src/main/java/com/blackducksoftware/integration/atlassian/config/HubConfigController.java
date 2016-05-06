/*******************************************************************************
 * Copyright (C) 2016 Black Duck Software, Inc.
 * http://www.blackducksoftware.com/
 *
 * This program is free software; you can redistribute it and/or
 * modify it under the terms of the GNU General Public License version 2 only
 * as published by the Free Software Foundation.
 *
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE. See the
 * GNU General Public License for more details.
 *
 * You should have received a copy of the GNU General Public License version 2
 * along with this program; if not, write to the Free Software
 * Foundation, Inc., 51 Franklin Street, Fifth Floor, Boston, MA 02110-1301, USA.
 *******************************************************************************/
package com.blackducksoftware.integration.atlassian.config;

import javax.servlet.http.HttpServletRequest;
import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
import javax.ws.rs.PUT;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.Response.Status;

import org.apache.commons.lang3.StringUtils;
import org.apache.commons.lang3.math.NumberUtils;

import com.atlassian.sal.api.pluginsettings.PluginSettings;
import com.atlassian.sal.api.pluginsettings.PluginSettingsFactory;
import com.atlassian.sal.api.transaction.TransactionCallback;
import com.atlassian.sal.api.transaction.TransactionTemplate;
import com.atlassian.sal.api.user.UserManager;
import com.blackducksoftware.integration.atlassian.utils.HubConfigKeys;
import com.blackducksoftware.integration.hub.builder.HubServerConfigBuilder;
import com.blackducksoftware.integration.hub.builder.ValidationResultEnum;
import com.blackducksoftware.integration.hub.builder.ValidationResults;
import com.blackducksoftware.integration.hub.encryption.PasswordEncrypter;
import com.blackducksoftware.integration.hub.exception.EncryptionException;
import com.blackducksoftware.integration.hub.global.GlobalFieldKey;
import com.blackducksoftware.integration.hub.global.HubCredentialsFieldEnum;
import com.blackducksoftware.integration.hub.global.HubProxyInfoFieldEnum;
import com.blackducksoftware.integration.hub.global.HubServerConfig;
import com.blackducksoftware.integration.hub.global.HubServerConfigFieldEnum;

@Path("/")
public class HubConfigController {
	private final UserManager userManager;
	private final PluginSettingsFactory pluginSettingsFactory;
	private final TransactionTemplate transactionTemplate;

	public HubConfigController(final UserManager userManager, final PluginSettingsFactory pluginSettingsFactory,
			final TransactionTemplate transactionTemplate) {
		this.userManager = userManager;
		this.pluginSettingsFactory = pluginSettingsFactory;
		this.transactionTemplate = transactionTemplate;
	}

	@GET
	@Produces(MediaType.APPLICATION_JSON)
	public Response get(@Context final HttpServletRequest request) {
		final String username = userManager.getRemoteUsername(request);
		if (username == null || !userManager.isSystemAdmin(username)) {
			return Response.status(Status.UNAUTHORIZED).build();
		}

		final Object obj = transactionTemplate.execute(new TransactionCallback() {
			@Override
			public Object doInTransaction() {
				final PluginSettings settings = pluginSettingsFactory.createGlobalSettings();

				final String hubUrl = getValue(settings, HubConfigKeys.CONFIG_HUB_URL);
				final String username = getValue(settings, HubConfigKeys.CONFIG_HUB_USER);
				final String password = getValue(settings, HubConfigKeys.CONFIG_HUB_PASS);
				final String passwordLength = getValue(settings, HubConfigKeys.CONFIG_HUB_PASS_LENGTH);
				final String timeout = getValue(settings, HubConfigKeys.CONFIG_HUB_TIMEOUT);
				final String proxyHost = getValue(settings, HubConfigKeys.CONFIG_PROXY_HOST);
				final String proxyPort = getValue(settings, HubConfigKeys.CONFIG_PROXY_PORT);
				final String noProxyHosts = getValue(settings, HubConfigKeys.CONFIG_PROXY_NO_HOST);
				final String proxyUser = getValue(settings, HubConfigKeys.CONFIG_PROXY_USER);
				final String proxyPassword = getValue(settings, HubConfigKeys.CONFIG_PROXY_PASS);
				final String proxyPasswordLength = getValue(settings, HubConfigKeys.CONFIG_PROXY_PASS_LENGTH);

				final HubServerConfigSerializable config = new HubServerConfigSerializable();


				final HubServerConfigBuilder serverConfigBuilder = new HubServerConfigBuilder(true);
				serverConfigBuilder.setHubUrl(hubUrl);
				serverConfigBuilder.setTimeout(timeout);
				serverConfigBuilder.setUsername(username);
				serverConfigBuilder.setPassword(password);
				serverConfigBuilder.setPasswordLength(NumberUtils.toInt(passwordLength));

				serverConfigBuilder.setProxyHost(proxyHost);
				serverConfigBuilder.setProxyPort(proxyPort);
				serverConfigBuilder.setIgnoredProxyHosts(noProxyHosts);
				serverConfigBuilder.setUsername(proxyUser);
				serverConfigBuilder.setPassword(proxyPassword);
				serverConfigBuilder.setPasswordLength(NumberUtils.toInt(proxyPasswordLength));
				final ValidationResults<GlobalFieldKey, HubServerConfig> serverConfigResults = serverConfigBuilder
						.build();
				if (serverConfigResults.hasErrors()) {
					config.setHubUrlError(serverConfigResults.getResultString(HubServerConfigFieldEnum.HUBURL,
							ValidationResultEnum.ERROR));
					config.setTimeoutError(serverConfigResults.getResultString(HubServerConfigFieldEnum.HUBTIMEOUT,
							ValidationResultEnum.ERROR));
					config.setUsernameError(serverConfigResults.getResultString(HubCredentialsFieldEnum.USERNAME,
							ValidationResultEnum.ERROR));
					config.setPasswordError(serverConfigResults.getResultString(HubCredentialsFieldEnum.PASSWORD,
							ValidationResultEnum.ERROR));
					config.setHubProxyHostError(
							serverConfigResults.getResultString(HubProxyInfoFieldEnum.PROXYHOST,
									ValidationResultEnum.ERROR));
					config.setHubNoProxyHostsError(
							serverConfigResults.getResultString(HubProxyInfoFieldEnum.NOPROXYHOSTS,
							ValidationResultEnum.ERROR));
					config.setHubProxyPortError(
							serverConfigResults.getResultString(HubProxyInfoFieldEnum.PROXYPORT,
									ValidationResultEnum.ERROR));
					config.setHubProxyUserError(serverConfigResults.getResultString(HubProxyInfoFieldEnum.PROXYUSERNAME,
							ValidationResultEnum.ERROR));
					config.setHubProxyPasswordError(serverConfigResults
							.getResultString(HubProxyInfoFieldEnum.PROXYPASSWORD,
							ValidationResultEnum.ERROR));
				}

				config.setHubUrl(hubUrl);
				config.setUsername(username);

				if (StringUtils.isNotBlank(password)) {
					final int passwordLengthInt = getIntFromObject(passwordLength);
					if (passwordLengthInt > 0) {
						config.setPasswordLength(passwordLengthInt);
						config.setPassword(config.getMaskedPassword());
					}
				}

				config.setTimeout(timeout);

				config.setHubProxyHost(proxyHost);
				config.setHubProxyPort(proxyPort);

				config.setHubNoProxyHosts(noProxyHosts);
				config.setHubProxyUser(proxyUser);

				if (StringUtils.isNotBlank(proxyPassword)) {
					final int hubProxyPasswordLength = getIntFromObject(proxyPasswordLength);
					if (hubProxyPasswordLength > 0) {
						config.setHubProxyPasswordLength(hubProxyPasswordLength);
						config.setHubProxyPassword(config.getMaskedProxyPassword());
					}
				}
				return config;
			}
		});

		return Response.ok(obj).build();
	}

	private int getIntFromObject(final String value) {
		if (StringUtils.isNotBlank(value)) {
			return NumberUtils.toInt(value);
		}
		return 0;
	}

	@PUT
	@Consumes(MediaType.APPLICATION_JSON)
	public Response put(final HubServerConfigSerializable config, @Context final HttpServletRequest request) {
		final String username = userManager.getRemoteUsername(request);
		if (username == null || !userManager.isSystemAdmin(username)) {
			return Response.status(Status.UNAUTHORIZED).build();
		}

		transactionTemplate.execute(new TransactionCallback() {
			@Override
			public Object doInTransaction() {
				final PluginSettings settings = pluginSettingsFactory.createGlobalSettings();
				setValue(settings, HubConfigKeys.CONFIG_HUB_URL, config.getHubUrl());
				setValue(settings, HubConfigKeys.CONFIG_HUB_USER, config.getUsername());

				if (!config.isPasswordMasked()) {
					// only update the stored password if it is not the masked
					// password used for display
					final String password = config.getPassword();
					if (StringUtils.isNotBlank(password) && config.getPasswordLength() == 0) {
						try {
							final String encPassword = PasswordEncrypter.encrypt(password);

							setValue(settings, HubConfigKeys.CONFIG_HUB_PASS, encPassword);
							setValue(settings, HubConfigKeys.CONFIG_HUB_PASS_LENGTH, String.valueOf(password.length()));
						} catch (IllegalArgumentException | EncryptionException e) {
						}
					}
				}
				setValue(settings, HubConfigKeys.CONFIG_HUB_TIMEOUT, String.valueOf(config.getTimeout()));
				setValue(settings, HubConfigKeys.CONFIG_PROXY_HOST, config.getHubProxyHost());
				setValue(settings, HubConfigKeys.CONFIG_PROXY_PORT, String.valueOf(config.getHubProxyPort()));
				setValue(settings, HubConfigKeys.CONFIG_PROXY_NO_HOST, config.getHubNoProxyHosts());
				setValue(settings, HubConfigKeys.CONFIG_PROXY_USER, config.getHubProxyUser());

				String proxyPassword = config.getHubProxyPassword();
				if (StringUtils.isNotBlank(proxyPassword) && !config.isProxyPasswordMasked()) {
					// only update the stored password if it is not the masked
					// password used for display
					try {
						proxyPassword = PasswordEncrypter.encrypt(proxyPassword);

						setValue(settings, HubConfigKeys.CONFIG_PROXY_PASS, proxyPassword);
						setValue(settings, HubConfigKeys.CONFIG_PROXY_PASS_LENGTH,
								String.valueOf(proxyPassword.length()));
					} catch (IllegalArgumentException | EncryptionException e) {
					}
				}
				return null;
			}
		});
		return Response.noContent().build();
	}

	private String getValue(final PluginSettings settings, final String key) {
		return (String) settings.get(key);
	}

	private void setValue(final PluginSettings settings, final String key, final Object value) {
		settings.put(key, String.valueOf(value));
	}

}
