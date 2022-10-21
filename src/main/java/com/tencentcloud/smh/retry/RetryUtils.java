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

package com.tencentcloud.smh.retry;

import com.tencentcloud.smh.exception.ClientExceptionConstants;
import com.tencentcloud.smh.exception.SmhClientException;
import com.tencentcloud.smh.exception.SmhServiceException;
import org.apache.http.HttpStatus;

import java.util.HashSet;
import java.util.Set;

public class RetryUtils {

    static final Set<Integer> RETRYABLE_STATUS_CODES = new HashSet<Integer>(4);
    static final Set<String> RETRYABLE_CLIENT_ERROR_CODES = new HashSet<>(1);

    static {
        RETRYABLE_STATUS_CODES.add(HttpStatus.SC_INTERNAL_SERVER_ERROR);
        RETRYABLE_STATUS_CODES.add(HttpStatus.SC_BAD_GATEWAY);
        RETRYABLE_STATUS_CODES.add(HttpStatus.SC_SERVICE_UNAVAILABLE);
        RETRYABLE_STATUS_CODES.add(HttpStatus.SC_GATEWAY_TIMEOUT);

        RETRYABLE_CLIENT_ERROR_CODES.add(ClientExceptionConstants.CONNECTION_TIMEOUT);
        RETRYABLE_CLIENT_ERROR_CODES.add(ClientExceptionConstants.HOST_CONNECT);
        RETRYABLE_CLIENT_ERROR_CODES.add(ClientExceptionConstants.UNKNOWN_HOST);
        RETRYABLE_CLIENT_ERROR_CODES.add(ClientExceptionConstants.SOCKET_TIMEOUT);
        RETRYABLE_CLIENT_ERROR_CODES.add(ClientExceptionConstants.CLIENT_PROTOCAL_EXCEPTION);
    }

    /**
     * Returns true if the specified exception is a retryable service side exception.
     *
     * @param exception The exception to test.
     * @return True if the exception resulted from a retryable service error, otherwise false.
     */
    public static boolean isRetryableServiceException(Exception exception) {
        return exception instanceof SmhServiceException && isRetryableServiceException((SmhServiceException) exception);
    }

    /**
     * Returns true if the specified exception is a retryable service side exception.
     *
     * @param exception The exception to test.
     * @return True if the exception resulted from a retryable service error, otherwise false.
     */
    public static boolean isRetryableServiceException(SmhServiceException exception) {
        return RETRYABLE_STATUS_CODES.contains(exception.getStatusCode());
    }

    /**
     * Returns true if the specified exception is a retryable service side exception.
     *
     * @param exception The exception to test.
     * @return True if the exception resulted from a retryable service error, otherwise false.
     */
    public static boolean isRetryableClientException(Exception exception) {
        return exception instanceof SmhClientException && isRetryableClientException((SmhClientException) exception);
    }

    /**
     * Returns true if the specified exception is a retryable service side exception.
     *
     * @param exception The exception to test.
     * @return True if the exception resulted from a retryable service error, otherwise false.
     */
    public static boolean isRetryableClientException(SmhClientException exception) {
        return RETRYABLE_CLIENT_ERROR_CODES.contains(exception.getErrorCode());
    }

}
