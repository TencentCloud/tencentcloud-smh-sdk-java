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

package com.tencentcloud.smh.model.batch;

import com.tencentcloud.smh.model.task.TaskResultResponse;

import java.io.Serializable;
import java.util.List;

public class BatchSyncResponse implements Serializable {
    /**
     * 结果内容
     */
    private List<TaskResultResponse> result;

    public List<TaskResultResponse> getResult() {
        return result;
    }

    public void setResult(List<TaskResultResponse> result) {
        this.result = result;
    }


}
