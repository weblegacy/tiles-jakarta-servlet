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

package org.apache.tiles.servlet.context;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertInstanceOf;
import static org.junit.jupiter.api.Assertions.assertSame;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.HashMap;
import java.util.Map;
import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.apache.tiles.TilesApplicationContext;
import org.apache.tiles.context.TilesRequestContext;
import org.apache.tiles.servlet.mock.MockHttpServletRequest;
import org.apache.tiles.servlet.mock.MockHttpServletResponse;
import org.apache.tiles.servlet.mock.MockHttpSession;
import org.apache.tiles.servlet.mock.MockServletContext;
import org.easymock.EasyMock;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

/**
 * Tests {@link ServletTilesRequestContext} behavior.
 *
 * @version $Rev$ $Date$
 */
public class ServletTilesRequestContextTest {

    /**
     * Test path to check forward and include.
     */
    private static final String TEST_PATH = "testPath.jsp";

    /**
     * The request context.
     */
    private TilesRequestContext context;

    /**
     * The servlet context.
     */
    private MockServletContext servletContext;

    /**
     * The Tiles application context.
     */
    private TilesApplicationContext applicationContext;

    /**
     * Method is executed <i>before</i> <b>each</b> {@code @Test} method in the current class.
     */
    @BeforeEach
    public void setUp() throws Exception {
        servletContext = new MockServletContext();
        applicationContext = EasyMock.createMock(TilesApplicationContext.class);
        Map<String, Object> applicationScope = new HashMap<String, Object>();
        applicationScope.put("applicationAttribute1", "applicationValue1");
        applicationScope.put("applicationAttribute2", "applicationValue2");
        EasyMock.expect(applicationContext.getApplicationScope()).andReturn(
                applicationScope);
        Map<String, String> initParams = new HashMap<String, String>();
        initParams.put("initParameter1", "initParameterValue1");
        EasyMock.expect(applicationContext.getInitParams()).andReturn(
                initParams);
        MockHttpSession session = new MockHttpSession(servletContext);
        MockHttpServletRequest request = new MockHttpServletRequest(session);
        request.addHeader("Content-Type", "text/html");
        request.addParameter("myParam", "value1");
        request.addParameter("myParam", "value2");

        MockHttpServletResponse response = new MockHttpServletResponse();
        context = new ServletTilesRequestContext(applicationContext, request,
                response);

        Map<String, Object> requestScope = context.getRequestScope();
        requestScope.put("attribute1", "value1");
        requestScope.put("attribute2", "value2");

        Map<String, Object> sessionScope = context.getSessionScope();
        sessionScope.put("sessionAttribute1", "sessionValue1");
        sessionScope.put("sessionAttribute2", "sessionValue2");
        EasyMock.replay(applicationContext);
    }

    /**
     * Tests getting the header.
     */
    @Test
    public void testGetHeader() {
        Map<String, String> map = context.getHeader();
        assertEquals("text/html", map.get("Content-Type"),
                "The header does not contain a set value");
        doTestReadMap(map, String.class, String.class, "header map");
    }

    /**
     * Tests getting the header value.
     */
    @Test
    public void testGetHeaderValues() {
        Map<String, String[]> map = context.getHeaderValues();
        String[] array = map.get("Content-Type");
        assertEquals(1, array.length, "The header-length is not one");
        assertEquals("text/html", array[0], "The header does not contain a set value");
        doTestReadMap(map, String.class, String[].class, "header values map");
    }

    /**
     * Tests getting the parameters.
     */
    @Test
    public void testGetParam() {
        Map<String, String> map = context.getParam();
        assertTrue("value1".equals(map.get("myParam")) || "value2".equals(map.get("myParam")),
                "The parameters do not contain a set value");
        doTestReadMap(map, String.class, String.class, "parameter map");
    }

    /**
     * Tests getting the parameter values.
     */
    @Test
    public void testGetParamValues() {
        Map<String, String[]> map = context.getParamValues();
        String[] array = map.get("myParam");
        assertEquals(2, array.length, "The parameter-length is not two");
        assertTrue(("value1".equals(array[0]) && "value2".equals(array[1]))
                || ("value1".equals(array[1]) && "value2".equals(array[0])),
                "The parameters not contain a set value");
        doTestReadMap(map, String.class, String[].class, "parameter values map");
    }

