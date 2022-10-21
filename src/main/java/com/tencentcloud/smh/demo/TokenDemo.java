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
import com.tencentcloud.smh.internal.token.TokenPostBodyRequest;
import com.tencentcloud.smh.internal.token.TokenRequest;
import com.tencentcloud.smh.model.token.TokenResponse;

/**
 * 此处为演示参数使用，完整处理规则请参考API文档
 */
public class TokenDemo {

    public static void main(String[] args) throws Exception {
        ClientConfig clientConfig =new ClientConfig();
        clientConfig.setHttpProtocol(HttpProtocol.http);
        SMHClient client = new SMHClient(clientConfig);
        deleteAccessToken(client);
        extensionAccessToken(client);
        // 关闭客户端
        client.shutdown();
    }

    /**
     * get请求 获取token
     * @param client
     */
    public static void getAccessToken(SMHClient client) {
        TokenRequest request=new TokenRequest();
        TokenPostBodyRequest postRequest = new TokenPostBodyRequest();
        request.setLibraryId("");
        request.setLibrarySecret("");
        request.setGrant("admin");
        request.setUserId("1234");
        request.setClientId("client1");
        TokenResponse response=client.getAccessToken(request);

        System.out.println(response);
    }

    /**
     * post请求获取token
     * @param client
     */
    public static void postCreateAccessToken(SMHClient client){
        TokenRequest request=new TokenRequest();
//        TokenPostBodyRequest postRequest = new TokenPostBodyRequest();
        request.setLibraryId("");
        request.setLibrarySecret("");
        request.setGrant("admin");
        request.setUserId("");
        request.setClientId("client");
//        Map<String,Boolean> shareAuthorityMap = new HashMap<>();
//        shareAuthorityMap.put("canDownload",false);
//        postRequest.setShareAuthority(shareAuthorityMap);
//        List<Integer> shareDirectoryIdList = new ArrayList<>();
//        shareDirectoryIdList.add(1);
//        shareDirectoryIdList.add(2);
//        shareDirectoryIdList.add(3);
//        postRequest.setShareDirectoryIdList(shareDirectoryIdList);
//        List<Integer> shareDirectoryHistoryIdList = new ArrayList<>();
//        shareDirectoryHistoryIdList.add(1);
//        shareDirectoryHistoryIdList.add(2);
//        shareDirectoryHistoryIdList.add(3);
//        postRequest.setShareDirectoryHistoryIdList(shareDirectoryHistoryIdList);
//        request.setTokenPostBodyRequest(postRequest);
        TokenResponse response=client.postCreateAccessToken(request);
        System.out.println(response);
    }

    /**
     * 续期token
     * @param client
     */
    public static void extensionAccessToken(SMHClient client){
        TokenRequest request=new TokenRequest();
        request.setLibraryId("");
        request.setAccessToken("");
        TokenResponse response=client.extensionAccessToken(request);
        System.out.println(response);
    }

    /**
     * 删除用户的所有token
     * @param client
     */
    public static void deleteUserAllToken(SMHClient client){
        TokenRequest request=new TokenRequest();
        request.setLibraryId("");
        request.setLibrarySecret("");
        request.setUserId("");
        client.deleteUserAllToken(request);
    }

    /**
     * 删除指定token
     * @param client
     */
    public static void deleteAccessToken(SMHClient client){
        TokenRequest request=new TokenRequest();
        request.setLibraryId("");
        request.setAccessToken("");
        client.deleteAccessToken(request);
    }

    /**
     * 删除指定用户 指定客户端访问令牌
     * @param client
     */
    public static void deleteUserClientToken(SMHClient client){
        TokenRequest request=new TokenRequest();
        request.setLibraryId("");
        request.setLibrarySecret("");
        request.setUserId("");
        request.setClientId("client1");
        client.deleteUserClientToken(request);
    }
}
