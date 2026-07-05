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

import jakarta.servlet.RequestDispatcher;
import jakarta.servlet.ServletContext;
import jakarta.servlet.ServletException;
import jakarta.servlet.ServletRequest;
import jakarta.servlet.ServletResponse;
import jakarta.servlet.annotation.MultipartConfig;
import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import jakarta.servlet.http.HttpSessionContext;
import jakarta.servlet.http.HttpUpgradeHandler;
import jakarta.servlet.http.HttpUtils;
import jakarta.servlet.http.Part;
import jakarta.servlet.http.WebConnection;
import java.io.IOException;
import java.security.Principal;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.Enumeration;
import java.util.LinkedHashMap;
import java.util.List;

/**
 * The mock-class for {@link HttpServletRequest}.
 *
 * <p>Extends the {@link ServletRequest} interface to provide request information for HTTP
 * servlets.</p>
 *
 * <p>The servlet container creates an {@code HttpServletRequest} object and passes it as an
 * argument to the servlet's service methods ({@code doGet}, {@code doPost}, etc).</p>
 *
 * @author Various
 */
public class MockHttpServletRequest extends MockServletRequest implements HttpServletRequest {

    /**
     * The http-servlet-request's parameters.
     */
    private final LinkedHashMap<String, ArrayList<String>> headers = new LinkedHashMap<>();

    /**
     * The current {@code HttpSession} associated with this request.
     */
    private HttpSession session;

    /**
     * Initialize this class.
     */
    public MockHttpServletRequest() {
        this(null);
    }

    /**
     * Initialize this class.
     *
     * @param session the current {@code HttpSession} associated with this request
     */
    public MockHttpServletRequest(HttpSession session) {
        this.session = session;
    }

    /**
     * Adds a new header to the http-servlet-request.
     *
     * @param name  the name of the header
     * @param value the value of the header
     */
    public void addHeader(String name, String value) {
        headers.computeIfAbsent(name, n -> new ArrayList<>()).add(value);
    }

    /**
     * Returns the name of the authentication scheme used to protect the servlet. All servlet
     * containers support basic, form and client certificate authentication, and may additionally
     * support digest authentication. If the servlet is not authenticated {@code null} is returned.
     *
     * <p>Same as the value of the CGI variable {@code AUTH_TYPE}.</p>
     *
     * @return one of the static members {@code BASIC_AUTH}, {@code FORM_AUTH},
     *         {@code CLIENT_CERT_AUTH}, {@code DIGEST_AUTH} (suitable for == comparison) or the
     *         container-specific string indicating the authentication scheme, or {@code null} if
     *         the request was not authenticated.
     */
    @Override
    public String getAuthType() {
        throw new UnsupportedOperationException();
    }

    /**
     * Returns an array containing all of the {@code Cookie} objects the client sent with this
     * request. This method returns {@code null} if no cookies were sent.
     *
     * @return an array of all the {@code Cookies} included with this request, or {@code null} if
     *         the request has no cookies
     */
    @Override
    public Cookie[] getCookies() {
        throw new UnsupportedOperationException();
    }

    /**
     * Returns the value of the specified request header as a {@code long} value that represents a
     * {@code Date} object. Use this method with headers that contain dates, such as
     * {@code If-Modified-Since}.
     *
     * <p>The date is returned as the number of milliseconds since January 1, 1970 GMT. The header
     * name is case insensitive.</p>
     *
     * <p>If the request did not have a header of the specified name, this method returns
     * {@code -1}. If the header can't be converted to a date, the method throws an
     * {@link IllegalArgumentException}.</p>
     *
     * @param name a {@code String} specifying the name of the header
     *
     * @return a {@code long} value representing the date specified in the header expressed as the
     *         number of milliseconds since January 1, 1970 GMT, or {@code -1} if the named header
     *         was not included with the request
     *
     * @throws IllegalArgumentException If the header value can't be converted to a date
     */
    @Override
    public long getDateHeader(String name) {
        throw new UnsupportedOperationException();
    }

