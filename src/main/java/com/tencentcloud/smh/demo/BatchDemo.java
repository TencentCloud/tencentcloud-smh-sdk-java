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
import com.tencentcloud.smh.internal.batch.BatchCopyRequest;
import com.tencentcloud.smh.internal.batch.BatchDeleteRequest;
import com.tencentcloud.smh.internal.batch.BatchMoveRequest;
import com.tencentcloud.smh.internal.batch.BatchRequest;
import com.tencentcloud.smh.model.batch.BatchResponse;

import java.util.ArrayList;
import java.util.List;

public class BatchDemo {
    public static void main(String[] args) throws Exception {
        ClientConfig clientConfig =new ClientConfig();
        clientConfig.setHttpProtocol(HttpProtocol.http);
        SMHClient client = new SMHClient(clientConfig);
        postDelete(client);
        // 关闭客户端
        client.shutdown();
    }

    /**
     * 批量复制目录或文件
     * @param client
     */
    public static void postCopy(SMHClient client){
        BatchRequest request=new BatchRequest();

        request.setLibraryId("");
        request.setSpaceId("");
        request.setAccessToken("");
//        request.setShareAccessToken("");
        List<BatchCopyRequest> batchCopyRequestList = new ArrayList<>();
        BatchCopyRequest copyRequest1 = new BatchCopyRequest();
        copyRequest1.setCopyFrom("space3pu367651updc/kwy/test/1.txt");
        copyRequest1.setTo("spacedgr/kwy/test/1.txt");
        batchCopyRequestList.add(copyRequest1);

//        BatchCopyRequest copyRequest2 = new BatchCopyRequest();
//        copyRequest2.setCopyFrom("产品使用手册.pdf");
//        copyRequest2.setTo("新建文件夹/产品使用手册.pdf");
//        copyRequest2.setCopyFromLibraryId("smh0qqtecxx1ou17");
//        copyRequest2.setCopyFromSpaceId("space3fswkhvd4du2i");
//        copyRequest2.setConflictResolutionStrategy("rename");
//        batchCopyRequestList.add(copyRequest2);
//
//        BatchCopyRequest copyRequest3 = new BatchCopyRequest();
//        copyRequest3.setCopyFrom("轻松提升办公技能.docx");
//        copyRequest3.setTo("新建文件夹/产品使用手册.pdf");
//        copyRequest3.setCopyFromLibraryId("smh0qqtecxx1ou17");
//        copyRequest3.setCopyFromSpaceId("space3fswkhvd4du2i");
//        copyRequest3.setConflictResolutionStrategy("rename");
//        batchCopyRequestList.add(copyRequest3);


        request.setBatchCopyRequests(batchCopyRequestList);
        BatchResponse response=client.batchCopy(request);
        System.out.println(response);
    }

    /**
     * 批量删除目录或文件
     * @param client
     */
    public static void postDelete(SMHClient client){
        BatchRequest request=new BatchRequest();

        request.setLibraryId("");
        request.setSpaceId("-");
        request.setAccessToken("");
        List<BatchDeleteRequest> batchDeleteRequests = new ArrayList<>();
        BatchDeleteRequest batchDeleteRequest1 = new BatchDeleteRequest();
        batchDeleteRequest1.setPath("zfw-weiyankong (2)");
        batchDeleteRequests.add(batchDeleteRequest1);


        request.setBatchDeleteRequests(batchDeleteRequests);
        BatchResponse response=client.batchDelete(request);
        System.out.println(response);
    }
    /**
     * 批量复制目录或文件
     * @param client
     */
    public static void batchMove(SMHClient client){
        BatchRequest request=new BatchRequest();

        request.setLibraryId("");
        request.setSpaceId("-");
        request.setAccessToken("");

        List<BatchMoveRequest> batchMoveRequestList = new ArrayList<>();
        BatchMoveRequest copyRequest1 = new BatchMoveRequest();
        copyRequest1.setFrom("zfw-weiyankong (1)");
        copyRequest1.setTo("zfw-weiyankong (2)");
        copyRequest1.setMoveAuthority("true");
        batchMoveRequestList.add(copyRequest1);
        request.setBatchMoveRequests(batchMoveRequestList);
        BatchResponse response=client.batchMove(request);
        System.out.println(response);
    }
}
