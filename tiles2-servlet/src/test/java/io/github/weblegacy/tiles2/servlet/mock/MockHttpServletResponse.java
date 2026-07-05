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

import jakarta.servlet.ServletResponse;
import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Collection;

/**
 * The mock-class for {@link HttpServletResponse}.
 *
 * <p>Extends the {@link ServletResponse} interface to provide HTTP-specific functionality in
 * sending a response. For example, it has methods to access HTTP headers and cookies.</p>
 *
 * <p>The servlet container creates an {@code HttpServletResponse} object and passes it as an
 * argument to the servlet's service methods ({@code doGet}, {@code doPost}, etc).</p>
 *
 * @author Various
 *
 * @see ServletResponse
 */
public class MockHttpServletResponse extends MockServletResponse implements HttpServletResponse {

    /**
     * Initialize this class.
     */
    public MockHttpServletResponse() {
    }

    /**
     * Adds the specified cookie to the response. This method can be called multiple times to set
     * more than one cookie.
     *
     * @param cookie the Cookie to return to the client
     */
    @Override
    public void addCookie(Cookie cookie) {
        throw new UnsupportedOperationException();
    }

    /**
     * Returns a boolean indicating whether the named response header has already been set.
     *
     * @param name the header name
     *
     * @return {@code true} if the named response header has already been set; {@code false}
     *         otherwise
     */
    @Override
    public boolean containsHeader(String name) {
        throw new UnsupportedOperationException();
    }

    /**
     * Encodes the specified URL by including the session ID, or, if encoding is not needed, returns
     * the URL unchanged. The implementation of this method includes the logic to determine whether
     * the session ID needs to be encoded in the URL. For example, if the browser supports cookies,
     * or session tracking is turned off, URL encoding is unnecessary.
     *
     * <p>For robust session tracking, all URLs emitted by a servlet should be run through this
     * method. Otherwise, URL rewriting cannot be used with browsers which do not support
     * cookies.</p>
     *
     * <p>If the URL is relative, it is always relative to the current HttpServletRequest.</p>
     *
     * @param url the url to be encoded.
     *
     * @return the encoded URL if encoding is needed; the unchanged URL otherwise.
     *
     * @throws IllegalArgumentException if the url is not valid
     */
    @Override
    public String encodeURL(String url) {
        throw new UnsupportedOperationException();
    }

    /**
     * Encodes the specified URL for use in the {@code sendRedirect} method or, if encoding is not
     * needed, returns the URL unchanged. The implementation of this method includes the logic to
     * determine whether the session ID needs to be encoded in the URL. For example, if the browser
     * supports cookies, or session tracking is turned off, URL encoding is unnecessary. Because the
     * rules for making this determination can differ from those used to decide whether to encode a
     * normal link, this method is separated from the {@code encodeURL} method.
     *
     * <p>All URLs sent to the {@code HttpServletResponse.sendRedirect} method should be run
     * through this method. Otherwise, URL rewriting cannot be used with browsers which do not
     * support cookies.</p>
     *
     * <p>If the URL is relative, it is always relative to the current HttpServletRequest.</p>
     *
     * @param url the url to be encoded.
     *
     * @return the encoded URL if encoding is needed; the unchanged URL otherwise.
     *
     * @throws IllegalArgumentException if the url is not valid
     *
     * @see #sendRedirect(String)
     * @see #encodeUrl(String)
     */
    @Override
    public String encodeRedirectURL(String url) {
        throw new UnsupportedOperationException();
    }

    /**
     * Encodes the URL by including the session ID in it, or if encoding is not needed, returns the
     * URL unchanged. URL encoding must be provided by the servlet engine if URL rewriting is
     * present and enabled and there is a valid session for the request that this response is part
     * of and the session is not being maintained via a cookie or other non URL means.
     *
     * <p>All URLs emitted by a servlet should be run through this method to ensure that session
     * tracking is seamless with all browsers.</p>
     *
     * @param url the url to be encoded.
     *
     * @return the encoded URL if encoding is needed; the unchanged URL otherwise.
     *
     * @throws IllegalArgumentException if the url is not valid
     *
     * @deprecated As of version 2.1, use encodeURL(String url) instead
     */
    @Override
    @Deprecated
    public String encodeUrl(String url) {
        return encodeURL(url);
    }

    /**
     * Encodes the specified URL for use in the sendRedirect method or, if encoding is not needed,
     * returns the URL unchanged. This additional encoding method is provided because the rules for
     * determining whether or not to encode the URL may be different in the redirect case. The
     * given URL must be an absolute URL. Relative URLs are not permitted and must throw an
     * {@link IllegalArgumentException}.
     *
     * <p>All URLs sent to the sendRedirect method should be run through this method to ensure that
     * session tracking is seamless with all browsers.</p>
     *
     * @param url the url to be encoded.
     *
     * @return the encoded URL if encoding is needed; the unchanged URL otherwise.
     *
     * @throws IllegalArgumentException if the url is not valid
     *
     * @deprecated As of version 2.1, use encodeRedirectURL(String url) instead
     */
    @Override
    @Deprecated
    public String encodeRedirectUrl(String url) {
        return encodeRedirectURL(url);
    }