    /**
     * Returns the value of the specified request header as a {@code String}. If the request did
     * not include a header of the specified name, this method returns {@code null}. If there are
     * multiple headers with the same name, this method returns the first head in the request. The
     * header name is case insensitive. You can use this method with any request header.
     *
     * @param name a {@code String} specifying the header name
     *
     * @return a {@code String} containing the value of the requested header, or {@code null} if
     *         the request does not have a header of that name
     */
    @Override
    public String getHeader(String name) {
        final List<String> values = headers.get(name);
        return values == null || values.isEmpty() ? null : values.get(0);
    }

    /**
     * Returns all the values of the specified request header as an {@code Enumeration} of
     * {@code String} objects.
     *
     * <p>Some headers, such as {@code Accept-Language} can be sent by clients as several headers
     * each with a different value rather than sending the header as a comma separated list.</p>
     *
     * <p>If the request did not include any headers of the specified name, this method returns an
     * empty {@link Enumeration}. The header name is case insensitive. You can use this method with
     * any request header.</p>
     *
     * @param name a {@link String} specifying the header name
     *
     * @return an {@link Enumeration} containing the values of the requested header. If the request
     *         does not have any headers of that name return an empty enumeration. If the container
     *         does not allow access to header information, return {@code null}
     */
    @Override
    public Enumeration<String> getHeaders(String name) {
        final List<String> values = headers.get(name);
        return values == null || values.isEmpty()
                ? Collections.emptyEnumeration()
                : Collections.enumeration(values);
    }

    /**
     * Returns an enumeration of all the header names this request contains. If the request has no
     * headers, this method returns an empty enumeration.
     *
     * <p>Some servlet containers do not allow servlets to access headers using this method, in
     * which case this method returns {@code null}.</p>
     *
     * @return an enumeration of all the header names sent with this request; if the request has no
     *         headers, an empty enumeration; if the servlet container does not allow servlets to
     *         use this method, {@code null}
     */
    @Override
    public Enumeration<String> getHeaderNames() {
        return Collections.enumeration(headers.keySet());
    }

    /**
     * Returns the value of the specified request header as an {@code int}. If the request does not
     * have a header of the specified name, this method returns {@code -1}. If the header cannot be
     * converted to an integer, this method throws a {@link NumberFormatException}.
     *
     * <p>The header name is case insensitive.</p>
     *
     * @param name a {@code String} specifying the name of a request header
     *
     * @return an integer expressing the value of the request header or {@code -1} if the request
     *         doesn't have a header of this name
     *
     * @throws NumberFormatException If the header value can't be converted to an {@code int}
     */
    @Override
    public int getIntHeader(String name) {
        throw new UnsupportedOperationException();
    }

    /**
     * Returns the name of the HTTP method with which this request was made, for example,
     * {@code GET}, {@code POST}, or {@code PUT}. Same as the value of the CGI variable
     * {@code REQUEST_METHOD}.
     *
     * @return a {@code String} specifying the name of the method with which this request was made
     */
    @Override
    public String getMethod() {
        throw new UnsupportedOperationException();
    }

    /**
     * Returns any extra path information associated with the URL the client sent when it made this
     * request. The extra path information follows the servlet path but precedes the query string
     * and will start with a "/" character.
     *
     * <p>This method returns {@code null} if there was no extra path information.</p>
     *
     * <p>Same as the value of the CGI variable {@code PATH_INFO}.</p>
     *
     * @return a {@code String}, decoded by the web container, specifying extra path information
     *         that comes after the servlet path but before the query string in the request URL; or
     *         {@code null} if the URL does not have any extra path information
     */
    @Override
    public String getPathInfo() {
        throw new UnsupportedOperationException();
    }