    /**
     * Tests getting request scope attributes.
     */
    @Test
    public void testGetRequestScope() {
        Map<String, Object> map = context.getRequestScope();
        assertEquals("value1", map.get("attribute1"),
                "The request scope does not contain a set value");
        assertEquals("value2", map.get("attribute2"),
                "The request scope does not contain a set value");
        doTestReadMap(map, String.class, Object.class, "request scope map");
    }

    /**
     * Tests getting session scope attributes.
     */
    @Test
    public void testGetSessionScope() {
        Map<String, Object> map = context.getSessionScope();
        assertEquals("sessionValue1", map.get("sessionAttribute1"),
                "The session scope does not contain a set value");
        assertEquals("sessionValue2", map.get("sessionAttribute2"),
                "The session scope does not contain a set value");
        doTestReadMap(map, String.class, Object.class, "session scope map");
    }

    /**
     * Tests {@link ServletTilesRequestContext#getApplicationContext()}.
     */
    @Test
    public void testGetApplicationContext() {
        assertSame(applicationContext, context.getApplicationContext(),
                "The objects are not the same");
    }

    /**
     * Tests getting application scope attributes.
     */
    @Test
    public void testGetApplicationScope() {
        Map<String, Object> map = ((TilesApplicationContext) context)
                .getApplicationScope();
        assertEquals("applicationValue1", map.get("applicationAttribute1"),
                "The application scope does not contain a set value");
        assertEquals("applicationValue2", map.get("applicationAttribute2"),
                "The application scope does not contain a set value");
        doTestReadMap(map, String.class, Object.class, "application scope map");
    }

    /**
     * Tests getting init parameters.
     */
    @Test
    public void testGetInitParams() {
        Map<String, String> map = ((TilesApplicationContext) context)
                .getInitParams();
        assertEquals("initParameterValue1", map.get("initParameter1"),
                "The init parameters do not contain a set value");
        doTestReadMap(map, String.class, String.class,
                "init parameters scope map");
    }

    /**
     * Tests {@link ServletTilesRequestContext#getOutputStream()}.
     *
     * @throws IOException If something goes wrong.
     */
    @Test
    public void testGetOutputStream() throws IOException {
        HttpServletRequest request = EasyMock
                .createMock(HttpServletRequest.class);
        HttpServletResponse response = EasyMock
                .createMock(HttpServletResponse.class);
        TilesApplicationContext applicationContext = EasyMock
                .createMock(TilesApplicationContext.class);
        ServletOutputStream os = EasyMock.createMock(ServletOutputStream.class);
        EasyMock.expect(response.getOutputStream()).andReturn(os);
        EasyMock.replay(request, response, applicationContext, os);
        ServletTilesRequestContext requestContext = new ServletTilesRequestContext(
                applicationContext, request, response);
        assertEquals(os, requestContext.getOutputStream());
        EasyMock.verify(request, response, applicationContext, os);
    }

    /**
     * Tests {@link ServletTilesRequestContext#getWriter()}.
     *
     * @throws IOException If something goes wrong.
     */
    @Test
    public void testGetWriter() throws IOException {
        HttpServletRequest request = EasyMock
                .createMock(HttpServletRequest.class);
        HttpServletResponse response = EasyMock
                .createMock(HttpServletResponse.class);
        TilesApplicationContext applicationContext = EasyMock
                .createMock(TilesApplicationContext.class);
        PrintWriter writer = EasyMock.createMock(PrintWriter.class);
        EasyMock.expect(response.getWriter()).andReturn(writer);
        EasyMock.replay(request, response, applicationContext, writer);
        ServletTilesRequestContext requestContext = new ServletTilesRequestContext(
                applicationContext, request, response);
        assertEquals(writer, requestContext.getWriter());
        EasyMock.verify(request, response, applicationContext, writer);
    }

