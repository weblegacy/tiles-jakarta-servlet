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

import java.io.BufferedReader;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.security.Principal;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Enumeration;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.Map.Entry;
import javax.servlet.RequestDispatcher;
import javax.servlet.ServletContext;
import javax.servlet.ServletInputStream;
import javax.servlet.ServletRequest;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import javax.servlet.http.HttpSessionContext;
import javax.servlet.http.HttpUtils;

/**
 * The mock-class for {@link HttpServletRequest}.
 */
@SuppressWarnings("deprecation")
public class MockHttpServletRequest implements HttpServletRequest {

    /**
     * The http-servlet-request's parameters.
     */
    private final LinkedHashMap<String, ArrayList<String>> parameters = new LinkedHashMap<>();

    /**
     * The http-servlet-request's parameters.
     */
    private final LinkedHashMap<String, ArrayList<String>> headers = new LinkedHashMap<>();

    /**
     * Holds all attributes of this http-session.
     */
    private final LinkedHashMap<String, Object> attributes = new LinkedHashMap<>();

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
     * Adds a new parameter to the http-servlet-request.
     *
     * @param name  the name of the parameter
     * @param value the value of the parameter
     */
    public void addParameter(String name, String value) {
        parameters.computeIfAbsent(name, n -> new ArrayList<>()).add(value);
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
    @SuppressWarnings("rawtypes")
    public Enumeration getHeaders(String name) {
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
    @SuppressWarnings("rawtypes")
    public Enumeration getHeaderNames() {
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
     * @see #isRequestedSessionIdValid
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
     * <p>To reconstruct an URL with a scheme and host, use {@link HttpUtils#getRequestURL}.</p>
     *
     * @return a {@code String} containing the part of the URL from the protocol name up to the
     *         query string
     *
     * @see HttpUtils#getRequestURL
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
     * <p>If this request has been forwarded using {@link RequestDispatcher#forward}, the server
     * path in the reconstructed URL must reflect the path used to obtain the
     * {@link RequestDispatcher}, and not the server path specified by the client.</p>
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
     * Checks whether the requested session ID is still valid.
     *
     * <p>If the client did not specify any session ID, this method returns {@code false}.</p>
     *
     * @return {@code true} if this request has an id for a valid session in the current session
     *         context; {@code false} otherwise
     *
     * @see #getRequestedSessionId
     * @see #getSession
     * @see HttpSessionContext
     */
    @Override
    public boolean isRequestedSessionIdValid() {
        throw new UnsupportedOperationException();
    }

    /**
     * Checks whether the requested session ID came in as a cookie.
     *
     * @return {@code true} if the session ID came in as a cookie; otherwise, {@code false}
     *
     * @see #getSession
     */
    @Override
    public boolean isRequestedSessionIdFromCookie() {
        throw new UnsupportedOperationException();
    }

    /**
     * Checks whether the requested session ID came in as part of the request URL.
     *
     * @return {@code true} if the session ID came in as part of a URL; otherwise, {@code false}
     *
     * @see #getSession
     */
    @Override
    public boolean isRequestedSessionIdFromURL() {
        throw new UnsupportedOperationException();
    }

    /**
     * Returns {@code true} if the session id for this request was provided from the client as part
     * of a URL; {@code false} otherwise. Note that the spelling URL in the method name indicates
     * that the method is new.
     *
     * @deprecated As of Version 2.1 of the Java Servlet API, use
     *             {@link #isRequestedSessionIdFromURL} instead.
     */
    @Override
    @Deprecated
    public boolean isRequestedSessionIdFromUrl() {
        throw new UnsupportedOperationException();
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
    @SuppressWarnings("rawtypes")
    public Enumeration getAttributeNames() {
        return Collections.enumeration(attributes.keySet());
    }

    /**
     * Returns the name of the character encoding used in the body of this request. This method
     * returns {@code null} if the request does not specify a character encoding.
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
     * {@code -1} if the length is not known. For HTTP servlets, same as the value of the CGI
     * variable {@code CONTENT_LENGTH}.
     *
     * @return an integer containing the length of the request body or {@code -1} if the length is
     *         not known
     */
    @Override
    public int getContentLength() {
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
     * this method or {@link #getReader} may be called to read the body, not both.
     *
     * @return a {@link ServletInputStream} object containing the body of the request
     *
     * @throws IllegalStateException if the {@link #getReader} method has already been called for
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
     * the parameter might have more than one value, use {@link #getParameterValues}.</p>
     *
     * <p>If you use this method with a multivalued parameter, the value returned is equal to the
     * first value in the array returned by {@code getParameterValues}.</p>
     *
     * <p>If the parameter data was sent in the request body, such as occurs with an HTTP POST
     * request, then reading the body directly via {@link #getInputStream} or {@link #getReader}
     * can interfere with the execution of this method.</p>
     *
     * @param name a {@code String} specifying the name of the parameter
     *
     * @return a {@code String} representing the single value of the parameter
     *
     * @see #getParameterValues
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
    @SuppressWarnings("rawtypes")
    public Enumeration getParameterNames() {
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
     * @see #getParameter
     */
    @Override
    public String[] getParameterValues(String name) {
        final List<String> values = parameters.get(name);
        return values == null || values.isEmpty() ? null : values.toArray(new String[0]);
    }

    /**
     * Returns a Map of the parameters of this request. Request parameters are extra information
     * sent with the request. For HTTP servlets, parameters are contained in the query string or
     * posted form data.
     *
     * @return an immutable Map containing parameter names as keys and parameter values as map
     *         values. The keys in the parameter map are of type String. The values in the
     *         parameter map are of type String array.
     */
    @Override
    @SuppressWarnings("rawtypes")
    public Map getParameterMap() {
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
     * Either this method or {@link #getInputStream} may be called to read the body, not both.
     *
     * @return a {@code BufferedReader} containing the body of the request
     *
     * @throws UnsupportedEncodingException if the character set encoding used is not supported and
     *                                      the text cannot be decoded
     * @throws IllegalStateException        if {@link #getInputStream} method has been called on
     *                                      this request
     * @throws IOException                  if an input or output exception occurred
     *
     * @see #getInputStream
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
     * <p>Attribute names should follow the same conventions as package names. Names beginning with
     * {@code java.*}, {@code javax.*}, and {@code com.sun.*}, are reserved for use by Sun
     * Microsystems.<br>
     * If the object passed in is {@code null}, the effect is the same as calling
     * {@link #removeAttribute}.<br>
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
    @SuppressWarnings("rawtypes")
    public Enumeration getLocales() {
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
     * <p>The difference between this method and {@link ServletContext#getRequestDispatcher} is
     * that this method can take a relative path.</p>
     *
     * @param path a {@code String} specifying the pathname to the resource. If it is relative, it
     *             must be relative against the current servlet.
     *
     * @return a {@code RequestDispatcher} object that acts as a wrapper for the resource at the
     *         specified path, or {@code null} if the servlet container cannot return a
     *         {@code RequestDispatcher}
     *
     * @see RequestDispatcher
     * @see ServletContext#getRequestDispatcher
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
     * @deprecated As of Version 2.1 of the Java Servlet API, use
     *             {@link ServletContext#getRealPath} instead.
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
     * @since 2.4
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
     * @since 2.4
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
     * @since 2.4
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
     * @since 2.4
     */
    @Override
    public int getLocalPort() {
        throw new UnsupportedOperationException();
    }
}
