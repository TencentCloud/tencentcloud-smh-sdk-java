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

package com.tencentcloud.smh.model.task;

import java.io.Serializable;
import java.util.List;

public class TaskResponse  implements Serializable {
    /**
     * 任务 ID
     */
    private int id;

    /**
     * 任务状态码
     * 202: 任务进行中，
     * 200: 任务成功完成且有返回结果，返回结果在 result 字段中，
     * 204: 任务成功完成且无返回结果，
     * 500: 任务执行失败；
     */
    private int status;
    /**
     * 任务成功完成后的返回结果
     */
    private List<TaskResultResponse> result;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }

    public List<TaskResultResponse> getResult() {
        return result;
    }

    public void setResult(List<TaskResultResponse> result) {
        this.result = result;
    }

}
