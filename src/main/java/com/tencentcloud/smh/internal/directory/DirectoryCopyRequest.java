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

import java.io.Serializable;

@JsonInclude(JsonInclude.Include.NON_EMPTY)
public class DirectoryCopyRequest implements Serializable {

    /**
     * 被复制的源目录或相簿路径，必选参数；
     */
    private String copyFrom;

    /**
     * 被复制的源空间 SpaceId；
     */
    private String copyFromSpaceId;

    public String getCopyFrom() {
        return copyFrom;
    }

    public void setCopyFrom(String copyFrom) {
        this.copyFrom = copyFrom;
    }

    public String getCopyFromSpaceId() {
        return copyFromSpaceId;
    }

    public void setCopyFromSpaceId(String copyFromSpaceId) {
        this.copyFromSpaceId = copyFromSpaceId;
    }

}
