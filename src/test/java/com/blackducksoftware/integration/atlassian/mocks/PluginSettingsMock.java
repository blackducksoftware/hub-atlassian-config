package com.blackducksoftware.integration.atlassian.mocks;

import java.util.HashMap;

import com.atlassian.sal.api.pluginsettings.PluginSettings;

public class PluginSettingsMock implements PluginSettings {

	private final HashMap<String, Object> settings = new HashMap<String, Object>();

	@Override
	public Object get(final String key) {
		return settings.get(key);
	}

	@Override
	public Object put(final String key, final Object value) {
		return settings.put(key, value);
	}

	@Override
	public Object remove(final String key) {
		return settings.remove(key);
	}

}