    /**
     * Sends an error response to the client using the specified status and clears the buffer. The
     * server defaults to creating the response to look like an HTML-formatted server error page
     * containing the specified message, setting the content type to "text/html". The caller is
     * <strong>not</strong> responsible for escaping or re-encoding the message to ensure it is safe
     * with respect to the current response encoding and content type. This aspect of safety is the
     * responsibility of the container, as it is generating the error page containing the message.
     * The server will preserve cookies and may clear or update any headers needed to serve the
     * error page as a valid response.
     *
     * <p>If an error-page declaration has been made for the web application corresponding to the
     * status code passed in, it will be served back in preference to the suggested msg parameter
     * and the msg parameter will be ignored.</p>
     *
     * <p>If the response has already been committed, this method throws an
     * {@link IllegalStateException}. After using this method, the response should be considered to
     * be committed and should not be written to.</p>
     *
     * @param sc  the error status code
     * @param msg the descriptive message
     *
     * @throws IOException           If an input or output exception occurs
     * @throws IllegalStateException If the response was committed
     */
    @Override
    public void sendError(int sc, String msg) throws IOException {
        throw new UnsupportedOperationException();
    }

    /**
     * Sends an error response to the client using the specified status code and clears the buffer.
     *
     * <p>The server will preserve cookies and may clear or update any headers needed to serve the
     * error page as a valid response.</p>
     *
     * <p>If an error-page declaration has been made for the web application corresponding to the
     * status code passed in, it will be served back the error page.</p>
     *
     * <p>If the response has already been committed, this method throws an
     * {@link IllegalStateException}. After using this method, the response should be considered to
     * be committed and should not be written to.</p>
     *
     * @param sc the error status code
     *
     * @throws IOException           If an input or output exception occurs
     * @throws IllegalStateException If the response was committed before this method call
     */
    @Override
    public void sendError(int sc) throws IOException {
        throw new UnsupportedOperationException();
    }

    /**
     * Sends a temporary redirect response to the client using the specified redirect location URL
     * and clears the buffer. The buffer will be replaced with the data set by this method. Calling
     * this method sets the status code to {@link HttpServletResponse#SC_FOUND} 302 (Found).
     *
     * <p>This method can accept relative URLs;the servlet container must convert the relative URL
     * to an absolute URL before sending the response to the client. If the location is relative
     * without a leading '/' the container interprets it as relative to the current request URI. If
     * the location is relative with a leading '/' the container interprets it as relative to the
     * servlet container root. If the location is relative with two leading '/' the container
     * interprets it as a network-path reference (see
     * <a href="https://datatracker.ietf.org/doc/html/rfc3986">RFC 3986: Uniform Resource Identifier
     * (URI): Generic Syntax</a>, section 4.2 &quot;Relative Reference&quot;).</p>
     *
     * <p>If the response has already been committed, this method throws an
     * {@link IllegalStateException}. After using this method, the response should be considered to
     * be committed and should not be written to.</p>
     *
     * @param location the redirect location URL
     *
     * @throws IOException           If an input or output exception occurs
     * @throws IllegalStateException If the response was committed or if a partial URL is given and
     *                               cannot be converted into a valid URL
     */
    @Override
    public void sendRedirect(String location) throws IOException {
        throw new UnsupportedOperationException();
    }

    /**
     * Sets a response header with the given name and date-value. The date is specified in terms of
     * milliseconds since the epoch. If the header had already been set, the new value overwrites
     * the previous one. The {@code containsHeader} method can be used to test for the presence of
     * a header before setting its value.
     *
     * @param name the name of the header to set
     * @param date the assigned date value
     *
     * @see #containsHeader(String)
     * @see #addDateHeader(String, long)
     */
    @Override
    public void setDateHeader(String name, long date) {
        throw new UnsupportedOperationException();
    }

    /**
     * Adds a response header with the given name and date-value. The date is specified in terms of
     * milliseconds since the epoch. This method allows response headers to have multiple values.
     *
     * @param name the name of the header to set
     * @param date the additional date value
     *
     * @see #setDateHeader(String, long)
     */
    @Override
    public void addDateHeader(String name, long date) {
        throw new UnsupportedOperationException();
    }

    /**
     * Sets a response header with the given name and value. If the header had already been set,
     * the new value overwrites the previous one. The {@code containsHeader} method can be used to
     * test for the presence of a header before setting its value.
     *
     * @param name  the name of the header
     * @param value the header value If it contains octet string, it should be encoded according to
     *              <a href="https://datatracker.ietf.org/doc/html/rfc2047">RFC 2047</a>
     *
     * @see #containsHeader(String)
     * @see #addHeader(String, String)
     */
    @Override
    public void setHeader(String name, String value) {
        throw new UnsupportedOperationException();
    }

