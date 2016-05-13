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