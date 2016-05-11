package com.blackducksoftware.integration.atlassian.mocks;

import java.net.URI;

import com.atlassian.sal.api.auth.LoginUriProvider;

public class LoginUriProviderMock implements LoginUriProvider {


	public LoginUriProviderMock() {
	}

	@Override
	public URI getLoginUri(final URI returnUri) {
		return returnUri;
	}

}
