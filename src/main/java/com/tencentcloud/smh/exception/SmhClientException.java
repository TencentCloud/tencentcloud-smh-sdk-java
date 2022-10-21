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

/**
 * Base exception class for any errors that occur while attempting to use an SMH
 * client from SMH SDK for Java to make service calls to TencentCloud  Smh.
 *
 * Error responses from services will be handled as SmhServiceExceptions.
 * This class is primarily for errors that occur when unable to get a response
 * from a service, or when the client is unable to parse the response from a
 * service. For example, if a caller tries to use a client to make a service
 * call, but no network connection is present, an SmhClientException will be
 * thrown to indicate that the client wasn't able to successfully make the
 * service call, and no information from the service is available.
 *
 * Note : If the SDK is able to parse the response; but doesn't recognize the
 * error code from the service, an SmhServiceException is thrown
 *
 * Callers should typically deal with exceptions through SmhServiceException,
 * which represent error responses returned by services. SmhServiceException
 * has much more information available for callers to appropriately deal with
 * different types of errors that can occur.
 *
 * @see SmhServiceException
 */

public class SmhClientException extends RuntimeException {

    private static final long serialVersionUID = 1L;
    private String errorCode = ClientExceptionConstants.UNKNOWN;

    /**
     * Creates a new SmhClientException with the specified message, and root
     * cause.
     *
     * @param message An error message describing why this exception was thrown.
     * @param t The underlying cause of this exception.
     */
    public SmhClientException(String message, Throwable t) {
        super(message, t);
    }

    public SmhClientException(String message, String errorCode, Throwable t) {
        super(message, t);
        this.errorCode = errorCode;
    }

    /**
     * Creates a new SmhClientException with the specified message.
     *
     * @param message An error message describing why this exception was thrown.
     */
    public SmhClientException(String message) {
        super(message);
    }

    public SmhClientException(Throwable t) {
        super(t);
    }

    public String getErrorCode() {
        return errorCode;
    }

    public void setErrorCode(String errorCode) {
        this.errorCode = errorCode;
    }

    /**
     * Returns a hint as to whether it makes sense to retry upon this exception.
     * Default is true, but subclass may override.
     */
    public boolean isRetryable() {
        return true;
    }
}
