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

import jakarta.servlet.AsyncContext;
import jakarta.servlet.AsyncEvent;
import jakarta.servlet.AsyncListener;
import jakarta.servlet.DispatcherType;
import jakarta.servlet.Filter;
import jakarta.servlet.FilterChain;
import jakarta.servlet.RequestDispatcher;
import jakarta.servlet.Servlet;
import jakarta.servlet.ServletContext;
import jakarta.servlet.ServletInputStream;
import jakarta.servlet.ServletRequest;
import jakarta.servlet.ServletRequestWrapper;
import jakarta.servlet.ServletResponse;
import jakarta.servlet.ServletResponseWrapper;
import jakarta.servlet.http.HttpServletRequest;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Enumeration;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.Map.Entry;

/**
 * The mock-class for {@link ServletRequest}.
 *
 * <p>Defines an object to provide client request information to a servlet. The servlet container
 * creates a {@code ServletRequest} object and passes it as an argument to the servlet's
 * {@code service} method.</p>
 *
 * <p>A {@code ServletRequest} object provides data including parameter name and values, attributes,
 * and an input stream. Interfaces that extend {@code ServletRequest} can provide additional
 * protocol-specific data (for example, HTTP data is provided by {@link HttpServletRequest}.</p>
 *
 * @author Various
 *
 * @see HttpServletRequest
 */
public class MockServletRequest implements ServletRequest {

    /**
     * Holds all attributes of this http-session.
     */
    private final LinkedHashMap<String, Object> attributes = new LinkedHashMap<>();

    /**
     * The http-servlet-request's parameters.
     */
    private final LinkedHashMap<String, ArrayList<String>> parameters = new LinkedHashMap<>();

    /**
     * Initialize this class.
     */
    public MockServletRequest() {
    }

    /**
     * Adds a new parameter to the http-servlet-request.
     *
     * @param name  the name of the parameter
     * @param value the value of the parameter
     */
    public void addParameter(String name, String value) {
        parameters.computeIfAbsent(name, n -> new ArrayList<>()).add(value);
    }

    /**
     * Returns the value of the named attribute as an {@code Object}, or {@code null} if no
     * attribute of the given name exists.
     *
     * <p>Attributes can be set two ways. The servlet container may set attributes to make
     * available custom information about a request. For example, for requests made using HTTPS,
     * the attribute {@code javax.servlet.request.X509Certificate} can be used to retrieve
     * information on the certificate of the client. Attributes can also be set programatically
     * using {@link ServletRequest#setAttribute}. This allows information to be embedded into a
     * request before a {@link RequestDispatcher} call.</p>
     *
     * <p>Attribute names should follow the same conventions as package names. This specification
     * reserves names matching {@code java.*}, {@code javax.*}, and {@code sun.*}.</p>
     *
     * @param name a {@code String} specifying the name of the attribute
     *
     * @return an {@code Object} containing the value of the attribute, or {@code null} if the
     *         attribute does not exist
     */
    @Override
    public Object getAttribute(String name) {
        return attributes.get(name);
    }

    /**
     * Returns an {@code Enumeration} containing the names of the attributes available to this
     * request. This method returns an empty {@code Enumeration} if the request has no attributes
     * available to it.
     *
     * @return an {@code Enumeration} of strings containing the names of the request's attributes
     */
    @Override
    public Enumeration<String> getAttributeNames() {
        return Collections.enumeration(attributes.keySet());
    }

    /**
     * Returns the name of the character encoding used in the body of this request. This method
     * returns {@code null} if no request encoding character encoding has been specified. The
     * following methods for specifying the request character encoding are consulted, in decreasing
     * order of priority: per request, per web app (using
     * {@link ServletContext#setRequestCharacterEncoding(String)}, deployment descriptor), and per
     * container (for all web applications deployed in that container, using vendor specific
     * configuration).
     *
     * @return a {@code String} containing the name of the character encoding, or {@code null} if
     *         the request does not specify a character encoding
     */
    @Override
    public String getCharacterEncoding() {
        throw new UnsupportedOperationException();
    }

    /**
     * Overrides the name of the character encoding used in the body of this request. This method
     * must be called prior to reading request parameters or reading input using getReader().
     * Otherwise, it has no effect.
     *
     * @param env {@code String} containing the name of the character encoding.
     *
     * @throws UnsupportedEncodingException if this ServletRequest is still in a state where a
     *                                      character encoding may be set, but the specified
     *                                      encoding is invalid
     */
    @Override
    public void setCharacterEncoding(String env) throws UnsupportedEncodingException {
        throw new UnsupportedOperationException();
    }