    /**
     * Returns any extra path information after the servlet name but before the query string, and
     * translates it to a real path. Same as the value of the CGI variable {@code PATH_TRANSLATED}.
     *
     * <p>If the URL does not have any extra path information, this method returns {@code null} or
     * the servlet container cannot translate the virtual path to a real path for any reason (such
     * as when the web application is executed from an archive).</p>
     *
     * <p>The web container does not decode this string.</p>
     *
     * @return a {@code String} specifying the real path, or {@code null} if the URL does not have
     *         any extra path information
     */
    @Override
    public String getPathTranslated() {
        throw new UnsupportedOperationException();
    }

    /**
     * Returns the portion of the request URI that indicates the context of the request. The
     * context path always comes first in a request URI. The path starts with a "/" character but
     * does not end with a "/" character. For servlets in the default (root) context, this method
     * returns "". The container does not decode this string.
     *
     * <p>It is possible that a servlet container may match a context by more than one context
     * path. In such cases this method will return the actual context path used by the request and
     * it may differ from the path returned by the {@link ServletContext#getContextPath()} method.
     * The context path returned by {@link ServletContext#getContextPath()} should be considered as
     * the prime or preferred context path of the application.</p>
     *
     * @return a {@code String} specifying the portion of the request URI that indicates the
     *         context of the request
     *
     * @see ServletContext#getContextPath()
     */
    @Override
    public String getContextPath() {
        throw new UnsupportedOperationException();
    }

    /**
     * Returns the query string that is contained in the request URL after the path. This method
     * returns {@code null} if the URL does not have a query string. Same as the value of the CGI
     * variable {@code QUERY_STRING}.
     *
     * @return a {@code String} containing the query string or {@code null} if the URL contains no
     *         query string. The value is not decoded by the container.
     */
    @Override
    public String getQueryString() {
        throw new UnsupportedOperationException();
    }

    /**
     * Returns the login of the user making this request, if the user has been authenticated, or
     * {@code null} if the user has not been authenticated. Whether the user name is sent with each
     * subsequent request depends on the browser and type of authentication. Same as the value of
     * the CGI variable {@code REMOTE_USER}.
     *
     * @return a {@code String} specifying the login of the user making this request, or
     *         {@code null} if the user login is not known
     */
    @Override
    public String getRemoteUser() {
        throw new UnsupportedOperationException();
    }

    /**
     * Returns a boolean indicating whether the authenticated user is included in the specified
     * logical "role". Roles and role membership can be defined using deployment descriptors. If
     * the user has not been authenticated, the method returns {@code false}.
     *
     * <p>The role name “*” should never be used as an argument in calling {@code isUserInRole}. Any
     * call to {@code isUserInRole} with “*” must return {@code false}.<br>
     * If the role-name of the security-role to be tested is “**”, and the application has NOT
     * declared an application security-role with role-name “**”, {@code isUserInRole} must only
     * return {@code true} if the user has been authenticated; that is, only when
     * {@link #getRemoteUser()} and {@link #getUserPrincipal()} would both return a non-null value.
     * Otherwise, the container must check the user for membership in the application role.</p>
     *
     * @param role a {@code String} specifying the name of the role
     *
     * @return a {@code boolean} indicating whether the user making this request belongs to a given
     *         role; {@code false} if the user has not been authenticated
     */
    @Override
    public boolean isUserInRole(String role) {
        throw new UnsupportedOperationException();
    }

    /**
     * Returns a {@code Principal} object containing the name of the current authenticated user. If
     * the user has not been authenticated, the method returns {@code null}.
     *
     * @return a {@code Principal} containing the name of the user making this request;
     *         {@code null} if the user has not been authenticated
     */
    @Override
    public Principal getUserPrincipal() {
        throw new UnsupportedOperationException();
    }

    /**
     * Returns the session ID specified by the client. This may not be the same as the ID of the
     * current valid session for this request. If the client did not specify a session ID, this
     * method returns {@code null}.
     *
     * @return a {@code String} specifying the session ID, or {@code null} if the request did not
     *         specify a session ID
     *
     * @see #isRequestedSessionIdValid()
     */
    @Override
    public String getRequestedSessionId() {
        throw new UnsupportedOperationException();
    }

