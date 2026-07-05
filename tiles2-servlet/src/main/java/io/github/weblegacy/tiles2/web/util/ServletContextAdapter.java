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

package io.github.weblegacy.tiles2.web.util;

import jakarta.servlet.Filter;
import jakarta.servlet.FilterRegistration;
import jakarta.servlet.RequestDispatcher;
import jakarta.servlet.Servlet;
import jakarta.servlet.ServletConfig;
import jakarta.servlet.ServletContext;
import jakarta.servlet.ServletException;
import jakarta.servlet.ServletRegistration;
import jakarta.servlet.ServletRegistration.Dynamic;
import jakarta.servlet.SessionCookieConfig;
import jakarta.servlet.SessionTrackingMode;
import jakarta.servlet.descriptor.JspConfigDescriptor;
import java.io.InputStream;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.Enumeration;
import java.util.EventListener;
import java.util.Hashtable;
import java.util.Map;
import java.util.Set;

/**
 * Adapts a servlet config and a servlet context to become a unique servlet context.
 *
 * @version $Rev$ $Date$
 */
@SuppressWarnings("deprecation")
public class ServletContextAdapter implements ServletContext {

    /**
     * The root context to use.
     */
    private ServletContext rootContext;

    /**
     * The union of init parameters of {@link ServletConfig} and {@link ServletContext}.
     */
    private Hashtable<String, String> initParameters;

    /**
     * Constructor.
     *
     * @param config The servlet configuration object.
     */
    public ServletContextAdapter(ServletConfig config) {
        this.rootContext = config.getServletContext();
        initParameters = new Hashtable<String, String>();
        Enumeration<String> enumeration = rootContext
                .getInitParameterNames();
        while (enumeration.hasMoreElements()) {
            String paramName = enumeration.nextElement();
            initParameters.put(paramName, rootContext
                    .getInitParameter(paramName));
        }
        enumeration = config.getInitParameterNames();
        while (enumeration.hasMoreElements()) {
            String paramName = enumeration.nextElement();
            initParameters.put(paramName, config.getInitParameter(paramName));
        }
    }

    /** {@inheritDoc} */
    public ServletContext getContext(String string) {
        return rootContext.getContext(string);
    }

    /** {@inheritDoc} */
    public int getMajorVersion() {
        return rootContext.getMajorVersion();
    }

    /** {@inheritDoc} */
    public int getMinorVersion() {
        return rootContext.getMinorVersion();
    }

    /** {@inheritDoc} */
    public String getMimeType(String string) {
        return rootContext.getMimeType(string);
    }

    /** {@inheritDoc} */
    public Set<String> getResourcePaths(String string) {
        return rootContext.getResourcePaths(string);
    }

    /** {@inheritDoc} */
    public URL getResource(String string) throws MalformedURLException {
        return rootContext.getResource(string);
    }

    /** {@inheritDoc} */
    public InputStream getResourceAsStream(String string) {
        return rootContext.getResourceAsStream(string);
    }

    /** {@inheritDoc} */
    public RequestDispatcher getRequestDispatcher(String string) {
        return rootContext.getRequestDispatcher(string);
    }

    /** {@inheritDoc} */
    public RequestDispatcher getNamedDispatcher(String string) {
        return rootContext.getNamedDispatcher(string);
    }

    /** {@inheritDoc} */
    @Deprecated
    public Servlet getServlet(String string) throws ServletException {
        return rootContext.getServlet(string);
    }

    /** {@inheritDoc} */
    @Deprecated
    public Enumeration<Servlet> getServlets() {
        return rootContext.getServlets();
    }

    /** {@inheritDoc} */
    @Deprecated
    public Enumeration<String> getServletNames() {
        return rootContext.getServletNames();
    }

    /** {@inheritDoc} */
    public void log(String string) {
        rootContext.log(string);
    }

    /** {@inheritDoc} */
    public void log(Exception exception, String string) {
        rootContext.log(exception, string);
    }

