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

import com.tencentcloud.smh.event.ProgressListener;
import com.tencentcloud.smh.internal.SmhServiceRequest;

import java.io.InputStream;
import java.util.HashMap;
import java.util.Map;

public class SmhHttpRequest<T extends SmhServiceRequest> {

    /** The resource path being requested */
    private String resourcePath;


    private Map<String, Object> parameters = new HashMap<String, Object>();

    /** Map of the headers included in this request */
    private Map<String, String> headers = new HashMap<String, String>();
    // HTTP protocol
    private HttpProtocol protocol;
    /** The service endpoint to which this request should be sent */
    private String endpoint;

    /** The HTTP method to use when sending this request. */
    private HttpMethodName httpMethod = HttpMethodName.POST;

    /** An optional stream from which to read the request payload. */
    private InputStream content;

    private T originRequest;
    
    private ProgressListener progressListener;

//    private String ciSpecialEndParameter;

    public SmhHttpRequest(T originRequest) {
        this.originRequest = originRequest;
//        this.ciSpecialEndParameter = originRequest.getCiSpecialEndParameter();
    }

    public void addHeader(String name, String value) {
        headers.put(name, value);
    }

    public Map<String, String> getHeaders() {
        return headers;
    }

    public void setResourcePath(String resourcePath) {
        this.resourcePath = resourcePath;
    }

    public String getResourcePath() {
        return resourcePath;
    }

    public void addParameter(String name, Object value) {
        parameters.put(name, value);
    }

    public Map<String, Object> getParameters() {
        return parameters;
    }

    public SmhHttpRequest<T> withParameter(String name, String value) {
        addParameter(name, value);
        return this;
    }
    
    public HttpProtocol getProtocol() {
        return protocol;
    }

    public void setProtocol(HttpProtocol protocol) {
        this.protocol = protocol;
    }

    public HttpMethodName getHttpMethod() {
        return httpMethod;
    }

    public void setHttpMethod(HttpMethodName httpMethod) {
        this.httpMethod = httpMethod;
    }

    public void setEndpoint(String endpoint) {
        this.endpoint = endpoint;
    }

    public String getEndpoint() {
        return endpoint;
    }

    public void setHeaders(Map<String, String> headers) {
        this.headers.clear();
        this.headers.putAll(headers);
    }

    public void setParameters(Map<String, String> parameters) {
        this.parameters.clear();
        this.parameters.putAll(parameters);
    }

    public InputStream getContent() {
        return content;
    }

    public void setContent(InputStream content) {
        this.content = content;
    }

    public T getOriginalRequest() {
        return originRequest;
    }
    
    public ProgressListener getProgressListener() {
        return progressListener;
    }

    public void setProgressListener(ProgressListener progressListener) {
        this.progressListener = progressListener;
    }

//    public String getCiSpecialEndParameter() {
//        return ciSpecialEndParameter;
//    }
//
//    public void setCiSpecialEndParameter(String ciSpecialEndParameter) {
//        this.ciSpecialEndParameter = ciSpecialEndParameter;
//    }

    @Override
    public String toString() {
        StringBuilder strBuilder = new StringBuilder();
        strBuilder.append("endpoint: ").append(this.endpoint).append(", resourcepath: ")
                .append(this.resourcePath).append(", httpMethod: ").append(this.httpMethod);
        
        strBuilder.append(", headers { ");
        for (String headerKey : this.headers.keySet()) {
            String headerValue = this.headers.get(headerKey);
            strBuilder.append(headerKey).append(" : ").append(headerValue).append(", ");
        }
        strBuilder.append("}, params: { ");
        for (String paramKey : this.parameters.keySet()) {
            Object paramValue = this.parameters.get(paramKey);
            strBuilder.append(paramKey).append(" : ").append(paramValue).append(", ");
        }
        strBuilder.append("}");
        return strBuilder.toString();
    }
}
