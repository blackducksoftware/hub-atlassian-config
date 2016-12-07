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
package com.blackducksoftware.integration.atlassian;

import java.io.IOException;
import java.net.URI;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang3.StringUtils;
import org.restlet.engine.Engine;
import org.restlet.engine.connector.HttpClientHelper;

import com.atlassian.sal.api.auth.LoginUriProvider;
import com.atlassian.sal.api.pluginsettings.PluginSettings;
import com.atlassian.sal.api.pluginsettings.PluginSettingsFactory;
import com.atlassian.sal.api.user.UserManager;
import com.atlassian.templaterenderer.TemplateRenderer;
import com.blackducksoftware.integration.atlassian.utils.HubConfigKeys;

public class HubAdminServlet extends HttpServlet {

    private static final long serialVersionUID = 8293922701957754642L;

    private final UserManager userManager;

    private final LoginUriProvider loginUriProvider;

    private final TemplateRenderer renderer;

    private final PluginSettingsFactory pluginSettingsFactory;

    public HubAdminServlet(final UserManager userManager, final LoginUriProvider loginUriProvider,
            final TemplateRenderer renderer, PluginSettingsFactory pluginSettingsFactory) {
        this.userManager = userManager;
        this.loginUriProvider = loginUriProvider;
        this.renderer = renderer;
        this.pluginSettingsFactory = pluginSettingsFactory;

        initializeHttpClientHelper();
    }

    private void initializeHttpClientHelper() {
        // configure the Restlet engine so that the HTTPHandle and classes
        // from the com.sun.net.httpserver package
        // do not need to be used at runtime to make client calls.
        // DO NOT REMOVE THIS or the OSGI bundle will throw a
        // ClassNotFoundException for com.sun.net.httpserver.HttpHandler.
        // Since we are acting as a client we do not need the httpserver
        // components.

        // This workaround found here:
        // http://stackoverflow.com/questions/25179243/com-sun-net-httpserver-httphandler-classnotfound-exception-on-java-embedded-runt

        Engine.register(false);
        Engine.getInstance().getRegisteredClients().add(new HttpClientHelper(null));
    }

    private boolean isUserAuthorized(final HttpServletRequest request) {
        final String username = userManager.getRemoteUsername(request);
        if (username == null) {
            return false;
        }
        if (userManager.isSystemAdmin(username)) {
            return true;
        }

        final PluginSettings settings = pluginSettingsFactory.createGlobalSettings();
        final String hubJiraGroupsString = (String) settings.get(HubConfigKeys.HUB_CONFIG_GROUPS);

        if (StringUtils.isNotBlank(hubJiraGroupsString)) {
            final String[] hubJiraGroups = hubJiraGroupsString.split(",");
            boolean userIsInGroups = false;
            for (final String hubJiraGroup : hubJiraGroups) {
                if (userManager.isUserInGroup(username, hubJiraGroup.trim())) {
                    userIsInGroups = true;
                    break;
                }
            }
            if (userIsInGroups) {
                return true;
            }
        }
        return false;
    }

    @Override
    public void doGet(final HttpServletRequest request, final HttpServletResponse response)
            throws IOException, ServletException {
        if (!isUserAuthorized(request)) {
            redirectToLogin(request, response);
            return;
        }
        response.setContentType("text/html;charset=utf-8");
        renderer.render("hub-admin.vm", response.getWriter());
    }

    private void redirectToLogin(final HttpServletRequest request, final HttpServletResponse response)
            throws IOException {
        response.sendRedirect(loginUriProvider.getLoginUri(getUri(request)).toASCIIString());
    }

    private URI getUri(final HttpServletRequest request) {
        final StringBuffer builder = request.getRequestURL();
        if (request.getQueryString() != null) {
            builder.append("?");
            builder.append(request.getQueryString());
        }
        return URI.create(builder.toString());
    }

}