    /** {@inheritDoc} */
    public void log(String string, Throwable throwable) {
        rootContext.log(string, throwable);
    }

    /** {@inheritDoc} */
    public String getRealPath(String string) {
        return rootContext.getRealPath(string);
    }

    /** {@inheritDoc} */
    public String getServerInfo() {
        return rootContext.getServerInfo();
    }

    /** {@inheritDoc} */
    public String getInitParameter(String string) {
        return initParameters.get(string);
    }

    /** {@inheritDoc} */
    public Enumeration<String> getInitParameterNames() {
        return initParameters.keys();
    }

    /** {@inheritDoc} */
    public Object getAttribute(String string) {
        return rootContext.getAttribute(string);
    }

    /** {@inheritDoc} */
    public Enumeration<String> getAttributeNames() {
        return rootContext.getAttributeNames();
    }

    /** {@inheritDoc} */
    public void setAttribute(String string, Object object) {
        rootContext.setAttribute(string, object);
    }

    /** {@inheritDoc} */
    public void removeAttribute(String string) {
        rootContext.removeAttribute(string);
    }

    /** {@inheritDoc} */
    public String getServletContextName() {
        return rootContext.getServletContextName();
    }

    /** {@inheritDoc} */
    public String getContextPath() {
        return rootContext.getContextPath();
    }

    /** {@inheritDoc} */
    @Override
    public int getEffectiveMajorVersion() {
        return rootContext.getEffectiveMajorVersion();
    }

    /** {@inheritDoc} */
    @Override
    public int getEffectiveMinorVersion() {
        return rootContext.getEffectiveMinorVersion();
    }

    /** {@inheritDoc} */
    @Override
    public boolean setInitParameter(String name, String value) {
        if (initParameters.contains(name)) {
            return false;
        }
        initParameters.put(name, value);
        return true;
    }

    /** {@inheritDoc} */
    @Override
    public Dynamic addServlet(String servletName, String className) {
        return rootContext.addServlet(servletName, className);
    }

    /** {@inheritDoc} */
    @Override
    public Dynamic addServlet(String servletName, Servlet servlet) {
        return rootContext.addServlet(servletName, servlet);
    }

    /** {@inheritDoc} */
    @Override
    public Dynamic addServlet(String servletName, Class<? extends Servlet> servletClass) {
        return rootContext.addServlet(servletName, servletClass);
    }

    /** {@inheritDoc} */
    @Override
    public <T extends Servlet> T createServlet(Class<T> clazz) throws ServletException {
        return rootContext.createServlet(clazz);
    }

    /** {@inheritDoc} */
    @Override
    public ServletRegistration getServletRegistration(String servletName) {
        return rootContext.getServletRegistration(servletName);
    }

    /** {@inheritDoc} */
    @Override
    public Map<String, ? extends ServletRegistration> getServletRegistrations() {
        return rootContext.getServletRegistrations();
    }

    /** {@inheritDoc} */
    @Override
    public FilterRegistration.Dynamic addFilter(String filterName, String className) {
        return rootContext.addFilter(filterName, className);
    }

    /** {@inheritDoc} */
    @Override
    public FilterRegistration.Dynamic addFilter(String filterName, Filter filter) {
        return rootContext.addFilter(filterName, filter);
    }

    /** {@inheritDoc} */
    @Override
    public FilterRegistration.Dynamic addFilter(String filterName,
            Class<? extends Filter> filterClass) {

        return rootContext.addFilter(filterName, filterClass);
    }

    /** {@inheritDoc} */
    @Override
    public <T extends Filter> T createFilter(Class<T> clazz) throws ServletException {
        return rootContext.createFilter(clazz);
    }

    /** {@inheritDoc} */
    @Override
    public FilterRegistration getFilterRegistration(String filterName) {
        return rootContext.getFilterRegistration(filterName);
    }

    /** {@inheritDoc} */
    @Override
    public Map<String, ? extends FilterRegistration> getFilterRegistrations() {
        return rootContext.getFilterRegistrations();
    }

