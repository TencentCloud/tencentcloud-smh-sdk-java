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

package com.tencentcloud.smh;

import com.tencentcloud.smh.internal.batch.BatchRequest;
import com.tencentcloud.smh.internal.directory.DirectoryDetailRequest;
import com.tencentcloud.smh.internal.directory.DirectoryRequest;
import com.tencentcloud.smh.internal.file.FileCopyRequest;
import com.tencentcloud.smh.internal.file.FileLinkRequest;
import com.tencentcloud.smh.internal.file.FileRequest;
import com.tencentcloud.smh.internal.file.FileThumbnailRequest;
import com.tencentcloud.smh.internal.file.FileUrlInfoRequest;
import com.tencentcloud.smh.internal.file.PutFilePartRequest;
import com.tencentcloud.smh.internal.file.PutFileRequest;
import com.tencentcloud.smh.internal.quota.QuotaRequest;
import com.tencentcloud.smh.internal.space.SpaceRequest;
import com.tencentcloud.smh.internal.stats.StatsRequest;
import com.tencentcloud.smh.internal.task.TaskRequest;
import com.tencentcloud.smh.internal.token.TokenRequest;
import com.tencentcloud.smh.model.batch.BatchResponse;
import com.tencentcloud.smh.model.directory.DirectoryContentsResponse;
import com.tencentcloud.smh.model.directory.DirectoryMoveResponse;
import com.tencentcloud.smh.model.directory.DirectoryInfoResponse;
import com.tencentcloud.smh.model.directory.DirectoryDeleteResponse;
import com.tencentcloud.smh.model.directory.DirectoryCopyResponse;
import com.tencentcloud.smh.model.directory.DirectoryCreateResponse;
import com.tencentcloud.smh.model.file.FileConfirmResponse;
import com.tencentcloud.smh.model.file.FileCopyResponse;
import com.tencentcloud.smh.model.file.FileDeleteResponse;
import com.tencentcloud.smh.model.file.FileInfoResponse;
import com.tencentcloud.smh.model.file.FileLinkToResponse;
import com.tencentcloud.smh.model.file.FileMoveResponse;
import com.tencentcloud.smh.model.file.FileUploadStatusResponse;
import com.tencentcloud.smh.model.file.FileUrlInfoResponse;
import com.tencentcloud.smh.model.file.SMHFile;
import com.tencentcloud.smh.model.file.UploadFileResponse;
import com.tencentcloud.smh.model.quota.QuotaCapacityResponse;
import com.tencentcloud.smh.model.quota.QuotaCreateResponse;
import com.tencentcloud.smh.model.space.LibraryUsageResponse;
import com.tencentcloud.smh.model.space.SpaceCreateResponse;
import com.tencentcloud.smh.model.space.SpaceExtensionResponse;
import com.tencentcloud.smh.model.space.SpaceResponse;
import com.tencentcloud.smh.model.space.SpaceSizeResponse;
import com.tencentcloud.smh.model.stats.StatsResponse;
import com.tencentcloud.smh.model.task.TaskResponse;
import com.tencentcloud.smh.model.token.TokenResponse;


import java.util.List;

public interface SMH {

    /**
     * return the client config. client config include the region info, default expired sign time,
     * etc.
     *
     * @return ClientConfig.
     */
    public ClientConfig getClientConfig();

    /**
     * 生成令牌: https://cloud.tencent.com/document/product/1339/71159
     * @param request
     * @return
     */
    TokenResponse getAccessToken(TokenRequest request);

    /**
     * 生成令牌: https://cloud.tencent.com/document/product/1339/71159
     * @param request
     * @return
     */
    TokenResponse postCreateAccessToken(TokenRequest request);

    /**
     * 访问令牌续期 https://cloud.tencent.com/document/product/1339/71160
     * @param tokenRequest
     * @return
     */
    TokenResponse extensionAccessToken(TokenRequest tokenRequest);

    /**
     * 删除特定用户的所有访问令牌 https://cloud.tencent.com/document/product/1339/71162
     * @param tokenRequest
     */
    void deleteUserAllToken(TokenRequest tokenRequest);

    /**
     * 删除特定用户及特定客户端的访问令牌  https://cloud.tencent.com/document/product/1339/71199
     * @param tokenRequest
     */
    void deleteUserClientToken(TokenRequest tokenRequest);

