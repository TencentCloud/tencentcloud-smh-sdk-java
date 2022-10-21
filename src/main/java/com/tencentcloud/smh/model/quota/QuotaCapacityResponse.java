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

package com.tencentcloud.smh.model.quota;

import com.tencentcloud.smh.model.CommonResponse;

import java.io.Serializable;

public class QuotaCapacityResponse extends CommonResponse implements Serializable {
    /**
     * 配额 ID，用于后续查询配额的具体信息、修改配额值或删除配额；
     */
    private int id;

    /**
     * 配额的具体值，单位为字节（Byte），可以指定为数字形式或字符串形式，
     * 为了避免大数产生的精度损失，该字段指定为字符串形式；
     */
    private String capacity;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getCapacity() {
        return capacity;
    }

    public void setCapacity(String capacity) {
        this.capacity = capacity;
    }

    @Override
    public String toString() {
        return "QuotaCapacityResponse{" +
                "id=" + id +
                ", capacity='" + capacity + '\'' +
                '}';
    }
}
