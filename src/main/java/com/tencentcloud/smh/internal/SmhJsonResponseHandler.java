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

package com.tencentcloud.smh.internal;


import com.tencentcloud.smh.Headers;
import com.tencentcloud.smh.http.SmhHttpResponse;

import com.tencentcloud.smh.model.CommonResponse;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.InputStream;
import java.util.Map;

public class SmhJsonResponseHandler<T> extends AbstractSmhResponseHandler<T> {

    /** The SAX unmarshaller to use when handling the response from SMH */
    private Unmarshaller<T, InputStream> responseUnmarshaller;

    /** Shared logger for profiling information */
    private static final Logger log = LoggerFactory.getLogger(SmhJsonResponseHandler.class);

    /** Response headers from the processed response */
    private Map<String, String> responseHeaders;

    /**
     * Constructs a new SMH response handler that will use the specified SAX
     * unmarshaller to turn the response into an object.
     *
     * @param responseUnmarshaller
     *            The SAX unmarshaller to use on the response from SMH.
     */
    public SmhJsonResponseHandler(Unmarshaller<T, InputStream> responseUnmarshaller) {
        this.responseUnmarshaller = responseUnmarshaller;
    }

    @Override
    public SmhServiceResponse<T> handle(SmhHttpResponse response) throws Exception {
        SmhServiceResponse<T> smhResponse = new SmhServiceResponse<T>();
        responseHeaders = response.getHeaders();
        if (responseUnmarshaller != null) {
            log.trace("Beginning to parse service response JSON");
            T result = responseUnmarshaller.unmarshall(response.getStatusCode(),response.getContent());
            log.trace("Done parsing service response JSON");
            smhResponse.setResult(result);
        }
        if (smhResponse.getResult() != null && smhResponse.getResult() instanceof CommonResponse){
            ((CommonResponse)smhResponse.getResult()).setRequestId(responseHeaders.get(Headers.REQUEST_ID));
        }
        return smhResponse;
    }

    /**
     * Returns the headers from the processed response. Will return null until a
     * response has been handled.
     *
     * @return the headers from the processed response.
     */
    public Map<String, String> getResponseHeaders() {
        return responseHeaders;
    }
}
