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
import com.tencentcloud.smh.internal.quota.QuotaBodyRequest;
import com.tencentcloud.smh.internal.quota.QuotaRequest;
import com.tencentcloud.smh.model.quota.QuotaCapacityResponse;
import com.tencentcloud.smh.model.quota.QuotaCreateResponse;

public class QuotaDemo {
    public static void main(String[] args) throws Exception {
        ClientConfig clientConfig =new ClientConfig();
        clientConfig.setHttpProtocol(HttpProtocol.http);
        SMHClient client = new SMHClient(clientConfig);
        updateQuotaCapacity(client);
//        createQuota(client);
        getQuotaCapacity(client);
        // 关闭客户端
        client.shutdown();
    }

    /**
     * 创建配额
     * @param client
     */
    public static void createQuota(SMHClient client){
        QuotaRequest request=new  QuotaRequest();
        QuotaBodyRequest postRequest = new QuotaBodyRequest();
        request.setLibraryId("");
        request.setAccessToken("");

        String[] spaces = {""};
        postRequest.setSpaces(spaces);
        postRequest.setCapacity("10995116000");
        postRequest.setRemoveWhenExceed(true);
        postRequest.setRemoveAfterDays(30);
        postRequest.setRemoveNewest(false);
        request.setQuotaBodyRequest(postRequest);

        QuotaCreateResponse response=client.createQuota(request);
        System.out.println(response);
    }

    /**
     * 获取租户空间配额
     * @param client
     */
    public static void getQuotaCapacity(SMHClient client){
        QuotaRequest request=new  QuotaRequest();
        request.setLibraryId("");
        request.setAccessToken("");
        request.setSpaceId("");
        QuotaCapacityResponse response=client.getQuotaCapacity(request);
        System.out.println(response);
    }

    /**
     * 修改配额
     * @param client
     */
    public static void updateQuotaCapacity(SMHClient client){
        QuotaRequest request=new  QuotaRequest();
        QuotaBodyRequest postRequest = new QuotaBodyRequest();
        request.setLibraryId("");
        request.setAccessToken("");
        request.setSpaceId("");
        postRequest.setCapacity("10995118000");
        postRequest.setRemoveWhenExceed(true);
        postRequest.setRemoveAfterDays(30);
        postRequest.setRemoveNewest(false);
        request.setQuotaBodyRequest(postRequest);

        client.updateQuotaCapacity(request);

    }

}
