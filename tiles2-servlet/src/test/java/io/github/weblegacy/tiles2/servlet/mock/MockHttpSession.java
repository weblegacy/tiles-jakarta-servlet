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

import java.util.Collections;
import java.util.Enumeration;
import java.util.LinkedHashMap;
import javax.servlet.ServletContext;
import javax.servlet.http.HttpSession;
import javax.servlet.http.HttpSessionContext;

/**
 * The mock-class for {@link HttpSession}.
 */
@SuppressWarnings("deprecation")
public class MockHttpSession implements HttpSession {

    /**
     * Holds all attributes of this http-session.
     */
    private final LinkedHashMap<String, Object> attributes = new LinkedHashMap<>();

    /**
     * Initialize this class.
     *
     * @param servletContext the {@link ServletContext} in which the caller is executing
     */
    public MockHttpSession(ServletContext servletContext) {
    }

    /**
     * Returns the time when this session was created, measured in milliseconds since midnight
     * January 1, 1970 GMT.
     *
     * @return a {@code long} specifying when this session was created, expressed in milliseconds
     *         since 1/1/1970 GMT
     *
     * @throws IllegalStateException if this method is called on an invalidated session
     */
    @Override
    public long getCreationTime() {
        throw new UnsupportedOperationException();
    }

    /**
     * Returns a string containing the unique identifier assigned to this session. The identifier
     * is assigned by the servlet container and is implementation dependent.
     *
     * @return a string specifying the identifier assigned to this session
     */
    @Override
    public String getId() {
        throw new UnsupportedOperationException();
    }

    /**
     * Returns the last time the client sent a request associated with this session, as the number
     * of milliseconds since midnight January 1, 1970 GMT, and marked by the time the container
     * received the request.
     *
     * <p>Actions that your application takes, such as getting or setting a value associated with
     * the session, do not affect the access time.</p>
     *
     * @return a {@code long} representing the last time the client sent a request associated with
     *         this session, expressed in milliseconds since 1/1/1970 GMT
     *
     * @throws IllegalStateException if this method is called on an invalidated session
     */
    @Override
    public long getLastAccessedTime() {
        throw new UnsupportedOperationException();
    }

    /**
     * Returns the ServletContext to which this session belongs.
     *
     * @return The ServletContext object for the web application
     *
     * @since 2.3
     */
    @Override
    public ServletContext getServletContext() {
        throw new UnsupportedOperationException();
    }

    /**
     * Specifies the time, in seconds, between client requests before the servlet container will
     * invalidate this session. A negative time indicates the session should never timeout.
     *
     * @param interval An integer specifying the number of seconds
     */
    @Override
    public void setMaxInactiveInterval(int interval) {
        throw new UnsupportedOperationException();
    }

    /**
     * Returns the maximum time interval, in seconds, that the servlet container will keep this
     * session open between client accesses. After this interval, the servlet container will
     * invalidate the session. The maximum time interval can be set with the
     * {@link #setMaxInactiveInterval} method. A negative time indicates the session should never
     * timeout.
     *
     * @return an integer specifying the number of seconds this session remains open between client
     *         requests
     *
     * @see #setMaxInactiveInterval
     */
    @Override
    public int getMaxInactiveInterval() {
        throw new UnsupportedOperationException();
    }

    /**
     * Returns the context object within which sessions on the server are held.
     *
     * @deprecated As of Version 2.1, this method is deprecated and has no replacement. It will be
     *             removed in a future version of the Java Servlet API.
     */
    @Override
    @Deprecated
    public HttpSessionContext getSessionContext() {
        throw new UnsupportedOperationException();
    }

    /**
     * Returns the object bound with the specified name in this session, or {@code null} if no
     * object is bound under the name.
     *
     * @param name a string specifying the name of the object
     *
     * @return the object with the specified name
     *
     * @throws IllegalStateException if this method is called on an invalidated session
     */
    @Override
    public Object getAttribute(String name) {
        return attributes.get(name);
    }

    /**
     * Returns the object bound to a given name in the session. Returns {@code null} if there is no
     * such binding.
     *
     * <p>This method must throw an {@link IllegalStateException} if it is called after this
     * session has been invalidated.</p>
     *
     * @param name a string specifying the name of the object
     *
     * @return the object with the specified name
     *
     * @throws IllegalStateException if this method is called on an invalidated session
     *
     * @deprecated As of Version 2.2, this method is replaced by {@link #getAttribute}.
     */
    @Override
    @Deprecated
    public Object getValue(String name) {
        throw new UnsupportedOperationException();
    }

