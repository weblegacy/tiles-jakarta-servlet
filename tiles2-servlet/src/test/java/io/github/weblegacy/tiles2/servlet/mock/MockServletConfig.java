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

package io.github.weblegacy.tiles2.servlet.mock;

import jakarta.servlet.ServletConfig;
import jakarta.servlet.ServletContext;
import java.util.Collections;
import java.util.Enumeration;
import java.util.LinkedHashMap;

/**
 * The mock-class for {@link ServletConfig}.
 *
 * <p>A servlet configuration object used by a servlet container to pass information to a servlet
 * during initialization.</p>
 */
public class MockServletConfig implements ServletConfig {

    /**
     * The reference to the {@link ServletContext} in which the caller is executing.
     */
    private final ServletContext servletContext;

    /**
     * The servlet's initialization parameters.
     */
    private final LinkedHashMap<String, String> initParameters = new LinkedHashMap<>();

    /**
     * Initialize this mock-class with the {@link ServletContext}.
     *
     * @param servletContext the {@link ServletContext} in which the caller is executing
     */
    public MockServletConfig(ServletContext servletContext) {
        this.servletContext = servletContext;
    }

    /**
     * Adds a new initialization parameter to the servlet-config.
     *
     * @param name  the name of the initialization parameter
     * @param value the value of the initialization parameter
     */
    public void addInitParameter(String name, String value) {
        initParameters.put(name, value);
    }

    /**
     * Returns the name of this servlet instance. The name may be provided via server
     * administration, assigned in the web application deployment descriptor, or for an
     * unregistered (and thus unnamed) servlet instance it will be the servlet's class name.
     *
     * @return the name of the servlet instance
     */
    @Override
    public String getServletName() {
        throw new UnsupportedOperationException();
    }

    /**
     * Returns a reference to the {@link ServletContext} in which the caller is executing.
     *
     * @return a {@link ServletContext} object, used by the caller to interact with its servlet
     *         container
     *
     * @see ServletContext
     */
    @Override
    public ServletContext getServletContext() {
        return servletContext;
    }

    /**
     * Gets the value of the initialization parameter with the given name.
     *
     * @param name the name of the initialization parameter whose value to get
     *
     * @return a {@code String} containing the value of the initialization parameter, or
     *         {@code null} if the initialization parameter does not exist
     */
    @Override
    public String getInitParameter(String name) {
        return initParameters.get(name);
    }

    /**
     * Returns the names of the servlet's initialization parameters as an {@code Enumeration} of
     * {@code String} objects, or an empty {@code Enumeration} if the servlet has no initialization
     * parameters.
     *
     * @return an {@code Enumeration} of {@code String} objects containing the names of the
     *         servlet's initialization parameters
     */
    @Override
    public Enumeration<String> getInitParameterNames() {
        return Collections.enumeration(initParameters.keySet());
    }
}