    /**
     * Returns the length, in bytes, of the request body and made available by the input stream, or
     * {@code -1} if the length is not known or is greater than {link Integer#MAX_VALUE}. For HTTP
     * servlets, same as the value of the CGI variable {@code CONTENT_LENGTH}.
     *
     * @return an integer containing the length of the request body or {@code -1} if the length is
     *         not known or is greater than {@link Integer#MAX_VALUE}.
     */
    @Override
    public int getContentLength() {
        throw new UnsupportedOperationException();
    }

    /**
     * Returns the length, in bytes, of the request body and made available by the input stream, or
     * {@code -1} if the length is not known. For HTTP servlets, same as the value of the CGI
     * variable {@code CONTENT_LENGTH}.
     *
     * @return a long containing the length of the request body or {@code -1L} if the length is not
     *         known
     *
     * @since Servlet 3.1
     */
    public long getContentLengthLong() {
        throw new UnsupportedOperationException();
    }

    /**
     * Returns the MIME type of the body of the request, or {@code null} if the type is not known.
     * For HTTP servlets, same as the value of the CGI variable {@code CONTENT_TYPE}.
     *
     * @return a {@code String} containing the name of the MIME type of the request, or
     *         {@code null} if the type is not known
     */
    @Override
    public String getContentType() {
        throw new UnsupportedOperationException();
    }

    /**
     * Retrieves the body of the request as binary data using a {@link ServletInputStream}. Either
     * this method or {@link #getReader()} may be called to read the body, not both.
     *
     * @return a {@link ServletInputStream} object containing the body of the request
     *
     * @throws IllegalStateException if the {@link #getReader()} method has already been called for
     *                               this request
     * @throws IOException           if an input or output exception occurred
     */
    @Override
    public ServletInputStream getInputStream() throws IOException {
        throw new UnsupportedOperationException();
    }

    /**
     * Returns the value of a request parameter as a {@code String}, or {@code null} if the
     * parameter does not exist. Request parameters are extra information sent with the request.
     * For HTTP servlets, parameters are contained in the query string or posted form data.
     *
     * <p>You should only use this method when you are sure the parameter has only one value. If
     * the parameter might have more than one value, use {@link #getParameterValues(String)}.</p>
     *
     * <p>If you use this method with a multivalued parameter, the value returned is equal to the
     * first value in the array returned by {@code getParameterValues}.</p>
     *
     * <p>If the parameter data was sent in the request body, such as occurs with an HTTP POST
     * request, then reading the body directly via {@link #getInputStream()} or {@link #getReader()}
     * can interfere with the execution of this method.</p>
     *
     * @param name a {@code String} specifying the name of the parameter
     *
     * @return a {@code String} representing the single value of the parameter
     *
     * @see #getParameterValues(String)
     */
    @Override
    public String getParameter(String name) {
        final List<String> values = parameters.get(name);
        return values == null || values.isEmpty() ? null : values.get(0);
    }

    /**
     * Returns an {@code Enumeration} of {@code String} objects containing the names of the
     * parameters contained in this request. If the request has no parameters, the method returns
     * an empty {@code Enumeration}.
     *
     * @return an {@code Enumeration} of {@code String} objects, each {@code String} containing the
     *         name of a request parameter; or an empty {@code Enumeration} if the request has no
     *         parameters
     */
    @Override
    public Enumeration<String> getParameterNames() {
        return Collections.enumeration(parameters.keySet());
    }

    /**
     * Returns an array of {@code String} objects containing all of the values the given request
     * parameter has, or {@code null} if the parameter does not exist.
     *
     * <p>If the parameter has a single value, the array has a length of {@code 1}.</p>
     *
     * @param name a {@code String} containing the name of the parameter whose value is requested
     *
     * @return an array of {@code String} objects containing the parameter's values
     *
     * @see #getParameter(String)
     */
    @Override
    public String[] getParameterValues(String name) {
        final List<String> values = parameters.get(name);
        return values == null || values.isEmpty() ? null : values.toArray(new String[0]);
    }

    /**
     * Returns a {@link Map} of the parameters of this request.
     *
     * <p>Request parameters are extra information sent with the request. For HTTP servlets,
     * parameters are contained in the query string or posted form data.</p>
     *
     * @return an immutable Map containing parameter names as keys and parameter values as map
     *         values. The keys in the parameter map are of type String. The values in the
     *         parameter map are of type String array.
     */
    @Override
    public Map<String, String[]> getParameterMap() {
        final LinkedHashMap<String, String[]> ret = new LinkedHashMap<>(parameters.size());

        for (Entry<String, ArrayList<String>> entry : parameters.entrySet()) {
            ret.put(entry.getKey(), entry.getValue().toArray(new String[0]));
        }

        return Collections.unmodifiableMap(ret);
    }

