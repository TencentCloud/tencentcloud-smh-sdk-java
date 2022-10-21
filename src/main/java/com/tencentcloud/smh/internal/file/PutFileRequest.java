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


package com.tencentcloud.smh.internal.file;

import java.io.File;
import java.io.InputStream;
import java.io.Serializable;

public class PutFileRequest extends AbstractPutFileRequest implements Serializable {
    private String libraryId;
    private String spaceId;
    /**
     * 文件路径；
     */
    private String filePath;
    private String fileSize;
    private String accessToken;
    private String userId;
    private String conflictResolutionStrategy;

    private String crc64;

    public PutFileRequest() {
        super();
    }

    public PutFileRequest(InputStream input,
                            ObjectMetadata metadata) {
        super(input, metadata);
    }
    @Override
    @SuppressWarnings("unchecked")
    public PutFileRequest  withFile(File file) {
        return super.withFile(file);
    }

    @SuppressWarnings("unchecked")
    public PutFileRequest withMetadata(ObjectMetadata metadata) {
        return super.withMetadata(metadata);
    }

    @Override
    @SuppressWarnings("unchecked")
    public PutFileRequest  withInputStream(InputStream inputStream) {
        return super.withInputStream(inputStream);
    }


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

    public String getFilePath() {
        return filePath;
    }

    public void setFilePath(String filePath) {
        this.filePath = filePath;
    }

    public String getFileSize() {
        return fileSize;
    }

    public void setFileSize(String fileSize) {
        this.fileSize = fileSize;
    }

    public String getConflictResolutionStrategy() {
        return conflictResolutionStrategy;
    }

    public void setConflictResolutionStrategy(String conflictResolutionStrategy) {
        this.conflictResolutionStrategy = conflictResolutionStrategy;
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


    public String getCrc64() {
        return crc64;
    }

    public void setCrc64(String crc64) {
        this.crc64 = crc64;
    }

}