    /**
     * 删除指定的访问令牌  https://cloud.tencent.com/document/product/1339/71161
     * @param tokenRequest
     */
    void deleteAccessToken(TokenRequest tokenRequest);

    /**
     * 创建租户空间 https://cloud.tencent.com/document/product/1339/71156
     * @param spaceRequest
     */
    SpaceCreateResponse createSpace(SpaceRequest spaceRequest);

    /**
     * 查询library空间使用量  https://cloud.tencent.com/document/product/1339/71154
     * @param spaceRequest
     */
    LibraryUsageResponse getLibraryUsage(SpaceRequest spaceRequest);

    /**
     * 批量查询租户空间容量信息 https://cloud.tencent.com/document/product/1339/71155
     * @param spaceRequest
     */
    List<LibraryUsageResponse> getSpaceUsageList(SpaceRequest spaceRequest);
    /**
     * 查询租户空间大小 https://cloud.tencent.com/document/product/1339/71153
     * @param spaceRequest
     */
    SpaceSizeResponse getSpaceSize(SpaceRequest spaceRequest);

    /**
     * 删除租户空间 https://cloud.tencent.com/document/product/1339/71158
     * @param spaceRequest
     */
    void deleteSpace(SpaceRequest spaceRequest);

    /**
     * 查询用户空间属性 https://cloud.tencent.com/document/product/1339/71152
     * @param spaceRequest
     */
    SpaceExtensionResponse getSpaceExtension(SpaceRequest spaceRequest);

    /**
     * 列出租户空间 https://cloud.tencent.com/document/product/1339/71151
     * @param spaceRequest
     */
    List<SpaceResponse> getSpaceList(SpaceRequest spaceRequest);

    /**
     * 查询任务接口 https://cloud.tencent.com/document/product/1339/71002
     * @param taskRequest
     */
    List<TaskResponse> getTask(TaskRequest taskRequest);

    //流量与统计操作
    /**
     * 空间存储信息统计 https://cloud.tencent.com/document/product/1339/71255
     */
    List<StatsResponse> getStats(StatsRequest statsRequest);

    // 配额操作
    /**
     * 创建配额 https://cloud.tencent.com/document/product/1339/71118
     * @param: quotaRequest
    */
    QuotaCreateResponse createQuota(QuotaRequest quotaRequest);

    /**
     * 获取租户空间配额 https://cloud.tencent.com/document/product/1339/71117
     * @param
    */
    QuotaCapacityResponse getQuotaCapacity(QuotaRequest quotaRequest);

    /**
     * 修改配额 https://cloud.tencent.com/document/product/1339/71119
     * @param
     */
    void updateQuotaCapacity(QuotaRequest quotaRequest);

    // 目录和文件批量操作
    /**
     * 批量复制目录或文件 https://cloud.tencent.com/document/product/1339/71258
     * @param batchRequest
     */
    BatchResponse batchCopy(BatchRequest batchRequest);

    /**
     * 批量重命名或移动目录文件 https://cloud.tencent.com/document/product/1339/71260
     * @param batchRequest
     * @return
     */
    BatchResponse batchMove(BatchRequest batchRequest);

    /**
     * 批量删除文件 https://cloud.tencent.com/document/product/1339/71259
     * @param batchRequest
     * @return
     */
    BatchResponse batchDelete(BatchRequest batchRequest);

    //目录或相簿操作

    /**
     * 创建目录或相簿
     * @param directoryRequest
     * @return
     */
    DirectoryCreateResponse createDirectory(DirectoryRequest directoryRequest);

    /**
     * 查看目录或相簿详情 https://cloud.tencent.com/document/product/1339/71146
     * @param directoryRequest
     * @return
     */
    DirectoryInfoResponse getDirectoryInfo(DirectoryRequest directoryRequest);

    /**
     * 复制目录 https://cloud.tencent.com/document/product/1339/71149
     * @param directoryRequest
     * @return
     */
    DirectoryCopyResponse copyDirectory(DirectoryRequest directoryRequest);

    /**
     * 跨空间复制目录
     * @param directoryRequest
     * @return
     */
    DirectoryCopyResponse copyDirectoryCrossSpace(DirectoryRequest directoryRequest);