    /**
     * Returns an {@code Enumeration} of {@code String} objects containing the names of all the
     * objects bound to this session.
     *
     * @return an {@code Enumeration} of {@code String} objects specifying the names of all the
     *         objects bound to this session
     *
     * @throws IllegalStateException if this method is called on an invalidated session
     */
    @Override
    @SuppressWarnings("rawtypes")
    public Enumeration getAttributeNames() {
        return Collections.enumeration(attributes.keySet());
    }

    /**
     * Returns an array of the names of all the values bound into this session.
     *
     * <p>This method should throw an {@link IllegalStateException} if it is called after this
     * session has been invalidated.</p>
     *
     * @return an array of {@code String} objects specifying the names of all the objects bound to
     *         this session
     *
     * @throws IllegalStateException if this method is called on an invalidated session
     *
     * @deprecated As of Version 2.2, this method is replaced by {@link #getAttributeNames}.
     */
    @Override
    @Deprecated
    public String[] getValueNames() {
        throw new UnsupportedOperationException();
    }

    /**
     * Binds an object to this session, using the name specified. If an object of the same name is
     * already bound to the session, the object is replaced.
     *
     * <p>After this method executes, and if the new object implements
     * {@code HttpSessionBindingListener}, the container calls
     * {@code HttpSessionBindingListener.valueBound}. The container then notifies any
     * {@code HttpSessionAttributeListener}s in the web application.</p>
     *
     * <p>If an object was already bound to this session of this name that implements
     * {@code HttpSessionBindingListener}, its {@code HttpSessionBindingListener.valueUnbound}
     * method is called.</p>
     *
     * <p>If the value passed in is null, this has the same effect as calling
     * {@code removeAttribute()}.</p>
     *
     * @param name  the name to which the object is bound; cannot be null
     * @param value the object to be bound
     *
     * @throws IllegalStateException if this method is called on an invalidated session
     */
    @Override
    public void setAttribute(String name, Object value) {
        attributes.put(name, value);
    }

    /**
     * Binds the specified object into the session with the given name. Any existing binding with
     * the same name is replaced. Objects placed into the session which implement the
     * {@code HttpSessionBindingListener} interface will call its {@code valueBound} method.
     *
     * <p>Some servlet engine implementations will persist session data or distribute it amongst
     * multiple network nodes. For an object bound into the session to be distributed or persisted
     * to disk, it must implement the {@code Serializable} interface.</p>
     *
     * <p>This method should throw an {@link IllegalStateException} if it is called after this
     * session has been invalidated.</p>
     *
     * @param name  the name to which the object is bound; cannot be null
     * @param value the object to be bound; cannot be null
     *
     * @throws IllegalStateException if this method is called on an invalidated session
     *
     * @deprecated As of Version 2.2, this method is replaced by {@link #setAttribute}.
     */
    @Override
    @Deprecated
    public void putValue(String name, Object value) {
        throw new UnsupportedOperationException();
    }

    /**
     * Removes the object bound with the specified name from this session. If the session does not
     * have an object bound with the specified name, this method does nothing.
     *
     * <p>After this method executes, and if the object implements
     * {@code HttpSessionBindingListener}, the container calls
     * {@code HttpSessionBindingListener.valueUnbound}. The container then notifies any
     * {@code HttpSessionAttributeListener}s in the web application.</p>
     *
     * @param name the name of the object to remove from this session
     *
     * @throws IllegalStateException if this method is called on an invalidated session
     */
    @Override
    public void removeAttribute(String name) {
        throw new UnsupportedOperationException();
    }

    /**
     * Unbinds an object in the session with the given name. If there is no object bound to the
     * given name, this method does nothing. If the object bound to the name implements the
     * {@code HttpSessionBindingListener}, its {@code valueUnbound} method will be called.
     *
     * <p>This method should throw an {@link IllegalStateException} if it is called after this
     * session has been invalidated.</p>
     *
     * @param name the name of the object to remove from this session
     *
     * @throws IllegalStateException if this method is called on an invalidated session
     *
     * @deprecated As of Version 2.2, this method is replaced by {@link #removeAttribute}.
     */
    @Override
    @Deprecated
    public void removeValue(String name) {
        throw new UnsupportedOperationException();
    }

    /**
     * Invalidates this session then unbinds any objects bound to it.
     *
     * @throws IllegalStateException if this method is called on an already invalidated session
     */
    @Override
    public void invalidate() {
        throw new UnsupportedOperationException();
    }

    /**
     * Returns {@code true} if the client does not yet know about the session or if the client
     * chooses not to join the session. For example, if the server used only cookie-based sessions,
     * and the client had disabled the use of cookies, then a session would be new on each request.
     *
     * @return {@code true} if the server has created a session, but the client has not yet joined
     *
     * @throws IllegalStateException if this method is called on an already invalidated session
     */
    @Override
    public boolean isNew() {
        throw new UnsupportedOperationException();
    }
}
