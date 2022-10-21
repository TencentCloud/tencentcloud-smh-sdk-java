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

package com.tencentcloud.smh.model.file;

import com.tencentcloud.smh.model.CommonResponse;
import com.tencentcloud.smh.model.directory.AuthorityResponse;

import java.io.Serializable;
import java.util.Arrays;
import java.util.Map;

public class FileInfoResponse extends CommonResponse implements Serializable {
    private String[] path;
    private String name;
    private String type;
    private String userId;
    private String creationTime;
    private String modificationTime;
    private Object versionId;
    private String eTag;
    private String size;
    private String crc64;
    private AuthorityResponse authorityList;
    private Map<String,Object> metaData;
    private String fileType;
    private boolean previewByDoc;
    private boolean previewByCI;
    private boolean previewAsIcon;
    private String contentType;
    private String  historySize;

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

    public Object getVersionId() {
        return versionId;
    }

    public void setVersionId(Object versionId) {
        this.versionId = versionId;
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

    public String getCrc64() {
        return crc64;
    }

    public void setCrc64(String crc64) {
        this.crc64 = crc64;
    }

    public AuthorityResponse getAuthorityList() {
        return authorityList;
    }

    public void setAuthorityList(AuthorityResponse authorityList) {
        this.authorityList = authorityList;
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

    public String getHistorySize() {
        return historySize;
    }

    public void setHistorySize(String historySize) {
        this.historySize = historySize;
    }

    @Override
    public String toString() {
        return "FileInfoResponse{" +
                "path=" + Arrays.toString(path) +
                ", name='" + name + '\'' +
                ", type='" + type + '\'' +
                ", userId='" + userId + '\'' +
                ", creationTime='" + creationTime + '\'' +
                ", modificationTime='" + modificationTime + '\'' +
                ", versionId=" + versionId +
                ", eTag='" + eTag + '\'' +
                ", size='" + size + '\'' +
                ", crc64='" + crc64 + '\'' +
                ", authorityList=" + authorityList +
                ", metaData=" + metaData +
                ", fileType='" + fileType + '\'' +
                ", previewByDoc=" + previewByDoc +
                ", previewByCI=" + previewByCI +
                ", previewAsIcon=" + previewAsIcon +
                ", contentType='" + contentType + '\'' +
                ", historySize='" + historySize + '\'' +
                '}';
    }
}