    /**
     * 检查目录状态 https://cloud.tencent.com/document/product/1339/71145
     * @param directoryRequest
     */
    boolean doesDirectoryExist(DirectoryRequest directoryRequest);

    /**
     * 删除目录 https://cloud.tencent.com/document/product/1339/71147
     * @param directoryRequest
     * @return
     */
    DirectoryDeleteResponse deleteDirectory(DirectoryRequest directoryRequest);

    /**
     * 重命名或移动目录 https://cloud.tencent.com/document/product/1339/71148
     * @param directoryMoveRequest
     * @return
     */
    DirectoryMoveResponse moveDirectory(DirectoryRequest directoryMoveRequest);

    /**
     * 列出目录内容 https://cloud.tencent.com/document/product/1339/71143
     * @param directoryDetailRequest
     * @return
     */
    DirectoryContentsResponse getDirectoryContents(DirectoryDetailRequest directoryDetailRequest);

    // 文件相关

    /**
     * 简单上传文件 https://cloud.tencent.com/document/product/1339/71132
     * @param putFileRequest
     * @return
     */
    FileConfirmResponse putFile(PutFileRequest putFileRequest);

    /**
     * 初始化分块上传文件 https://cloud.tencent.com/document/product/1339/71133
     * @param putFileRequest
     * @return
     */
    UploadFileResponse initiateMultipartUpload(PutFileRequest putFileRequest);

    /**
     * 分块上传
     * @param putFilePartRequest
     */
    void uploadPart(PutFilePartRequest putFilePartRequest);

    /**
     * 分块任务续期 https://cloud.tencent.com/document/product/1339/71134
     * @param fileRequest
     * @return
     */
    UploadFileResponse putPartFileReNewal(FileRequest fileRequest);

    /**
     * 完成上传文件 https://cloud.tencent.com/document/product/1339/71135
     * @param fileRequest
     * @return
     */
    FileConfirmResponse confirmPutFile(FileRequest fileRequest);

    /**
     * 查看文件详情
     * @param fileRequest
     * @return
     */
    FileInfoResponse getFileInfo(FileRequest fileRequest);

    /**
     * 创建符号链接 https://cloud.tencent.com/document/product/1339/71139
     * @param fileRequest
     * @return
     */
    FileLinkToResponse linkFile(FileLinkRequest fileRequest);

    /**
     * 复制文件 https://cloud.tencent.com/document/product/1339/71142
     * @param fileRequest
     * @return
     */
    FileCopyResponse copyFile(FileCopyRequest fileRequest);

    /**
     * 查看文件上传状态 https://cloud.tencent.com/document/product/1339/71136
     * @param fileRequest
     * @return
     */
    FileUploadStatusResponse getFileUploadStatus(FileRequest fileRequest);

    /**
     * 获取文件下载链接和信息 https://cloud.tencent.com/document/product/1339/71127
     * @param fileRequest
     * @return
     */
    FileUrlInfoResponse getFileUrlInfo(FileUrlInfoRequest fileRequest);

    /**
     * 取消上传文件 https://cloud.tencent.com/document/product/1339/71137
     * @param fileRequest
     */
    void cancelUploadFile(FileRequest fileRequest);

    /**
     * 删除文件 https://cloud.tencent.com/document/product/1339/71140
     * @param fileRequest
     * @return
     */
    FileDeleteResponse deleteFile(FileRequest fileRequest);


    /**
     * 获取视频封面缩略图 https://cloud.tencent.com/document/product/1339/71129
     * @param fileThumbnailRequest
     * @return
     */
    SMHFile getFileThumbnail(FileThumbnailRequest fileThumbnailRequest);

    /**
     * 下载文件到本地 https://cloud.tencent.com/document/product/1339/71126
     * @param fileRequest
     * @return
     */
    SMHFile downloadFile(FileUrlInfoRequest fileRequest);

    /**
     * 重命名或移动文件 https://cloud.tencent.com/document/product/1339/71141
     * @param fileRequest
     * @return
     */
    FileMoveResponse moveFile(FileRequest fileRequest);

    /**
     * 检查文件状态 https://cloud.tencent.com/document/product/1339/71128
     * @param fileRequest
     * @return
     */
    boolean doesFileExist(FileUrlInfoRequest fileRequest);
}


