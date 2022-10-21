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

package com.tencentcloud.smh.internal.quota;

import com.tencentcloud.smh.internal.SmhServiceRequest;

public class QuotaRequest extends SmhServiceRequest {

    /**
     * 租户空间的空间 ID,如果媒体库为单租户模式，则该参数固定为连字符(`-`)；如果媒体库为多租户模式，则必须指定该参数；
     */
    private String spaceId;

    /**
     * 访问令牌
     */
    private String accessToken;

    /**
     *  媒体库 ID，必选参数，在媒体托管控制台创建媒体库后获取，请参阅 [开发者指南 - 创建媒体库]；
     */
    private String libraryId;

    private QuotaBodyRequest quotaBodyRequest;


    /**
     * 用户身份识别，当访问令牌对应的权限为管理员权限且申请访问令牌时的用户身份识别为空时用来临时指定用户身份，
     * 详情请参阅生成访问令牌接口，可选参数；
     */
    private String userId;


    public String getSpaceId() {
        return spaceId;
    }

    public void setSpaceId(String spaceId) {
        this.spaceId = spaceId;
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

    public QuotaBodyRequest getQuotaBodyRequest() {
        return quotaBodyRequest;
    }

    public void setQuotaBodyRequest(QuotaBodyRequest quotaBodyRequest) {
        this.quotaBodyRequest = quotaBodyRequest;
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }


    @Override
    public String toString() {
        return "QuotaRequest{" +
                "spaceId='" + spaceId + '\'' +
                ", accessToken='" + accessToken + '\'' +
                ", libraryId='" + libraryId + '\'' +
                ", quotaBodyRequest=" + quotaBodyRequest +
                ", userId='" + userId + '\'' +
                '}';
    }

}