    /**
     * Tests {@link ServletTilesRequestContext#getPrintWriter()}.
     *
     * @throws IOException If something goes wrong.
     */
    @Test
    public void testGetPrintWriter() throws IOException {
        HttpServletRequest request = EasyMock
                .createMock(HttpServletRequest.class);
        HttpServletResponse response = EasyMock
                .createMock(HttpServletResponse.class);
        TilesApplicationContext applicationContext = EasyMock
                .createMock(TilesApplicationContext.class);
        PrintWriter writer = EasyMock.createMock(PrintWriter.class);
        EasyMock.expect(response.getWriter()).andReturn(writer);
        EasyMock.replay(request, response, applicationContext, writer);
        ServletTilesRequestContext requestContext = new ServletTilesRequestContext(
                applicationContext, request, response);
        assertEquals(writer, requestContext.getPrintWriter());
        EasyMock.verify(request, response, applicationContext, writer);
    }

    /**
     * Tests the forced inclusion in the request.
     *
     * @throws IOException If something goes wrong.
     */
    @Test
    public void testForceInclude() throws IOException {
        MockHttpServletRequest request = new MockHttpServletRequest();
        MockHttpServletResponse response = new CommitSupportMockHttpServletResponse();
        MockServletTilesRequestContext context = new MockServletTilesRequestContext(
                applicationContext, request, response);
        context.dispatch(TEST_PATH);
        assertEquals(1, context.getForwardCount(), "Forward has not been called");
        assertEquals(0, context.getIncludeCount(), "Include has been called");
        assertFalse(ServletUtil.isForceInclude(request),
                "Force include has been incorrectly set.");
        ServletUtil.setForceInclude(request, true);
        context.dispatch(TEST_PATH);
        assertEquals(1, context.getForwardCount(), "Forward has been called");
        assertEquals(1, context.getIncludeCount(), "Include has not been called");
    }

    /**
     * Tests a generic map.
     *
     * @param <K> The key type.
     * @param <V> The value type.
     * @param currentMap The map to check.
     * @param keyClass The key class.
     * @param valueClass The value class.
     * @param mapName The name of the map to test (for messages).
     */
    private <K, V> void doTestReadMap(Map<K, V> currentMap, Class<K> keyClass,
            Class<V> valueClass, String mapName) {
        int size1 = currentMap.keySet().size();
        int size2 = currentMap.entrySet().size();
        assertEquals(size1, size2,
                "The map" + mapName + " has keySet and entrySet of different size");
        for (K key : currentMap.keySet()) {
            assertInstanceOf(keyClass, key, "The key is not of class" + keyClass.getName());
            V value = currentMap.get(key);
            assertInstanceOf(valueClass, value, "The value is not of class" + valueClass.getName());
            assertTrue(currentMap.containsValue(value), "The map " + mapName
                    + " does not return the correct value for 'containsValue'");
        }
    }

    /**
     * Extends {@link MockHttpServletResponse} to override
     * {@link MockHttpServletResponse#isCommitted()} method.
     */
    private static class CommitSupportMockHttpServletResponse extends
            MockHttpServletResponse {

        /** {@inheritDoc} */
        @Override
        public boolean isCommitted() {
            return false;
        }
    }

    /**
     * Extends {@link ServletTilesRequestContext} to check forward and include.
     */
    private static class MockServletTilesRequestContext extends
            ServletTilesRequestContext {

        /**
         * The number of times that forward has been called.
         */
        private int forwardCount = 0;

        /**
         * The number of times that include has been called.
         */
        private int includeCount = 0;

        /**
         * Constructor.
         *
         * @param applicationContext The Tiles application context.
         * @param request The request.
         * @param response The response.
         */
        public MockServletTilesRequestContext(
                TilesApplicationContext applicationContext,
                HttpServletRequest request, HttpServletResponse response) {
            super(applicationContext, request, response);
        }

        /** {@inheritDoc} */
        @Override
        protected void forward(String path) throws IOException {
            forwardCount++;
        }

        /** {@inheritDoc} */
        @Override
        public void include(String path) throws IOException {
            includeCount++;
        }

        /**
         * Returns the forward count.
         *
         * @return The forward count.
         */
        public int getForwardCount() {
            return forwardCount;
        }

        /**
         * Returns the include count.
         *
         * @return The include count.
         */
        public int getIncludeCount() {
            return includeCount;
        }
    }
}
