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

package com.tencentcloud.smh.internal.token;

import com.fasterxml.jackson.annotation.JsonInclude;

import java.io.Serializable;
import java.util.List;
import java.util.Map;

@JsonInclude(JsonInclude.Include.NON_EMPTY)
public class TokenPostBodyRequest implements Serializable {
    /**
     * 分享 ID 集合
     */

    private List<Integer> shareDirectoryIdList;

    /**
     * 分享权限对象
     */

    private Map<String,Boolean> shareAuthority;
    /**
     * 分享历史版本 ID 集合
     */

    private List<Integer> shareDirectoryHistoryIdList;

    /**
     * 触发信息，用于记录日志
     */
    private String attachInfo;

    public List<Integer> getShareDirectoryIdList() {
        return shareDirectoryIdList;
    }

    public void setShareDirectoryIdList(List<Integer> shareDirectoryIdList) {
        this.shareDirectoryIdList = shareDirectoryIdList;
    }

    public Map<String, Boolean> getShareAuthority() {
        return shareAuthority;
    }

    public void setShareAuthority(Map<String, Boolean> shareAuthority) {

        this.shareAuthority = shareAuthority;
    }

    public List<Integer> getShareDirectoryHistoryIdList() {
        return shareDirectoryHistoryIdList;
    }

    public void setShareDirectoryHistoryIdList(List<Integer> shareDirectoryHistoryIdList) {
        this.shareDirectoryHistoryIdList = shareDirectoryHistoryIdList;
    }

    public String getAttachInfo() {
        return attachInfo;
    }

    public void setAttachInfo(String attachInfo) {
        this.attachInfo = attachInfo;
    }

    @Override
    public String toString() {
        return "AccessTokenPostBody{" +
                "shareDirectoryIdList=" + shareDirectoryIdList +
                ", shareAuthority=" + shareAuthority +
                ", shareDirectoryHistoryIdList=" + shareDirectoryHistoryIdList +
                ", attachInfo='" + attachInfo + '\'' +
                '}';
    }

}
