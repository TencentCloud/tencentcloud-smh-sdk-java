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

package com.tencentcloud.smh;

/**
 * Common SMH HTTP header values used throughout the SMH Java client.
 */
public interface Headers {

    /*
     * Standard HTTP Headers
     */

    public static final String CONTENT_TYPE = "Content-Type";
    public static final String SDK_LOG_DEBUG = "x-smh-sdk-log-debug";
    /**
     * SMH response header for a request's smh request ID
     */
    public static final String REQUEST_ID = "X-Request-Id";
    public static final String CONTENT_LENGTH = "Content-Length";
    /**
     * Prefix for SMH user metadata: x-smh-meta-
     */
    public static final String SMH_USER_METADATA_PREFIX = "x-smh-meta-";

}