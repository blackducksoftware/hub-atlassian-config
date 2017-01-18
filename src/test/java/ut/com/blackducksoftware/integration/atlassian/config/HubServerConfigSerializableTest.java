/**
 * Hub Atlassian Config
 *
 * Copyright (C) 2017 Black Duck Software, Inc.
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
 */
package ut.com.blackducksoftware.integration.atlassian.config;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import org.junit.Test;

import com.blackducksoftware.integration.atlassian.config.HubServerConfigSerializable;

import nl.jqno.equalsverifier.EqualsVerifier;
import nl.jqno.equalsverifier.Warning;

public class HubServerConfigSerializableTest {

    @Test
    public void testHubServerConfigSerializable() {
        final String string1 = "testString1";
        final int int1 = 1;
        final String string2 = "testString2";
        final int int2 = 2;

        final HubServerConfigSerializable item1 = new HubServerConfigSerializable();
        item1.setHubUrl(string1);
        item1.setTimeout(string1);
        item1.setUsername(string1);
        item1.setPassword(string1);
        item1.setPasswordLength(int1);
        item1.setHubProxyHost(string1);
        item1.setHubProxyPort(string1);
        item1.setHubNoProxyHosts(string1);
        item1.setHubProxyUser(string1);
        item1.setHubProxyPassword(string1);
        item1.setHubProxyPasswordLength(int1);
        item1.setHubUrlError(string1);
        item1.setTimeoutError(string1);
        item1.setUsernameError(string1);
        item1.setPasswordError(string1);
        item1.setHubProxyHostError(string1);
        item1.setHubProxyPortError(string1);
        item1.setHubNoProxyHostsError(string1);
        item1.setHubProxyUserError(string1);
        item1.setHubProxyPasswordError(string1);
        item1.setTestConnectionError(string1);

        final HubServerConfigSerializable item2 = new HubServerConfigSerializable();
        item2.setHubUrl(string2);
        item2.setTimeout(string2);
        item2.setUsername(string2);
        item2.setPassword(string2);
        item2.setPasswordLength(int2);
        item2.setHubProxyHost(string2);
        item2.setHubProxyPort(string2);
        item2.setHubNoProxyHosts(string2);
        item2.setHubProxyUser(string2);
        item2.setHubProxyPassword(string2);
        item2.setHubProxyPasswordLength(int2);
        item2.setHubUrlError(string2);
        item2.setTimeoutError(string2);
        item2.setUsernameError(string2);
        item2.setPasswordError(string2);
        item2.setHubProxyHostError(string2);
        item2.setHubProxyPortError(string2);
        item2.setHubNoProxyHostsError(string2);
        item2.setHubProxyUserError(string2);
        item2.setHubProxyPasswordError(string2);
        item2.setTestConnectionError(string2);

        final HubServerConfigSerializable item3 = new HubServerConfigSerializable();
        item3.setHubUrl(string1);
        item3.setTimeout(string1);
        item3.setUsername(string1);
        item3.setPassword(string1);
        item3.setPasswordLength(int1);
        item3.setHubProxyHost(string1);
        item3.setHubProxyPort(string1);
        item3.setHubNoProxyHosts(string1);
        item3.setHubProxyUser(string1);
        item3.setHubProxyPassword(string1);
        item3.setHubProxyPasswordLength(int1);
        item3.setHubUrlError(string1);
        item3.setTimeoutError(string1);
        item3.setUsernameError(string1);
        item3.setPasswordError(string1);
        item3.setHubProxyHostError(string1);
        item3.setHubProxyPortError(string1);
        item3.setHubNoProxyHostsError(string1);
        item3.setHubProxyUserError(string1);
        item3.setHubProxyPasswordError(string1);
        item3.setTestConnectionError(string1);

        final HubServerConfigSerializable item4 = new HubServerConfigSerializable();

        assertEquals(string1, item1.getHubUrl());
        assertEquals(string1, item1.getTimeout());
        assertEquals(string1, item1.getUsername());
        assertEquals(string1, item1.getPassword());
        assertEquals(int1, item1.getPasswordLength());
        assertEquals(string1, item1.getHubProxyHost());
        assertEquals(string1, item1.getHubProxyPort());
        assertEquals(string1, item1.getHubNoProxyHosts());
        assertEquals(string1, item1.getHubProxyUser());
        assertEquals(string1, item1.getHubProxyPassword());
        assertEquals(int1, item1.getHubProxyPasswordLength());
        assertEquals(string1, item1.getHubUrlError());
        assertEquals(string1, item1.getTimeoutError());
        assertEquals(string1, item1.getUsernameError());
        assertEquals(string1, item1.getPasswordError());
        assertEquals(string1, item1.getHubProxyHostError());
        assertEquals(string1, item1.getHubProxyPortError());
        assertEquals(string1, item1.getHubNoProxyHostsError());
        assertEquals(string1, item1.getHubProxyUserError());
        assertEquals(string1, item1.getHubProxyPasswordError());
        assertEquals(string1, item1.getTestConnectionError());

        assertEquals(string2, item2.getHubUrl());
        assertEquals(string2, item2.getTimeout());
        assertEquals(string2, item2.getUsername());
        assertEquals(string2, item2.getPassword());
        assertEquals(int2, item2.getPasswordLength());
        assertEquals(string2, item2.getHubProxyHost());
        assertEquals(string2, item2.getHubProxyPort());
        assertEquals(string2, item2.getHubNoProxyHosts());
        assertEquals(string2, item2.getHubProxyUser());
        assertEquals(string2, item2.getHubProxyPassword());
        assertEquals(int2, item2.getHubProxyPasswordLength());
        assertEquals(string2, item2.getHubUrlError());
        assertEquals(string2, item2.getTimeoutError());
        assertEquals(string2, item2.getUsernameError());
        assertEquals(string2, item2.getPasswordError());
        assertEquals(string2, item2.getHubProxyHostError());
        assertEquals(string2, item2.getHubProxyPortError());
        assertEquals(string2, item2.getHubNoProxyHostsError());
        assertEquals(string2, item2.getHubProxyUserError());
        assertEquals(string2, item2.getHubProxyPasswordError());
        assertEquals(string2, item2.getTestConnectionError());

        assertTrue(item1.hasErrors());
        assertFalse(item4.hasErrors());

        assertTrue(item1.equals(item3));
        assertTrue(!item1.equals(item2));

        EqualsVerifier.forClass(HubServerConfigSerializable.class).suppress(Warning.NONFINAL_FIELDS)
                .suppress(Warning.STRICT_INHERITANCE).verify();

        assertTrue(item1.hashCode() != item2.hashCode());
        assertEquals(item1.hashCode(), item3.hashCode());

        final StringBuilder builder = new StringBuilder();
        builder.append("HubServerConfigSerializable [testConnectionError=");
        builder.append(item1.getTestConnectionError());
        builder.append(", hubUrl=");
        builder.append(item1.getHubUrl());
        builder.append(", hubUrlError=");
        builder.append(item1.getHubUrlError());
        builder.append(", timeout=");
        builder.append(item1.getTimeout());
        builder.append(", timeoutError=");
        builder.append(item1.getTimeoutError());
        builder.append(", username=");
        builder.append(item1.getUsername());
        builder.append(", usernameError=");
        builder.append(item1.getUsernameError());
        builder.append(", password=");
        builder.append(item1.getPassword());
        builder.append(", passwordError=");
        builder.append(item1.getPasswordError());
        builder.append(", passwordLength=");
        builder.append(item1.getPasswordLength());
        builder.append(", hubProxyHost=");
        builder.append(item1.getHubProxyHost());
        builder.append(", hubProxyHostError=");
        builder.append(item1.getHubProxyHostError());
        builder.append(", hubProxyPort=");
        builder.append(item1.getHubProxyPort());
        builder.append(", hubProxyPortError=");
        builder.append(item1.getHubProxyPortError());
        builder.append(", hubNoProxyHosts=");
        builder.append(item1.getHubNoProxyHosts());
        builder.append(", hubNoProxyHostsError=");
        builder.append(item1.getHubNoProxyHostsError());
        builder.append(", hubProxyUser=");
        builder.append(item1.getHubProxyUser());
        builder.append(", hubProxyUserError=");
        builder.append(item1.getHubProxyUserError());
        builder.append(", hubProxyPassword=");
        builder.append(item1.getHubProxyPassword());
        builder.append(", hubProxyPasswordError=");
        builder.append(item1.getHubProxyPasswordError());
        builder.append(", hubProxyPasswordLength=");
        builder.append(item1.getHubProxyPasswordLength());
        builder.append("]");
        assertEquals(builder.toString(), item1.toString());

    }
}
