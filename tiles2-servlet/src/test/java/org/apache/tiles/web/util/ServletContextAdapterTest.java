/*
 * $Id$
 *
 * Licensed to the Apache Software Foundation (ASF) under one
 * or more contributor license agreements.  See the NOTICE file
 * distributed with this work for additional information
 * regarding copyright ownership.  The ASF licenses this file
 * to you under the Apache License, Version 2.0 (the
 * "License"); you may not use this file except in compliance
 * with the License.  You may obtain a copy of the License at
 *
 * http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing,
 * software distributed under the License is distributed on an
 * "AS IS" BASIS, WITHOUT WARRANTIES OR CONDITIONS OF ANY
 * KIND, either express or implied.  See the License for the
 * specific language governing permissions and limitations
 * under the License.
 */

package org.apache.tiles.web.util;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.util.Enumeration;
import java.util.HashSet;
import java.util.Set;
import org.apache.tiles.servlet.mock.MockServletConfig;
import org.apache.tiles.servlet.mock.MockServletContext;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

/**
 * Tests {@link ServletContextAdapter}.
 *
 * @version $Rev$ $Date$
 */
public class ServletContextAdapterTest {

    /**
     * The context to test.
     */
    private ServletContextAdapter context;

    /**
     * Method is executed <i>before</i> <b>each</b> {@code @Test} method in the current class.
     */
    @BeforeEach
    public void setUp() throws Exception {
        MockServletContext rootContext = new MockServletContext();
        rootContext.addInitParameter("initParameter1", "parameterValue1");
        rootContext.addInitParameter("initParameter2", "parameterValue2");
        MockServletConfig config = new MockServletConfig(rootContext);
        config.addInitParameter("initParameter1", "newParameterValue1");
        config.addInitParameter("newInitParameter", "newParameterValue2");
        context = new ServletContextAdapter(config);
    }

    /**
     * Test init parameters.
     */
    @Test
    public void testGetInitParameters() {
        assertEquals("newParameterValue1", context.getInitParameter("initParameter1"));
        assertEquals("parameterValue2", context.getInitParameter("initParameter2"));
        assertEquals("newParameterValue2", context.getInitParameter("newInitParameter"));

        Set<String> paramSet = new HashSet<String>();
        paramSet.add("initParameter1");
        paramSet.add("initParameter2");
        paramSet.add("newInitParameter");
        Enumeration<?> names = context.getInitParameterNames();
        while (names.hasMoreElements()) {
            String name = names.nextElement().toString();
            assertTrue(paramSet.contains(name));
            paramSet.remove(name);
        }

        assertTrue(paramSet.isEmpty());
    }

}
