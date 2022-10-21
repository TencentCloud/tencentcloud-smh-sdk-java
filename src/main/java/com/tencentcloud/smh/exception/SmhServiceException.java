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



package com.tencentcloud.smh.exception;

import java.util.HashMap;
import java.util.Map;

public class SmhServiceException  extends SmhClientException  {
    private static final long serialVersionUID = 1L;

    /**
     * Indicates who is responsible (if known) for a failed request.
     *
     * For example, if a client is using an invalid SMH access key, the returned exception will
     * indicate that there is an error in the request the caller is sending. Retrying that same
     * request will *not* result in a successful response. The Client ErrorType indicates that there
     * is a problem in the request the user is sending (ex: incorrect access keys, invalid parameter
     * value, missing parameter, etc.), and that the caller must take some action to correct the
     * request before it should be resent. Client errors are typically associated an HTTP error code
     * in the 4xx range.
     *
     * The Service ErrorType indicates that although the request the caller sent was valid, the
     * service was unable to fulfill the request because of problems on the service's side. These
     * types of errors can be retried by the caller since the caller's request was valid and the
     * problem occurred while processing the request on the service side. Service errors will be
     * accompanied by an HTTP error code in the 5xx range.
     *
     * Finally, if there isn't enough information to determine who's fault the error response is, an
     * Unknown ErrorType will be set.
     */
    public enum ErrorType {
        Client, Service, Unknown
    }

    @Override
    public String toString() {
        return "SmhServiceException{" +
                "code='" + code + '\'' +
                ", message='" + message + '\'' +
                ", statusCode=" + statusCode +
                ", requestId='" + requestId + '\'' +
                ", additionalDetails=" + additionalDetails +
                ", stack='" + stack + '\'' +
                ", errorType=" + errorType +
                '}';
    }

    /**
     * The SMH error code represented by this exception (ex: WrongLibraryIdOrSecret).
     */
    private String code;

    /**
     * The error message as returned by the service.
     */
    private String message;

    /**
     * The HTTP status code that was returned with this error
     */
    private int statusCode;

    /**
     * The unique SMH identifier for the service request the caller made. The SMH request ID can
     * uniquely identify the SMH request.
     */
    private String requestId;
    /**
     * Additional information on the exception.
     */
    private Map<String, String> additionalDetails;
    /**
     * The DetailedError message
     */
    private String stack;
    /**
     * Indicates (if known) whether this exception was the fault of the caller or the service.
     *
     * @see ErrorType
     */
    private ErrorType errorType = ErrorType.Unknown;

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public int getStatusCode() {
        return statusCode;
    }

    public void setStatusCode(int statusCode) {
        this.statusCode = statusCode;
    }

    public String getRequestId() {
        return requestId;
    }

    public void setRequestId(String requestId) {
        this.requestId = requestId;
    }

    public String getStack() {
        return stack;
    }

    public void setStack(String stack) {
        this.stack = stack;
    }

    /**
     * Sets the type of error represented by this exception (sender, receiver, or unknown),
     * indicating if this exception was the caller's fault, or the service's fault.
     *
     * @param errorType The type of error represented by this exception (sender or receiver),
     *         indicating if this exception was the caller's fault or the service's fault.
     */
    public void setErrorType(ErrorType errorType) {
        this.errorType = errorType;
    }

    /**
     * Indicates who is responsible for this exception (caller, service, or unknown).
     *
     * @return A value indicating who is responsible for this exception (caller, service, or
     *         unknown).
     */
    public ErrorType getErrorType() {
        return errorType;
    }

    /**
     * Constructs a new SmhServiceException with the specified message.
     *
     * @param errorMessage An error message describing what went wrong.
     */
    public SmhServiceException(String errorMessage) {
        super((String) null);
        this.message = errorMessage;
    }

    /**
     * Constructs a new SmhServiceException with the specified message and exception indicating the
     * root cause.
     *
     * @param errorMessage An error message describing what went wrong.
     * @param cause The root exception that caused this exception to be thrown.
     */
    public SmhServiceException(String errorMessage, Exception cause) {
        super(null, cause);
        this.message = errorMessage;
    }


    /**
     * Returns any additional information retrieved in the error response.
     */
    public Map<String, String> getAdditionalDetails() {
        return additionalDetails;
    }

    /**
     * Sets additional information about the response.
     */
    public void setAdditionalDetails(Map<String, String> additionalDetails) {
        this.additionalDetails = additionalDetails;
    }

    /**
     * Adds an entry to the additional information map.
     */
    public void addAdditionalDetail(String key, String detail) {
        if (detail == null || detail.trim().isEmpty()) {
            return;
        }

        if (this.additionalDetails == null) {
            this.additionalDetails = new HashMap<String, String>();
        }

        String additionalContent = this.additionalDetails.get(key);
        if (additionalContent != null && !additionalContent.trim().isEmpty()) {
            detail = additionalContent + "-" + detail;
        }
        if (!detail.isEmpty()) {
            additionalDetails.put(key, detail);
        }
    }

}
