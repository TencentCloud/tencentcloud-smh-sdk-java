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

import java.io.Serializable;

public class LocalSyncResponse implements Serializable {
    /**
     * 当该文件夹为同步盘时，返回同步任务 ID
     */
    private int syncId;
    /**
     * 当前文件夹是否为同步盘，如果是同步盘根目录返回 true，如果是同步盘子级节点，返回 false，如果不是同步盘，不返回该字段
     */
    private boolean isSyncRootFolder;
    /**
     * 当该文件夹为同步盘时，返回同步方式，`local_to_cloud`，非必返
     */
    private String strategy;
    /**
     * 当该文件夹为同步盘时，返回设置同步任务的 userId
     */
    private String syncUserId;

    public int getSyncId() {
        return syncId;
    }

    public void setSyncId(int syncId) {
        this.syncId = syncId;
    }

    public boolean isSyncRootFolder() {
        return isSyncRootFolder;
    }

    public void setSyncRootFolder(boolean syncRootFolder) {
        isSyncRootFolder = syncRootFolder;
    }

    public String getStrategy() {
        return strategy;
    }

    public void setStrategy(String strategy) {
        this.strategy = strategy;
    }

    public String getSyncUserId() {
        return syncUserId;
    }

    public void setSyncUserId(String syncUserId) {
        this.syncUserId = syncUserId;
    }

    @Override
    public String toString() {
        return "LocalSyncResponse{" +
                "syncId=" + syncId +
                ", isSyncRootFolder=" + isSyncRootFolder +
                ", strategy='" + strategy + '\'' +
                ", syncUserId='" + syncUserId + '\'' +
                '}';
    }
}
