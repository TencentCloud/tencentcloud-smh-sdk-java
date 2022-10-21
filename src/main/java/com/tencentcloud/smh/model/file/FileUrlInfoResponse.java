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

import java.io.Serializable;
import java.util.Map;

public class FileUrlInfoResponse extends CommonResponse implements Serializable {
    /**
     * 带签名的下载链接，签名有效时长约 2 小时，需在签名有效期内发起下载
     */
    private String cosUrl;
    /**
     * 文件类型
     */
    private String type;
    /**
     * 文件首次完成时间
     */
    private String creationTime;
    /**
     * 文件最近一次被覆盖的时间
     */
    private String modificationTime;
    /**
     * 媒体类型
     */
    private String contentType;
    /**
     * 文件大小
     */
    private String size;
    /**
     * 文件ETag
     */
    private String eTag;
    /**
     * 文件的 CRC64-ECMA182 校验值
     */
    private String crc64;
    /**
     * 文件类型：excel、powerpoint 等；
     */
    private String fileType;
    /**
     * 是否可通过 wps 预览；
     */
    private boolean previewByDoc;
    /**
     * 是否可通过万象预览；
     */
    private boolean previewByCI;
    /**
     * 是否可用预览图当做 icon；
     */
    private boolean previewAsIcon;
    /**
     * 元数据
     */
    private Map<String,String> metaData;

    public String getCosUrl() {
        return cosUrl;
    }

    public void setCosUrl(String cosUrl) {
        this.cosUrl = cosUrl;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
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

    public String getContentType() {
        return contentType;
    }

    public void setContentType(String contentType) {
        this.contentType = contentType;
    }

    public String getSize() {
        return size;
    }

    public void setSize(String size) {
        this.size = size;
    }

    public String geteTag() {
        return eTag;
    }

    public void seteTag(String eTag) {
        this.eTag = eTag;
    }

    public String getCrc64() {
        return crc64;
    }

    public void setCrc64(String crc64) {
        this.crc64 = crc64;
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

    public Map<String, String> getMetaData() {
        return metaData;
    }

    public void setMetaData(Map<String, String> metaData) {
        this.metaData = metaData;
    }

}
