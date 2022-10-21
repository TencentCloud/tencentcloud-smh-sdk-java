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

package com.tencentcloud.smh.internal.token;

import com.tencentcloud.smh.internal.SmhServiceRequest;


public class TokenRequest extends SmhServiceRequest {

    /**
     * 访问令牌
     */
    private String accessToken;

    /**
     *  媒体库 ID，必选参数，在媒体托管控制台创建媒体库后获取，请参阅 [开发者指南 - 创建媒体库]；
     */
    private String libraryId;
    /**
     * 媒体库密钥，必选参数，在媒体托管控制台创建媒体库后获取，请参阅 [开发者指南 - 创建媒体库]；
     */
    private String librarySecret;
    /**
     * 空间 ID，如果媒体库为单租户模式，则无需指定该参数；
     * 如果媒体库为多租户模式，当需要操作租户空间时，无需指定该参数，
     * 当进行其他操作时，若授予管理员权限则该参数为可选，否则必须指定该参数；可同时指定多个空间 ID，使用英文逗号(,)分隔；
     */
    private String spaceId;
    /**
     * 用户身份识别，可选参数，由业务后台自行控制；
     */
    private String userId;
    /**
     * 客户端识别，可选参数，由业务后台自行控制；
     */
    private String clientId;
    /**
     * 可选参数，由业务后台自行控制；
     */
    private String sessionId;
    /**
     * 同步盘 syncId，可选参数，由业务后台自行控制；
     */
    private String localSyncId;
    /**
     * 令牌有效时长及每次使用令牌后自动续期的有效时长，可选参数，单位为秒，有效值为正整数，传入其他值将使用默认值 86400（24 小时），传入小于 1200 的值将自动使用最小值 1200（20 分钟）；
     */
    private String period;
    /**
     * 空间标记，表示能够访问的空间，可选参数；
     */
    private String allowSpaceTag;

    /**
     * 授予的权限，如为空则只拥有读取权限，可指定此参数在只读基础上同时附加下述多个权限，并使用英文逗号(,)分隔，可选参数：
     *
     * - acl: 使用基于目录和文件的细粒度权限控制；
     * - admin: 管理员权限，拥有所有权限；
     * - create_space: 拥有创建租户空间权限；
     * - delete_space: 拥有删除租户空间权限；
     * - space_admin: 租户空间管理员权限，拥有除租户空间操作以外的所有权限；
     * - create_directory: 拥有创建目录或相簿权限；
     * - delete_directory: 拥有删除目录或相簿权限（未开启回收站）/将目录或相簿移入回收站权限（开启回收站）；
     * - delete_directory_permanent: 开启回收站时，拥有永久删除目录或相簿权限；
     * - move_directory: 拥有重命名或移动目录或相簿权限；
     * - copy_directory: 拥有重复制目录或相簿权限；
     * - upload_file: 拥有上传文件权限，但不允许覆盖已有文件；
     * - upload_file_force: 拥有上传文件权限，且允许覆盖已有文件；
     * - begin_upload: 拥有开始上传文件权限，但不允许覆盖已有文件；
     * - begin_upload_force: 拥有开始上传文件权限，且允许覆盖已有文件；
     * - confirm_upload: 拥有完成上传文件权限；将开始上传与完成上传权限分离，
     * --主要用于业务前后端权限的分离，使完成上传必须经过业务后端；
     * --如果同时需要开始上传和完成上传权限，可简单指定 upload_file 或 upload_file_force；
     * - create_symlink: 拥有创建符号链接权限，但不允许覆盖已有文件或符号链接；
     * - create_symlink_force:  拥有创建符号链接权限，且允许覆盖已有文件或符号链接；
     * - delete_file: 拥有删除文件权限（未开启回收站）/将文件移入回收站权限（开启回收站）；
     * - delete_file_permanent: 开启回收站时，拥有永久删除文件权限；
     * - move_file: 拥有重命名或移动文件权限，但不允许覆盖已有文件；
     * - move_file_force: 拥有重命名或移动文件权限，且允许覆盖已有文件；
     * - copy_file: 拥有复制文件权限，但不允许覆盖已有文件；
     * - copy_file_force: 拥有复制文件权限，且允许覆盖已有文件；
     * - delete_recycled: 拥有删除回收站项目权限；
     * - restore_recycled: 拥有恢复回收站项目权限；
     */
    private String grant;

    /**
     * 要传输的body信息;
     */
    private TokenPostBodyRequest tokenPostBodyRequest;


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

    public String getLibrarySecret() {
        return librarySecret;
    }

    public void setLibrarySecret(String librarySecret) {
        this.librarySecret = librarySecret;
    }

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

    public String getClientId() {
        return clientId;
    }

    public void setClientId(String clientId) {
        this.clientId = clientId;
    }

    public String getSessionId() {
        return sessionId;
    }

    public void setSessionId(String sessionId) {
        this.sessionId = sessionId;
    }

    public String getLocalSyncId() {
        return localSyncId;
    }

    public void setLocalSyncId(String localSyncId) {
        this.localSyncId = localSyncId;
    }

    public String getPeriod() {
        return period;
    }

    public void setPeriod(String period) {
        this.period = period;
    }

    public String getAllowSpaceTag() {
        return allowSpaceTag;
    }

    public void setAllowSpaceTag(String allowSpaceTag) {
        this.allowSpaceTag = allowSpaceTag;
    }

    public String getGrant() {
        return grant;
    }

    public void setGrant(String grant) {
        this.grant = grant;
    }

    public TokenPostBodyRequest getTokenPostBodyRequest() {
        return tokenPostBodyRequest;
    }

    public void setTokenPostBodyRequest(TokenPostBodyRequest tokenPostBodyRequest) {
        this.tokenPostBodyRequest = tokenPostBodyRequest;
    }



    @Override
    public String toString() {
        return "TokenRequest{" +
                "accessToken='" + accessToken + '\'' +
                ", libraryId='" + libraryId + '\'' +
                ", librarySecret='" + librarySecret + '\'' +
                ", spaceId='" + spaceId + '\'' +
                ", userId='" + userId + '\'' +
                ", clientId='" + clientId + '\'' +
                ", sessionId='" + sessionId + '\'' +
                ", localSyncId='" + localSyncId + '\'' +
                ", period='" + period + '\'' +
                ", allowSpaceTag='" + allowSpaceTag + '\'' +
                ", grant='" + grant + '\'' +
                ", tokenPostBodyRequest=" + tokenPostBodyRequest +
                '}';
    }


}
