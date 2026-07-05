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

import java.io.IOException;
import java.io.PrintWriter;
import java.io.UnsupportedEncodingException;
import java.util.Locale;
import javax.servlet.ServletOutputStream;
import javax.servlet.ServletResponse;

/**
 * The mock-class for {@link ServletResponse}.
 *
 * <p>Defines an object to assist a servlet in sending a response to the client. The servlet
 * container creates a {@code ServletResponse} object and passes it as an argument to the servlet's
 * {@code service} method.</p>
 *
 * <p>To send binary data in a MIME body response, use the {@link ServletOutputStream} returned by
 * {@link #getOutputStream}. To send character data, use the {@code PrintWriter} object returned by
 * {@link #getWriter}. To mix binary and text data, for example, to create a multipart response, use
 * a {@code ServletOutputStream} and manage the character sections manually.</p>
 *
 * <p>The charset for the MIME body response can be specified explicitly using the
 * {@link #setCharacterEncoding} and {@link #setContentType} methods, or implicitly using the
 * {@link #setLocale} method. Explicit specifications take precedence over implicit specifications.
 * If no charset is specified, ISO-8859-1 will be used. The {@code setCharacterEncoding},
 * {@code setContentType}, or {@code setLocale} method must be called before {@code getWriter} and
 * before committing the response for the character encoding to be used.</p>
 *
 * <p>See the Internet RFCs such as <a href="https://www.ietf.org/rfc/rfc2045.txt">RFC 2045</a> for
 * more information on MIME. Protocols such as SMTP and HTTP define profiles of MIME, and those
 * standards are still evolving.</p>
 *
 * @author Various
 *
 * @see ServletOutputStream
 */
public class MockServletResponse implements ServletResponse {

    /**
     * Initialize this class.
     */
    public MockServletResponse() {
    }

    /**
     * Returns the name of the character encoding (MIME charset) used for the body sent in this
     * response. The character encoding may have been specified explicitly using the
     * {@link #setCharacterEncoding} or {@link #setContentType} methods, or implicitly using the
     * {@link #setLocale} method. Explicit specifications take precedence over implicit
     * specifications. Calls made to these methods after {@code getWriter} has been called or after
     * the response has been committed have no effect on the character encoding. If no character
     * encoding has been specified, {@code ISO-8859-1} is returned.
     *
     * <p>See <a href="https://datatracker.ietf.org/doc/html/rfc2047">RFC 2047</a> for more
     * information about character encoding and MIME.</p>
     *
     * @return a {@code String} specifying the name of the character encoding, for example,
     *         {@code UTF-8}
     */
    @Override
    public String getCharacterEncoding() {
        throw new UnsupportedOperationException();
    }

    /**
     * Returns the content type used for the MIME body sent in this response. The content type
     * proper must have been specified using {@link #setContentType} before the response is
     * committed. If no content type has been specified, this method returns null. If a content
     * type has been specified, and a character encoding has been explicitly or implicitly
     * specified as described in {@link #getCharacterEncoding} or {@link #getWriter} has been
     * called, the charset parameter is included in the string returned. If no character encoding
     * has been specified, the charset parameter is omitted.
     *
     * @return a {@code String} specifying the content type, for example,
     *         {@code text/html; charset=UTF-8}, or null
     *
     * @since Servlet 2.4
     */
    @Override
    public String getContentType() {
        throw new UnsupportedOperationException();
    }

    /**
     * Returns a {@link ServletOutputStream} suitable for writing binary data in the response. The
     * servlet container does not encode the binary data.
     *
     * <p>Calling {@code flush()} on the {@link ServletOutputStream} commits the response.</p>
     *
     * <p>Either this method or {@link #getWriter} may be called to write the body, not both.</p>
     *
     * @return a {@link ServletOutputStream} for writing binary data
     *
     * @throws IllegalStateException if the {@code getWriter} method has been called on this
     *                               response
     * @throws IOException           if an input or output exception occurred
     *
     * @see #getWriter
     */
    @Override
    public ServletOutputStream getOutputStream() throws IOException {
        throw new UnsupportedOperationException();
    }

