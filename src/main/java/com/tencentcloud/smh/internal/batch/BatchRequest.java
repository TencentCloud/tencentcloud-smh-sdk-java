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

package com.tencentcloud.smh.internal.batch;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.tencentcloud.smh.internal.SmhServiceRequest;

import java.util.List;

@JsonInclude(JsonInclude.Include.NON_EMPTY)
public class BatchRequest extends SmhServiceRequest {
    private String libraryId;
    /**
     * 空间 ID，必须指定该参数，如果媒体库为单租户模式，则该参数固定为连字符(`-`)；
     */
    private String spaceId;
    private String shareAccessToken;
    private String accessToken;
    private String userId;
    /**
     * 批量复制body
     */
    private List<BatchCopyRequest> batchCopyRequests;
    /**
     * 批量重命名或移动body
     */
    private List<BatchMoveRequest> batchMoveRequests;
    /**
     * 批量删除body
     */
    private List<BatchDeleteRequest> batchDeleteRequests;

    public String getLibraryId() {
        return libraryId;
    }

    public void setLibraryId(String libraryId) {
        this.libraryId = libraryId;
    }

    public String getSpaceId() {
        return spaceId;
    }

    public void setSpaceId(String spaceId) {
        this.spaceId = spaceId;
    }

    public String getShareAccessToken() {
        return shareAccessToken;
    }

    public void setShareAccessToken(String shareAccessToken) {
        this.shareAccessToken = shareAccessToken;
    }

    public String getAccessToken() {
        return accessToken;
    }

    public void setAccessToken(String accessToken) {
        this.accessToken = accessToken;
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public List<BatchCopyRequest> getBatchCopyRequests() {
        return batchCopyRequests;
    }

    public void setBatchCopyRequests(List<BatchCopyRequest> batchCopyRequests) {
        this.batchCopyRequests = batchCopyRequests;
    }

    public List<BatchMoveRequest> getBatchMoveRequests() {
        return batchMoveRequests;
    }

    public void setBatchMoveRequests(List<BatchMoveRequest> batchMoveRequests) {
        this.batchMoveRequests = batchMoveRequests;
    }

    public List<BatchDeleteRequest> getBatchDeleteRequests() {
        return batchDeleteRequests;
    }

    public void setBatchDeleteRequests(List<BatchDeleteRequest> batchDeleteRequests) {
        this.batchDeleteRequests = batchDeleteRequests;
    }
    
    @Override
    public String toString() {
        return "BatchRequest{" +
                "libraryId='" + libraryId + '\'' +
                ", spaceId='" + spaceId + '\'' +
                ", shareAccessToken='" + shareAccessToken + '\'' +
                ", accessToken='" + accessToken + '\'' +
                ", userId='" + userId + '\'' +
                ", batchCopyRequests=" + batchCopyRequests +
                ", batchMoveRequests=" + batchMoveRequests +
                ", batchDeleteRequests=" + batchDeleteRequests +
                '}';
    }


}
