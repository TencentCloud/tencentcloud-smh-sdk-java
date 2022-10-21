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
public class BatchMoveRequest implements Serializable {
    /**
     * 被重命名或移动的源目录、相簿或文件路径，必选参数；
     */
    private String from;
    /**
     *  目标目录、相簿或文件路径，必选参数；
     */
    private String to;
    /**
     *  是否移动文件夹权限，可选参数，true 移动，false 不移动（默认）；
     */
    private String moveAuthority;
    /**
     * 文件名冲突时的处理方式，ask: 冲突时返回 status: 409 及 SameNameDirectoryOrFileExists 错误码，
     * rename: 冲突时自动重命名文件，overwrite: 如果冲突目标为目录时返回 status: 409 及 SameNameDirectoryOrFileExists 错误码，
     * 否则覆盖已有文件，如果目标为目录或相簿时，默认为 ask 且不支持 overwrite，如果目标为文件默认为 rename；
     */
    private String conflictResolutionStrategy;

    public String getFrom() {
        return from;
    }

    public void setFrom(String from) {
        this.from = from;
    }

    public String getTo() {
        return to;
    }

    public void setTo(String to) {
        this.to = to;
    }

    public String getMoveAuthority() {
        return moveAuthority;
    }

    public void setMoveAuthority(String moveAuthority) {
        this.moveAuthority = moveAuthority;
    }

    public String getConflictResolutionStrategy() {
        return conflictResolutionStrategy;
    }

    public void setConflictResolutionStrategy(String conflictResolutionStrategy) {
        this.conflictResolutionStrategy = conflictResolutionStrategy;
    }

    @Override
    public String toString() {
        return "BatchMoveRequest{" +
                "from='" + from + '\'' +
                ", to='" + to + '\'' +
                ", moveAuthority=" + moveAuthority +
                ", conflictResolutionStrategy='" + conflictResolutionStrategy + '\'' +
                '}';
    }
}
