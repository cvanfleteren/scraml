/*
 *
 *  (C) Copyright 2015 Atomic BITS (http://atomicbits.io).
 *
 *  All rights reserved. This program and the accompanying materials
 *  are made available under the terms of the GNU Affero General Public License
 *  (AGPL) version 3.0 which accompanies this distribution, and is available in
 *  the LICENSE file or at http://www.gnu.org/licenses/agpl-3.0.en.html
 *
 *  This library is distributed in the hope that it will be useful,
 *  but WITHOUT ANY WARRANTY; without even the implied warranty of
 *  MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE. See the GNU
 *  Affero General Public License for more details.
 *
 *  Contributors:
 *      Peter Rigole
 *
 */

package io.atomicbits.scraml.client.java;

import io.atomicbits.scraml.dsl.java.RequestBuilder;
import io.atomicbits.scraml.dsl.java.rxhttpclient.RxHttpClientSupport;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by peter on 19/08/15.
 */
public class JXoClient {

    private String host;
    private int port;
    private String protocol;
    private int requestTimeout;
    private int maxConnections;
    private Map<String, String> defaultHeaders;

    // It's important that the requestBuilder is package-accessible.
    RequestBuilder requestBuilder = new RequestBuilder();

    public JXoClient(String host,
                     int port,
                     String protocol,
                     int requestTimeout,
                     int maxConnections,
                     Map<String, String> defaultHeaders) {
        this.host = host;
        this.port = port;
        this.protocol = protocol;
        this.requestTimeout = requestTimeout;
        this.maxConnections = maxConnections;
        this.defaultHeaders = defaultHeaders;

        // Have a look at how the field 'rest' is initialized. That's why we have to reuse the existing (empty) RequestBuilder.
        this.requestBuilder.initialize(
                new RequestBuilder(
                        new RxHttpClientSupport(protocol, host, port, null, requestTimeout, maxConnections, new HashMap<String, String>())
                )
        );
        this.requestBuilder.initializeChildren();
        System.out.println(this.requestBuilder.toString());
    }

    public RestResource rest = new RestResource(this.requestBuilder);

    public Map<String, String> getDefaultHeaders() {
        return defaultHeaders;
    }

    public String getHost() {
        return host;
    }

    public int getMaxConnections() {
        return maxConnections;
    }

    public int getPort() {
        return port;
    }

    public String getProtocol() {
        return protocol;
    }

    public int getRequestTimeout() {
        return requestTimeout;
    }

    public void close() {
        this.requestBuilder.getClient().close();
    }

}
