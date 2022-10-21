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

package com.tencentcloud.smh.model.task;

import com.tencentcloud.smh.model.CommonResponse;

import java.io.Serializable;

public class TaskResultResponse extends CommonResponse implements Serializable {
    private String[] path;
    private int status;
    private String[] copyFrom;
    private String[] to;
    private int recycledItemId;
    private String code;
    private String message;

    public String[] getPath() {
        return path;
    }

    public void setPath(String[] path) {
        this.path = path;
    }

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }

    public String[] getCopyFrom() {
        return copyFrom;
    }

    public void setCopyFrom(String[] copyFrom) {
        this.copyFrom = copyFrom;
    }

    public String[] getTo() {
        return to;
    }

    public void setTo(String[] to) {
        this.to = to;
    }

    public int getRecycledItemId() {
        return recycledItemId;
    }

    public void setRecycledItemId(int recycledItemId) {
        this.recycledItemId = recycledItemId;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

}
