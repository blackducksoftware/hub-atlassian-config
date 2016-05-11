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
package it.com.blackducksoftware.integration.atlassian;

import static org.junit.Assert.assertEquals;

import org.junit.Test;
import org.junit.runner.RunWith;

import com.atlassian.plugins.osgi.test.AtlassianPluginsTestRunner;
import com.atlassian.sal.api.ApplicationProperties;
import com.blackducksoftware.integration.atlassian.BdHubRefApp;

@RunWith(AtlassianPluginsTestRunner.class)
public class BdHubRefAppTest
{
	private final ApplicationProperties applicationProperties;
	private final BdHubRefApp bdHubRefApp;

	public BdHubRefAppTest(final ApplicationProperties applicationProperties, final BdHubRefApp bdHubRefApp)
	{
		this.applicationProperties = applicationProperties;
		this.bdHubRefApp = bdHubRefApp;
	}

	@Test
	public void testMyName()
	{
		assertEquals("names do not match!", "bdHubRefApp:" + applicationProperties.getDisplayName(),
				bdHubRefApp.getName());
	}
}