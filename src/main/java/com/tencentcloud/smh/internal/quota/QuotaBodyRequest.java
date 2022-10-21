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

package com.tencentcloud.smh.internal.quota;

import com.fasterxml.jackson.annotation.JsonInclude;

import java.io.Serializable;
import java.util.Arrays;

@JsonInclude(JsonInclude.Include.NON_EMPTY)
public class QuotaBodyRequest implements Serializable {
    /**
     * 对于多租户空间媒体库，指定配额所涵盖的租户空间，以便同时控制多个租户空间的配额，不支持传空数组；
     * 对于单租户空间，不能指定该字段；
     */

    private String[] spaces;
    /**
     * 配额的具体值，单位为字节（Byte），可以指定为数字形式或字符串形式，
     * 为了避免大数产生的精度损失，建议该字段指定为字符串形式
     */
    private String capacity;
    /**
     * 当指定为 false 时，配额仅用于上传时判断是否有足够空间，对于已经超限的空间不执行任何删除清理操作；
     * 当指定为 true 时，创建配额将检查当前存储量，
     * 如果存储量已经超限，那么将在 removeAfterDays 天数到达后开始删除文件已保证存储量在配额之下，默认删除最早的文件，
     * 如果 removeNewest 指定为 true 则删除最新的文件，必选参数；
     */
    private boolean removeWhenExceed;

    /**
     * 存储量超限后在进行文件删除前等待的天数，必选参数；
     */
    private boolean removeNewest;
    /**
     *  是否从最新的文件开始删除，默认为 false，即从最旧的文件开始删除，可选参数；
     */
    private int removeAfterDays;

    public String[] getSpaces() {
        return spaces;
    }

    public void setSpaces(String[] spaces) {
        this.spaces = spaces;
    }

    public String getCapacity() {
        return capacity;
    }

    public void setCapacity(String capacity) {
        this.capacity = capacity;
    }

    public boolean isRemoveWhenExceed() {
        return removeWhenExceed;
    }

    public void setRemoveWhenExceed(boolean removeWhenExceed) {
        this.removeWhenExceed = removeWhenExceed;
    }

    public boolean isRemoveNewest() {
        return removeNewest;
    }

    public void setRemoveNewest(boolean removeNewest) {
        this.removeNewest = removeNewest;
    }

    public int getRemoveAfterDays() {
        return removeAfterDays;
    }

    public void setRemoveAfterDays(int removeAfterDays) {
        this.removeAfterDays = removeAfterDays;
    }


    @Override
    public String toString() {
        return "QuotaBodyRequest{" +
                "spaces=" + Arrays.toString(spaces) +
                ", capacity='" + capacity + '\'' +
                ", removeWhenExceed=" + removeWhenExceed +
                ", removeNewest=" + removeNewest +
                ", removeAfterDays=" + removeAfterDays +
                '}';
    }
}