    /**
     * Returns the name and version of the protocol the request uses in the form
     * <i>protocol/majorVersion.minorVersion</i>, for example, HTTP/1.1. For HTTP servlets, the
     * value returned is the same as the value of the CGI variable {@code SERVER_PROTOCOL}.
     *
     * @return a {@code String} containing the protocol name and version number
     */
    @Override
    public String getProtocol() {
        throw new UnsupportedOperationException();
    }

    /**
     * Returns the name of the scheme used to make this request, for example, {@code http},
     * {@code https}, or {@code ftp}. Different schemes have different rules for constructing URLs,
     * as noted in <a href="https://datatracker.ietf.org/doc/html/rfc1738">RFC 1738</a>.
     *
     * @return a {@code String} containing the name of the scheme used to make this request
     */
    @Override
    public String getScheme() {
        throw new UnsupportedOperationException();
    }

    /**
     * Returns the host name of the server to which the request was sent. It is the value of the
     * part before ":" in the {@code Host} header value, if any, or the resolved server name, or
     * the server IP address.
     *
     * @return a {@code String} containing the name of the server
     */
    @Override
    public String getServerName() {
        throw new UnsupportedOperationException();
    }

    /**
     * Returns the port number to which the request was sent. It is the value of the part after ":"
     * in the {@code Host} header value, if any, or the server port where the client connection was
     * accepted on.
     *
     * @return an integer specifying the port number
     */
    @Override
    public int getServerPort() {
        throw new UnsupportedOperationException();
    }

    /**
     * Retrieves the body of the request as character data using a {@code BufferedReader}. The
     * reader translates the character data according to the character encoding used on the body.
     * Either this method or {@link #getInputStream()} may be called to read the body, not both.
     *
     * @return a {@code BufferedReader} containing the body of the request
     *
     * @throws UnsupportedEncodingException if the character set encoding used is not supported and
     *                                      the text cannot be decoded
     * @throws IllegalStateException        if {@link #getInputStream()} method has been called on
     *                                      this request
     * @throws IOException                  if an input or output exception occurred
     *
     * @see #getInputStream()
     */
    @Override
    public BufferedReader getReader() throws IOException {
        throw new UnsupportedOperationException();
    }

    /**
     * Returns the Internet Protocol (IP) address of the client or last proxy that sent the
     * request. For HTTP servlets, same as the value of the CGI variable {@code REMOTE_ADDR}.
     *
     * @return a {@code String} containing the IP address of the client that sent the request
     */
    @Override
    public String getRemoteAddr() {
        throw new UnsupportedOperationException();
    }

    /**
     * Returns the fully qualified name of the client or the last proxy that sent the request. If
     * the engine cannot or chooses not to resolve the hostname (to improve performance), this
     * method returns the dotted-string form of the IP address. For HTTP servlets, same as the
     * value of the CGI variable {@code REMOTE_HOST}.
     *
     * @return a {@code String} containing the fully qualified name of the client
     */
    @Override
    public String getRemoteHost() {
        throw new UnsupportedOperationException();
    }

    /**
     * Stores an attribute in this request. Attributes are reset between requests. This method is
     * most often used in conjunction with {@link RequestDispatcher}.
     *
     * <p>Attribute names should follow the same conventions as package names.<br>
     * If the object passed in is {@code null}, the effect is the same as calling
     * {@link #removeAttribute(String)}.<br>
     * It is warned that when the request is dispatched from the servlet resides in a different web
     * application by {@code RequestDispatcher}, the object set by this method may not be correctly
     * retrieved in the caller servlet.</p>
     *
     * @param name a {@code String} specifying the name of the attribute
     * @param o    the {@code Object} to be stored
     */
    @Override
    public void setAttribute(String name, Object o) {
        if (name == null) {
            throw new IllegalArgumentException("Attribute name cannot be null");
        }

        if (o == null) {
            removeAttribute(name);
            return;
        }

        attributes.put(name, o);
    }

