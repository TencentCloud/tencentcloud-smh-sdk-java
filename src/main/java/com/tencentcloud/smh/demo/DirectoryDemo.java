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
import com.tencentcloud.smh.internal.directory.DirectoryDetailRequest;
import com.tencentcloud.smh.internal.directory.DirectoryMoveRequest;
import com.tencentcloud.smh.internal.directory.DirectoryRequest;
import com.tencentcloud.smh.model.directory.*;

public class DirectoryDemo {
    public static void main(String[] args) throws Exception {
        ClientConfig clientConfig =new ClientConfig();
        clientConfig.setHttpProtocol(HttpProtocol.http);
        SMHClient client = new SMHClient(clientConfig);
//        directoryDelete(client);
        getDirectoryContents(client);
        // 关闭客户端
        client.shutdown();
    }

    /**
     * 创建目录
     * @param client
     */
    public static void createDirectory(SMHClient client){
        DirectoryRequest request=new  DirectoryRequest();

        request.setLibraryId("");
        request.setAccessToken("");
        request.setSpaceId("");
        request.setDirPath("kwy/test");
//        request.setConflictResolutionStrategy("rename");
        DirectoryCreateResponse response=client.createDirectory(request);
        System.out.println(response);
    }

    /**
     * 获取目录详情
     * @param client
     */
    public static void getDirectoryInfo(SMHClient client){
        DirectoryRequest request=new  DirectoryRequest();

        request.setLibraryId("");
        request.setAccessToken("");
        request.setSpaceId("");
        request.setDirPath("spacedgr/kwy/test");
        DirectoryInfoResponse response=client.getDirectoryInfo(request);
        System.out.println(response);
    }

    /**
     * 查看目录是否存在
     * @param client
     */
    public static void getDirectoryStatus(SMHClient client){
        DirectoryRequest request=new  DirectoryRequest();

        request.setLibraryId("");
        request.setAccessToken("");
        request.setSpaceId("");
        request.setDirPath("spacedgr/kwy/test");
        System.out.println(client.doesDirectoryExist(request));
    }

    /**
     * 列出目录内容
     * @param client
     */
    public static void getDirectoryContents(SMHClient client){
        DirectoryDetailRequest request=new DirectoryDetailRequest();

        request.setLibraryId("");
        request.setAccessToken("");
        request.setSpaceId("");

//        request.setDirPath("/");
        request.setDirPath("crosscopytest");
        DirectoryContentsResponse directoryDetail = client.getDirectoryContents(request);
        System.out.println(directoryDetail);
    }

    /**
     * 复制目录
     * @param client
     */
    public static void copyDirectory(SMHClient client){
        DirectoryRequest request=new DirectoryRequest();

        request.setLibraryId("");
        request.setAccessToken("");
        request.setSpaceId("");
        request.setDirPath("spacedgr/kwy/test");

        DirectoryCopyRequest directoryCopyRequest = new DirectoryCopyRequest();
        directoryCopyRequest.setCopyFrom("space651updc/kwy/test");
        request.setDirectoryCopyRequest(directoryCopyRequest);
        request.setConflictResolutionStrategy("rename");

        DirectoryCopyResponse response = client.copyDirectory(request);
        System.out.println(response);
    }

    /**
     * 重命名或移动目录
     * @param client
     */
    public static void moveDirectory(SMHClient client){
        DirectoryRequest request=new DirectoryRequest();

        request.setLibraryId("");
        request.setAccessToken("");
        request.setSpaceId("");

        request.setDirPath("spacedgr/kwy/test (2)");
        request.setMoveAuthority("false");
        DirectoryMoveRequest directoryMoveRequest = new DirectoryMoveRequest();
        directoryMoveRequest.setFrom("spacedgr/kwy/test (1)");
        request.setDirectoryMoveRequest(directoryMoveRequest);

        DirectoryMoveResponse response = client.moveDirectory(request);
        System.out.println(response);
    }

    /**
     * 删除目录
     * @param client
     */
    public static void directoryDelete(SMHClient client){
        DirectoryRequest request=new DirectoryRequest();

        request.setLibraryId("");
        request.setAccessToken("");
        request.setSpaceId("");
        request.setDirPath("spacedgr/kwy/test (2)");
        request.setPermanent("0");
        DirectoryDeleteResponse response = client.deleteDirectory(request);
    }
}
