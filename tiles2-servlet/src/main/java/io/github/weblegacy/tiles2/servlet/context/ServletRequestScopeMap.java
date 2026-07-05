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

import jakarta.servlet.ServletRequest;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Enumeration;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;
import org.apache.tiles.context.MapEntry;

/**
 * Private implementation of {@code Map} for servlet request attributes.
 *
 * @version $Rev$ $Date$
 */
final class ServletRequestScopeMap implements Map<String, Object> {

    /**
     * Constructor.
     *
     * @param request The request object to use.
     */
    public ServletRequestScopeMap(ServletRequest request) {
        this.request = request;
    }

    /**
     * The request object to use.
     */
    private ServletRequest request = null;

    /** {@inheritDoc} */
    @Override
    public void clear() {
        Iterator<String> keys = keySet().iterator();
        while (keys.hasNext()) {
            request.removeAttribute(keys.next());
        }
    }

    /** {@inheritDoc} */
    @Override
    public boolean containsKey(Object key) {
        return request.getAttribute(key(key)) != null;
    }

    /** {@inheritDoc} */
    @Override
    public boolean containsValue(Object value) {
        if (value == null) {
            return false;
        }
        Enumeration<String> keys = request.getAttributeNames();
        while (keys.hasMoreElements()) {
            Object next = request.getAttribute(keys.nextElement());
            if (next == value) {
                return true;
            }
        }
        return false;
    }

    /** {@inheritDoc} */
    @Override
    public Set<Map.Entry<String, Object>> entrySet() {
        Set<Map.Entry<String, Object>> set = new HashSet<>();
        Enumeration<String> keys = request.getAttributeNames();
        String key;
        while (keys.hasMoreElements()) {
            key = keys.nextElement();
            set.add(new MapEntry<>(key, request.getAttribute(key), true));
        }
        return set;
    }

    /** {@inheritDoc} */
    @Override
    public boolean equals(Object o) {
        ServletRequest otherRequest = ((ServletRequestScopeMap) o).request;
        boolean retValue = true;
        synchronized (request) {
            for (Enumeration<String> attribs = request.getAttributeNames(); attribs
                    .hasMoreElements()
                    && retValue;) {
                String attributeName = attribs.nextElement();
                retValue = request.getAttribute(attributeName).equals(
                        otherRequest.getAttribute(attributeName));
            }
        }

        return retValue;
    }

    /** {@inheritDoc} */
    @Override
    public Object get(Object key) {
        return request.getAttribute(key(key));
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
        Enumeration<String> keys = request.getAttributeNames();
        while (keys.hasMoreElements()) {
            set.add(keys.nextElement());
        }
        return set;
    }

    /** {@inheritDoc} */
    @Override
    public Object put(String key, Object value) {
        if (value == null) {
            return remove(key);
        }
        String skey = key(key);
        Object previous = request.getAttribute(skey);
        request.setAttribute(skey, value);
        return previous;
    }

    /** {@inheritDoc} */
    @Override
    public void putAll(Map<? extends String, ? extends Object> map) {
        for (String key : map.keySet()) {
            request.setAttribute(key, map.get(key));
        }
    }

    /** {@inheritDoc} */
    @Override
    public Object remove(Object key) {
        String skey = key(key);
        Object previous = request.getAttribute(skey);
        request.removeAttribute(skey);
        return previous;
    }

    /** {@inheritDoc} */
    @Override
    public int size() {
        int n = 0;
        Enumeration<String> keys = request.getAttributeNames();
        while (keys.hasMoreElements()) {
            keys.nextElement();
            n++;
        }
        return n;
    }

    /** {@inheritDoc} */
    @Override
    public Collection<Object> values() {
        List<Object> list = new ArrayList<>();
        Enumeration<String> keys = request.getAttributeNames();
        while (keys.hasMoreElements()) {
            list.add(request.getAttribute(keys.nextElement()));
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