    /**
     * Removes an attribute from this request. This method is not generally needed as attributes
     * only persist as long as the request is being handled.
     *
     * <p>Attribute names should follow the same conventions as package names. Names beginning with
     * {@code java.*}, {@code javax.*}, and {@code com.sun.*}, are reserved for use by Sun
     * Microsystems.</p>
     *
     * @param name a {@code String} specifying the name of the attribute to remove
     */
    @Override
    public void removeAttribute(String name) {
        attributes.remove(name);
    }

    /**
     * Returns the preferred {@code Locale} that the client will accept content in, based on the
     * {@code Accept-Language} header. If the client request doesn't provide an
     * {@code Accept-Language} header, this method returns the default locale for the server.
     *
     * @return the preferred {@code Locale} for the client
     */
    @Override
    public Locale getLocale() {
        throw new UnsupportedOperationException();
    }

    /**
     * Returns an {@code Enumeration} of {@code Locale} objects indicating, in decreasing order
     * starting with the preferred locale, the locales that are acceptable to the client based on
     * the {@code Accept-Language} header. If the client request doesn't provide an
     * {@code Accept-Language} header, this method returns an {@code Enumeration} containing one
     * {@code Locale}, the default locale for the server.
     *
     * @return an {@code Enumeration} of preferred {@code Locale} objects for the client
     */
    @Override
    public Enumeration<Locale> getLocales() {
        throw new UnsupportedOperationException();
    }

    /**
     * Returns a boolean indicating whether this request was made using a secure channel, such as
     * HTTPS.
     *
     * @return a boolean indicating if the request was made using a secure channel
     */
    @Override
    public boolean isSecure() {
        throw new UnsupportedOperationException();
    }

    /**
     * Returns a {@link RequestDispatcher} object that acts as a wrapper for the resource located
     * at the given path. A {@code RequestDispatcher} object can be used to forward a request to
     * the resource or to include the resource in a response. The resource can be dynamic or
     * static.
     *
     * <p>The pathname specified may be relative, although it cannot extend outside the current
     * servlet context. If the path begins with a "/" it is interpreted as relative to the current
     * context root. This method returns {@code null} if the servlet container cannot return a
     * {@code RequestDispatcher}.</p>
     *
     * <p>The difference between this method and {@link ServletContext#getRequestDispatcher(String)}
     * is that this method can take a relative path.</p>
     *
     * @param path a {@code String} specifying the pathname to the resource. If it is relative, it
     *             must be relative against the current servlet.
     *
     * @return a {@code RequestDispatcher} object that acts as a wrapper for the resource at the
     *         specified path, or {@code null} if the servlet container cannot return a
     *         {@code RequestDispatcher}
     *
     * @see RequestDispatcher
     * @see ServletContext#getRequestDispatcher(String)
     */
    @Override
    public RequestDispatcher getRequestDispatcher(String path) {
        throw new UnsupportedOperationException();
    }

    /**
     * Applies alias rules to the specified virtual path in URL path format, that is,
     * {@code /dir/dir/filename.ext}. Returns a String representing the corresponding real path in
     * the format that is appropriate for the machine (including the proper path separators) that
     * the servlet engine is running on.
     *
     * <p>Returns {@code null} if the translation could not be performed for any reason.</p>
     *
     * @param path the path for which the real path is to be returned.
     *
     * @return the <i>real</i> path, or {@code >null} if the translation cannot be performed.
     *
     * @deprecated As of Version 2.1 of the Java Servlet API, use
     *             {@link ServletContext#getRealPath(String)} instead.
     */
    @Override
    @Deprecated
    public String getRealPath(String path) {
        throw new UnsupportedOperationException();
    }

    /**
     * Returns the Internet Protocol (IP) source port of the client or last proxy that sent the
     * request.
     *
     * @return an integer specifying the port number
     *
     * @since Servlet 2.4
     */
    @Override
    public int getRemotePort() {
        throw new UnsupportedOperationException();
    }

    /**
     * Returns the host name of the Internet Protocol (IP) interface on which the request was
     * received.
     *
     * @return a {@code String} containing the host name of the IP on which the request was
     *         received.
     *
     * @since Servlet 2.4
     */
    @Override
    public String getLocalName() {
        throw new UnsupportedOperationException();
    }

    /**
     * Returns the Internet Protocol (IP) address of the interface on which the request was
     * received.
     *
     * @return a {@code String} containing the IP address on which the request was received.
     *
     * @since Servlet 2.4
     */
    @Override
    public String getLocalAddr() {
        throw new UnsupportedOperationException();
    }

    /**
     * Returns the Internet Protocol (IP) port number of the interface on which the request was
     * received.
     *
     * @return an integer specifying the port number
     *
     * @since Servlet 2.4
     */
    @Override
    public int getLocalPort() {
        throw new UnsupportedOperationException();
    }

