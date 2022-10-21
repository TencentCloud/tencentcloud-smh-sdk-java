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

/**
 * Super interface for {@link RetryPolicy} that defines a strategy for backing off between retries.
 */
public interface BackoffStrategy {

    /**
     * Compute the delay before the next retry request. This strategy is only consulted when there will be a next retry.
     *
     * @return Amount of time in milliseconds to wait before the next attempt. Must be non-negative (can be zero).
     */
    long computeDelayBeforeNextRetry(int retryIndex);
}
