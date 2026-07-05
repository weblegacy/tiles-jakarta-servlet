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

import jakarta.servlet.Filter;
import jakarta.servlet.FilterRegistration;
import jakarta.servlet.RequestDispatcher;
import jakarta.servlet.Servlet;
import jakarta.servlet.ServletConfig;
import jakarta.servlet.ServletContainerInitializer;
import jakarta.servlet.ServletContext;
import jakarta.servlet.ServletContextAttributeListener;
import jakarta.servlet.ServletContextListener;
import jakarta.servlet.ServletException;
import jakarta.servlet.ServletRegistration;
import jakarta.servlet.ServletRequestAttributeListener;
import jakarta.servlet.ServletRequestListener;
import jakarta.servlet.ServletSecurityElement;
import jakarta.servlet.SessionCookieConfig;
import jakarta.servlet.SessionTrackingMode;
import jakarta.servlet.SingleThreadModel;
import jakarta.servlet.annotation.MultipartConfig;
import jakarta.servlet.annotation.ServletSecurity;
import jakarta.servlet.annotation.WebListener;
import jakarta.servlet.descriptor.JspConfigDescriptor;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSessionAttributeListener;
import jakarta.servlet.http.HttpSessionIdListener;
import jakarta.servlet.http.HttpSessionListener;
import java.io.InputStream;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.Collections;
import java.util.Enumeration;
import java.util.EventListener;
import java.util.LinkedHashMap;
import java.util.Map;
import java.util.Set;

/**
 * The mock-class for {@link ServletContext}.
 *
 * <p>Defines a set of methods that a servlet uses to communicate with its servlet container, for
 * example, to get the MIME type of a file, dispatch requests, or write to a log file.</p>
 *
 * <p>There is one context per "web application" per Java Virtual Machine. (A "web application" is a
 * collection of servlets and content installed under a specific subset of the server's URL
 * namespace such as {@code /catalog} and possibly installed via a {@code .war} file.)</p>
 *
 * <p>In the case of a web application marked "distributed" in its deployment descriptor, there will
 * be one context instance for each virtual machine.  In this situation, the context cannot be used
 * as a location to share global information (because the information won't be truly global). Use an
 * external resource like a database instead.</p>
 *
 * <p>The {@code ServletContext} object is contained within the {@link ServletConfig} object, which
 * the Web server provides the servlet when the servlet is initialized.</p>
 *
 * @author Various
 *
 * @see Servlet#getServletConfig()
 * @see ServletConfig#getServletContext()
 */
public class MockServletContext implements ServletContext {

    /**
     * The servlet's initialization parameters.
     */
    private final LinkedHashMap<String, String> initParameters = new LinkedHashMap<>();