    /**
     * Returns the part of this request's URL from the protocol name up to the query string in the
     * first line of the HTTP request. The web container does not decode this String. For example:
     *
     * <table>
     *   <caption>Examples of Returned Values</caption>
     *   <tr>
     *     <th>First line of HTTP request</th>
     *     <th>Returned Value</th>
     *   </tr>
     *   <tr>
     *     <td>POST /some/path.html HTTP/1.1</td>
     *     <td>/some/path.html</td>
     *   </tr>
     *   <tr>
     *     <td>GET http://foo.bar/a.html HTTP/1.0</td>
     *     <td>/a.html</td>
     *   </tr>
     *   <tr>
     *     <td>HEAD /xyz?a=b HTTP/1.1</td>
     *     <td>/xyz</td>
     *   </tr>
     * </table>
     *
     * <p>To reconstruct an URL with a scheme and host, use
     * {@link HttpUtils#getRequestURL(HttpServletRequest)}.</p>
     *
     * @return a {@code String} containing the part of the URL from the protocol name up to the
     *         query string
     *
     * @see HttpUtils#getRequestURL(HttpServletRequest)
     */
    @Override
    public String getRequestURI() {
        throw new UnsupportedOperationException();
    }

    /**
     * Reconstructs the URL the client used to make the request. The returned URL contains a
     * protocol, server name, port number, and server path, but it does not include query string
     * parameters.
     *
     * <p>If this request has been forwarded using
     * {@link RequestDispatcher#forward(ServletRequest, ServletResponse)}, the server path
     * in the reconstructed URL must reflect the path used to obtain the {@link RequestDispatcher},
     * and not the server path specified by the client.</p>
     *
     * <p>Because this method returns a {@code StringBuffer}, not a string, you can modify the URL
     * easily, for example, to append query parameters.</p>
     *
     * <p>This method is useful for creating redirect messages and for reporting errors.</p>
     *
     * @return a {@code StringBuffer} object containing the reconstructed URL
     */
    @Override
    public StringBuffer getRequestURL() {
        throw new UnsupportedOperationException();
    }

    /**
     * Returns the part of this request's URL that calls the servlet. This path starts with a "/"
     * character and includes either the servlet name or a path to the servlet, but does not
     * include any extra path information or a query string. Same as the value of the CGI variable
     * {@code SCRIPT_NAME}.
     *
     * <p>This method will return an empty string ("") if the servlet used to process this request
     * was matched using the "/*" pattern.</p>
     *
     * @return a {@code String} containing the name or path of the servlet being called, as
     *         specified in the request URL, decoded, or an empty string if the servlet used to
     *         process the request is matched using the "/*" pattern.
     */
    @Override
    public String getServletPath() {
        throw new UnsupportedOperationException();
    }

    /**
     * Returns the current {@code HttpSession} associated with this request or, if there is no
     * current session and {@code create} is true, returns a new session.
     *
     * <p>If {@code create} is {@code false} and the request has no valid {@code HttpSession}, this
     * method returns {@code null}.</p>
     *
     * <p>To make sure the session is properly maintained, you must call this method before the
     * response is committed. If the container is using cookies to maintain session integrity and
     * is asked to create a new session when the response is committed, an IllegalStateException is
     * thrown.</p>
     *
     * @param create {@code true} to create a new session for this request if necessary;
     *               {@code false} to return {@code null} if there's no current session
     *
     * @return the {@code HttpSession} associated with this request or {@code null} if
     *         {@code create} is {@code false} and the request has no valid session
     *
     * @see #getSession()
     */
    @Override
    public HttpSession getSession(boolean create) {
        return session == null && create ? new MockHttpSession(new MockServletContext()) : session;
    }

    /**
     * Returns the current session associated with this request, or if the request does not have a
     * session, creates one.
     *
     * @return the {@code HttpSession} associated with this request
     *
     * @see #getSession(boolean)
     */
    @Override
    public HttpSession getSession() {
        return getSession(true);
    }

