package com.blackducksoftware.integration.atlassian.mocks;

import com.atlassian.sal.api.pluginsettings.PluginSettings;
import com.atlassian.sal.api.pluginsettings.PluginSettingsFactory;

public class PluginSettingsFactoryMock implements PluginSettingsFactory {

	PluginSettings settings = new PluginSettingsMock();

	@Override
	public PluginSettings createSettingsForKey(final String key) {
		return null;
	}

	@Override
	public PluginSettings createGlobalSettings() {
		return settings;
	}

}