    /**
     * Returns a {@code PrintWriter} object that can send character text to the client. The
     * {@code PrintWriter} uses the character encoding returned by {@link #getCharacterEncoding}.
     * If the response's character encoding has not been specified as described in
     * {@code getCharacterEncoding} (i.e., the method just returns the default value
     * {@code ISO-8859-1}), {@code getWriter} updates it to {@code ISO-8859-1}.
     *
     * <p>Calling {@code flush()} on the {@link PrintWriter} commits the response.</p>
     *
     * <p>Either this method or {@link #getOutputStream} may be called to write the body, not
     * both.</p>
     *
     * @return a {@code PrintWriter} object that can return character data to the client
     *
     * @throws UnsupportedEncodingException if the character encoding returned by
     *                                      {@code getCharacterEncoding} cannot be used
     * @throws IllegalStateException        if the {@code getOutputStream} method has already been
     *                                      called for this response object
     * @throws IOException                  if an input or output exception occurred
     *
     * @see #getOutputStream
     * @see #setCharacterEncoding
     */
    @Override
    public PrintWriter getWriter() throws IOException {
        throw new UnsupportedOperationException();
    }

    /**
     * Sets the character encoding (MIME charset) of the response being sent to the client, for
     * example, to UTF-8. If the character encoding has already been set by {@link #setContentType}
     * or {@link #setLocale}, this method overrides it. Calling {@link #setContentType} with the
     * {@code String} of {@code text/html} and calling this method with the {@code String} of
     * {@code UTF-8} is equivalent with calling {@code setContentType} with the {@code String} of
     * {@code text/html; charset=UTF-8}.
     *
     * <p>This method can be called repeatedly to change the character encoding. This method has no
     * effect if it is called after {@code getWriter} has been called or after the response has
     * been committed.</p>
     *
     * <p>Containers must communicate the character encoding used for the servlet response's writer
     * to the client if the protocol provides a way for doing so. In the case of HTTP, the
     * character encoding is communicated as part of the {@code Content-Type} header for text media
     * types. Note that the character encoding cannot be communicated via HTTP headers if the
     * servlet does not specify a content type; however, it is still used to encode text written
     * via the servlet response's writer.</p>
     *
     * @param charset a String specifying only the character set defined by IANA
     *                <a
     *                href="https://www.iana.org/assignments/character-sets/character-sets.xhtml">
     *                Character Sets</a>
     *
     * @see #setContentType #setLocale
     *
     * @since Servlet 2.4
     */
    @Override
    public void setCharacterEncoding(String charset) {
        throw new UnsupportedOperationException();
    }

    /**
     * Sets the length of the content body in the response In HTTP servlets, this method sets the
     * HTTP Content-Length header.
     *
     * @param len an integer specifying the length of the content being returned to the client;
     *            sets the Content-Length header
     */
    @Override
    public void setContentLength(int len) {
        throw new UnsupportedOperationException();
    }

    /**
     * Sets the content type of the response being sent to the client, if the response has not been
     * committed yet. The given content type may include a character encoding specification, for
     * example, {@code text/html;charset=UTF-8}. The response's character encoding is only set from
     * the given content type if this method is called before {@code getWriter} is called.
     *
     * <p>This method may be called repeatedly to change content type and character encoding. This
     * method has no effect if called after the response has been committed. It does not set the
     * response's character encoding if it is called after {@code getWriter} has been called or
     * after the response has been committed.</p>
     *
     * <p>Containers must communicate the content type and the character encoding used for the
     * servlet response's writer to the client if the protocol provides a way for doing so. In the
     * case of HTTP, the {@code Content-Type} header is used.</p>
     *
     * @param type a {@code String} specifying the MIME type of the content
     *
     * @see #setLocale
     * @see #setCharacterEncoding
     * @see #getOutputStream
     * @see #getWriter
     */
    @Override
    public void setContentType(String type) {
        throw new UnsupportedOperationException();
    }

    /**
     * Sets the preferred buffer size for the body of the response. The servlet container will use
     * a buffer at least as large as the size requested. The actual buffer size used can be found
     * using {@code getBufferSize}.
     *
     * <p>A larger buffer allows more content to be written before anything is actually sent, thus
     * providing the servlet with more time to set appropriate status codes and headers. A smaller
     * buffer decreases server memory load and allows the client to start receiving data more
     * quickly.</p>
     *
     * <p>This method must be called before any response body content is written; if content has
     * been written or the response object has been committed, this method throws an
     * {@link IllegalStateException}.</p>
     *
     * @param size the preferred buffer size
     *
     * @throws IllegalStateException if this method is called after content has been written
     *
     * @see #getBufferSize
     * @see #flushBuffer
     * @see #isCommitted
     * @see #reset
     */
    @Override
    public void setBufferSize(int size) {
        throw new UnsupportedOperationException();
    }