    /** {@inheritDoc} */
    @Override
    public SessionCookieConfig getSessionCookieConfig() {
        return rootContext.getSessionCookieConfig();
    }

    /** {@inheritDoc} */
    @Override
    public void setSessionTrackingModes(Set<SessionTrackingMode> sessionTrackingModes) {
        rootContext.setSessionTrackingModes(sessionTrackingModes);
    }

    /** {@inheritDoc} */
    @Override
    public Set<SessionTrackingMode> getDefaultSessionTrackingModes() {
        return rootContext.getDefaultSessionTrackingModes();
    }

    /** {@inheritDoc} */
    @Override
    public Set<SessionTrackingMode> getEffectiveSessionTrackingModes() {
        return rootContext.getEffectiveSessionTrackingModes();
    }

    /** {@inheritDoc} */
    @Override
    public void addListener(String className) {
        rootContext.addListener(className);
    }

    /** {@inheritDoc} */
    @Override
    public <T extends EventListener> void addListener(T t) {
        rootContext.addListener(t);
    }

    /** {@inheritDoc} */
    @Override
    public void addListener(Class<? extends EventListener> listenerClass) {
        rootContext.addListener(listenerClass);
    }

    /** {@inheritDoc} */
    @Override
    public <T extends EventListener> T createListener(Class<T> clazz) throws ServletException {
        return rootContext.createListener(clazz);
    }

    /** {@inheritDoc} */
    @Override
    public JspConfigDescriptor getJspConfigDescriptor() {
        return rootContext.getJspConfigDescriptor();
    }

    /** {@inheritDoc} */
    @Override
    public ClassLoader getClassLoader() {
        return rootContext.getClassLoader();
    }

    /** {@inheritDoc} */
    @Override
    public void declareRoles(String... roleNames) {
        rootContext.declareRoles(roleNames);
    }

    /** {@inheritDoc} */
    @Override
    public String getVirtualServerName() {
        return rootContext.getVirtualServerName();
    }

    /** {@inheritDoc} */
    @Override
    public Dynamic addJspFile(String servletName, String jspFile) {
        return rootContext.addJspFile(servletName, jspFile);
    }

    /** {@inheritDoc} */
    @Override
    public int getSessionTimeout() {
        return rootContext.getSessionTimeout();
    }

    /** {@inheritDoc} */
    @Override
    public void setSessionTimeout(int sessionTimeout) {
        rootContext.setSessionTimeout(sessionTimeout);
    }

    /** {@inheritDoc} */
    @Override
    public String getRequestCharacterEncoding() {
        return rootContext.getRequestCharacterEncoding();
    }

    /** {@inheritDoc} */
    @Override
    public void setRequestCharacterEncoding(String encoding) {
        rootContext.setRequestCharacterEncoding(encoding);
    }

    /** {@inheritDoc} */
    @Override
    public String getResponseCharacterEncoding() {
        return rootContext.getResponseCharacterEncoding();
    }

    /** {@inheritDoc} */
    @Override
    public void setResponseCharacterEncoding(String encoding) {
        rootContext.setResponseCharacterEncoding(encoding);
    }

    /**
     * Composes an enumeration into a single one.
     *
     * @param <T> Type of enumeration
     */
    class CompositeEnumeration<T> implements Enumeration<T> {

        /**
         * The first enumeration to consider.
         */
        private Enumeration<T> first;

        /**
         * The second enumeration to consider.
         */
        private Enumeration<T> second;

        /**
         * Constructor.
         *
         * @param first  The first enumeration to consider.
         * @param second The second enumeration to consider.
         */
        public CompositeEnumeration(Enumeration<T> first, Enumeration<T> second) {
            this.first = first;
            this.second = second;
        }

        /** {@inheritDoc} */
        public boolean hasMoreElements() {
            return first.hasMoreElements() || second.hasMoreElements();
        }

        /** {@inheritDoc} */
        public T nextElement() {
            if (first.hasMoreElements()) {
                return first.nextElement();
            }

            return second.nextElement();
        }
    }
}
