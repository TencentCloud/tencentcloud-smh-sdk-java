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
import com.tencentcloud.smh.Headers;
import com.tencentcloud.smh.SMHClient;
import com.tencentcloud.smh.http.HttpProtocol;
import com.tencentcloud.smh.internal.file.*;
import com.tencentcloud.smh.model.file.*;

import java.io.*;
import java.nio.ByteBuffer;
import java.nio.channels.FileChannel;
import java.util.HashMap;
import java.util.Map;

public class FileDemo {
    public static void main(String[] args) throws Exception {
        ClientConfig clientConfig =new ClientConfig();
        clientConfig.setHttpProtocol(HttpProtocol.http);
        SMHClient client = new SMHClient(clientConfig);
        putFile(client);
//        moveFile(client);
//        getFileThumbnail(client);
//        deleteFile(client);
//        downloadFile(client);
        doesFileExist(client);
//        getFileUrlInfo(client);
//        reNewalPutFile(client);
        // 关闭客户端
        client.shutdown();
    }

    /**
     * 简单上传文件
     * @param client
     */
    public static void putFile(SMHClient client){

        PutFileRequest request= new  PutFileRequest();
//        request.setCrc64("12244146811852800889");
        request.setFile(new File("/Users/kongweiyan/Desktop/test-pic/1.txt"));
//        ObjectMetadata objectMetadata = new ObjectMetadata();
//        Map<String,String> userMetadata = new HashMap<>();
//        userMetadata.put("txt","kwy-test-id-1");
//        objectMetadata.setUserMetadata(userMetadata);
//        request.withMetadata(objectMetadata);

        request.setLibraryId("");
        request.setSpaceId("");
        request.setFilePath("spac1updc/kwy/test/1.txt");
        request.setAccessToken("");
//        request.setConflictResolutionStrategy("ask");
        FileConfirmResponse response=client.putFile(request);
        System.out.println(response);
    }

    /**
     * 查看文件详情
     * @param client
     */
    public static void getFileInfo(SMHClient client){
        FileRequest request=new  FileRequest();
        request.setLibraryId("");
        request.setSpaceId("");
        request.setFilePath("spacedgr/kwy/test/2.txt");
//        request.setFilePath("foo-kwy-test/1-link.txt");
//        request.setFilePath("foo-kwy-test/1-copy.txt");
        request.setAccessToken("");
        FileInfoResponse response=client.getFileInfo(request);
        System.out.println(response);
    }

    /**
     * 创建符号链接
     * @param client
     */
    public static void linkFile(SMHClient client){
        FileLinkRequest request=new  FileLinkRequest();
        request.setLibraryId("");
        request.setSpaceId("");
        request.setFilePath("spacedgr/kwy/test/2-link.txt");
        request.setAccessToken("");
        FileLinkBodyRequest linkBodyRequest = new FileLinkBodyRequest();
        linkBodyRequest.setLinkTo("spacedgr/kwy/test/2.txt");
        request.setLinkBodyRequest(linkBodyRequest);
        FileLinkToResponse response=client.linkFile(request);
        System.out.println(response);
    }

    /**
     * 复制文件
     * @param client
     */
    public static void copyFile(SMHClient client){
        FileCopyRequest request=new  FileCopyRequest();
        request.setLibraryId("");
        request.setSpaceId("");
        request.setFilePath("spacedgr/kwy/test/2-copy.txt");
        request.setAccessToken("");
        FileCopyBodyRequest bodyRequest = new FileCopyBodyRequest();
        bodyRequest.setCopyFrom("spacedgr/kwy/test/2.txt");
        request.setFileCopyBodyRequest(bodyRequest);
        FileCopyResponse response=client.copyFile(request);
        System.out.println(response);
    }



    /**
     * 获取文件上传任务状态
     * @param client
     */
    public static void getFileUploadStatus(SMHClient client){
        FileRequest request=new  FileRequest();
        request.setLibraryId("");
        request.setSpaceId("");
        request.setAccessToken("");
        request.setConfirmKey("");
        FileUploadStatusResponse response=client.getFileUploadStatus(request);
        System.out.println(response);
    }

    /**
     * 获取文件下载链接和信息
     * @param client
     */
    public static void getFileUrlInfo(SMHClient client){
        FileUrlInfoRequest request=new  FileUrlInfoRequest();
        request.setLibraryId("");
        request.setSpaceId("");
        request.setAccessToken("");
        request.setFilePath("spacedgr/kwy/test/2.txt");
        request.setPurpose("preview");
        request.setContentDisposition("inline");
        FileUrlInfoResponse response=client.getFileUrlInfo(request);
        System.out.println(response);
    }

    /**
     * 删除文件
     * @param client
     */
    public static void deleteFile(SMHClient client){
        FileRequest request=new  FileRequest();
        request.setLibraryId("");
        request.setSpaceId("");
        request.setAccessToken("");
        request.setFilePath("spacedgr/kwy/test/2.txt");
        request.setPermanent("0");
        FileDeleteResponse response=client.deleteFile(request);
        System.out.println(response);
    }

