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

public class FileMoveResponse extends CommonResponse implements Serializable {
    /**
     * 字符串数组 或 null，如果是字符串数组则表示最终的文件路径，数组中的最后一个元素代表最终的文件名，
     * 其他元素代表每一级目录名，因为可能存在同名文件自动重命名，所以这里的最终路径可能不等同于移动或重命名时指定的目标路径；
     * 如果是 null 则表示目标路径的某级父级目录已被删除，该目标文件已经无法访问；
     */
    private Object path;

    public Object getPath() {
        return path;
    }

    public void setPath(Object path) {
        this.path = path;
    }

}
