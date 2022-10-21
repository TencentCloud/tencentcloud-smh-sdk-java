/*
 * Copyright (c) 2017-2018 THL A29 Limited, a Tencent company. All Rights Reserved.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *    http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */


package com.tencentcloud.smh.http;

public interface HttpResponseHandler<T> {
    /**
     * Accepts an HTTP response object, and returns an object of type T.
     * Individual implementations may choose to handle the response however they
     * need to, and return any type that they need to.
     *
     * @param response
     *            The HTTP response to handle, as received from an SMH service.
     *
     * @return An object of type T, as defined by individual implementations.
     *
     * @throws Exception
     *             If any problems are encountered handling the response.
     */
    public T handle(SmhHttpResponse response) throws Exception;

    /**
     * Indicates if this response handler requires that the underlying HTTP
     * connection <b>not</b> be closed automatically after the response is
     * handled.
     * 
     * For example, if the object returned by this response handler manually
     * manages the stream of data from the HTTP connection, and doesn't read all
     * the data from the connection in the {@link #handle(HttpResponse)} method,
     * this method can be used to prevent the underlying connection from being
     * prematurely closed.
     *
     * Response handlers should use this option very carefully, since it means
     * that resource cleanup is no longer handled automatically, and if
     * neglected, can result in the client runtime running out of resources for
     * new HTTP connections.
     *
     * @return True if this response handler requires that the underlying HTTP
     *         connection be left open, and not automatically closed, otherwise
     *         false.
     */
    public boolean needsConnectionLeftOpen();
}
