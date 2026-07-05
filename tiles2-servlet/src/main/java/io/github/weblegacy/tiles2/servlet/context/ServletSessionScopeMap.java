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
import jakarta.servlet.http.HttpSession;
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
 * Private implementation of {@code Map} for HTTP session attributes.
 *
 * @version $Rev$ $Date$
 */
final class ServletSessionScopeMap implements Map<String, Object> {

    /**
     * Constructor.
     *
     * @param request The request object to use.
     */
    public ServletSessionScopeMap(HttpServletRequest request) {
        this.request = request;
    }

    /**
     * The request object to use.
     */
    private HttpServletRequest request = null;

    /** {@inheritDoc} */
    @Override
    public void clear() {
        HttpSession session = request.getSession(false);
        if (session != null) {
            Iterator<String> keys = keySet().iterator();
            while (keys.hasNext()) {
                session.removeAttribute(keys.next());
            }
        }
    }

    /** {@inheritDoc} */
    @Override
    public boolean containsKey(Object key) {
        HttpSession session = request.getSession(false);
        if (session == null) {
            return false;
        }

        return session.getAttribute(key(key)) != null;
    }

    /** {@inheritDoc} */
    @Override
    public boolean containsValue(Object value) {
        HttpSession session = request.getSession(false);
        if (session == null || value == null) {
            return false;
        }
        Enumeration<String> keys = session.getAttributeNames();
        while (keys.hasMoreElements()) {
            Object next = session.getAttribute(keys.nextElement());
            if (next == value) {
                return true;
            }
        }
        return false;
    }

    /** {@inheritDoc} */
    @Override
    public Set<Map.Entry<String, Object>> entrySet() {
        HttpSession session = request.getSession(false);
        Set<Map.Entry<String, Object>> set = new HashSet<>();
        if (session != null) {
            Enumeration<String> keys = session.getAttributeNames();
            String key;
            while (keys.hasMoreElements()) {
                key = keys.nextElement();
                set.add(new MapEntry<>(key, session.getAttribute(key), true));
            }
        }
        return set;
    }

    /** {@inheritDoc} */
    @Override
    public boolean equals(Object o) {
        boolean retValue = true;

        HttpSession session = request.getSession(false);
        synchronized (session) {
            HttpSession otherSession = ((ServletSessionScopeMap) o).request
                    .getSession(false);
            if (session == null) {
                retValue = otherSession == null;
            } else {
                for (Enumeration<String> attribs = session.getAttributeNames(); attribs
                        .hasMoreElements()
                        && retValue;) {
                    String attributeName = attribs.nextElement();
                    retValue = session.getAttribute(attributeName).equals(
                            otherSession.getAttribute(attributeName));
                }
            }
        }

        return retValue;
    }

    /** {@inheritDoc} */
    @Override
    public Object get(Object key) {
        HttpSession session = request.getSession(false);
        if (session == null) {
            return null;
        }

        return session.getAttribute(key(key));
    }

    /** {@inheritDoc} */
    @Override
    public int hashCode() {
        HttpSession session = request.getSession(false);
        if (session == null) {
            return 0;
        }

        return session.hashCode();
    }

    /** {@inheritDoc} */
    @Override
    public boolean isEmpty() {
        return size() < 1;
    }

    /** {@inheritDoc} */
    @Override
    public Set<String> keySet() {
        HttpSession session = request.getSession(false);
        Set<String> set = new HashSet<>();
        if (session != null) {
            Enumeration<String> keys = session.getAttributeNames();
            while (keys.hasMoreElements()) {
                set.add(keys.nextElement());
            }
        }
        return set;
    }

    /** {@inheritDoc} */
    @Override
    public Object put(String key, Object value) {
        HttpSession session = request.getSession();
        if (value == null) {
            return remove(key);
        }
        String skey = key(key);
        Object previous = session.getAttribute(skey);
        session.setAttribute(skey, value);
        return previous;
    }

    /** {@inheritDoc} */
    @Override
    public void putAll(Map<? extends String, ? extends Object> map) {
        HttpSession session = request.getSession();
        for (String key : map.keySet()) {
            session.setAttribute(key, map.get(key));
        }
    }

    /** {@inheritDoc} */
    @Override
    public Object remove(Object key) {
        HttpSession session = request.getSession(false);
        if (session == null) {
            return null;
        }

        String skey = key(key);
        Object previous = session.getAttribute(skey);
        session.removeAttribute(skey);
        return previous;
    }

    /** {@inheritDoc} */
    @Override
    public int size() {
        HttpSession session = request.getSession(false);
        int n = 0;
        if (session != null) {
            Enumeration<String> keys = session.getAttributeNames();
            while (keys.hasMoreElements()) {
                keys.nextElement();
                n++;
            }
        }
        return n;
    }

    /** {@inheritDoc} */
    @Override
    public Collection<Object> values() {
        HttpSession session = request.getSession(false);
        List<Object> list = new ArrayList<>();
        if (session != null) {
            Enumeration<String> keys = session.getAttributeNames();
            while (keys.hasMoreElements()) {
                list.add(session.getAttribute(keys.nextElement()));
            }
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
