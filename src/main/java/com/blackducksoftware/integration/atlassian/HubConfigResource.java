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
package com.blackducksoftware.integration.atlassian;

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

import com.atlassian.sal.api.pluginsettings.PluginSettings;
import com.atlassian.sal.api.pluginsettings.PluginSettingsFactory;
import com.atlassian.sal.api.transaction.TransactionCallback;
import com.atlassian.sal.api.transaction.TransactionTemplate;
import com.atlassian.sal.api.user.UserManager;
import com.blackducksoftware.integration.hub.encryption.PasswordEncrypter;
import com.blackducksoftware.integration.hub.exception.EncryptionException;

@Path("/")
public class HubConfigResource {
	private final UserManager userManager;
	private final PluginSettingsFactory pluginSettingsFactory;
	private final TransactionTemplate transactionTemplate;

	public HubConfigResource(final UserManager userManager, final PluginSettingsFactory pluginSettingsFactory,
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

		return Response.ok(transactionTemplate.execute(new TransactionCallback() {
			@Override
			public Object doInTransaction() {
				final PluginSettings settings = pluginSettingsFactory.createGlobalSettings();

				final HubServerConfig config = new HubServerConfig();
				config.setHubUrl((String) settings.get(HubConfigKeys.CONFIG_HUB_URL));
				config.setUsername((String) settings.get(HubConfigKeys.CONFIG_HUB_USER));
				final String hubPassword = (String) settings.get(HubConfigKeys.CONFIG_HUB_PASS);

				if (StringUtils.isNotBlank(hubPassword)) {
					final int hubPasswordLength = getIntFromObject(settings.get(HubConfigKeys.CONFIG_HUB_PASS_LENGTH));
					if (hubPasswordLength > 0) {
						config.setPasswordLength(hubPasswordLength);
						config.setPassword(config.getMaskedPassword());
					}
				}

				config.setTimeout(getIntFromObject(settings.get(HubConfigKeys.CONFIG_HUB_TIMEOUT)));

				config.setHubProxyHost((String) settings.get(HubConfigKeys.CONFIG_PROXY_HOST));
				config.setHubProxyPort(getIntFromObject(settings.get(HubConfigKeys.CONFIG_PROXY_PORT)));
				config.setHubNoProxyHosts((String) settings.get(HubConfigKeys.CONFIG_PROXY_NO_HOST));
				config.setHubProxyUser((String) settings.get(HubConfigKeys.CONFIG_PROXY_USER));
				final String hubProxyPassword = (String) settings.get(HubConfigKeys.CONFIG_PROXY_PASS);

				if (StringUtils.isNotBlank(hubProxyPassword)) {
					final int hubProxyPasswordLength = getIntFromObject(
							settings.get(HubConfigKeys.CONFIG_PROXY_PASS_LENGTH));
					if (hubProxyPasswordLength > 0) {
						config.setHubProxyPasswordLength(hubProxyPasswordLength);
						config.setHubProxyPassword(config.getMaskedProxyPassword());
					}

					try {
						config.setHubProxyPassword(PasswordEncrypter.encrypt(hubProxyPassword));
						config.setHubProxyPasswordLength(hubProxyPassword.length());
					} catch (IllegalArgumentException | EncryptionException e) {
						e.printStackTrace();
					}
				}
				return config;
			}
		})).build();
	}

	private int getIntFromObject(final Object value){
		final String valueString = (String) value;
		if (StringUtils.isNotBlank(valueString)) {
			return Integer.valueOf(valueString);
		}
		return 0;
	}

	@PUT
	@Consumes(MediaType.APPLICATION_JSON)
	public Response put(final HubServerConfig config, @Context final HttpServletRequest request) {
		final String username = userManager.getRemoteUsername(request);
		if (username == null || !userManager.isSystemAdmin(username)) {
			return Response.status(Status.UNAUTHORIZED).build();
		}

		transactionTemplate.execute(new TransactionCallback() {
			@Override
			public Object doInTransaction() {
				final PluginSettings pluginSettings = pluginSettingsFactory.createGlobalSettings();
				pluginSettings.put(HubConfigKeys.CONFIG_HUB_URL, config.getHubUrl());
				pluginSettings.put(HubConfigKeys.CONFIG_HUB_USER, config.getUsername());

				if (!config.isPasswordMasked()) {
					final String password = config.getPassword();

					if (StringUtils.isNotBlank(password) && config.getPasswordLength() == 0) {
						try {
							final String encPassword = PasswordEncrypter.encrypt(password);
							pluginSettings.put(HubConfigKeys.CONFIG_HUB_PASS, encPassword);
							pluginSettings.put(HubConfigKeys.CONFIG_HUB_PASS_LENGTH, String.valueOf(password.length()));
						} catch (IllegalArgumentException | EncryptionException e) {
						}
					}
				}
				pluginSettings.put(HubConfigKeys.CONFIG_HUB_TIMEOUT, String.valueOf(config.getTimeout()));
				pluginSettings.put(HubConfigKeys.CONFIG_PROXY_HOST, config.getHubProxyHost());
				pluginSettings.put(HubConfigKeys.CONFIG_PROXY_PORT, String.valueOf(config.getHubProxyPort()));
				pluginSettings.put(HubConfigKeys.CONFIG_PROXY_NO_HOST, config.getHubNoProxyHosts());
				pluginSettings.put(HubConfigKeys.CONFIG_PROXY_USER, config.getUsername());

				if (!config.isProxyPasswordMasked()) {
					String proxyPassword = config.getHubProxyPassword();
					if (StringUtils.isNotBlank(proxyPassword) && config.getHubProxyPasswordLength() == 0) {
						try {
							proxyPassword = PasswordEncrypter.encrypt(proxyPassword);

							pluginSettings.put(HubConfigKeys.CONFIG_PROXY_PASS, proxyPassword);
							pluginSettings.put(HubConfigKeys.CONFIG_PROXY_PASS_LENGTH,
									String.valueOf(proxyPassword.length()));
						} catch (IllegalArgumentException | EncryptionException e) {
						}
					}
				}
				return null;
			}
		});
		return Response.noContent().build();
	}

}
