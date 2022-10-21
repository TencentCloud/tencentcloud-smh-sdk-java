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

public class PutFilePartRequest extends AbstractPutFileRequest implements Serializable {
    private String domain;
    private String path;
    private String confirmKey;
    private String uploadId;
    /**
     * 为1开始的分块顺序
     */
    private String partNumber;

    public PutFilePartRequest() {
        super();
    }

    public PutFilePartRequest(InputStream input,
                              ObjectMetadata metadata) {
        super(input, metadata);
    }

    @Override
    @SuppressWarnings("unchecked")
    public PutFilePartRequest withFile(File file) {
        return super.withFile(file);
    }

    @SuppressWarnings("unchecked")
    public PutFilePartRequest withMetadata(ObjectMetadata metadata) {
        return super.withMetadata(metadata);
    }

    @Override
    @SuppressWarnings("unchecked")
    public PutFilePartRequest withInputStream(InputStream inputStream) {
        return super.withInputStream(inputStream);
    }


    public String getDomain() {
        return domain;
    }

    public void setDomain(String domain) {
        this.domain = domain;
    }

    public String getPath() {
        return path;
    }

    public void setPath(String path) {
        this.path = path;
    }


    public String getConfirmKey() {
        return confirmKey;
    }

    public void setConfirmKey(String confirmKey) {
        this.confirmKey = confirmKey;
    }

    public String getUploadId() {
        return uploadId;
    }

    public void setUploadId(String uploadId) {
        this.uploadId = uploadId;
    }

    public String getPartNumber() {
        return partNumber;
    }

    public void setPartNumber(String partNumber) {
        this.partNumber = partNumber;
    }

}

