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

package io.github.weblegacy.tiles2.web.startup;

import java.io.IOException;
import java.util.Enumeration;
import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletConfig;
import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import org.apache.tiles.definition.util.DefinitionsFactoryUtil;

/**
 * Processes Reloadable Tiles Definitions.
 *
 * @version $Rev$ $Date$
 *
 * @deprecated Use {@link TilesServlet} or {@link TilesListener}. If you want a decoration Filter,
 *             use {@link io.github.weblegacy.tiles2.web.util.TilesDecorationFilter}. Moreover,
 *             definition files reload themselves if the
 *             {@link org.apache.tiles.definition.dao.DefinitionDAO} implements
 *             {@link org.apache.tiles.definition.Refreshable}.
 */
@Deprecated
public class TilesFilter extends TilesServlet implements Filter {

    private static final long serialVersionUID = -2130513159373472856L;

    /**
     * The filter configuration object we are associated with. If this value is null, this filter
     * instance is not currently configured.
     */
    private FilterConfig filterConfig = null;

    /**
     * Checks whether Tiles Definitions need to be reloaded.
     *
     * @param request  The servlet request we are processing
     * @param response The servlet response we are creating
     * @param chain    The filter chain we are processing
     *
     * @throws IOException      if an input/output error occurs
     * @throws ServletException if a servlet error occurs
     */
    @Deprecated
    public void doFilter(ServletRequest request, ServletResponse response,
                         FilterChain chain)
        throws IOException, ServletException {

        try {
            DefinitionsFactoryUtil.reloadDefinitionsFactory(
                    getServletContext());
            chain.doFilter(request, response);
        } catch (Exception e) {
            throw new ServletException("Error processing request.", e);
        }
    }

    /**
     * Returns the filter configuration object for this filter.
     *
     * @return The filter configuration.
     */
    @Deprecated
    public FilterConfig getFilterConfig() {
        return filterConfig;
    }

    /**
     * Set the filter configuration object for this filter.
     *
     * @param filterConfig The filter configuration object
     */
    @Deprecated
    public void setFilterConfig(FilterConfig filterConfig) {
        this.filterConfig = filterConfig;
    }

    /**
     * Destroy method for this filter.
     */
    @Deprecated
    public void destroy() {
        super.destroy();
    }

    /** {@inheritDoc} */
    @Deprecated
    public void init(FilterConfig filterConfig) throws ServletException {
        this.filterConfig = filterConfig;
        super.init(createServletConfig());

        if (DEBUG) {
            log("TilesDecorationFilter:Initializing filter");
        }
    }

    /** {@inheritDoc} */
    @Deprecated
    public void log(String msg) {
        filterConfig.getServletContext().log(msg);
    }

    /**
     * A DEBUG flag.
     */
    // FIXME Is it really used?
    private static final boolean DEBUG = true;

    /**
     * Creates a servlet configuration object from the filter configuration object.
     *
     * @return The servlet configuration object.
     */
    private ServletConfig createServletConfig() {
        return new ServletConfigAdapter(filterConfig);
    }

    /**
     * Adapts a filter configuration object to become a servlet configuration object.
     */
    @Deprecated
    class ServletConfigAdapter implements ServletConfig {

        /**
         * The filter configuration object to use.
         */
        private FilterConfig config;

        /**
         * Constructor.
         *
         * @param config The filter configuration object to use.
         */
        @Deprecated
        public ServletConfigAdapter(FilterConfig config) {
            this.config = config;
        }

        /** {@inheritDoc} */
        @Deprecated
        public String getServletName() {
            return config.getFilterName();
        }

        /** {@inheritDoc} */
        @Deprecated
        public ServletContext getServletContext() {
            return config.getServletContext();
        }

        /** {@inheritDoc} */
        @Deprecated
        public String getInitParameter(String string) {
            return config.getInitParameter(string);
        }

        /** {@inheritDoc} */
        @Deprecated
        public Enumeration<String> getInitParameterNames() {
            return config.getInitParameterNames();
        }
    }

}
