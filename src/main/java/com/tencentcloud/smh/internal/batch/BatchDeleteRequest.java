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

package com.tencentcloud.smh.internal.batch;

import com.fasterxml.jackson.annotation.JsonInclude;

import java.io.Serializable;

@JsonInclude(JsonInclude.Include.NON_EMPTY)
public class BatchDeleteRequest implements Serializable {
    /**
     *  被删除的目录、相簿或文件路径，必选参数；
     */
    private String path;
    /**
     * 当开启回收站时，可选参数，则该参数指定将文件移入回收站还是永久删除文件，true: 永久删除，false: 移入回收站，默认为 false；
     *
     */
    private String permanent;

    public String getPath() {
        return path;
    }

    public void setPath(String path) {
        this.path = path;
    }

    public String getPermanent() {
        return permanent;
    }

    public void setPermanent(String permanent) {
        this.permanent = permanent;
    }

    @Override
    public String toString() {
        return "BatchDeleteRequest{" +
                "path='" + path + '\'' +
                ", permanent=" + permanent +
                '}';
    }

}
