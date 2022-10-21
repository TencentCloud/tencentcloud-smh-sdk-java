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

import com.tencentcloud.smh.model.CommonResponse;

import java.io.Serializable;
import java.util.Arrays;

public class DirectoryCreateResponse extends CommonResponse implements Serializable {
    /**
     * 字符串数组，表示最终的目录或相簿路径，因为可能存在自动重命名，所以这里的最终路径可能不等同于创建目录或相簿时指定的路径；
     */
    private String[] path;

    public String[] getPath() {
        return path;
    }

    public void setPath(String[] path) {
        this.path = path;
    }

    @Override
    public String toString() {
        return "DirectoryCreateResponse{" +
                "path=" + Arrays.toString(path) +
                '}';
    }
}
