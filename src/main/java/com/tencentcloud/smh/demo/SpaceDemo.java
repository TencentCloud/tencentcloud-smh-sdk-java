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
import com.tencentcloud.smh.internal.directory.DirectoryCopyRequest;
import com.tencentcloud.smh.internal.directory.DirectoryRequest;
import com.tencentcloud.smh.internal.space.SpacePostBodyRequest;
import com.tencentcloud.smh.internal.space.SpaceRequest;
import com.tencentcloud.smh.model.directory.DirectoryCopyResponse;
import com.tencentcloud.smh.model.space.*;

import java.util.List;


public class SpaceDemo {
    public static void main(String[] args) throws Exception {
        ClientConfig clientConfig =new ClientConfig();
        clientConfig.setHttpProtocol(HttpProtocol.http);
        SMHClient client = new SMHClient(clientConfig);
        deleteSpace(client);
        // 关闭客户端
        client.shutdown();
    }

    /**
     * 创建租户空间
     * @param client
     */
    public static void createSpace(SMHClient client){
        SpaceRequest request=new  SpaceRequest();
        SpacePostBodyRequest postRequest = new  SpacePostBodyRequest();
        request.setLibraryId("");
        request.setAccessToken("");
        request.setUserId("admin");
        postRequest.setAllowPhoto(true);
        postRequest.setAllowVideo(true);
        postRequest.setPublicRead(false);
        postRequest.setMultiAlbum(true);
        postRequest.setRecognizeSensitiveContent(true);
        postRequest.setSpaceTag("team");
//        String[] allowPhotoExtname = {".jpg", ".png", ".heic"};
//        String[] allowVideoExtname = { ".mp4", ".mov"};
//        String[] allowFileExtname = {".txt"};
//        postRequest.setAllowPhotoExtname(allowPhotoExtname);
//        postRequest.setAllowVideoExtname(allowVideoExtname);
//        postRequest.setAllowFileExtname(allowFileExtname);
        request.setSpacePostBodyRequest(postRequest);
        SpaceCreateResponse response=client.createSpace(request);
        System.out.println(response);
    }

    /**
     * 列出租户空间
     * @param client
     */
    public static void getSpaceList(SMHClient client){
        SpaceRequest request=new  SpaceRequest();
        request.setLibraryId("");
        request.setAccessToken("");
        request.setUserId("admin");
        List<SpaceResponse> response=client.getSpaceList(request);
        System.out.println(response);
    }

    /**
     * 获取租户空间属性
     * @param client
     */
    public static void getSpaceExtension(SMHClient client){
        SpaceRequest request=new  SpaceRequest();
        request.setLibraryId("");
        request.setAccessToken("");
        request.setSpaceId("");
        request.setUserId("admin");
        SpaceExtensionResponse response=client.getSpaceExtension(request);
        System.out.println(response);
    }

    /**
     * 查询租户空间大小
     * @param client
     */
    public static void getSpaceSize(SMHClient client){
        SpaceRequest request=new  SpaceRequest();
        request.setLibraryId("");
        request.setAccessToken("");
        request.setSpaceId("");
//        request.setUserId("admin");
        SpaceSizeResponse response=client.getSpaceSize(request);
        System.out.println(response);
    }

    /**
     * 查看library使用率
     * @param client
     */
    public static void getLibraryUsage(SMHClient client){
        SpaceRequest request=new  SpaceRequest();
        request.setLibraryId("");
        request.setAccessToken("");
//        request.setSpaceId("space1t4qhi3gojd77");
//        request.setUserId("admin");
        LibraryUsageResponse response=client.getLibraryUsage(request);
        System.out.println(response);
    }

    /**
     * 批量查询租户空间容量信息
     * @param client
     */
    public static void getSpaceUsageList(SMHClient client){
        SpaceRequest request=new  SpaceRequest();
        request.setLibraryId("");
        request.setAccessToken("");
        request.setSpaceIds("space1,space2");
//        request.setUserId("admin");
        List<LibraryUsageResponse> response=client.getSpaceUsageList(request);
        System.out.println(response);
    }

    /**
     * 删除租户空间
     * @param client
     */
    public static void deleteSpace(SMHClient client){
        SpaceRequest request=new  SpaceRequest();
        request.setLibraryId("");
        request.setAccessToken("");
        request.setSpaceId("");
//        request.setUserId("admin");
        client.deleteSpace(request);
    }

    /**
     *
     * @param client
     */
    public static void directoryCrossSpaceCopy(SMHClient client){
        DirectoryRequest request=new  DirectoryRequest();
        request.setLibraryId("");
        request.setAccessToken("");
        request.setSpaceId("");
        request.setDirPath("crosscopytest");
        DirectoryCopyRequest directoryCopyRequest = new DirectoryCopyRequest();
        directoryCopyRequest.setCopyFrom("spacedgr/kwy/test");
        directoryCopyRequest.setCopyFromSpaceId("space3pu367651updc");
        request.setDirectoryCopyRequest(directoryCopyRequest);
        DirectoryCopyResponse response = client.copyDirectoryCrossSpace(request);
        System.out.println(response);
    }
}