    /**
     * 判断文件是否存在
     * @param client
     */
    public static void doesFileExist(SMHClient client){
        FileUrlInfoRequest request=new  FileUrlInfoRequest();
        request.setLibraryId("");
        request.setSpaceId("");
        request.setAccessToken("");
        request.setFilePath("space651updc/kwy/test/1.txt");
        boolean response=client.doesFileExist(request);
        System.out.println(response);
    }

    /**
     * 分块上传
     * @param client
     */
    public static void putFilePart(SMHClient client){
        File file = new File("/Users/kongweiyan/Desktop/test-pic/1-60题易错题.mp4");
        PutFileRequest request= new  PutFileRequest();

        ObjectMetadata objectMetadata = new ObjectMetadata();
        Map<String,String> userMetadata = new HashMap<>();
        userMetadata.put("request-id","kwy-test-request-id-rar");
        objectMetadata.setUserMetadata(userMetadata);
        request.withMetadata(objectMetadata);

        request.setLibraryId("");
        request.setSpaceId("");
        request.setFilePath("spacedgr/kwy/test/1-60题易错题.mp4");
        request.setAccessToken("");
        UploadFileResponse uploadFileResponse=client.initiateMultipartUpload(request);
        System.out.println(uploadFileResponse.getConfirmKey());
        FileInputStream fileInputStream = null;

        try {
            fileInputStream = new FileInputStream(file);
            FileChannel fileChannel = fileInputStream.getChannel();

            int capacity = 1*1024*1024;//1M
            ByteBuffer byteBuffer = ByteBuffer.allocate(capacity);
            int i = 1;
            while( fileChannel.read(byteBuffer) != -1) {
                System.out.println(i);
                //读取后，将位置置为0，将limit置为容量, 以备下次读入到字节缓冲中，从0开始存储
                PutFilePartRequest putFilePartRequest = new PutFilePartRequest();
                putFilePartRequest.setDomain(uploadFileResponse.getDomain());
                putFilePartRequest.setPartNumber(String.valueOf(i));
                putFilePartRequest.setPath(uploadFileResponse.getPath());
                putFilePartRequest.setUploadId(uploadFileResponse.getUploadId());
                ObjectMetadata cosMetadata=new ObjectMetadata();
                Map<String,String> headers = uploadFileResponse.getHeaders();
                headers.put(Headers.CONTENT_LENGTH,String.valueOf(capacity));
                cosMetadata.setUserMetadata(uploadFileResponse.getHeaders());
                putFilePartRequest.setMetadata(cosMetadata);
                byteBuffer.clear();
                putFilePartRequest.setInputStream(new ByteArrayInputStream(byteBuffer.array()));
                client.uploadPart(putFilePartRequest);
                i ++;
            }
            FileRequest fileRequest = new FileRequest();
            fileRequest.setLibraryId(request.getLibraryId());
            fileRequest.setSpaceId(request.getSpaceId());
            fileRequest.setConfirmKey(uploadFileResponse.getConfirmKey());
            fileRequest.setConflictResolutionStrategy(request.getConflictResolutionStrategy());
            fileRequest.setAccessToken(request.getAccessToken());
            FileConfirmResponse fileConfirmResponse = client.confirmPutFile(fileRequest);
            System.out.println(fileConfirmResponse);
            fileInputStream.close();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
            System.out.println(e);
        }finally {
            if (fileInputStream != null) {
                try {
                    fileInputStream.close();
                } catch (IOException e) {
                    System.out.println(e);
                }
            }
        }

    }

    /**
     * 取消上传文件
     * @param client
     */
    public static void cancelUploadFile(SMHClient client){
        FileRequest request = new FileRequest();
        request.setLibraryId("");
        request.setSpaceId("");
        request.setConfirmKey("");
        request.setAccessToken("");
        client.cancelUploadFile(request);
    }

    /**
     * 重命名或移动文件
     * @param client
     */
    public static void moveFile(SMHClient client){
        FileRequest request = new FileRequest();
        request.setLibraryId("");
        request.setSpaceId("");
        request.setFilePath("spacedgr/kwy/test/2-copy-rename.txt");
        request.setAccessToken("8b0de1075d0c2d6b0c86ff0b0972e90252dbe74a3e6a78b59029964022479442e993dd725966436736eb58918ac95540deb04abd8848e4c9ad2c9af2d7a43970");
        FileMoveBodyRequest fileMoveBodyRequest = new FileMoveBodyRequest();
        fileMoveBodyRequest.setFrom("spacedgr/kwy/test/2-copy.txt");
        request.setFileMoveBodyRequest(fileMoveBodyRequest);
        FileMoveResponse fileMoveResponse =  client.moveFile(request);
        System.out.println(fileMoveResponse);
    }