    /**
     * Adds a response header with the given name and value. This method allows response headers to
     * have multiple values.
     *
     * @param name  the name of the header
     * @param value the additional header value If it contains octet string, it should be encoded
     *              according to
     *              <a href="https://datatracker.ietf.org/doc/html/rfc2047">RFC 2047</a>
     *
     * @see #setHeader(String, String)
     */
    @Override
    public void addHeader(String name, String value) {
        throw new UnsupportedOperationException();
    }

    /**
     * Sets a response header with the given name and integer value. If the header had already been
     * set, the new value overwrites the previous one. The {@code containsHeader} method can be
     * used to test for the presence of a header before setting its value.
     *
     * @param name  the name of the header
     * @param value the assigned integer value
     *
     * @see #containsHeader(String)
     * @see #addIntHeader(String, int)
     */
    @Override
    public void setIntHeader(String name, int value) {
        throw new UnsupportedOperationException();
    }

    /**
     * Adds a response header with the given name and integer value. This method allows response
     * headers to have multiple values.
     *
     * @param name  the name of the header
     * @param value the assigned integer value
     *
     * @see #setIntHeader(String, int)
     */
    @Override
    public void addIntHeader(String name, int value) {
        throw new UnsupportedOperationException();
    }

    /**
     * Sets the status code for this response.
     *
     * <p>This method is used to set the return status code when there is no error (for example, for
     * the SC_OK or SC_MOVED_TEMPORARILY status codes).</p>
     *
     * <p>If this method is used to set an error code, then the container's error page mechanism
     * will not be triggered. If there is an error and the caller wishes to invoke an error page
     * defined in the web application, then {@link #sendError(int)} must be used instead.</p>
     *
     * <p>This method preserves any cookies and other response headers.</p>
     *
     * <p>Valid status codes are those in the 2XX, 3XX, 4XX, and 5XX ranges. Other status codes are
     * treated as container specific.</p>
     *
     * @param sc the status code
     *
     * @see #sendError(int)
     */
    @Override
    public void setStatus(int sc) {
        throw new UnsupportedOperationException();
    }

    /**
     * Sets the status code and message for this response.
     *
     * @param sc the status code
     * @param sm the status message
     *
     * @deprecated As of version 2.1, due to ambiguous meaning of the message parameter. To set a
     *             status code use {@link #setStatus(int)}, to send an error with a description use
     *             {@link #sendError(int, String)}.
     */
    @Override
    @Deprecated
    public void setStatus(int sc, String sm) {
        throw new UnsupportedOperationException();
    }

    /**
     * Gets the current status code of this response.
     *
     * @return the current status code of this response
     *
     * @since Servlet 3.0
     */
    @Override
    public int getStatus() {
        throw new UnsupportedOperationException();
    }

    /**
     * Gets the value of the response header with the given name.
     *
     * <p>If a response header with the given name exists and contains multiple values, the value
     * that was added first will be returned.</p>
     *
     * <p>This method considers only response headers set or added via
     * {@link #setHeader(String, String)}, {@link #addHeader(String, String)},
     * {@link #setDateHeader(String, long)}, {@link #addDateHeader(String, long)},
     * {@link #setIntHeader(String, int)}, or {@link #addIntHeader(String, int)}, respectively.</p>
     *
     * @param name the name of the response header whose value to return
     *
     * @return the value of the response header with the given name, or {@code null} if no header
     *         with the given name has been set on this response
     *
     * @since Servlet 3.0
     */
    @Override
    public String getHeader(String name) {
        throw new UnsupportedOperationException();
    }

    /**
     * Gets the values of the response header with the given name.
     *
     * <p>This method considers only response headers set or added via
     * {@link #setHeader(String, String)}, {@link #addHeader(String, String)},
     * {@link #setDateHeader(String, long)}, {@link #addDateHeader(String, long)},
     * {@link #setIntHeader(String, int)}, or {@link #addIntHeader(String, int)}, respectively.</p>
     *
     * <p>Any changes to the returned {@code Collection} must not affect this
     * {@code HttpServletResponse}.</p>
     *
     * @param name the name of the response header whose values to return
     *
     * @return a (possibly empty) {@code Collection} of the values of the response header with the
     *         given name
     *
     * @since Servlet 3.0
     */
    @Override
    public Collection<String> getHeaders(String name) {
        throw new UnsupportedOperationException();
    }

    /**
     * Gets the names of the headers of this response.
     *
     * <p>This method considers only response headers set or added via
     * {@link #setHeader(String, String)}, {@link #addHeader(String, String)},
     * {@link #setDateHeader(String, long)}, {@link #addDateHeader(String, long)},
     * {@link #setIntHeader(String, int)}, or {@link #addIntHeader(String, int)}, respectively.</p>
     *
     * <p>Any changes to the returned {@code Collection} must not affect this
     * {@code HttpServletResponse}.</p>
     *
     * @return a (possibly empty) {@code Collection} of the names of the headers of this response
     *
     * @since Servlet 3.0
     */
    @Override
    public Collection<String> getHeaderNames() {
        throw new UnsupportedOperationException();
    }
}
