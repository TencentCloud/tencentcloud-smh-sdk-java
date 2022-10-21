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

package com.tencentcloud.smh.demo;

import com.tencentcloud.smh.ClientConfig;
import com.tencentcloud.smh.SMHClient;
import com.tencentcloud.smh.http.HttpProtocol;
import com.tencentcloud.smh.internal.task.TaskRequest;
import com.tencentcloud.smh.model.task.TaskResponse;

import java.util.List;

public class TaskDemo {
    public static void main(String[] args) throws Exception {
        ClientConfig clientConfig =new ClientConfig();
        clientConfig.setHttpProtocol(HttpProtocol.http);
        SMHClient client = new SMHClient(clientConfig);
        getTask(client);
        // 关闭客户端
        client.shutdown();
    }

    /**
     * 查看任务执行情况
     * @param client
     */
    public static void getTask(SMHClient client){
        TaskRequest request=new  TaskRequest();

        request.setLibraryId("");
        request.setAccessToken("");
        request.setSpaceId("");
        request.setTaskIdList("926360");
        List<TaskResponse> response=client.getTask(request);
        System.out.println(response);
    }
}
