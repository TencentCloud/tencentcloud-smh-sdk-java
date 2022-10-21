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

package com.tencentcloud.smh.model.directory;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.tencentcloud.smh.model.CommonResponse;

import java.io.Serializable;
import java.util.Arrays;
import java.util.List;
import java.util.Map;

@JsonInclude(JsonInclude.Include.NON_EMPTY)
public class DirectoryInfoResponse extends CommonResponse implements Serializable {
    /**
     * 字符串数组 或 null，如果是字符串数组则表示最终的文件路径，数组中的最后一个元素代表最终的文件名，
     * 其他元素代表每一级目录名，因为可能存在同名文件自动重命名，所以这里的最终路径可能不等同于开始上传时指定的路径；
     * 如果是 null 则表示该文件所在的目录或其某个父级目录已被删除，该文件已经无法访问
     */
    private String[] path;
    /**
     * 目录或相簿名或文件名；
     */
    private String name;
    /**
     * 类型
     * - `dir`: 目录或相簿；
     * - `file`: 文件，仅用于文件类型媒体库；
     * - `image`: 图片，仅用于媒体类型媒体库；
     * - `video`: 视频，仅用于媒体类型媒体库；
     * - `symlink`: 符号链接；
     */
    private String type;
    /**
     * 创建人
     */
    private String userId;

    private String userOrgId;
    private int virusAuditStatus;
    /**
     * 上传时间
     */
    private String creationTime;
    /**
     * 文件最近一次被覆盖的时间，或者目录内最近一次增删子目录或文件的时间；
     */
    private String modificationTime;
    /**
     * 目录或文件的 ETag；
     */
    private String eTag;
    /**
     * 文件大小，为了避免数字精度问题，这里为字符串格式（仅非目录或相簿返回）；
     */
    private String size;
    private AuthorityResponse authorityList;
    /**
     * 当该文件夹是同步盘，或是同步盘的子级文件目录时，返回该字段，否则为 null：
     */
    private LocalSyncResponse localSync;
    /**
     * 标签列表
     */
    private List<TagResponse> tagList;
    /**
     * 文件的 CRC64-ECMA182 校验值，为了避免数字精度问题，这里为字符串格式（仅非目录或相簿返回）；
     */
    private String crc64;
    /**
     * 版本号（仅非目录或相簿返回）；
     */
    private Object versionId;
    /**
     * 文件元数据信息（仅非目录或相簿返回）；
     */
    private Map<String,Object> metaData;
    /**
     * 文件类型：excel、powerpoint 等（仅非目录或相簿返回）；
     */
    private String fileType;
    /**
     * 是否可通过 wps 预览（仅非目录或相簿返回）；
     */
    private boolean previewByDoc;
    /**
     * 否可通过万象预览（仅非目录或相簿返回）；
     */
    private boolean previewByCI;
    /**
     * 是否可用预览图作为 icon（仅非目录或相簿返回）
     */
    private boolean previewAsIcon;
    /**
     * 媒体类型（仅非目录或相簿返回）；
     */
    private String contentType;
    /**
     *  是否被共享
     */
    private boolean isAuthorized;

    public String[] getPath() {
        return path;
    }

    public void setPath(String[] path) {
        this.path = path;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public String getUserOrgId() {
        return userOrgId;
    }

    public void setUserOrgId(String userOrgId) {
        this.userOrgId = userOrgId;
    }

    public int getVirusAuditStatus() {
        return virusAuditStatus;
    }

    public void setVirusAuditStatus(int virusAuditStatus) {
        this.virusAuditStatus = virusAuditStatus;
    }

    public String getCreationTime() {
        return creationTime;
    }

    public void setCreationTime(String creationTime) {
        this.creationTime = creationTime;
    }

    public String getModificationTime() {
        return modificationTime;
    }

    public void setModificationTime(String modificationTime) {
        this.modificationTime = modificationTime;
    }

    public String geteTag() {
        return eTag;
    }

    public void seteTag(String eTag) {
        this.eTag = eTag;
    }

    public String getSize() {
        return size;
    }

    public void setSize(String size) {
        this.size = size;
    }

    public AuthorityResponse getAuthorityList() {
        return authorityList;
    }

    public void setAuthorityList(AuthorityResponse authorityList) {
        this.authorityList = authorityList;
    }

    public LocalSyncResponse getLocalSync() {
        return localSync;
    }

    public void setLocalSync(LocalSyncResponse localSync) {
        this.localSync = localSync;
    }

    public List<TagResponse> getTagList() {
        return tagList;
    }

    public void setTagList(List<TagResponse> tagList) {
        this.tagList = tagList;
    }

    public String getCrc64() {
        return crc64;
    }

    public void setCrc64(String crc64) {
        this.crc64 = crc64;
    }

    public Object getVersionId() {
        return versionId;
    }

    public void setVersionId(Object versionId) {
        this.versionId = versionId;
    }

    public Map<String, Object> getMetaData() {
        return metaData;
    }

    public void setMetaData(Map<String, Object> metaData) {
        this.metaData = metaData;
    }

    public String getFileType() {
        return fileType;
    }

    public void setFileType(String fileType) {
        this.fileType = fileType;
    }

    public boolean isPreviewByDoc() {
        return previewByDoc;
    }

    public void setPreviewByDoc(boolean previewByDoc) {
        this.previewByDoc = previewByDoc;
    }

    public boolean isPreviewByCI() {
        return previewByCI;
    }

    public void setPreviewByCI(boolean previewByCI) {
        this.previewByCI = previewByCI;
    }

    public boolean isPreviewAsIcon() {
        return previewAsIcon;
    }

    public void setPreviewAsIcon(boolean previewAsIcon) {
        this.previewAsIcon = previewAsIcon;
    }

    public String getContentType() {
        return contentType;
    }

    public void setContentType(String contentType) {
        this.contentType = contentType;
    }

    public boolean isAuthorized() {
        return isAuthorized;
    }

    public void setAuthorized(boolean authorized) {
        isAuthorized = authorized;
    }


    @Override
    public String toString() {
        return "DirectoryInfoResponse{" +
                "path=" + Arrays.toString(path) +
                ", name='" + name + '\'' +
                ", type='" + type + '\'' +
                ", userId='" + userId + '\'' +
                ", userOrgId='" + userOrgId + '\'' +
                ", virusAuditStatus=" + virusAuditStatus +
                ", creationTime='" + creationTime + '\'' +
                ", modificationTime='" + modificationTime + '\'' +
                ", eTag='" + eTag + '\'' +
                ", size='" + size + '\'' +
                ", authorityList=" + authorityList +
                ", localSync=" + localSync +
                ", tagList=" + tagList +
                ", crc64='" + crc64 + '\'' +
                ", versionId=" + versionId +
                ", metaData=" + metaData +
                ", fileType='" + fileType + '\'' +
                ", previewByDoc=" + previewByDoc +
                ", previewByCI=" + previewByCI +
                ", previewAsIcon=" + previewAsIcon +
                ", contentType='" + contentType + '\'' +
                ", isAuthorized=" + isAuthorized +
                '}';
    }

}
