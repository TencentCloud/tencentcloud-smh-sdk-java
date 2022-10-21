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

package com.tencentcloud.smh.internal.stats;

import com.tencentcloud.smh.internal.SmhServiceRequest;

public class StatsRequest extends SmhServiceRequest {

    private String libraryId;
    /**
     * Space 标签,可选参数
     */
    private String spaceTag;
    private String accessToken;

    public String getLibraryId() {
        return libraryId;
    }

    public void setLibraryId(String libraryId) {
        this.libraryId = libraryId;
    }

    public String getSpaceTag() {
        return spaceTag;
    }

    public void setSpaceTag(String spaceTag) {
        this.spaceTag = spaceTag;
    }

    public String getAccessToken() {
        return accessToken;
    }

    public void setAccessToken(String accessToken) {
        this.accessToken = accessToken;
    }

    @Override
    public String toString() {
        return "StatsRequest{" +
                "libraryId='" + libraryId + '\'' +
                ", spaceTag='" + spaceTag + '\'' +
                ", accessToken='" + accessToken + '\'' +
                '}';
    }


}
