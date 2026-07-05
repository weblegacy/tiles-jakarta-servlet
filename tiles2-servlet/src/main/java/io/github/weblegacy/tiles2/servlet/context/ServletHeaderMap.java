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

package io.github.weblegacy.tiles2.servlet.context;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Enumeration;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;
import org.apache.tiles.context.MapEntry;

/**
 * Private implementation of {@code Map} for servlet request name-value.
 *
 * @version $Rev$ $Date$
 */
final class ServletHeaderMap implements Map<String, String> {

    /**
     * Constructor.
     *
     * @param request The request object to use.
     *
     * @deprecated Use {@link #ServletHeaderMap(HttpServletRequest,HttpServletResponse)} instead
     */
    @Deprecated
    public ServletHeaderMap(HttpServletRequest request) {
        this(request, null);
    }

    /**
     * Constructor.
     *
     * @param request  The request object to use.
     * @param response The response object to use.
     *
     * @since 2.2.0
     */
    public ServletHeaderMap(HttpServletRequest request, HttpServletResponse response) {
        this.request = request;
        this.response = response;
    }

    /**
     * The request object to use.
     */
    private HttpServletRequest request = null;

    /**
     * The request object to use.
     */
    private HttpServletResponse response = null;

    /** {@inheritDoc} */
    @Override
    public void clear() {
        throw new UnsupportedOperationException();
    }

    /** {@inheritDoc} */
    @Override
    public boolean containsKey(Object key) {
        return request.getHeader(key(key)) != null;
    }

    /** {@inheritDoc} */
    @Override
    public boolean containsValue(Object value) {
        for (String element : values()) {
            if (value.equals(element)) {
                return true;
            }
        }
        return false;
    }

    /** {@inheritDoc} */
    @Override
    public Set<Map.Entry<String, String>> entrySet() {
        Set<Map.Entry<String, String>> set = new HashSet<>();
        Enumeration<String> keys = request.getHeaderNames();
        String key;
        while (keys.hasMoreElements()) {
            key = keys.nextElement();
            set.add(new MapEntry<>(key, request.getHeader(key), false));
        }
        return set;
    }

    /** {@inheritDoc} */
    @Override
    public boolean equals(Object o) {
        HttpServletRequest otherRequest = ((ServletHeaderMap) o).request;
        boolean retValue = true;
        synchronized (request) {
            for (Enumeration<String> attribs = request.getHeaderNames(); attribs
                    .hasMoreElements()
                    && retValue;) {
                String parameterName = attribs.nextElement();
                retValue = request.getHeader(parameterName).equals(
                        otherRequest.getHeader(parameterName));
            }
        }

        return retValue;
    }

    /** {@inheritDoc} */
    @Override
    public String get(Object key) {
        return request.getHeader(key(key));
    }

    /** {@inheritDoc} */
    @Override
    public int hashCode() {
        return request.hashCode();
    }

    /** {@inheritDoc} */
    @Override
    public boolean isEmpty() {
        return size() < 1;
    }

    /** {@inheritDoc} */
    @Override
    public Set<String> keySet() {
        Set<String> set = new HashSet<>();
        Enumeration<String> keys = request.getHeaderNames();
        while (keys.hasMoreElements()) {
            set.add(keys.nextElement());
        }
        return set;
    }

    /** {@inheritDoc} */
    @Override
    public String put(String key, String value) {
        response.setHeader(key, value);
        return value;
    }

    /** {@inheritDoc} */
    @Override
    public void putAll(Map<? extends String, ? extends String> map) {
        for (Map.Entry<? extends String, ? extends String> entry : map
                .entrySet()) {
            response.setHeader(entry.getKey(), entry.getValue());
        }
    }

    /** {@inheritDoc} */
    @Override
    public String remove(Object key) {
        throw new UnsupportedOperationException();
    }

    /** {@inheritDoc} */
    @Override
    public int size() {
        int n = 0;
        Enumeration<String> keys = request.getHeaderNames();
        while (keys.hasMoreElements()) {
            keys.nextElement();
            n++;
        }
        return n;
    }

    /** {@inheritDoc} */
    @Override
    public Collection<String> values() {
        List<String> list = new ArrayList<>();
        Enumeration<String> keys = request.getHeaderNames();
        while (keys.hasMoreElements()) {
            list.add(request.getHeader(keys.nextElement()));
        }
        return list;
    }

    /**
     * Returns the string representation of the key.
     *
     * @param key The key.
     *
     * @return The string representation of the key.
     *
     * @throws IllegalArgumentException If the key is {@code null}.
     */
    private String key(Object key) {
        if (key == null) {
            throw new IllegalArgumentException();
        } else if (key instanceof String) {
            return (String) key;
        } else {
            return key.toString();
        }
    }
}