    /**
     * Change the session id of the current session associated with this request and return the new
     * session id.
     *
     * @return the new session id
     *
     * @throws IllegalStateException if there is no session associated with the request
     *
     * @since Servlet 3.1
     */
    @Override
    public String changeSessionId() {
        throw new UnsupportedOperationException();
    }

    /**
     * Checks whether the requested session ID is still valid.
     *
     * <p>If the client did not specify any session ID, this method returns {@code false}.</p>
     *
     * @return {@code true} if this request has an id for a valid session in the current session
     *         context; {@code false} otherwise
     *
     * @see #getRequestedSessionId()
     * @see #getSession()
     * @see HttpSessionContext
     */
    @Override
    public boolean isRequestedSessionIdValid() {
        throw new UnsupportedOperationException();
    }

    /**
     * Checks whether the requested session ID was conveyed to the server as an HTTP cookie.
     *
     * @return {@code true} if the session ID was conveyed to the server an an HTTP cookie;
     *         otherwise, {@code false}
     *
     * @see #getSession()
     */
    @Override
    public boolean isRequestedSessionIdFromCookie() {
        throw new UnsupportedOperationException();
    }

    /**
     * Checks whether the requested session ID was conveyed to the server as part of the request
     * URL.
     *
     * @return {@code true} if the session ID was conveyed to the server as part of a URL;
     *         otherwise, {@code false}
     *
     * @see #getSession()
     */
    @Override
    public boolean isRequestedSessionIdFromURL() {
        throw new UnsupportedOperationException();
    }

    /**
     * Checks whether the requested session ID was conveyed to the server as part of the request
     * URL.
     *
     * @return {@code true} if the session ID was conveyed to the server as part of a URL;
     *         otherwise, {@code false}
     *
     * @deprecated As of Version 2.1 of the Java Servlet API, use
     *             {@link #isRequestedSessionIdFromURL()} instead.
     */
    @Override
    @Deprecated
    public boolean isRequestedSessionIdFromUrl() {
        return isRequestedSessionIdFromURL();
    }

    /**
     * Use the container login mechanism configured for the {@code ServletContext} to authenticate
     * the user making this request.
     *
     * <p>This method may modify and commit the argument {@code HttpServletResponse}.</p>
     *
     * @param response The {@code HttpServletResponse} associated with this
     *                 {@code HttpServletRequest}
     *
     * @return {@code true} when non-null values were or have been established as the values
     *         returned by {@code getUserPrincipal}, {@code getRemoteUser}, and {@code getAuthType}.
     *         Return {@code false} if authentication is incomplete and the underlying login
     *         mechanism has committed, in the response, the message (e.g., challenge) and HTTP
     *         status code to be returned to the user.
     *
     * @throws IOException           if an input or output error occurred while reading from this
     *                               request or writing to the given response
     * @throws IllegalStateException if the login mechanism attempted to modify the response and it
     *                               was already committed
     * @throws ServletException      if the authentication failed and the caller is responsible for
     *                               handling the error (i.e., the underlying login mechanism did
     *                               NOT establish the message and HTTP status code to be returned
     *                               to the user)
     *
     * @since Servlet 3.0
     */
    @Override
    public boolean authenticate(HttpServletResponse response) throws IOException, ServletException {
        throw new UnsupportedOperationException();
    }

    /**
     * Validate the provided username and password in the password validation realm used by the web
     * container login mechanism configured for the {@code ServletContext}.
     *
     * <p>This method returns without throwing a {@code ServletException} when the login mechanism
     * configured for the {@code ServletContext} supports username password validation, and when, at
     * the time of the call to login, the identity of the caller of the request had not been
     * established (i.e, all of {@code getUserPrincipal}, {@code getRemoteUser}, and
     * {@code getAuthType} return null), and when validation of the provided credentials is
     * successful. Otherwise, this method throws a {@code ServletException} as described below.</p>
     *
     * <p>When this method returns without throwing an exception, it must have established non-null
     * values as the values returned by {@code getUserPrincipal}, {@code getRemoteUser}, and
     * {@code getAuthType}.</p>
     *
     * @param username The {@code String} value corresponding to the login identifier of the user.
     * @param password The password {@code String} corresponding to the identified user.
     *
     * @throws ServletException if the configured login mechanism does not support username password
     *                          authentication, or if a non-null caller identity had already been
     *                          established (prior to the call to login), or if validation of the
     *                          provided username and password fails.
     *
     * @since Servlet 3.0
     */
    @Override
    public void login(String username, String password) throws ServletException {
        throw new UnsupportedOperationException();
    }