    /**
     * Gets the servlet context to which this ServletRequest was last dispatched.
     *
     * @return the servlet context to which this ServletRequest was last dispatched
     *
     * @since Servlet 3.0
     */
    public ServletContext getServletContext() {
        throw new UnsupportedOperationException();
    }

    /**
     * Puts this request into asynchronous mode, and initializes its {@link AsyncContext} with the
     * original (unwrapped) ServletRequest and ServletResponse objects.
     *
     * <p>Calling this method will cause committal of the associated response to be delayed until
     * {@link AsyncContext#complete()} is called on the returned {@link AsyncContext}, or the
     * asynchronous operation has timed out.</p>
     *
     * <p>Calling {@link AsyncContext#hasOriginalRequestAndResponse()} on the returned AsyncContext
     * will return {@code true}. Any filters invoked in the <i>outbound</i> direction after this
     * request was put into asynchronous mode may use this as an indication that any request and/or
     * response wrappers that they added during their <i>inbound</i> invocation need not stay
     * around for the duration of the asynchronous operation, and therefore any of their associated
     * resources may be released.</p>
     *
     * <p>This method clears the list of {@link AsyncListener} instances (if any) that were
     * registered with the AsyncContext returned by the previous call to one of the startAsync
     * methods, after calling each AsyncListener at its
     * {@link AsyncListener#onStartAsync(AsyncEvent)} method.</p>
     *
     * <p>Subsequent invocations of this method, or its overloaded variant, will return the same
     * AsyncContext instance, reinitialized as appropriate.</p>
     *
     * @return the (re)initialized AsyncContext
     *
     * @throws IllegalStateException if this request is within the scope of a filter or servlet
     *                               that does not support asynchronous operations (that is,
     *                               {@link #isAsyncSupported()} returns false), or if this method
     *                               is called again without any asynchronous dispatch (resulting
     *                               from one of the {@link AsyncContext#dispatch} methods), is
     *                               called outside the scope of any such dispatch, or is called
     *                               again within the scope of the same dispatch, or if the response
     *                               has already been closed
     *
     * @see AsyncContext#dispatch()
     *
     * @since Servlet 3.0
     */
    public AsyncContext startAsync() throws IllegalStateException {
        throw new UnsupportedOperationException();
    }

    /**
     * Puts this request into asynchronous mode, and initializes its {@link AsyncContext} with the
     * given request and response objects.
     *
     * <p>The ServletRequest and ServletResponse arguments must be the same instances, or instances
     * of {@link ServletRequestWrapper} and {@link ServletResponseWrapper} that wrap them, that
     * were passed to the {@link Servlet#service(ServletRequest, ServletResponse)} method of the
     * Servlet or the {@link Filter#doFilter(ServletRequest, ServletResponse, FilterChain)} method
     * of the Filter, respectively, in whose scope this method is being called.</p>
     *
     * <p>Calling this method will cause committal of the associated response to be delayed until
     * {@link AsyncContext#complete()} is called on the returned {@link AsyncContext}, or the
     * asynchronous operation has timed out.</p>
     *
     * <p>Calling {@link AsyncContext#hasOriginalRequestAndResponse()} on the returned AsyncContext
     * will return {@code false}, unless the passed in ServletRequest and ServletResponse arguments
     * are the original ones or do not carry any application-provided wrappers. Any filters invoked
     * in the <i>outbound</i> direction after this request was put into asynchronous mode may use
     * this as an indication that some of the request and/or response wrappers that they added
     * during their <i>inbound</i> invocation may need to stay in place for the duration of the
     * asynchronous operation, and their associated resources may not be released. A
     * ServletRequestWrapper applied during the <i>inbound</i> invocation of a filter may be
     * released by the <i>outbound</i> invocation of the filter only if the given
     * {@code servletRequest}, which is used to initialize the AsyncContext and will be returned by
     * a call to {@link AsyncContext#getRequest()}, does not contain said ServletRequestWrapper. The
     * same holds true for ServletResponseWrapper instances./p>
     *
     * <p>This method clears the list of {@link AsyncListener} instances (if any) that were
     * registered with the AsyncContext returned by the previous call to one of the startAsync
     * methods, after calling each AsyncListener at its
     * {@link AsyncListener#onStartAsync(AsyncEvent)} method.</p>
     *
     * <p>Subsequent invocations of this method, or its zero-argument variant, will return the same
     * AsyncContext instance, reinitialized as appropriate. If a call to this method is followed by
     * a call to its zero-argument variant, the specified (and possibly wrapped) request and
     * response objects will remain <i>locked in</i> on the returned AsyncContext.</p>
     *
     * @param servletRequest  the ServletRequest used to initialize the AsyncContext
     * @param servletResponse the ServletResponse used to initialize the AsyncContext
     *
     * @return the (re)initialized AsyncContext
     *
     * @throws IllegalStateException if this request is within the scope of a filter or servlet that
     *                               does not support asynchronous operations (that is,
     *                               {@link #isAsyncSupported()} returns false), or if this method
     *                               is called again without any asynchronous dispatch (resulting
     *                               from one of the {@link AsyncContext#dispatch} methods), is
     *                               called outside the scope of any such dispatch, or is called
     *                               again within the scope of the same dispatch, or if the response
     *                               has already been closed
     *
     * @since Servlet 3.0
     */
    public AsyncContext startAsync(ServletRequest servletRequest, ServletResponse servletResponse)
            throws IllegalStateException {

        throw new UnsupportedOperationException();
    }

