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
import java.util.List;

public class FileUploadStatusResponse extends CommonResponse implements Serializable {
    /**
     * 代表当前上传任务是否为已完成；
     */
    private boolean confirmed;
    /**
     *  字符串数组 或 null，如果是字符串数组则表示最终的文件路径，数组中的最后一个元素代表最终的文件名，
     *  其他元素代表每一级目录名，因为可能存在同名文件自动重命名，所以这里的最终路径可能不等同于开始上传时指定的路径；
     *  如果是 null 则表示该文件所在的目录或其某个父级目录已被删除，该文件已经无法访问；
     */
    private Object path;
    /**
     * 类型
     */
    private String type;
    /**
     * 上传任务创建时间
     */
    private String creationTime;
    /**
     * 是否强制覆盖同路径文件
     */
    private boolean force;
    /**
     * 如果为分块上传则返回该字段，包含已上传的分块信息；否则不返回该字段；
     */
    private List<PartResponse> parts;
    /**
     *  如果为分块上传则返回该字段，包含继续进行分块上传的信息（可参阅**开始分块上传文件**接口）；否则不返回该字段；
     */
    private UploadFileResponse uploadPartInfo;


    public boolean isConfirmed() {
        return confirmed;
    }

    public void setConfirmed(boolean confirmed) {
        this.confirmed = confirmed;
    }

    public Object getPath() {
        return path;
    }

    public void setPath(Object path) {
        this.path = path;
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

    public boolean isForce() {
        return force;
    }

    public void setForce(boolean force) {
        this.force = force;
    }

    public List<PartResponse> getParts() {
        return parts;
    }

    public void setParts(List<PartResponse> parts) {
        this.parts = parts;
    }

    public UploadFileResponse getUploadPartInfo() {
        return uploadPartInfo;
    }

    public void setUploadPartInfo(UploadFileResponse uploadPartInfo) {
        this.uploadPartInfo = uploadPartInfo;
    }

}