    /**
     * 下载文件到本地
     * @param client
     */
    public static void downloadFile(SMHClient client) {
        FileUrlInfoRequest request = new FileUrlInfoRequest();
        request.setLibraryId("");
        request.setSpaceId("");
        request.setPurpose("download");
        request.setAccessToken("");
        request.setFilePath("spacedgr/kwy/test/1-60题易错题.mp4");
//        request.setFilePath("spacedgr/kwy/test/go1.18.5.linux-amd64.tar.gz");
//        request.setLocalFile(new File("/Users/kongweiyan/Desktop/smhtest/go1.18.5.linux-amd64.tar.gz"));
        request.setLocalFile(new File("/Users/kongweiyan/Desktop/smhtest/1-60题易错题.mp4"));
        client.downloadFile(request);
    }

    /**
     * 分块上传任务续期
     * @param client
     */
    public static void reNewalPutFile(SMHClient client) {
        FileRequest request = new FileRequest();
        request.setLibraryId("");
        request.setSpaceId("");
        request.setConfirmKey("");
        request.setAccessToken("");
        UploadFileResponse response =  client.putPartFileReNewal(request);
        System.out.println(response);
    }

    /**
     * 续传
     * @param client
     */
    public static void putFilePartCont(SMHClient client){
        File file = new File("/Users/kongweiyan/Desktop/抖音直播录制工具x64（1201更新）.rar");
        FileRequest request = new FileRequest();
        request.setLibraryId("");
        request.setSpaceId("");
        request.setConfirmKey("32cd0d5f161b21e1e6a8befb5fa55e10f8a3d67538df814112fd7ce2f2dd30fa");
        request.setAccessToken("8b0de1075d0c2d6b0c86ff0b0972e90252dbe74a3e6a78b59029964022479442e993dd725966436736eb58918ac95540deb04abd8848e4c9ad2c9af2d7a43970");
        UploadFileResponse uploadFileResponse =  client.putPartFileReNewal(request);
        System.out.println(uploadFileResponse.getConfirmKey());
        FileInputStream fileInputStream = null;

        try {
            fileInputStream = new FileInputStream(file);
            FileChannel fileChannel = fileInputStream.getChannel();

            int capacity = 1*1024*1024;//1M
            ByteBuffer byteBuffer = ByteBuffer.allocate(capacity);
            int i = 1;
            while( fileChannel.read(byteBuffer) != -1) {
                System.out.println(i);
                //读取后，将位置置为0，将limit置为容量, 以备下次读入到字节缓冲中，从0开始存储
                PutFilePartRequest putFilePartRequest = new PutFilePartRequest();
                putFilePartRequest.setDomain(uploadFileResponse.getDomain());
                putFilePartRequest.setPartNumber(String.valueOf(i));
                putFilePartRequest.setPath(uploadFileResponse.getPath());
                putFilePartRequest.setUploadId(uploadFileResponse.getUploadId());
                ObjectMetadata cosMetadata=new ObjectMetadata();
                Map<String,String> headers = uploadFileResponse.getHeaders();
                headers.put(Headers.CONTENT_LENGTH,String.valueOf(capacity));
                cosMetadata.setUserMetadata(uploadFileResponse.getHeaders());
                putFilePartRequest.setMetadata(cosMetadata);
                byteBuffer.clear();
                putFilePartRequest.setInputStream(new ByteArrayInputStream(byteBuffer.array()));
                client.uploadPart(putFilePartRequest);
                i ++;
            }
            FileRequest fileRequest = new FileRequest();
            fileRequest.setLibraryId(request.getLibraryId());
            fileRequest.setSpaceId(request.getSpaceId());
            fileRequest.setConfirmKey(uploadFileResponse.getConfirmKey());
            fileRequest.setConflictResolutionStrategy(request.getConflictResolutionStrategy());
            fileRequest.setAccessToken(request.getAccessToken());
            FileConfirmResponse fileConfirmResponse = client.confirmPutFile(fileRequest);
            System.out.println(fileConfirmResponse);
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
            System.out.println(e);
        }finally {
            if (fileInputStream != null) {
                try {
                    fileInputStream.close();
                } catch (IOException e) {
                    System.out.println(e);
                }
            }
        }

    }


    /**
     * 获取视频封面缩略图
     * @param smhClient
     */
    public static void getFileThumbnail(SMHClient smhClient){
        FileThumbnailRequest request = new FileThumbnailRequest();
        request.setLocalFile(new File("/Users/kongweiyan/Desktop/smhtest/易错题.jpg"));
        request.setLibraryId("");
        request.setSpaceId("space3pu367651updc");
        request.setPurpose("download");
        request.setAccessToken("8b0de1075d0c2d6b0c86ff0b0972e90252dbe74a3e6a78b59029964022479442e993dd725966436736eb58918ac95540deb04abd8848e4c9ad2c9af2d7a43970");
//        request.setFilePath("spacedgr/kwy/test/2.txt");
        request.setFilePath("spacedgr/kwy/test/1-60题易错题.mp4");
        request.setHeightSize("500");
        request.setHeightSize("500");
        smhClient.getFileThumbnail(request);
    }
}
