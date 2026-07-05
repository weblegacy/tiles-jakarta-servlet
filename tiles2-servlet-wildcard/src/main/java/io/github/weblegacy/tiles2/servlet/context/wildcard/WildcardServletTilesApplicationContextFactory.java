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

package io.github.weblegacy.tiles2.servlet.context.wildcard;

import io.github.weblegacy.tiles2.servlet.wildcard.WildcardServletTilesApplicationContext;
import jakarta.servlet.ServletContext;
import java.util.Map;
import org.apache.tiles.Initializable;
import org.apache.tiles.TilesApplicationContext;
import org.apache.tiles.context.AbstractTilesApplicationContextFactory;

/**
 * In the {@link #createApplicationContext(Object)} method creates an instance of
 * {@link WildcardServletTilesApplicationContext}.
 *
 * @version $Rev$ $Date$
 *
 * @since 2.1.1
 *
 * @deprecated Create an instance of {@link WildcardServletTilesApplicationContext} directly.
 */
@Deprecated
public class WildcardServletTilesApplicationContextFactory extends
        AbstractTilesApplicationContextFactory implements Initializable {

    /** {@inheritDoc} */
    @Deprecated
    public void init(Map<String, String> params) {
        // Does nothing.
    }

    /** {@inheritDoc} */
    @Deprecated
    public TilesApplicationContext createApplicationContext(Object context) {
        if (context instanceof ServletContext) {
            ServletContext servletContext = (ServletContext) context;
            return new WildcardServletTilesApplicationContext(servletContext);
        }
        return null;
    }
}
