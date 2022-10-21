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
import com.tencentcloud.smh.internal.stats.StatsRequest;
import com.tencentcloud.smh.internal.token.TokenPostBodyRequest;
import com.tencentcloud.smh.model.stats.StatsResponse;

import java.util.List;

public class StatsDemo {

    public static void main(String[] args) throws Exception {
        ClientConfig clientConfig =new ClientConfig();
        clientConfig.setHttpProtocol(HttpProtocol.http);
        SMHClient client = new SMHClient(clientConfig);
        getStats(client);
        // 关闭客户端
        client.shutdown();
    }

    /**
     * 空间存储信息统计
     * @param client
     */
    public static void getStats(SMHClient client) {
        StatsRequest request=new StatsRequest();
        request.setLibraryId("");
        request.setAccessToken("");
        request.setSpaceTag("team");
        List<StatsResponse> response=client.getStats(request);

        System.out.println(response);
    }
}
