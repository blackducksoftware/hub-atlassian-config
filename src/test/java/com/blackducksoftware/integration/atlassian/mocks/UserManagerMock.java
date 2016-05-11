package com.blackducksoftware.integration.atlassian.mocks;

import java.security.Principal;

import javax.servlet.http.HttpServletRequest;

import com.atlassian.sal.api.user.UserManager;
import com.atlassian.sal.api.user.UserResolutionException;

public class UserManagerMock implements UserManager {

	String remoteUsername;
	boolean isSystemAdmin;

	public UserManagerMock() {
	}

	public void setRemoteUsername(final String remoteUsername) {
		this.remoteUsername = remoteUsername;
	}

	@Override
	public String getRemoteUsername() {
		return remoteUsername;
	}

	@Override
	public String getRemoteUsername(final HttpServletRequest request) {
		return remoteUsername;
	}

	@Override
	public boolean isUserInGroup(final String username, final String group) {
		return false;
	}

	public void setIsSystemAdmin(final boolean isSystemAdmin) {
		this.isSystemAdmin = isSystemAdmin;
	}

	@Override
	public boolean isSystemAdmin(final String username) {
		return isSystemAdmin;
	}

	@Override
	public boolean authenticate(final String username, final String password) {
		return false;
	}

	@Override
	public Principal resolve(final String username) throws UserResolutionException {
		return null;
	}

}
