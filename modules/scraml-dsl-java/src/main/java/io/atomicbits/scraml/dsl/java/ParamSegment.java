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

package io.atomicbits.scraml.dsl.java;

/**
 * Created by peter on 19/08/15.
 */
public class ParamSegment<T> implements Segment {

    protected T value;
    // We have to initialize it empty and fill it in later to get the resource segments initialized as fields and not methods.
    protected RequestBuilder requestBuilder = new RequestBuilder();

    public ParamSegment() {

    }

    public ParamSegment(T value, RequestBuilder parentRequestBuilder) {
        this.value = value;
        this.requestBuilder.appendPathElement(value.toString());
        parentRequestBuilder.addChild(this.requestBuilder);
        // A paramsegment is initialized later than the client, so it needs to trigger the initialization of its children.
        parentRequestBuilder.initializeChildren();
    }

    public T getValue() {
        return this.value;
    }

}