    /**
     * Initialize this class.
     */
    public MockServletContext() {
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
     * Returns the context path of the web application.
     *
     * <p>The context path is the portion of the request URI that is used to select the context of
     * the request. The context path always comes first in a request URI. If this context is the
     * "root" context rooted at the base of the Web server's URL name space, this path will be an
     * empty string. Otherwise, if the context is not rooted at the root of the server's name space,
     * the path starts with a / character but does not end with a / character.</p>
     *
     * <p>It is possible that a servlet container may match a context by more than one context
     * path. In such cases the {@link HttpServletRequest#getContextPath()} will return the
     * actual context path used by the request and it may differ from the path returned by this
     * method. The context path returned by this method should be considered as the prime or
     * preferred context path of the application.</p>
     *
     * @return The context path of the web application, or "" for the root context
     *
     * @see HttpServletRequest#getContextPath()
     *
     * @since Servlet 2.5
     */
    @Override
    public String getContextPath() {
        throw new UnsupportedOperationException();
    }

    /**
     * Returns a {@code ServletContext} object that corresponds to a specified URL on the server.
     *
     * <p>This method allows servlets to gain access to the context for various parts of the
     * server, and as needed obtain {@link RequestDispatcher} objects from the context. The given
     * path must be begin with {@code /}, is interpreted relative to the server's document root
     * and is matched against the context roots of other web applications hosted on this
     * container.</p>
     *
     * <p>In a security conscious environment, the servlet container may return {@code null} for a
     * given URL.</p>
     *
     * @param uripath a {@code String} specifying the context path of another web application in
     *                the container.
     *
     * @return the {@code ServletContext} object that corresponds to the named URL, or null if
     *         either none exists or the container wishes to restrict this access.
     *
     * @see RequestDispatcher
     *
     */
    @Override
    public ServletContext getContext(String uripath) {
        throw new UnsupportedOperationException();
    }

    /**
     * Returns the major version of the Servlet API that this servlet container supports. All
     * implementations that comply with Version 5.0 must have this method return the integer 3.
     *
     * @return 5
     */
    @Override
    public int getMajorVersion() {
        return 5;
    }

    /**
     * Returns the minor version of the Servlet API that this servlet container supports. All
     * implementations that comply with Version 5.0 must have this method return the integer 0.
     *
     * @return 0
     */
    @Override
    public int getMinorVersion() {
        return 0;
    }

    /**
     * Gets the major version of the Servlet specification that the application represented by this
     * ServletContext is based on.
     *
     * <p>The value returned may be different from {@link #getMajorVersion()}, which returns the
     * major version of the Servlet specification supported by the Servlet container.</p>
     *
     * @return the major version of the Servlet specification that the application represented by
     *         this ServletContext is based on
     *
     * @throws UnsupportedOperationException if this ServletContext was passed to the
     *                                       {@link ServletContextListener
     *                                       #contextInitialized(ServletContextEvent)} method of a
     *                                       {@link ServletContextListener} that was neither
     *                                       declared in {@code web.xml} or
     *                                       {@code web-fragment.xml}, nor annotated with
     *                                       {@link WebListener}
     *
     * @since Servlet 3.0
     */
    @Override
    public int getEffectiveMajorVersion() {
        throw new UnsupportedOperationException();
    }

    /**
     * Gets the minor version of the Servlet specification that the application represented by this
     * ServletContext is based on.
     *
     * <p>The value returned may be different from {@link #getMinorVersion()}, which returns the
     * minor version of the Servlet specification supported by the Servlet container.</p>
     *
     * @return the minor version of the Servlet specification that the application represented by
     *         this ServletContext is based on
     *
     * @throws UnsupportedOperationException if this ServletContext was passed to the
     *                                       {@link ServletContextListener
     *                                       #contextInitialized(ServletContextEvent)} method of a
     *                                       {@link ServletContextListener} that was neither
     *                                       declared in {@code web.xml} or
     *                                       {@code web-fragment.xml}, nor annotated with
     *                                       {@link WebListener}
     *
     * @since Servlet 3.0
     */
    @Override
    public int getEffectiveMinorVersion() {
        throw new UnsupportedOperationException();
    }

    /**
     * Returns the MIME type of the specified file, or {@code null} if the MIME type is not known.
     * The MIME type is determined by the configuration of the servlet container, and may be
     * specified in a web application deployment descriptor. Common MIME types include
     * {@code "text/html"} and {@code "image/gif"}.
     *
     * @param file a {@code String} specifying the name of a file
     *
     * @return a {@code String} specifying the file's MIME type
     */
    @Override
    public String getMimeType(String file) {
        throw new UnsupportedOperationException();
    }

    /**
     * Returns a directory-like listing of all the paths to resources within the web application
     * whose longest sub-path matches the supplied path argument.
     *
     * <p>Paths indicating subdirectory paths end with a {@code /}.</p>
     *
     * <p>The returned paths are all relative to the root of the web application, or relative to
     * the {@code /META-INF/resources} directory of a JAR file inside the web application's
     * {@code /WEB-INF/lib} directory, and have a leading {@code /}.</p>
     *
     * <p>The returned set is not backed by the {@code ServletContext} object, so changes in the
     * returned set are not reflected in the {@code ServletContext} object, and vice-versa.</p>
     *
     * <p>For example, for a web application containing:</p>
     *
     * <pre>{@code
     *   /welcome.html
     *   /catalog/index.html
     *   /catalog/products.html
     *   /catalog/offers/books.html
     *   /catalog/offers/music.html
     *   /customer/login.jsp
     *   /WEB-INF/web.xml
     *   /WEB-INF/classes/com.acme.OrderServlet.class
     *   /WEB-INF/lib/catalog.jar!/META-INF/resources/catalog/moreOffers/books.html
     * }</pre>
     *
     * {@code getResourcePaths("/")} would return
     * {@code {"/welcome.html", "/catalog/", "/customer/", "/WEB-INF/"}}, and
     * {@code getResourcePaths("/catalog/")} would return
     * {@code {"/catalog/index.html", "/catalog/products.html", "/catalog/offers/",
     * "/catalog/moreOffers/"}}.
     *
     * @param path the partial path used to match the resources, which must start with a {@code /}
     *
     * @return a Set containing the directory listing, or {@code null} if there are no resources in
     *         the web application whose path begins with the supplied path.
     *
     * @since Servlet 2.3
     */
    @Override
    public Set<String> getResourcePaths(String path) {
        throw new UnsupportedOperationException();
    }

    /**
     * Returns a URL to the resource that is mapped to the given path.
     *
     * <p>The path must begin with a {@code /} and is interpreted as relative to the current
     * context root, or relative to the {@code /META-INF/resources} directory of a JAR file inside
     * the web application's {@code /WEB-INF/lib} directory.<br>
     * This method will first search the document root of the web application for the requested
     * resource, before searching any of the JAR files inside {@code /WEB-INF/lib}.<br>
     * The order in which the JAR files inside {@code /WEB-INF/lib} are searched is undefined.</p>
     *
     * <p>This method allows the servlet container to make a resource available to servlets from
     * any source. Resources can be located on a local or remote file system, in a database, or
     * in a {@code .war} file.</p>
     *
     * <p>The servlet container must implement the URL handlers and {@code URLConnection} objects
     * that are necessary to access the resource.</p>
     *
     * <p>This method returns {@code null} if no resource is mapped to the pathname.</p>
     *
     * <p>Some containers may allow writing to the URL returned by this method using the methods of
     * the URL class.</p>
     *
     * <p>The resource content is returned directly, so be aware that requesting a {@code .jsp}
     * page returns the JSP source code. Use a {@code RequestDispatcher} instead to include
     * results of an execution.</p>
     *
     * <p>This method has a different purpose than {@link Class#getResource(String)}, which looks up
     * resources based on a class loader. This method does not use class loaders.</p>
     *
     * @param path a {@code String} specifying the path to the resource
     *
     * @return the resource located at the named path, or {@code null} if there is no resource at
     *         that path
     *
     * @throws MalformedURLException if the pathname is not given in the correct form
     */
    @Override
    public URL getResource(String path) throws MalformedURLException {
        throw new UnsupportedOperationException();
    }

    /**
     * Returns the resource located at the named path as an {@code InputStream} object.
     *
     * <p>The data in the {@code InputStream} can be of any type or length. The path must be
     * specified according to the rules given in {@code getResource}. This method returns
     * {@code null} if no resource exists at the specified path.</p>
     *
     * <p>Meta-information such as content length and content type that is available via
     * {@code getResource} method is lost when using this method.</p>
     *
     * <p>The servlet container must implement the URL handlers and {@code URLConnection} objects
     * necessary to access the resource.</p>
     *
     * <p>This method is different from {@link Class#getResourceAsStream(String)}, which uses a
     * class loader. This method allows servlet containers to make a resource available to a servlet
     * from any location, without using a class loader.</p>
     *
     * @param path a {@code String} specifying the path to the resource
     *
     * @return the {@code InputStream} returned to the servlet, or {@code null} if no resource
     *         exists at the specified path
     */
    @Override
    public InputStream getResourceAsStream(String path) {
        throw new UnsupportedOperationException();
    }

    /**
     * Returns a {@link RequestDispatcher} object that acts as a wrapper for the resource located
     * at the given path. A {@code RequestDispatcher} object can be used to forward a request to
     * the resource or to include the resource in a response. The resource can be dynamic or
     * static.
     *
     * <p>The pathname must begin with a {@code /} and is interpreted as relative to the current
     * context root. Use {@code getContext} to obtain a {@code RequestDispatcher} for resources in
     * foreign contexts.</p>
     *
     * <p>This method returns {@code null} if the {@code ServletContext} cannot return a
     * {@code RequestDispatcher}.</p>
     *
     * @param path a {@code String} specifying the pathname to the resource
     *
     * @return a {@code RequestDispatcher} object that acts as a wrapper for the resource at the
     *         specified path, or {@code null} if the {@code ServletContext} cannot return a
     *         {@code RequestDispatcher}
     *
     * @see RequestDispatcher
     * @see ServletContext#getContext(String)
     */
    @Override
    public RequestDispatcher getRequestDispatcher(String path) {
        throw new UnsupportedOperationException();
    }

    /**
     * Returns a {@link RequestDispatcher} object that acts as a wrapper for the named servlet.
     *
     * <p>Servlets (and JSP pages also) may be given names via server administration or via a web
     * application deployment descriptor. A servlet instance can determine its name using
     * {@link ServletConfig#getServletName()}.</p>
     *
     * <p>This method returns {@code null} if the {@code ServletContext} cannot return a
     * {@code RequestDispatcher} for any reason.</p>
     *
     * @param name a {@code String} specifying the name of a servlet to wrap
     *
     * @return a {@code RequestDispatcher} object that acts as a wrapper for the amed servlet, or
     *         {@code null} if the {@code ServletContext} cannot return a {@code RequestDispatcher}
     *
     * @see RequestDispatcher
     * @see ServletContext#getContext(String)
     * @see ServletConfig#getServletName()
     */
    @Override
    public RequestDispatcher getNamedDispatcher(String name) {
        throw new UnsupportedOperationException();
    }

    /**
     * In lieu of this method, servlets can share information using the {@code ServletContext} class
     * and can perform shared business logic by invoking methods on common non-servlet classes.
     *
     * @param name the servlet name
     *
     * @return the {@link Servlet} with the given name
     *
     * @throws ServletException if an exception has occurred that interfaces with servlet's normal
     *                          operation
     *
     * @deprecated As of Java Servlet API 2.1, with no direct replacement.
     *
     *             <p>This method was originally defined to retrieve a servlet from a
     *             {@code ServletContext}. In this version, this method always returns {@code null}
     *             and remains only to preserve binary compatibility. This method will be
     *             permanently removed in a future version of the Java Servlet API.</p>
     */
    @Override
    @Deprecated
    public Servlet getServlet(String name) throws ServletException {
        return null;
    }

    /**
     * Return an {@code Enumeration} of {@link Servlet}.
     *
     * @deprecated As of Java Servlet API 2.0, with no replacement.
     *
     *             <p>This method was originally defined to return an {@code Enumeration} of all
     *             the servlets known to this ServletContext.<br>
     *             In this version, this method always returns an empty enumeration and remains
     *             only to preserve binary compatibility. This method will be permanently removed
     *             in a future version of the Java Servlet API.</p>
     */
    @Override
    @Deprecated
    public Enumeration<Servlet> getServlets() {
        return Collections.emptyEnumeration();
    }

    /**
     * Return an {@code Enumeration} of {@link Servlet} names.
     *
     * @deprecated As of Java Servlet API 2.1, with no replacement.
     *
     *             <p>This method was originally defined to return an {@code Enumeration} of all
     *             the servlet names known to this context. In this version, this method always
     *             returns an empty {@code Enumeration} and remains only to preserve binary
     *             compatibility. This method will be permanently removed in a future version of
     *             the Java Servlet API.</p>
     */
    @Override
    @Deprecated
    public Enumeration<String> getServletNames() {
        return Collections.emptyEnumeration();
    }

    /**
     * Writes the specified message to a servlet log file, usually an event log. The name and type
     * of the servlet log file is specific to the servlet container.
     *
     * @param msg a {@code String} specifying the message to be written to the log file
     */
    @Override
    public void log(String msg) {
        throw new UnsupportedOperationException();
    }

    /**
     * Write an exception's stack trace and an explanatory error message to the servlet log file.
     *
     * @param exception the {@code Exception} error
     * @param msg       a {@code String} that describes the exception
     *
     * @deprecated As of Java Servlet API 2.1, use {@link #log(String, Throwable)} instead.
     *
     *             <p>This method was originally defined to write an exception's stack trace and an
     *             explanatory error message to the servlet log file.</p>
     */
    @Override
    @Deprecated
    public void log(Exception exception, String msg) {
        log(msg, exception);
    }

    /**
     * Writes an explanatory message and a stack trace for a given {@code Throwable} exception to
     * the servlet log file. The name and type of the servlet log file is specific to the servlet
     * container, usually an event log.
     *
     * @param message   a {@code String} that describes the error or exception
     * @param throwable the {@code Throwable} error or exception
     */
    @Override
    public void log(String message, Throwable throwable) {
        throw new UnsupportedOperationException();
    }

    /**
     * Gets the <i>real</i> path corresponding to the given <i>virtual</i> path.
     *
     * <p>For example, if {@code path} is equal to {@code /index.html}, this method will return
     * the absolute file path on the server's filesystem to which a request of the form
     * {@code http://<host>:<port>/<contextPath>/index.html} would be mapped, where
     * {@code <contextPath>} corresponds to the context path of this ServletContext.</p>
     *
     * <p>The real path returned will be in a form appropriate to the computer and operating system
     * on which the servlet container is running, including the proper path separators.</p>
     *
     * <p>Resources inside the {@code /META-INF/resources} directories of JAR files bundled in the
     * application's {@code /WEB-INF/lib} directory must be considered only if the container has
     * unpacked them from their containing JAR file, in which case the path to the unpacked
     * location must be returned.</p>
     *
     * <p>This method returns {@code null} if the servlet container is unable to translate the
     * given <i>virtual</i> path to a <i>real</i> path.</p>
     *
     * @param path the <i>virtual</i> path to be translated to a <i>real</i> path
     *
     * @return the <i>real</i> path, or {@code null} if the translation cannot be performed
     */
    @Override
    public String getRealPath(String path) {
        throw new UnsupportedOperationException();
    }

    /**
     * Returns the name and version of the servlet container on which the servlet is running.
     *
     * <p>The form of the returned string is<i>servername</i>/<i>versionnumber</i>. For example,
     * the JavaServer Web Development Kit may return the string
     * {@code JavaServer Web Dev Kit/1.0}.</p>
     *
     * <p>The servlet container may return other optional information after the primary string in
     * parentheses, for example,
     * {@code JavaServer Web Dev Kit/1.0 (JDK 1.1.6; Windows NT 4.0 x86)}.</p>
     *
     * @return a {@code String} containing at least the servlet container name and version number
     */
    @Override
    public String getServerInfo() {
        throw new UnsupportedOperationException();
    }

    /**
     * Returns a {@code String} containing the value of the named context-wide initialization
     * parameter, or {@code null} if the parameter does not exist.
     *
     * <p>This method can make available configuration information useful to an entire web
     * application. For example, it can provide a webmaster's email address or the name of a system
     * that holds critical data.</p>
     *
     * @param name a {@code String} containing the name of the parameter whose value is requested
     *
     * @return a {@code String} containing the value of the context's initialization parameter, or
     *         {@code null} if the context's initialization parameter does not exist.
     *
     * @throws NullPointerException if the argument {@code name} is {@code null}
     *
     * @see ServletConfig#getInitParameter(String)
     */
    @Override
    public String getInitParameter(String name) {
        return initParameters.get(name);
    }

    /**
     * Returns the names of the context's initialization parameters as an {@code Enumeration} of
     * {@code String} objects, or an empty {@code Enumeration} if the context has no initialization
     * parameters.
     *
     * @return an {@code Enumeration} of {@code String} objects containing the names of the
     *         context's initialization parameters
     *
     * @see ServletConfig#getInitParameter(String)
     */
    @Override
    public Enumeration<String> getInitParameterNames() {
        return Collections.enumeration(initParameters.keySet());
    }

    /**
     * Sets the context initialization parameter with the given name and value on this
     * ServletContext.
     *
     * @param name  the name of the context initialization parameter to set
     * @param value the value of the context initialization parameter to set
     *
     * @return {@code true} if the context initialization parameter with the given name and value
     *         was set successfully on this ServletContext, and {@code false} if it was not set
     *         because this ServletContext already contains a context initialization parameter with
     *         a matching name
     *
     * @throws IllegalStateException         if this ServletContext has already been initialized
     * @throws NullPointerException          if the name parameter is {@code null}
     * @throws UnsupportedOperationException if this ServletContext was passed to the
     *                                       {@link ServletContextListener
     *                                       #contextInitialized(ServletContextEvent)} method of a
     *                                       {@link ServletContextListener} that was neither
     *                                       declared in {@code web.xml} or
     *                                       {@code web-fragment.xml}, nor annotated with
     *                                       {@link WebListener}
     *
     * @since Servlet 3.0
     */
    @Override
    public boolean setInitParameter(String name, String value) {
        if (initParameters.containsKey(name)) {
            return false;
        }

        initParameters.put(name, value);
        return true;
    }

    /**
     * Returns the servlet container attribute with the given name, or {@code null} if there is no
     * attribute by that name. An attribute allows a servlet container to give the servlet
     * additional information not already provided by this interface. See your server documentation
     * for information about its attributes. A list of supported attributes can be retrieved using
     * {@code getAttributeNames}.
     *
     * <p>The attribute is returned as a {@link Object} or some subclass. Attribute names should
     * follow the same convention as package names. The Java Servlet API specification reserves
     * names matching {@code java.*}, {@code javax.*}, and {@code sun.*}.</p>
     *
     * @param name a {@code String} specifying the name of the attribute
     *
     * @return an {@code Object} containing the value of the attribute, or {@code null} if no
     *         attribute exists matching the given name
     *
     * @throws NullPointerException if the argument {@code name} is {@code null}
     *
     * @see ServletContext#getAttributeNames()
     */
    @Override
    public Object getAttribute(String name) {
        throw new UnsupportedOperationException();
    }

    /**
     * Returns an {@code Enumeration} containing the attribute names available within this
     * ServletContext.
     *
     * <p>Use the {@link #getAttribute(String)} method with an attribute name to get the value of an
     * attribute.</p>
     *
     * @return an {@code Enumeration} of attribute names
     *
     * @see #getAttribute(String)
     */
    @Override
    public Enumeration<String> getAttributeNames() {
        throw new UnsupportedOperationException();
    }

    /**
     * Binds an object to a given attribute name in this ServletContext. If the name specified is
     * already used for an attribute, this method will replace the attribute with the new to the
     * new attribute.
     *
     * <p>If listeners are configured on the {@code ServletContext} the container notifies them
     * accordingly.</p>
     *
     * <p>If a {@code null} value is passed, the effect is the same as calling
     * {@code removeAttribute()}.</p>
     *
     * <p>Attribute names should follow the same convention as package names. The Java Servlet API
     * specification reserves names matching {@code java.*}, {@code javax.*}, and
     * {@code sun.*}.</p>
     *
     * @param name   a {@code String} specifying the name of the attribute
     * @param object an {@code Object} representing the attribute to be bound
     *
     * @throws NullPointerException if the name parameter is {@code null}
     */
    @Override
    public void setAttribute(String name, Object object) {
        throw new UnsupportedOperationException();
    }

    /**
     * Removes the attribute with the given name from the ServletContext. After removal,
     * subsequent calls to {@link #getAttribute(String)} to retrieve the attribute's value will
     * return {@code null}.
     *
     * <p>If listeners are configured on the {@code ServletContext} the container notifies them
     * accordingly.</p>
     *
     * @param name a {@code String} specifying the name of the attribute to be
     *             removed
     */
    @Override
    public void removeAttribute(String name) {
        throw new UnsupportedOperationException();
    }

    /**
     * Returns the name of this web application corresponding to this ServletContext as specified
     * in the deployment descriptor for this web application by the display-name element.
     *
     * @return The name of the web application or null if no name has been declared in the
     *         deployment descriptor.
     *
     * @since Servlet 2.3
     */
    @Override
    public String getServletContextName() {
        throw new UnsupportedOperationException();
    }

    /**
     * Adds the servlet with the given name and class name to this servlet context.
     *
     * <p>The registered servlet may be further configured via the returned
     * {@link ServletRegistration} object.</p>
     *
     * <p>The specified {@code className} will be loaded using the classloader associated with the
     * application represented by this ServletContext.</p>
     *
     * <p>If this ServletContext already contains a preliminary ServletRegistration for a servlet
     * with the given {@code servletName}, it will be completed (by assigning the given
     * {@code className} to it) and returned.</p>
     *
     * <p>This method introspects the class with the given {@code className} for the
     * {@link ServletSecurity}, {@link MultipartConfig}, {@code javax.annotation.security.RunAs},
     * and {@code javax.annotation.security.DeclareRoles} annotations. In addition, this method
     * supports resource injection if the class with the given {@code className} represents a
     * Managed Bean. See the Java EE platform and JSR 299 specifications for additional details
     * about Managed Beans and resource injection.</p>
     *
     * @param servletName the name of the servlet
     * @param className   the fully qualified class name of the servlet
     *
     * @return a ServletRegistration object that may be used to further configure the registered
     *         servlet, or {@code null} if this ServletContext already contains a complete
     *         ServletRegistration for a servlet with the given {@code servletName}
     *
     * @throws IllegalStateException         if this ServletContext has already been initialized
     * @throws IllegalArgumentException      if {@code servletName} is {@code null} or an empty
     *                                       String
     * @throws UnsupportedOperationException if this ServletContext was passed to the
     *                                       {@link ServletContextListener
     *                                       #contextInitialized(ServletContextEvent)} method of a
     *                                       {@link ServletContextListener} that was neither
     *                                       declared in {@code web.xml} or
     *                                       {@code web-fragment.xml}, nor annotated with
     *                                       {@link WebListener}
     *
     * @since Servlet 3.0
     */
    @Override
    public ServletRegistration.Dynamic addServlet(String servletName, String className) {
        throw new UnsupportedOperationException();
    }

    /**
     * Registers the given servlet instance with this ServletContext under the given
     * {@code servletName}.
     *
     * <p>The registered servlet may be further configured via the returned
     * {@link ServletRegistration} object.</p>
     *
     * <p>If this ServletContext already contains a preliminary ServletRegistration for a servlet
     * with the given {@code servletName}, it will be completed (by assigning the class name of
     * the given servlet instance to it) and returned.</p>
     *
     * @param servletName the name of the servlet
     * @param servlet     the servlet instance to register
     *
     * @return a ServletRegistration object that may be used to further configure the given
     *         servlet, or {@code null} if this ServletContext already contains a complete
     *         ServletRegistration for a servlet with the given {@code servletName} or if the same
     *         servlet instance has already been registered with this or another ServletContext in
     *         the same container
     *
     * @throws IllegalStateException         if this ServletContext has already been initialized
     * @throws UnsupportedOperationException if this ServletContext was passed to the
     *                                       {@link ServletContextListener
     *                                       #contextInitialized(ServletContextEvent)} method of a
     *                                       {@link ServletContextListener} that was neither
     *                                       declared in {@code web.xml} or
     *                                       {@code web-fragment.xml}, nor annotated with
     *                                       {@link WebListener}
     * @throws IllegalArgumentException      if the given servlet instance implements
     *                                       {@link SingleThreadModel}, or {@code servletName} is
     *                                       {@code null} or an empty String
     *
     * @since Servlet 3.0
     */
    @Override
    public ServletRegistration.Dynamic addServlet(String servletName, Servlet servlet) {
        throw new UnsupportedOperationException();
    }

    /**
     * Adds the servlet with the given name and class type to this servlet context.
     *
     * <p>The registered servlet may be further configured via the returned
     * {@link ServletRegistration} object.</p>
     *
     * <p>If this ServletContext already contains a preliminary ServletRegistration for a servlet
     * with the given {@code servletName}, it will be completed (by assigning the name of the
     * given {@code servletClass} to it) and returned.</p>
     *
     * <p>This method introspects the given {@code servletClass} for the
     * {@link ServletSecurity}, {@link MultipartConfig}, {@code javax.annotation.security.RunAs},
     * and {@code javax.annotation.security.DeclareRoles} annotations. In addition, this method
     * supports resource injection if the given {@code servletClass} represents a Managed Bean.
     * See the Java EE platform and JSR 299 specifications for additional details about Managed
     * Beans and resource injection.</p>
     *
     * @param servletName  the name of the servlet
     * @param servletClass the class object from which the servlet will be instantiated
     *
     * @return a ServletRegistration object that may be used to further configure the registered
     *         servlet, or {@code null} if this ServletContext already contains a complete
     *         ServletRegistration for the given {@code servletName}
     *
     * @throws IllegalStateException         if this ServletContext has already been initialized
     * @throws IllegalArgumentException      if {@code servletName} is {@code null} or an empty
     *                                       String
     * @throws UnsupportedOperationException if this ServletContext was passed to the
     *                                       {@link ServletContextListener
     *                                       #contextInitialized(ServletContextEvent)} method of a
     *                                       {@link ServletContextListener} that was neither
     *                                       declared in {@code web.xml} or
     *                                       {@code web-fragment.xml}, nor annotated with
     *                                       {@link WebListener}
     *
     * @since Servlet 3.0
     */
    @Override
    public ServletRegistration.Dynamic addServlet(String servletName,
            Class<? extends Servlet> servletClass) {

        throw new UnsupportedOperationException();
    }

    /**
     * Adds the servlet with the given jsp file to this servlet context.
     *
     * <p>The registered servlet may be further configured via the returned
     * {@link ServletRegistration} object.</p>
     *
     * <p>If this ServletContext already contains a preliminary ServletRegistration for a servlet
     * with the given {@code servletName}, it will be completed (by assigning the given
     * {@code jspFile} to it) and returned.</p>
     *
     * @param servletName the name of the servlet
     * @param jspFile     the full path to a JSP file within the web application beginning with a
     *                    '/'.
     *
     * @return a ServletRegistration object that may be used to further configure the registered
     *         servlet, or {@code null} if this ServletContext already contains a complete
     *         ServletRegistration for a servlet with the given {@code servletName}
     *
     * @throws IllegalStateException         if this ServletContext has already been initialized
     * @throws IllegalArgumentException      if {@code servletName} is {@code null} or an empty
     *                                       String
     * @throws UnsupportedOperationException if this ServletContext was passed to the
     *                                       {@link ServletContextListener
     *                                       #contextInitialized(ServletContextEvent)} method of a
     *                                       {@link ServletContextListener} that was neither
     *                                       declared in {@code web.xml} or
     *                                       {@code web-fragment.xml}, nor annotated with
     *                                       {@link WebListener}
     *
     * @since Servlet 4.0
     */
    @Override
    public ServletRegistration.Dynamic addJspFile(String servletName, String jspFile) {
        throw new UnsupportedOperationException();
    }

    /**
     * Instantiates the given Servlet class.
     *
     * <p>The returned Servlet instance may be further customized before it is registered with this
     * ServletContext via a call to {@link #addServlet(String,Servlet)}.</p>
     *
     * <p>The given Servlet class must define a zero argument constructor, which is used to
     * instantiate it.</p>
     *
     * <p>This method introspects the given {@code clazz} for the following annotations:
     * {@link ServletSecurity}, {@link MultipartConfig}, {@code javax.annotation.security.RunAs},
     * and {@code javax.annotation.security.DeclareRoles}. In addition, this method supports
     * resource injection if the given {@code clazz} represents a Managed Bean. See the Java EE
     * platform and JSR 299 specifications for additional details about Managed Beans and resource
     * injection.</p>
     *
     * @param <T>   the class of the Servlet to create
     * @param clazz the Servlet class to instantiate
     *
     * @return the new Servlet instance
     *
     * @throws ServletException              if the given {@code clazz} fails to be instantiated
     * @throws UnsupportedOperationException if this ServletContext was passed to the
     *                                       {@link ServletContextListener
     *                                       #contextInitialized(ServletContextEvent)} method of a
     *                                       {@link ServletContextListener} that was neither
     *                                       declared in {@code web.xml} or
     *                                       {@code web-fragment.xml}, nor annotated with
     *                                       {@link WebListener}
     *
     * @since Servlet 3.0
     */
    @Override
    public <T extends Servlet> T createServlet(Class<T> clazz) throws ServletException {
        throw new UnsupportedOperationException();
    }

    /**
     * Gets the ServletRegistration corresponding to the servlet with the given
     * {@code servletName}.
     *
     * @param servletName the name of a servlet
     *
     * @return the (complete or preliminary) ServletRegistration for the servlet with the given
     *         {@code servletName}, or null if no ServletRegistration exists under that name
     *
     * @throws UnsupportedOperationException if this ServletContext was passed to the
     *                                       {@link ServletContextListener
     *                                       #contextInitialized(ServletContextEvent)} method of a
     *                                       {@link ServletContextListener} that was neither
     *                                       declared in {@code web.xml} or
     *                                       {@code web-fragment.xml}, nor annotated with
     *                                       {@link WebListener}
     *
     * @since Servlet 3.0
     */
    @Override
    public ServletRegistration getServletRegistration(String servletName) {
        throw new UnsupportedOperationException();
    }

    /**
     * Gets a (possibly empty) Map of the ServletRegistration objects (keyed by servlet name)
     * corresponding to all servlets registered with this ServletContext.
     *
     * <p>The returned Map includes the ServletRegistration objects corresponding to all declared
     * and annotated servlets, as well as the ServletRegistration objects corresponding to all
     * servlets that have been added via one of the {@code addServlet} and {@code addJspFile}
     * methods.</p>
     *
     * <p>If permitted, any changes to the returned Map must not affect this ServletContext.</p>
     *
     * @return Map of the (complete and preliminary) ServletRegistration objects corresponding to
     *         all servlets currently registered with this ServletContext
     *
     * @throws UnsupportedOperationException if this ServletContext was passed to the
     *                                       {@link ServletContextListener
     *                                       #contextInitialized(ServletContextEvent)} method of a
     *                                       {@link ServletContextListener} that was neither
     *                                       declared in {@code web.xml} or
     *                                       {@code web-fragment.xml}, nor annotated with
     *                                       {@link WebListener}
     *
     * @since Servlet 3.0
     */
    @Override
    public Map<String, ? extends ServletRegistration> getServletRegistrations() {
        throw new UnsupportedOperationException();
    }

    /**
     * Adds the filter with the given name and class name to this servlet context.
     *
     * <p>The registered filter may be further configured via the returned
     * {@link FilterRegistration} object.</p>
     *
     * <p>The specified {@code className} will be loaded using the classloader associated with the
     * application represented by this ServletContext.</p>
     *
     * <p>If this ServletContext already contains a preliminary FilterRegistration for a filter
     * with the given {@code filterName}, it will be completed (by assigning the given
     * {@code className} to it) and returned.</p>
     *
     * <p>This method supports resource injection if the class with the given {@code className}
     * represents a Managed Bean. See the Java EE platform and JSR 299 specifications for
     * additional details about Managed Beans and resource injection.</p>
     *
     * @param filterName the name of the filter
     * @param className  the fully qualified class name of the filter
     *
     * @return a FilterRegistration object that may be used to further configure the registered
     *         filter, or {@code null} if this ServletContext already contains a complete
     *         FilterRegistration for a filter with the given {@code filterName}
     *
     * @throws IllegalStateException         if this ServletContext has already been initialized
     * @throws IllegalArgumentException      if {@code servletName} is {@code null} or an empty
     *                                       String
     * @throws UnsupportedOperationException if this ServletContext was passed to the
     *                                       {@link ServletContextListener
     *                                       #contextInitialized(ServletContextEvent)} method of a
     *                                       {@link ServletContextListener} that was neither
     *                                       declared in {@code web.xml} or
     *                                       {@code web-fragment.xml}, nor annotated with
     *                                       {@link WebListener}
     *
     * @since Servlet 3.0
     */
    @Override
    public FilterRegistration.Dynamic addFilter(String filterName, String className) {
        throw new UnsupportedOperationException();
    }

    /**
     * Registers the given filter instance with this ServletContext under the given
     * {@code filterName}.
     *
     * <p>The registered filter may be further configured via the returned
     * {@link FilterRegistration} object.</p>
     *
     * <p>If this ServletContext already contains a preliminary FilterRegistration for a filter
     * with the given {@code filterName}, it will be completed (by assigning the class name of the
     * given filter instance to it) and returned.</p>
     *
     * @param filterName the name of the filter
     * @param filter     the filter instance to register
     *
     * @return a FilterRegistration object that may be used to further configure the given filter,
     *         or {@code null} if this ServletContext already contains a complete
     *         FilterRegistration for a filter with the given {@code filterName} or if the same
     *         filter instance has already been registered with this or another ServletContext in
     *         the same container
     *
     * @throws IllegalStateException         if this ServletContext has already been initialized
     * @throws IllegalArgumentException      if {@code servletName} is {@code null} or an empty
     *                                       String
     * @throws UnsupportedOperationException if this ServletContext was passed to the
     *                                       {@link ServletContextListener
     *                                       #contextInitialized(ServletContextEvent)} method of a
     *                                       {@link ServletContextListener} that was neither
     *                                       declared in {@code web.xml} or
     *                                       {@code web-fragment.xml}, nor annotated with
     *                                       {@link WebListener}
     *
     * @since Servlet 3.0
     */
    @Override
    public FilterRegistration.Dynamic addFilter(String filterName, Filter filter) {
        throw new UnsupportedOperationException();
    }

    /**
     * Adds the filter with the given name and class type to this servlet context.
     *
     * <p>The registered filter may be further configured via the returned
     * {@link FilterRegistration} object.</p>
     *
     * <p>If this ServletContext already contains a preliminary FilterRegistration for a filter
     * with the given {@code filterName}, it will be completed (by assigning the name of the given
     * {@code filterClass} to it) and returned.</p>
     *
     * <p>This method supports resource injection if the given {@code filterClass} represents a
     * Managed Bean. See the Java EE platform and JSR 299 specifications for additional details
     * about Managed Beans and resource injection.</p>
     *
     * @param filterName  the name of the filter
     * @param filterClass the class object from which the filter will be instantiated
     *
     * @return a FilterRegistration object that may be used to further configure the registered
     *         filter, or {@code null} if this ServletContext already contains a complete
     *         FilterRegistration for a filter with the given {@code filterName}
     *
     * @throws IllegalStateException         if this ServletContext has already been initialized
     * @throws IllegalArgumentException      if {@code servletName} is {@code null} or an empty
     *                                       String
     * @throws UnsupportedOperationException if this ServletContext was passed to the
     *                                       {@link ServletContextListener
     *                                       #contextInitialized(ServletContextEvent)} method of a
     *                                       {@link ServletContextListener} that was neither
     *                                       declared in {@code web.xml} or
     *                                       {@code web-fragment.xml}, nor annotated with
     *                                       {@link WebListener}
     *
     * @since Servlet 3.0
     */
    @Override
    public FilterRegistration.Dynamic addFilter(String filterName,
            Class<? extends Filter> filterClass) {

        throw new UnsupportedOperationException();
    }

    /**
     * Instantiates the given Filter class.
     *
     * <p>The returned Filter instance may be further customized before it is registered with this
     * ServletContext via a call to {@link #addFilter(String,Filter)}.</p>
     *
     * <p>The given Filter class must define a zero argument constructor, which is used to
     * instantiate it.</p>
     *
     * <p>This method supports resource injection if the given {@code clazz} represents a Managed
     * Bean. See the Java EE platform and JSR 299 specifications for additional details about
     * Managed Beans and resource injection.</p>
     *
     * @param <T>   the class of the Filter to create
     * @param clazz the Filter class to instantiate
     *
     * @return the new Filter instance
     *
     * @throws ServletException              if the given {@code clazz} fails to be instantiated
     * @throws UnsupportedOperationException if this ServletContext was passed to the
     *                                       {@link ServletContextListener
     *                                       #contextInitialized(ServletContextEvent)} method of a
     *                                       {@link ServletContextListener} that was neither
     *                                       declared in {@code web.xml} or
     *                                       {@code web-fragment.xml}, nor annotated with
     *                                       {@link WebListener}
     *
     * @since Servlet 3.0
     */
    @Override
    public <T extends Filter> T createFilter(Class<T> clazz) throws ServletException {
        throw new UnsupportedOperationException();
    }

    /**
     * Gets the FilterRegistration corresponding to the filter with the given {@code filterName}.
     *
     * @param filterName the name of a filter
     *
     * @return the (complete or preliminary) FilterRegistration for the filter with the given
     *         {@code filterName}, or null if no FilterRegistration exists under that name
     *
     * @throws UnsupportedOperationException if this ServletContext was passed to the
     *                                       {@link ServletContextListener
     *                                       #contextInitialized(ServletContextEvent)} method of a
     *                                       {@link ServletContextListener} that was neither
     *                                       declared in {@code web.xml} or
     *                                       {@code web-fragment.xml}, nor annotated with
     *                                       {@link WebListener}
     *
     * @since Servlet 3.0
     */
    @Override
    public FilterRegistration getFilterRegistration(String filterName) {
        throw new UnsupportedOperationException();
    }

    /**
     * Gets a (possibly empty) Map of the FilterRegistration objects (keyed by filter name)
     * corresponding to all filters registered with this ServletContext.
     *
     * <p>The returned Map includes the FilterRegistration objects corresponding to all declared
     * and annotated filters, as well as the FilterRegistration objects corresponding to all
     * filters that have been added via one of the {@code addFilter} methods.</p>
     *
     * <p>Any changes to the returned Map must not affect this ServletContext.</p>
     *
     * @return Map of the (complete and preliminary) FilterRegistration objects corresponding to
     *         all filters currently registered with this ServletContext
     *
     * @throws UnsupportedOperationException if this ServletContext was passed to the
     *                                       {@link ServletContextListener
     *                                       #contextInitialized(ServletContextEvent)} method of a
     *                                       {@link ServletContextListener} that was neither
     *                                       declared in {@code web.xml} or
     *                                       {@code web-fragment.xml}, nor annotated with
     *                                       {@link WebListener}
     *
     * @since Servlet 3.0
     */
    @Override
    public Map<String, ? extends FilterRegistration> getFilterRegistrations() {
        throw new UnsupportedOperationException();
    }

    /**
     * Gets the {@link SessionCookieConfig} object through which various properties of the session
     * tracking cookies created on behalf of this {@code ServletContext} may be configured.
     *
     * <p>Repeated invocations of this method will return the same {@code SessionCookieConfig}
     * instance.</p>
     *
     * @return the {@code SessionCookieConfig} object through which various properties of the
     *         session tracking cookies created on behalf of this {@code ServletContext} may be
     *         configured
     *
     * @throws UnsupportedOperationException if this ServletContext was passed to the
     *                                       {@link ServletContextListener
     *                                       #contextInitialized(ServletContextEvent)} method of a
     *                                       {@link ServletContextListener} that was neither
     *                                       declared in {@code web.xml} or
     *                                       {@code web-fragment.xml}, nor annotated with
     *                                       {@link WebListener}
     *
     * @since Servlet 3.0
     */
    @Override
    public SessionCookieConfig getSessionCookieConfig() {
        throw new UnsupportedOperationException();
    }

    /**
     * Sets the session tracking modes that are to become effective for this
     * {@code ServletContext}.
     *
     * <p>The given {@code sessionTrackingModes} replaces any session tracking modes set by a
     * previous invocation of this method on this {@code ServletContext}.</p>
     *
     * @param sessionTrackingModes the set of session tracking modes to become effective for this
     *                             {@code ServletContext}
     *
     * @throws IllegalStateException         if this ServletContext has already been initialized
     * @throws UnsupportedOperationException if this ServletContext was passed to the
     *                                       {@link ServletContextListener
     *                                       #contextInitialized(ServletContextEvent)} method of a
     *                                       {@link ServletContextListener} that was neither
     *                                       declared in {@code web.xml} or
     *                                       {@code web-fragment.xml}, nor annotated with
     *                                       {@link WebListener}
     * @throws IllegalArgumentException      if {@code sessionTrackingModes} specifies a
     *                                       combination of {@code SessionTrackingMode.SSL} with a
     *                                       session tracking mode other than
     *                                       {@code SessionTrackingMode.SSL}, or if
     *                                       {@code sessionTrackingModes} specifies a session
     *                                       tracking mode that is not supported by the servlet
     *                                       container
     *
     * @since Servlet 3.0
     */
    @Override
    public void setSessionTrackingModes(Set<SessionTrackingMode> sessionTrackingModes) {
        throw new UnsupportedOperationException();
    }

    /**
     * Gets the session tracking modes that are supported by default for this
     * {@code ServletContext}.
     *
     * <p>The returned set is not backed by the {@code ServletContext} object, so changes in the
     * returned set are not reflected in the {@code ServletContext} object, and vice-versa.</p>
     *
     * @return set of the session tracking modes supported by default for this
     *         {@code ServletContext}
     *
     * @throws UnsupportedOperationException if this ServletContext was passed to the
     *                                       {@link ServletContextListener
     *                                       #contextInitialized(ServletContextEvent)} method of a
     *                                       {@link ServletContextListener} that was neither
     *                                       declared in {@code web.xml} or
     *                                       {@code web-fragment.xml}, nor annotated with
     *                                       {@link WebListener}
     *
     * @since Servlet 3.0
     */
    @Override
    public Set<SessionTrackingMode> getDefaultSessionTrackingModes() {
        throw new UnsupportedOperationException();
    }

    /**
     * Gets the session tracking modes that are in effect for this {@code ServletContext}.
     *
     * <p>The session tracking modes in effect are those provided to
     * {@link #setSessionTrackingModes(Set)}.</p>
     *
     * <p>The returned set is not backed by the {@code ServletContext} object, so changes in the
     * returned set are not reflected in the {@code ServletContext} object, and vice-versa.</p>
     *
     * @return set of the session tracking modes in effect for this {@code ServletContext}
     *
     * @throws UnsupportedOperationException if this ServletContext was passed to the
     *                                       {@link ServletContextListener
     *                                       #contextInitialized(ServletContextEvent)} method of a
     *                                       {@link ServletContextListener} that was neither
     *                                       declared in {@code web.xml} or
     *                                       {@code web-fragment.xml}, nor annotated with
     *                                       {@link WebListener}
     *
     * @since Servlet 3.0
     */
    @Override
    public Set<SessionTrackingMode> getEffectiveSessionTrackingModes() {
        throw new UnsupportedOperationException();
    }

    /**
     * Adds the listener with the given class name to this ServletContext.
     *
     * <p>The class with the given name will be loaded using the classloader associated with the
     * application represented by this ServletContext, and must implement one or more of the
     * following interfaces:</p>
     *
     * <ul>
     * <li>{@link ServletContextAttributeListener}</li>
     * <li>{@link ServletRequestListener}</li>
     * <li>{@link ServletRequestAttributeListener}</li>
     * <li>{@link HttpSessionAttributeListener}</li>
     * <li>{@link HttpSessionIdListener}</li>
     * <li>{@link HttpSessionListener}</li>
     * </ul>
     *
     * <p>If this ServletContext was passed to {@link ServletContainerInitializer#onStartup}, then
     * the class with the given name may also implement {@link ServletContextListener}, in addition
     * to the interfaces listed above.</p>
     *
     * <p>As part of this method call, the container must load the class with the specified class
     * name to ensure that it implements one of the required interfaces.</p>
     *
     * <p>If the class with the given name implements a listener interface whose invocation order
     * corresponds to the declaration order (i.e., if it implements {@link ServletRequestListener},
     * {@link ServletContextListener}, or {@link HttpSessionListener}), then the new listener will
     * be added to the end of the ordered list of listeners of that interface.</p>
     *
     * <p>This method supports resource injection if the class with the given {@code className}
     * represents a Managed Bean. See the Java EE platform and JSR 299 specifications for
     * additional details about Managed Beans and resource injection.</p>
     *
     * @param className the fully qualified class name of the listener
     *
     * @throws IllegalArgumentException      if the class with the given name does not implement
     *                                       any of the above interfaces, or if it implements
     *                                       {@link ServletContextListener} and this ServletContext
     *                                       was not passed to
     *                                       {@link ServletContainerInitializer#onStartup}
     * @throws IllegalStateException         if this ServletContext has already been initialized
     * @throws UnsupportedOperationException if this ServletContext was passed to the
     *                                       {@link ServletContextListener
     *                                       #contextInitialized(ServletContextEvent)} method of a
     *                                       {@link ServletContextListener} that was neither
     *                                       declared in {@code web.xml} or
     *                                       {@code web-fragment.xml}, nor annotated with
     *                                       {@link WebListener}
     *
     * @since Servlet 3.0
     */
    @Override
    public void addListener(String className) {
        throw new UnsupportedOperationException();
    }

    /**
     * Adds the given listener to this ServletContext.
     *
     * <p>The given listener must be an instance of one or more of the following interfaces:</p>
     *
     * <ul>
     * <li>{@link ServletContextAttributeListener}</li>
     * <li>{@link ServletRequestListener}</li>
     * <li>{@link ServletRequestAttributeListener}</li>
     * <li>{@link HttpSessionAttributeListener}</li>
     * <li>{@link HttpSessionIdListener}</li>
     * <li>{@link HttpSessionListener}</li>
     * </ul>
     *
     * <p>If this ServletContext was passed to {@link ServletContainerInitializer#onStartup}, then
     * the given listener may also be an instance of {@link ServletContextListener}, in addition to
     * the interfaces listed above.</p>
     *
     * <p>If the given listener is an instance of a listener interface whose invocation order
     * corresponds to the declaration order (i.e., if it is an instance of
     * {@link ServletRequestListener}, {@link ServletContextListener}, or
     * {@link HttpSessionListener}), then the listener will be added to the end of the ordered list
     * of listeners of that interface.</p>
     *
     * @param <T> the class of the EventListener to add
     * @param t   the listener to be added
     *
     * @throws IllegalArgumentException      if the given listener is not an instance of any of the
     *                                       above interfaces, or if it is an instance of
     *                                       {@link ServletContextListener} and this ServletContext
     *                                       was not passed to
     *                                       {@link ServletContainerInitializer#onStartup}
     * @throws IllegalStateException         if this ServletContext has already been initialized
     * @throws UnsupportedOperationException if this ServletContext was passed to the
     *                                       {@link ServletContextListener
     *                                       #contextInitialized(ServletContextEvent)} method of a
     *                                       {@link ServletContextListener} that was neither
     *                                       declared in {@code web.xml} or
     *                                       {@code web-fragment.xml}, nor annotated with
     *                                       {@link WebListener}
     *
     * @since Servlet 3.0
     */
    @Override
    public <T extends EventListener> void addListener(T t) {
        throw new UnsupportedOperationException();
    }

    /**
     * Adds a listener of the given class type to this ServletContext.
     *
     * <p>The given {@code listenerClass} must implement one or more of the following
     * interfaces:</p>
     *
     * <ul>
     * <li>{@link ServletContextAttributeListener}</li>
     * <li>{@link ServletRequestListener}</li>
     * <li>{@link ServletRequestAttributeListener}</li>
     * <li>{@link HttpSessionAttributeListener}</li>
     * <li>{@link HttpSessionIdListener}</li>
     * <li>{@link HttpSessionListener}</li>
     * </ul>
     *
     * <p>If this ServletContext was passed to {@link ServletContainerInitializer#onStartup}, then
     * the given {@code listenerClass} may also implement {@link ServletContextListener}, in
     * addition to the interfaces listed above.</p>
     *
     * <p>If the given {@code listenerClass} implements a listener interface whose invocation
     * order corresponds to the declaration order (i.e., if it implements
     * {@link ServletRequestListener}, {@link ServletContextListener}, or
     * {@link HttpSessionListener}), then the new listener will be added to the end of the ordered
     * list of listeners of that interface.</p>
     *
     * <p>This method supports resource injection if the given {@code listenerClass} represents a
     * Managed Bean. See the Java EE platform and JSR 299 specifications for additional details
     * about Managed Beans and resource injection.</p>
     *
     * @param listenerClass the listener class to be instantiated
     *
     * @throws IllegalArgumentException      if the given {@code listenerClass} does not implement
     *                                       any of the above interfaces, or if it implements
     *                                       {@link ServletContextListener} and this ServletContext
     *                                       was not passed to
     *                                       {@link ServletContainerInitializer#onStartup}
     * @throws IllegalStateException         if this ServletContext has already been initialized
     * @throws UnsupportedOperationException if this ServletContext was passed to the
     *                                       {@link ServletContextListener
     *                                       #contextInitialized(ServletContextEvent)} method of a
     *                                       {@link ServletContextListener} that was neither
     *                                       declared in {@code web.xml} or
     *                                       {@code web-fragment.xml}, nor annotated with
     *                                       {@link WebListener}
     *
     * @since Servlet 3.0
     */
    @Override
    public void addListener(Class<? extends EventListener> listenerClass) {
        throw new UnsupportedOperationException();
    }

    /**
     * Instantiates the given EventListener class.
     *
     * <p>The specified EventListener class must implement at least one of the
     * {@link ServletContextListener}, {@link ServletContextAttributeListener},
     * {@link ServletRequestListener}, {@link ServletRequestAttributeListener},
     * {@link HttpSessionAttributeListener}, {@link HttpSessionIdListener}, or
     * {@link HttpSessionListener} interfaces.</p>
     *
     * <p>The returned EventListener instance may be further customized before it is registered
     * with this ServletContext via a call to {@link #addListener(EventListener)}.</p>
     *
     * <p>The given EventListener class must define a zero argument constructor, which is used to
     * instantiate it.</p>
     *
     * <p>This method supports resource injection if the given {@code clazz} represents a Managed
     * Bean. See the Java EE platform and JSR 299 specifications for additional details about
     * Managed Beans and resource injection.</p>
     *
     * @param <T>   the class of the EventListener to create
     * @param clazz the EventListener class to instantiate
     *
     * @return the new EventListener instance
     *
     * @throws ServletException              if the given {@code clazz} fails to be instantiated
     * @throws UnsupportedOperationException if this ServletContext was passed to the
     *                                       {@link ServletContextListener
     *                                       #contextInitialized(ServletContextEvent)} method of a
     *                                       {@link ServletContextListener} that was neither
     *                                       declared in {@code web.xml} or
     *                                       {@code web-fragment.xml}, nor annotated with
     *                                       {@link WebListener}
     * @throws IllegalArgumentException      if the specified EventListener class does not
     *                                       implement any of the {@link ServletContextListener},
     *                                       {@link ServletContextAttributeListener},
     *                                       {@link ServletRequestListener},
     *                                       {@link ServletRequestAttributeListener},
     *                                       {@link HttpSessionAttributeListener},
     *                                       {@link HttpSessionIdListener}, or
     *                                       {@link HttpSessionListener} interfaces.
     *
     * @since Servlet 3.0
     */
    @Override
    public <T extends EventListener> T createListener(Class<T> clazz) throws ServletException {
        throw new UnsupportedOperationException();
    }

    /**
     * Gets the {@code &lt;jsp-config&gt;} related configuration that was aggregated from the
     * {@code web.xml} and {@code web-fragment.xml} descriptor files of the web application
     * represented by this ServletContext.
     *
     * @return the {@code &lt;jsp-config&gt;} related configuration that was aggregated from the
     *         {@code web.xml} and {@code web-fragment.xml} descriptor files of the web application
     *         represented by this ServletContext, or null if no such configuration exists
     *
     * @throws UnsupportedOperationException if this ServletContext was passed to the
     *                                       {@link ServletContextListener
     *                                       #contextInitialized(ServletContextEvent)} method of a
     *                                       {@link ServletContextListener} that was neither
     *                                       declared in {@code web.xml} or
     *                                       {@code web-fragment.xml}, nor annotated with
     *                                       {@link WebListener}
     *
     * @see JspConfigDescriptor
     *
     * @since Servlet 3.0
     */
    @Override
    public JspConfigDescriptor getJspConfigDescriptor() {
        throw new UnsupportedOperationException();
    }

    /**
     * Gets the class loader of the web application represented by this ServletContext.
     *
     * <p>If a security manager exists, and the caller's class loader is not the same as, or an
     * ancestor of the requested class loader, then the security manager's {@code checkPermission}
     * method is called with a {@code RuntimePermission("getClassLoader")} permission to check
     * whether access to the requested class loader should be granted.</p>
     *
     * @return the class loader of the web application represented by this ServletContext
     *
     * @throws UnsupportedOperationException if this ServletContext was passed to the
     *                                       {@link ServletContextListener
     *                                       #contextInitialized(ServletContextEvent)} method of a
     *                                       {@link ServletContextListener} that was neither
     *                                       declared in {@code web.xml} or
     *                                       {@code web-fragment.xml}, nor annotated with
     *                                       {@link WebListener}
     * @throws SecurityException             if a security manager denies access to the requested
     *                                       class loader
     *
     * @since Servlet 3.0
     */
    @Override
    public ClassLoader getClassLoader() {
        throw new UnsupportedOperationException();
    }

    /**
     * Declares role names that are tested using {@code isUserInRole}.
     *
     * <p>Roles that are implicitly declared as a result of their use within the
     * {@link ServletRegistration.Dynamic#setServletSecurity(ServletSecurityElement)} or
     * {@link ServletRegistration.Dynamic#setRunAsRole(String)} methods of the
     * {@link ServletRegistration} interface need not be declared.</p>
     *
     * @param roleNames the role names being declared
     *
     * @throws UnsupportedOperationException if this ServletContext was passed to the
     *                                       {@link ServletContextListener
     *                                       #contextInitialized(ServletContextEvent)} method of a
     *                                       {@link ServletContextListener} that was neither
     *                                       declared in {@code web.xml} or
     *                                       {@code web-fragment.xml}, nor annotated with
     *                                       {@link WebListener}
     * @throws IllegalArgumentException      if any of the argument roleNames is null or the empty
     *                                       string
     * @throws IllegalStateException         if the ServletContext has already been initialized
     *
     * @since Servlet 3.0
     */
    @Override
    public void declareRoles(String... roleNames) {
        throw new UnsupportedOperationException();
    }

    /**
     * Returns the configuration name of the logical host on which the ServletContext is deployed.
     *
     * <p>Servlet containers may support multiple logical hosts. This method must return the same
     * name for all the servlet contexts deployed on a logical host, and the name returned by this
     * method must be distinct, stable per logical host, and suitable for use in associating server
     * configuration information with the logical host. The returned value is NOT expected or
     * required to be equivalent to a network address or hostname of the logical host.</p>
     *
     * @return a {@code String} containing the configuration name of the logical host on which the
     *         servlet context is deployed.
     *
     * @throws UnsupportedOperationException if this ServletContext was passed to the
     *                                       {@link ServletContextListener
     *                                       #contextInitialized(ServletContextEvent)} method of a
     *                                       {@link ServletContextListener} that was neither
     *                                       declared in {@code web.xml} or
     *                                       {@code web-fragment.xml}, nor annotated with
     *                                       {@link WebListener}
     *
     * @since Servlet 3.1
     */
    @Override
    public String getVirtualServerName() {
        throw new UnsupportedOperationException();
    }

    /**
     * Gets the session timeout in minutes that are supported by default for this
     * {@code ServletContext}.
     *
     * @return the session timeout in minutes that are supported by default for this
     *         {@code ServletContext}
     *
     * @throws UnsupportedOperationException if this ServletContext was passed to the
     *                                       {@link ServletContextListener
     *                                       #contextInitialized(ServletContextEvent)} method of a
     *                                       {@link ServletContextListener} that was neither
     *                                       declared in {@code web.xml} or
     *                                       {@code web-fragment.xml}, nor annotated with
     *                                       {@link WebListener}
     *
     * @since Servlet 4.0
     */
    @Override
    public int getSessionTimeout() {
        throw new UnsupportedOperationException();
    }

    /**
     * Sets the session timeout in minutes for this ServletContext.
     *
     * @param sessionTimeout session timeout in minutes
     *
     * @throws IllegalStateException         if this ServletContext has already been initialized
     * @throws UnsupportedOperationException if this ServletContext was passed to the
     *                                       {@link ServletContextListener
     *                                       #contextInitialized(ServletContextEvent)} method of a
     *                                       {@link ServletContextListener} that was neither
     *                                       declared in {@code web.xml} or
     *                                       {@code web-fragment.xml}, nor annotated with
     *                                       {@link WebListener}
     *
     * @since Servlet 4.0
     */
    @Override
    public void setSessionTimeout(int sessionTimeout) {
        throw new UnsupportedOperationException();
    }

    /**
     * Gets the request character encoding that are supported by default for this
     * {@code ServletContext}. This method returns {@code null} if no request encoding character
     * encoding has been specified in deployment descriptor or container specific configuration (for
     * all web applications in the container).
     *
     * @return the request character encoding that are supported by default for this
     *         {@code ServletContext}
     *
     * @throws UnsupportedOperationException if this ServletContext was passed to the
     *                                       {@link ServletContextListener
     *                                       #contextInitialized(ServletContextEvent)} method of a
     *                                       {@link ServletContextListener} that was neither
     *                                       declared in {@code web.xml} or
     *                                       {@code web-fragment.xml}, nor annotated with
     *                                       {@link WebListener}
     *
     * @since Servlet 4.0
     */
    @Override
    public String getRequestCharacterEncoding() {
        throw new UnsupportedOperationException();
    }

    /**
     * Sets the request character encoding for this ServletContext.
     *
     * @param encoding request character encoding
     *
     * @throws IllegalStateException         if this ServletContext has already been initialized
     * @throws UnsupportedOperationException if this ServletContext was passed to the
     *                                       {@link ServletContextListener
     *                                       #contextInitialized(ServletContextEvent)} method of a
     *                                       {@link ServletContextListener} that was neither
     *                                       declared in {@code web.xml} or
     *                                       {@code web-fragment.xml}, nor annotated with
     *                                       {@link WebListener}
     *
     * @since Servlet 4.0
     */
    @Override
    public void setRequestCharacterEncoding(String encoding) {
        throw new UnsupportedOperationException();
    }

    /**
     * Gets the response character encoding that are supported by default for this
     * {@code ServletContext}. This method returns {@code null} if no response encoding character
     * encoding has been specified in deployment descriptor or container specific configuration (for
     * all web applications in the container).
     *
     * @return the request character encoding that are supported by default for this
     *         {@code ServletContext}
     *
     * @throws UnsupportedOperationException if this ServletContext was passed to the
     *                                       {@link ServletContextListener
     *                                       #contextInitialized(ServletContextEvent)} method of a
     *                                       {@link ServletContextListener} that was neither
     *                                       declared in {@code web.xml} or
     *                                       {@code web-fragment.xml}, nor annotated with
     *                                       {@link WebListener}
     *
     * @since Servlet 4.0
     */
    @Override
    public String getResponseCharacterEncoding() {
        throw new UnsupportedOperationException();
    }

    /**
     * Sets the response character encoding for this ServletContext.
     *
     * @param encoding response character encoding
     *
     * @throws IllegalStateException         if this ServletContext has already been initialized
     * @throws UnsupportedOperationException if this ServletContext was passed to the
     *                                       {@link ServletContextListener
     *                                       #contextInitialized(ServletContextEvent)} method of a
     *                                       {@link ServletContextListener} that was neither
     *                                       declared in {@code web.xml} or
     *                                       {@code web-fragment.xml}, nor annotated with
     *                                       {@link WebListener}
     *
     * @since Servlet 4.0
     */
    @Override
    public void setResponseCharacterEncoding(String encoding) {
        throw new UnsupportedOperationException();
    }
}