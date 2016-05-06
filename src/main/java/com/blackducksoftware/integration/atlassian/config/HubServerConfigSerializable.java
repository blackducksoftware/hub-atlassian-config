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

import java.io.Serializable;
import java.util.Arrays;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

import org.apache.commons.lang3.StringUtils;

@XmlRootElement
@XmlAccessorType(XmlAccessType.FIELD)
public class HubServerConfigSerializable implements Serializable {

	private static final long serialVersionUID = -6619125551471497995L;

	@XmlElement
	private String hubUrl;

	@XmlElement
	private String hubUrlError;

	@XmlElement
	private String timeout;

	@XmlElement
	private String timeoutError;

	@XmlElement
	private String username;

	@XmlElement
	private String usernameError;

	@XmlElement
	private String password;

	@XmlElement
	private String passwordError;

	private int passwordLength;

	@XmlElement
	private String hubProxyHost;

	@XmlElement
	private String hubProxyHostError;

	@XmlElement
	private String hubProxyPort;

	@XmlElement
	private String hubProxyPortError;

	@XmlElement
	private String hubNoProxyHosts;

	@XmlElement
	private String hubNoProxyHostsError;

	@XmlElement
	private String hubProxyUser;

	@XmlElement
	private String hubProxyUserError;

	@XmlElement
	private String hubProxyPassword;

	@XmlElement
	private String hubProxyPasswordError;


	private int hubProxyPasswordLength;

	public HubServerConfigSerializable() {

	}

	public String getMaskedPassword() {
		return getMaskedString(passwordLength);
	}

	public boolean isPasswordMasked() {
		return isStringMasked(password);
	}

	public String getMaskedProxyPassword() {
		return getMaskedString(hubProxyPasswordLength);
	}

