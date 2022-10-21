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


package com.tencentcloud.smh.internal.directory;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.tencentcloud.smh.internal.SmhServiceRequest;

@JsonInclude(JsonInclude.Include.NON_EMPTY)
public class DirectoryRequest extends SmhServiceRequest {

    /**
     * 媒体库 ID，必选参数；
     */
    private String libraryId;

    /**
     * - 空间 ID，如果媒体库为单租户模式，则该参数固定为连字符(`-`)；如果媒体库为多租户模式，则必须指定该参数；
     */
    private String spaceId;
    /**
     *  目录路径或相簿名，对于多级目录，使用斜杠(`/`)分隔，例如 `foo/bar`，可选参数，对于根目录，该参数留空;
     */
    private String dirPath;
    /**
     * 最后一级目录冲突时的处理方式，ask: 冲突时返回 HTTP 409 Conflict 及 SameNameDirectoryOrFileExists 错误码，rename: 冲突时自动重命名最后一级目录，默认为 ask；
     */
    private String conflictResolutionStrategy;
    /**
     * 访问令牌；
     */
    private String accessToken;
    /**
     * 用户身份识别
     */
    private String userId;
    /**
     * 当媒体库开启回收站时，则该参数指定将文件移入回收站还是永久删除文件，1: 永久删除，0: 移入回收站，默认为 0；
     */
    private String permanent;

    /**
     * 复制传输body
     */
    public DirectoryCopyRequest directoryCopyRequest;

    /**
     * 重命名或移动body
     */
    public DirectoryMoveRequest directoryMoveRequest;

    /**
     * 是否移动文件夹权限，true 移动，false 不移动；
     */
    private String  moveAuthority;


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

    public String getDirPath() {
        return dirPath;
    }

    public void setDirPath(String dirPath) {
        this.dirPath = dirPath;
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

    public String getPermanent() {
        return permanent;
    }

    public void setPermanent(String permanent) {
        this.permanent = permanent;
    }

    public DirectoryCopyRequest getDirectoryCopyRequest() {
        return directoryCopyRequest;
    }

    public void setDirectoryCopyRequest(DirectoryCopyRequest directoryCopyRequest) {
        this.directoryCopyRequest = directoryCopyRequest;
    }

    public DirectoryMoveRequest getDirectoryMoveRequest() {
        return directoryMoveRequest;
    }

    public void setDirectoryMoveRequest(DirectoryMoveRequest directoryMoveRequest) {
        this.directoryMoveRequest = directoryMoveRequest;
    }

    public String getMoveAuthority() {
        return moveAuthority;
    }

    public void setMoveAuthority(String moveAuthority) {
        this.moveAuthority = moveAuthority;
    }

    @Override
    public String toString() {
        return "DirectoryRequest{" +
                "libraryId='" + libraryId + '\'' +
                ", spaceId='" + spaceId + '\'' +
                ", dirPath='" + dirPath + '\'' +
                ", conflictResolutionStrategy='" + conflictResolutionStrategy + '\'' +
                ", accessToken='" + accessToken + '\'' +
                ", userId='" + userId + '\'' +
                ", permanent='" + permanent + '\'' +
                ", directoryCopyRequest=" + directoryCopyRequest +
                ", directoryMoveRequest=" + directoryMoveRequest +
                ", moveAuthority='" + moveAuthority + '\'' +
                '}';
    }


}
