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

package com.tencentcloud.smh.model.space;

import java.io.Serializable;

public class LibraryUsageResponse implements Serializable {
    /**
     * library 配额，如果为 null 则无配额
     */
    private Object capacity;

    /**
     * library 剩余可用存储额度大小，如果为 null 则无限制（注：已分配额度的租户空间，不论使用与否都将占用 library 可用存储额度大小）
     */
    private Object availableSpace;
    /**
     *  library 分配给租户空间的总存储额度 (多租户空间 library)
     */
    private Object totalAllocatedSpaceQuota;
    /**
     *  library 已上传文件占用的存储额度
     */
    private Object totalFileSize;

    @Override
    public String toString() {
        return "SpaceUsageResponse{" +
                "capacity=" + capacity +
                ", availableSpace=" + availableSpace +
                ", totalAllocatedSpaceQuota=" + totalAllocatedSpaceQuota +
                ", totalFileSize=" + totalFileSize +
                '}';
    }

    public Object getCapacity() {
        return capacity;
    }

    public void setCapacity(Object capacity) {
        this.capacity = capacity;
    }

    public Object getAvailableSpace() {
        return availableSpace;
    }

    public void setAvailableSpace(Object availableSpace) {
        this.availableSpace = availableSpace;
    }

    public Object getTotalAllocatedSpaceQuota() {
        return totalAllocatedSpaceQuota;
    }

    public void setTotalAllocatedSpaceQuota(Object totalAllocatedSpaceQuota) {
        this.totalAllocatedSpaceQuota = totalAllocatedSpaceQuota;
    }

    public Object getTotalFileSize() {
        return totalFileSize;
    }

    public void setTotalFileSize(Object totalFileSize) {
        this.totalFileSize = totalFileSize;
    }


}