	public boolean isProxyPasswordMasked() {
		return isStringMasked(hubProxyPassword);
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


	public String getHubUrl() {
		return hubUrl;
	}

	public void setHubUrl(final String hubUrl) {
		this.hubUrl = hubUrl;
	}


	public String getHubUrlError() {
		return hubUrlError;
	}

	public void setHubUrlError(final String hubUrlError) {
		this.hubUrlError = hubUrlError;
	}


	public String getTimeout() {
		return timeout;
	}

	public void setTimeout(final String timeout) {
		this.timeout = timeout;
	}


	public String getTimeoutError() {
		return timeoutError;
	}

	public void setTimeoutError(final String timeoutError) {
		this.timeoutError = timeoutError;
	}


	public String getUsername() {
		return username;
	}

	public void setUsername(final String username) {
		this.username = username;
	}


	public String getUsernameError() {
		return usernameError;
	}

	public void setUsernameError(final String usernameError) {
		this.usernameError = usernameError;
	}


	public String getPassword() {
		return password;
	}

	public void setPassword(final String password) {
		this.password = password;
	}


	public String getPasswordError() {
		return passwordError;
	}

	public void setPasswordError(final String passwordError) {
		this.passwordError = passwordError;
	}

	public int getPasswordLength() {
		return passwordLength;
	}

	public void setPasswordLength(final int passwordLength) {
		this.passwordLength = passwordLength;
	}


	public String getHubProxyHost() {
		return hubProxyHost;
	}

	public void setHubProxyHost(final String hubProxyHost) {
		this.hubProxyHost = hubProxyHost;
	}


	public String getHubProxyHostError() {
		return hubProxyHostError;
	}

	public void setHubProxyHostError(final String hubProxyHostError) {
		this.hubProxyHostError = hubProxyHostError;
	}


	public String getHubProxyPort() {
		return hubProxyPort;
	}

	public void setHubProxyPort(final String hubProxyPort) {
		this.hubProxyPort = hubProxyPort;
	}


	public String getHubProxyPortError() {
		return hubProxyPortError;
	}

	public void setHubProxyPortError(final String hubProxyPortError) {
		this.hubProxyPortError = hubProxyPortError;
	}


	public String getHubNoProxyHosts() {
		return hubNoProxyHosts;
	}

	public void setHubNoProxyHosts(final String hubNoProxyHosts) {
		this.hubNoProxyHosts = hubNoProxyHosts;
	}


	public String getHubNoProxyHostsError() {
		return hubNoProxyHostsError;
	}

	public void setHubNoProxyHostsError(final String hubNoProxyHostsError) {
		this.hubNoProxyHostsError = hubNoProxyHostsError;
	}


	public String getHubProxyUser() {
		return hubProxyUser;
	}

	public void setHubProxyUser(final String hubProxyUser) {
		this.hubProxyUser = hubProxyUser;
	}


	public String getHubProxyUserError() {
		return hubProxyUserError;
	}

	public void setHubProxyUserError(final String hubProxyUserError) {
		this.hubProxyUserError = hubProxyUserError;
	}


	public String getHubProxyPassword() {
		return hubProxyPassword;
	}

	public void setHubProxyPassword(final String hubProxyPassword) {
		this.hubProxyPassword = hubProxyPassword;
	}


	public String getHubProxyPasswordError() {
		return hubProxyPasswordError;
	}

	public void setHubProxyPasswordError(final String hubProxyPasswordError) {
		this.hubProxyPasswordError = hubProxyPasswordError;
	}

	public int getHubProxyPasswordLength() {
		return hubProxyPasswordLength;
	}

	public void setHubProxyPasswordLength(final int hubProxyPasswordLength) {
		this.hubProxyPasswordLength = hubProxyPasswordLength;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((hubNoProxyHosts == null) ? 0 : hubNoProxyHosts.hashCode());
		result = prime * result + ((hubNoProxyHostsError == null) ? 0 : hubNoProxyHostsError.hashCode());
		result = prime * result + ((hubProxyHost == null) ? 0 : hubProxyHost.hashCode());
		result = prime * result + ((hubProxyHostError == null) ? 0 : hubProxyHostError.hashCode());
		result = prime * result + ((hubProxyPassword == null) ? 0 : hubProxyPassword.hashCode());
		result = prime * result + ((hubProxyPasswordError == null) ? 0 : hubProxyPasswordError.hashCode());
		result = prime * result + hubProxyPasswordLength;
		result = prime * result + ((hubProxyPort == null) ? 0 : hubProxyPort.hashCode());
		result = prime * result + ((hubProxyPortError == null) ? 0 : hubProxyPortError.hashCode());
		result = prime * result + ((hubProxyUser == null) ? 0 : hubProxyUser.hashCode());
		result = prime * result + ((hubProxyUserError == null) ? 0 : hubProxyUserError.hashCode());
		result = prime * result + ((hubUrl == null) ? 0 : hubUrl.hashCode());
		result = prime * result + ((hubUrlError == null) ? 0 : hubUrlError.hashCode());
		result = prime * result + ((password == null) ? 0 : password.hashCode());
		result = prime * result + ((passwordError == null) ? 0 : passwordError.hashCode());
		result = prime * result + passwordLength;
		result = prime * result + ((timeout == null) ? 0 : timeout.hashCode());
		result = prime * result + ((timeoutError == null) ? 0 : timeoutError.hashCode());
		result = prime * result + ((username == null) ? 0 : username.hashCode());
		result = prime * result + ((usernameError == null) ? 0 : usernameError.hashCode());
		return result;
	}

	@Override
	public boolean equals(final Object obj) {
		if (this == obj) {
			return true;
		}
		if (obj == null) {
			return false;
		}
		if (!(obj instanceof HubServerConfigSerializable)) {
			return false;
		}
		final HubServerConfigSerializable other = (HubServerConfigSerializable) obj;
		if (hubNoProxyHosts == null) {
			if (other.hubNoProxyHosts != null) {
				return false;
			}
		} else if (!hubNoProxyHosts.equals(other.hubNoProxyHosts)) {
			return false;
		}
		if (hubNoProxyHostsError == null) {
			if (other.hubNoProxyHostsError != null) {
				return false;
			}
		} else if (!hubNoProxyHostsError.equals(other.hubNoProxyHostsError)) {
			return false;
		}
		if (hubProxyHost == null) {
			if (other.hubProxyHost != null) {
				return false;
			}
		} else if (!hubProxyHost.equals(other.hubProxyHost)) {
			return false;
		}
		if (hubProxyHostError == null) {
			if (other.hubProxyHostError != null) {
				return false;
			}
		} else if (!hubProxyHostError.equals(other.hubProxyHostError)) {
			return false;
		}
		if (hubProxyPassword == null) {
			if (other.hubProxyPassword != null) {
				return false;
			}
		} else if (!hubProxyPassword.equals(other.hubProxyPassword)) {
			return false;
		}
		if (hubProxyPasswordError == null) {
			if (other.hubProxyPasswordError != null) {
				return false;
			}
		} else if (!hubProxyPasswordError.equals(other.hubProxyPasswordError)) {
			return false;
		}
		if (hubProxyPasswordLength != other.hubProxyPasswordLength) {
			return false;
		}
		if (hubProxyPort == null) {
			if (other.hubProxyPort != null) {
				return false;
			}
		} else if (!hubProxyPort.equals(other.hubProxyPort)) {
			return false;
		}
		if (hubProxyPortError == null) {
			if (other.hubProxyPortError != null) {
				return false;
			}
		} else if (!hubProxyPortError.equals(other.hubProxyPortError)) {
			return false;
		}
		if (hubProxyUser == null) {
			if (other.hubProxyUser != null) {
				return false;
			}
		} else if (!hubProxyUser.equals(other.hubProxyUser)) {
			return false;
		}
		if (hubProxyUserError == null) {
			if (other.hubProxyUserError != null) {
				return false;
			}
		} else if (!hubProxyUserError.equals(other.hubProxyUserError)) {
			return false;
		}
		if (hubUrl == null) {
			if (other.hubUrl != null) {
				return false;
			}
		} else if (!hubUrl.equals(other.hubUrl)) {
			return false;
		}
		if (hubUrlError == null) {
			if (other.hubUrlError != null) {
				return false;
			}
		} else if (!hubUrlError.equals(other.hubUrlError)) {
			return false;
		}
		if (password == null) {
			if (other.password != null) {
				return false;
			}
		} else if (!password.equals(other.password)) {
			return false;
		}
		if (passwordError == null) {
			if (other.passwordError != null) {
				return false;
			}
		} else if (!passwordError.equals(other.passwordError)) {
			return false;
		}
		if (passwordLength != other.passwordLength) {
			return false;
		}
		if (timeout == null) {
			if (other.timeout != null) {
				return false;
			}
		} else if (!timeout.equals(other.timeout)) {
			return false;
		}
		if (timeoutError == null) {
			if (other.timeoutError != null) {
				return false;
			}
		} else if (!timeoutError.equals(other.timeoutError)) {
			return false;
		}
		if (username == null) {
			if (other.username != null) {
				return false;
			}
		} else if (!username.equals(other.username)) {
			return false;
		}
		if (usernameError == null) {
			if (other.usernameError != null) {
				return false;
			}
		} else if (!usernameError.equals(other.usernameError)) {
			return false;
		}
		return true;
	}


}
