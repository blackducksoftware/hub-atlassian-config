package com.blackducksoftware.integration.atlassian;

import java.util.Arrays;

import org.apache.commons.lang3.StringUtils;

public class HubServerConfig {

	public String hubUrl;
	public int timeout;
	public String username;
	public String password;
	public int passwordLength;

	public String hubProxyHost;
	public int hubProxyPort;
	public String hubNoProxyHosts;
	public String hubProxyUser;
	public String hubProxyPassword;
	public int hubProxyPasswordLength;

	public HubServerConfig() {

	}

	public String getHubUrl() {
		return hubUrl;
	}

	public int getTimeout() {
		return timeout;
	}

	public String getUsername() {
		return username;
	}

	public String getPassword() {
		return password;
	}

	public String getMaskedPassword() {
		return getMaskedString(passwordLength);
	}

	public boolean isPasswordMasked() {
		return isStringMasked(password);
	}

	public int getPasswordLength() {
		return passwordLength;
	}

	public String getHubProxyHost() {
		return hubProxyHost;
	}

	public int getHubProxyPort() {
		return hubProxyPort;
	}

	public String getHubNoProxyHosts() {
		return hubNoProxyHosts;
	}

	public String getHubProxyUser() {
		return hubProxyUser;
	}

	public String getHubProxyPassword() {
		return hubProxyPassword;
	}

	public String getMaskedProxyPassword() {
		return getMaskedString(hubProxyPasswordLength);
	}

	public boolean isProxyPasswordMasked() {
		return isStringMasked(hubProxyPassword);
	}

	public int getHubProxyPasswordLength() {
		return hubProxyPasswordLength;
	}

	public void setHubUrl(final String hubUrl) {
		this.hubUrl = hubUrl;
	}

	public void setTimeout(final int timeout) {
		this.timeout = timeout;
	}

	public void setUsername(final String username) {
		this.username = username;
	}

	public void setPassword(final String password) {
		this.password = password;
	}

	public void setPasswordLength(final int passwordLength) {
		this.passwordLength = passwordLength;
	}

	public void setHubProxyHost(final String hubProxyHost) {
		this.hubProxyHost = hubProxyHost;
	}

	public void setHubProxyPort(final int hubProxyPort) {
		this.hubProxyPort = hubProxyPort;
	}

	public void setHubNoProxyHosts(final String hubNoProxyHosts) {
		this.hubNoProxyHosts = hubNoProxyHosts;
	}

	public void setHubProxyUser(final String hubProxyUser) {
		this.hubProxyUser = hubProxyUser;
	}

	public void setHubProxyPassword(final String hubProxyPassword) {
		this.hubProxyPassword = hubProxyPassword;
	}

	public void setHubProxyPasswordLength(final int hubProxyPasswordLength) {
		this.hubProxyPasswordLength = hubProxyPasswordLength;
	}

	private String getMaskedString(final int length) {
		if (length == 0) {
			return null;
		}
		final char[] array = new char[length];
		Arrays.fill(array, '*');
		return new String(array);
	}

	private boolean isStringMasked(final String value) {
		if (StringUtils.isBlank(value)) {
			return false;
		}
		final String masked = getMaskedString(value.length());
		if (value.equals(masked)) {
			return true;
		}
		return false;
	}
}
