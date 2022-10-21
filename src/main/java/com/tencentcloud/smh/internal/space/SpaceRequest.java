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

package com.tencentcloud.smh.internal.space;

import com.tencentcloud.smh.internal.SmhServiceRequest;

public class SpaceRequest extends SmhServiceRequest {
    /**
     * 租户空间的空间 ID
     */
    private String spaceId;

    /**
     * 空间id列表, 用 , 分割
     */
    private String spaceIds;
    /**
     * 访问令牌
     */
    private String accessToken;

    /**
     *  媒体库 ID，必选参数，在媒体托管控制台创建媒体库后获取，请参阅 [开发者指南 - 创建媒体库]；
     */
    private String libraryId;

    /**
     * post请求传输body内容
     */
    private SpacePostBodyRequest spacePostBodyRequest;


    /**
     * 用户身份识别，可选参数，由业务后台自行控制；
     */
    private String userId;

    public String getSpaceId() {
        return spaceId;
    }

    public void setSpaceId(String spaceId) {
        this.spaceId = spaceId;
    }

    public String getSpaceIds() {
        return spaceIds;
    }

    public void setSpaceIds(String spaceIds) {
        this.spaceIds = spaceIds;
    }


    public String getAccessToken() {
        return accessToken;
    }

    public void setAccessToken(String accessToken) {
        this.accessToken = accessToken;
    }

    public String getLibraryId() {
        return libraryId;
    }

    public void setLibraryId(String libraryId) {
        this.libraryId = libraryId;
    }

    public SpacePostBodyRequest getSpacePostBodyRequest() {
        return spacePostBodyRequest;
    }

    public void setSpacePostBodyRequest(SpacePostBodyRequest spacePostBodyRequest) {
        this.spacePostBodyRequest = spacePostBodyRequest;
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    @Override
    public String toString() {
        return "SpaceRequest{" +
                "spaceId='" + spaceId + '\'' +
                ", spaceIds='" + spaceIds + '\'' +
                ", accessToken='" + accessToken + '\'' +
                ", libraryId='" + libraryId + '\'' +
                ", spacePostBodyRequest=" + spacePostBodyRequest +
                ", userId='" + userId + '\'' +
                '}';
    }


}
