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

package com.tencentcloud.smh.model.space;

import com.tencentcloud.smh.model.CommonResponse;

import java.io.Serializable;

public class SpaceResponse extends CommonResponse implements Serializable {
    /**
     * 租户空间 ID；
     */
    private String spaceId;
    /**
     * 创建者用户 ID;
     */
    private String userId;
    /**
     * 租户空间创建时间，ISO 8601格式的日期与时间字符串，例如 `2020-10-14T10:17:57.953Z`；
     */
    private String creationTime;

    public String getSpaceId() {
        return spaceId;
    }

    public void setSpaceId(String spaceId) {
        this.spaceId = spaceId;
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public String getCreationTime() {
        return creationTime;
    }

    public void setCreationTime(String creationTime) {
        this.creationTime = creationTime;
    }

    @Override
    public String toString() {
        return "SpaceResponse{" +
                "spaceId='" + spaceId + '\'' +
                ", userId='" + userId + '\'' +
                ", creationTime='" + creationTime + '\'' +
                '}';
    }
}
