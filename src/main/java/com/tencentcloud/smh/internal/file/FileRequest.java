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

import com.tencentcloud.smh.internal.SmhServiceRequest;

public class FileRequest extends SmhServiceRequest {
    private String libraryId;
    /**
     * 如果媒体库为单租户模式，则该参数固定为连字符(`-`)；
     */
    private String spaceId;
    /**
     * 完整文件路径，例如 `foo/bar/file_new.docx`；
     */
    private String filePath;
    /**
     *  上传文件大小，单位为字节（Byte），用于判断剩余空间是否足够，可选参数；
     */
    private String fileSize;
    private String accessToken;
    private String userId;
    private String confirmKey;
    /**
     * 文件名冲突时的处理方式，ask: 冲突时返回 HTTP 409 Conflict 及 SameNameDirectoryOrFileExists 错误码，
     * rename: 冲突时自动重命名文件，
     * overwrite: 如果冲突目标为目录时返回 HTTP 409 Conflict 及 SameNameDirectoryOrFileExists 错误码，
     * 否则覆盖已有文件，默认为 rename；
     */
    private String conflictResolutionStrategy;
    private FileConfirmBodyRequest fileConfirmBodyRequest;
    private String permanent;
    private FileMoveBodyRequest fileMoveBodyRequest;


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

    public String getConfirmKey() {
        return confirmKey;
    }

    public void setConfirmKey(String confirmKey) {
        this.confirmKey = confirmKey;
    }

    public String getConflictResolutionStrategy() {
        return conflictResolutionStrategy;
    }

    public void setConflictResolutionStrategy(String conflictResolutionStrategy) {
        this.conflictResolutionStrategy = conflictResolutionStrategy;
    }

    public FileConfirmBodyRequest getFileConfirmBodyRequest() {
        return fileConfirmBodyRequest;
    }

    public void setFileConfirmBodyRequest(FileConfirmBodyRequest fileConfirmBodyRequest) {
        this.fileConfirmBodyRequest = fileConfirmBodyRequest;
    }

    public String getPermanent() {
        return permanent;
    }

    public void setPermanent(String permanent) {
        this.permanent = permanent;
    }

    public FileMoveBodyRequest getFileMoveBodyRequest() {
        return fileMoveBodyRequest;
    }

    public void setFileMoveBodyRequest(FileMoveBodyRequest fileMoveBodyRequest) {
        this.fileMoveBodyRequest = fileMoveBodyRequest;
    }
}
