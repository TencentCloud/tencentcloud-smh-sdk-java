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

import java.io.IOException;

import com.tencentcloud.smh.ClientConfig;
import com.tencentcloud.smh.http.SmhHttpRequest;
import com.tencentcloud.smh.internal.SmhServiceRequest;
import org.apache.http.HttpResponse;


public class PredefinedRetryPolicies {

    /**
     * No retry policy
     **/
    public static final RetryPolicy NO_RETRY_POLICY = new RetryPolicy() {
        @Override
        public <X extends SmhServiceRequest> boolean shouldRetry(SmhHttpRequest<X> request,
                                                                 HttpResponse response,
                                                                 Exception exception,
                                                                 int retryIndex) {
            return false;
        }
    };

    /**
     * SDK default retry policy
     */
    public static final RetryPolicy DEFAULT;

    static {
        DEFAULT = getDefaultRetryPolicy();
    }

    public static class SdkDefaultRetryPolicy extends RetryPolicy {

        @Override
        public <X extends SmhServiceRequest> boolean shouldRetry(SmhHttpRequest<X> request,
                                                                 HttpResponse response,
                                                                 Exception exception,
                                                                 int retryIndex) {
            if (RetryUtils.isRetryableServiceException(exception)) {
                return true;
            }

            // Always retry on client exceptions caused by IOException
            if (exception.getCause() instanceof IOException) {
                return true;
            }
            return false;
        }
    }

    /**
     * Returns the SDK default retry policy. This policy will honor the
     * maxErrorRetry set in ClientConfiguration.
     *
     * @see ClientConfig#setMaxErrorRetry(int)
     */
    public static RetryPolicy getDefaultRetryPolicy() {
        return new SdkDefaultRetryPolicy();
    }

}