    /**
     * Establish {@code null} as the value returned when {@code getUserPrincipal},
     * {@code getRemoteUser}, and {@code getAuthType} is called on the request.
     *
     * @throws ServletException if logout fails
     *
     * @since Servlet 3.0
     */
    @Override
    public void logout() throws ServletException {
        throw new UnsupportedOperationException();
    }

    /**
     * Gets all the {@link Part} components of this request, provided that it is of type
     * {@code multipart/form-data}.
     *
     * <p>If this request is of type {@code multipart/form-data}, but does not contain any
     * {@code Part} components, the returned {@code Collection} will be empty.</p>
     *
     * <p>Any changes to the returned {@code Collection} must not affect this
     * {@code HttpServletRequest}.</p>
     *
     * @return a (possibly empty) {@code Collection} of the {@code Part} components of this request
     *
     * @throws IOException           if an I/O error occurred during the retrieval of the
     *                               {@link Part} components of this request
     * @throws ServletException      if this request is not of type {@code multipart/form-data}
     * @throws IllegalStateException if the request body is larger than {@code maxRequestSize}, or
     *                               any {@code Part} in the request is larger than
     *                               {@code maxFileSize}, or there is no {@code @MultipartConfig} or
     *                               {@code multipart-config} in deployment descriptors
     *
     * @see MultipartConfig#maxFileSize()
     * @see MultipartConfig#maxRequestSize()
     *
     * @since Servlet 3.0
     */
    @Override
    public Collection<Part> getParts() throws IOException, ServletException {
        throw new UnsupportedOperationException();
    }

    /**
     * Gets the {@link Part} with the given name.
     *
     * @param name the name of the requested {@code Part}
     *
     * @return The {@code Part} with the given name, or {@code null} if this request is of type
     *         {@code multipart/form-data}, but does not contain the requested {@code Part}
     *
     * @throws IOException           if an I/O error occurred during the retrieval of the requested
     *                               {@code Part}
     * @throws ServletException      if this request is not of type {@code multipart/form-data}
     * @throws IllegalStateException if the request body is larger than {@code maxRequestSize}, or
     *                               any {@code Part} in the request is larger than
     *                               {@code maxFileSize}, or there is no {@code @MultipartConfig} or
     *                               {@code multipart-config} in deployment descriptors
     *
     * @see MultipartConfig#maxFileSize()
     * @see MultipartConfig#maxRequestSize()
     *
     * @since Servlet 3.0
     */
    @Override
    public Part getPart(String name) throws IOException, ServletException {
        throw new UnsupportedOperationException();
    }

    /**
     * Create an instance of {@code HttpUpgradeHandler} for an given class and uses it for the http
     * protocol upgrade processing.
     *
     * @param <T>          The {@code Class}, which extends {@link HttpUpgradeHandler}, of the
     *                     {@code handlerClass}.
     * @param handlerClass The {@code HttpUpgradeHandler} class used for the upgrade.
     *
     * @return an instance of the {@code HttpUpgradeHandler}
     *
     * @throws IOException      if an I/O error occurred during the upgrade
     * @throws ServletException if the given {@code handlerClass} fails to be instantiated
     *
     * @see HttpUpgradeHandler
     * @see WebConnection
     *
     * @since Servlet 3.1
     */
    @Override
    public <T extends HttpUpgradeHandler> T  upgrade(Class<T> handlerClass)
            throws IOException, ServletException {

        throw new UnsupportedOperationException();
    }
}