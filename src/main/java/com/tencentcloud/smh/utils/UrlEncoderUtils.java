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

package com.tencentcloud.smh.utils;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;

public class UrlEncoderUtils {

    public static final String PATH_DELIMITER = "/";
    private static final String ENCODE_DELIMITER = "%2F";
    private static final Logger log = LoggerFactory.getLogger(UrlEncoderUtils.class);

    public static String encode(String originUrl) {
        try {
            return URLEncoder.encode(originUrl, StandardCharsets.UTF_8.name())
                    .replace(ENCODE_DELIMITER,PATH_DELIMITER)
                    .replace("+", "%20")
                    .replace("*", "%2A")
                    .replace("%7E", "~")
                    .replace("%28","(")
                    .replace("%29",")");
        } catch (UnsupportedEncodingException e) {
            log.error("URLEncoder error, exception: {}", e);
        }
        return null;
    }

}
