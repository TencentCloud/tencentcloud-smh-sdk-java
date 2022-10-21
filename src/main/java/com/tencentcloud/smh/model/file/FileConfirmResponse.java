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
import java.util.Arrays;
import java.util.Map;

public class FileConfirmResponse extends CommonResponse implements Serializable {
    /**
     * 字符串数组 或 null，如果是字符串数组则表示最终的文件路径，数组中的最后一个元素代表最终的文件名，
     * 其他元素代表每一级目录名，因为可能存在同名文件自动重命名，所以这里的最终路径可能不等同于开始上传时指定的路径；
     * 如果是 null 则表示该文件所在的目录或其某个父级目录已被删除，该文件已经无法访问；
     */
    private String[] path;
    /**
     * 最终文件名；
     */
    private String name;
    /**
     * 文件类型;
     */
    private String type;
    /**
     * 文件首次完成上传的时间；
     */
    private String creationTime;
    /**
     * 文件最近一次被覆盖的时间；
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
     * 元数据
     */
    private Map<String,String> metaData;
    /**
     * 文件类型：excel、powerpoint 等；
     */
    private String fileType;
    /**
     * 0-6 查毒状态
     *
     * - 0 未检测 （文件夹不标记可疑状态，一直为0）
     * - 1 检测中
     * - 2 无风险
     * - 3 风险文件
     * - 4 无法检测 （比如文件太大超过可检测范围，端侧当无风险处理）（超1G不检）
     * - 5 人为标记为无风险
     * - 6 检测任务失败
     */
    private int virusAuditStatus;
    /**
     * 用于完成文件上传的确认参数
     */
    private String confirmKey;
    /**
     * 是否可通过万象预览；
     */
    private boolean previewByCI;


    @Override
    public String toString() {
        return "FileConfirmResponse{" +
                "path=" + Arrays.toString(path) +
                ", name='" + name + '\'' +
                ", type='" + type + '\'' +
                ", creationTime='" + creationTime + '\'' +
                ", modificationTime='" + modificationTime + '\'' +
                ", contentType='" + contentType + '\'' +
                ", size='" + size + '\'' +
                ", eTag='" + eTag + '\'' +
                ", crc64='" + crc64 + '\'' +
                ", metaData=" + metaData +
                ", fileType='" + fileType + '\'' +
                ", virusAuditStatus=" + virusAuditStatus +
                ", confirmKey='" + confirmKey + '\'' +
                ", previewByCI=" + previewByCI +
                '}';
    }

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

    public Map<String, String> getMetaData() {
        return metaData;
    }

    public void setMetaData(Map<String, String> metaData) {
        this.metaData = metaData;
    }

    public String getFileType() {
        return fileType;
    }

    public void setFileType(String fileType) {
        this.fileType = fileType;
    }

    public int getVirusAuditStatus() {
        return virusAuditStatus;
    }

    public void setVirusAuditStatus(int virusAuditStatus) {
        this.virusAuditStatus = virusAuditStatus;
    }

    public String getConfirmKey() {
        return confirmKey;
    }

    public void setConfirmKey(String confirmKey) {
        this.confirmKey = confirmKey;
    }

    public boolean isPreviewByCI() {
        return previewByCI;
    }

    public void setPreviewByCI(boolean previewByCI) {
        this.previewByCI = previewByCI;
    }

}