    /**
     * Returns the actual buffer size used for the response. If no buffering is used, this method
     * returns 0.
     *
     * @return the actual buffer size used
     *
     * @see #setBufferSize
     * @see #flushBuffer
     * @see #isCommitted
     * @see #reset
     */
    @Override
    public int getBufferSize() {
        throw new UnsupportedOperationException();
    }

    /**
     * Forces any content in the buffer to be written to the client. A call to this method
     * automatically commits the response, meaning the status code and headers will be written.
     *
     * @see #setBufferSize
     * @see #getBufferSize
     * @see #isCommitted
     * @see #reset
     */
    @Override
    public void flushBuffer() throws IOException {
        throw new UnsupportedOperationException();
    }

    /**
     * Clears the content of the underlying buffer in the response without clearing headers or
     * status code. If the response has been committed, this method throws an
     * {@link IllegalStateException}.
     *
     * @see #setBufferSize
     * @see #getBufferSize
     * @see #isCommitted
     * @see #reset
     *
     * @since Servlet 2.3
     */
    @Override
    public void resetBuffer() {
        throw new UnsupportedOperationException();
    }

    /**
     * Returns a boolean indicating if the response has been committed. A committed response has
     * already had its status code and headers written.
     *
     * @return a boolean indicating if the response has been committed
     *
     * @see #setBufferSize
     * @see #getBufferSize
     * @see #flushBuffer
     * @see #reset
     */
    @Override
    public boolean isCommitted() {
        throw new UnsupportedOperationException();
    }

    /**
     * Clears any data that exists in the buffer as well as the status code and headers. If the
     * response has been committed, this method throws an {@link IllegalStateException}.
     *
     * @throws IllegalStateException if the response has already been committed
     *
     * @see #setBufferSize
     * @see #getBufferSize
     * @see #flushBuffer
     * @see #isCommitted
     */
    @Override
    public void reset() {
        throw new UnsupportedOperationException();
    }

    /**
     * Sets the locale of the response, if the response has not been committed yet. It also sets
     * the response's character encoding appropriately for the locale, if the character encoding
     * has not been explicitly set using {@link #setContentType} or {@link #setCharacterEncoding},
     * {@code getWriter} hasn't been called yet, and the response hasn't been committed yet. If the
     * deployment descriptor contains a {@code locale-encoding-mapping-list} element, and that
     * element provides a mapping for the given locale, that mapping is used. Otherwise, the
     * mapping from locale to character encoding is container dependent.
     *
     * <p>This method may be called repeatedly to change locale and character encoding. The method
     * has no effect if called after the response has been committed. It does not set the
     * response's character encoding if it is called after {@link #setContentType} has been called
     * with a charset specification, after {@link #setCharacterEncoding} has been called, after
     * {@code getWriter} has been called, or after the response has been committed.</p>
     *
     * <p>Containers must communicate the locale and the character encoding used for the servlet
     * response's writer to the client if the protocol provides a way for doing so. In the case of
     * HTTP, the locale is communicated via the {@code Content-Language} header, the character
     * encoding as part of the {@code Content-Type} header for text media types. Note that the
     * character encoding cannot be communicated via HTTP headers if the servlet does not specify a
     * content type; however, it is still used to encode text written via the servlet response's
     * writer.</p>
     *
     * @param loc the locale of the response
     *
     * @see #getLocale
     * @see #setContentType
     * @see #setCharacterEncoding
     */
    @Override
    public void setLocale(Locale loc) {
        throw new UnsupportedOperationException();
    }

    /**
     * Returns the locale specified for this response using the {@link #setLocale} method. Calls
     * made to {@code setLocale} after the response is committed have no effect. If no locale has
     * been specified, the container's default locale is returned.
     *
     * @see #setLocale
     */
    @Override
    public Locale getLocale() {
        throw new UnsupportedOperationException();
    }
}