    /**
     * Checks if this request has been put into asynchronous mode.
     *
     * <p>A ServletRequest is put into asynchronous mode by calling {@link #startAsync()} or
     * {@link #startAsync(ServletRequest,ServletResponse)} on it.</p>
     *
     * <p>This method returns {@code false} if this request was put into asynchronous mode, but has
     * since been dispatched using one of the {@link AsyncContext#dispatch} methods or released from
     * asynchronous mode via a call to {@link AsyncContext#complete()}.</p>
     *
     * @return {@code true} if this request has been put into asynchronous mode, {@code false}
     *         otherwise
     *
     * @since Servlet 3.0
     */
    public boolean isAsyncStarted() {
        throw new UnsupportedOperationException();
    }

    /**
     * Checks if this request supports asynchronous operation.
     *
     * <p>Asynchronous operation is disabled for this request if this request is within the scope of
     * a filter or servlet that has not been annotated or flagged in the deployment descriptor as
     * being able to support asynchronous handling.</p>
     *
     * @return {@code true} if this request supports asynchronous operation, {@code false} otherwise
     *
     * @since Servlet 3.0
     */
    public boolean isAsyncSupported() {
        throw new UnsupportedOperationException();
    }

    /**
     * Gets the AsyncContext that was created or reinitialized by the most recent invocation of
     * {@link #startAsync()} or {@link #startAsync(ServletRequest,ServletResponse)} on this request.
     *
     * @return the AsyncContext that was created or reinitialized by the most recent invocation of
     *         {@link #startAsync()} or {@link #startAsync(ServletRequest,ServletResponse)} on this
     *         request
     *
     * @throws IllegalStateException if this request has not been put into asynchronous mode, i.e.,
     *                               if neither {@link #startAsync()} nor
     *                               {@link #startAsync(ServletRequest,ServletResponse)} has been
     *                               called
     *
     * @since Servlet 3.0
     */
    public AsyncContext getAsyncContext() {
        throw new UnsupportedOperationException();
    }

    /**
     * Gets the dispatcher type of this request.
     *
     * <p>The dispatcher type of a request is used by the container to select the filters that need
     * to be applied to the request: Only filters with matching dispatcher type and url patterns
     * will be applied.</p>
     *
     * <p>Allowing a filter that has been configured for multiple dispatcher types to query a
     * request for its dispatcher type allows the filter to process the request differently
     * depending on its dispatcher type.</p>
     *
     * <p>The initial dispatcher type of a request is defined as {@code DispatcherType.REQUEST}.
     * The dispatcher type of a request dispatched via
     * {@link RequestDispatcher#forward(ServletRequest, ServletResponse)} or
     * {@link RequestDispatcher#include(ServletRequest, ServletResponse)} is given as
     * {@code DispatcherType.FORWARD} or {@code DispatcherType.INCLUDE}, respectively, while the
     * dispatcher type of an asynchronous request dispatched via one of the
     * {@link AsyncContext#dispatch} methods is given as {@code DispatcherType.ASYNC}. Finally, the
     * dispatcher type of a request dispatched to an error page by the container's error handling
     * mechanism is given as {@code DispatcherType.ERROR}.</p>
     *
     * @return the dispatcher type of this request
     *
     * @see DispatcherType
     *
     * @since Servlet 3.0
     */
    public DispatcherType getDispatcherType() {
        throw new UnsupportedOperationException();
    }
}
